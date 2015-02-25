package net.wasitec.sieveanalisis.bean;

public class Reporte {
	private String empresa, titulo, usuario, comentario;

	public Reporte(String empresa, String titulo, String usuario,
			String comentario) {
		this.empresa = empresa;
		this.titulo = titulo;
		this.usuario = usuario;
		this.comentario = comentario;
	}

	public Reporte() {
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
