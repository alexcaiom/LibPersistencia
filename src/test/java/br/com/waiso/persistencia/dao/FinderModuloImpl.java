package br.com.waiso.persistencia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.GeradorSQLBean;
import br.com.massuda.alexander.persistencia.jdbc.utils.TipoOperacao;
import br.com.waiso.persistencia.model.Modulo;
import br.com.waiso.persistencia.model.Sistema;

public class FinderModuloImpl extends Finder<Modulo> {

	public FinderModuloImpl() {
		this(TipoOperacao.NORMAL);
	}
	
	public FinderModuloImpl(TipoOperacao tipo) {
		super(Modulo.class);
		tipoOperacao = tipo;
	}

	public Modulo pesquisar(Long id) {
		Modulo o;
		try {
			o = (Modulo) entidade.newInstance();
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
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public List<Modulo> listar() {
		List<Modulo> objs = new ArrayList<Modulo>();
		String sql = GeradorSQLBean.getInstancia(entidade).getComandoSelecao();
		try {
			resultados = pesquisarSemParametro(sql);
			
			while (resultados.next()) {
				Modulo o = entidade.newInstance();
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
	
	public void preencher(Modulo o, ResultSet resultado) throws SQLException {
		if (existe(o) && existe(resultado) && !resultado.isClosed()) {
			super.preencher(o, resultado);
			
			Long idSistema = o.getSistema().getId();
			Sistema sistemaAssociado = new FinderSistemaImpl().pesquisar(idSistema);
			o.setSistema(sistemaAssociado);
		}
	}

	public void preencher(Modulo o) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			super.preencher(o);
			
			if (existe(o.getSistema().getId())) {
				Long idSistema = o.getSistema().getId();
				Sistema sistemaAssociado = new FinderSistemaImpl().pesquisar(idSistema);
				o.setSistema(sistemaAssociado);
			}
		}
	}
}
