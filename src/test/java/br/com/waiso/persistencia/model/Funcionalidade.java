package br.com.waiso.persistencia.model;

import br.com.massuda.alexander.persistencia.jdbc.anotacoes.Tabela;
import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;

@Tabela(nome="tbl_funcionalidade")
public class Funcionalidade extends EntidadeModelo {

	private String nome;
	private String acesso;
	private Modulo modulo;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getAcesso() {
		return acesso;
	}
	public void setAcesso(String acesso) {
		this.acesso = acesso;
	}
	public Modulo getModulo() {
		return modulo;
	}
	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}
	@Override
	public String toString() {
		return "Funcionalidade [nome=" + nome + ", acesso=" + acesso
				+ ", modulo=" + modulo + ", id=" + id + "]";
	}
}
