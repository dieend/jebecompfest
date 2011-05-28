package jembalang.compfest.game;

import android.graphics.Canvas;

public interface DrawableObject {
	public void draw(Canvas canvas);
	public boolean isActive();
}
