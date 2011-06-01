package jembalang.compfest.game;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Jembalang extends Activity implements OnClickListener {
	private GameThread gameView;
	private Dialog dialog;

	/** Called when the activity is first created. */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			dialog.show();
			return true;
		default:

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		dialog = new Dialog(this);
		dialog.setContentView(R.layout.ingamemenu);
		dialog.setTitle("Game Paused");
		dialog.setCancelable(false);

		Button newgame_btn = (Button)findViewById(R.id.newgame_btn);
		Button resume_btn = (Button)dialog.findViewById(R.id.resume_btn);
		Button setting_btn = (Button)dialog.findViewById(R.id.setting_btn);
		Button exit_btn = (Button)dialog.findViewById(R.id.exit_btn);

		newgame_btn.setOnClickListener(this);
		resume_btn.setOnClickListener(this);
		setting_btn.setOnClickListener(this);
		exit_btn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		if (view == dialog.findViewById(R.id.exit_btn)) {
			finish();
		}
		if (view == dialog.findViewById(R.id.resume_btn)) {
			dialog.cancel();
		}
		if (view == dialog.findViewById(R.id.setting_btn)) {
			Toast.makeText(view.getContext(), "Setting Button clicked",
					Toast.LENGTH_LONG).show();
		}
		if (view == findViewById(R.id.newgame_btn)){
			setContentView(R.layout.cobagabung);
			gameView = (GameThread) Jembalang.this
					.findViewById(R.id.game_view);
			gameView.setFocusable(true);
			gameView.setFocusableInTouchMode(true);
			gameView.start();

		}
	}

}