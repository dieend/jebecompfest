package jembalang.compfest.game;

import java.util.Iterator;
import java.util.Vector;

import android.graphics.Canvas;

public class LayerManager {
	private Vector<DrawableObject> layers;
	public LayerManager(){
		layers = new Vector<DrawableObject>();
	}
	
	public synchronized void append(DrawableObject layer){
	    
	        synchronized(layers){
	            layers.add(layer);
	        }
	    
	}
	public synchronized void draw(Canvas canvas){
	    
	        synchronized (layers) {
	            Iterator<DrawableObject> it = layers.iterator();
	            DrawableObject d;
	            while(it.hasNext()){
	                d = it.next();
	                if (d==null){}
	                else if (!d.isActive()){
	                    it.remove();
	                }else {
	                    d.draw(canvas);
	                }
	            }
	        }
	    
	}

}

