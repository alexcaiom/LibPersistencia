package br.com.massuda.alexander.persistencia.jdbc.testes.model;

import br.com.massuda.alexander.persistencia.jdbc.anotacoes.Tabela;
import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;

@Tabela(nome="tbl_perfil")
public class Perfil extends EntidadeModelo {

	private String nome;
	private NivelHierarquico nivel;
	
	public Perfil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param nome
	 * @param nivel
	 */
	public Perfil(String nome, NivelHierarquico nivel) {
		this.nome = nome;
		this.nivel = nivel;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public NivelHierarquico getNivel() {
		return nivel;
	}
	public void setNivel(NivelHierarquico nivel) {
		this.nivel = nivel;
	}

	@Override
	public String toString() {
		return "Perfil [nome=" + nome + ", nivel=" + nivel + "]";
	}
	
}
