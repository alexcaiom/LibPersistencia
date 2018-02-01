package br.com.waiso.persistencia.test;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.waiso.persistencia.dao.FinderSistemaImpl;
import br.com.waiso.persistencia.dao.SistemaDAOImpl;
import br.com.waiso.persistencia.model.Sistema;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SistemaJPATest {

	FinderSistemaImpl finder = new FinderSistemaImpl();
	SistemaDAOImpl dao = new SistemaDAOImpl();
	private String nome = "Sistema ";
	private long id = 3l;

	//@Test
	public void test2Incluir() {
		Sistema s = new Sistema();
		s.setNome(nome+id);
		
		s = dao.incluir(s);
		System.out.println(s);
		id = s.getId();
	}
	
//	@Test
	public void test3Editar() {
		Sistema s = finder.pesquisar(id);
		s.setNome(nome+id);
		dao.editar(s);
	}
	
//	@Test
	public void test4PesquisarLong() {
		System.out.println(finder.pesquisar(id));
	}

//	@Test
	public void test5PesquisarPorNomeComoString() {
		System.out.println(finder.pesquisarPorNomeComo("a"));
	}

	//@Test
	public void test1Listar() {
		List<Sistema> sistemas = finder.listar();
		for (Sistema o : sistemas) {
 			System.out.println(o);
		}
		test2Incluir();
		test3Editar();
		test4PesquisarLong();
		test5PesquisarPorNomeComoString();
		testExcluir();
	}
	
	//@Test
	public void testExcluir() {
		Sistema sistema = finder.pesquisar(3l);
		dao.excluir(sistema);
	}

}
