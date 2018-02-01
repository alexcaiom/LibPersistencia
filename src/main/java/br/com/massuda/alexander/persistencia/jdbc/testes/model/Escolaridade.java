package br.com.massuda.alexander.persistencia.jdbc.testes.model;

import java.util.Calendar;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;

public class Escolaridade extends EntidadeModelo {

	private String nomeDoCurso;
	private InstituicaoDeEnsino instituicao;
	private Calendar inicio;
	private Calendar fim;
	private AreaDeEstudo area;
	private List<AtividadeEscolar> atividades;
	private GrauDeEscolaridade escolaridade;
	
	public String getNomeDoCurso() {
		return nomeDoCurso;
	}
	public void setNomeDoCurso(String nomeDoCurso) {
		this.nomeDoCurso = nomeDoCurso;
	}
	public InstituicaoDeEnsino getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(InstituicaoDeEnsino instituicao) {
		this.instituicao = instituicao;
	}
	public Calendar getInicio() {
		return inicio;
	}
	public void setInicio(Calendar inicio) {
		this.inicio = inicio;
	}
	public Calendar getFim() {
		return fim;
	}
	public void setFim(Calendar fim) {
		this.fim = fim;
	}
	public AreaDeEstudo getArea() {
		return area;
	}
	public void setArea(AreaDeEstudo area) {
		this.area = area;
	}
	public List<AtividadeEscolar> getAtividades() {
		return atividades;
	}
	public void setAtividades(List<AtividadeEscolar> atividades) {
		this.atividades = atividades;
	}
	public GrauDeEscolaridade getEscolaridade() {
		return escolaridade;
	}
	public void setEscolaridade(GrauDeEscolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}
	@Override
	public String toString() {
		return "Escolaridade [nomeDoCurso=" + nomeDoCurso + ", instituicao="
				+ instituicao + ", inicio=" + inicio + ", fim=" + fim
				+ ", area=" + area + ", atividades=" + atividades
				+ ", escolaridade=" + escolaridade + ", id=" + id + "]";
	}
	
}
