/**
 * 
 */
package br.com.massuda.alexander.persistencia.jdbc.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import br.com.massuda.alexander.spring.framework.infra.utils.UtilsData;
import br.com.waiso.framework.abstratas.Classe;

/**
 * @author Alex
 *
 */
public class MonitoradorDeConexao extends Thread {

	private static final int INTERVALO_DE_ERROS = 10000;
	private static final String QUERY = "select 1 from dual";
	private static boolean viva = true;
	private long intervalo = 10000; //1segundo
	
	private static MonitoradorDeConexao instancia;
	
	public MonitoradorDeConexao(long intervalo) {
		setName(this.getClass().getSimpleName());
		this.intervalo = intervalo;
	}
	
	@Override
	public void run() {
		while (viva) {
			try {
				ResultSet resultado = GerenciadorConexaoJDBC.getConexao().createStatement().executeQuery(QUERY);
				if (resultado.next()) {
					System.out.println(UtilsData.getDataHoraAgoraPorExtenso()+ ": conexao bd ativa");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					System.out.println("intervalo de erros: "+INTERVALO_DE_ERROS);
					sleep(INTERVALO_DE_ERROS);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//renovar conexao;
			}
			
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static MonitoradorDeConexao getInstancia(long intervalo) {
		if (Classe.naoExiste(instancia)) {
			instancia = new MonitoradorDeConexao(intervalo);
			instancia.start();
		}
		return instancia;
	}
	
	public void encerrar() {
		MonitoradorDeConexao.viva = false;
	}
}
