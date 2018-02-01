/**
 * 
 */
package br.com.massuda.alexander.persistencia.jdbc.testes;

import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.testes.dao.FinderUsuarioImpl;
import br.com.massuda.alexander.persistencia.jdbc.testes.model.Usuario;

/**
 * @author Alex
 *
 */
public class TesteCadastroUsuario {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Usuario> usuarios = new FinderUsuarioImpl().listar();
		for (Usuario u : usuarios) {
			System.out.println(u);
		}
	}

}
