package br.com.waiso.persistencia.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.massuda.alexander.persistencia.interfaces.IFinder;
import br.com.massuda.alexander.persistencia.jdbc.utils.TipoOperacao;
import br.com.waiso.persistencia.model.Perfil;

public class FinderPerfilImpl extends Finder<Perfil> implements IFinder<Long, Perfil> {

	public FinderPerfilImpl() {
		super(Perfil.class);
		tipoOperacao = TipoOperacao.NORMAL;
	}
	
	public FinderPerfilImpl(TipoOperacao tipoOperacao) {
		super(Perfil.class);
		this.tipoOperacao = tipoOperacao;
	}

	@Override
	public Perfil pesquisar(Long id) {
		return super.pesquisar(id);
	}

	@Override
	public List<Perfil> listar() {
		return super.listar();
	}
	
	public void preencher(Perfil o) throws SQLException {
		super.preencher(o);
	}

}
