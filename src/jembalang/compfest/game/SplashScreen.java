package jembalang.compfest.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
 
public class SplashScreen extends Activity {
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.splash);
      
      LinearLayout mLayout = (LinearLayout)findViewById(R.id.mLayout);
      mLayout.setBackgroundColor(0xffffffff);
   
      Thread splashThread = new Thread() {
         @Override
         public void run() {
            try {
               int waited = 0;
               while (waited < 5000) {
                  sleep(100);
                  waited += 100;
               }
            } catch (InterruptedException e) {
               // do nothing
            } finally {
               finish();
               Intent i = new Intent();
               i.setClassName("jembalang.compfest.game",
                              "jembalang.compfest.game.MainMenu");
               startActivity(i);
            }
         }
      };
      splashThread.start();
   }
}