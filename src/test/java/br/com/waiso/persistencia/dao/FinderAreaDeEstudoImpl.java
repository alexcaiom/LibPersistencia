package br.com.waiso.persistencia.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.waiso.persistencia.model.escolaridade.AreaDeEstudo;

public class FinderAreaDeEstudoImpl extends Finder<AreaDeEstudo>  {

	public FinderAreaDeEstudoImpl() {
		super(AreaDeEstudo.class);
	}

	public AreaDeEstudo pesquisar(Long id) {
		AreaDeEstudo o = null;
		o = super.pesquisar(id);
		return o;
	}
	
	public List<AreaDeEstudo> pesquisarPorNomeComo(String nome) {
		List<AreaDeEstudo> objs = new ArrayList<>();
		objs = super.pesquisarPorNomeComo(nome);
		return objs;
	}

	public List<AreaDeEstudo> listar() {
		List<AreaDeEstudo> objs = new ArrayList<>();
		objs = super.listar();
		return objs;
	}

	public void preencher(AreaDeEstudo o) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			super.preencher(o);
		}
	}

}
