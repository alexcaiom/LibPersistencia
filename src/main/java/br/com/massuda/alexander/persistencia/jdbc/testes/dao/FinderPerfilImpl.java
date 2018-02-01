package br.com.massuda.alexander.persistencia.jdbc.testes.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.testes.model.Perfil;
import br.com.massuda.alexander.persistencia.jdbc.utils.TipoOperacao;

public class FinderPerfilImpl extends Finder<Perfil>  {

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
