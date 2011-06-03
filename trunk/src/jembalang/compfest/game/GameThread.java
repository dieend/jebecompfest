package jembalang.compfest.game;

import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnKeyListener;
import android.widget.Toast;

public class GameThread extends View implements Runnable, OnKeyListener {
	private Vector<Bug> bug;
//	private Vector<Sprite> Coin;
	private boolean active;
	private int width;
	private int height;
	private int player_HP;
	private int gameScore;
	private int gameHit;
	private int gameShot;
	private int bar_width;
	private int bar_top;
	private int bar_bottom;
	private int bar_left;
	private int bar_right;
	private float time;
	private LayerManager layerManager;
	private Paint paint;
	private int inscreen;
	private Random rand;
	private RectF fire;
	private Rect lapis;
	private Jembalang host;
	private Toast warnBullet;
	private Toast warnActive;
	private Bitmap wave;
	private Rect waveRect;
	private Rect waveRectf;
	public static int PAUSED = 0;
	public static int WIN = 1;
	public static int LOSE = 2;
	
	public void setHost(Jembalang host){
		this.host = host;
	}
	public GameThread(Context context, AttributeSet attr) {
		super(context, attr);
		fire = new RectF();
		paint = new Paint();
		paint.setColor(Color.RED);
		setOnKeyListener(this);
		wave = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
		this.setBackgroundResource(R.drawable.bg_rumput);
		// Calculate scale
		inscreen = 0;
		rand = new Random(System.currentTimeMillis());
		bug = new Vector<Bug>();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		lapis = new Rect(0,height*90/100,width,height);
		height = height*90/100;
		bar_width = width / 4 - width / 10;
		bar_left = width - bar_width - width / 24;
		bar_top = height / 80;
		bar_right = bar_left + bar_width;
		bar_bottom = bar_top + bar_width / 6;
		layerManager = new LayerManager();
		warnBullet = Toast.makeText(getContext(), "RELOAD!",
				Toast.LENGTH_SHORT);
		warnActive = Toast.makeText(getContext(), "ACTIVATING WEAPON",
				Toast.LENGTH_SHORT); 
	}

	public GameThread(Context context) {
		super(context);
		fire = new RectF();
		paint = new Paint();
		paint.setColor(Color.RED);
		setOnKeyListener(this);
		// Calculate scale
		this.setBackgroundResource(R.drawable.bg_rumput);
		inscreen = 0;
		rand = new Random(System.currentTimeMillis());
		bug = new Vector<Bug>();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		bar_width = width / 4 - width / 10;
		bar_left = width - bar_width - width / 24;
		bar_top = height / 80;
		bar_right = bar_left + bar_width;
		bar_bottom = bar_top + bar_width / 6;
		layerManager = new LayerManager();
	}

	public void ready() {
		active = true;
		ImageCollection.init(getResources());
		createbug();
		player_HP = 100;
		gameScore = 0;
		gameHit = 0;
		gameShot = 0;
		waveRectf = new Rect(width-wave.getWidth()-5, height+5, width-5,height+wave.getHeight()+5);
		waveRect = new Rect(0,0,wave.getWidth()-1,wave.getHeight()-1);
		Weapon.init(this, Player.getInstance().getWeapons());
		time = 0;
	}
	public void start() {
		((Thread) new Thread(this)).start();
	}

	private void createbug() {
		for (int i = 0; i < 40; i++) {
			bug.add(i, Bug.Factory(Bug.BUG1, this));
		}
	}

	public int getViewWidth() {
		return width;
	}

	public int getViewHeight() {
		return height;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.WHITE);
		paint.setTextSize(10);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawText("SCORE   : " + gameScore, 10, 10, paint);
		canvas.drawText("ACCURACY: " + (gameShot==0?0:(gameHit*100/gameShot)), 10, 20, paint);
		canvas.drawText("MONEY   : " + Player.getInstance().getCoin(), 10, 30, paint);
		paint.setAntiAlias(false);
		paint.setColor(Color.RED);
		canvas.drawRect(bar_left, bar_top, bar_right, bar_bottom, paint);
		paint.setColor(Color.GREEN);
		canvas.drawRect(bar_left, bar_top, bar_left
				+ (Math.max(0,player_HP) * bar_width) / 100, bar_bottom, paint);
		layerManager.draw(canvas);
		paint.setColor(Color.GRAY);
		canvas.drawRect(lapis, paint);
		paint.setColor(Color.WHITE);
		paint.setTextSize(10);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawText("bullet :"+Weapon.take().bulletRemaining() + "/" +Weapon.take().maxBullet, 0, height+10, paint);
		if (Weapon.take().bulletRemaining() <= 0){
			canvas.drawText("Click the weapon image to reload with "+Weapon.take().price + "$",0, height+20, paint);
		}
		canvas.drawBitmap(wave, waveRect, waveRectf, null);
		// canvas.drawRect(fire, paint);
	}

	public void run() {
		while (active) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException ex) {
			}
			// pemanggilan fungsi ini otomatis ngebuat memanggil onDraw();
			updatePhysics();
			postInvalidate();
		}
		int status;
		if (player_HP<=0){
			host.endgame(LOSE,null);
		}else if (bug.size() <=0 ){
			Vector<Integer> medals = new Vector<Integer>();
			Integer[] k = new Integer[medals.size()];
			//TODO jika dapat medal, masukin ke medals;
			host.endgame(WIN, medals.toArray(k));
		}
		
	}
	
	public void updatePhysics() {
		time += 1;
		waveRect.bottom =(40-bug.size())*wave.getHeight()/40; 
		waveRectf.bottom = height+5+waveRect.height();
		if (player_HP <= 0 || bug.size() <= 0) {
			active = false;
			return;
		}
		if (inscreen == 0) {
			inscreen = rand.nextInt(Math.min(bug.size(), 4));
		}
		Iterator<Bug> it = bug.iterator();
		Log.d(MainMenu.APPNAME, "Bug size"+bug.size());
		synchronized (bug) {
			for (int i = 0; i < inscreen; i++) {
				if (it.hasNext()) {
					Log.d(MainMenu.APPNAME, "Bug number"+i);
					Bug b = it.next(); 
					if (!b.visible) {
						b.visible = true;
						layerManager.append(b);
					}
					b.update(time);
					if (!b.isAlive()) {
						Explosion.makeExplosion(ImageCollection.is().getImage(
								ImageCollection.IMAGE_BUG_DIE, b.getType()),
								layerManager, b.getRectangle(), 12);
						it.remove();
						Player.getInstance().addCoin(b.getMoney());
						b.die();
						b = null;
						inscreen--;
					} else {
						if (b.getY() > getViewHeight()) {
							player_HP -= b.damage();
							it.remove();
							b.die();
							b = null;
							inscreen--;
						}
					}
				}
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (Weapon.take().noBullet() ){
				warnBullet.cancel();
				warnBullet.setDuration(Toast.LENGTH_SHORT);
				warnBullet.show();
				return true;
			}
			if (Weapon.active){
				warnActive.cancel();
				warnActive.setDuration(Toast.LENGTH_SHORT);
				warnActive.show();
				return true;
			}
			Weapon.take().setFire(event.getRawX(), event.getRawY());
			// fire = Weapon.take().getArea();
			if (Weapon.take().type == Weapon.RUDAL){
				Timer t = new Timer();
				t.schedule(new TimerTask() {
	
					@Override
					public void run() {
						gameShot += 1;
						Weapon.active = false;
						Explosion.makeExplosion(ImageCollection.is().getImage(ImageCollection.IMAGE_WEAPON_EXPLOSION, Weapon.RUDAL2),layerManager,Weapon.take().getArea());
						boolean[] hit = new boolean[1];
						Iterator<Bug> it = bug.iterator();
						synchronized(bug){
							while (it.hasNext()){
								Bug b = it.next();
								if (b != null && b.visible) {
									gameScore += b.hit(Weapon.take(), hit);
									if (hit[0]) {
										gameHit += 1;
									}
								}
							}
						}
					}
				}, Weapon.take().delay());
			} else {
				gameShot += 1;
				Weapon.active = false;
				boolean[] hit = new boolean[1];
				Iterator<Bug> it = bug.iterator();
				synchronized (bug) {	
					while (it.hasNext()){
						Bug b = it.next();
						if (b != null && b.visible) {
							gameScore += b.hit(Weapon.take(), hit);
							if (hit[0]) {
								gameHit += 1;
							}
						}
					}
				}
			}
		}else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			
		}
		return true;
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		return false;
	}

	public LayerManager getLayerManager() {
		return layerManager;
	}

	public void pause() {
		active = false;
	}

	public void resume() {
		active = true;
		((Thread) new Thread(this)).start();
	}
}
