package br.com.massuda.alexander.persistencia.jdbc.testes.dao;

import br.com.massuda.alexander.persistencia.jdbc.DAOGenericoJDBCImpl;
import br.com.massuda.alexander.persistencia.jdbc.utils.ConstantesPersistencia;
import br.com.waiso.framework.utilitario.IntegerUtils;
import br.com.waiso.framework.utilitario.StringUtils;

public abstract class DAOTeste<T> extends DAOGenericoJDBCImpl<T> {

	public DAOTeste(Class<T> entidade) {
		super(entidade);
		configurarBancoDeDadosDadosAcesso();
	}

	/**
	 * Caso a aplicacao esteja sozinha (Standalone),
	 * usamos uma configuracao padrao 
	 */
	public static void configurarBancoDeDadosDadosAcesso() {
		if (deveUsarConfiguracoesPadraoDeBanco()) {
			ConstantesPersistencia.BD_CONEXAO_LOCAL = ConstantesPersistenciaTeste.BANCO_DE_DADOS_LOCAL;
			ConstantesPersistencia.BD_CONEXAO_USUARIO = ConstantesPersistenciaTeste.BANCO_DE_DADOS_CONEXAO_USUARIO;
			ConstantesPersistencia.BD_CONEXAO_SENHA = ConstantesPersistenciaTeste.BANCO_DE_DADOS_CONEXAO_SENHA;
			ConstantesPersistencia.BD_CONEXAO_NOME_BD = ConstantesPersistenciaTeste.BANCO_DE_DADOS_NOME;
			ConstantesPersistencia.BD_CONEXAO_PORTA = ConstantesPersistenciaTeste.BANCO_DE_DADOS_LOCAL_PORTA;
					
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
