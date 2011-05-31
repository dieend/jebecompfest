package jembalang.compfest.game;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;

public class Bug extends Sprite {
	private MovingFunction mf;
	private int HP;
	private int maxHP;
	private Paint paint;
	private static Random rand = new Random(System.currentTimeMillis());
	private int attack;
	private int base_score;
	private int type;
	private int buff;
	public static final int TYPE1 = 0;
	public static final int BIRD= 1;
	public static final int BUG1 = 2;
	public static int[] NORMAL ={0,1,2};
	public static int[] FREEZING ={3,4,5};
	public static int[] BURNING={6,7,8};
	public static int[] POISONED={9,10,11};
	private static int getRandom(int bounds){
		return (Math.abs(rand.nextInt())%bounds);
	}
	
	public int getType() {
		return type;
	}
	public static Bug Factory(int bugType, GameThread host){
		Bug tmp = new Bug(ImageCollection.is().getImage(ImageCollection.IMAGE_BUG, bugType), host);
		if (bugType == TYPE1){
			tmp.maxHP = 20;
			tmp.HP = tmp.maxHP;
			tmp.attack = 10;
			tmp.setFunction(null);
			tmp.base_score = 100;
		} else if (bugType == BIRD){
			tmp.maxHP = 1;
		} else if (bugType == BUG1){
			tmp.maxHP = 30;
			tmp.HP = tmp.maxHP;
			tmp.attack = 10;
			tmp.setFunction(null);
			tmp.base_score = 100;;
			tmp.setSequence(NORMAL);
		}
		tmp.visible = false;
		tmp.paint = new Paint();
		tmp.type = bugType;
		return tmp;
	}
	private Bug(Bitmap[] images, GameThread host){
		super(images);
		this.host = host;
	}

	public void setFunction(MovingFunction mf){
		if (mf == null){
			this.mf = new MovingFunction(this, MovingFunction.getEasy(),host.getViewWidth(),host.getViewHeight());
		} else {
			this.mf = mf;
		}
	}
	public void update(float time){
		float dx = mf.getdx(time);
		float dy = mf.getdy(time);
		setRotate(Math.atan2(dy, dx)* 180/Math.PI-90);
		if (buff == Weapon.BUFF_FREEZE&& tintTime > 0){
			dx *= 0.2;
			dy *= 0.2;
		}
		move(dx, dy);
		nextFrame();
		//tes
	}
	@Override
	public void draw(Canvas canvas) {
		if (visible){
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
					super.paint = null;
					setSequence(NORMAL);
				}
			}
			canvas.drawBitmap(b,(float)x,(float)y,super.paint);
			b.recycle();
			paint.setColor(Color.RED);
			canvas.drawRect(x, y, x+30, y+5, paint);
			paint.setColor(Color.GREEN);
			canvas.drawRect(x, y, x+(HP*30/maxHP), y+5, paint);
		}
	}
	
	public int hit(Weapon weapon,boolean[] hitted){
		int[] score = new int[1];
		score[0] = 0;
		hitted[0] = false;
		if (RectF.intersects(weapon.getArea(),getRectangle())){
			hitted[0] = true;
			score[0] = base_score;
			int d=weapon.getDamage(getRectangle(),score);
			if (d>0){
				HP-=d;
				if (weapon.buffType() == Weapon.BUFF_FREEZE){
					setTint(FREEZING,weapon.tintTime());
				}
			}
			buff = weapon.buffType();
		}
		return score[0];
	}
	public boolean isAlive(){
		return (HP>0);
	}
	public int damage() {
		return attack;
	}
	public void setTint(int[] sequence,int tintTime){
		tinted = true;
		this.tintTime = tintTime;
		setSequence(sequence);
	}
}
