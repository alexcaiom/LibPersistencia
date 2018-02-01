package br.com.massuda.alexander.persistencia.jdbc.utils;

public class ConstantesPersistencia {

	/**
	 * Banco de Dados
	 */
	public static final String TIPO_BD_MySQL = "mysql";
	public static final String TIPO_BD_Oracle = "orcl";
	public static final String TIPO_BD_PostGree = "postgree";
	
	/**
	 * Conexao
	 */
	public static String BD_CONEXAO_LOCAL = "localhost";
	public static int 	 BD_CONEXAO_PORTA = 3306;
	public static String BD_CONEXAO_NOME_BD = "sistema_permissoes";
	public static String BD_CONEXAO_USUARIO = "root";
	public static String BD_CONEXAO_SENHA = "";
	public static String BD_UNIDADE_PERSISTENCIA = "";
	
	/**
	 * Configuracoes de Conjunto de Conexoes (Pool)
	 */
	public static int QUANTIDADE_MAXIMA_DE_CONEXOES = 5;
	public static long TEMPO_SINCRONIZACAO = 1000;
	public static final long TEMPO_SINCRONIZACAO_EM_SEGUNDOS = 2;
	
	public static boolean BANCO_DE_DADOS_POOL_CONEXOES_ATIVO = false;
	public static final int BD_CONEXAO_NUMERO_TENTATIVAS = 5;
	
	public static final String BANCO_DE_DADOS_TESTE_LOCAL = "localhost";
	public static final int    BANCO_DE_DADOS_TESTE_CONEXAO_PORTA = 3306;
	public static final String BANCO_DE_DADOS_TESTE_CONEXAO_USUARIO = "root";
	public static final String BANCO_DE_DADOS_TESTE_CONEXAO_SENHA = "lifesgood";
	public static final String BANCO_DE_DADOS_TESTE_NOME = "alexcaio_"+br.com.massuda.alexander.persistencia.jdbc.utils.ConstantesPersistencia.BD_CONEXAO_NOME_BD+"test";
	
}