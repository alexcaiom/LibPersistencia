package br.com.massuda.alexander.persistencia.jdbc.finder;

import java.util.Calendar;
import java.util.Date;

enum TipoDeDado {

	INTEGER			(Integer.class, 	int.class),
	LONG			(Long.class, 		long.class),
	DOUBLE			(Double.class, 		double.class),
	FLOAT			(Float.class, 		float.class),
	BOOLEAN			(Boolean.class, 	boolean.class),
	STRING			(String.class),
	CALENDAR		(Calendar.class, 	Date.class),
	ENUM			(Enum.class);
	
	private Class[] tipos;
	
	private TipoDeDado(Class...tipos) {
		this.tipos = tipos;
	}
	
	public static final TipoDeDado get(Class tipo){
		for (TipoDeDado tipoDeDado : values()) {
			for (Class classe : tipoDeDado.tipos) {
				if (tipo.equals(classe)) {
					return tipoDeDado;
				}
			}
		}
		
		return null;
	}
}