package br.com.waiso.persistencia.model.profissional;

import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;

public class Segmento extends EntidadeModelo {

	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Segmento [nome=" + nome + ", id=" + id + "]";
	}
	
}
