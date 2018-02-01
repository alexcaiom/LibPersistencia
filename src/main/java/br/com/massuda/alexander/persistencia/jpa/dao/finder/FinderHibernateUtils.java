package br.com.massuda.alexander.persistencia.jpa.dao.finder;

import org.hibernate.criterion.CriteriaQuery;

import br.com.massuda.alexander.persistencia.jpa.DAOJPA;

public class FinderHibernateUtils<T> extends DAOJPA {
	
	CriteriaQuery criteriaQuery = null;
	
	/*public CriteriaQuery getBuilder() {
		return getFabrica().
	}*/
	
	/*public List<Criteria> filtros = new ArrayList<>();

	public void addFiltro(Filtro filtro) {
		if (existe(filtro)) {
			filtros.add(filtro);
		}
	}
	
	public void limparFiltros() {
		filtros.clear();
	}

	public List<Filtro> getFiltros() {
		return filtros;
	}*/
	
	
}