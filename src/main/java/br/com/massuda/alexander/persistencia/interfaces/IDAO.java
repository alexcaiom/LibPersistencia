package br.com.massuda.alexander.persistencia.interfaces;

import br.com.waiso.framework.exceptions.ErroUsuario;

public interface IDAO<T> {

	public T incluir(T o) throws ErroUsuario;
	public void editar(T o)  throws ErroUsuario;
	public void excluir(T o)  throws ErroUsuario;
	
}
