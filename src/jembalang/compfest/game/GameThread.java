package jembalang.compfest.game;

import java.util.ArrayList;
import java.util.Random;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnKeyListener;
import android.widget.Button;

public class GameThread extends View implements Runnable, OnKeyListener {
	private ArrayList<Bug> bug;
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

	public GameThread(Context context, AttributeSet attr) {
		super(context, attr);
		fire = new RectF();
		paint = new Paint();
		paint.setColor(Color.RED);
		setOnKeyListener(this);
		// Calculate scale
		inscreen = 0;
		rand = new Random(System.currentTimeMillis());
		bug = new ArrayList<Bug>();
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

	public GameThread(Context context) {
		super(context);
		fire = new RectF();
		paint = new Paint();
		paint.setColor(Color.RED);
		setOnKeyListener(this);
		// Calculate scale
		inscreen = 0;
		rand = new Random(System.currentTimeMillis());
		bug = new ArrayList<Bug>();
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

	public void start() {
		active = true;
		ImageCollection.init(getResources());
		createbug();
		player_HP = 100;
		gameScore = 0;
		gameHit = 0;
		gameShot = 0;
		Weapon.init(this, Weapon.BURNER);
		time = 0;
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
		canvas.drawText("" + gameScore, 10, 10, paint);
		canvas.drawText("" + gameHit + "/" + gameShot, 10, 20, paint);
		paint.setAntiAlias(false);
		paint.setColor(Color.RED);
		canvas.drawRect(bar_left, bar_top, bar_right, bar_bottom, paint);
		paint.setColor(Color.GREEN);
		canvas.drawRect(bar_left, bar_top, bar_left
				+ (player_HP * bar_width / 100), bar_bottom, paint);
		layerManager.draw(canvas);
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
	}

	public void updatePhysics() {
		time += 1;
		if (inscreen == 0) {
			inscreen = rand.nextInt(Math.min(bug.size(), 4));
		}
		for (int i = 0; i < inscreen; i++) {
			if (i < bug.size() && bug.get(i) != null) {
				Bug b = bug.get(i);
				if (!b.visible) {
					b.visible = true;
					layerManager.append(b);
				}
				b.update(time);
				if (!b.isAlive()) {
					Explosion.makeExplosion(ImageCollection.is().getImage(
							ImageCollection.IMAGE_BUG_DIE, b.getType()),
							layerManager, b.getRectangle(), 12);
					bug.remove(i);
					b.die();
					b = null;
					inscreen--;
				} else {
					if (b.getY() > getViewHeight()) {
						player_HP -= b.damage();
						bug.remove(i);
						b.die();
						b = null;
						inscreen--;
					}
				}
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (Weapon.take().noBullet())
				return true;
			Weapon.take().setFire(event.getRawX(), event.getRawY());
			// fire = Weapon.take().getArea();
			gameShot += 1;
			boolean[] hit = new boolean[1];
			for (Bug b : bug) {
				if (b != null && b.visible) {
					gameScore += b.hit(Weapon.take(), hit);
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
