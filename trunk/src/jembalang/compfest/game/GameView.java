package jembalang.compfest.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
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
	private float time;
	Resources r;
	private LayerManager layerManager;
	public GameView(Context context) {
		super(context);
		r = context.getResources();
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
		createbird();
		createbug();
		time = 0;
		((Thread)new Thread(this)).start();

	}
	private void createbug() {
		bug = new Bug(r, R.drawable.rock, 1, 1,this);
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
        bird = new Sprite(r, R.drawable.bird,3,1);
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
        layerManager.draw(canvas);
//        canvas.drawBitmap(ms, 0, 0, null);
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
    	bug.update(time);
    }
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if (bird.collideWith(event.getRawX(), event.getRawY())){
			bird.setVisible(false);
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
	
}
