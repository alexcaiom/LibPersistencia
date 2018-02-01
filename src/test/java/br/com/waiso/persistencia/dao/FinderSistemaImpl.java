package br.com.waiso.persistencia.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.waiso.persistencia.model.Sistema;

public class FinderSistemaImpl extends Finder<Sistema> {

	public FinderSistemaImpl() {
		super(Sistema.class);
	}

	public Sistema pesquisar(long id) {
		Sistema s = null;
		s = super.pesquisar(id);
		return s;
	}
	
	public List<Sistema> pesquisarPorNomeComo(String nome) {
		List<Sistema> sistemas = new ArrayList<>();
		sistemas = super.pesquisarPorNomeComo(nome);
		return sistemas;
	}

	public List<Sistema> listar() {
		List<Sistema> sistemas = new ArrayList<Sistema>();
		sistemas = super.listar();
		return sistemas;
	}

	public void preencher(Sistema o) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			super.preencher(o);
		}
	}

}
