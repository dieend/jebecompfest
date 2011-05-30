package jembalang.compfest.game;

import java.util.Random;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Bug extends Sprite {
	private MoveFunction mv;
	private int HP;
	private int maxHP;
	private Paint paint;
	private static Random rand = new Random(System.currentTimeMillis());
	private int attack;
	private int base_score;
	private int type;
	public static Vector<Bitmap[]> DieImage = new Vector<Bitmap[]>();
	public static Vector<Bitmap[]> Image = new Vector<Bitmap[]>();
	public static final int TYPE1 = 0;
	public static final int BIRD= 1;
	private static int getRandom(int bounds){
		return (Math.abs(rand.nextInt())%bounds);
	}
	
	public int getType() {
		return type;
	}
	public static Bug Factory(int bugType, GameThread host){
		Bug tmp = null;
		if (bugType == TYPE1){
			tmp = new Bug(ImageCollection.is().getImage(ImageCollection.IMAGE_BUG, bugType), host);
			tmp.setPosition(getRandom(host.getViewWidth()),0);
			tmp.maxHP = 100;
			tmp.HP = 100;
			tmp.attack = 10;
			tmp.setFunction(MoveFunction.Factory(MoveFunction.LINEAR, 1));
			tmp.base_score = 100;
//			tmp.paint = new Paint();
		} else if (bugType == BIRD){
			tmp = new Bug(ImageCollection.is().getImage(ImageCollection.IMAGE_BUG, bugType), host);
			tmp.maxHP = 1;
		}
		tmp.paint = new Paint();
		tmp.type = bugType;
		return tmp;
	}
	private Bug(Bitmap[] images, GameThread host){
		super(images);
		this.host = host;
	}

	public void setFunction(MoveFunction mv){
		if (mv == null){
			this.mv = MoveFunction.Factory(MoveFunction.STAY, 0);
		} else {
			this.mv = mv;
		}
	}
	public void update(float time){
		move(mv.getdx(time), mv.getdy(time));
		nextFrame();
		//tes
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		paint.setColor(Color.RED);
		canvas.drawRect(x, y, x+30, y+5, paint);
		paint.setColor(Color.GREEN);
		canvas.drawRect(x, y, x+(HP*30/maxHP), y+5, paint);
	}
	
	public int hit(Weapon weapon,boolean[] hitted){
		int[] score = new int[1];
		score[0] = 0;
		hitted[0] = false;
		if (Rect.intersects(weapon.getArea(),getRectangle())){
			hitted[0] = true;
			score[0] = base_score;
			int d=weapon.getDamage(getRectangle(),score);
			if (d>0){
				HP-=d; 
				setTint(weapon.tintColor(),weapon.tintTime());
			}
		}
		return score[0];
	}
	public boolean isAlive(){
		return (HP>0);
	}
	public int damage() {
		return attack;
	}
}
