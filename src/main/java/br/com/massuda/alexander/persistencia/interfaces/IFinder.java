package br.com.massuda.alexander.persistencia.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IFinder<ChavePrimaria, T> {

	public T pesquisar(ChavePrimaria id);
	public List<T> pesquisarPorNomeComo(String nome);
	public List<T> listar();
	
	public void preencher(T o) throws SQLException;
	
}
