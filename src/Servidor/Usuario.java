package Servidor;

import java.io.Serializable;

public class Usuario implements Serializable{
	private String user;
	private String pass;
	private String tipo;
	
	public Usuario(String user, String pass, String tipo) {
		this.user = user;
		this.pass = pass;
		this.tipo = tipo;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
	
}
