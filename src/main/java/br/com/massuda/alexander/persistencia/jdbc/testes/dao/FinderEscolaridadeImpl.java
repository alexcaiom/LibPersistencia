package br.com.massuda.alexander.persistencia.jdbc.testes.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.testes.model.Escolaridade;

public class FinderEscolaridadeImpl extends Finder<Escolaridade> {

	public FinderEscolaridadeImpl() {
		super(Escolaridade.class);
	}

	public Escolaridade pesquisar(Long id) {
		Escolaridade o = null;
		o = super.pesquisar(id);
		return o;
	}
	
	public List<Escolaridade> pesquisarPorNomeComo(String nome) {
		List<Escolaridade> objs = new ArrayList<Escolaridade>();
		objs = super.pesquisarPorNomeComo(nome);
		return objs;
	}

	public List<Escolaridade> listar() {
		List<Escolaridade> objs = new ArrayList<Escolaridade>();
		objs = super.listar();
		return objs;
	}

	public void preencher(Escolaridade o) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			super.preencher(o);
		}
	}

}
