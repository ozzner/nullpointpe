package net.wasitec.sieveanalisis;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

import net.wasitec.sieveanalisis.R;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Splash_Activity extends Activity {

	private ProgressBar progressBar;
	private int progressStatus = 0;
	private Handler handler = new Handler();

	private static final int TIEMPO_DEL_SPLASH = 2500;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		progress();
		TimerTask task = new TimerTask(){
            @Override
            public void run() {
            	
            	
                Intent mainIntent = new Intent().setClass(
                		Splash_Activity.this, InicioActivity.class);
                startActivity(mainIntent);
                
                
                finish();
        		overridePendingTransition(R.anim.anim_in_splash,
						R.anim.anim_out_splash);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_DEL_SPLASH);
				
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
		
	}
		
	public void progress (){
		
		new Thread(new Runnable() {
			public void run() {
				
				
				while (progressStatus < 100) {
					progressStatus += 10;
					
					handler.post(new Runnable() {
						public void run() {
							progressBar.setProgress(progressStatus);

						}
					});
					try {
						
						Thread.sleep(290);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
		
	}

