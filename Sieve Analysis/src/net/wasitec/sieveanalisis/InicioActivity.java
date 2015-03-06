package net.wasitec.sieveanalisis;

import net.wasitec.sieveanalisis.R;
import net.wasitec.sieveanalisis.bean.Datos;
import net.wasitec.sieveanalisis.bean.Reporte;
import net.wasitec.sieveanalisis.bean.VariablesGlobales;
import net.wasitec.sieveanalisis.servicios.Publicidad;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class InicioActivity extends SherlockActivity {

	private EditText empresa, titulo, usuario, comentario, txtPesoInicial,
			txtCantidadTransformar, txtMaterial;
	private Spinner spnUnidad, spnSistema;
	private static Datos datos;
	private static Reporte reporte;
	private String empresa_, titulo_, usuario_, comentario_, sistema_, peso_,
			cantidad_, material_;
	private double unidad_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inicio_layout);
		ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
		getWindow().setBackgroundDrawable(colorDrawable);
		iniciarPublicidad();
		capturaVistas();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void capturaVistas() {
		empresa = (EditText) findViewById(R.id.txtEmpresa);
		titulo = (EditText) findViewById(R.id.txtTitulo);
		usuario = (EditText) findViewById(R.id.txtUsuario);
		comentario = (EditText) findViewById(R.id.txtInicioComentario);
		spnUnidad = (Spinner) findViewById(R.id.spnUnidad);
		spnSistema = (Spinner) findViewById(R.id.spnSistema);
		txtCantidadTransformar = (EditText) findViewById(R.id.txtDatosCantidadTransformar);
		txtPesoInicial = (EditText) findViewById(R.id.txtDatosPesoInicial);
		txtMaterial = (EditText) findViewById(R.id.txtDatosMaterial);
	}

	public boolean validarEntradas() {
		empresa_ = empresa.getText().toString();
		titulo_ = titulo.getText().toString();
		usuario_ = usuario.getText().toString();
		comentario_ = comentario.getText().toString();
		sistema_ = spnSistema.getSelectedItem().toString();
		unidad_ = spnUnidad.getSelectedItemId();
		peso_ = txtPesoInicial.getText().toString();
		cantidad_ = txtCantidadTransformar.getText().toString();
		material_ = txtMaterial.getText().toString();
		if (empresa_.equals("") || titulo_.equals("") || usuario_.equals("")
				|| peso_.equals("") || cantidad_.equals("")
				|| material_.equals("")) {
			Toast.makeText(this, R.string.llenar_obligatorios,
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			setReporte();
			return true;
		}
	}

	public void setReporte() {
		reporte = new Reporte(empresa_, titulo_, usuario_, comentario_);
		datos = new Datos(sistema_, unidad_, peso_, cantidad_, material_);
		VariablesGlobales global = new VariablesGlobales();
		global.setReporte(reporte);
		global.setDatos(datos);
	}

	public void clickIr() {
		Intent i = new Intent(this, Malla_Activity.class);
		if (validarEntradas())
			startActivity(i);
	}

	public void clickLimpiar() {
		empresa.setText(null);
		titulo.setText(null);
		usuario.setText(null);
		comentario.setText(null);
		txtCantidadTransformar.setText(null);
		txtPesoInicial.setText(null);
		txtMaterial.setText(null);
	}

	public void iniciarPublicidad() {
		ImageButton imgBanner = (ImageButton) findViewById(R.id.btnPublicidad);
		final Publicidad pub = new Publicidad(imgBanner);

		imgBanner.setImageBitmap(pub.getImg());
		imgBanner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(pub.getUrl()));
				startActivity(i);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.inicio, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.siguiente:
			clickIr();
			return true;
		case R.id.limpiar:
			clickLimpiar();
			return true;
		case R.id.salir:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
