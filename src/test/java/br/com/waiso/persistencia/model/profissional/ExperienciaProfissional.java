package br.com.waiso.persistencia.model.profissional;

import java.util.Calendar;

import br.com.massuda.alexander.persistencia.jdbc.anotacoes.Tabela;
import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;
import br.com.waiso.persistencia.model.UsuarioNivelVisualizacao;

@Tabela(nome="tbl_experiencia_profissional")
public class ExperienciaProfissional extends EntidadeModelo {

	private Empresa empresa;
	private Cargo cargo;
	private Calendar inicio;
	private Calendar fim;
	private UsuarioNivelVisualizacao nivelDeAcesso; 
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
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
	public UsuarioNivelVisualizacao getNivelDeAcesso() {
		return nivelDeAcesso;
	}
	public void setNivelDeAcesso(UsuarioNivelVisualizacao nivelDeAcesso) {
		this.nivelDeAcesso = nivelDeAcesso;
	}
	
}
