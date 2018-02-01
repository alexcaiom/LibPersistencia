package br.com.waiso.persistencia.model.escolaridade;

import br.com.massuda.alexander.persistencia.jdbc.anotacoes.Tabela;
import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;
import br.com.waiso.persistencia.model.Endereco;

@Tabela(nome="tbl_instituicao_de_ensino")
public class InstituicaoDeEnsino extends EntidadeModelo {

	private String nome;
	private Endereco endereco;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
}
