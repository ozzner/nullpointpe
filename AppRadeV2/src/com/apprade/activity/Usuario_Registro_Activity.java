package com.apprade.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.apprade.R;
import com.apprade.adapter.Adapter_Dialog_Fragment;
import com.apprade.dao.DAO_Usuario;
import com.apprade.helper.Helper_SharedPreferences;
import com.apprade.helper.Helper_SubRoutines;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

public class Usuario_Registro_Activity extends FragmentActivity implements
		ValidationListener {

	private Validator validator;
	private Button btnSend;
	@Required(order = 1, message = "Ingrese nombres")
	@TextRule(order = 2, minLength = 2, trim = true)
	private EditText etNombres;
	@Required(order = 3, message = "Este campo es requerido.")
	@TextRule(order = 4, minLength = 3, trim = true)
	private EditText etCorreo;
	@Password(order = 5, message = "Debe ingresar un password")
	@TextRule(order = 6, minLength = 3, message = "Ingrese min 3 caracteres.", trim = true)
	private EditText etPassword;
	@ConfirmPassword(order = 7, message = "Las contraseñas no son iguales")
	private EditText etConfPassword;
	private RadioGroup rgSexo;
	private ImageButton ib;
	private ProgressDialog proDialog;
	private String sFecha = "2006-05-18", sNombre, sEmail, sPassword,
			 sSexo;
	private ActionBar actionBar;
	private DAO_Usuario dao;
	Adapter_Dialog_Fragment oDialFrag;
	private Helper_SubRoutines oRoutine;


	public Usuario_Registro_Activity() {
		super();
		oDialFrag = new Adapter_Dialog_Fragment();
		dao = new DAO_Usuario();
		oRoutine = new Helper_SubRoutines();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_intro_3);

		btnSend = (Button) findViewById(R.id.btn_enviar);
		etNombres = (EditText) findViewById(R.id.et_nombres);
		etPassword = (EditText) findViewById(R.id.et_password_reg);
		etConfPassword = (EditText) findViewById(R.id.et_pass_confirmar);
		etCorreo = (EditText) findViewById(R.id.et_correo);
		ib = (ImageButton) findViewById(R.id.imb_date);
		rgSexo = (RadioGroup) findViewById(R.id.rg_sexo);

		validator = new Validator(this);
		validator.setValidationListener(this);

		ib.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				oDialFrag.show(getSupportFragmentManager(), "MyDataPiker");
			}
		});

		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				validator.validate();
			}
		});

	}

	private boolean validarRegistro() {
		
		boolean esError = false;
		RadioButton selectRadio = (RadioButton) findViewById(rgSexo
				.getCheckedRadioButtonId());
		String sexo = selectRadio.getText().toString();
		sSexo = sexo;
		sFecha = oDialFrag.getsFecha();
		
		if (sFecha.equals("2006-05-18")) {
			oRoutine.showToast(getApplicationContext(),
					"Ingrese fecha nacimiento");
			esError = true;
		} else {
			if (sFecha.equals(oRoutine
					.getCurrentTime(Helper_SubRoutines.TAG_FORMAT_DATE_MM))) {
				esError = true;
				oRoutine.showToast(getApplicationContext(),
						"Ingrese fecha correcta");
			}
		}

		return esError;
	}

	class TaskHttpMethodAsync extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			boolean bRequest = false;

			if (dao.registarUsuario(sEmail, sSexo, sNombre, sPassword, sFecha))
				bRequest = true;

			return bRequest;
		}

		@Override
		protected void onPreExecute() {

			showDialogo();

			proDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					TaskHttpMethodAsync.this.cancel(true);
				}
			});
			proDialog.setProgress(0);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			proDialog.dismiss();

			
			if (result) {

				Helper_SharedPreferences oShaPre = new Helper_SharedPreferences();
				oShaPre.storeLogin(sNombre, sEmail,
						dao.oUsuario.getUsuarioID(), 1, getApplicationContext());

				Toast.makeText(
						getApplicationContext(),
						"Hola "
								+ sNombre
								+ ", ya puedes iniciar sesión con tus datos, presiona Login ",
						Toast.LENGTH_LONG).show();
				Intent i = new Intent(getApplicationContext(),
						Usuario_Login_Activity.class);
				i.putExtra("correo", sEmail);
				i.putExtra("password", sPassword);
				startActivity(i);
				finish();
			} else {
				Toast.makeText(
						getApplicationContext(),
						dao.oJsonStatus.getMessage() + ". "
								+ dao.oJsonStatus.getMessage(), Toast.LENGTH_LONG)
						.show();
			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.registro_menu, menu);

		return true;
	}

	public void showDialogo() {
		proDialog = new ProgressDialog(Usuario_Registro_Activity.this);
		proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		proDialog.setMessage("Enviando...");
		proDialog.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		actionBar = getActionBar();

		switch (item.getItemId()) {

		case R.id.reg_login_action:
			Intent login = new Intent(getApplicationContext(),
					Usuario_Login_Activity.class);
			startActivity(login);
			finish();
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

	@Override
	public void onValidationSucceeded() {

		if (!validarRegistro()) {
			sNombre = etNombres.getText().toString().trim();
			sEmail = etCorreo.getText().toString().trim();
			sPassword = etConfPassword.getText().toString().trim();

			if (oRoutine.isOnline(getApplicationContext()))
				new TaskHttpMethodAsync().execute();
			else
				Toast.makeText(getApplicationContext(),
						"Necesita tener conexión a internet.",
						Toast.LENGTH_SHORT).show();
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

	private void LoadInfo() {
		final View v;

		AlertDialog.Builder adInfo = new AlertDialog.Builder(
				Usuario_Registro_Activity.this);

		LayoutInflater layInfo = this.getLayoutInflater();
		v = layInfo.inflate(R.layout.dialog_custom_about, null);
		adInfo.setView(v);

		adInfo.setNeutralButton("Okay", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		adInfo.show();
	}

}
