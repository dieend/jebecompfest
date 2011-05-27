package jembalang.compfest.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;

public class Layer {
	protected boolean visible;
	
	protected Bitmap image;
	protected Bitmap tintB;
	protected boolean tinted;
	protected Bitmap tintedImage;
	protected int x;
	protected int y;
	protected Matrix matrix;
	protected Matrix matrixMirror;
	protected Matrix mat;
	protected boolean mirror;
	protected Rect rect;
	protected Canvas cTintB;
	protected Canvas cTintedImage;
	protected int refx;
	protected int refy;
	protected Bitmap b;
	protected float degree;
	protected GameView host;
	public Layer(Bitmap image){
		this.image = image;
		init();
	}
	public Layer(Resources r, int id){
		this.image = BitmapFactory.decodeResource(r, id);
		init();
	}
	public void setHost(GameView host){
		this.host = host;
	}
	private void init() {
		this.tinted = false;
		this.mirror = false;
		matrix = new Matrix();
		mat = new Matrix();
		float[] mirrorY = 
		 {  -1, 0, 0, 
		  0, 1, 0,  
		  0, 0, 1    
		 };
		 matrixMirror = new Matrix();
		 matrixMirror.setValues(mirrorY);
		 tintedImage = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
		tintB = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
		cTintB = new Canvas(tintB);
		cTintedImage = new Canvas(tintedImage);
		rect = new Rect(getX(), getY(), getX()+getWidth(), getY()+getHeight());
		visible = true;
		refx = 0;
		refy = 0;
	}
	/**
	 * return sprite position x;
	 * @return x
	 */
	public int getX(){
		return x;
	}
	/**
	 * return sprite position y
	 * @return y;
	 */
	public int getY(){
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
			mat.set(matrix);
			if (mirror){
				mat.postConcat(matrixMirror);
			}
			mat.setRotate(degree);
			if (!tinted){
				b = Bitmap.createBitmap(image, 0, 0, getWidth(), getHeight(), mat,true);
			} else {
				b = Bitmap.createBitmap(tintedImage, 0, 0, getWidth(), getHeight(), mat,true);
			}
			canvas.drawBitmap(b, x, y, null);
		}
	}
	/**
	 * Check wether this sprite collide with Sprite s. Collision detected by bounds;
	 * @param s
	 * @return
	 */
	public boolean collideWith(Layer s){
		return Rect.intersects(this.getRectangle(), s.getRectangle());
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
	public Rect getRectangle(){
		return new Rect(x, y, x+getWidth(), y+getHeight());
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
	/**
	 * tint the sprite with color a,r,g,b
	 * @param a
	 * @param r
	 * @param g
	 * @param b
	 */
	public void tint(int a, int r, int g, int b){
		tinted = true;
		cTintB.drawARGB(a, r, g, b);
		cTintedImage.drawBitmap(image,0,0, null);
		int pixel;
		for (int i=0; i<image.getHeight(); i++){
        	for (int j=0; j<image.getWidth(); j++){
        		pixel = image.getPixel(j, i);
        		if (Color.alpha(pixel) != 0) {
        			cTintedImage.drawBitmap(tintB, j,i, null);
        		}
        	}
        }
	}
	/**
	 * untint the image
	 */
	public void unTint(){
		tinted = false;
	}
	public void setVisible(boolean v){
		this.visible = v;
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
		matrix.reset();
	}
	
	/**
	 * rotate the image by degrees
	 * @param degrees
	 */
	public void setRotate(int degrees){
		degree = (float)degrees;
	}
	
	/**
	 * manipulate image by the matrix
	 * @param matrix
	 */
	public void setTransformMatrix(Matrix matrix){
		this.matrix = matrix;
	}
	
}
