package jembalang.compfest.game;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class Weapon {
	private int bullet;
	private int base_damage;
	private Rect area;
	private int type;
	private int buff;
	private static Canvas canvas;
	private static int currentWeapon;
	private static List<Weapon> weaponList;
	public static final int GUN = 0;
	public static final int BUFF_NO = 0;
	private static View host;
	private static Paint paint = new Paint();
	public static void setView(View h){
		host = h;
	}
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
		this.type = type;
		if (type == GUN){
			area = new Rect(0,0,10,10);
			base_damage = 20;
			buff = BUFF_NO;
			bullet = -1;
		}
	}
	public static void setCanvas(Canvas c){
		canvas = c;
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
//		(new Animate(x, y,area)).start();
	}
	public void setFire(float x, float y){
		x -= ((int)((area.right-area.left)/2));
		y -= ((int)((area.bottom-area.top)/2));
		area.offsetTo((int)x, (int)y);
//		(new Animate((int)x, (int)y,area)).start();
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
	public Rect getArea(){
		return area;
	}
//	class Animate extends Thread {
//		private int x;
//		private int y;
//		Rect area;
//		Animate(int x, int y, Rect area){
//			this.x = x;
//			this.y = y;
//			this.area = new Rect(area);
//		}
//		public void run() {
//			if (type == GUN){	
//				paint.setColor(Color.RED);
//			}
//			int draw = 1000;
//			while (draw > 0){
//				draw--;
////				host.postInvalidate(area.left, area.top, area.right, area.bottom);
//				canvas.drawRect(area, paint);
//			}
//			Log.d("WEAPON", "ANIMATE");
//		}
//	}
}
