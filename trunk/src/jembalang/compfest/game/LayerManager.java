package jembalang.compfest.game;

import java.util.ArrayList;

import android.graphics.Canvas;

public class LayerManager {
	ArrayList<Layer> layers;
	public LayerManager(){
		layers = new ArrayList<Layer>();
	}
	public void append(Layer layer){
		layers.add(layer);
	}
	public void draw(Canvas canvas){
		for (Layer layer:layers){
			layer.draw(canvas);
		}
	}
	public void remove(Layer layer){
		layers.remove(layer);
	}
}
