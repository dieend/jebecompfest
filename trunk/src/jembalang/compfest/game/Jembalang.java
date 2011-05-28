package jembalang.compfest.game;

import android.app.Activity;
import android.os.Bundle;

public class Jembalang extends Activity {
	private GameThread gameView;
	private SplashScreen splashScreen;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashScreen = new SplashScreen();
        gameView = new GameThread(this);
        setContentView(gameView);
        gameView.setFocusable(true);
        gameView.setFocusableInTouchMode(true);
        gameView.start();
    }
    
    
}