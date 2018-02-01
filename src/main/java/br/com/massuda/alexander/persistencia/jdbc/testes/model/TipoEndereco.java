package br.com.massuda.alexander.persistencia.jdbc.testes.model;

public enum TipoEndereco {

	RESIDENCIAL,
	COMERCIAL;

	public static TipoEndereco get(String tipoString) {
		for (TipoEndereco tipo : values()) {
			if (tipo.name().equals(tipoString)) {
				return tipo;
			}
		}
		return null;
	}
	
}
