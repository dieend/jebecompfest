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
	private Button setting_btn;
	private Button backtomainmenu_btn; 
	private Button exit_btn;
	private ImageView nextweapon_btn;
	private ImageView prevweapon_btn;
	private ImageView weapon_img;
	/** Called when the activity is first created. */

	private Button resume_btn; 
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
		gameView = (GameThread) findViewById(R.id.game_view);
		gameView.setFocusable(true);
		gameView.setFocusableInTouchMode(true);
		gameView.setHost(this);
		gameView.ready();
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.ingamemenu);
		dialog.setTitle("Game Paused");
		dialog.setCancelable(false);
		
		resume_btn = (Button) dialog.findViewById(R.id.resume_btn);
		setting_btn = (Button) dialog
				.findViewById(R.id.setting_btn);
		backtomainmenu_btn = (Button) dialog
				.findViewById(R.id.backtomainmenu_btn);
		exit_btn = (Button) dialog.findViewById(R.id.exit_btn);
		nextweapon_btn = (ImageView) findViewById(R.id.img_larrow);
		prevweapon_btn = (ImageView) findViewById(R.id.img_rarrow);
		// TODO implements weapon image change
		weapon_img = (ImageView) findViewById(R.id.img_weapon);

		resume_btn.setOnClickListener(this);
		setting_btn.setOnClickListener(this);
		backtomainmenu_btn.setOnClickListener(this);
		exit_btn.setOnClickListener(this);
		nextweapon_btn.setOnClickListener(this);
		prevweapon_btn.setOnClickListener(this);
		weapon_img.setOnClickListener(this);
		gameView.start();
	}
	@Override
	public void onResume(){
		super.onResume();
//		if (ImageCollection.is() != null)
//			ImageCollection.is().reinit();
	}
	@Override
	public void onClick(View view) {
		if (view == exit_btn) {
			finish();
			System.exit(0);
		}
		if (view == resume_btn) {
			dialog.cancel();
			if (gameView != null) {
				gameView.resume();
			}
		}
		if (view == setting_btn) {
			Toast.makeText(view.getContext(), "Setting Button clicked",
					Toast.LENGTH_SHORT).show();
		}
		if (view == backtomainmenu_btn) {
			finish();
			gameView = null;
			Intent i = new Intent();
			i.setClassName("jembalang.compfest.game","jembalang.compfest.game.MainMenu");
			startActivity(i);
		}
		if (view == prevweapon_btn) {
			Weapon.prevWeapon();
			weapon_img.setImageBitmap(Weapon.take().getImage());
		}
		if (view == nextweapon_btn) {
			Weapon.nextWeapon();
			weapon_img.setImageBitmap(Weapon.take().getImage());
			
		}
		if (view == weapon_img){
			Weapon.take().reload();
		}
	}
	public void endgame(int status, Integer[] medals){
		if (status != GameThread.PAUSED){
			if (status == GameThread.LOSE){
				// TODO kalau menang nampilin apa
			}else if (status == GameThread.WIN){
				// TODO kalau kalah nampilin apa
			}
		}
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		Weapon.destroy();
	}
}