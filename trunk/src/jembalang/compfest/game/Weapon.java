package jembalang.compfest.game;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;

public class Weapon {
	private int bullet;
	private int base_damage;
	private Rect area;
	private int type;
	private int buff;
	
	private static int currentWeapon;
	private static List<Weapon> weaponList;
	public static final int GUN = 0;
	public static final int BUFF_NO = 0;
	public static Weapon take(){
		if (weaponList == null){
			weaponList = new ArrayList<Weapon>();
			currentWeapon = 0;
			Weapon.Factory(GUN);
		}
		Weapon p =weaponList.get(currentWeapon); 
		return p;
	}
	public static void nextWeapon() {
		currentWeapon = (currentWeapon + 1) % weaponList.size();
	}
	public static void prevWeapon() {
		currentWeapon = (currentWeapon - 1) % weaponList.size();
	}
	private Weapon(int type){
		if (type == GUN){
			area = new Rect(0,0,10,10);
			base_damage = 20;
			buff = BUFF_NO;
			bullet = -1;
			this.type = GUN;
		}
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
			}
		}
	}
	public void setFire(int x, int y){
		x -= ((area.right-area.left)/2);
		y -= ((area.bottom-area.top)/2);
		area.offsetTo(x, y);
	}
	public void setFire(float x, float y){
		x -= ((int)((area.right-area.left)/2));
		y -= ((int)((area.bottom-area.top)/2));
		area.offsetTo((int)x, (int)y);
	}
	public int getDamage(Rect enemy){
		int damage = 0;
		Rect k = new Rect(area);
		k.setIntersect(area, enemy);
		damage = (countArea(k)/countArea(area)) * base_damage;
		return damage;
	}
	public static int countArea(Rect a){
		return ((a.right-a.left) * (a.bottom-a.top));
	}
	class Animate extends Thread {
		public void run() {
			
		}
	}
}
