package jembalang.compfest.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Explosion implements DrawableObject{
	private int currentFrame;
	private Rect drawPosition;
	public boolean active;
	private int len;
	Bitmap[] frameImage;
	public Explosion(Bitmap[] images,LayerManager host, Rect drawPosition){
		currentFrame = 0;
		active = true;
		frameImage = images;
		len =frameImage.length;
		int dx = (frameImage[currentFrame].getWidth()-drawPosition.width())/2;
		int dy = (frameImage[currentFrame].getHeight()-drawPosition.height())/2;
		this.drawPosition = new Rect(drawPosition.left-dx, drawPosition.top-dy,drawPosition.right+dx,drawPosition.bottom+dy);
		
	}
	@Override
	public void draw(Canvas canvas) {
		if (currentFrame < len){
			canvas.drawBitmap(frameImage[currentFrame], null, drawPosition, null);
			currentFrame++;
		} else {
			active = false;
		}
	}
	public static void makeExplosion(Bitmap[] images,LayerManager host, Rect drawPosition){
		host.append(new Explosion(images, host, drawPosition));
	}
	@Override
	public boolean isActive() {
		return active;
	}
	
}
