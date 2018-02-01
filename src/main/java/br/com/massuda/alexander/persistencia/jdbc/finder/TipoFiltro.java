package br.com.massuda.alexander.persistencia.jdbc.finder;

public enum TipoFiltro {

	IGUAL_A				("="),
	DIFERENTE_DE		("<>"),
	MAIOR_QUE			(">"),
	MENOR_QUE			("<"),
	MAIOR_OU_IGUAL_A	(">="),
	MENOR_OU_IGUAL_A	("<="),
	COMO				(" like ");
	
	private String textoCondicional;
	
	private TipoFiltro(String textoCondicional) {
		this.textoCondicional  = textoCondicional;
	}

	public String getTextoCondicional() {
		return textoCondicional;
	}
	
}
