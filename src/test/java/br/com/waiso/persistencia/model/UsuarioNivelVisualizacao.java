package br.com.waiso.persistencia.model;
/**
 * @author Alex
 *
 */
public enum UsuarioNivelVisualizacao {

	PUBLICO(3, "Público"),
	AMIGOS(2, "Amigos"),
	PRIVADO(1, "Público");
	
	int cod;
	String nome;
	
	UsuarioNivelVisualizacao(int cod, String nome) {
		this.cod = cod;
		this.nome = nome;
	}

	/**
	 * @return the cod
	 */
	public final int getCod() {
		return cod;
	}

	/**
	 * @param cod the cod to set
	 */
	public final void setCod(int cod) {
		this.cod = cod;
	}

	/**
	 * @return the nome
	 */
	public final String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public final void setNome(String nome) {
		this.nome = nome;
	}
	
	public static UsuarioNivelVisualizacao getVisualizacao(int cod){
		for(UsuarioNivelVisualizacao v: values()){
			if(v.getCod() == cod){
				return v;
			}
		}
		return null;
	}
	
}