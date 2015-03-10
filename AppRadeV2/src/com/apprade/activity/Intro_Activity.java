/**
 * 
 */
package com.apprade.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apprade.R;
import com.apprade.adapter.Adapter_ViewPage;
import com.apprade.dao.DAO_Usuario;
import com.apprade.entity.Entity_Ranking;
import com.apprade.helper.Helper_SharedPreferences;
import com.apprade.helper.Helper_SubRoutines;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;
import com.viewpagerindicator.LinePageIndicator;

/**
 * @author renzo
 *
 */
public class Intro_Activity extends FragmentActivity implements
		ValidationListener {

	private Validator validator;
	private ViewPager vp_Intro;
	private LinePageIndicator pi_Intros;
	private ActionBar actionBar;
	@Required(order = 2, message = "Este campo es requerido.")
	@TextRule(order = 3, minLength = 3, message = "Ingrese min 3 caracteres.")
	private EditText password;
	@Required(order = 1, message = "Este campo es requerido.")
	private EditText email;
	private DAO_Usuario dao;
	public Entity_Ranking rank;
	private String sEmail = "", sPassword = "";
	private ProgressDialog proDialogo;
	private Helper_SubRoutines oRoutine;

	public Intro_Activity() {
		
		oRoutine  = new Helper_SubRoutines();
		rank = new Entity_Ranking();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dao = new DAO_Usuario(getApplicationContext());
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
		
		if (!oRoutine.isOnline(getApplicationContext())) 
			Toast.makeText(getApplicationContext(), "Necesita tener conexión a internet.", Toast.LENGTH_SHORT).show();
		
		
		/* PARTE 1 */
		setContentView(R.layout.activity_intro);
		vp_Intro = (ViewPager) findViewById(R.id.vp_intro);

		Adapter_ViewPage vpAdapter = new Adapter_ViewPage(
				getSupportFragmentManager());
		vp_Intro.setAdapter(vpAdapter);

		pi_Intros = (LinePageIndicator) findViewById(R.id.indicator);
		pi_Intros.setViewPager(vp_Intro);

		validator = new Validator(this);
		validator.setValidationListener(this);

	}

	protected void showDialogo() {
		proDialogo = new ProgressDialog(Intro_Activity.this);
		proDialogo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		proDialogo.setMessage("Conectando...");
		proDialogo.show();
	}

	protected void llamarMapa() {

		Intent mapa = new Intent(getApplicationContext(),
				App_GPSMapa_Activity.class);
		mapa.putExtra("user_id", dao.oUsuario.getUsuarioID());
		mapa.putExtra("user", dao.oUsuario.getNombre());
		startActivity(mapa);
		finish();

	}

	class TaskHttpMethodAsync extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			boolean bRequest = false;

		if (dao.loginUsuario(sEmail, sPassword, 1))
			bRequest = true;

			return bRequest;
		}

		@Override
		protected void onPreExecute() {

			showDialogo();

			proDialogo.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					TaskHttpMethodAsync.this.cancel(true);
				}
			});
			proDialogo.setProgress(0);
			// pDialog.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			proDialogo.dismiss();

			if (result) {
				llamarMapa();
				Helper_SharedPreferences oShared = new Helper_SharedPreferences();
//				oShared.getAllLoginDataStored(getApplicationContext());

				oShared.storeLogin(dao.oUsuario.getNombre(),
						dao.oUsuario.getEmail(), dao.oUsuario.getUsuarioID(),
						1, getApplicationContext());
			} else {
				Toast.makeText(
						getApplicationContext(),
						dao.oJsonStatus.getMessage() + ". "
								+ dao.oJsonStatus.getInfo() + ".",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onCancelled() {
		}

	}// End ClassAsync

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.registro_menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final View v;
		actionBar = getActionBar();

		switch (item.getItemId()) {

		case R.id.reg_login_action:

			actionBar.setSubtitle("Login");
			
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					Intro_Activity.this);
			
			LayoutInflater inflater = this.getLayoutInflater();
			v = inflater.inflate(R.layout.dialog_custom_login, null);
			alertDialog.setView(v);

			
			/* When positive (yes/ok) is clicked */
			alertDialog.setPositiveButton("¡Iniciar!",
					new DialogInterface.OnClickListener() {
				
						@Override
						public void onClick(DialogInterface dialog, int which) {

							email = (EditText) v.findViewById(R.id.txtEmail);
							password = (EditText) v
									.findViewById(R.id.txtPassword);
														
							validator.validate();
						}
					});

			/* When negative (No/cancel) button is clicked */
			alertDialog.setNegativeButton("Salir",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
//							dialog.cancel();
						}
					});

			alertDialog.show();

			break;
		case R.id.reg_about_action:
			actionBar.setSubtitle("About app");
			LoadInfo();
			break;

		default:
			break;
		}

		return true;
	}

	
	private void LoadInfo() {
		final View v;
		
		AlertDialog.Builder adInfo = new AlertDialog.Builder(Intro_Activity.this);
		
		LayoutInflater layInfo = this.getLayoutInflater();
		v = layInfo.inflate(R.layout.dialog_custom_about, null);
		adInfo.setView(v);
		
		adInfo.setNeutralButton("Okay",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		
		adInfo.show();
	}
	
	
	@Override
	public void onValidationSucceeded() {
		
		sEmail = email.getText().toString().trim();
		sPassword = password.getText().toString().trim();
		
		if (sEmail.isEmpty()||sPassword.isEmpty()) {
			Toast.makeText(getApplicationContext(), "Debe completar todos los campos!", Toast.LENGTH_SHORT);
		}else{
			
			if (oRoutine.isOnline(getApplicationContext())) {
				new TaskHttpMethodAsync().execute();
			} else {
				Toast.makeText(getApplicationContext(), "Necesita tener conexión a internet.", Toast.LENGTH_SHORT).show();
			} 
		}
			
	}

	@Override
	public void onValidationFailed(View failedView, Rule<?> failedRule) {
		
		String message = failedRule.getFailureMessage();
		
		if (failedView instanceof EditText) {
			failedView.requestFocus();
			((EditText) failedView).setError(message);
		} else {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		}
	}

}
