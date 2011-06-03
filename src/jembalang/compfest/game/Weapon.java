package jembalang.compfest.game;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.Log;

public class Weapon {
	/*
	 * how to add a new Weapon:
	 * - Add constant BUFF (if it have any) and WEAPONTYPE
	 * - Add the constructor at Weapon(int type), just copy the previous and edit
	 * - Define weapon image explosion at ImageCollection
	 * - Define the buff effect at bug.hit()
	 */
	private int bullet;
	private int base_damage;
	private RectF area;
	int type;
	private int buff;
	private int delay;
	private static int currentWeapon;
	private static List<Weapon> weaponList;
	private int ttint;
	private int tint;
	private Bitmap image;
	private static GameThread host;
	int price;
	int maxBullet;
	public static boolean active; 
	public static final int GUN = 0;
	public static final int SLOWER = 1;
	public static final int BURNER = 2;
	public static final int RUDAL = 3;
	public static final int RUDAL2 = 4;
	public static final int BUFF_NO = 0;
	public static final int BUFF_FREEZE = 1;
	public static final int BUFF_PARALYZED= 2;

	private Weapon(int type){
		this.type = type;
		if (type == GUN){
			area = new RectF(0,0,20,20);
			base_damage = 20;
			buff = BUFF_NO;
			maxBullet = 60;
			ttint = 10;
			tint = Color.RED;
			delay = 0;
			price = 0;
		} else if (type == SLOWER) {
			area = new RectF(0,0,40,40);
			base_damage = 10;
			buff = BUFF_FREEZE;
			maxBullet = 10;
			ttint = 100;
			tint = Color.BLUE;
			delay = 0;
			price = 200;
		} else if (type == BURNER){
			area = new RectF(0,0,10,10);
			base_damage = 50;
			buff = BUFF_PARALYZED;
			maxBullet = 10;
			ttint = 20;
			tint = Color.YELLOW;
			delay = 10;
			price = 400;
		} else if (type == RUDAL){
			area = new RectF(0,0,240,320);
			base_damage = 100;
			buff = BUFF_NO;
			maxBullet = 2;
			ttint = 10;
			tint = Color.RED;
			delay = 3000;
			price = 500;
		}
		bullet = maxBullet;
		this.image = ImageCollection.is().getImage(ImageCollection.IMAGE_WEAPON, type)[0];
	}
	
	
	public static void init(GameThread h,Integer...weaponTypes) {
		weaponList = new ArrayList<Weapon>(); 
		currentWeapon = 0;
		host = h;
		for(int type: weaponTypes){
			Weapon.Factory(type);
		}
		active = false;
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
		if (currentWeapon == 0){
			currentWeapon = weaponList.size() - 1;
		} else {
			currentWeapon = currentWeapon - 1;
		}
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
		if (type != RUDAL){
			x -= (area.width()/2);
			y -= (area.height()/2);
			area.offsetTo(x, y);
		}
		Explosion.makeExplosion(ImageCollection.is().getImage(ImageCollection.IMAGE_WEAPON_EXPLOSION, type), 
				host.getLayerManager(), area);
		active = true;
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
	public int delay(){
		return delay;
	}
	public Bitmap getImage(){
		return image;
	}
	public void reload() {
		if (Player.getInstance().buy(price)){
			bullet = maxBullet;
		}
	}
}
