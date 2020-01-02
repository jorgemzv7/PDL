package Practica;

import java.awt.Desktop;
import java.io.*;
import java.util.ArrayList;

public class AnalizadorLexico {
	static int c, num, p;									// Numero de caracter leido, num para tokens digito y p
	static int linea = 1;
	static int estado = 0;									// Indica el estado actual del automata
	static boolean error = false;							
	static String concat = "";								// Para la acc sem de concatenar
	static String accion = "";								// Indica la acc sem 
	final static String ruta= "C:\\Users\\Jorge\\"+
    		"Documents\\Clase\\Tercero\\"
    		+ "PDL\\Práctica\\";
	final static String rutaDestino= "C:\\Users\\Jorge\\"+
    		"Documents\\Clase\\Tercero\\"
    		+ "PDL\\Práctica\\tokens.txt";
	static FileWriter fichero = null;
    static PrintWriter pw = null;
    static TablaSimbolos TS = new TablaSimbolos("Tabla de Símbolos", 0, new ArrayList<String>());
	
	public static void analizadorLexico (String rutaArchivo){
		try(FileReader fr = new FileReader(rutaArchivo)){
			MT_AFD MT_AFD = new MT_AFD (estado,accion);
			c =  fr.read();
	        while(estado<9) {						// LOOP UNTIL ESTADO FINAL
	        	
	        	if (tipoCaracter(c)==3){					// Llega un '/' -> Contemplo comentario
	        		c=fr.read();
        			if(tipoCaracter(c)!=3){					// Comentario de linea mal formado
        				System.err.print("Error léxico: comentario de línea mal formado en línea " + linea);
        			}
        			while(c != 10){							// Hasta salto de línea
        				c=fr.read();
        			}
	        	}											//FIN Contemplo comentarios
	        	if(c==10){linea++;}							//Si cambiamos de linea aumento contador

	        	accion = MT_AFD.accion(estado, tipoCaracter(c));
	        	estado = MT_AFD.estado(estado, tipoCaracter(c));
	        	System.out.println("ASCII "+c+" - Caracter "+(char) c+" - C tipo "+tipoCaracter(c)+
	        			" - Acción "+accion+" - Estado "+estado+" - Línea "+linea + " - num= "+num+ " Concat= " + concat);
	        	
	        	if(accion=="EOF"){										//Compruebo EOF	        		
	        		break;
	        	}		
	        	
	        	switch(accion){
	        	
	        	case "L": c=fr.read(); break;
	        	
	        	case "L,C":
	        		concatenar((char) c); 
	        		num++;
	        		if(concat.length()>64){	
	        			estado=0;
	        			num=0;
	        			concat="";
	        			error=true;
	        			System.err.println("Error léxico 5: Cadena demasiado larga Línea "+linea);
	        			while(tipoCaracter(c)!=5){									//Termino de leer cadena
		        			c=fr.read();
		        		}
	        		}
	        		c=fr.read();
	        		break;
	        		
	        	case "A": num = c - 48; c=fr.read(); break;
	        	
	        	case "B": num = num*10 + c - 48;        	
	        		if(num>32767){
	        			estado=0; 
	        			error=true;
	        			System.err.println("Error léxico 2: Número demasiado grande Línea "+linea);
	        			while(tipoCaracter(c)==2){									//Termino de leer el número
		        			c=fr.read();
		        			//System.out.println("Leo num excesivo " + num);
		        		}
	        		}else{
	        			c=fr.read();
	        		}
	        		break;

	        	case "GT1": c=fr.read(); genToken("EQ"); break;
	        	case "GT2": c=fr.read(); genToken("Coma"); break;
	        	case "GT3": c=fr.read(); genToken("PtyComa"); break;
	        	case "GT4": c=fr.read(); genToken("Parent1"); break;
	        	case "GT5": c=fr.read(); genToken("Parent2"); break;
	        	case "GT6": c=fr.read(); genToken("Corch1"); break;
	        	case "GT7": c=fr.read(); genToken("Corch2"); break;
	        	case "GT8": c=fr.read(); genToken("SUMA"); break;
	        	case "GT9":	genToken("ENT"); break;
	        	case "GT10": c=fr.read(); genToken("DEC"); break;

	        	case "GT11": 
	        		genToken("CADENA");
        			c=fr.read();						// Porque estamos en la última comilla
        			num=0;
	        		break;
	        	
	        	case "GT12": c=fr.read(); genToken("AND"); break;
	        	case "GT13": c=fr.read(); genToken("NotEQ"); break;
	        	
	        	case "GT17":
	        		p = TS.buscar(concat);
	        		if(p != -1 && p < 10) {									// Palabra reservada
	        			System.out.println("p= "+p+" -> Llamo a GTPR");
	        			genToken("PR");
	        		}
	        		else if (p == -1) {										// Identificador nuevo
	        			System.out.println("p= "+p+" -> Añado nuevo ID y llamo a GTVar");
	        			TS.anadir(concat);	
	        			genToken("ID");
	        		}
	        		else if(p != -1 && p >= 10) {							// Identificador ya existente
	        			System.out.println("p= "+p+" -> Llamo a GTVar");
	        			genToken("ID");
	        		}
	        		concat = ""; num = 0;
	        		break;
	        	
	        	case "GT18":
	        	p = TS.buscar(concat);
	        	if (p == -1) {												// Identificador nuevo
        			System.out.println("Añado nuevo ID y llamo a GTVar");
        			TS.anadir(concat);
        			genToken("ID");
        		}
        		else if(p != -1 && p >= 10) {								// Identificador ya existente
        			System.out.println("Llamo a GTVar");
        			genToken("ID");
        		}
	        	concat = "";
	        	break;
	        	
	        	//ERRORES 
	        	
	        	case "0": c=fr.read(); System.err.println("Error al generar token en línea "+linea); error = true; break;
	        	case "3": c=fr.read(); System.err.println("Error léxico 3: autodecremento mal escrito en línea "+linea); error = true; break;
	        	case "7": c=fr.read(); System.err.println("Error léxico 7: operador AND mal escrito en línea "+linea); error = true; break;
	        	case "8": c=fr.read(); System.err.println("Error léxico 8: operador NotEQ mal escrito en línea "+linea); error = true; break;
	       
	        	}
	        							
	        }													// CIERRE DEL WHILE
	        fr.close();
	    }catch(IOException ex){
	        System.err.println("Error al leer el archivo");
	        ex.printStackTrace();
	    }
	}
	
	private static int tipoCaracter (int c){
		if(c==-1){												
			return -1;											//-1 = EOF
		}if(c == 32 || c == 9 || c==13 || c==10){				// SPACE, TAB, CR, NL
			return 0;											// 0 = del
		}if (c > 64 && c < 91 || c > 96 && c < 123){			// Letras mayúsculas y minusculas en ASCII
			return 1;								  			// Devuelve 1 para letra
		}if (c > 47 && c < 58){									// La posición de los dígitos 0-9 en ASCII	
			return 2;											// 2 = dígito {0-9}
		}if (c == 47){
			return 3;											// 3 = '/' 
		}if (c == 45){										
			return 4;											// 4 = '-'
		}if (c == 39){
			return 5;											// 5 = '''
		}if (c == 61){
			return 6;											// 6 = '='
		}if (c == 44){
			return 7;											// 7 = ','
		}if (c == 59){
			return 8;											// 8 = ';'
		}if (c == 40){
			return 9;											// 9 = '('
		}if (c == 41){
			return 10;											// 10 = ')'
		}if (c == 123){
			return 11;											// 11 = '{'
		}if (c == 125){
			return 12;											// 12 = '}'
		}if (c == 43){
			return 13;											// 13 = '+'
		}if (c == 38){
			return 14;											// 14 = '&'
		}if (c == 33){
			return 15;											// 15 = '!'
		}if (c == 95){
			return 16;											// 16 = '_'
		}if (c == 0){
			return 17;											// 17 = null
		}
		return 99;
	}
		
	private static String concatenar(char c){
		concat = concat + c;
		return concat;
	}
	
	private static void genToken(String a) throws IOException {
		
            switch(a){
            case "PR": 		pw.println("<"+TS.getCont().get(p)+",>"); p=0; estado = 0; break;
            case "DEC": 	pw.println("<DEC,>"); estado = 0; break;
            case "EQ": 		pw.println("<EQ,>"); estado = 0; break;
            case "Coma": 	pw.println("<Coma,>"); estado = 0; break;
            case "PtyComa": pw.println("<PtyComa,>"); estado = 0; break;
            case "Parent1": pw.println("<Parent1,>"); estado = 0; break;
            case "Parent2": pw.println("<Parent2,>"); estado = 0; break;
            case "Corch1": 	pw.println("<Corch1,>"); estado = 0; break;
            case "Corch2": 	pw.println("<Corch2,>"); estado = 0; break;
            case "SUMA": 	pw.println("<SUMA,>"); estado = 0; break;
            case "AND": 	pw.println("<AND,>"); estado = 0; break;
            case "NotEQ": 	pw.println("<NotEQ,>"); estado = 0; break;
            case "ENT": 	pw.println("<ENT,"+num+">"); estado = 0; num=0; break;
            case "CADENA": 	pw.println("<CADENA,\"" + concat + "\">"); estado = 0; concat=""; num=0; break;
            
            case "ID": 
            	System.out.println("La posición será "+TS.buscar(concat));
            	pw.println("<ID, "+TS.buscar(concat)+">");
            	estado = 0; 
            	concat=""; 
            	break;
            
            default: estado=-1; System.err.println("Error al invocar a genToken (String) en línea "+linea); break;
            } 
	}

	
	public static void main(String[] args) throws IOException {

		System.out.println("Introduce el número de prueba que se va a analizar:");
		String digito = ""+(char) System.in.read();
		int leido = System.in.read();
		if(leido!=-1){
			digito= digito+(char) leido;
		}
		fichero = new FileWriter(rutaDestino);
	    pw = new PrintWriter(fichero);
		analizadorLexico(ruta + "Prueba" + digito + ".txt");           
        // Aseguramos que se cierra el fichero.
        if (null != fichero){fichero.close();}
        if(!error){
		     try {
		            File objetofile = new File (rutaDestino);
		            Desktop.getDesktop().open(objetofile);
		     }catch (IOException ex) {
		    	 System.out.println(ex);
		     }
        }
	}

}
