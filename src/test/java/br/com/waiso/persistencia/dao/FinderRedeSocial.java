package br.com.waiso.persistencia.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.GeradorSQLBean;
import br.com.massuda.alexander.persistencia.jdbc.utils.TipoOperacao;
import br.com.waiso.persistencia.model.RedeSocial;

public class FinderRedeSocial extends Finder<RedeSocial> {
	
	public FinderRedeSocial() {
		this(TipoOperacao.NORMAL);
	}

	public FinderRedeSocial(TipoOperacao tipo) {
		super(RedeSocial.class);
		this.tipoOperacao = tipo;
	}
	
	public List<RedeSocial> listarPorUsuario (long idUsuario) {
		List<RedeSocial> redes = new ArrayList<>();
		String sql = GeradorSQLBean.getInstancia(entidade).getComandoSelecao();
		sql += "\n where usuario_id = ?";
		
		try {
			comandoPreparado = novoComandoPreparado(sql);
			comandoPreparado.setLong(1, idUsuario);

			resultados = pesquisarComParametros(comandoPreparado);

			while (resultados.next()) {
				RedeSocial o = new RedeSocial();
				preencher(o);
				redes.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return redes;
	}

}
