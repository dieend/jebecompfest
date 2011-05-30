package jembalang.compfest.game;

import android.content.Context;
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

public class GameThread extends View implements Runnable,OnKeyListener{
	private Bug bug;
	private boolean active;
	private int width;
	private int height;
	private int player_HP;
	private int gameScore;
	private int gameHit;
	private int gameShot;
	private float time;
	private Rect fire;
	private LayerManager layerManager;
	private Paint paint;

	public GameThread(Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(Color.RED);
		fire = new Rect();
        setOnKeyListener(this);
        //Calculate scale 
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay(); 
        width = display.getWidth(); 
        height = display.getHeight();
        layerManager = new LayerManager();
    }
	public void start(){
		
		active = true;
		ImageCollection.init(getResources());
		createbug();
		player_HP = 100;
		gameScore = 0;
		gameHit = 0;
		gameShot = 0;
		Weapon.init(this, Weapon.GUN);
		time = 0;
		((Thread)new Thread(this)).start();

	}
	private void createbug() {
		bug = Bug.Factory(Bug.TYPE1, this);
		layerManager.append(bug);
	}
	public int getViewWidth() {
		return width;
	}
	public int getViewHeight(){
		return height;
	}

	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.WHITE); 
		paint.setStyle(Paint.Style.FILL); 
		canvas.drawPaint(paint); 

		paint.setColor(Color.BLACK); 
		paint.setTextSize(10);
		paint.setAntiAlias(true);
		canvas.drawText(""+gameScore, 10, 10, paint);
        canvas.drawText(""+gameHit+"/"+gameShot, 10, 20, paint);
        paint.setAntiAlias(false);
        layerManager.draw(canvas);
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
//    			Explosion.makeExplosion(Bug.DieImage.get(bug.getType()), bug.getRectangle());
    			bug = null;
    		} else {
	    		if (bug.getY() > getViewHeight()){
	    			player_HP -= bug.damage();
	    			bug.die();
	    			bug = null;
	    		}
	    	}
    	}
    }
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN){
			Weapon.take().setFire(event.getRawX(), event.getRawY());
			fire = Weapon.take().getArea();
			gameShot += 1;
			boolean[] hit = new boolean[1];
			if (bug!= null){
				gameScore += bug.hit(Weapon.take(),hit);
				if (hit[0]) {
					gameHit += 1;
				}
			}
		}
		return true;
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		return false;
	}
	public LayerManager getLayerManager(){
		return layerManager;
	}
}
