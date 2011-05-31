package jembalang.compfest.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Jembalang extends Activity {
	private GameThread gameView;
	private Button button;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button = (Button)findViewById(R.id.newgame);
        button.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
		        gameView = new GameThread(Jembalang.this);
		        setContentView(gameView);
		        gameView.setFocusable(true);
		        gameView.setFocusableInTouchMode(true);
		        gameView.start();
			}
		});
    }
    
    
}