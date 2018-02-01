///**
// * 
// */
//package br.com.massuda.alexander.persistencia.jdbc.finder;
//
//import static org.junit.Assert.*;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//import javax.sql.DataSource;
//
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import static org.mockito.Mockito.*;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import com.mysql.jdbc.Field;
//
//import br.com.massuda.alexander.persistencia.jdbc.utils.ConstantesPersistencia;
//import br.com.waiso.persistencia.dao.FinderSistemaImpl;
//import br.com.waiso.persistencia.model.Sistema;
//import br.com.waiso.persistencia.utils.Constantes;
//
///**
// * @author Alex
// *
// */
//@RunWith(MockitoJUnitRunner.class)
//public class FinderJDBCTest {
//	
//	@InjectMocks
//	private FinderSistemaImpl finder;
//	
//	@Mock
//	private Connection mockConexao;
//	@Mock
//	private DataSource mockDataSource;
//	@Mock
//	private PreparedStatement mockPreparedStatement;
//	@Mock
//	private ResultSet mockResultSet;
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//		setVariaveisAmbiente();
//		when(mockDataSource.getConnection()).thenReturn(mockConexao);
//		when(mockDataSource.getConnection(anyString(), anyString())).thenReturn(mockConexao);
//		doNothing().when(mockConexao).commit();
//		when(mockConexao.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//		doNothing().when(mockPreparedStatement).setLong(anyInt(), anyLong());
//		when(mockPreparedStatement.execute()).thenReturn(true);
//		when(mockResultSet.next()).thenReturn(true, false);
//	}
//
//	private void setVariaveisAmbiente() {
//		ConstantesPersistencia.BD_CONEXAO_LOCAL = ConstantesPersistencia.BANCO_DE_DADOS_TESTE_LOCAL;
//		ConstantesPersistencia.BD_CONEXAO_NOME_BD = ConstantesPersistencia.BANCO_DE_DADOS_TESTE_NOME;
//		ConstantesPersistencia.BD_CONEXAO_PORTA = ConstantesPersistencia.BANCO_DE_DADOS_TESTE_CONEXAO_PORTA;
//		ConstantesPersistencia.BD_CONEXAO_USUARIO = ConstantesPersistencia.BANCO_DE_DADOS_TESTE_CONEXAO_USUARIO;
//		ConstantesPersistencia.BD_CONEXAO_SENHA = ConstantesPersistencia.BANCO_DE_DADOS_TESTE_CONEXAO_SENHA;
//	}
//
//	/**
//	 * Test method for {@link br.com.massuda.alexander.persistencia.jdbc.finder.FinderJDBC#pesquisar(java.lang.Long)}.
//	 */
//	@Test
//	public void testPesquisarLong() {
//		long id = 1;
//		when(methodCall)
//		Sistema pesquisar = finder.pesquisar(id);
//		
//	}
//
//	/**
//	 * Test method for {@link br.com.massuda.alexander.persistencia.jdbc.finder.FinderJDBC#pesquisarPorNomeComo(java.lang.String)}.
//	 */
//	@Test
//	public void testPesquisarPorNomeComo() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link br.com.massuda.alexander.persistencia.jdbc.finder.FinderJDBC#listar()}.
//	 */
//	@Test
//	public void testListar() {
//		fail("Not yet implemented");
//	}
//
//}
