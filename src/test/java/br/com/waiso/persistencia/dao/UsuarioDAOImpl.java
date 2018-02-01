package br.com.waiso.persistencia.dao;

import br.com.waiso.framework.exceptions.ErroUsuario;
import br.com.waiso.persistencia.model.Usuario;

public class UsuarioDAOImpl extends DAO<Usuario> {

	public UsuarioDAOImpl() {
		super(Usuario.class);
	}
	
	public Usuario incluir(Usuario o) throws ErroUsuario {
		return super.incluir(o);
	}

	public void editar(Usuario o) throws ErroUsuario {
		super.editar(o);
	}

	public void excluir(Usuario o) throws ErroUsuario {
		super.excluir(o);
	}
	
	public void excluir(long id) throws ErroUsuario {
		super.excluir(id);
	}

}
