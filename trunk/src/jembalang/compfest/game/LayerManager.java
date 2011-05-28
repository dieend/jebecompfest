package jembalang.compfest.game;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Canvas;

public class LayerManager {
	ArrayList<DrawableObject> layers;
	public LayerManager(){
		layers = new ArrayList<DrawableObject>();
	}
	public synchronized void append(DrawableObject layer){
		layers.add(layer);
	}
	public synchronized void draw(Canvas canvas){
		Iterator<DrawableObject> it = layers.iterator();
		DrawableObject d;
		while(it.hasNext()){
			d = it.next();
			d.draw(canvas);
			if (!d.isActive()){
				it.remove();
			}
		}
	}
}
