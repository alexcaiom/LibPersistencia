package br.com.waiso.persistencia.dao;

import br.com.massuda.alexander.persistencia.jdbc.finder.FinderJDBC;

public abstract class Finder<T> extends FinderJDBC<T> {

	public Finder(Class<T> entidade) {
		super(entidade);
		DAO.configurarBancoDeDadosDadosAcesso();
	}

}
