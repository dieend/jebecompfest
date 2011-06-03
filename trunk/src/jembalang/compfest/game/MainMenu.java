package jembalang.compfest.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener {
	public static final String APPNAME= "BUG_MASSACRE";
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Player.init();
//		try {
//			FileInputStream fis = openFileInput(APPNAME);
//			Player.init(fis);
//		} catch (FileNotFoundException ex){
//			Player.init();
//		}
		SoundManager.getInstance();
        SoundManager.initSounds(this);
        SoundManager.loadSounds();
		
		Button newgame_btn = (Button)findViewById(R.id.newgame_btn);
		Button setting_btn = (Button)findViewById(R.id.setting_btn);
		newgame_btn.setOnClickListener(this);
		setting_btn.setOnClickListener(this);
	}
	@Override
	public void onResume(){
		super.onResume();
	}
	@Override
	public void onRestart(){
		super.onRestart();
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		SoundManager.cleanup();
		try {
			FileOutputStream fos = openFileOutput(APPNAME, MODE_PRIVATE);
			Player.save(fos);
		} catch(FileNotFoundException ex){
			Log.e(APPNAME, "Failed to save",ex);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == findViewById(R.id.newgame_btn)){
			finish();
			Intent i = new Intent();
			i.setClassName("jembalang.compfest.game", "jembalang.compfest.game.LevelSelectAct");
			startActivity(i);
		}
		if (v == findViewById(R.id.setting_btn)){
			// TODO main menu setting button
		}
	}

}
