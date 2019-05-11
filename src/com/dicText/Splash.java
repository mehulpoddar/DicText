package com.dicText;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Splash extends Activity {
    /** Called when the activity is first created. */
	MediaPlayer ourSong;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ourSong=MediaPlayer.create(Splash.this,R.raw.start);
        ourSong.start();
        Thread timer= new Thread()
        {
        	public void run()
        	{
        		try{
        			sleep(16000);
        		}
        		catch(InterruptedException e)
        		{
        			e.printStackTrace();
        		}
        		finally{
        			Intent i=new Intent(Splash.this,DicTextActivity.class);
        			startActivity(i);
        		}
        	}
        };
        timer.start();
       }
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ourSong.release();
		finish();
	}
   }