package jembalang.compfest.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Explosion implements DrawableObject{
	private int currentFrame;
	private Rect drawPosition;
	public boolean active;
	private boolean timed;
	private int len;
	private int time;
	Bitmap[] frameImage;
	public Explosion(Bitmap[] images, Rect drawPosition){
		currentFrame = 0;
		time = 0;
		timed = false;
		active = true;
		frameImage = images;
		len =frameImage.length;
		int dx = (frameImage[currentFrame].getWidth()-drawPosition.width())/2;
		int dy = (frameImage[currentFrame].getHeight()-drawPosition.height())/2;
		this.drawPosition = new Rect(drawPosition.left-dx, drawPosition.top-dy,drawPosition.right+dx,drawPosition.bottom+dy);
		
	}
	public Explosion(Bitmap[] images, Rect drawPosition,int time){
		currentFrame = 0;
		active = true;
		frameImage = images;
		this.time = time;
		timed = true;
		len =frameImage.length;
		int dx = (frameImage[currentFrame].getWidth()-drawPosition.width())/2;
		int dy = (frameImage[currentFrame].getHeight()-drawPosition.height())/2;
		this.drawPosition = new Rect(drawPosition.left-dx, drawPosition.top-dy,drawPosition.right+dx,drawPosition.bottom+dy);
		
	}
	@Override
	public void draw(Canvas canvas) {
		if (timed){
			if (time > 0){
				canvas.drawBitmap(frameImage[currentFrame], null, drawPosition, null);
				currentFrame++;
				currentFrame = currentFrame % len;
				time--;
			} else {
				active = false;
			}
		} else {
			if (currentFrame < len){
				canvas.drawBitmap(frameImage[currentFrame], null, drawPosition, null);
				currentFrame++;
			} else {
				active = false;
			}
		}
	}
	public static void makeExplosion(Bitmap[] images,LayerManager host, Rect drawPosition){
		host.append(new Explosion(images, drawPosition));
	}
	public static void makeExplosion(Bitmap[] images,LayerManager host, Rect drawPosition, int time){
		host.append(new Explosion(images, drawPosition, time));
	}
	@Override
	public boolean isActive() {
		return active;
	}
	
}
