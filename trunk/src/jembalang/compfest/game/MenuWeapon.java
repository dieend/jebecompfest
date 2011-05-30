package jembalang.compfest.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MenuWeapon extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		final ImageView weaponimage = (ImageView)findViewById(R.id.img_ak47);
		
		ImageView larrow = (ImageView)findViewById(R.id.img_larrow);
		larrow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				weaponimage.setImageResource(R.drawable.sword);
			}
		});
		
		ImageView rarrow = (ImageView)findViewById(R.id.img_rarrow);
		rarrow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				weaponimage.setImageResource(R.drawable.ak47);
			}
		});
	}
}