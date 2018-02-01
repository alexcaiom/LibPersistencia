package br.com.massuda.alexander.persistencia.jdbc.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Coluna {

	public String nome() default "NULL";
	public boolean anulavel() default true;
	public boolean unica() default false;
	public int tamanho() default 0;
	public boolean deveSerPersistida() default true;
	
}