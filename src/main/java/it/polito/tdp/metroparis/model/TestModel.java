package it.polito.tdp.metroparis.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		Model m = new Model() ;
		
		m.creaGrafo(); 
		Fermata f = m.getFermata("Passy");
		Fermata b = m.getFermata("Vavin");
		
		List<Fermata> fermateadiacenti = m.FermateAdiacenti(f);
		//System.out.println(fermateadiacenti);
		List<Fermata> Cammino = m.TrovaCammino(f,b);
		System.out.println(Cammino);
	}

}
