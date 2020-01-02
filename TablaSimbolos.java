package Practica;

import java.util.ArrayList;
//import java.util.HashMap;
//import Practica.Pair;

public class TablaSimbolos {
	
	private String nombre;
	private int num;
	private ArrayList <String> cont;
	
	public TablaSimbolos(String nombre, int num, ArrayList <String> cont) {
		this.nombre = nombre + "#" + num;
		this.num = num;
		
		cont.add(0, "boolean");
		cont.add(1, "for");
		cont.add(2, "function");
		cont.add(3, "if");
		cont.add(4, "input");
		cont.add(5, "int");
		cont.add(6, "print");
		cont.add(7, "return");
		cont.add(8, "string");
		cont.add(9, "var");
		
		this.cont = cont;
	}
	
	public void anadir(String a) {
		cont.add(a);
	}
	
	public int buscar(String nombre) {
		for (int i = 0; i < cont.size(); i++) {
			if (nombre.equals(cont.get(i))){							// Si coincide con lexema de cierta posición
				return i;
			}
		}
		return -1;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public int getNum() {
		return this.num;
	}
	
	public ArrayList<String> getCont(){
		return this.cont;
	}

}
