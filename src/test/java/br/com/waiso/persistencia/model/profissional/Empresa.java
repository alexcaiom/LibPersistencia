package br.com.waiso.persistencia.model.profissional;

import br.com.massuda.alexander.persistencia.jdbc.anotacoes.Tabela;
import br.com.waiso.persistencia.model.Pessoa;
import br.com.waiso.persistencia.model.TipoPessoa;

@Tabela(nome="tbl_empresa")
public class Empresa extends Pessoa {

	private Segmento segmento;
	
	public Empresa() {
		this.tipo = TipoPessoa.PESSOA_JURIDICA;
	}

	public Segmento getSegmento() {
		return segmento;
	}

	public void setSegmento(Segmento segmento) {
		this.segmento = segmento;
	}

	@Override
	public String toString() {
		return "Empresa [segmento=" + segmento + ", id=" + id + ", nome="
				+ nome + ", dataDeNascimento=" + dataDeNascimento + ", email="
				+ email + ", identificacao=" + identificacao + ", tipo=" + tipo
				+ "]";
	}

}
