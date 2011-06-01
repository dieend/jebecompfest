package jembalang.compfest.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button newgame_btn = (Button)findViewById(R.id.newgame_btn);
		Button setting_btn = (Button)findViewById(R.id.setting_btn);
		newgame_btn.setOnClickListener(this);
		setting_btn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == findViewById(R.id.newgame_btn)){
			finish();
			Intent i = new Intent();
			i.setClassName("jembalang.compfest.game", "jembalang.compfest.game.Jembalang");
			startActivity(i);
		}
		if (v == findViewById(R.id.setting_btn)){
			// TODO main menu setting button
		}
	}
	
	
}
