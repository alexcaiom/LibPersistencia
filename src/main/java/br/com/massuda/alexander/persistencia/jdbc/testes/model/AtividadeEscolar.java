package br.com.massuda.alexander.persistencia.jdbc.testes.model;

import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;

public class AtividadeEscolar extends EntidadeModelo {

	private String nome;
	private String descricao;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
