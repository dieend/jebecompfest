package jembalang.compfest.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
public class Sprite extends Layer{
	
	
	private int row;
	private int column;
	private int frameHeight;
	private int frameWidth;
	private int[] sequence;
	private int currentFrameIdx; 

	
	/**
	 * Sprite constructor. column is the number of sprite frame column in the image, so is the height
	 * The Bitmap image should have Bitmap.Config.ARGB_8888 if possible.
	 * @param image sprite roll image
	 * @param frameWidth one frame height
	 * @param frameHeight one frame width
	 */
	public Sprite(Bitmap image, int column, int row){
		super(image);
		this.column = column;
		this.row = row;
		init();
	}
	/**
	 * Sprite constructor, r is the resource from the context.getResource(), and id is the
	 * resource id. Column is the number of frame horizontally, row the number of frame vertically
	 * @param r
	 * @param id
	 * @param column
	 * @param row
	 */
	public Sprite(Resources r, int id, int column, int row){
		super(r,id);
		this.column = column;
		this.row = row;
		init();
	}
	
	/**
	 * draw the sprite to canvas
	 * @param canvas
	 */
	@Override
	public void draw(Canvas canvas){
		if (visible){
			int crow = (sequence[currentFrameIdx]/column) * frameHeight;
			int ccol = (sequence[currentFrameIdx]%column) * frameWidth;
			mat.set(matrix);
			mat.setRotate(degree);
			if (mirror){
				mat.postConcat(matrixMirror);
			}
			if (!tinted){
				b = Bitmap.createBitmap(image, ccol, crow, frameWidth, frameHeight, mat,true);
			} else {
				b = Bitmap.createBitmap(tintedImage, ccol, crow, frameWidth, frameHeight, mat,true);
			}
			canvas.drawBitmap(b,(float)x,(float)y,null);
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
		this.frameWidth = image.getWidth()/column;
		this.frameHeight = image.getHeight()/row;
		this.currentFrameIdx = 0;
		int offsetx = frameWidth/10;
		int offsety = frameHeight/10;
		rect = new Rect(x+offsetx, y+offsety, x+frameWidth-offsetx, y+frameHeight-offsety);
		sequence = new int[column*row];
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
	
	/**
	 * set the image used to draw frame
	 * @param image
	 */
	public void setImage(Bitmap image,int column, int row){
		this.image = image;
		this.column = column;
		this.row = row;
		this.frameWidth = image.getWidth()/frameWidth;
		this.frameHeight = image.getHeight()/frameHeight;
		matrix.reset();
	}
	public void setSequence(int[] seq){
		this.sequence = seq;
		currentFrameIdx = 0;
	}

}