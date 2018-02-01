package br.com.massuda.alexander.persistencia.jdbc.utils;

public class ParametroParaComandoParametrizado {

	private Object valor;
	private Class tipo;
	
	public Object valor() {
		return valor;
	}
	public void valor(Object valor) {
		this.valor = valor;
	}
	public Class tipo() {
		return tipo;
	}
	public void tipo(Class tipo) {
		this.tipo = tipo;
	}
	
}
