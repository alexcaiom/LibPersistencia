package br.com.waiso.persistencia.test;

import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.massuda.alexander.persistencia.jdbc.utils.TipoOperacao;
import br.com.waiso.framework.abstratas.Classe;
import br.com.waiso.persistencia.dao.FinderUsuarioImpl;
import br.com.waiso.persistencia.dao.UsuarioDAOImpl;
import br.com.waiso.persistencia.model.Perfil;
import br.com.waiso.persistencia.model.Usuario;

public class UsuarioTest {

	FinderUsuarioImpl finder = new FinderUsuarioImpl();
	UsuarioDAOImpl dao = new UsuarioDAOImpl();
	String login = "usuario1";
	long id = 0;
	
	@Before
	public void init() {
		finder.tipoOperacao = TipoOperacao.PESQUISA_COM_MULTIPLAS_TABELAS;
	}
	
//	@Before
	public void testInclusao() {
		Usuario usuario = new Usuario();
		Usuario pesquisarPorLogin = finder.pesquisarPorLogin(login);
		if (Classe.naoExiste(pesquisarPorLogin)) {
			usuario.setLogin(login);
			String senha = "senha1";
			usuario.setSenha(senha);
			usuario.setContadorSenhaInvalida(0);
			usuario.setDataDeCriacao(GregorianCalendar.getInstance());
			Perfil perfil = new Perfil();
			perfil.setId(2l);
			usuario.setPerfil(perfil);
			String email = "usuario1@gmail.com";
			usuario.setEmail(email);
			String identificacao = "00011122233";
			usuario.setIdentificacao(identificacao);
			String nome = "Usuario 1";
			usuario.setNome(nome);
			
			usuario = dao.incluir(usuario);
			
		}
		
		
		System.out.println(usuario);
		id = usuario.getId();
	}
	
//	@Test
	public void testEdicao() {
		login = "usuario3";
		Usuario usuario = getUsuarioPorLogin(login);
		
		String senha = "senha2";
		usuario.setSenha(senha);
		usuario.setContadorSenhaInvalida(usuario.getContadorSenhaInvalida()+1);
		usuario.setDataDeCriacao(GregorianCalendar.getInstance());
		String email = "usuario2@gmail.com";
		usuario.setEmail(email);
		String identificacao = "00011122233";
		usuario.setIdentificacao(identificacao);
		String nome = "Usuario 2";
		usuario.setNome(nome);
		
		dao.editar(usuario);
		login = usuario.getLogin();
	}
	
	//@Test
	public void testPesquisarPorLogin() {
		String login = "admin";
		Usuario usuario = getUsuarioPorLogin(login);
		System.out.println(usuario);
	}

	private Usuario getUsuarioPorLogin(String login) {
		return finder.pesquisarPorLogin(login);
	}
	
	//@Test
	public void testListar() {
		List<Usuario> usuarios = finder.listar();
		for (Usuario usuario : usuarios) {
			System.out.println(usuario);
			System.out.println("----------------");
		}
	}
	
//	@Test
	public void testExcluir() {
		Usuario u = getUsuarioPorLogin(login);
		dao.excluir(u);
	}
	
//	@Test
	public void testExcluirPorId() {
		Usuario u = getUsuarioPorId(2l);
		dao.excluir(u);
	}

	private Usuario getUsuarioPorId(long id) {
		return finder.pesquisar(id);
	}
	
}
