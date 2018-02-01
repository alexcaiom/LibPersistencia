package br.com.massuda.alexander.persistencia.jdbc.testes.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.massuda.alexander.persistencia.jdbc.testes.model.CoordenadasGeograficas;
import br.com.massuda.alexander.persistencia.jdbc.testes.model.Endereco;
import br.com.massuda.alexander.persistencia.jdbc.testes.model.TipoEndereco;
import br.com.massuda.alexander.persistencia.jdbc.utils.TipoOperacao;

public class FinderEnderecoImpl extends Finder<Endereco> {

	public FinderEnderecoImpl() {
		super(Endereco.class);
		tipoOperacao = TipoOperacao.PESQUISA_COM_MULTIPLAS_TABELAS;
	}
	
	public FinderEnderecoImpl(TipoOperacao tipoOperacao) {
		super(Endereco.class);
		this.tipoOperacao = tipoOperacao;
	}
	

	public Endereco pesquisar(Long id) {
		Endereco o;
		try {
			o = (Endereco) entidade.newInstance();
			String sql = "select * from tbl_endereco";

			sql += "\n where id = ?";

			PreparedStatement comandoPreparado = null;
			ResultSet resultado = null;
			try {
				comandoPreparado = novoComandoPreparado(sql);
				comandoPreparado.setLong(1, id);

				resultado = pesquisarComParametros(comandoPreparado);

				if (resultado.next()) {
					preencher(o, resultado);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					fecharObjetosDeComandoEPesquisa(null, comandoPreparado, resultado);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return o;
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	

	public void preencher(Endereco o, ResultSet resultados) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			CoordenadasGeograficas coordenadas = new CoordenadasGeograficas();
			coordenadas.setLatitude(resultados.getDouble("latitude"));
			coordenadas.setLongitude(resultados.getDouble("longitude"));
			o.setCoordenadasGeograficas(coordenadas);
			
			o.setComplemento(resultados.getString("complemento"));
			o.setNumero(resultados.getInt("numero"));
			TipoEndereco tipo = TipoEndereco.get(resultados.getString("tipo_endereco"));
			o.setTipo(tipo);
		}
	}
	
	public void preencher(Endereco o) throws SQLException {
		if (existe(o) && existe(resultados) && !resultados.isClosed()) {
			CoordenadasGeograficas coordenadas = new CoordenadasGeograficas();
			coordenadas.setLatitude(resultados.getDouble("latitude"));
			coordenadas.setLongitude(resultados.getDouble("longitude"));
			o.setCoordenadasGeograficas(coordenadas);
			
			o.setComplemento(resultados.getString("complemento"));
			o.setNumero(resultados.getInt("numero"));
			TipoEndereco tipo = TipoEndereco.get(resultados.getString("tipo_endereco"));
			o.setTipo(tipo);
		}
	}

}
