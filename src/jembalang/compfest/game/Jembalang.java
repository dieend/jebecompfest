package jembalang.compfest.game;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Jembalang extends Activity implements OnClickListener {
	private GameThread gameView;
	private Dialog dialog;
	private Dialog windialog;
	private Dialog losedialog;

	/** Called when the activity is first created. */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (gameView != null) {
				gameView.pause();
				dialog.show();
				return true;
			}
		default:
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameplay);
		
		gameView = (GameThread) Jembalang.this.findViewById(R.id.game_view);
		gameView.setFocusable(true);
		gameView.setFocusableInTouchMode(true);
		gameView.setHost(this);
		gameView.start();

		dialog = new Dialog(this);
		dialog.setContentView(R.layout.ingamemenu);
		dialog.setTitle("Game Paused");
		dialog.setCancelable(false);

		windialog = new Dialog(this);
		windialog.setContentView(R.layout.windialog);
		windialog.setTitle("You Win");
		windialog.setCancelable(false);

		losedialog = new Dialog(this);
		losedialog.setContentView(R.layout.losedialog);
		losedialog.setTitle("You Lose");
		losedialog.setCancelable(false);

		Button backtolevelselectfromwin_btn = (Button) windialog
				.findViewById(R.id.button1);
		Button backtolevelselectfromlose_btn = (Button) losedialog
				.findViewById(R.id.button1);
		Button resume_btn = (Button) dialog.findViewById(R.id.resume_btn);
		Button setting_btn = (Button) dialog.findViewById(R.id.setting_btn);
		Button backtomainmenu_btn = (Button) dialog
				.findViewById(R.id.backtomainmenu_btn);
		Button exit_btn = (Button) dialog.findViewById(R.id.exit_btn);
		ImageView nextweapon_btn = (ImageView) findViewById(R.id.img_larrow);
		ImageView prevweapon_btn = (ImageView) findViewById(R.id.img_rarrow);
		ImageView weapon_img = (ImageView) findViewById(R.id.img_weapon);

		backtolevelselectfromlose_btn.setOnClickListener(this);
		backtolevelselectfromwin_btn.setOnClickListener(this);
		resume_btn.setOnClickListener(this);
		setting_btn.setOnClickListener(this);
		backtomainmenu_btn.setOnClickListener(this);
		exit_btn.setOnClickListener(this);
		nextweapon_btn.setOnClickListener(this);
		prevweapon_btn.setOnClickListener(this);


	}

	@Override
	public void onResume() {
		super.onResume();
		// if (ImageCollection.is() != null)
		// ImageCollection.is().reinit();
	}

	@Override
	public void onClick(View view) {
		if (view == dialog.findViewById(R.id.exit_btn)) {
			finish();
			System.exit(0);
		}
		if (view == dialog.findViewById(R.id.resume_btn)) {
			dialog.cancel();
			if (gameView != null) {
				gameView.resume();
			}
		}
		if (view == dialog.findViewById(R.id.setting_btn)) {
			Toast.makeText(view.getContext(), "Setting Button clicked",
					Toast.LENGTH_LONG).show();
		}
		if (view == dialog.findViewById(R.id.backtomainmenu_btn)) {
			finish();
			gameView = null;
			Intent i = new Intent();
			i.setClassName("jembalang.compfest.game",
					"jembalang.compfest.game.MainMenu");
			startActivity(i);
		}
		if (view == findViewById(R.id.img_larrow)) {
			Weapon.prevWeapon();
		}
		if (view == findViewById(R.id.img_rarrow)) {
			Weapon.nextWeapon();
		}
		if (view == windialog.findViewById(R.id.button1)
				|| view == losedialog.findViewById(R.id.button1)) {
			finish();
			Intent i = new Intent();
			i.setClassName("jembalang.compfest.game",
					"jembalang.compfest.game.LevelSelectAct");
			startActivity(i);
		}
	}

	public void endgame(int status, Integer[] medals) {
		if (status != GameThread.PAUSED) {
			if (status == GameThread.LOSE) {
				losedialog.show();
			} else if (status == GameThread.WIN) {
				windialog.show();
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Weapon.destroy();
	}

}