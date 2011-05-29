package jembalang.compfest.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;

public class Weapon {
	private int bullet;
	private int base_damage;
	private Rect area;
	private int type;
	private int buff;
	private static int currentWeapon;
	private static List<Weapon> weaponList;
	private int ttint;
	private int tint;
	public static final int GUN = 0;
	public static final int BUFF_NO = 0;
	public static Vector<Bitmap[]> explosionImage;
	private static GameThread host;
	
	public static void setView(GameThread h){
		host = h;
	}
	public static void init() {
		if (weaponList == null){
			weaponList = new ArrayList<Weapon>();
			explosionImage = new Vector<Bitmap[]>(); 
			currentWeapon = 0;
			Weapon.Factory(GUN);
		}
	}
	public static Weapon take(){
		if (weaponList == null){
			init();
		}
		return weaponList.get(currentWeapon);
	}
	public static void nextWeapon() {
		currentWeapon = (currentWeapon + 1) % weaponList.size();
	}
	public static void prevWeapon() {
		currentWeapon = (currentWeapon - 1) % weaponList.size();
	}
	private Weapon(int type){
		this.type = type;
		if (type == GUN){
			area = new Rect(0,0,40,40);
			base_damage = 20;
			buff = BUFF_NO;
			bullet = -1;
			ttint = 10;
			tint = Color.RED;
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
			if (type == GUN){
				Weapon wp;
				wp = new Weapon(type);
				weaponList.add(wp);
				Bitmap[] explosion = new Bitmap[12];
				Bitmap tmp = BitmapFactory.decodeResource(host.getResources(), R.drawable.explosion);
				for (int i=0; i<12; i++){
					explosion[i]= Bitmap.createBitmap(tmp, i*75,0 , 75, 75);
				}
				explosionImage.add(explosion);
				tmp.recycle();
			}
		}
	}
	public void setFire(int x, int y){
		if (bullet > 0 || bullet == -1){
			if (bullet>0) bullet -= 1;
			x -= (area.width()/2);
			y -= (area.height()/2);
			area.offsetTo(x, y);
			Explosion.makeExplosion(explosionImage.get(currentWeapon), host.getLayerManager(), area);
		}
	}
	public void setFire(float x, float y){
		setFire((int) x, (int) y);
	}
	public int getDamage(Rect enemy){
		int damage = 0;
		Rect k = new Rect(getArea());
		if (k.setIntersect(getArea(), enemy)) {
			damage = (countArea(k)* base_damage/countArea(enemy));
		}
		return damage;
	}
	public static int countArea(Rect a){
		return ((a.right-a.left) * (a.bottom-a.top));
	}
	public Rect getArea(){
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
