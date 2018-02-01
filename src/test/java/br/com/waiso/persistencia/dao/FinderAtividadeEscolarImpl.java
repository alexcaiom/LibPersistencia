package br.com.waiso.persistencia.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.waiso.persistencia.model.escolaridade.AtividadeEscolar;

public class FinderAtividadeEscolarImpl extends Finder<AtividadeEscolar>  {

	public FinderAtividadeEscolarImpl() {
		super(AtividadeEscolar.class);
	}

	public AtividadeEscolar pesquisar(Long id) {
		AtividadeEscolar o = null;
		o = super.pesquisar(id);
		return o;
	}
	
	public List<AtividadeEscolar> pesquisarPorNomeComo(String nome) {
		List<AtividadeEscolar> objs = new ArrayList<>();
		objs = super.pesquisarPorNomeComo(nome);
		return objs;
	}

	public List<AtividadeEscolar> listar() {
		List<AtividadeEscolar> objs = new ArrayList<>();
		objs = super.listar();
		return objs;
	}

	public void preencher(AtividadeEscolar o) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			super.preencher(o);
		}
	}

}
