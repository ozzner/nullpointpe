package net.wasitec.sieveanalisis;

import net.wasitec.sieveanalisis.R;
import net.wasitec.sieveanalisis.bean.Malla;
import net.wasitec.sieveanalisis.bean.VariablesGlobales;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Reporte_Tabla_Fragment extends Fragment {

	private static final String INDEX = "index";
	private Malla malla;

	public static Reporte_Tabla_Fragment newInstance(int index) {

		// Crear una instancia a un nuevo fragment
		Reporte_Tabla_Fragment fragment = new Reporte_Tabla_Fragment();

		// Guarda los parámetros
		Bundle bundle = new Bundle();
		bundle.putInt(INDEX, index);
		fragment.setArguments(bundle);
		fragment.setRetainInstance(true);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		malla = new VariablesGlobales().getMalla();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.reporte_tabla, container, false);
		TextView cabecera1_tabla = (TextView) rootView
				.findViewById(R.id.txtReporte_TSieve);
		String cabecera1 = "  "
				+ new VariablesGlobales().getDatos().getSistema() + " [mm] ";
		cabecera1_tabla.setText(cabecera1);
		TableLayout table = (TableLayout) rootView
				.findViewById(R.id.tableReporte);

		for (int i = 0; i < malla.getFracciones().length; i++) {
			
			TableRow row = new TableRow(rootView.getContext());
			row.setId(10 + i);
			
			row.setLayoutParams(new TableRow.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
					Gravity.CENTER));

			TextView label1 = new TextView(rootView.getContext());
			label1.setId(20 + i);
			label1.setGravity(Gravity.CENTER);
			label1.setBackgroundResource(R.drawable.celda_cuerpo);
			
			if (i == 0)
				label1.setText(" " + " < " + malla.getPicker()[i + 1]);
			else if (i == malla.getFracciones().length - 1)
				label1.setText(" > " + malla.getPicker()[i]);
			else
				label1.setText(" " + malla.getPicker()[i] + " - "
						+ malla.getPicker()[i + 1]);
			
			label1.setTypeface(Typeface.DEFAULT_BOLD);
			label1.setPadding(2, 0, 2, 1);
			row.addView(label1);

			TextView label2 = new TextView(rootView.getContext());
			label2.setId(30 + i);
			label2.setGravity(Gravity.CENTER);
			label2.setBackgroundResource(R.drawable.celda_cuerpo);
			label2.setText(Double.toString(malla.getFraccionPorcentaje()[i]));
			label2.setTypeface(Typeface.DEFAULT_BOLD);
			label2.setPadding(2, 0, 2, 1);
			row.addView(label2);

			TextView label3 = new TextView(rootView.getContext());
			label3.setId(40 + i);
			label3.setGravity(Gravity.CENTER);
			label3.setBackgroundResource(R.drawable.celda_cuerpo);
			label3.setText(Double.toString(malla.getAcumulado()[i]));
			label3.setTypeface(Typeface.DEFAULT_BOLD);
			label3.setPadding(2, 0, 2, 1);
			row.addView(label3);

			table.addView(row, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
					Gravity.CENTER));
		}

		return rootView;
	}
}
