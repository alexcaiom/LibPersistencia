package br.com.massuda.alexander.persistencia.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

import br.com.massuda.alexander.persistencia.jdbc.utils.ConjuntoDeConexoesJDBC;
import br.com.massuda.alexander.persistencia.jdbc.utils.ConstantesPersistencia;
import br.com.massuda.alexander.persistencia.jdbc.utils.GerenciadorConexaoJDBC;
import br.com.massuda.alexander.persistencia.jdbc.utils.GestorDeConjuntosDeConexoesJDBC;
import br.com.massuda.alexander.persistencia.jdbc.utils.MonitoradorDeConexao;
import br.com.massuda.alexander.persistencia.jdbc.utils.TipoOperacao;
import br.com.waiso.framework.abstratas.Classe;

public abstract class DAOJDBC extends Classe{
	
	public Statement comando = null;
	public PreparedStatement comandoPreparado = null;
	public ResultSet resultados = null;
	public static final Integer RETORNAR_ID_GERADO = Statement.RETURN_GENERATED_KEYS;
	public OperacaoCRUD tipoCRUD = null;
	public Connection conexao = null;
	protected boolean conexaoPorPesquisa = false;
	protected long tempoIntervalo = 0l; 
	
	private int numeroDeTentativasDeConexao = ConstantesPersistencia.BD_CONEXAO_NUMERO_TENTATIVAS;
	
	public DAOJDBC() {
		verificarSePoolDeConexoesDeveSerAtivado();
	}
	
	@PostConstruct
	public void init() {
		MonitoradorDeConexao.getInstancia(tempoIntervalo);
	}

	public TipoOperacao tipoOperacao = TipoOperacao.NORMAL;
	
	public void executarComando(String sql) throws SQLException{
		setComando(getConexao().createStatement());
		getComando().execute(sql);
		fechar();
	}
	
	public ResultSet pesquisarSemParametro(String sql) throws SQLException{
		setComando(getConexao().createStatement());
		setResultados(getComando().executeQuery(sql));
		return getResultados();
	}
	
	public boolean executarOperacaoParametrizada(PreparedStatement comandoPreparado) throws SQLException {
		return comandoPreparado.execute();
	}
	
	public ResultSet pesquisarComParametros(PreparedStatement comandoPreparado) throws SQLException {
		return comandoPreparado.executeQuery();
	}
	
	public ResultSet executarOperacaoERetornarChavesGeradas(PreparedStatement operacao) throws SQLException {
		operacao.execute();
		return operacao.getGeneratedKeys();
	}
	
	public Connection getConexao() throws SQLException {
		if (ConstantesPersistencia.BANCO_DE_DADOS_POOL_CONEXOES_ATIVO) {
			this.conexao = ConjuntoDeConexoesJDBC.getConexaoDisponivel();
		} else {
			this.conexao = tentarConectar();
		}
		return conexao;
	}
	
	public Statement novoComando() throws SQLException {
		return getConexao().createStatement();
	}
	
	public PreparedStatement novoComandoPreparado(String sql, Integer parametro) throws SQLException{
		if (existe(parametro)) {
			return getConexao().prepareStatement(sql, parametro);
		} else {
			return getConexao().prepareStatement(sql);
		}
	}
	
	public PreparedStatement novoComandoPreparado(String sql) throws SQLException{
		return novoComandoPreparado(sql, null);
	}
	
	public Statement getComando() {
		return comando;
	}
	public void setComando(Statement comando) {
		this.comando = comando;
	}
	public PreparedStatement getComandoPreparado() {
		return comandoPreparado;
	}
	public void setComandoPreparado(PreparedStatement comandoPreparado) {
		this.comandoPreparado = comandoPreparado;
	}
	public ResultSet getResultados() {
		return resultados;
	}
	public void setResultados(ResultSet resultados) {
		this.resultados = resultados;
	}
	
	public void fecharOperacaoPrincipal() throws SQLException {
		tipoOperacao = TipoOperacao.NORMAL;
		fechar();
	}
	
	public void fechar() throws SQLException {
		if (tipoOperacao == TipoOperacao.NORMAL) {
			fecharObjetosDeComandoEPesquisa(comando, comandoPreparado, resultados);
			/**
			 * FIXME Teste de fechamento de Conexao na Classe Controladora
			 */
			if (ConstantesPersistencia.BANCO_DE_DADOS_POOL_CONEXOES_ATIVO) {
				ConjuntoDeConexoesJDBC.liberarConexao(conexao);
			} else if (conexaoPorPesquisa) {
				GerenciadorConexaoJDBC.fecharConexao();
			}
		}
	}

	public void fecharObjetosDeComandoEPesquisa(Statement comando, PreparedStatement comandoPreparado, ResultSet resultados) throws SQLException {
		if (existe(comando) && !comando.isClosed()) {
			comando.close();
			setComando(null);
		}
		if (existe(comandoPreparado) && !comandoPreparado.isClosed()) {
			comandoPreparado.close();
			setComandoPreparado(null);
		}
		if (existe(resultados) && !resultados.isClosed()) {
			resultados.close();
			setResultados(null);
		}
	}
	
	private void verificarSePoolDeConexoesDeveSerAtivado() {
		if (ConstantesPersistencia.BANCO_DE_DADOS_POOL_CONEXOES_ATIVO) {
			GestorDeConjuntosDeConexoesJDBC.iniciar();
		}
	}
	
	

	private Connection tentarConectar() {
		Connection conexao = null;
		try {
			conexao = GerenciadorConexaoJDBC.novaConexao();
		} catch (CommunicationsException e) {
			System.err.println("Erro ao tentar conectar com o Banco de Dados");
			verificarSeEstaConfiguradoParaTentarMaisVezes();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conexao; 
	}
	

	private Connection verificarSeEstaConfiguradoParaTentarMaisVezes() {
		Connection conexao = null;
		if (--numeroDeTentativasDeConexao > 0) {
			int tentativa = ConstantesPersistencia.BD_CONEXAO_NUMERO_TENTATIVAS - numeroDeTentativasDeConexao;
			System.out.println("Realizando tentativa de conexao com o Banco de Dados: "+tentativa);
			for (int i = 0; i < numeroDeTentativasDeConexao; i++) {
				conexao = tentarConectar();
				if (existe(conexao)) {
					return conexao;
				}
			}
		} else {
			System.err.println("Numero maximo de tentativas de conexao excedido!");
		}
		return conexao;
		
	}

}