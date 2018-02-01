package br.com.waiso.persistencia.model;

import br.com.massuda.alexander.persistencia.jdbc.anotacoes.Tabela;
import br.com.massuda.alexander.persistencia.jdbc.model.EntidadeModelo;

@Tabela(nome="tbl_endereco")
public class Endereco extends EntidadeModelo {

	private CoordenadasGeograficas coordenadasGeograficas;
	private Integer numero;
	private String complemento;
	private TipoEndereco tipo;
	
	public CoordenadasGeograficas getCoordenadasGeograficas() {
		return coordenadasGeograficas;
	}
	public void setCoordenadasGeograficas(
			CoordenadasGeograficas coordenadasGeograficas) {
		this.coordenadasGeograficas = coordenadasGeograficas;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public TipoEndereco getTipo() {
		return tipo;
	}
	public void setTipo(TipoEndereco tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return "Endereco [coordenadasGeograficas=" + coordenadasGeograficas
				+ ", numero=" + numero 
				+ ", complemento=" + complemento
				+ ", tipo=" + tipo 
				+ ", id=" + id + "]";
	}
	
}
