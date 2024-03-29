package jembalang.compfest.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

public class Layer implements DrawableObject{
	protected boolean visible;
	
	private Bitmap image;
	protected boolean tinted;
	protected float x;
	protected float y;
	protected int tintTime;
	protected Paint paint;
	protected Matrix matrixMirror;
	protected Matrix mat;
	protected boolean mirror;
	protected RectF rect;
	protected int refx;
	protected int refy;
	protected Bitmap b;
	protected float degree;
	protected GameThread host;
	protected boolean active;
	public Layer(Bitmap image){
		this.image = image;
		init();
	}
	public void setHost(GameThread host){
		this.host = host;
	}
	private void init() {
		this.tinted = false;
		this.mirror = false;
		mat = new Matrix();
		active = true;
		float[] mirrorY = 
		 {  -1, 0, 0, 
		  0, 1, 0,  
		  0, 0, 1    
		 };
		 matrixMirror = new Matrix();
		 matrixMirror.setValues(mirrorY);
		 if (this instanceof Sprite){
			 rect = new RectF();
		 } else {
			 rect = new RectF(getX(), getY(), getX()+getWidth(), getY()+getHeight());
		 }
		visible = true;
		refx = 0;
		refy = 0;
	}
	/**
	 * return sprite position x;
	 * @return x
	 */
	public float getX(){
		return x;
	}
	/**
	 * return sprite position y
	 * @return y;
	 */
	public float getY(){
		return y;
	}
	public int getWidth() {
		return image.getWidth();
	}
	public int getHeight(){
		return image.getHeight();
	}
	public void draw(Canvas canvas){
		if (visible){
			mat.setRotate(degree);
			if (mirror){
				mat.postConcat(matrixMirror);
			}
			b = Bitmap.createBitmap(image, 0, 0, getWidth(), getHeight(), mat,true);
			if (tinted || tintTime>0){
			
				if (tintTime == 0){
					tintTime -=1;
					tinted = false;
					paint = null;
				}
			}
			canvas.drawBitmap(b, x, y, paint);
		}
	}
	/**
	 * Check wether this sprite collide with Sprite s. Collision detected by bounds;
	 * @param s
	 * @return
	 */
	public boolean collideWith(Layer s){
		return RectF.intersects(this.getRectangle(), s.getRectangle());
	}
	public boolean collideWith(int x, int y){
		return getRectangle().contains(x, y);
	}
	public boolean collideWith(float x, float y){
		return getRectangle().contains((int)x, (int)y);
	}
	/**
	 * reference pixel
	 * @param x
	 * @param y
	 */
	public void defineReferencePixel(int x, int y){
		refx = x;
		refy = y;
	}
	/**
	 * return the bounds of the sprite
	 * @return rectangle bounding the sprite
	 */
	public RectF getRectangle(){
		rect.offsetTo(x, y);
		return rect;
	}
	/**
	 * set the sprite position at (x,y)
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	/**
	 * make the sprite move dx, dy
	 * @param dx
	 * @param dy
	 */
	public void move(int dx, int dy){
		x += dx;
		y += dy;
	}
	public void move(float dx, float dy){
		x += dx;
		y += dy;
	}
	public void die(){
		this.setVisible(false);
		active = false;
	}
	/**
	 * 
	 * @param color color
	 * @param tintTime how long the image will be tinted
	 */
//	public void setTint(int color,int tintTime){
//		tinted = true;
//		this.tintTime = tintTime;
//		paint = new Paint();
//		paint.setColor(color);
//		paint.setColorFilter(new LightingColorFilter(color, 1));
//	}
	/**
	 * untint the image
	 */
	public void unTint(){
		tinted = false;
	}
	public void setVisible(boolean v){
		this.visible = v;
	}
	public boolean isActive(){
		return active;
	}
	/**
	 * set whether the image should be mirrored or not
	 * @param mirrored
	 */
	public void setMirror(boolean mirrored){
		this.mirror = mirrored;
	}
	/**
	 * set the image return normally
	 */
	public void setNormal(){
		degree = 0;
		mirror = false;
	}
	
	/**
	 * rotate the image by degrees
	 * @param degrees
	 */
	public void setRotate(int degrees){
		degree = (float)degrees;
	}
	public void setRotate(double degrees){
		degree = (float)(degrees);
	}
	

}
