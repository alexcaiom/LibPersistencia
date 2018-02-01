package br.com.waiso.persistencia.test;

import org.junit.Test;

import br.com.waiso.persistencia.dao.FinderModuloImpl;
import br.com.waiso.persistencia.dao.FinderSistemaImpl;
import br.com.waiso.persistencia.dao.ModuloDAOImpl;
import br.com.waiso.persistencia.model.Modulo;
import br.com.waiso.persistencia.model.Sistema;

public class ModuloTest {

	FinderModuloImpl finder = new FinderModuloImpl();
	ModuloDAOImpl dao = new ModuloDAOImpl();
	
	//@Test
	public void testIncluir() {
		Modulo modulo = new Modulo();
		modulo.setNome("Cadastro");
		Sistema sistema = new Sistema();
		long idNenhumSistema = 0l;
		sistema.setId(idNenhumSistema);
		modulo.setSistema(sistema);
		System.out.println(dao.incluir(modulo));
	}
	
	//@Test
	public void testEditar() {
		long idModulo = 4l;
		Modulo modulo = finder.pesquisar(idModulo);
		System.out.println(modulo);
		long idSistema = 4l;
		Sistema sistema = new FinderSistemaImpl().pesquisar(idSistema);
		modulo.setSistema(sistema);
		dao.editar(modulo);
	}
	
	//@Test
	public void testPesquisarLong() {
		System.out.println(finder.pesquisar(1l));
	}

	//@Test
	public void testPesquisarPorNomeComoString() {
		String nome = "a";
		System.out.println(finder.pesquisarPorNomeComo(nome));
	}

	//@Test
	public void testListar() {
		for (Modulo o : finder.listar()) {
			System.out.println(o);
		}
	}

}
