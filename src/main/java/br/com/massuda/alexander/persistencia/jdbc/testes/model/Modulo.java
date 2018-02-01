package br.com.massuda.alexander.persistencia.jdbc.testes.model;
import br.com.massuda.alexander.persistencia.jdbc.anotacoes.Tabela;
import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;

@Tabela(nome="tbl_modulo")
public class Modulo extends EntidadeModelo {

	private String nome;
	private Sistema sistema;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Sistema getSistema() {
		return sistema;
	}
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	@Override
	public String toString() {
		return "Modulo [nome=" + nome + ", sistema=" + sistema + "]";
	}
}