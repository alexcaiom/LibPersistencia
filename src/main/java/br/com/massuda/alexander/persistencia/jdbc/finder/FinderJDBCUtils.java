package br.com.massuda.alexander.persistencia.jdbc.finder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.massuda.alexander.persistencia.jdbc.DAOGenericoJDBCImpl;
import br.com.massuda.alexander.persistencia.jdbc.GeradorSQLBean;
import br.com.massuda.alexander.persistencia.jdbc.anotacoes.ChaveEstrangeira;

public class FinderJDBCUtils<T> extends DAOGenericoJDBCImpl<T> {
	
	public List<Filtro> filtros = new ArrayList<>();

	public FinderJDBCUtils(Class entidade) {
		super(entidade);
	}

	protected void preencher(T o) throws SQLException {
		preencher(o, resultados);
	}
	
	protected void preencher(T o, ResultSet resultados) throws SQLException {
		Field[] campos = o.getClass().getDeclaredFields();

		List<Field> atributos = new ArrayList<Field>();
		List<Method> settersDosAtributos = new ArrayList<Method>();
		//Primeiro, verificamos os campos e se existe heranca
		captarMetodosGet(o, campos, atributos, settersDosAtributos);

		if (possuiHeranca) {
			addCamposHerdadosAListaDeMetodosSetters(o.getClass(), settersDosAtributos, atributos);
		}

		//Depois, iteramos os getters ja verificados e pegamos seus valores
		Method[] metodos = settersDosAtributos.toArray(new Method[settersDosAtributos.size()]);
		for (int i=0; i < metodos.length; i++) {
			Method metodo = metodos[i];
			Field campo = atributos.get(i);
			boolean anotado = GeradorSQLBean.getInstancia(entidade).ehChaveEstrangeira(campo)
					           && campo.isAnnotationPresent(ChaveEstrangeira.class);
			String nomeDoCampoNoBancoDeDados = anotado ? GeradorSQLBean.getInstancia(entidade).getCampo(campo, false) : "";
			setValorDoAtributoPorTipo(resultados, campo.getType(), campo.getName(), nomeDoCampoNoBancoDeDados, metodo, o, anotado);
		}
	}

	private void captarMetodosGet(T o, Field[] campos, List<Field> atributos, List<Method> settersDosAtributos) {
		for (int i = 0; i < campos.length; i++) {
			Field campo  = campos[i];
			if (!GeradorSQLBean.campoDeveSerPersistido(campo)) { continue; }
			String campoSetter = (GeradorSQLBean.getInstancia(o.getClass()).getCampoNormal(campo));
			Character primeiroCaracter = campoSetter.charAt(0);
			Class tipoCampo = campo.getType();
			campoSetter = "set"+primeiroCaracter.toUpperCase(primeiroCaracter)+campoSetter.substring(1);
			try {
				Method metodo = o.getClass().getMethod(campoSetter, tipoCampo);
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
	}
	
	public void setValorDoAtributoPorTipo(ResultSet resultado, Class tipoCampo, String nome, String nomeBD, Method setter, T o, Boolean anotado) throws SQLException {
		try {
			Object valor = null;
			if (tipoCampo == Integer.class || tipoCampo == int.class) {
				valor = resultado.getInt(nome);
				setter.invoke(o, valor);
			} else if (tipoCampo == Long.class || tipoCampo == long.class) {
				valor = resultado.getLong(nome);
				setter.invoke(o, valor);
			} else if (tipoCampo == Double.class || tipoCampo == double.class) {
				valor = resultado.getDouble(nome);
				setter.invoke(o, valor);
			} else if (tipoCampo == Float.class || tipoCampo == float.class) {
				valor = resultado.getFloat(nome);
				setter.invoke(o, valor);
			} else if (tipoCampo == Boolean.class || tipoCampo == boolean.class) {
				valor = resultado.getBoolean(nome);
				setter.invoke(o, valor);
			} else if (tipoCampo == String.class) {
				valor = resultado.getString(nome);
				setter.invoke(o, valor);
			} else if (tipoCampo == Calendar.class || tipoCampo == Date.class) {

				java.sql.Date dataBD = null;
				if (tipoCampo == Calendar.class) {
					Calendar data = GregorianCalendar.getInstance();
					dataBD = resultado.getDate(nome);
					if (existe(dataBD)) {
						data.setTimeInMillis(dataBD.getTime());
						valor = data;
					}
				} else if (tipoCampo == Date.class) {
					dataBD = resultado.getDate(nome);
					if (existe(dataBD)) {
						Date data = new Date(dataBD.getTime());
						valor = data;
					}
				}
				setter.invoke(o, valor);
			} else if (existe(tipoCampo) && tipoCampo.isEnum()) {
				String nameEnum = resultado.getString(nome);
				if (existe(nameEnum)) {
					valor = Enum.valueOf(tipoCampo, nameEnum);
				}
				setter.invoke(o, valor);
			} else if (existe(tipoCampo) && (tipoCampo.getName().startsWith("com") || tipoCampo.getName().startsWith("br")) && !tipoCampo.getClass().isEnum()) {
				System.out.println("Chave estrangeira: " + nome);
				boolean temAtributoId = false;
				try {
					//FIXME este trecho de Codigo nao cobre herancas profundas
					boolean temHeranca = verificaSePossuiHeranca(tipoCampo);
					if (temHeranca) {
						Class mae = tipoCampo.getSuperclass();
						while(verificaSePossuiHeranca(mae)){
							mae = mae.getSuperclass();
						}
						temAtributoId = existe(mae.getDeclaredField("id"));
					} else {
						temAtributoId = existe(tipoCampo.getDeclaredField("id"));
					}
					//FIXME este trecho de Codigo nao cobre herancas profundas
				}
				catch (NoSuchFieldException e) {
					e.printStackTrace(); 
				}
				catch (SecurityException e) {  
					e.printStackTrace();  
				}
				
				if (temAtributoId) {
					Object entidadeRelacionada = tipoCampo.newInstance();
					Method metodoSetId = null;
					if (!possuiHeranca) {
						metodoSetId = entidadeRelacionada.getClass().getMethod("setId", Long.class);
					} else {
						Class mae = entidadeRelacionada.getClass().getSuperclass();
						while(verificaSePossuiHeranca(mae)){
							mae = mae.getSuperclass();
						}
						metodoSetId = mae.getMethod("setId", Long.class);
					}
					String sufixo_id = (!anotado) ? "_id" : "";
					nomeBD = nomeBD.isEmpty() ? nome + sufixo_id : nomeBD;
					Long id = resultado.getLong(nomeBD);
					metodoSetId.invoke(entidadeRelacionada, id);
					
					Method metodoSetEntidade = null;
					try {//Tentamos obter o setter da chave estrangeira. Se for o mesmo tipo de entidade, podemos usar o modo normal.
						metodoSetEntidade =  o.getClass().getMethod("set"+tipoCampo.getSimpleName(), tipoCampo);
					} catch (NoSuchMethodException eSemMetodo){
						metodoSetEntidade = o.getClass().getMethod("set"+nome.substring(0, 1).toString().toUpperCase()+nome.substring(1), tipoCampo);
					}
					
					metodoSetEntidade.invoke(o, entidadeRelacionada);
				}
				
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
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
	}
	
	
}