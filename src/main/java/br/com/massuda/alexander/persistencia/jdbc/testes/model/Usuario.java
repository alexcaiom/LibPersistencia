package br.com.massuda.alexander.persistencia.jdbc.testes.model;

import java.util.Calendar;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.anotacoes.Tabela;

@Tabela(nome="tbl_usuario")
public class Usuario extends Pessoa {

	private String login;
	private String senha;
	private Calendar dataDeCriacao;
	private RespostaUsuarioAutenticacao status;
	private int contadorSenhaInvalida;
	private Perfil perfil;
//	private List<GrupoDeCirculos> grupo; 
	private List<RedeSocial> redesSociais;
	private List<Endereco> enderecos;
	private Endereco naturalidade;
	private List<Telefone> telefones;
	private List<Escolaridade> historicoAcademico;
//	private List<ExperienciaProfissional> historicoProfissional;
	private List<Foto> fotos;
	
	public Usuario() {
		this.tipo = TipoPessoa.PESSOA_FISICA;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Calendar getDataDeCriacao() {
		return dataDeCriacao;
	}
	public void setDataDeCriacao(Calendar dataDeCriacao) {
		this.dataDeCriacao = dataDeCriacao;
	}
	public RespostaUsuarioAutenticacao getStatus() {
		return status;
	}
	public void setStatus(RespostaUsuarioAutenticacao status) {
		this.status = status;
	}
	public int getContadorSenhaInvalida() {
		return contadorSenhaInvalida;
	}
	public void setContadorSenhaInvalida(int contadorSenhaInvalida) {
		this.contadorSenhaInvalida = contadorSenhaInvalida;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<RedeSocial> getRedesSociais() {
		return redesSociais;
	}

	public void setRedesSociais(List<RedeSocial> redesSociais) {
		this.redesSociais = redesSociais;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Endereco getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(Endereco naturalidade) {
		this.naturalidade = naturalidade;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public List<Escolaridade> getHistoricoAcademico() {
		return historicoAcademico;
	}

	public void setHistoricoAcademico(List<Escolaridade> historicoAcademico) {
		this.historicoAcademico = historicoAcademico;
	}

//	public List<ExperienciaProfissional> getHistoricoProfissional() {
//		return historicoProfissional;
//	}
//
//	public void setHistoricoProfissional(
//			List<ExperienciaProfissional> historicoProfissional) {
//		this.historicoProfissional = historicoProfissional;
//	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}

	@Override
	public String toString() {
		return "Usuario [login=" + login 
				+ ", id=" + id 
				+ ", nome=" + nome
				+ ", senha=" + senha
				+ ", dataDeCriacao=" + dataDeCriacao 
				+ ", status=" + status
				+ ", contadorSenhaInvalida=" + contadorSenhaInvalida
				+ ", perfil=" + perfil 
				+ ", redesSociais=" + redesSociais
				+ ", enderecos=" + enderecos 
				+ ", naturalidade=" + naturalidade
				+ ", telefones=" + telefones 
				+ ", historicoAcademico=" + historicoAcademico 
//				+ ", historicoProfissional=" + historicoProfissional 
				+ ", dataDeNascimento=" + dataDeNascimento 
				+ ", email=" + email
				+ ", identificacao=" + identificacao 
				+ ", fotos=" + fotos 
				+ ", tipo=" + tipo + "]";
	}


}
