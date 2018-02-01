package br.com.waiso.persistencia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.GeradorSQLBean;
import br.com.massuda.alexander.persistencia.jdbc.utils.TipoOperacao;
import br.com.waiso.persistencia.model.Usuario;
import br.com.waiso.persistencia.model.Funcionalidade;
import br.com.waiso.persistencia.model.Modulo;

public class FinderFuncionalidadeImpl extends Finder<Funcionalidade> {
	
	public FinderFuncionalidadeImpl() {
		this(TipoOperacao.NORMAL);
	}

	public FinderFuncionalidadeImpl(TipoOperacao tipo) {
		super(Funcionalidade.class);
		tipoOperacao = tipo;
	}
	

	public Funcionalidade pesquisar(Long id) {
		Funcionalidade o;
		try {
			o = (Funcionalidade) entidade.newInstance();
			String sql = GeradorSQLBean.getInstancia(entidade).getComandoSelecao();
			
			sql += "\n where id = ?";
			
			if (tipoOperacao == TipoOperacao.NORMAL) {
				try {
					comandoPreparado = novoComandoPreparado(sql);
					comandoPreparado.setLong(1, id);
					
					resultados = pesquisarComParametros(comandoPreparado);
					
					if (resultados.next()) {
						preencher(o);
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
						preencher(o, resultado);
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
			return o;
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	public List<Funcionalidade> pesquisarPorAcessoComo(Usuario usuario, String acesso) {
		List<Funcionalidade> funcionalidades = new ArrayList<>();
		String sql = GeradorSQLBean.getInstancia(entidade).getComandoSelecao();
		sql += "\n where acesso like ?";
		sql += "\n and usuario_id = ?";
		if (tipoOperacao == TipoOperacao.NORMAL) {
			try {
				comandoPreparado = novoComandoPreparado(sql);
				comandoPreparado.setString(1, "%"+acesso+"%");
				comandoPreparado.setLong(2, usuario.getId());
				resultados = pesquisarComParametros(comandoPreparado);
				if (resultados.next()) {
					Funcionalidade f = new Funcionalidade();
					super.preencher(f);
					funcionalidades.add(f);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					fechar();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			PreparedStatement comandoPreparado = null;
			ResultSet resultado = null;
			try {
				comandoPreparado = novoComandoPreparado(sql);
				comandoPreparado.setString(1, "%"+acesso+"%");
				comandoPreparado.setLong(2, usuario.getId());
				resultado = pesquisarComParametros(comandoPreparado);
				if (resultado.next()) {
					Funcionalidade f = new Funcionalidade();
					super.preencher(f, resultado);
					funcionalidades.add(f);
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
		
		
		
		return funcionalidades;
	}
	
	public List<Funcionalidade> pesquisarPorAcesso(Usuario usuario, String acesso) {
		List<Funcionalidade> funcionalidades = new ArrayList<>();
		String sql = GeradorSQLBean.getInstancia(entidade).getComandoSelecao();
		sql += "\n where acesso = ?";
		sql += "\n and usuario_id = ?";
		if (tipoOperacao == TipoOperacao.NORMAL) {
			try {
				comandoPreparado = novoComandoPreparado(sql);
				comandoPreparado.setString(1, acesso);
				comandoPreparado.setLong(2, usuario.getId());
				resultados = pesquisarComParametros(comandoPreparado);
				if (resultados.next()) {
					Funcionalidade f = new Funcionalidade();
					super.preencher(f);
					funcionalidades.add(f);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					fechar();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			PreparedStatement comandoPreparado = null;
			ResultSet resultado = null;
			try {
				comandoPreparado = novoComandoPreparado(sql);
				comandoPreparado.setString(1, acesso);
				comandoPreparado.setLong(2, usuario.getId());
				resultado = pesquisarComParametros(comandoPreparado);
				if (resultado.next()) {
					Funcionalidade f = new Funcionalidade();
					super.preencher(f, resultado);
					funcionalidades.add(f);
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
		
		
		
		return funcionalidades;
	}
	

	public List<Funcionalidade> listar() {
		List<Funcionalidade> objs = new ArrayList<Funcionalidade>();
		String sql = GeradorSQLBean.getInstancia(entidade).getComandoSelecao();
		try {
			resultados = pesquisarSemParametro(sql);
			
			while (resultados.next()) {
				Funcionalidade o = entidade.newInstance();
				preencher(o);
				objs.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			try {
				fechar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return objs;
	}
	
	public void preencher(Funcionalidade o, ResultSet resultado) throws SQLException {
		if (existe(o) && existe(resultado) && !resultado.isClosed()) {
			super.preencher(o, resultado);
			
			Long idModulo = o.getModulo().getId();
			Modulo m = new FinderModuloImpl(tipoOperacao).pesquisar(idModulo);
			o.setModulo(m);
		}
	}

	public void preencher(Funcionalidade o) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			super.preencher(o);
			
			Long idModulo = o.getModulo().getId();
			Modulo m = new FinderModuloImpl(tipoOperacao).pesquisar(idModulo);
			o.setModulo(m);
		}
	}

}
