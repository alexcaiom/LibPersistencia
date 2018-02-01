package br.com.waiso.persistencia.dao;

import br.com.massuda.alexander.persistencia.jdbc.DAOGenericoJDBCImpl;
import br.com.massuda.alexander.persistencia.jdbc.utils.ConstantesPersistencia;
import br.com.waiso.framework.utilitario.IntegerUtils;
import br.com.waiso.framework.utilitario.StringUtils;
import br.com.waiso.persistencia.utils.Constantes;

public abstract class DAO<T> extends DAOGenericoJDBCImpl<T> {

	public DAO(Class<T> entidade) {
		super(entidade);
		configurarBancoDeDadosDadosAcesso();
	}

	/**
	 * Caso a aplicacao esteja sozinha (Standalone),
	 * usamos uma configuracao padrao 
	 */
	public static void configurarBancoDeDadosDadosAcesso() {
		if (deveUsarConfiguracoesPadraoDeBanco()) {
			ConstantesPersistencia.BD_CONEXAO_LOCAL = Constantes.BANCO_DE_DADOS_LOCAL_PADRAO;
			ConstantesPersistencia.BD_CONEXAO_USUARIO = Constantes.BANCO_DE_DADOS_CONEXAO_USUARIO_PADRAO;
			ConstantesPersistencia.BD_CONEXAO_SENHA = Constantes.BANCO_DE_DADOS_CONEXAO_SENHA_PADRAO;
			ConstantesPersistencia.BD_CONEXAO_NOME_BD = Constantes.BANCO_DE_DADOS_NOME_PADRAO;
		}
	}

	private static boolean deveUsarConfiguracoesPadraoDeBanco() {
		return 
				StringUtils.isBlank(ConstantesPersistencia.BD_CONEXAO_LOCAL) &&
				IntegerUtils.isBlank(String.valueOf(ConstantesPersistencia.BD_CONEXAO_PORTA)) &&
				StringUtils.isBlank(ConstantesPersistencia.BD_CONEXAO_LOCAL) &&
				StringUtils.isBlank(ConstantesPersistencia.BD_CONEXAO_LOCAL) &&
				StringUtils.isBlank(ConstantesPersistencia.BD_CONEXAO_LOCAL)
				;
	}
}
