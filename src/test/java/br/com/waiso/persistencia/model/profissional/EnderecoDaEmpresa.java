package br.com.waiso.persistencia.model.profissional;

import br.com.massuda.alexander.persistencia.jdbc.anotacoes.Tabela;
import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;
import br.com.waiso.persistencia.model.Endereco;
import br.com.waiso.persistencia.model.Usuario;

@Tabela(nome="tbl_endereco_usuario")
public class EnderecoDaEmpresa extends EntidadeModelo {
	
	private Usuario usuario;
	private Endereco endereco;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
}
