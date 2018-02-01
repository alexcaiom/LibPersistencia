package br.com.massuda.alexander.persistencia.jdbc.testes.model;

import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;

public class GrupoDeCirculos extends EntidadeModelo{

	private String titulo;
	private String descricao;
	private List<Circulo> circulosDoGrupo;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<Circulo> getCirculosDoGrupo() {
		return circulosDoGrupo;
	}
	public void setCirculosDoGrupo(List<Circulo> circulosDoGrupo) {
		this.circulosDoGrupo = circulosDoGrupo;
	}
}
