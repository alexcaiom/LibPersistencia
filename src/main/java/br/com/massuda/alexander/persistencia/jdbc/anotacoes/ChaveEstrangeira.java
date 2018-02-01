package br.com.massuda.alexander.persistencia.jdbc.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ChaveEstrangeira {

	public String nome() default "NULL";
	public boolean anulavel() default true;
	public boolean estaEmOutraTabela() default false;
	
}