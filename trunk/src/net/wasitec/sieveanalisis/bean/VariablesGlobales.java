package net.wasitec.sieveanalisis.bean;

public class VariablesGlobales {
	static private Reporte reporte = null;
	static private Datos datos = null;
	static private Malla malla = null;

	public VariablesGlobales() {
		if (datos == null) {
			datos = new Datos();
		}
		if (reporte == null) {
			reporte = new Reporte();
		}
		if (malla == null)
			malla = new Malla();
	}

	public Datos getDatos() {
		return datos;
	}

	public void setDatos(Datos datos) {
		VariablesGlobales.datos = datos;
	}

	public Reporte getReporte() {
		return reporte;
	}

	public void setReporte(Reporte reporte) {
		VariablesGlobales.reporte = reporte;
	}

	public Malla getMalla() {
		return malla;
	}

	public void setMalla(Malla malla) {
		VariablesGlobales.malla = malla;
	}

	public void limpiarReporte() {
		reporte = null;
	}

	public void limpiarDatos() {
		datos = null;
	}

	public void limpiarMalla() {
		malla = null;
	}
}
