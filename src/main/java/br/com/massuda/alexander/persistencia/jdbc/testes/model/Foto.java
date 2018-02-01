package br.com.massuda.alexander.persistencia.jdbc.testes.model;

import java.util.Calendar;

import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;

public class Foto extends EntidadeModelo {

	private String descricao;
	private String caminho;
	private Calendar data;
	
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	
	
}
