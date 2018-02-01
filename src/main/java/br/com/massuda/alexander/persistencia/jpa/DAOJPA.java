package br.com.massuda.alexander.persistencia.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.massuda.alexander.persistencia.jdbc.utils.ConstantesPersistencia;
import br.com.waiso.framework.abstratas.Classe;

public abstract class DAOJPA extends Classe {

	private EntityManager em;
	private EntityManagerFactory fabrica;
	
	public EntityManager getEm() {
		if (naoExiste(em) && !em.isOpen()) {
			em = getFabrica().createEntityManager();
		}
		return em;
	}
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	public EntityManagerFactory getFabrica() {
		if (naoExiste(fabrica)) {
			setFabrica(Persistence.createEntityManagerFactory(ConstantesPersistencia.BD_UNIDADE_PERSISTENCIA));
		}
		return fabrica;
	}
	public void setFabrica(EntityManagerFactory fabrica) {
		this.fabrica = fabrica;
	}
	
	public void fechar() {
		if (existe(em) && em.isOpen()) {
			em.close();
		}
	}
}
