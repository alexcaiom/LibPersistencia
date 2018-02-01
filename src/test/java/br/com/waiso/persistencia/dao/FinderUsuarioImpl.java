package br.com.waiso.persistencia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.GeradorSQLBean;
import br.com.massuda.alexander.persistencia.jdbc.utils.TipoOperacao;
import br.com.waiso.framework.exceptions.ErroUsuario;
import br.com.waiso.persistencia.model.Endereco;
import br.com.waiso.persistencia.model.Perfil;
import br.com.waiso.persistencia.model.RedeSocial;
import br.com.waiso.persistencia.model.Usuario;
import br.com.waiso.persistencia.model.escolaridade.Escolaridade;

public class FinderUsuarioImpl extends Finder<Usuario> {

	public FinderUsuarioImpl() {
		super(Usuario.class);
		tipoOperacao = TipoOperacao.PESQUISA_COM_MULTIPLAS_TABELAS;
	}
	
	public FinderUsuarioImpl(TipoOperacao tipoOperacao) {
		super(Usuario.class);
		this.tipoOperacao = tipoOperacao;
	}
	

	public List<Usuario> pesquisarPorNomeComo(String nome) {
		List<Usuario> usuarios = new ArrayList<>();
		StringBuilder sql = new StringBuilder(GeradorSQLBean.getInstancia(entidade).getComandoSelecao());
			sql.append(" where nome like ?");
			
			try {
				comandoPreparado = novoComandoPreparado(sql.toString());
				comandoPreparado.setString(1, "%"+nome+"%");
				
				resultados = pesquisarComParametros(comandoPreparado);
				
				while (resultados.next()) {
					Usuario u = new Usuario();
					preencher(u);
					usuarios.add(u);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return usuarios;
	}

	public Usuario pesquisar(Long id) {
		Usuario u = new Usuario();
		String sql = GeradorSQLBean.getInstancia(Usuario.class).getComandoSelecao();
		
		sql += "\n where id = ?";
		
		if (tipoOperacao == TipoOperacao.NORMAL) {
			try {
				comandoPreparado = novoComandoPreparado(sql);
				comandoPreparado.setLong(1, id);
				
				resultados = pesquisarComParametros(comandoPreparado);
				
				if (resultados.next()) {
					super.preencher(u);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					fecharObjetosDeComandoEPesquisa(null, comandoPreparado, resultados);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			PreparedStatement comandoPreparado = null;
			ResultSet resultado = null;
			try {
				comandoPreparado = novoComandoPreparado(sql);
				comandoPreparado.setLong(1, id);
				
				resultado = pesquisarComParametros(comandoPreparado);
				
				if (resultado.next()) {
					super.preencher(u, resultado);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					fecharObjetosDeComandoEPesquisa(null, comandoPreparado, resultado);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return u;
	}
	
	public Usuario pesquisarPorLogin(String login) {
		Usuario u = null;
		String sql = GeradorSQLBean.getInstancia(Usuario.class).getComandoSelecao();
		
		sql += "\n where login = ?";
		
		if (tipoOperacao == TipoOperacao.NORMAL) {
			try {
				comandoPreparado = novoComandoPreparado(sql);
				comandoPreparado.setString(1, login);
				
				resultados = pesquisarComParametros(comandoPreparado);
				
				if (resultados.next()) {
					u = new Usuario();
					preencher(u);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					fecharObjetosDeComandoEPesquisa(null, comandoPreparado, resultados);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			PreparedStatement comandoPreparado = null;
			ResultSet resultado = null;
			try {
				comandoPreparado = novoComandoPreparado(sql);
				comandoPreparado.setString(1, login);
				
				resultado = pesquisarComParametros(comandoPreparado);
				
				if (resultado.next()) {
					u = new Usuario();
					preencher(u, resultado);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					fecharObjetosDeComandoEPesquisa(null, comandoPreparado, resultado);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return u;
	}
	
	public List<Usuario> pesquisarPorLoginComo(String login) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		String sql = GeradorSQLBean.getInstancia(Usuario.class).getComandoSelecao();
		
		sql += "\n where login like '% ? %'";
		
		if (tipoOperacao == TipoOperacao.NORMAL) {
			try {
				comandoPreparado = novoComandoPreparado(sql);
				comandoPreparado.setString(1, login);
				
				resultados = pesquisarComParametros(comandoPreparado);
				
				while (resultados.next()) {
					Usuario u = new Usuario();
					super.preencher(u);
					usuarios.add(u);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					fecharObjetosDeComandoEPesquisa(null, comandoPreparado, resultados);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			PreparedStatement comandoPreparado = null;
			ResultSet resultado = null;
			try {
				comandoPreparado = novoComandoPreparado(sql);
				comandoPreparado.setString(1, login);
				
				resultado = pesquisarComParametros(comandoPreparado);
				
				while (resultado.next()) {
					Usuario u = new Usuario();
					super.preencher(u, resultado);
					usuarios.add(u);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					fecharObjetosDeComandoEPesquisa(null, comandoPreparado, resultado);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return usuarios;
	}

	public List<Usuario> listar() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		String sql = GeradorSQLBean.getInstancia(Usuario.class).getComandoSelecao();
		try {
			resultados = pesquisarSemParametro(sql);
			
			while (resultados.next()) {
				Usuario u = new Usuario();
				preencher(u);
				usuarios.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ErroUsuario(e);
		} finally {
			try {
				fechar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return usuarios;
	}
	
	public void preencher(Usuario o, ResultSet resultados) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			super.preencher(o, resultados);
			if (existe(o.getPerfil())) {
				FinderPerfilImpl finderPerfil = new FinderPerfilImpl(TipoOperacao.OPERACAO_COMPLEXA);
				Perfil perfil = finderPerfil.pesquisar(o.getPerfil().getId());
				o.setPerfil(perfil);
			}
			if (existe(o.getNaturalidade())) {
				FinderEnderecoImpl finderEndereco = new FinderEnderecoImpl();
				Endereco naturalidade = finderEndereco.pesquisar(o.getNaturalidade().getId());
				o.setNaturalidade(naturalidade);
			}
			preencherRedesSociais(o);
			preencherEnderecos(o);
			preencherEscolaridades(o);
		}
	}

	public void preencher(Usuario o) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			super.preencher(o);
			if (existe(o.getPerfil())) {
				FinderPerfilImpl finderPerfil = new FinderPerfilImpl(TipoOperacao.OPERACAO_COMPLEXA);
				Perfil perfil = finderPerfil.pesquisar(o.getPerfil().getId());
				o.setPerfil(perfil);
			}
			if (existe(o.getNaturalidade()) && existe(o.getNaturalidade().getId())) {
				FinderEnderecoImpl finderEndereco = new FinderEnderecoImpl();
				Endereco naturalidade = finderEndereco.pesquisar(o.getNaturalidade().getId());
				o.setNaturalidade(naturalidade);
			}
			preencherRedesSociais(o);
			preencherEnderecos(o);
			preencherEscolaridades(o);
		}
	}

	private void preencherRedesSociais(Usuario o) {
		FinderRedeSocial finderRedeSocial = new FinderRedeSocial();
		List<RedeSocial> redesSociais = finderRedeSocial.listarPorUsuario(o.getId());
		o.setRedesSociais(redesSociais);
	}
	
	private void preencherEnderecos(Usuario o) {
		FinderEnderecoImpl finderEndereco = new FinderEnderecoImpl();
		List<Endereco> enderecos = finderEndereco.listarPorUsuario(o.getId());
		o.setEnderecos(enderecos);
	}
	
	private void preencherEscolaridades(Usuario o) {
		FinderEscolaridadeImpl finderEscolaridade = new FinderEscolaridadeImpl();
		List<Escolaridade> escolaridades = finderEscolaridade.listarPorUsuario(o.getId());
		o.setEscolaridades(escolaridades);
	}
}
