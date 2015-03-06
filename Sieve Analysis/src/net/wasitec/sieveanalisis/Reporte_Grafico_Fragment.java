package net.wasitec.sieveanalisis;

import net.wasitec.sieveanalisis.R;
import net.wasitec.sieveanalisis.servicios.Grafico;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Reporte_Grafico_Fragment extends Fragment {

	private static final String INDEX = "index";

	private static final String IMAGE_ROUTE = "image";
	private static Context contexto;
	private int imageroute;

	public static Reporte_Grafico_Fragment newInstance(int index,
			Context context) {

		// Crear una instancia a un nuevo fragment
		Reporte_Grafico_Fragment fragment = new Reporte_Grafico_Fragment();

		// Guarda los parámetros
		Bundle bundle = new Bundle();
		bundle.putInt(INDEX, index);
		fragment.setArguments(bundle);
		fragment.setRetainInstance(true);
		contexto = context;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//

		// Imagenes
		this.imageroute = (getArguments() != null) ? getArguments().getInt(
				IMAGE_ROUTE) : 0;
	}

	ViewGroup rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = (ViewGroup) inflater.inflate(R.layout.reporte_grafico,
				container, false);
		LinearLayout chartContainer = (LinearLayout) rootView
				.findViewById(R.id.chart_container);
		new Grafico().openChart(chartContainer, contexto);
		return rootView;
	}
}