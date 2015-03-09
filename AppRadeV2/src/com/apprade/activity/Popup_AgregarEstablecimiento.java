package com.apprade.activity;

//import android.support.v7.app.ActionBarActivity;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.apprade.R;
import com.apprade.dao.DAO_Establecimiento;
import com.apprade.entity.Entity_Comentario;

public class Popup_AgregarEstablecimiento extends Activity implements OnClickListener, OnItemSelectedListener {

	//Views
		private	EditText et_direccion;
		private	EditText et_latitude;
		private	EditText et_longitude;
		private	EditText et_distrito;
		private	EditText et_nombre;
		private Spinner  spin_categoria;
		private Button btn_send_local;
		private Button btn_cancel_send;
		private ProgressDialog proDialog;
		
		
	//Establisment values
		private String sNombre;
		private String sDistrito;
		private String sDireccion;
		private int iCat_id;
		private int iDis_id;
		private double dLatitude;
		private double dLongitude;
		
	//Generics
		private Context _context;
		private static final String DEFAULT_STATUS = "pendiente";
		private static final String TAG = Popup_AgregarEstablecimiento.class.getSimpleName();
		
		
	//Constructors	
		public Popup_AgregarEstablecimiento() {
			super();
			_context = Popup_AgregarEstablecimiento.this;
		}

		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_agregar_establecimiento);
		
		initViews();
		savedInstanceState = getIntent().getExtras();
		setValuesFromBundle(savedInstanceState);
		pupulateSpinnerCategory();
		initListeners();
	}


	private void initViews() {
		
		et_direccion    = (EditText)findViewById(R.id.et_direccion);
		et_latitude     = (EditText)findViewById(R.id.et_latitude);
		et_longitude    = (EditText)findViewById(R.id.et_longitude);
		et_distrito     = (EditText)findViewById(R.id.et_distrito);
		et_nombre       = (EditText)findViewById(R.id.et_nombre);
		spin_categoria  = (Spinner)findViewById(R.id.spin_distritos);
		btn_send_local  = (Button)findViewById(R.id.btn_send_local);
		btn_cancel_send = (Button)findViewById(R.id.btn_cancel_local);
		
	}
	

	private void setValuesFromBundle(Bundle values) {
		
		//get
		sDireccion  = values.getString("direccion");
		dLatitude   = values.getDouble("latitude");
		dLongitude  = values.getDouble("longitude");
		sDistrito   = values.getString("distrito");
		
		//set
		et_direccion.setText(sDireccion);
		et_longitude.setText(String.valueOf(dLongitude));
		et_latitude.setText(String.valueOf(dLatitude));
		et_distrito.setText(sDistrito);
		
		et_latitude.setEnabled(false);
		et_longitude.setEnabled(false);
	}


	private void pupulateSpinnerCategory() {
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.array_catagory, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_categoria.setAdapter(adapter);		
	}
	
	private void initListeners() {
		
		btn_send_local.setOnClickListener(this);
		spin_categoria.setOnItemSelectedListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		
		if (checkFieldsRequired()) {
			new SendLocalAsync().execute();
		}else{
			showToast("Campos obligatorios: Nombre");
		}
		
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		iCat_id = position + 1;
		Log.e(TAG, "CategoryID " + iCat_id);
	}


	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		Log.e(TAG, "NOTHING ");
	}
	
	
	public void cancelarEnvioClick(View v){
		finish();
	}
	
	
	/************ SUBROUTINES *************/
	
	private void showDialog(){
		proDialog = new ProgressDialog(_context);
		proDialog.setTitle("Nuevo local");
		proDialog.setMessage("Enviando...");
		proDialog.setCancelable(false);
		proDialog.show();
	}
	
	private void showToast(String text){
		
		Context context = _context;
		int duration = Toast.LENGTH_SHORT;
		
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		//Position
		toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 8);
	}
	
	private boolean checkFieldsRequired(){
		boolean bOk = false;
		
		//nesesary
		dLatitude = Double.parseDouble(et_latitude.getText().toString().trim());
		dLongitude = Double.parseDouble(et_longitude.getText().toString().trim());
		sNombre = et_nombre.getText().toString().trim();
		
		sDireccion = et_direccion.getText().toString().trim();
		sDistrito = et_distrito.getText().toString().trim();
		
		if (String.valueOf(dLatitude) != "" && String.valueOf(dLatitude) != "" && !sNombre.isEmpty()) {
			bOk = true;
		}
		
		if (sDireccion.isEmpty()) 
			sDireccion = "Contacto móvil";
		
		if (sDistrito.isEmpty()) 
			sDistrito = "Contacto móvil";
		
		return bOk;
	}
	
	
	/************ MENU *************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_popup_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	/************ ASYNC TASKS *************/

	class SendLocalAsync extends AsyncTask<Void, Void, Boolean>{
		DAO_Establecimiento dao;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			boolean b = false;
			dao = new DAO_Establecimiento();
			
			int result = dao.enviarNuevoEstablecimiento(
											sDireccion,
											sNombre,
											DEFAULT_STATUS,
											iCat_id, 
											18, 
											dLatitude,
											dLongitude);
			if (result == 1) {
				b = true;
			}
			
			return b;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			proDialog.dismiss();
			
			if (result) {
				String text = dao.oJsonStatus.getMessage();
				showToast(text);
				
				new Thread(new Runnable() {

					@Override
					public void run() {

						try {
							Thread.sleep(1500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								finish();
							}
						});
					}
				}).start();
				
			}else{
				showToast("Vuelva a intentarlo.");
			}
			
		}
	}


	


	
	
}
