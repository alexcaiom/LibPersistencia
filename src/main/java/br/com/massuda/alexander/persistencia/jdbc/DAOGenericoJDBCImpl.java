package br.com.massuda.alexander.persistencia.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import br.com.massuda.alexander.persistencia.interfaces.IDAO;
import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;
import br.com.waiso.framework.abstratas.Classe;
import br.com.waiso.framework.exceptions.ErroUsuario;

public abstract class DAOGenericoJDBCImpl<T> extends DAOGenericoJDBCImplUtils<T> implements IDAO<T> {
	
	protected Class<T> entidade;
	
	public DAOGenericoJDBCImpl(Class<T> entidade) {
		this.entidade = entidade;
		this.possuiHeranca = verificaSePossuiHeranca(entidade);
	}

	public T incluir (T o) throws ErroUsuario {
		tipoCRUD = OperacaoCRUD.INSERCAO;
		String sql = GeradorSQLBean.getInstancia(o.getClass()).getComandoInsercao(o);
		log(sql);
		try {
			comandoPreparado = novoComandoPreparado(sql, RETORNAR_ID_GERADO);
			
			preencherParametrosDeComandoDeInsercao(comandoPreparado, o);

			ResultSet retornoChavesGeradas = executarOperacaoERetornarChavesGeradas(comandoPreparado);
			if (temAtributoIdEditavel(o)) {
				if (retornoChavesGeradas.next()) {
					Long chaveGerada = retornoChavesGeradas.getLong(1);
					setarId(o, chaveGerada, null);
				}
			}
			retornoChavesGeradas.close();
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			try {
				fechar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return o;
	}

	public void editar (T o) {

		String sql = GeradorSQLBean.getInstancia(entidade).getComandoAtualizacao(o);
		log(sql);
		try {
			comandoPreparado =  novoComandoPreparado(sql);
			preencherEdicoes(comandoPreparado, o);
			
			preencherFiltros(comandoPreparado, o, null);
			executarOperacaoParametrizada(comandoPreparado);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				fechar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void excluir(T o) throws ErroUsuario {
		if (Classe.naoExiste(o)) {
			throw new ErroUsuario("Objeto nao informado");
		}
		String sql = GeradorSQLBean.getInstancia(entidade).getComandoExclusao();
		sql += "\n where id = ?";
		log(sql);
		try {
			comandoPreparado = novoComandoPreparado(sql);
			preencherExclusao(comandoPreparado, o);
			executarOperacaoParametrizada(comandoPreparado);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				fechar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void excluir(long id) throws ErroUsuario {
		String sql = GeradorSQLBean.getInstancia(entidade).getComandoExclusao();
		sql += "\n where id = ?";
		log(sql);
		try {
			comandoPreparado = novoComandoPreparado(sql);
			comandoPreparado.setLong(1, id);
			executarOperacaoParametrizada(comandoPreparado);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				fechar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void excluir(EntidadeModelo objeto) throws ErroUsuario {
		verificaSeOModeloEstaPreenchido(objeto);
		
		excluir(objeto.getId());
	}


}
