package br.com.massuda.alexander.persistencia.jdbc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MetodoDeEntidade {

	private boolean chavePrimaria;
	private boolean chaveEstrangeira;
	private Field campoDoMetodo;
	private Method metodo;
	private Object obj;
	/**
	 * @param chavePrimaria
	 * @param chaveEstrangeira
	 * @param metodo
	 */
	public MetodoDeEntidade(boolean chavePrimaria, boolean chaveEstrangeira,
			Method metodo) {
		this.chavePrimaria = chavePrimaria;
		this.chaveEstrangeira = chaveEstrangeira;
		this.metodo = metodo;
	}
	/**
	 * @param chavePrimaria
	 * @param chaveEstrangeira
	 * @param metodo
	 */
	public MetodoDeEntidade(boolean chavePrimaria, boolean chaveEstrangeira,
			Method metodo, Object obj) {
		this.chavePrimaria = chavePrimaria;
		this.chaveEstrangeira = chaveEstrangeira;
		this.metodo = metodo;
		this.setObj(obj);
	}
	
	public boolean isChavePrimaria() {
		return chavePrimaria;
	}
	public void setChavePrimaria(boolean chavePrimaria) {
		this.chavePrimaria = chavePrimaria;
	}
	public boolean isChaveEstrangeira() {
		return chaveEstrangeira;
	}
	public void setChaveEstrangeira(boolean chaveEstrangeira) {
		this.chaveEstrangeira = chaveEstrangeira;
	}
	public Method get() {
		return metodo;
	}
	public void set(Method metodo) {
		this.metodo = metodo;
	}
	
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Field getCampoDoMetodo() {
		return campoDoMetodo;
	}
	public void setCampoDoMetodo(Field campoDoMetodo) {
		this.campoDoMetodo = campoDoMetodo;
	}
	public Object chamar(Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return this.metodo.invoke(getObj(), args);
	}	
	
	public Object chamar(Object o) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return this.metodo.invoke(o);
	}	
	
	public Object chamar(Object o, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return this.metodo.invoke(o, args);
	}	
}
