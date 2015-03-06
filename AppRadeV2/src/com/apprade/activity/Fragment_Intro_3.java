package com.apprade.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

public class Fragment_Intro_3 extends Fragment implements ValidationListener {


	private Helper_SubRoutines oRoutine;
	private Validator validator;
	Adapter_Dialog_Fragment oDialFrag;
	private Button btnSend;
	@Required(order = 1, message = "Ingrese un nombre.")
	@TextRule(order = 2,trim = true)
	private EditText etNombres;
	@Required(order = 3, message = "Este campo es requerido.")
	@TextRule(order = 5,trim = true)
	private EditText etCorreo;
	@Password(order = 6,message = "Ingrese un password")
	@TextRule(order = 7, minLength = 3, message = "Ingrese min 3 caracteres.",trim = true)
	private EditText etPassword;
	@ConfirmPassword(order = 8, message = "Las contraseñas no son iguales")
	private EditText etConfPassword;
	private RadioGroup rgSexo;
	private RadioButton selectRadio;
	private ImageButton ib;
	private ProgressDialog proDialog;
	private String sFecha = "2006-05-18", sNombre, sEmail, sPassword, sSexo;
	private DAO_Usuario dao;

	public Fragment_Intro_3() {
		dao = new DAO_Usuario();
		oRoutine = new Helper_SubRoutines();
		oDialFrag = new Adapter_Dialog_Fragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_intro_3, container, false);

		btnSend = (Button) v.findViewById(R.id.btn_enviar);
		etNombres = (EditText) v.findViewById(R.id.et_nombres);
		etPassword = (EditText) v.findViewById(R.id.et_password_reg);
		etConfPassword = (EditText) v.findViewById(R.id.et_pass_confirmar);
		etCorreo = (EditText) v.findViewById(R.id.et_correo);
		ib = (ImageButton) v.findViewById(R.id.imb_date);
		rgSexo = (RadioGroup) v.findViewById(R.id.rg_sexo);
		selectRadio = (RadioButton) v.findViewById(rgSexo
				.getCheckedRadioButtonId());

		validator = new Validator(this);
		validator.setValidationListener(this);
		
		ib.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				oDialFrag.show(getChildFragmentManager(), "MyDataPiker");
				
			}
		});

		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EnviarRegistro();
				validator.validate();

			}
		});

		return v;
	}

	private boolean validarRegistro() {

		boolean esError = false;

		String sexo = selectRadio.getText().toString();

		sSexo = sexo;

		if (sFecha == null) {
			oRoutine.showToast(getActivity(), "Ingrese fecha nacimiento");
			esError = true;
		} else {

			if (sFecha.equals(oRoutine
					.getCurrentTime(Helper_SubRoutines.TAG_FORMAT_DATE_MM))) {
				esError = true;
				oRoutine.showToast(getActivity(), "Ingrese fecha correcta");
			}
		}

		return esError;
	}

	 public void EnviarRegistro() {
	
		 sNombre = etNombres.getText().toString();
		 sEmail = etCorreo.getText().toString();
		 sPassword = etPassword.getText().toString();
		
		 oDialFrag = new Adapter_Dialog_Fragment();
		 sFecha = oDialFrag.getsFecha();
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
						dao.oUsuario.getUsuarioID(), 1, getActivity());

				Toast.makeText(
						getActivity(),
						"Hola "
								+ sNombre
								+ ", ya puedes iniciar sesión con tus datos, presiona Login ",
						Toast.LENGTH_LONG).show();
				Intent i = new Intent(getActivity(),
						Usuario_Login_Activity.class);
				i.putExtra("correo", sEmail);
				i.putExtra("password", sPassword);
				startActivity(i);
				getActivity().finish();
			} else {
				Toast.makeText(
						getActivity(),
						dao.oJsonStatus.getMessage() + ". "
								+ dao.oJsonStatus.getInfo(), Toast.LENGTH_LONG)
						.show();
			}

		}

	}

	public void showDialogo() {
		proDialog = new ProgressDialog(getActivity());
		proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		proDialog.setMessage("Enviando...");
		proDialog.show();
	}

	@Override
	public void onValidationSucceeded() {
		
		if (!validarRegistro()){
			
			if (oRoutine.isOnline(getActivity())) 
				new TaskHttpMethodAsync().execute();
			 else 
				Toast.makeText(getActivity(), "Necesita tener conexión a internet.", Toast.LENGTH_SHORT).show();
			
			
		}
			
	}

	@Override
	public void onValidationFailed(View failedView, Rule<?> failedRule) {

		String message = failedRule.getFailureMessage();

		if (failedView instanceof EditText) {
			failedView.requestFocus();
			((EditText) failedView).setError(message);
		} else {
			Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
		}
	}

}// End class
