package br.com.massuda.alexander.persistencia.jdbc.testes.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.GeradorSQLBean;
import br.com.massuda.alexander.persistencia.jdbc.testes.model.Endereco;
import br.com.massuda.alexander.persistencia.jdbc.testes.model.InstituicaoDeEnsino;
import br.com.massuda.alexander.persistencia.jdbc.utils.TipoOperacao;

public class FinderInstituicaoDeEnsinoImpl extends Finder<InstituicaoDeEnsino> {
	
	public FinderInstituicaoDeEnsinoImpl() {
		this(TipoOperacao.NORMAL);
	}

	public FinderInstituicaoDeEnsinoImpl(TipoOperacao tipo) {
		super(InstituicaoDeEnsino.class);
		this.tipoOperacao = tipo;
	}

	public InstituicaoDeEnsino pesquisar(Long id) {
		InstituicaoDeEnsino o = null;
		o = super.pesquisar(id);
		return o;
	}
	
	public List<InstituicaoDeEnsino> pesquisarPorNomeComo(String nome) {
		List<InstituicaoDeEnsino> objs = new ArrayList<InstituicaoDeEnsino>();
		objs = super.pesquisarPorNomeComo(nome);
		return objs;
	}

	public List<InstituicaoDeEnsino> listar() {
		List<InstituicaoDeEnsino> objs = new ArrayList<InstituicaoDeEnsino>();
	
		String sql = GeradorSQLBean.getInstancia(entidade).getComandoSelecao();
		try {
			resultados = pesquisarSemParametro(sql);
			
			while (resultados.next()) {
				InstituicaoDeEnsino o = new InstituicaoDeEnsino();
				preencher(o);
				objs.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return objs;
	}

	public void preencher(InstituicaoDeEnsino o) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			super.preencher(o);
		}
		
		
		if (existe(o.getEndereco())) {
			Long id = o.getEndereco().getId();
			Endereco endereco = new FinderEnderecoImpl().pesquisar(id);
			o.setEndereco(endereco);
		}
	}

}
