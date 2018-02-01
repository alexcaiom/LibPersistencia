package br.com.massuda.alexander.persistencia.jdbc.finder;

import java.lang.reflect.Field;

public class Filtro {

	private Field atributo;
	private TipoFiltro tipo;
	private Object valor;
	private boolean positivo;

	private Filtro() {}
	
	public Field getAtributo() {
		return atributo;
	}
	public Filtro setAtributo(Field atributo) {
		this.atributo = atributo;
		return this;
	}
	public TipoFiltro getTipo() {
		return tipo;
	}
	public Filtro setTipo(TipoFiltro tipo) {
		this.tipo = tipo;
		return this;
	}
	public Object getValor() {
		return valor;
	}
	public Filtro setValor(Object valor) {
		this.valor = valor;
		return this;
	}
	public boolean isPositivo() {
		return positivo;
	}
	public Filtro setPositivo(boolean positivo) {
		this.positivo = positivo;
		return this;
	}
	
}
