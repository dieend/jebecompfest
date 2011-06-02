package jembalang.compfest.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LevelOverAct extends Activity implements OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.levelover);

		Button repeatlevel_btn = (Button) findViewById(R.id.repeatlevel_btn);
		Button mainmenu_btn = (Button) findViewById(R.id.mainmenu_btn);
		repeatlevel_btn.setOnClickListener(this);
		mainmenu_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == findViewById(R.id.repeatlevel_btn)) {
			finish();
			Intent i = new Intent();
			i.setClassName("jembalang.compfest.game",
					"jembalang.compfest.game.Jembalang");
			startActivity(i);
		}
		if (v == findViewById(R.id.mainmenu_btn)) {
			finish();
			Intent i = new Intent();
			i.setClassName("jembalang.compfest.game",
					"jembalang.compfest.game.MainMenu");
			startActivity(i);
		}
	}
}
