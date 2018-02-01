package br.com.massuda.alexander.persistencia.jdbc.testes.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.GeradorSQLBean;
import br.com.massuda.alexander.persistencia.jdbc.testes.model.Perfil;
import br.com.massuda.alexander.persistencia.jdbc.testes.model.Usuario;
import br.com.massuda.alexander.persistencia.jdbc.utils.TipoOperacao;
import br.com.waiso.framework.exceptions.ErroUsuario;


public class FinderUsuarioImpl extends Finder<Usuario>  {

	public FinderUsuarioImpl() {
		super(Usuario.class);
		tipoOperacao = TipoOperacao.PESQUISA_COM_MULTIPLAS_TABELAS;
	}
	
	public FinderUsuarioImpl(TipoOperacao tipoOperacao) {
		super(Usuario.class);
		this.tipoOperacao = tipoOperacao;
	}
	

	@Override
	public List<Usuario> pesquisarPorNomeComo(String nome) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
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
				comandoPreparado.setString(1, login);
				
				resultado = pesquisarComParametros(comandoPreparado);
				
				if (resultado.next()) {
					u = new Usuario();
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

	public void preencher(Usuario o) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			super.preencher(o);
			if (existe(o.getPerfil())) {
				FinderPerfilImpl finderPerfil = new FinderPerfilImpl(TipoOperacao.PESQUISA_COM_MULTIPLAS_TABELAS);
				Perfil perfil = finderPerfil.pesquisar(o.getPerfil().getId());
				o.setPerfil(perfil);
			}
		}
	}

}
