package br.com.massuda.alexander.persistencia.jpa;

import br.com.waiso.framework.exceptions.ErroUsuario;

public class DAOGenericoJPAImpl<T> extends DAOJPA {
	
	protected Class<T> entidade;
	
	public DAOGenericoJPAImpl(Class<T> entidade) {
		this.entidade = entidade;
	}

	public T incluir (T o) throws ErroUsuario {
		try {
			getEm().persist(o);
		}  finally {
			fechar();
		}
		return o;
	}

	public void editar (T o) {
		try {
			getEm().merge(o);
		} finally {
			fechar();
		}
	}

	public void excluir(T o) throws ErroUsuario {
		try {
			getEm().remove(o);
		} finally {
			fechar();
		}
	}
	
	public void excluir(long id) throws ErroUsuario {
		try {
			T o = getEm().find(entidade, id);
			getEm().remove(o);
		} finally {
			fechar();
		}
	}

}
