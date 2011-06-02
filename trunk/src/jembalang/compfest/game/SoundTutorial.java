package jembalang.compfest.game;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SoundTutorial extends Activity {
    private Button mChangeActivityButton;
	private Button mPlaySound1Button;
	private Button mPlaySound2Button;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Create, Initialise and then load the Sound manager
        SoundManager.getInstance();
        SoundManager.initSounds(this);
        SoundManager.loadSounds();
        
        
        mChangeActivityButton = (Button) this.findViewById(R.id.ChangeActivity);
        mChangeActivityButton.setOnClickListener(new OnClickListener() {
        	  public void onClick(View v) {
        		             		
        		  Intent StartGameIntent = new Intent(SoundTutorial.this,Activity2.class);
                  startActivity(StartGameIntent);
                  
                  
        	  }
        	});
        
        mPlaySound1Button = (Button) this.findViewById(R.id.PlaySound1);
        mPlaySound1Button.setOnClickListener(new OnClickListener() {
        	  public void onClick(View v) {
        		             		
        		  SoundManager.playSound(1, 1);
        	  }
        	});
        
        mPlaySound2Button = (Button) this.findViewById(R.id.PlaySound2);
        mPlaySound2Button.setOnClickListener(new OnClickListener() {
        	  public void onClick(View v) {
        		             		
        		  SoundManager.playSound(2, 1);      		  
        	  }
        	});
        
       
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	SoundManager.cleanup();
    }
}