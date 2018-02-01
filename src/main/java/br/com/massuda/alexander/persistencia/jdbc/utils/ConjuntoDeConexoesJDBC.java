package br.com.massuda.alexander.persistencia.jdbc.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

import br.com.waiso.framework.abstratas.Classe;

public class ConjuntoDeConexoesJDBC extends Classe {
	
	private static ConjuntoDeConexoesJDBC instancia = null;
	protected static List<Connection> conexoesDisponiveis = new ArrayList<Connection>();
	protected static List<Connection> conexoesEmUso = new ArrayList<Connection>();
	private int numeroDeTentativasDeConexao = ConstantesPersistencia.BD_CONEXAO_NUMERO_TENTATIVAS;
	
	private ConjuntoDeConexoesJDBC() {
		preencherConjuntoDeConexoes();
	}


	private void preencherConjuntoDeConexoes() {
		int conexoesASeremCriadas = ConstantesPersistencia.QUANTIDADE_MAXIMA_DE_CONEXOES - conexoesDisponiveis.size();
		for (int i = 0; (i < conexoesASeremCriadas) && numeroDeTentativasDeConexao > 0; i++) {
			try {
				Connection conexao = GerenciadorConexaoJDBC.novaConexao();
				conexoesDisponiveis.add(conexao);
			} catch (CommunicationsException e) {
				System.err.println("Erro ao tentar conectar com o Banco de Dados");
				verificarSeEstaConfiguradoParaTentarMaisVezes();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	

	public static Connection getConexaoDisponivel() throws SQLException {
		if (naoExiste(instancia)) {
			instancia = new ConjuntoDeConexoesJDBC();
		}
		synchronized (conexoesDisponiveis) {
			for (Connection conexao : conexoesDisponiveis) {
				if (existe(conexao) && !conexao.isClosed()) {
					conexoesDisponiveis.remove(conexao);
					conexoesEmUso.add(conexao);
					return conexao;
				}
			}
		}
		return null;
	}
	
	public static void liberarConexao(Connection conexao) {
		if (existe(conexao)) {
			synchronized (conexoesEmUso) {
				synchronized (conexoesDisponiveis) {
					for (Connection con : conexoesEmUso) {
						if (con == conexao) {
							conexoesDisponiveis.add(con);
							conexoesEmUso.remove(conexao);
						}
					}
				}
				
			}
		}
	}
	
	public void finalizar() {
		try {
			if (conexoesDisponiveis.isEmpty() && !conexoesEmUso.isEmpty()) {
				for (Connection conexao : conexoesEmUso) {
					liberarConexao(conexao);
				}
			}

			for (Connection conexao : conexoesDisponiveis) {
				if (existe(conexao) && !conexao.isClosed()) {
					conexao.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void verificarSeEstaConfiguradoParaTentarMaisVezes() {
		if (--numeroDeTentativasDeConexao > 0) {
			int tentativa = ConstantesPersistencia.BD_CONEXAO_NUMERO_TENTATIVAS - numeroDeTentativasDeConexao;
			System.out.println("Realizando tentativa de conexao com o Banco de Dados: "+tentativa);
			for (int i = 0; i < numeroDeTentativasDeConexao; i++) {
				preencherConjuntoDeConexoes();
			}
		} else {
			System.err.println("Numero maximo de tentativas de conexao excedido!");
		}
		
	}
	public static ConjuntoDeConexoesJDBC getInstancia() {
		if (naoExiste(instancia)) {
			setInstancia(new ConjuntoDeConexoesJDBC());
		}
		return instancia;
	}

	private static void setInstancia(ConjuntoDeConexoesJDBC instancia) {
		ConjuntoDeConexoesJDBC.instancia = instancia;
	}
	
}
