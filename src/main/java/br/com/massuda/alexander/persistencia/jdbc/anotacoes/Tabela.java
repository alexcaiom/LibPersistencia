package br.com.massuda.alexander.persistencia.jdbc.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Tabela {

	public String nome();
	
}