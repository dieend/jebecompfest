package jembalang.compfest.game;

import java.util.HashMap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageCollection {
	public static final int IMAGE_WEAPON = 0;
	public static final int IMAGE_BUG = 1;
	public static final int IMAGE_WEAPON_EXPLOSION= 2;
	public static final int IMAGE_BUG_DIE= 3;
	private HashMap<Integer, Bitmap[]> WeaponImage;
	private HashMap<Integer, Bitmap[]> BugImage;
	private HashMap<Integer, Bitmap[]> WeaponExplosionImage;
	private HashMap<Integer, Bitmap[]> BugDieImage;
	private static ImageCollection imageCollection;
	private static Resources resources;
	public static void init(Resources r){
		resources = r;
		imageCollection = new ImageCollection();
	} 
	public static ImageCollection is(){
		return imageCollection;
	}
	public Bitmap[] getImage(int SOURCE_TYPE, int TYPE_OF_SOURCE){
		Bitmap[] tmp = null;
		switch (SOURCE_TYPE){
			case IMAGE_BUG:
				if (BugImage == null){
					BugImage = new HashMap<Integer, Bitmap[]>();
				}
				tmp= BugImage.get(TYPE_OF_SOURCE);
				if (tmp == null){
					tmp = BugImageLoader(TYPE_OF_SOURCE);
				}
				break;
			case IMAGE_BUG_DIE:
				if (BugDieImage == null){
					BugDieImage = new HashMap<Integer, Bitmap[]>();
				}
				tmp = BugDieImage.get(TYPE_OF_SOURCE);
				if (tmp == null){
					tmp = BugDieImageLoader(TYPE_OF_SOURCE);
				}
				break;
				default:
					break;
		}
		return tmp;
	}
	private Bitmap[] BugImageLoader(int bugType){
		Bitmap[] image = null;
		int id = R.drawable.rock;
		int column=1, row=1;
		Bitmap tmp=null;
		if (bugType == Bug.TYPE1){
			id = R.drawable.rock;
			column = 1;
			row = 1;
		} else if (bugType == Bug.BIRD){
			id = R.drawable.bird;
			column = 3;
			row = 1;
		}
		tmp= BitmapFactory.decodeResource(resources, id);
		image = splitImage(tmp,row,column);
		BugImage.put(bugType, image);
		return image;
	}
	
	private Bitmap[] BugDieImageLoader(int bugDieType){
		Bitmap[] image = null;
		BugDieImage.put(bugDieType, image);
		return image;
	}
	private Bitmap[] WeaponImageLoader(int weaponType){
		Bitmap[] image = null;
		WeaponImage.put(weaponType, image);
		return image;
	}
	private Bitmap[] WeaponExplosionLoader(int weaponExplosionType){
		Bitmap[] image = null;
		WeaponExplosionImage.put(weaponExplosionType, image);
		return image;
	}
	private Bitmap[] splitImage(Bitmap tmp, int row, int column){
		Bitmap[] images = null;
		int height=tmp.getHeight()/row;
		int width = tmp.getWidth()/column;
		images = new Bitmap[column*row];
		for (int i=0; i<column*row; i++){
			images[i] = Bitmap.createBitmap(tmp, (i%column)*width,(i/column)*height , width-1, height-1);
		}
//		tmp.recycle();
		return images;
	}
}
