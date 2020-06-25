package it.polito.tdp.ufo.model;

public class AnnoAvvistamento {
	private Integer anno;
	private Integer avvistamento;
	

	public AnnoAvvistamento(Integer anno, Integer avvistamento) {
		super();
		this.anno = anno;
		this.avvistamento = avvistamento;
	}

	public Integer getAnno() {
		return anno;
	}


	public Integer getAvvistamento() {
		return avvistamento;
	}



	@Override
	public String toString() {
		return  anno + " -nAvvistamenti: " + avvistamento;
	}
	
	

}
