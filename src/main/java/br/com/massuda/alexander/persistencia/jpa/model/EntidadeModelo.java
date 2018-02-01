package br.com.massuda.alexander.persistencia.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class EntidadeModelo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;

	public Long getId() {
		return id;
	}

	public EntidadeModelo setId(Long id) {
		this.id = id;
		return this;
	}
	
}
