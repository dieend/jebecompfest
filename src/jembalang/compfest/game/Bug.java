package jembalang.compfest.game;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class Bug extends Sprite {
	private MoveFunction mv;
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
	}
	public void setFunction(MoveFunction mv){
		if (mv == null){
			this.mv = MoveFunction.Factory(MoveFunction.STAY, 0);
		} else {
			this.mv = mv;
		}
	}
	public void update(){
		move(mv.getdx(), mv.getdy());
	}
	public void die(){
		this.setVisible(false);
	}
}
