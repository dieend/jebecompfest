package jembalang.compfest.game;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class Weapon {
	private int bullet;
	private int base_damage;
	private RectF area;
	private int type;
	private int buff;
	private static int currentWeapon;
	private static List<Weapon> weaponList;
	private int ttint;
	private int tint;
	private static GameThread host;
	
	public static final int GUN = 0;
	public static final int SLOWER = 1;
	public static final int BUFF_NO = 0;
	public static final int BUFF_FREEZE = 1;

	private Weapon(int type){
		this.type = type;
		if (type == GUN){
			area = new RectF(0,0,15,15);
			base_damage = 20;
			buff = BUFF_NO;
			bullet = -1;
			ttint = 10;
			tint = Color.RED;
		} else if (type == SLOWER) {
			area = new RectF(0,0,40,40);
			base_damage = 10;
			buff = BUFF_FREEZE;
			bullet = 10;
			ttint = 100;
			tint = Color.BLUE;
		}
	}
	
	
	public static void init(GameThread h,int...weaponTypes) {
		if (weaponList == null){
			weaponList = new ArrayList<Weapon>(); 
			currentWeapon = 0;
			host = h;
			for(int type: weaponTypes){
				Weapon.Factory(type);
			}
		}
	}
	public static Weapon take(){
		if (weaponList == null){
			Log.e("JB", "Weapon not initialized");
		}
		return weaponList.get(currentWeapon);
	}
	public static void destroy() {
		weaponList = null;
		host = null;
	}
	public static void nextWeapon() {
		currentWeapon = (currentWeapon + 1) % weaponList.size();
	}
	public static void prevWeapon() {
		currentWeapon = (currentWeapon - 1) % weaponList.size();
	}
	public int buffType() {
		return buff;
	}

	public static void Factory(int type){
		boolean found = false;
		for (Weapon w: weaponList){
			if (w.type == type){
				found = true;
				break;
			}
		}
		if (!found){
			Weapon wp;
			wp = new Weapon(type);
			weaponList.add(wp);
			ImageCollection.is().getImage(ImageCollection.IMAGE_WEAPON_EXPLOSION, type);	
		}
	}
	public void setFire(int x, int y){
	
		if (bullet>0) bullet -= 1;
		x -= (area.width()/2);
		y -= (area.height()/2);
		area.offsetTo(x, y);
		Explosion.makeExplosion(ImageCollection.is().getImage(ImageCollection.IMAGE_WEAPON_EXPLOSION, type), 
				host.getLayerManager(), area);
	}
	public boolean noBullet() {
		if (bullet > 0 || bullet == -1)
			return false;
		else return true;
	}
	public void setFire(float x, float y){
		setFire((int) x, (int) y);
	}
	public int getDamage(RectF enemy, int[] score){
		int damage = 0;
		RectF k = new RectF();
		float a = countArea(getArea());
		float e = countArea(enemy);
		if (k.setIntersect(getArea(), enemy)) {
			float i = countArea(k);
			damage = (int) (i * base_damage/Math.min(e,a));
			score[0] = (int) (score[0] * i / a);
		}
		return damage;
	}
	
	public static float countArea(RectF a){
		return ((a.width()) * (a.height()));
	}
	public RectF getArea(){
		return area;
	}
	public int bulletRemaining(){
		return bullet;
	}
	public void reload(int bulletNumber){
		bullet += bulletNumber;
	}
	public int tintColor(){
		return tint;
	}
	public int tintTime(){
		return ttint;
	}
}
