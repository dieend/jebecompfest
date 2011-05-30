package jembalang.compfest.game;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Bug extends Sprite {
	private MovingFunction mf;
	private int HP;
	private int maxHP;
	private Paint paint;
	private static Random rand = new Random(System.currentTimeMillis());
	private int attack;
	private int base_score;
	private int type;
	public static final int TYPE1 = 0;
	public static final int BIRD= 1;
	public static final int BUG1 = 2;
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
			tmp.maxHP = 20;
			tmp.HP = tmp.maxHP;
			tmp.attack = 10;
			tmp.setFunction(null);
			tmp.base_score = 100;
//			tmp.paint = new Paint();
		} else if (bugType == BIRD){
			tmp = new Bug(ImageCollection.is().getImage(ImageCollection.IMAGE_BUG, bugType), host);
			tmp.maxHP = 1;
		} else if (bugType == BUG1){
			tmp = new Bug(ImageCollection.is().getImage(ImageCollection.IMAGE_BUG, bugType), host);
			tmp.setPosition(getRandom(host.getViewWidth()),0);
			tmp.maxHP = 30;
			tmp.HP = tmp.maxHP;
			tmp.attack = 10;
			tmp.setFunction(null);
			tmp.base_score = 100;
//			tmp.paint = new Paint();
		}
		tmp.visible = false;
		tmp.paint = new Paint();
		tmp.type = bugType;
		return tmp;
	}
	private Bug(Bitmap[] images, GameThread host){
		super(images);
		this.host = host;
	}

	public void setFunction(MovingFunction mf){
		if (mf == null){
			this.mf = new MovingFunction(this, MovingFunction.getEasy());
		} else {
			this.mf = mf;
		}
	}
	public void update(float time){
		float dx = mf.getdx(time);
		float dy = mf.getdy(time);
		setRotate(Math.atan2(dy, dx)* 180/Math.PI-90);
		move(dx, dy);
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
