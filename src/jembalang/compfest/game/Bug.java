package jembalang.compfest.game;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bug extends Sprite {
	private MoveFunction mv;
	private int HP;
	private static Random rand = new Random(System.currentTimeMillis());
	private static int getRandom(int bounds){
		return (Math.abs(rand.nextInt())%bounds);
	}
	public Bug(Resources r, int id, int column, int row, GameView host){
		super(r, id, column, row);
		this.host = host;
		init();
	}
	public Bug(Bitmap image, int column, int row, GameView host){
		super(image, column, row);
		this.host = host;
		init();
	}
	private void init() {
		setPosition(getRandom(host.getViewWidth()),0);
		HP = 100;
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
		//tes
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		canvas.drawRect(x, y, x+30, y+5, paint);
		paint.setColor(Color.GREEN);
		canvas.drawRect(x, y, x+(HP*30/100), y+5, paint);
	}
	public void die(){
		this.setVisible(false);
		host.getLayerManager().remove(this);
	}
	public void hit(Weapon weapon){
		HP-=weapon.getDamage(rect);
		if (HP<= 0){
			die();
		}
	}
	public boolean isAlive(){
		return (HP>0);
	}
}
