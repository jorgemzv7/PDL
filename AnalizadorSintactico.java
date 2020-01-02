package Practica;

import java.awt.Desktop;
import java.io.*;

public class AnalizadorSintactico {
	
	final static String rutaTokens= "C:\\Users\\Jorge\\"+
    		"Documents\\Clase\\Tercero\\PDL\\Práctica\\tokens.txt";
	final static String rutaParse= "C:\\Users\\Jorge\\"+
    		"Documents\\Clase\\Tercero\\PDL\\Práctica\\parse.txt";
	private static boolean error = false;
	static FileWriter fichero = null;
    static PrintWriter pw = null;
    private static int c;
    private static String lex = "", atr = "";
	
	public static void analizadorSintactico (String rutaArchivo){
		try(FileReader fr = new FileReader(rutaArchivo)){
			c=fr.read();
			System.out.println("Lex " + lex + " - Atr "+atr);
			while(c!=-1){
				if(c==60){								// Empieza el token (<)
					c=fr.read();
					while(c!=44){						// Hasta la coma (lexema
						lex = lex + (char) c;
						c=fr.read();
					}
					c=fr.read();
					while(c!=62){						// Hasta >
						atr = atr + (char) c;
						c=fr.read();
					}
				System.out.println("Lexema = " + lex + " - Atributo= "+ atr);
				}
				
				
				
			}			
	        fr.close();
	    }catch(IOException ex){
	        System.err.println("Error al leer el archivo");
	        ex.printStackTrace();
	    }
	
		}
	public static void main(String[] args) throws IOException {

		analizadorSintactico(rutaTokens);
		if (null != fichero){fichero.close();}
        if(!error){
		     try {
		            File objetofile = new File (rutaParse);
		            Desktop.getDesktop().open(objetofile);
		     }catch (IOException ex) {
		    	 System.out.println(ex);
		     }
        }
	}

}
