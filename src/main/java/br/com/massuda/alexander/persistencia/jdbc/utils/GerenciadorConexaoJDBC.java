package br.com.massuda.alexander.persistencia.jdbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.waiso.framework.abstratas.Classe;

public class GerenciadorConexaoJDBC extends Classe {

	private static final String TIPO_CONEXAO_BD_JDBC = "jdbc";
	private static final String DRIVER_JDBC_MYSQL = "com.mysql.jdbc.Driver";
	private static Connection conexao = null;
	
	public static Connection novaConexao() throws SQLException, ClassNotFoundException{
		return novaConexaoEm(DRIVER_JDBC_MYSQL, TIPO_CONEXAO_BD_JDBC, ConstantesPersistencia.TIPO_BD_MySQL, ConstantesPersistencia.BD_CONEXAO_LOCAL, ConstantesPersistencia.BD_CONEXAO_PORTA, ConstantesPersistencia.BD_CONEXAO_NOME_BD,
				ConstantesPersistencia.BD_CONEXAO_USUARIO, ConstantesPersistencia.BD_CONEXAO_SENHA);
	}
	
	private static Connection novaConexaoEm(String caminhoClasseDriver, String tipoConexao, String nomeDoSGBD, String local, int porta, String nomeBD,
			String nomeUsuario, String senhaUsuario) throws SQLException, ClassNotFoundException{
		Class.forName (caminhoClasseDriver);
		String url = new StringBuilder().append(tipoConexao).append(":")
					.append(nomeDoSGBD).append("://")
					.append(local).append(":").append(porta).append("/").append(nomeBD).toString();
		return DriverManager.getConnection(url, nomeUsuario, senhaUsuario);
	}

	public static Connection getConexao() throws SQLException {
		if (naoExiste(conexao) || conexao.isClosed()) {
			try {
				setConexao(novaConexao());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return conexao;
	}

	public static void setConexao(Connection conexao) {
		GerenciadorConexaoJDBC.conexao = conexao;
	}
	
	public static void fecharConexao() throws SQLException{
		if (existe(conexao) && !conexao.isClosed()) {
			conexao.close();
			conexao = null;
		}
	}
	
}