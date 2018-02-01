package br.com.waiso.persistencia.dao;

import br.com.waiso.persistencia.model.Modulo;
import br.com.waiso.framework.exceptions.ErroUsuario;

public class ModuloDAOImpl extends DAO<Modulo> {

	public ModuloDAOImpl() {
		super(Modulo.class);
	}
	
	public Modulo incluir(Modulo o) throws ErroUsuario {
		return super.incluir(o);
	}

	public void editar(Modulo o) throws ErroUsuario {
		super.editar(o);
	}

	public void excluir(Modulo o) throws ErroUsuario {
		super.excluir(o);
	}

}
