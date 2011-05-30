package jembalang.compfest.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
public class Sprite extends Layer{
	
	
	private int frameHeight;
	private int frameWidth;
	private int[] sequence;
	private int currentFrameIdx;
	private Bitmap[] images;
	/**
	 * Sprite constructor. column is the number of sprite frame column in the image, so is the height
	 * The Bitmap image should have Bitmap.Config.ARGB_8888 if possible.
	 * @param image sprite roll image
	 * @param frameWidth one frame height
	 * @param frameHeight one frame width
	 */
	public Sprite(Bitmap[] images){
		super(null);
		this.images = images;
		init();
	}
	
	
	/**
	 * draw the sprite to canvas
	 * @param canvas
	 */
	@Override
	public void draw(Canvas canvas){
		if (visible){
//			int crow = (sequence[currentFrameIdx]/column) * frameHeight;
//			int ccol = (sequence[currentFrameIdx]%column) * frameWidth;
			mat.setRotate(degree);
			if (mirror){
				mat.postConcat(matrixMirror);
			}
			b = Bitmap.createBitmap(images[sequence[currentFrameIdx]], 0, 0, getWidth(), getHeight(), mat,true);
			if (tinted || tintTime >0){
				tintTime-=1;
				if (tintTime == 0){
					tintTime -=1;
					tinted = false;
					paint = null;
				}
			}
			canvas.drawBitmap(b,(float)x,(float)y,paint);
			b.recycle();
		}
	}
	/**
	 * return a sprite height
	 * @return frameHeight
	 */
	@Override
	public int getHeight(){
		return frameHeight;
	}
	
	/**
	 * return a sprite width
	 * @return frameWidth
	 */
	@Override
	public int getWidth(){
		return frameWidth; 
	}
	private void init() {
		this.frameWidth = images[0].getWidth();
		this.frameHeight = images[0].getHeight();
		this.currentFrameIdx = 0;
		int offsetx = frameWidth/10;
		int offsety = frameHeight/10;
		rect = new Rect(x, y, x+frameWidth, y+frameHeight);
		refx = rect.centerX();
		refy = rect.centerY();
		sequence = new int[images.length];
		for (int i = 0; i<sequence.length; i++){
			sequence[i] = i;
		}
	}
	
	/**
	 * go to next frame
	 */
	public void nextFrame(){
		currentFrameIdx += 1;
		if (currentFrameIdx >= sequence.length){
			currentFrameIdx = 0;
		}
	}
	/**
	 * go to previous frame
	 */
	public void prevFrame(){
		currentFrameIdx -= 1;
		if (currentFrameIdx < 0){
			currentFrameIdx = sequence.length-1;
		}
	}
	/**
	 * set current frame
	 * @param currentFrame
	 */
	public void setCurrentFrameIdx(int currentFrameIdx){
		this.currentFrameIdx = currentFrameIdx;
	}
	

	public void setSequence(int[] seq){
		this.sequence = seq;
		currentFrameIdx = 0;
	}

}
