package br.com.massuda.alexander.persistencia.jdbc.testes.model;

import br.com.massuda.alexander.persistencia.jdbc.anotacoes.Tabela;
import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;

@Tabela(nome="tbl_permissao")
public class Permissao extends EntidadeModelo {

	private Usuario usuario;
	private Funcionalidade funcionalidade;
	private Boolean acessoEscrita;
	private Boolean acessoGravacao;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public Permissao setUsuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}
	public Funcionalidade getFuncionalidade() {
		return funcionalidade;
	}
	public Permissao setFuncionalidade(Funcionalidade funcionalidade) {
		this.funcionalidade = funcionalidade;
		return this;
	}
	public Boolean isAcessoEscrita() {
		return acessoEscrita;
	}
	public Permissao setAcessoEscrita(Boolean acessoEscrita) {
		this.acessoEscrita = acessoEscrita;
		return this;
	}
	public Boolean isAcessoGravacao() {
		return acessoGravacao;
	}
	public Permissao setAcessoGravacao(Boolean acessoGravacao) {
		this.acessoGravacao = acessoGravacao;
		return this;
	}
	@Override
	public String toString() {
		return "O [usuario=" + usuario + "] tem Permissao de acesso a funcionalidade=["
				+ funcionalidade + "] de Escrita=" + acessoEscrita
				+ " e Gravacao=" + acessoGravacao + ", id=" + id + "]";
	}
	
}
