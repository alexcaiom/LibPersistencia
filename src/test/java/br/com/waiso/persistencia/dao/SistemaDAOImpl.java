package br.com.waiso.persistencia.dao;

import br.com.waiso.persistencia.model.Sistema;
import br.com.waiso.framework.exceptions.ErroUsuario;

public class SistemaDAOImpl extends DAO<Sistema> {

	public SistemaDAOImpl() {
		super(Sistema.class);
	}
	
	public Sistema incluir(Sistema o) throws ErroUsuario {
		return super.incluir(o);
	}

	public void editar(Sistema o) throws ErroUsuario {
		super.editar(o);
	}

	public void excluir(Sistema o) throws ErroUsuario {
		super.excluir(o);
	}

}
