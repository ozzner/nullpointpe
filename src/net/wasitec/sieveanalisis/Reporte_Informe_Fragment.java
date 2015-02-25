package net.wasitec.sieveanalisis;

import net.wasitec.sieveanalisis.R;
import net.wasitec.sieveanalisis.bean.Datos;
import net.wasitec.sieveanalisis.bean.Malla;
import net.wasitec.sieveanalisis.bean.Reporte;
import net.wasitec.sieveanalisis.bean.VariablesGlobales;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Reporte_Informe_Fragment extends Fragment {

	private static final String INDEX = "index";

	public static Reporte_Informe_Fragment newInstance(int index) {

		// Crear una instancia a un nuevo fragment
		Reporte_Informe_Fragment fragment = new Reporte_Informe_Fragment();

		// Guarda los parámetros
		Bundle bundle = new Bundle();
		bundle.putInt(INDEX, index);
		fragment.setArguments(bundle);
		fragment.setRetainInstance(true);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.reporte_detalle, container, false);
		VariablesGlobales global = new VariablesGlobales();
		Reporte reporte = global.getReporte();
		Datos datos = new VariablesGlobales().getDatos();
		Malla malla = new VariablesGlobales().getMalla();

		TextView lblpesoInicial = (TextView) rootView
				.findViewById(R.id.labelPesoInicial);
		String pesoIni = getString(R.string.peso_inicial)
				+ datos.getUnidadMedida();
		lblpesoInicial.setText(pesoIni);

		TextView lblpesoCalculado = (TextView) rootView
				.findViewById(R.id.labelDetallePerdida);
		pesoIni = getString(R.string.peso_calculado) + datos.getUnidadMedida();
		lblpesoCalculado.setText(pesoIni);

		TextView lblTitulo = (TextView) rootView
				.findViewById(R.id.txtReporteTitulo);
		lblTitulo.setText(reporte.getTitulo());

		TextView txtEmpresa = (TextView) rootView
				.findViewById(R.id.txtReporteEmpresa);
		txtEmpresa.setText(reporte.getEmpresa());

		TextView txtUsuario = (TextView) rootView
				.findViewById(R.id.txtReporteUsuario);
		txtUsuario.setText(reporte.getUsuario());

		TextView txtMaterial = (TextView) rootView
				.findViewById(R.id.txtReporteMaterial);
		txtMaterial.setText(datos.getMaterial());

		TextView txtCantidad = (TextView) rootView
				.findViewById(R.id.txtReporteCantidad);
		txtCantidad.setText(datos.getPesoInicial());

		TextView txtPerdida = (TextView) rootView
				.findViewById(R.id.txtReportePerdida);
		txtPerdida.setText(Double.toString(malla.getSumaTotal()));

		TextView txtFecha = (TextView) rootView
				.findViewById(R.id.txtReporteFecha);
		txtFecha.setText(malla.getFecha());

		TextView txtSistema = (TextView) rootView
				.findViewById(R.id.txtReporteSistema);
		txtSistema.setText(datos.getSistema());

		TextView txtComentario = (TextView) rootView
				.findViewById(R.id.txtReporteComentario);
		txtComentario.setText(reporte.getComentario());
		return rootView;
	}
}