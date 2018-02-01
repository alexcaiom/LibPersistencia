package br.com.massuda.alexander.persistencia.jdbc.model;

import br.com.massuda.alexander.persistencia.jdbc.anotacoes.ChavePrimaria;

public class EntidadeModelo {

	@ChavePrimaria(autoIncremental=true)
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
