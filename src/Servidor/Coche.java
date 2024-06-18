package Servidor;

import java.io.Serializable;

public class Coche implements Serializable{
	private String matricula;
	private String marca;
	private String modelo;
	private int anio;
	private String color;
	private float precio;
	private String etiqueta;
	private String codMotor;
	private String combustible;
	private int cilindrada;
	private int potencia;
	private int nCilindros;
	private int nValvulas;
	
	public Coche() {
		
	}

	public Coche(String matricula, String marca, String modelo, int anio, String color, float precio, String etiqueta,
			String codMotor, String combustible, int cilindrada, int potencia, int nCilindros, int nValvulas) {
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.anio = anio;
		this.color = color;
		this.precio = precio;
		this.etiqueta = etiqueta;
		this.codMotor = codMotor;
		this.combustible = combustible;
		this.cilindrada = cilindrada;
		this.potencia = potencia;
		this.nCilindros = nCilindros;
		this.nValvulas = nValvulas;
	}

	@Override
	public String toString() {
		return "Matricula: " + matricula + ", Marca: " + marca + ", Modelo: " + modelo + ", Año: " + anio
				+ ", Color: " + color + ", Precio: " + precio + ", Etiqueta: " + etiqueta + ", Codigo de Motor: " + codMotor
				+ ", Combustible: " + combustible + ", Cilindrada: " + cilindrada + ", Potencia: " + potencia
				+ ", Numero de Cilindros: " + nCilindros + ", Número de Valvulas: " + nValvulas;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getCodMotor() {
		return codMotor;
	}

	public void setCodMotor(String codMotor) {
		this.codMotor = codMotor;
	}

	public String getCombustible() {
		return combustible;
	}

	public void setCombustible(String combustible) {
		this.combustible = combustible;
	}

	public int getCilindrada() {
		return cilindrada;
	}

	public void setCilindrada(int cilindrada) {
		this.cilindrada = cilindrada;
	}

	public int getPotencia() {
		return potencia;
	}

	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	public int getnCilindros() {
		return nCilindros;
	}

	public void setnCilindros(int nCilindros) {
		this.nCilindros = nCilindros;
	}

	public int getnValvulas() {
		return nValvulas;
	}

	public void setnValvulas(int nValvulas) {
		this.nValvulas = nValvulas;
	}
	
	
	
	
	
}
