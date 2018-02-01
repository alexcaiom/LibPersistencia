package br.com.massuda.alexander.persistencia.jdbc.testes.dao;

import br.com.massuda.alexander.persistencia.jdbc.finder.FinderJDBC;

public abstract class Finder<T> extends FinderJDBC<T> {

	public Finder(Class<T> entidade) {
		super(entidade);
		DAOTeste.configurarBancoDeDadosDadosAcesso();
	}

}
