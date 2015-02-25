package net.wasitec.sieveanalisis.bean;

public class Datos {
	private String sistema, unidadMedida, pesoInicial, cantidadTransformar,
			material;
	private int tipoSistema;

	public Datos(String sistema, double unidadMedida, String pesoInicial,
			String cantidadTransformar, String material) {
		this.sistema = sistema;
		this.pesoInicial = pesoInicial;
		this.cantidadTransformar = cantidadTransformar;
		this.material = material;
		tipoSistema();
		unidadMedida(unidadMedida);
	}

	private void unidadMedida(double unidadMedidas) {
		if (unidadMedidas == 0) {
			unidadMedida = "(g.)";
		} else {
			unidadMedida = "(oz.)";
		}
	}

	private void tipoSistema() {
		if (sistema.equals("ISO")) {
			tipoSistema = 1;
		} else if (sistema.equals("ASTM")) {
			tipoSistema = 2;
		} else if (sistema.equals("TYLER")) {
			tipoSistema = 3;
		} else if (sistema.equals("BRITANICO")) {
			tipoSistema = 4;
		}
	}

	public Datos() {
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getPesoInicial() {
		return pesoInicial;
	}

	public void setPesoInicial(String pesoInicial) {
		this.pesoInicial = pesoInicial;
	}

	public String getCantidadTransformar() {
		return cantidadTransformar;
	}

	public void setCantidadTransformar(String cantidadTransformar) {
		this.cantidadTransformar = cantidadTransformar;
	}

	public int getTipoSistema() {
		return tipoSistema;
	}

	public void setTipoSistema(int tipoSistema) {
		this.tipoSistema = tipoSistema;
	}

}
