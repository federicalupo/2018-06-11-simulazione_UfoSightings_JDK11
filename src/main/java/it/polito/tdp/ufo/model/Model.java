package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.List;


import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	private SightingsDAO dao;
	private Graph<String, DefaultEdge> grafo;
	
	public Model() {
		dao = new SightingsDAO();
	}

	public List<AnnoAvvistamento> tendina(){
		return dao.tendina();
	}
	
	public void creaGrafo(Integer anno) {
		this.grafo = new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		
		Graphs.addAllVertices(this.grafo, dao.vertici(anno));
		
		for(Arco a : dao.archi(anno)) {
			grafo.addEdge(a.getStato1(), a.getStato2());
		}
	}

	public Integer nVertici() {
		
		return this.grafo.vertexSet().size();
	}

	public Integer nArchi() {
		
		return grafo.edgeSet().size();
	}
	
	public List<String> vertici(Integer anno){
		return dao.vertici(anno);
	}
	
	public List<String> precedenti(String stato){
		
		return Graphs.predecessorListOf(this.grafo, stato);
	}
	
	public List<String> successivi(String stato){
		return Graphs.successorListOf(this.grafo, stato);
	}
	
	public List<String> statiRaggiungibili(String stato){
		GraphIterator<String, DefaultEdge> bfv = new BreadthFirstIterator<String, DefaultEdge>(this.grafo, stato);
		List<String> raggiungibili = new ArrayList<>();
		
		while(bfv.hasNext()) {
			raggiungibili.add(bfv.next());
			
		}
		
		return raggiungibili;
	}
}
