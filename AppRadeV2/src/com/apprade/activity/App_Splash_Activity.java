package com.apprade.activity;

import java.util.Timer;
import java.util.TimerTask;
import com.apprade.R;
import com.apprade.helper.Helper_SharedPreferences;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class App_Splash_Activity extends Activity {

	private static final int TIEMPO_DEL_SPLASH = 1900;
	private Helper_SharedPreferences pref;
	private String sStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		TimerTask task = new TimerTask() {
		  
			@Override
			public void run() {
				
				 pref = new Helper_SharedPreferences();
				 sStatus = pref.checkLogin(getApplicationContext());
			 
				switch (sStatus) {
					
				case "intro":
					Intent intro = new Intent().setClass(
							App_Splash_Activity.this, Intro_Activity.class);
					startActivity(intro);
					break;
					
				case "login":
					Intent login = new Intent().setClass(
							App_Splash_Activity.this, Usuario_Login_Activity.class);
					startActivity(login);
					break;
					
				case "mapa":
					Intent mapa = new Intent().setClass(
							App_Splash_Activity.this, App_GPSMapa_Activity.class);
					startActivity(mapa);
					break;
					
				default:
					break;
				}
			
				overridePendingTransition(R.anim.anim_in_splash,
						R.anim.anim_out_splash);
				finish();
			}
		};

		// Simulate a long loading process on application startup.
		Timer timer = new Timer();
		timer.schedule(task, TIEMPO_DEL_SPLASH);

	}

}
