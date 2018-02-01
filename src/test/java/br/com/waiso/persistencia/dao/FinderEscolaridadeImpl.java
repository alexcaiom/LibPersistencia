package br.com.waiso.persistencia.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.GeradorSQLBean;
import br.com.waiso.persistencia.model.escolaridade.Escolaridade;

public class FinderEscolaridadeImpl extends Finder<Escolaridade>  {

	public FinderEscolaridadeImpl() {
		super(Escolaridade.class);
	}

	public Escolaridade pesquisar(Long id) {
		Escolaridade o = null;
		o = super.pesquisar(id);
		return o;
	}
	
	public List<Escolaridade> pesquisarPorNomeComo(String nome) {
		List<Escolaridade> objs = new ArrayList<>();
		objs = super.pesquisarPorNomeComo(nome);
		return objs;
	}

	public List<Escolaridade> listar() {
		List<Escolaridade> objs = new ArrayList<>();
		objs = super.listar();
		return objs;
	}

	public void preencher(Escolaridade o) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			super.preencher(o);
		}
	}

	public List<Escolaridade> listarPorUsuario(long idUsuario) {
		List<Escolaridade> escolaridades = new ArrayList<>();
		
		String sql = GeradorSQLBean.getInstancia(entidade).getComandoSelecao();
		sql +=  "\n where   usuario_id = ?";
		
		/*String sql = 
				"select end.* "+
				"\n from  `tbl_endereco_usuario` endUsuario, `tbl_endereco`  end";
		sql +=  "\n where endUsuario.usuario_id = ?"
			+   "\n and   end.id = endUsuario.endereco_id";*/
		
		try {
			comandoPreparado = novoComandoPreparado(sql);
			comandoPreparado.setLong(1, idUsuario);
			resultados = pesquisarComParametros(comandoPreparado);

			while (resultados.next()) {
				Escolaridade o = new Escolaridade();
				preencher(o);
				escolaridades.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return escolaridades;
	}

}
