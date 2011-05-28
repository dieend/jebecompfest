package jembalang.compfest.game;

import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnKeyListener;

public class GameView extends View implements Runnable,OnKeyListener{
	private Sprite bird;
	private Bug bug;
	private boolean active;
	private int width;
	private int height;
	private int player_HP;
	private float time;
	private LayerManager layerManager;
	private Vector<Rect> explosion;
	private Vector<Integer> cnt;
	private Paint paint;
	private Bitmap bugImage;
	private Bitmap birdImage;
	public GameView(Context context) {
		super(context);
		explosion = new Vector<Rect>();
		cnt = new Vector<Integer>();
		paint = new Paint();
		paint.setColor(Color.RED);
        setOnKeyListener(this);
        //Calculate scale 
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        bugImage = BitmapFactory.decodeResource(getResources(), R.drawable.rock);
        birdImage = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        Display display = wm.getDefaultDisplay(); 
        width = display.getWidth(); 
        height = display.getHeight();
        layerManager = new LayerManager();
    }
	public void start(){
		
		active = true;
		createbird();
		createbug();
		player_HP = 100;
		Weapon.setView(this);
		time = 0;
		((Thread)new Thread(this)).start();

	}
	private void createbug() {
		bug = new Bug(bugImage, 1, 1,this);
		bug.setPosition(50, 50);
		bug.setFunction(MoveFunction.Factory(MoveFunction.LINEAR, 1));
		layerManager.append(bug);
	}
	public int getViewWidth() {
		return width;
	}
	public int getViewHeight(){
		return height;
	}
	private void createbird() {
        bird = new Sprite(birdImage,3,1);
        int xpos = 0;
        int ypos = 0;
        bird.setPosition(xpos, ypos);
        bird.defineReferencePixel(15, 15);
        // gambar dilapis warna merah, 128 itu transparansinya. maksimum 255 minimum 0
        bird.tint(128, 255, 0, 0);
        layerManager.append(bird);
    }
	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		width = getWidth();
		height = getHeight();
		for (int i=0; i<explosion.size(); i++){
        	if (cnt.get(i)<3) {
        		cnt.set(i, cnt.get(i)+1);
        		paint.setColor(Color.RED);
        		canvas.drawRect(explosion.get(i),paint);
        	} else {
        		cnt.remove(i);
        		explosion.remove(i);
        	}
        }
        layerManager.draw(canvas);
        //        canvas.drawBitmap(ms, 0, 0, null);
        paint.setColor(Color.RED);
        canvas.drawRect(width-60, 4, (width-60)+50, 14, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(width-60, 4, (width-60)+(player_HP*50/100), 14, paint);
    	}
    public void run() {	
    	while (active){
    		try{
    			Thread.sleep(20);
    		}catch(InterruptedException ex){
    		}
    		// pemanggilan fungsi ini otomatis ngebuat memanggil onDraw();
    		updatePhysics();
    		postInvalidate();

    	}
    }
    public void updatePhysics() {
    	time +=1;
    	if (bug!= null){
    		bug.update(time);
    		if (!bug.isAlive()){
    			bug.die();
    			bug = null;
    		} else {
	    		if (bug.getY() > getViewHeight()){
	    			player_HP -= bug.damage();
	    			layerManager.remove(bug);
	    			bug = null;
	    		}
	    	}
    	}
    }
	@Override
	public boolean onTouchEvent(MotionEvent event){
		Weapon.take().setFire(event.getRawX(), event.getRawY());
		explosion.add(new Rect(Weapon.take().getArea()));
		cnt.add(new Integer(0));
		if (bird.collideWith(event.getRawX(), event.getRawY())){
			bird.setVisible(false);
		}
		if (bug!= null){
			if (bug.collideWith(event.getRawX(), event.getRawY())){
				bug.hit(Weapon.take());
			}
		}
		return false;
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
			bird.move(-3, 0);
			bird.setMirror(true);
			bird.setRotate(0);
			bird.prevFrame();
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
			bird.move(3, 0);
			bird.setMirror(false);
			bird.setRotate(0);
			bird.nextFrame();
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
			bird.move(0, 3);
			bird.setRotate(90);
			bird.nextFrame();
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP){
			bird.move(0, -3);
			bird.setRotate(270);
			bird.nextFrame();
		}
		return false;
	}
	public LayerManager getLayerManager(){
		return layerManager;
	}
}
