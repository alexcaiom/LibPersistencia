package br.com.massuda.alexander.persistencia.jdbc.testes.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.GeradorSQLBean;
import br.com.massuda.alexander.persistencia.jdbc.testes.model.Funcionalidade;
import br.com.massuda.alexander.persistencia.jdbc.testes.model.Permissao;
import br.com.massuda.alexander.persistencia.jdbc.testes.model.Usuario;
import br.com.massuda.alexander.persistencia.jdbc.utils.TipoOperacao;

public class FinderPermissaoImpl extends Finder<Permissao> {
	
	private FinderUsuarioImpl finderUsuario = new FinderUsuarioImpl();
	private FinderFuncionalidadeImpl finderFuncionalidade = new FinderFuncionalidadeImpl();
	
	public FinderPermissaoImpl() {
		super(Permissao.class);
		tipoOperacao = TipoOperacao.PESQUISA_COM_MULTIPLAS_TABELAS;
	}
	
	public Permissao pesquisarPorUsuario(Long id) {
		Permissao p = new Permissao();
		String sql = GeradorSQLBean.getInstancia(Permissao.class).getComandoSelecao();
		
		sql += "\n where usuario_id = ?";
		
		try {
			comandoPreparado = novoComandoPreparado(sql);
			comandoPreparado.setLong(1, id);
			
			resultados = pesquisarComParametros(comandoPreparado);
			
			if (resultados.next()) {
				preencher(p);
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
		
		
		return p;
	}
	
	public Permissao pesquisar(Long id) {
		Permissao p = new Permissao();
		String sql = GeradorSQLBean.getInstancia(Permissao.class).getComandoSelecao();
		
		sql += "\n where id = ?";
		
		try {
			comandoPreparado = novoComandoPreparado(sql);
			comandoPreparado.setLong(1, id);
			
			resultados = pesquisarComParametros(comandoPreparado);
			
			if (resultados.next()) {
				preencher(p);
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
		
		
		return p;
	}

	public List<Permissao> listar() {
		List<Permissao> permissoes = new ArrayList<Permissao>();
		String sql = GeradorSQLBean.getInstancia(Permissao.class).getComandoSelecao();
		try {
			resultados = pesquisarSemParametro(sql);
			
			while (resultados.next()) {
				Permissao p = new Permissao();
				preencher(p);
				permissoes.add(p);
			}
			
			tipoOperacao = TipoOperacao.NORMAL;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				fechar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return permissoes;
	}

	public void preencher(Permissao o) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			super.preencher(o);

			Long idUsuario = o.getUsuario().getId();
			Usuario usuarioAssociado = finderUsuario.pesquisar(idUsuario);
			
			Long idFuncionalidade = o.getFuncionalidade().getId();
			Funcionalidade funcionalidadeAssociada = finderFuncionalidade.pesquisar(idFuncionalidade);
			
			o.setUsuario(usuarioAssociado);
			o.setFuncionalidade(funcionalidadeAssociada);
		}
	}

}
