package br.com.massuda.alexander.persistencia.jdbc;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.massuda.alexander.persistencia.jdbc.anotacoes.ChaveEstrangeira;
import br.com.massuda.alexander.persistencia.jdbc.anotacoes.ChavePrimaria;
import br.com.massuda.alexander.persistencia.jdbc.anotacoes.Coluna;
import br.com.massuda.alexander.persistencia.jdbc.anotacoes.Tabela;
import br.com.massuda.alexander.persistencia.jdbc.utils.MetodoDeEntidade;
import br.com.waiso.framework.abstratas.Classe;
import br.com.waiso.framework.abstratas.TipoMetodo;

/**
 * Esta classe realiza a leitura do Bean passado
 * e cria as queries de criação de entidade baseado
 * nos atributos dela.
 * @author Alex
 *
 */
public class GeradorSQLBean<T> extends Classe {

	private String tabela = ""; 
	private static final String CREATE_TABLE_SIMPLES = "create table ";
	private static final String DROP_TABLE_SIMPLES = "drop table ";
	private static final String INSERT_INTO_SIMPLES = "insert into ";
	private static final String UPDATE_SIMPLES = "update ";
	private static final String DELETE_FROM_SIMPLES = "delete from ";
	private static final String SELECT_FROM_SIMPLES = "select * from ";
	private final Class classeObjeto;

	private final List<Field> campos = new ArrayList<Field>();
	private final List<Field> camposPK = new ArrayList<Field>();
	private final List<Field> camposFK = new ArrayList<Field>();
	private boolean possuiHeranca = false;

	GeradorSQLBean(Class objeto){
		this.classeObjeto = objeto;
		if(this.classeObjeto != null){
			//Primeiro pegamos o nome da Tabela
			tabela = getNomeTabela();
			verificaCamposComChavePrimaria(objeto);

			for (Field campo : this.classeObjeto.getDeclaredFields()) {
				campos.add(campo);
			}
			possuiHeranca = possuiHeranca(this.classeObjeto);
			if (possuiHeranca) {
				addCamposHerdadosAListaDeMetodosGetters(this.classeObjeto);
			}
		}
	}

	private void verificaCamposComChavePrimaria(Class objeto) {
		for(Field campo: objeto.getDeclaredFields()){
			boolean temAnotacaoDeChavePrimaria = existe(campo.getAnnotation(ChavePrimaria.class));
			if(temAnotacaoDeChavePrimaria){
				camposPK.add(campo);
			}
		}

		boolean classeTemHeranca = objeto.getSuperclass() != Object.class;
		if (classeTemHeranca) {
			Class mae = objeto.getSuperclass();
			verificaCamposComChavePrimaria(mae);
		}
	}

	/**
	 * @param comando
	 * @param fks
	 * @param campo
	 * @param anotacoes
	 * @param tipoCampo
	 */
	public String getCampo(Field campo, boolean completo){
		StringBuilder txtcampo = new StringBuilder();
		if(!campo.getName().contains("serialVersionUID")){
			boolean ehChaveEstrangeira = campo.isAnnotationPresent(ChaveEstrangeira.class);
			String tipoCampo = campo.getType().getName();

			boolean pegarCampoNormal = !(ehChaveEstrangeira || tipoCampo.startsWith("com") || tipoCampo.startsWith("java.util")) || campo.getAnnotations().length == 0
					|| campo.getType().isPrimitive();

			/** Verificamos se o tipo do campo come�a com 'com',
			 * um indicativo de que estamos lidando com uma chave
			 * Estrangeira (FK)
			 */
			if(pegarCampoNormal){
				txtcampo.append(getCampoNormal(campo).trim());
				if (completo) {
					txtcampo.append(" ").append(getTipoCampo(campo).trim()).append(" ").append(getAtributosDeAnotacoes(campo.getAnnotations()).trim());
				}
			}else {
				txtcampo.append(getCampoChaveEstrangeira(campo).trim());
				if (completo) {
					txtcampo.append(" ").append(getTipoCampo(campo).trim()).append(" ").append(getAtributosDeAnotacoes(campo.getAnnotations()).trim());
				}
			}
		}

		return txtcampo.toString();
	}

	public String getCampoNormal(Field campo){
		StringBuilder txtcampo = new StringBuilder();
		boolean campoTemAnotacaoColuna = campo.isAnnotationPresent(Coluna.class);
		if (campoTemAnotacaoColuna) {
			Coluna coluna = campo.getAnnotation(Coluna.class);
			return coluna.nome();
		}
		/**
		 * No caso de Chave Estrangeira, fazemos a extra��o do ID
		 * do tipo informado
		 */
		txtcampo.append(campo.getName());
		return txtcampo.toString();
	}
	private String getCampoChaveEstrangeira(Field campo){
		StringBuilder txtcampo = new StringBuilder();
		if (campo.isEnumConstant()) {
			return campo.getName();
		}
		boolean campoTemAnotacaoChaveEstrangeira = campo.isAnnotationPresent(ChaveEstrangeira.class);
		if (campoTemAnotacaoChaveEstrangeira) {
			ChaveEstrangeira chaveEstrangeira = campo.getAnnotation(ChaveEstrangeira.class);
			return chaveEstrangeira.nome();
		}
		/**
		 * No caso de Chave Estrangeira, fazemos a extra��o do ID
		 * do tipo informado
		 */
		if((campo.getType().isInterface() && (campo.getType().getSimpleName().equalsIgnoreCase("List") || 
				campo.getType().getSimpleName().equalsIgnoreCase("List"))) || campo.getType().getName().contains("com")){
			txtcampo.append(campo.getName()).append("_id ");
		}
		return txtcampo.toString();
	}

	/**
	 * Campo que interpreta a chave estrangeira,
	 * construindo a constraint
	 * @param campo
	 * @return
	 */
	private String getChaveEstrangeira(Field campo){
		if(campo != null){
			String nomeCampo = campo.getName();

			StringBuilder constraint = new StringBuilder();
			/**
			 * Se o nosso campo tiver implementado uma Interface com Generics,
			 * detectamos aqui e j� extraimos o tipo do campo.
			 */

			String tipoCampo = campo.getType().getSimpleName();

			constraint.append("constraint fk_").append(tipoCampo).append("_").append(campo.getDeclaringClass().getSimpleName());
			constraint.append(" (").append(nomeCampo).append(") references ").append("tb_").append(tipoCampo);
			constraint.append(" (id)");

			return constraint.toString();
		}
		return "";
	}

	/**
	 * Metodo que transforma os tipos de dados
	 * do Java em tipos de dado de Banco
	 * @param campo
	 * @return
	 */
	private String getTipoCampo(Field campo) {
		Class classeTipo = null;
		if(!campo.getType().isPrimitive()){
			classeTipo = campo.getType();
			if(classeTipo == String.class){
				return "VARCHAR";
			} else if(classeTipo == Long.class || classeTipo == Boolean.class || classeTipo == Integer.class){
				return "INTEGER";
			} else if(classeTipo.getName().contains("com.")){
				return "INTEGER";
			} else if (classeTipo == Calendar.class) {
				return "DATETIME";
			} else if (classeTipo == BigDecimal.class) {
				return "DECIMAL";
			}
		} else {
			if(!campo.getName().contains("serialVersionUID")){
				if(campo.getType().getSimpleName().equalsIgnoreCase(long.class.getSimpleName()) ||
						campo.getType().getSimpleName().equalsIgnoreCase(boolean.class.getSimpleName()) ||
						campo.getType().getSimpleName().equalsIgnoreCase(int.class.getSimpleName())){
					return "INTEGER";
				}
			}
		}
		return "";
	}

	/**
	 * Caso o Bean tenha anota��es, estas s�o interpretadas
	 * para atributos de coluna no banco de dados.
	 * @param anotacoes
	 * @return
	 */
	private String getAtributosDeAnotacoes(Annotation[] anotacoes) {
		StringBuilder sb = new StringBuilder();
		String pk = "";
		StringBuilder nullable = new StringBuilder("");
		String auto_incremento = "";
		String unique = "";

		if(anotacoes != null && anotacoes.length > 0){
			for(Annotation anotacao: anotacoes){
				if(anotacao instanceof Coluna){
					Coluna a = (Coluna) anotacao;
					if(!a.anulavel()){
						nullable.insert(0, " not null");
					}
					/*if(a.generatedId()){

					}*/
					if(a.unica()){
						unique = " unique";
					}
				} else if (anotacao instanceof ChavePrimaria) {
					if (((ChavePrimaria)anotacao).autoIncremental()) {
						auto_incremento =" autoincrement"; 
					}
					pk = " primary key";

				}
			}
		}

		sb.append(pk).append(nullable.toString()).append(auto_incremento).append(unique);

		return sb.toString();
	}

	public String getComandoTabelaCriacao() {
		//Iniciando o comando
		StringBuilder comando = getComandoInicial(CREATE_TABLE_SIMPLES).append(" (");

		//Colocando os nomes dos campos
		//		System.out.println("Campos: ");
		Field[] campos = classeObjeto.getDeclaredFields();
		List<Field> fks = new ArrayList<Field>();

		/**
		 * 1a Parte - Cuidamos dos campos
		 */
		int quantidadeDeCampos = 0;
		for(int cont=0; cont < campos.length; cont++){

			Field campo = campos[cont];
			Annotation[] anotacoes = campo.getAnnotations();
			if(!getCampo(campo, false).equals("serialVersionUID") && !getCampo(campo, false).trim().isEmpty()){
				String nomeCampo = campo.getName();
				String tipoCampo = campo.getType().getName();
				//				System.out.println(nomeCampo + " (" + campo.getType().getSimpleName() + ")");

				StringBuilder linhaCampo = new StringBuilder();

				linhaCampo.append(getCampo(campo, true));

				if(!linhaCampo.toString().trim().isEmpty()){
					if(quantidadeDeCampos > 0){
						comando.append(", \n");
					} else{
						comando.append(" \n");
					}
					comando.append(linhaCampo.toString());
				}

				/** Verificamos se o tipo do campo come�a com 'com',
				 * um indicativo de que estamos lidando com uma chave
				 * Estrangeira (FK)
				 */
				if(tipoCampo.startsWith("com")){
					fks.add(campo);
				}

				quantidadeDeCampos++;
			}
		}

		//		System.out.println();
		/**
		 * 2a Parte: Agora lidamos com as FKs
		 */
		/*for(Field f: fks){
				comando.append(",\n").append(getChaveEstrangeira(f));
			}*/

		comando.append(")");

		//		System.out.println(comando);


		return comando.toString();
	}

	public String getComandoTabelaExclusao(){
		return getComandoInicial(DROP_TABLE_SIMPLES).toString();
	}

	public String getComandoInsercao(Object o){
		StringBuilder comando = new StringBuilder(getComandoInicial(INSERT_INTO_SIMPLES));

		//Primeiro, cuidamos de pegar todas as colunas
		comando.append(" (");

		List<MetodoDeEntidade> gettersDosAtributos = new ArrayList<>();
		boolean primeiroCampo = true;
		for (Field campo : campos) {
			boolean ehChavePrimaria = ehChavePrimaria(campo);
			boolean ehChaveEstrangeira = ehChaveEstrangeira(campo);
			if (ehChavePrimaria && ehChavePrimariaAutoIncremental(campo)) {
				continue;
			} 

			//Verifica se o atributo corrente esta preenchido
			MetodoDeEntidade metodo = null;
			boolean campoEstaPreenchido = false;
			try {
				Method metodoGet = getMetodo(o.getClass(), TipoMetodo.GET, campo.getName());
				
				metodo = new MetodoDeEntidade(ehChavePrimaria, ehChaveEstrangeira, metodoGet);
				metodo.setCampoDoMetodo(campo);
				campoEstaPreenchido = existe(metodoGet.invoke(o, null));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			if (!campoEstaPreenchido) {
				continue;
			}

			if (!primeiroCampo) {
				comando.append(", ");
			}

			if (ehChaveEstrangeira) {
				comando.append(getCampoChaveEstrangeira(campo));
			} else {
				comando.append(getCampoNormal(campo));
			}
			primeiroCampo = false;

			gettersDosAtributos.add(metodo);
		}


		comando.append(") ");
		comando.append("values");

		//Entao, pegamos seus respectivos valores
		comando.append(" (");
		//Neste passo, pegamos os getters
		primeiroCampo = true;
		for (int i=0; i < gettersDosAtributos.size(); i++) {
			boolean ehChavePrimaria = ehChavePrimaria(gettersDosAtributos.get(i).getCampoDoMetodo());
			if (ehChavePrimaria) {
				if (ehChavePrimariaAutoIncremental(gettersDosAtributos.get(i).getCampoDoMetodo())) {
					continue;
				}
			}
			if (!primeiroCampo) {
				comando.append(", ");
			}
			comando.append("?");
			primeiroCampo = false;
		}
		comando.append(") ");

		return comando.toString();
	}


	public boolean ehChavePrimariaAutoIncremental(Field campo) {
		ChavePrimaria anotacao = null;
		if (ehChavePrimaria(campo)) {
			anotacao = campo.getAnnotation(ChavePrimaria.class);
		}
		return existe(anotacao) && anotacao.autoIncremental();
	}

	public boolean ehChavePrimaria(Field campo) {
		return campo.isAnnotationPresent(ChavePrimaria.class);
	}

	public boolean ehChaveEstrangeira(Field campo) {
		boolean temAnotacaoChaveEstrangeira = campo.isAnnotationPresent(ChaveEstrangeira.class);
		if (temAnotacaoChaveEstrangeira) {
			ChaveEstrangeira chaveEstrangeira = campo.getAnnotation(ChaveEstrangeira.class);
			return !chaveEstrangeira.estaEmOutraTabela(); 
		}
		String tipoCampo = campo.getType().getName();
		return (tipoCampo.startsWith("br") || tipoCampo.startsWith("com") || tipoCampo.startsWith("java.util")) && campo.getType() != Calendar.class && campo.getType() != Date.class;
	}

	public String getComandoAtualizacao(T o){
		if (naoExiste(o)) {
			return "";
		}
		String comando = getComandoInicial(UPDATE_SIMPLES).toString();
		boolean primeiroCampo = true;
		StringBuilder parametros = new StringBuilder();
		Field[] campos = classeObjeto.getDeclaredFields();
		for (int i = 0; i < campos.length; i++) {
			if (primeiroCampo) {
				parametros.append("\nset ");
			} else {
				parametros.append(", \n");
			}

			boolean chavePrimaria = ehChavePrimaria(campos[i]);
			boolean chaveEstrangeira = ehChaveEstrangeira(campos[i]);

			if (!(chavePrimaria || chaveEstrangeira)) {
				String nomeCampo = getCampoNormal(campos[i]);
				parametros.append(nomeCampo).append(" = ?");
			} else if (chaveEstrangeira) {
				parametros.append(getCampoChaveEstrangeira(campos[i])).append(" = ?");
			}
			primeiroCampo = false;
		}

		StringBuilder filtro = new StringBuilder("\nwhere ");
		for (Field pks : camposPK) {
			boolean primeiraPK = true;
			if (!primeiraPK) {
				filtro.append("\n and ");
			}

			filtro.append(getCampoNormal(pks)).append(" = ?");
			primeiraPK = false;
		}

		return comando + parametros.toString() + filtro.toString();
	}

	public String getComandoExclusao(){
		String comando = getComandoInicial(DELETE_FROM_SIMPLES).toString();
		return comando;
	}

	public String getComandoSelecao(){
		String comando = getComandoInicial(SELECT_FROM_SIMPLES).toString();
		return comando;
	}

	/**
	 * @return
	 */
	public String getNomeTabela() {
		boolean temAnotacaoDeTabela = existe(classeObjeto.getAnnotation(Tabela.class));
		if (temAnotacaoDeTabela) {
			Tabela t = (Tabela) classeObjeto.getAnnotation(Tabela.class);
			tabela = t.nome();
		} else {
			tabela = "tb" + classeObjeto.getSimpleName().toLowerCase(new Locale("pt", "BR"));
		}
		return tabela;
	}



	private void addCamposHerdadosAListaDeMetodosGetters(Class classeDoObjetoFilho) {
		Class mae = classeDoObjetoFilho.getSuperclass();

		if (existe(mae)) {
			for (Field campoMae : mae.getDeclaredFields()) {
				campos.add(campoMae);
			}

			if (possuiHeranca(mae)) {
				addCamposHerdadosAListaDeMetodosGetters(mae);
			}
		}
	}

	protected static boolean possuiHeranca(Class classeASerVerificada) {
		return existe(classeASerVerificada.getSuperclass()) && (classeASerVerificada.getSuperclass() != Object.class);
	}

	public static boolean campoDeveSerPersistido(Field campo) {
		if (naoExiste(campo.getAnnotation(Coluna.class))) {
			return true;
		} else {
			return campo.getAnnotation(Coluna.class).deveSerPersistida(); 
		}
	}

	private StringBuilder getComandoInicial(String qualComando){
		StringBuilder sb = new StringBuilder(qualComando);
		sb.append(getNomeTabela());
		return sb;
	}

	/**
	 * Obtenha uma instancia do Gerador de SQL para trabalhar com a classe parametro informada (Tipo)
	 * @param tipo
	 * @return new GeradorSQLBean
	 */
	public static <T> GeradorSQLBean<T> getInstancia(Class<T> tipo){
		return new GeradorSQLBean<T>(tipo);
	}

	public static void main(String[] args) {
		//		System.out.println(GeradorSQLBean.getInstancia(Usuario.class).getCreateTable());
		//		System.out.println(GeradorSQLBean.getInstancia(Usuario.class).getDropTable());
		//		System.out.println(GeradorSQLBean.getInstancia(Usuario.class).getInsert());
	}

	public List<Field> getCamposPK() {
		return camposPK;
	}

	public List<Field> getCamposFK() {
		return camposFK;
	}

}