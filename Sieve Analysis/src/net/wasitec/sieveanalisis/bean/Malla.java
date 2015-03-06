package net.wasitec.sieveanalisis.bean;

public class Malla {
	private double sumaTotal;
	private double[] fracciones;
	private double[] pesoSieve;
	private double[] acumulado;
	private double[] fraccionPorcentaje;
	private String[] picker;
	private String fecha;

	public Malla(double sumaTotal, double[] fracciones, double[] pesoSieve,
			String fecha, double[] acumulado, String[] picker,
			double[] fraccionPorcentaje) {
		this.sumaTotal = sumaTotal;
		this.pesoSieve = pesoSieve;
		this.fracciones = fracciones;
		this.fecha = fecha;
		this.acumulado = acumulado;
		this.picker = picker;
		this.fraccionPorcentaje = fraccionPorcentaje;
	}

	public Malla() {
	}

	public double[] getPesoSieve() {
		return pesoSieve;
	}

	public void setPesoSieve(double[] pesoSieve) {
		this.pesoSieve = pesoSieve;
	}

	public double getSumaTotal() {
		return sumaTotal;
	}

	public void setSumaTotal(double sumaTotal) {
		this.sumaTotal = sumaTotal;
	}

	public double[] getFracciones() {
		return fracciones;
	}

	public void setFracciones(double[] fracciones) {
		this.fracciones = fracciones;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public double[] getAcumulado() {
		return acumulado;
	}

	public void setAcumulado(double[] acumulado) {
		this.acumulado = acumulado;
	}

	public String[] getPicker() {
		return picker;
	}

	public void setPicker(String[] picker) {
		this.picker = picker;
	}

	public double[] getFraccionPorcentaje() {
		return fraccionPorcentaje;
	}

	public void setFraccionPorcentaje(double[] fraccionPorcentaje) {
		this.fraccionPorcentaje = fraccionPorcentaje;
	}
}
