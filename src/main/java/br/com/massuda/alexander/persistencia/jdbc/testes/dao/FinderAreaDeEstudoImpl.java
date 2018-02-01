package br.com.massuda.alexander.persistencia.jdbc.testes.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.testes.model.AreaDeEstudo;

public class FinderAreaDeEstudoImpl extends Finder<AreaDeEstudo> {

	public FinderAreaDeEstudoImpl() {
		super(AreaDeEstudo.class);
	}

	public AreaDeEstudo pesquisar(Long id) {
		AreaDeEstudo o = null;
		o = super.pesquisar(id);
		return o;
	}
	
	public List<AreaDeEstudo> pesquisarPorNomeComo(String nome) {
		List<AreaDeEstudo> objs = new ArrayList<AreaDeEstudo>();
		objs = super.pesquisarPorNomeComo(nome);
		return objs;
	}

	public List<AreaDeEstudo> listar() {
		List<AreaDeEstudo> objs = new ArrayList<AreaDeEstudo>();
		objs = super.listar();
		return objs;
	}

	public void preencher(AreaDeEstudo o) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			super.preencher(o);
		}
	}

}
