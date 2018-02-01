package br.com.waiso.persistencia.model;

import java.util.Calendar;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.anotacoes.ChaveEstrangeira;
import br.com.massuda.alexander.persistencia.jdbc.anotacoes.Tabela;
import br.com.waiso.persistencia.model.escolaridade.Escolaridade;
import br.com.waiso.persistencia.model.profissional.ExperienciaProfissional;

@Tabela(nome="tbl_usuario")
public class Usuario extends Pessoa {

	private String login;
	private String senha;
	private Calendar dataDeCriacao;
//	private RespostaUsuarioAutenticacao status;
	private int contadorSenhaInvalida;
	private Perfil perfil;
//	@ChaveEstrangeira(estaEmOutraTabela=true)
//	private List<GrupoDeCirculos> grupo; 
	@ChaveEstrangeira(estaEmOutraTabela=true)
	private List<RedeSocial> redesSociais;
	@ChaveEstrangeira(estaEmOutraTabela=true)
	private List<Endereco> enderecos;
	private Endereco naturalidade;
	@ChaveEstrangeira(estaEmOutraTabela=true)
	private List<Telefone> telefones;
	@ChaveEstrangeira(estaEmOutraTabela=true)
	private List<Escolaridade> escolaridades;
	@ChaveEstrangeira(estaEmOutraTabela=true)
	private List<ExperienciaProfissional> historicoProfissional;
	
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
	/*public RespostaUsuarioAutenticacao getStatus() {
		return status;
	}
	public void setStatus(RespostaUsuarioAutenticacao status) {
		this.status = status;
	}*/
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

	public List<Escolaridade> getEscolaridades() {
		return escolaridades;
	}

	public void setEscolaridades(List<Escolaridade> escolaridades) {
		this.escolaridades = escolaridades;
	}

	public List<ExperienciaProfissional> getHistoricoProfissional() {
		return historicoProfissional;
	}

	public void setHistoricoProfissional(
			List<ExperienciaProfissional> historicoProfissional) {
		this.historicoProfissional = historicoProfissional;
	}

	@Override
	public String toString() {
		return "Usuario [login=" + login 
				+ "\n, id=" + id 
				+ "\n, nome=" + nome
				+ "\n, senha=" + senha
				+ "\n, dataDeCriacao=" + dataDeCriacao 
//				+ "\n, status=" + status
				+ "\n, contadorSenhaInvalida=" + contadorSenhaInvalida
				+ "\n, perfil=" + perfil 
				+ "\n, redesSociais=" + redesSociais
				+ "\n, enderecos=" + enderecos 
				+ "\n, naturalidade=" + naturalidade
				+ "\n, telefones=" + telefones 
				+ "\n, escolaridade=" + escolaridades
//				+ "\n, historicoProfissional=" + historicoProfissional 
				+ "\n, dataDeNascimento=" + dataDeNascimento 
				+ "\n, email=" + email
				+ "\n, identificacao=" + identificacao 
				+ "\n, tipo=" + tipo + "]";
	}


}
