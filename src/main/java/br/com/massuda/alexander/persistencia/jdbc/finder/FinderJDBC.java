package br.com.massuda.alexander.persistencia.jdbc.finder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.GeradorSQLBean;
import br.com.massuda.alexander.persistencia.jdbc.utils.TipoOperacao;


public class FinderJDBC<T> extends FinderJDBCUtils<T> {

	public FinderJDBC(Class<T> entidade) {
		super(entidade);
	}

	public T pesquisar(Long id) {
		T o;
		try {
			o = (T) entidade.newInstance();
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
	
	public List<T> pesquisarPorNomeComo(String nome) {
		List<T> objs = new ArrayList<>();
		StringBuilder sql = new StringBuilder(GeradorSQLBean.getInstancia(entidade).getComandoSelecao());
			sql.append(" where nome like ?");
			
			try {
				comandoPreparado = novoComandoPreparado(sql.toString());
				comandoPreparado.setString(1, "%"+nome+"%");
				
				resultados = pesquisarComParametros(comandoPreparado);
				
				while (resultados.next()) {
					T u = entidade.newInstance();
					preencher(u);
					objs.add(u);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		return objs;
	}

	public List<T> listar() {
		List<T> objs = new ArrayList<T>();
		String sql = GeradorSQLBean.getInstancia(entidade).getComandoSelecao();
		try {
			resultados = pesquisarSemParametro(sql);
			
			while (resultados.next()) {
				T o = entidade.newInstance();
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
	
	public List<T> pesquisar(List<Filtro> filtros) {
		List<T> objs = new ArrayList<T>();
		String sql = GeradorSQLBean.getInstancia(entidade).getComandoSelecao();
		try {
			sql += parametrizarPesquisa(filtros);
			comandoPreparado = novoComandoPreparado(sql);
			
			
			resultados = pesquisarComParametros(comandoPreparado);
			
			while (resultados.next()) {
				T o = entidade.newInstance();
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

	private String parametrizarPesquisa(List<Filtro> filtros) {
		StringBuilder clausulaFiltro = new StringBuilder("\n where ");
		
		boolean primeiro = true;
		for (Filtro filtro : filtros) {
			if (!primeiro) {
				clausulaFiltro.append("\n and ");
			}
			
			clausulaFiltro.append(GeradorSQLBean.getInstancia(entidade).getCampoNormal(filtro.getAtributo()))
					.append(filtro.getTipo().getTextoCondicional()).append("?");
			
			primeiro = false;
		}
		
		return clausulaFiltro.toString();
	}
	

	protected void preencherParametrosDeComandoDeInsercao(PreparedStatement comandoPreparado, List<Filtro> filtros) throws SQLException {
		for (int i = 0; i < filtros.size(); i++) {
			Filtro filtro = filtros.get(i);
			Object valor = filtro.getValor();
			Class tipo = filtro.getAtributo().getType();
			setParametroDeComandoPreparadoPorTipo(comandoPreparado, tipo, valor, i);
		}
	}	
	
	public void setParametroDeComandoPreparadoPorTipo(PreparedStatement comandoPreparado, Class tipo, Object valor, int indice) throws SQLException {
		TipoDeDado tipoDeDado = TipoDeDado.get(tipo);
		switch (tipoDeDado) {
		case INTEGER:
			comandoPreparado.setInt(indice, 		(existe(valor) ? (Integer) valor : null));
			break;
		case LONG:
			comandoPreparado.setLong(indice, (Long) valor);
			break;
		case DOUBLE:
			comandoPreparado.setDouble(indice, 		(existe(valor) ? (Double) valor : null));
			break;
		case FLOAT:
			comandoPreparado.setFloat(indice, 		(existe(valor) ? (Float) valor : null));
			break;
		case BOOLEAN:
			int v = (existe(valor) ? (Boolean) valor : false) ? 1 : 0;
			comandoPreparado.setInt(indice, 		v);
			break;
		case STRING:
			comandoPreparado.setString(indice, 		(String) valor);
			break;
		case CALENDAR:
			java.sql.Date dataBD = null;
			if (tipo == Calendar.class) {
				Calendar data = (Calendar)valor;
				dataBD = new java.sql.Date(data.getTimeInMillis());
			} else if (tipo == Date.class) {
				Date data = (Date)valor;
				dataBD = new java.sql.Date(data.getTime());
			}
			comandoPreparado.setDate(indice, dataBD);
		case ENUM:
			comandoPreparado.setString(indice, 		(existe(valor) ? ((Enum)valor).name() : null));
		default:
			break;
		}
	}
}
