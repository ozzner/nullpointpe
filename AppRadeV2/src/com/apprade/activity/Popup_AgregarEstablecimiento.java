package com.apprade.activity;

import com.apprade.R;







//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Popup_AgregarEstablecimiento extends Activity {

	//Views
	private	EditText et_direccion;
	private	EditText et_latitude;
	private	EditText et_longitude;
	private	EditText et_distrito;
	private	EditText et_nombre;
	private Spinner  spin_categoria;
	
	
	
	//Establisment values
		private String sNombre;
		private String sDistrito;
		private String sDireccion;
		private String arrCategoria[];
		private double dLatitude;
		private double dLongitude;
		
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_agregar_establecimiento);
		
		initViews();
		savedInstanceState = getIntent().getExtras();
		setValuesFromBundle(savedInstanceState);
		pupulateSpinnerCategory();
	}

	private void initViews() {
		
		et_direccion   = (EditText)findViewById(R.id.et_direccion);
		et_latitude    = (EditText)findViewById(R.id.et_latitude);
		et_longitude   = (EditText)findViewById(R.id.et_longitude);
		et_distrito    = (EditText)findViewById(R.id.et_distrito);
		et_nombre      = (EditText)findViewById(R.id.et_nombre);
		spin_categoria = (Spinner)findViewById(R.id.spin_distritos);
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
	}


	private void pupulateSpinnerCategory() {
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.array_catagory, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_categoria.setAdapter(adapter);		
	}

	
	
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
}
