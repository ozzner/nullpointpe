package net.wasitec.sieveanalisis;

import java.util.ArrayList;
import java.util.List;

import net.wasitec.sieveanalisis.R;
import net.wasitec.sieveanalisis.bean.Datos;
import net.wasitec.sieveanalisis.bean.Malla;
import net.wasitec.sieveanalisis.bean.Sistemas;
import net.wasitec.sieveanalisis.bean.VariablesGlobales;
import net.wasitec.sieveanalisis.servicios.Calcular;
import net.wasitec.sieveanalisis.servicios.Publicidad;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class Malla_Activity extends SherlockActivity {
	private VariablesGlobales global;
	private Datos datos;
	private List<EditText> tareEdit, sieveEdit, sievePicker;
	private List<TextView> resultado;
	private EditText edit;
	private TextView suma;
	private String[] sistem;
	private EditText label2;
	private int INDEX = 0;
	public double result_porc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.malla_layout);
		ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
		getWindow().setBackgroundDrawable(colorDrawable);
		global = new VariablesGlobales();
		datos = global.getDatos();
		cargarSistema();
		cargarEtiquetas();
		cargarMallas();
		iniciarPublicidad();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void iniciarPublicidad() {
		ImageButton imgBanner = (ImageButton) findViewById(R.id.btnPublicidad3);
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

	private void cargarSistema() {
		switch (datos.getTipoSistema()) {
		case 1:
			sistem = new Sistemas().getIso();
			break;
		case 2:
			sistem = new Sistemas().getAstm();
			break;
		case 3:
			sistem = new Sistemas().getTyler();
			break;
		case 4:
			sistem = new Sistemas().getBritanico();
			break;
		default:
			break;
		}
		// temporal = sistem;
	}

	private void cargarEtiquetas() {
		TextView txtUnidad1, txtUnidad2, txtUnidad3;
		txtUnidad1 = (TextView) findViewById(R.id.txtMalla_TTare);
		txtUnidad2 = (TextView) findViewById(R.id.txtMalla_TPSieve);
		txtUnidad3 = (TextView) findViewById(R.id.txtMalla_TResultado);
		String pesotare = getResources().getString(R.string.pesotare);
		String pesosieve = getResources().getString(R.string.pesosieve);
		String resultado = getResources().getString(R.string.resultado);

		txtUnidad1.setText(pesotare + " " + datos.getUnidadMedida());
		txtUnidad2.setText(pesosieve + " " + datos.getUnidadMedida());
		txtUnidad3.setText(resultado + " " + datos.getUnidadMedida());

	}

	private void cargarMallas() {
		tareEdit = new ArrayList<EditText>();
		sieveEdit = new ArrayList<EditText>();
		sievePicker = new ArrayList<EditText>();
		resultado = new ArrayList<TextView>();
		edit = (EditText) findViewById(R.id.txtMalla_Tare);
		tareEdit.add(edit);
		edit = (EditText) findViewById(R.id.txtMalla_Sieve);
		sieveEdit.add(edit);
		suma = (TextView) findViewById(R.id.txtMalla_Calculo);
		resultado.add(suma);
		TableLayout table = (TableLayout) findViewById(R.id.tableMallas);
		for (int i = 0; i < Integer.parseInt(datos.getCantidadTransformar()) - 1; i++) {
			TableRow row = new TableRow(this);
			row.setId(100 + i);
			row.setLayoutParams(new TableRow.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					Gravity.CENTER_HORIZONTAL));

			// crear nuevas Filas tipo TextView y EditText

			TextView label1 = new TextView(this);// numero
			label1.setId(200 + i);
			label1.setText(Integer.toString(i + 2));
			label1.setTypeface(Typeface.DEFAULT_BOLD);
			label1.setPadding(2, 0, 2, 1);
			row.addView(label1);
			label2 = new EditText(this);// Sieve
			label2.setId(210 + i);
			sievePicker.add(label2);
			final int id_ = i;
			label2.setEms(3);
			label2.setGravity(Gravity.CENTER);
			label2.setHint("-");
			label2.setEms(3);
			label2.setKeyListener(null);
			label2.setPadding(2, 0, 2, 1);
			label2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					clickOpenPicker(id_);
				}
			});
			row.addView(label2);

			EditText label3 = new EditText(this);// peso tare
			label3.setId(220 + i);
			tareEdit.add(label3);
			label3.setInputType(InputType.TYPE_CLASS_NUMBER
					| InputType.TYPE_NUMBER_FLAG_DECIMAL);
			String hint = getString(R.string.hintTare);
			String numero = Integer.toString(i + 2);
			label3.setHint(hint + " " + numero);
			label3.setGravity(Gravity.CENTER);
			label3.setPadding(2, 0, 2, 1);
			row.addView(label3);

			EditText label4 = new EditText(this);// peso sieve
			label4.setId(240 + i);
			sieveEdit.add(label4);
			label4.setGravity(Gravity.CENTER);
			label4.setInputType(InputType.TYPE_CLASS_NUMBER
					| InputType.TYPE_NUMBER_FLAG_DECIMAL);
			hint = getString(R.string.hintSieve);
			label4.setHint(hint + " " + numero);
			label4.setEms(3);
			label4.setPadding(2, 0, 2, 1);
			row.addView(label4);

			TextView label5 = new TextView(this);// calculo
			label5.setId(250 + i);
			resultado.add(label5);
			label5.setEms(3);
			label5.setTextSize(18);
			label5.setGravity(Gravity.CENTER);
			label5.setHint(R.string._);
			label5.setKeyListener(null);
			label5.setTypeface(Typeface.DEFAULT_BOLD);
			label5.setPadding(2, 0, 2, 1);
			row.addView(label5);
			table.addView(row, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					Gravity.CENTER_HORIZONTAL));
		}

		TableRow row2 = new TableRow(this);
		row2.setId(150);
		row2.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				Gravity.CENTER_HORIZONTAL));

		// create a new Fila tipo TextView cabecera

		TextView label1 = new TextView(this); // espacio1
		label1.setId(151);
		label1.setText("");
		label1.setTypeface(Typeface.DEFAULT_BOLD);
		label1.setPadding(2, 0, 2, 1);
		row2.addView(label1);

		TextView label21 = new TextView(this); // espacio2
		label21.setId(152);
		label21.setText("");
		label21.setTypeface(Typeface.DEFAULT_BOLD);
		label21.setPadding(2, 0, 2, 1);
		row2.addView(label21);

		TextView labelsumatoria = new TextView(this); // Sample Quantity
		labelsumatoria.setId(151);
		String sumar = getString(R.string.sumatoria) + datos.getUnidadMedida()
				+ ":";
		labelsumatoria.setText(sumar);
		labelsumatoria.setTextSize(17); // Tamaño de letra
		labelsumatoria.setTypeface(Typeface.DEFAULT_BOLD);
		labelsumatoria.setPadding(2, 0, 2, 1);
		labelsumatoria.setGravity(Gravity.RIGHT);
		row2.addView(labelsumatoria);

		suma = new TextView(this); // Sample Quantity number
		suma.setId(154);
		suma.setText("0.0");
		suma.setTextSize(18); // Tamaño de letra
		suma.setGravity(Gravity.CENTER);
		suma.setTypeface(Typeface.DEFAULT_BOLD);
		suma.setPadding(2, 0, 2, 1);
		row2.addView(suma);

		TextView label41 = new TextView(this); // espacio3
		label41.setId(155);
		label41.setText("");
		label41.setPadding(2, 0, 2, 1);
		row2.addView(label41);
		table.addView(row2, new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				Gravity.CENTER_HORIZONTAL));
	}

	public void clickMallaCalcular() {
		Calcular calcular = new Calcular();
		double sumatoria = 0;
		try {
			// validar datos no vacios
			if (calcular.validarNoVacios(tareEdit, sieveEdit, sievePicker)) {
				if (calcular.validarSieveMayorTare(sieveEdit, tareEdit)) {
					// calcular
					String[] picker = calcular.sievePicker(sievePicker,
							sieveEdit.size());

					sumatoria = calcular.sumarPeso(sieveEdit, tareEdit);
					double[] resultadoFraccion = calcular.resultadoFraccion();
					String fecha = calcular.fechaActual();
					double[] acumPorcentaje = calcular
							.resultadoFraccionPorcentaje(resultadoFraccion,
									sumatoria);
					double[] resultadoAcumulado = calcular
							.resultadoAcumulado(acumPorcentaje);

					double[] pesoSieve = calcular.pesoSieve(sieveEdit);
					suma.setText(Double.toString(sumatoria));
					for (int i = 0; i < resultadoFraccion.length; i++) {
						resultado.get(i).setText(
								Double.toString(resultadoFraccion[i]));
					}
					// guardar resultados
					new VariablesGlobales().setMalla(new Malla(sumatoria,
							resultadoFraccion, pesoSieve, fecha,
							resultadoAcumulado, picker, acumPorcentaje));
					if (calcular.sumaValida(sumatoria)) {
						Toast.makeText(this, R.string.calculo_correcto,
								Toast.LENGTH_LONG).show();
						double peso_ini = Double.parseDouble(new VariablesGlobales()
						.getDatos().getPesoInicial());
						
						result_porc = sumatoria * 100 / peso_ini;
						
					} else {
						double ini = Double.parseDouble(new VariablesGlobales()
								.getDatos().getPesoInicial());
						String part1 = getString(R.string.part1);
						String part2 = getString(R.string.part2);
						result_porc = (sumatoria / ini) * 100;
						result_porc = Math.round(result_porc * 1000.0) / 1000.0;
						new AlertDialog.Builder(this)
								.setTitle(R.string.alerta)
								.setMessage(
										part1 + " " + result_porc + " " + part2)
								.setNeutralButton("Close", null).show();

					}
				} else {
					Toast.makeText(this, R.string.sieve_mayor_tare,
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, R.string.validar_vacios,
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickMallaReporte() {

		if (new VariablesGlobales().getMalla().getSumaTotal() > 0) {
			Log.e("RESULT", String.valueOf(result_porc));
			if (result_porc <= 100) {
				Intent i = new Intent(this, Reporte_Activity.class);
				startActivity(i);
			} else {
				Toast.makeText(this, "El resultado no puede ser mayor a 100 %",
						Toast.LENGTH_LONG).show();
			}

		} else
			Toast.makeText(this, R.string.validar_calcular, Toast.LENGTH_SHORT)
					.show();
	}

	public void clickMallaLimpiar() {
		try {
			for (int i = 0; i < sieveEdit.size(); i++) {
				tareEdit.get(i).setText(null);
				sieveEdit.get(i).setText(null);

				resultado.get(i).setText(null);
			}
			new VariablesGlobales().limpiarMalla();
			for (int i = 0; i < sievePicker.size(); i++) {
				sievePicker.get(i).setText(null);
			}
			suma.setText("0.00");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickMallaRegresar() {
		new VariablesGlobales().limpiarDatos();
		new VariablesGlobales().limpiarReporte();
		onBackPressed();
	}

	public void clickOpenPicker(final int i) {
		final Dialog d = new Dialog(Malla_Activity.this);
		d.setTitle(datos.getSistema() + "-" + (i + 2));
		d.setContentView(R.layout.dialog_picker);
		Button b1 = (Button) d.findViewById(R.id.btnDialogOki);
		Button b2 = (Button) d.findViewById(R.id.btnDialogCanceli);
		final NumberPicker np = (NumberPicker) d.findViewById(R.id.np);
		np.setMaxValue(sistem.length - 1);
		np.setMinValue(0);
		np.setValue(INDEX);
		np.setDisplayedValues(sistem);
		np.setWrapSelectorWheel(false);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				np.clearFocus();
				INDEX = np.getValue();
				String retorno = sistem[INDEX];
				INDEX++;
				sievePicker.get(i).setText(retorno);
				sievePicker.get(i).setGravity(Gravity.CENTER);
				sievePicker.get(i).setKeyListener(null);
				d.dismiss();
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				d.dismiss();
			}
		});
		d.show();
	}

	@Override
	public void onBackPressed() {
		new VariablesGlobales().limpiarMalla();
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.malla, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.calcular:
			clickMallaCalcular();
			return true;
		case R.id.reporte:
			clickMallaReporte();
			return true;
		case R.id.limpiar:
			clickMallaLimpiar();
			return true;
		case R.id.regresar:
			clickMallaRegresar();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
