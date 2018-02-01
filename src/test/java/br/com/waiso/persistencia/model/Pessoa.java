package br.com.waiso.persistencia.model;

import java.util.Calendar;

import br.com.massuda.alexander.persistencia.jdbc.anotacoes.ChavePrimaria;

public class Pessoa {

	@ChavePrimaria(autoIncremental=true)
	protected Long id;
	protected String nome;
	protected Calendar dataDeNascimento;
	protected String email;
	protected String identificacao;
	protected TipoPessoa tipo;
	
	public Long getId() {
		return id;
	}
	public Pessoa setId(Long id) {
		this.id = id;
		return this;
	}
	public String getNome() {
		return nome;
	}
	public Pessoa setNome(String nome) {
		this.nome = nome;
		return this;
	}
	public Calendar getDataDeNascimento() {
		return dataDeNascimento;
	}
	public Pessoa setDataDeNascimento(Calendar dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	public TipoPessoa getTipo() {
		return tipo;
	}
	public void setTipo(TipoPessoa tipo) {
		this.tipo = tipo;
	}
	
}
