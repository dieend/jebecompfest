package jembalang.compfest.game;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Explosion implements DrawableObject{
	private int currentFrame;
	private int weaponSource;
	private Rect drawPosition;
	public boolean active;
	public Explosion(int weaponSource,LayerManager host, Rect drawPosition){
		this.weaponSource = weaponSource;
		currentFrame = 0;
		active = true;
		int dx = (Weapon.explosionImage.get(weaponSource)[currentFrame].getWidth()-drawPosition.width())/2;
		int dy = (Weapon.explosionImage.get(weaponSource)[currentFrame].getHeight()-drawPosition.height())/2;
		this.drawPosition = new Rect(drawPosition.left-dx, drawPosition.top-dy,drawPosition.right+dx,drawPosition.bottom+dy);
		
	}
	@Override
	public void draw(Canvas canvas) {
		if (currentFrame < Weapon.explosionImage.get(weaponSource).length){
			canvas.drawBitmap(Weapon.explosionImage.get(weaponSource)[currentFrame], null, drawPosition, null);
			currentFrame++;
		} else {
			active = false;
		}
	}
	public static void makeExplosion(int weaponSource,LayerManager host, Rect drawPosition){
		host.append(new Explosion(weaponSource, host, drawPosition));
	}
	@Override
	public boolean isActive() {
		return active;
	}
	
}
