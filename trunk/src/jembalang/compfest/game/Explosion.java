package jembalang.compfest.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Explosion implements DrawableObject{
	private int currentFrame;
	private RectF drawPosition;
	public boolean active;
	private boolean timed;
	private int len;
	private int time;
	Bitmap[] frameImage;
	public Explosion(Bitmap[] images, RectF drawPosition){
		currentFrame = 0;
		time = 0;
		timed = false;
		active = true;
		frameImage = images;
		len =frameImage.length;
		float dx = (frameImage[currentFrame].getWidth()-drawPosition.width())/2;
		float dy = (frameImage[currentFrame].getHeight()-drawPosition.height())/2;
		this.drawPosition = new RectF(drawPosition.left-dx, drawPosition.top-dy,drawPosition.right+dx,drawPosition.bottom+dy);
		
	}
	public Explosion(Bitmap[] images, RectF drawPosition,int time){
		currentFrame = 0;
		active = true;
		frameImage = images;
		this.time = time;
		timed = true;
		len =frameImage.length;
		float dx = (frameImage[currentFrame].getWidth()-drawPosition.width())/2;
		float dy = (frameImage[currentFrame].getHeight()-drawPosition.height())/2;
		this.drawPosition = new RectF(drawPosition.left-dx, drawPosition.top-dy,drawPosition.right+dx,drawPosition.bottom+dy);
		
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
	public static void makeExplosion(Bitmap[] images,LayerManager host, RectF drawPosition){
		host.append(new Explosion(images, drawPosition));
	}
	public static void makeExplosion(Bitmap[] images,LayerManager host, RectF drawPosition, int time){
		host.append(new Explosion(images, drawPosition, time));
	}
	@Override
	public boolean isActive() {
		return active;
	}
	
}
