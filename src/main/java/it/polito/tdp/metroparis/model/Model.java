package it.polito.tdp.metroparis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	Graph<Fermata, DefaultEdge> grafo ;
	Map<Fermata,Fermata> predecessore;

	public void creaGrafo() {
		this.grafo = new SimpleGraph<>(DefaultEdge.class) ;
		
		MetroDAO dao = new MetroDAO() ;
		List<Fermata> fermate = dao.getAllFermate() ;
				
		Graphs.addAllVertices(this.grafo, fermate) ;
			
		List<Connessione> connessioni = dao.getAllConnessioni(fermate) ;
		for(Connessione c: connessioni) {
			this.grafo.addEdge(c.getStazP(), c.getStazA()) ;
		}
		
		System.out.format("Grafo creato con %d vertici e %d archi\n",
				this.grafo.vertexSet().size(), this.grafo.edgeSet().size()) ;
		//System.out.println(this.grafo) ;
	}
	public List<Fermata> FermateAdiacenti(Fermata partenza){
		
		BreadthFirstIterator<Fermata,DefaultEdge> bfi = new 
				BreadthFirstIterator<Fermata,DefaultEdge>(this.grafo,partenza);
		
		predecessore = new HashMap<>();
		predecessore.put(partenza, null);
		
		bfi.addTraversalListener(new TraversalListener<Fermata,DefaultEdge>(){

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				
				DefaultEdge edge = e.getEdge();
				Fermata a = grafo.getEdgeSource(edge);
				Fermata b = grafo.getEdgeTarget(edge);
				
				if(predecessore.containsKey(b) && !predecessore.containsKey(a)) {
					//System.out.println("Conosco "+a.getNome()+" da "+b.getNome());
					predecessore.put(a, b);
				}
				else if(predecessore.containsKey(a) && !predecessore.containsKey(b)) {
					//System.out.println("Conosco "+b.getNome()+" da "+a.getNome());
					predecessore.put(b, a);
				}
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Fermata> e) {
				
				
//				Fermata nuova = e.getVertex();
//				Fermata prima = 
//						
//				if(!predecessore.containsKey(nuova)) {
//					predecessore.put(nuova, prima);
//				}
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Fermata> e) {
				// TODO Auto-generated method stub
				
			}});
		
//		DepthFirstIterator<Fermata,DefaultEdge> dfi = new
//				DepthFirstIterator<Fermata,DefaultEdge>(this.grafo,partenza);
//		
		List<Fermata> result = new ArrayList<Fermata>();
		while(bfi.hasNext()) {
			Fermata f = bfi.next();
			result.add(f);
		}
		
		return result;
	}
	public Fermata getFermata(String s) {
		
		for(Fermata f: this.grafo.vertexSet()) {
			if(f.getNome().equals(s)) {
				return f;
			}
		}
		return null;
	}
	
	public List<Fermata> TrovaCammino(Fermata partenza, Fermata arrivo){
		
		
		
		//List<Fermata> AdiacentiIniziale = new ArrayList<>(FermateAdiacenti(partenza));
		
		List<Fermata> Cammino = new LinkedList<>();
		Cammino.add(arrivo);
		Fermata f = arrivo;
		while(predecessore.get(f)!=null) {
			f = predecessore.get(f);
			Cammino.add(f);
		}
		
		return Cammino;
	}
}
