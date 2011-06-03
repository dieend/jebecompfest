package jembalang.compfest.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
	public void reinit() {
		for (Map.Entry<Integer, Bitmap[]> entry : WeaponExplosionImage.entrySet()){
			WeaponExplosionLoader(entry.getKey());
		}
		for (Map.Entry<Integer, Bitmap[]> entry : BugImage.entrySet()){
			BugImageLoader(entry.getKey());
		}
		for (Map.Entry<Integer, Bitmap[]> entry : BugDieImage.entrySet()){
			BugDieImageLoader(entry.getKey());
		}
		for (Map.Entry<Integer, Bitmap[]> entry : WeaponImage.entrySet()){
			WeaponImageLoader(entry.getKey());
		}
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
			case IMAGE_WEAPON_EXPLOSION:
				if (WeaponExplosionImage == null){
					WeaponExplosionImage = new HashMap<Integer, Bitmap[]>();
				}
				tmp =WeaponExplosionImage.get(TYPE_OF_SOURCE);
				if (tmp==null) {
					tmp = WeaponExplosionLoader(TYPE_OF_SOURCE);
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
		} else if (bugType == Bug.BUG1){
			id = R.drawable.bug;
			column = 3;
			row = 4;
		}
		tmp= BitmapFactory.decodeResource(resources, id);
		image = splitImage(tmp,row,column);
		BugImage.put(bugType, image);
		return image;
	}
	
	private Bitmap[] BugDieImageLoader(int bugDieType){
		Bitmap[] image = null;
		int id = R.drawable.rock;
		int column=1, row=1;
		Bitmap tmp=null;
		if (bugDieType == Bug.TYPE1){
			id = R.drawable.rock;
			column = 1;
			row = 1;
		} else if (bugDieType == Bug.BIRD){
			id = R.drawable.bird;
			column = 3;
			row = 1;
		} else if (bugDieType == Bug.BUG1){
			id = R.drawable.bugdie;
			column = 3;
			row = 1;
		}
		tmp= BitmapFactory.decodeResource(resources, id);
		image = splitImage(tmp,row,column);
		BugDieImage.put(bugDieType, image);
		return image;
	}
	private Bitmap[] WeaponExplosionLoader(int weaponExplosionType){
		Bitmap[] image = null;
		int id = R.drawable.rock;
		int column=1, row=1;
		Bitmap tmp=null;
		if (weaponExplosionType == Weapon.GUN){
			id = R.drawable.explosion;
			column = 12;
			row = 1;
		} else if (weaponExplosionType == Weapon.SLOWER){
			id = R.drawable.freezer;
			column = 12;
			row = 1;
		} else if (weaponExplosionType == Weapon.BURNER){
			id = R.drawable.laser;
			column = 10;
			row = 1;
		} else if (weaponExplosionType == Weapon.RUDAL){
			id = R.drawable.target;
			column = 1;
			row = 1;
		}
		tmp= BitmapFactory.decodeResource(resources, id);
		image = splitImage(tmp,row,column); 
		WeaponExplosionImage.put(weaponExplosionType, image);
		return image;
	}
	private Bitmap[] WeaponImageLoader(int weaponType){
		Bitmap[] image = null;
		WeaponImage.put(weaponType, image);
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
		return images;
	}
}
