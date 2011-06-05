package jembalang.compfest.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LevelSelectAct extends Activity implements OnClickListener {
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			finish();
			Intent i = new Intent();
			i.setClassName("jembalang.compfest.game",
					"jembalang.compfest.game.MainMenu");
			startActivity(i);
		default:
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.levelselect);
		
		Button play_btn = (Button) findViewById(R.id.play_btn);
		play_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == findViewById(R.id.play_btn)) {
			finish();
			Intent i = new Intent();
			i.setClassName("jembalang.compfest.game",
					"jembalang.compfest.game.Jembalang");
			startActivity(i);
		}
	}
		
}
