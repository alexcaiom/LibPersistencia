package br.com.massuda.alexander.persistencia.jpa.dao.finder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;*/



import br.com.massuda.alexander.persistencia.jdbc.GeradorSQLBean;
import br.com.massuda.alexander.persistencia.jdbc.utils.TipoOperacao;


public class FinderHibernate<ChavePrimaria, T> extends FinderHibernateUtils<T> {

	private Class<T> entidade;
	
	public T pesquisar(ChavePrimaria id) {
		return (T) getEm().find(Object.class, id);
	}
	
	public List<T> pesquisarPorNomeComo(String nome) {
		List<T> objs = new ArrayList<T>();
		
		
		
		return objs;
	}

	public List<T> listar() {
		List<T> objs = new ArrayList<T>();
		/*CriteriaQuery cq = getEm().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entidade));*/
		
		return objs;
	}
	
	public List<T> pesquisar() {
		List<T> objs = new ArrayList<>();
		return objs;
	}
}