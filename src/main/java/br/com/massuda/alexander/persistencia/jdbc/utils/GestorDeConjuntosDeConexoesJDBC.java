package br.com.massuda.alexander.persistencia.jdbc.utils;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.waiso.framework.abstratas.Classe;

public class GestorDeConjuntosDeConexoesJDBC extends Thread {

	private long tempoDeSincronizacaoEmSegundos = 0;
	private boolean continuar = true;
	private ConjuntoDeConexoesJDBC conjunto = null;
	private static GestorDeConjuntosDeConexoesJDBC gestor = null;
	
	public GestorDeConjuntosDeConexoesJDBC() {
		tempoDeSincronizacaoEmSegundos = ConstantesPersistencia.TEMPO_SINCRONIZACAO_EM_SEGUNDOS;
	}
	
	public static void iniciar() {
		if (!Classe.existe(gestor)) {
			gestor = new GestorDeConjuntosDeConexoesJDBC();
			gestor.start();
		}
	}
	
	@Override
	public void run() {
		while (continuar) {
			try {
				if (verificarSeConjuntoFoiCriado() ) {
					synchronized (ConjuntoDeConexoesJDBC.conexoesDisponiveis) {
						for (Connection conexao : ConjuntoDeConexoesJDBC.conexoesDisponiveis) {
							boolean conexaoValida = conexao.isValid(0);
							boolean conexaoFechada = conexao.isClosed();
							if (conexaoFechada || !conexaoValida) {
								ConjuntoDeConexoesJDBC.conexoesDisponiveis.remove(conexao);
								if (!conexaoValida) {
									if (conexao.isClosed()) {
										conexao = GerenciadorConexaoJDBC.novaConexao();
									}
								} else if (conexaoFechada) {
									conexao = GerenciadorConexaoJDBC.novaConexao();
								}
								ConjuntoDeConexoesJDBC.conexoesDisponiveis.add(conexao);
							}
						}
					}
				}
				sleep(tempoDeSincronizacaoEmSegundos);
			} catch (InterruptedException e) {
				encerrar();
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		encerrarConexoes();
	}
	
	private boolean verificarSeConjuntoFoiCriado() {
		if (Classe.naoExiste(conjunto)) {
			conjunto = ConjuntoDeConexoesJDBC.getInstancia();
		}
		
		return Classe.existe(conjunto);
	}

	private void encerrarConexoes() {
		conjunto.finalizar();
	}

	public void encerrar() {
		continuar = false;
		encerrarConexoes();
	}
	
}
