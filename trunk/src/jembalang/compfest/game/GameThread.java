package jembalang.compfest.game;

import java.util.ArrayList;

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
	private ArrayList<Bug> bug;
	private boolean active;
	private int width;
	private int height;
	private int player_HP;
	private int gameScore;
	private int gameHit;
	private int gameShot;
	private float time;
	private LayerManager layerManager;
	private Paint paint;
	private int inscreen;
	
	public GameThread(Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(Color.RED);
        setOnKeyListener(this);
        //Calculate scale
        bug = new ArrayList<Bug>();
        inscreen = 1;
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
		for (int i=0; i<20; i++){
			bug.add(i, Bug.Factory(Bug.BUG1, this));
		}
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
		paint.setTextSize(10);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawText(""+gameScore, 10, 10, paint);
        canvas.drawText(""+gameHit+"/"+gameShot, 10, 20, paint);
        paint.setAntiAlias(false);
        paint.setColor(Color.RED);
        canvas.drawRect(width-60, 4, (width-60)+50, 14, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(width-60, 4, (width-60)+(player_HP*50/100), 14, paint);
        layerManager.draw(canvas);
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
    	for (int i=0; i<inscreen; i++){
	    	if (i<bug.size() && bug.get(i)!= null){
	    		Bug b = bug.get(i);
	    		if (!b.visible){
	    			b.visible = true;
	    			layerManager.append(b);
	    		}
	    		b.update(time);
	    		if (!b.isAlive()){
	    			Explosion.makeExplosion(ImageCollection.is().getImage(ImageCollection.IMAGE_BUG_DIE, b.getType())
	    									,layerManager, b.getRectangle(),10);
	    			bug.remove(i);
	    			b.die();
	    			b = null;
	    		} else {
		    		if (b.getY() > getViewHeight()){
		    			player_HP -= b.damage();
		    			bug.remove(i);
		    			b.die();
		    			b = null;
		    		}
		    	}
	    	}
    	}
    }
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN){
			Weapon.take().setFire(event.getRawX(), event.getRawY());
			gameShot += 1;
			boolean[] hit = new boolean[1];
			for (Bug b:bug){
				if (b!= null && b.visible){
					gameScore += b.hit(Weapon.take(),hit);
					if (hit[0]) {
						gameHit += 1;
					}
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
