package br.com.waiso.persistencia.model;

import br.com.massuda.alexander.persistencia.jdbc.anotacoes.Tabela;
import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;

@Tabela(nome="tbl_rede_social")
public class RedeSocial extends EntidadeModelo {

	private String nome;
	private String token;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "RedeSocial [nome=" + nome + ", token=" + token + ", id=" + id
				+ "]";
	}
}
