package br.com.waiso.persistencia.dao;

import br.com.waiso.persistencia.model.Funcionalidade;
import br.com.waiso.framework.exceptions.ErroUsuario;

public class FuncionalidadeDAOImpl extends DAO<Funcionalidade> {

	public FuncionalidadeDAOImpl() {
		super(Funcionalidade.class);
	}
	
	public Funcionalidade incluir(Funcionalidade o) throws ErroUsuario {
		return super.incluir(o);
	}

	public void editar(Funcionalidade o) throws ErroUsuario {
		super.editar(o);
	}

	public void excluir(Funcionalidade o) throws ErroUsuario {
		super.excluir(o);
	}

}
