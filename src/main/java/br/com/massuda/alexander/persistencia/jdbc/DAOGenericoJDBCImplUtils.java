package br.com.massuda.alexander.persistencia.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;
import br.com.massuda.alexander.persistencia.jdbc.utils.MetodoDeEntidade;
import br.com.massuda.alexander.persistencia.jdbc.utils.ParametroParaComandoParametrizado;
import br.com.waiso.framework.abstratas.TipoMetodo;
import br.com.waiso.framework.exceptions.ErroObjetoNaoPreenchido;

public abstract class DAOGenericoJDBCImplUtils<T> extends DAOJDBC {

	protected Class<T> entidade;
	public boolean possuiHeranca = false;
	
	protected boolean setarId(T o, Long chaveGerada, Class classe) {
		boolean sucesso = false;
		try {
			if (naoExiste(classe)) {
				classe = o.getClass();
			}
			Method metodoSetId;
			metodoSetId = getMetodo(o.getClass(), TipoMetodo.SET, "id");
			metodoSetId.invoke(o, chaveGerada);
			sucesso = true;
			
			if (naoExiste(metodoSetId)) {
				if (possuiHeranca && GeradorSQLBean.possuiHeranca(o.getClass())) {
					classe = ((Class) o.getClass()).getSuperclass();
					sucesso = setarId(o, chaveGerada, classe);
				} else {
					sucesso = false;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return sucesso;
	}

	protected boolean temAtributoIdEditavel(T o) {
		return setarId(o, 1l, null);
	}
	
	public void preencherParametrosDeComandoDeInsercao(PreparedStatement comandoPreparado, T o) throws SQLException {

		Field[] campos = o.getClass().getDeclaredFields();

		List<MetodoDeEntidade> gettersDosAtributos = new ArrayList<MetodoDeEntidade>();
		//Primeiro, verificamos os campos e se existe heranca
		for (int i = 0; i < campos.length; i++) {
			Field campo  = campos[i];
			if (GeradorSQLBean.getInstancia(o.getClass()).ehChavePrimariaAutoIncremental(campo)) {
				continue;
			}
			String campoGetter = (GeradorSQLBean.getInstancia(o.getClass()).getCampoNormal(campo));
			Character primeiroCaracter = campoGetter.charAt(0);
			
			String prefixoGet = "";
			if (campo.getType() == Boolean.class || campo.getType() == boolean.class) {
				prefixoGet = "is";
			} else {
				prefixoGet = "get";
			}
			
			campoGetter = prefixoGet+primeiroCaracter.toUpperCase(primeiroCaracter)+campoGetter.substring(1);
			try {
				boolean chaveEstrangeira = GeradorSQLBean.getInstancia(o.getClass()).ehChaveEstrangeira(campo);
				MetodoDeEntidade metodo = null;
				if (!chaveEstrangeira) {
					Method metodoTemp = o.getClass().getMethod(campoGetter);
					metodo = new MetodoDeEntidade(false, false, metodoTemp, o);
				} else {
					Method metodoTemp = o.getClass().getMethod(campoGetter);
					metodo = new MetodoDeEntidade(false, true, metodoTemp, o);
				}
				gettersDosAtributos.add(metodo);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				System.out.println("O Metodo nao existe ou esta com outra nomenclatura. Implemente o getter com a IDE Eclipse");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		
		if (possuiHeranca) {
			addCamposHerdadosAListaDeMetodosGetters(o.getClass(), gettersDosAtributos);
		}
		
		//Depois, iteramos os getters ja verificados e pegamos seus valores
		MetodoDeEntidade[] metodos = gettersDosAtributos.toArray(new MetodoDeEntidade[gettersDosAtributos.size()]);
		for (int i=0; i < metodos.length; i++) {
			MetodoDeEntidade metodo = metodos[i];
			Object valor = null;
			try {
				Class tipo = null;
				valor = metodo.chamar(o);
				tipo = metodo.get().getReturnType();
				if (metodo.isChaveEstrangeira()) {
					Method m = tipo.getMethod("getId");
					if (existe(valor)) {
						valor = m.invoke(valor);
					}
					tipo = m.getReturnType();
				}
				
				setParametroDeComandoPreparadoPorTipo(comandoPreparado, tipo, valor, i+1);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addCamposHerdadosAListaDeMetodosGetters(Class classeASerVerificada, List<MetodoDeEntidade> gettersDosAtributos) {
		Class mae = classeASerVerificada.getSuperclass();
		
		Field[] campos = mae.getDeclaredFields();
		for (int i = 0; i < campos.length; i++) {
			Field campo  = campos[i];
			boolean ehChavePrimariaAutoIncremental = GeradorSQLBean.getInstancia(mae).ehChavePrimariaAutoIncremental(campo);
			if (ehChavePrimariaAutoIncremental && tipoCRUD == OperacaoCRUD.INSERCAO) {
				continue;
			}
			String campoGetter = (GeradorSQLBean.getInstancia(classeASerVerificada.getClass()).getCampoNormal(campo));
			Character primeiroCaracter = campoGetter.charAt(0);
			campoGetter = "get"+primeiroCaracter.toUpperCase(primeiroCaracter)+campoGetter.substring(1);
			boolean chavePrimaria = GeradorSQLBean.getInstancia(mae).ehChavePrimaria(campo);
			try {
				Method metodoTemp = mae.getMethod(campoGetter);
				MetodoDeEntidade metodo = new MetodoDeEntidade(chavePrimaria, false, metodoTemp);
				gettersDosAtributos.add(metodo);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				System.out.println("O Metodo nao existe ou esta com outra nomenclatura. Implemente o getter com a IDE Eclipse");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		
		if (verificaSePossuiHeranca(mae)) {
			addCamposHerdadosAListaDeMetodosGetters(mae, gettersDosAtributos);
		}
	}
	
	/*public void addCamposHerdadosAListaDeMetodosSetters(Class classeASerVerificada, List<Method> settersDosAtributos, List<Field> atributos) {
		Class mae = classeASerVerificada.getSuperclass();
		
		Field[] campos = mae.getDeclaredFields();
		for (int i = 0; i < campos.length; i++) {
			Field campo  = campos[i];
			Class tipoCampo = campo.getType();
			
			String campoSetter = (GeradorSQLBean.getInstancia(classeASerVerificada.getClass()).getCampoNormal(campo));
			Character primeiroCaracter = campoSetter.charAt(0);
			campoSetter = "set"+primeiroCaracter.toUpperCase(primeiroCaracter)+campoSetter.substring(1);
			try {
				Method metodo = mae.getMethod(campoSetter, tipoCampo);
				settersDosAtributos.add(metodo);
				atributos.add(campo);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				System.out.println("O Metodo nao existe ou esta com outra nomenclatura. Implemente o getter com a IDE Eclipse");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		
		if (verificaSePossuiHeranca(mae)) {
			addCamposHerdadosAListaDeMetodosSetters(mae, settersDosAtributos, atributos);
		}
	}*/

	public void setParametroDeComandoPreparadoPorTipo(PreparedStatement comandoPreparado, Class tipo, Object valor, int indice) throws SQLException {
		if (tipo == Integer.class || tipo == int.class) {
			comandoPreparado.setInt(indice, 		(existe(valor) ? (Integer) valor : null));
		} else if (tipo == Long.class || tipo == long.class) {
			comandoPreparado.setLong(indice, (Long) valor);
		} else if (tipo == Double.class || tipo == double.class) {
			comandoPreparado.setDouble(indice, 		(existe(valor) ? (Double) valor : null));
		} else if (tipo == Float.class || tipo == float.class) {
			comandoPreparado.setFloat(indice, 		(existe(valor) ? (Float) valor : null));
		} else if (tipo == Boolean.class || tipo == boolean.class) {
			int v = (existe(valor) ? (Boolean) valor : false) ? 1 : 0;
			comandoPreparado.setInt(indice, 		v);
		} else if (tipo == String.class) {
			comandoPreparado.setString(indice, 		(String) valor);
		} else if (tipo == Calendar.class || tipo == Date.class) {
			java.sql.Date dataBD = null;
			if (tipo == Calendar.class) {
				Calendar data = (Calendar)valor;
				dataBD = new java.sql.Date(data.getTimeInMillis());
			} else if (tipo == Date.class) {
				Date data = (Date)valor;
				dataBD = new java.sql.Date(data.getTime());
			}
			comandoPreparado.setDate(indice, dataBD);
		} else if (tipo.getClass().isEnum()) {
			comandoPreparado.setString(indice, 		(existe(valor) ? ((Enum)valor).name() : null));
		}
		
	}
	
	protected void preencherEdicoes(PreparedStatement comandoPreparado, T o) throws SQLException {

		Field[] campos = o.getClass().getDeclaredFields();

		List<MetodoDeEntidade> gettersDosAtributos = new ArrayList<MetodoDeEntidade>();
		//Primeiro, verificamos os campos e se existe heranca
		for (int i = 0; i < campos.length; i++) {
			Field campo  = campos[i];
			if (GeradorSQLBean.getInstancia(o.getClass()).ehChavePrimaria(campo)) {
				continue;
			}
			String campoGetter = (GeradorSQLBean.getInstancia(o.getClass()).getCampoNormal(campo));
			Character primeiroCaracter = campoGetter.charAt(0);
			
			String prefixoGet = "";
			if (campo.getType() == Boolean.class || campo.getType() == boolean.class) {
				prefixoGet = "is";
			} else {
				prefixoGet = "get";
			}
			
			campoGetter = prefixoGet+Character.toUpperCase(primeiroCaracter)+campoGetter.substring(1);
			try {
				boolean chaveEstrangeira = GeradorSQLBean.getInstancia(o.getClass()).ehChaveEstrangeira(campo);
				MetodoDeEntidade metodo = null;

				Method metodoTemp = o.getClass().getMethod(campoGetter);
				metodo = new MetodoDeEntidade(false, chaveEstrangeira, metodoTemp, o);
				gettersDosAtributos.add(metodo);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				System.out.println("O Metodo nao existe ou esta com outra nomenclatura. Implemente o getter com a IDE Eclipse");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		
		if (possuiHeranca) {
			addCamposHerdadosAListaDeMetodosGetters(o.getClass(), gettersDosAtributos);
		}
		
		//Depois, iteramos os getters ja verificados e pegamos seus valores
		MetodoDeEntidade[] metodos = gettersDosAtributos.toArray(new MetodoDeEntidade[gettersDosAtributos.size()]);
		for (int i=0; i < metodos.length; i++) {
			MetodoDeEntidade metodo = metodos[i];
			Object valor = null;
			try {
				Class tipo = null;
				valor = metodo.chamar(o);
				tipo = metodo.get().getReturnType();
				if (metodo.isChaveEstrangeira()) {
					Method m = getMetodo(tipo, TipoMetodo.GET, "id");
					if (existe(valor)) {
						valor = m.invoke(valor);
					}
					tipo = m.getReturnType();
				}
				
				setParametroDeComandoPreparadoPorTipo(comandoPreparado, tipo, valor, i+1);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void preencherFiltros(PreparedStatement comandoPreparado, T o, List<ParametroParaComandoParametrizado> filtros) throws SQLException {
		Class classe = o.getClass();
		List<MetodoDeEntidade> gettersDosAtributos = new ArrayList<>();
		for (Field campo : classe.getDeclaredFields()) {
			if (GeradorSQLBean.getInstancia(classe).ehChavePrimaria(campo)) {
				MetodoDeEntidade metodo = null;

				String campoGetter = (GeradorSQLBean.getInstancia(o.getClass()).getCampoNormal(campo));
				Character primeiroCaracter = campoGetter.charAt(0);
				
				String prefixoGet = "";
				if (campo.getType() == Boolean.class || campo.getType() == boolean.class) {
					prefixoGet = "is";
				} else {
					prefixoGet = "get";
				}
				
				campoGetter = prefixoGet+primeiroCaracter.toUpperCase(primeiroCaracter)+campoGetter.substring(1);
				
				Method metodoTemp;
				try {
					metodoTemp = o.getClass().getMethod(campoGetter);
					metodo = new MetodoDeEntidade(false, false, metodoTemp, o);
					gettersDosAtributos.add(metodo);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		if (possuiHeranca) {
			addCamposHerdadosAListaDeMetodosGetters(o.getClass(), gettersDosAtributos);
		}
		
		//Depois, iteramos os getters ja verificados e pegamos seus valores
		MetodoDeEntidade[] metodos = gettersDosAtributos.toArray(new MetodoDeEntidade[gettersDosAtributos.size()]);
		for (int i=0; i < metodos.length; i++) {
			MetodoDeEntidade metodo = metodos[i];
			Object valor = null;
			try {
				Class tipo = null;
				valor = metodo.chamar(o);
				tipo = metodo.get().getReturnType();
				if (metodo.isChaveEstrangeira()) {
					Method m = tipo.getMethod("getId");
					if (existe(valor)) {
						valor = m.invoke(valor);
					}
					tipo = m.getReturnType();
				}

				setParametroDeComandoPreparadoPorTipo(comandoPreparado, tipo, valor, i+1);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	

	protected boolean verificaSePossuiHeranca(Class classeASerVerificada) {
		return GeradorSQLBean.possuiHeranca(classeASerVerificada);
	}
	

	public void preencherExclusao(PreparedStatement comandoPreparado, T o) {
		Method metodoGetId;
		try {
			metodoGetId = getMetodo(o.getClass(), TipoMetodo.GET, "id");
			if (existe(metodoGetId)) {
				Long id = (Long) metodoGetId.invoke(o, null);
				comandoPreparado.setLong(1, id);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public void addCamposHerdadosAListaDeMetodosSetters(Class classeASerVerificada, List<Method> settersDosAtributos, List<Field> atributos) {
		Class entidadeMae = classeASerVerificada.getSuperclass();
		
		Field[] campos = entidadeMae.getDeclaredFields();
		for (int i = 0; i < campos.length; i++) {
			Field campo  = campos[i];
			Class tipoCampo = campo.getType();
			
			String campoSetter = (GeradorSQLBean.getInstancia(classeASerVerificada.getClass()).getCampoNormal(campo));
			Character primeiroCaracter = campoSetter.charAt(0);
			campoSetter = "set"+primeiroCaracter.toUpperCase(primeiroCaracter)+campoSetter.substring(1);
			try {
				Method metodo = entidadeMae.getMethod(campoSetter, tipoCampo);
				settersDosAtributos.add(metodo);
				atributos.add(campo);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				System.out.println("O Metodo nao existe ou esta com outra nomenclatura. Implemente o getter com a IDE Eclipse");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		
		if (verificaSePossuiHeranca(entidadeMae)) {
			addCamposHerdadosAListaDeMetodosSetters(entidadeMae, settersDosAtributos, atributos);
		}
	}
	

	protected void verificaSeOModeloEstaPreenchido(EntidadeModelo objeto)  {
		if (naoExiste(objeto)) {
			throw new ErroObjetoNaoPreenchido(objeto);
		}
	}
	
}
