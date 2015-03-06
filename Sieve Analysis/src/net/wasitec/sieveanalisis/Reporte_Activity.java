package net.wasitec.sieveanalisis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.wasitec.sieveanalisis.R;
import net.wasitec.sieveanalisis.bean.VariablesGlobales;
import net.wasitec.sieveanalisis.servicios.Publicidad;
import net.wasitec.sieveanalisis.utils.Connection;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.CirclePageIndicator;

public class Reporte_Activity extends SherlockFragmentActivity {
	
	ViewPager pager = null;
	private static final String TAG = Reporte_Activity.class.getSimpleName();
	Reporte_Pager_Adapter pagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reporte_fragment);
		ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
		getWindow().setBackgroundDrawable(colorDrawable);
		this.pager = (ViewPager) this.findViewById(R.id.pager);
		// Crea un adaptador con el fragment que se mostrara en el pager
		Reporte_Pager_Adapter adapter = new Reporte_Pager_Adapter(
				getSupportFragmentManager());
		adapter.addFragment(Reporte_Informe_Fragment.newInstance(0));
		adapter.addFragment(Reporte_Tabla_Fragment.newInstance(1));
		adapter.addFragment(Reporte_Grafico_Fragment.newInstance(2,
				getBaseContext()));
		CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
		this.pager.setAdapter(adapter);
		iniciarPublicidad();
		indicator.setViewPager(pager);
		indicator.setSnap(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void iniciarPublicidad() {
		ImageButton imgBanner = (ImageButton) findViewById(R.id.btnPublicidad4);
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
		inflater.inflate(R.menu.reporte_, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.ic_email:
			showCustomAlertDialog();
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void showToast(String text){
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}
	
	private void showCustomAlertDialog() {
		
		AlertDialog.Builder custom_alert = new Builder(Reporte_Activity.this);
		LayoutInflater inflate = this.getLayoutInflater();
		final View v = inflate.inflate(R.layout.dialog_send_email, null);
		custom_alert.setView(v);
		
		String sEnviar = getResources().getString(R.string.send);
		custom_alert.setPositiveButton(sEnviar, new DialogInterface.OnClickListener() {
		String params[] = new String[10];
		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				TextView tvEmail = (TextView)v.findViewById(R.id.tv_email);
				String correo = tvEmail.getText().toString().trim();
				
				if (correo != "") {
					VariablesGlobales global = new VariablesGlobales();
					
					params[0] = tvEmail.getText().toString();
					params[1] = global.getReporte().getEmpresa();
					params[2] = global.getReporte().getUsuario();
					params[3] = global.getDatos().getMaterial();
					params[4] = global.getDatos().getPesoInicial();
					params[5] = String.valueOf(global.getMalla().getSumaTotal());
					params[6] = global.getDatos().getSistema();
					params[7] = global.getReporte().getComentario();
					
					new sendEmailAsync().execute(params);
				}else{
					showToast("Error.");
				}
			}
			
		});
		
		custom_alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.e(TAG, "nothing");
			}
		});
		
		custom_alert.show();
	}


	private class sendEmailAsync extends AsyncTask<String, Boolean, Boolean> {
		String message = null;
		ProgressDialog dialog = null;
		String sEmail = "",sEmpresa="",sNombre = "",sMaterial,sPesoInicial,sPesoCalculado,sSistema,sComentario;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(Reporte_Activity.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setTitle(getResources().getString(R.string.by_email));
			dialog.setMessage("Sending...");
			dialog.setCancelable(false);
			dialog.show();
			super.onPreExecute();
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			boolean bResult = false;
			
			JSONObject jsonMain = new JSONObject();
			
			sEmail = params[0];
			sEmpresa = params[1];
			sNombre = params[2];
			sMaterial = params[3];
			sPesoInicial = params[4];
			sPesoCalculado = params[5];
			sSistema = params[6];
			sComentario = params[7];
			
			try {
				VariablesGlobales global = new VariablesGlobales();
				
				jsonMain.accumulate("mail", sEmail);
				jsonMain.accumulate("empresa", sEmpresa);
				jsonMain.accumulate("nombre", sNombre);
				jsonMain.accumulate("material", sMaterial);
				jsonMain.accumulate("pesoinicial", sPesoInicial);
				jsonMain.accumulate("pesocalculado", sPesoCalculado);
				jsonMain.accumulate("sistema", sSistema);
				jsonMain.accumulate("comentario", sComentario);
				
				JSONArray jsonTares = new JSONArray();
				int length = global.getMalla().getPicker().length;
				for (int x = 1; x < length-1; x++) {
					jsonTares.put(x-1, global.getMalla().getPicker()[x]);
				}
				
				jsonMain.accumulate("tares", jsonTares);
				JSONArray jsonPesos = new JSONArray();
				
				length = global.getMalla().getFraccionPorcentaje().length;
				for (int x = 0; x < length; x++) {
					jsonPesos.put(x , global.getMalla().getFraccionPorcentaje()[x]);
				}
				jsonMain.accumulate("pesos", jsonPesos);
				
				
				Log.e(TAG, jsonMain.toString());
				Connection conn = new Connection();
				JSONObject jsonResponse = conn.makeHttpRequest("POST", jsonMain);
				bResult = true;
				
				try {
					message = jsonResponse.get("result").toString();
				} catch (Exception e) {
					message = jsonResponse.get("error").toString();
				}
				
				Log.e(TAG, message);
				
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e(TAG, jsonMain.toString() + " Error ");
			}
			
			return bResult;
		}

		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			dialog.dismiss();
			showToast(message);
		}
		
	}
}
