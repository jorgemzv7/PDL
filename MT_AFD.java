package Practica;

public class MT_AFD {
	private int estado;
	private String accion;
	
	public MT_AFD (int estado, String accion){
		this.estado = estado;
		this.accion = accion;
	}
	
	public int getEstado(){
		return estado;
	}
	
	public String getAccion(){
		return accion;
	}
	
	public String accion (int estado, int tipoCaracter){
		switch(estado){
		case 0:											//Estado 0
			switch(tipoCaracter){
			case -1: return "EOF";
			case 0: return "L";
			case 1: return "L,C";
			case 2: return "A";
			case 4: return "L";
			case 5: return "L";
			case 6: return "GT1";
			case 7: return "GT2";
			case 8: return "GT3";
			case 9: return "GT4";
			case 10: return "GT5";
			case 11: return "GT6";
			case 12: return "GT7";
			case 13: return "GT8";
			case 14: return "L";
			case 15: return "L";
			case 16: return "0";
			case 17: return "0";
			}
		case 1:
			if(tipoCaracter==1 || tipoCaracter == 2
			|| tipoCaracter == 16){
				return "L,C";
			}
			return "GT17";							//Recibo o.c. en estado 1
		case 2: 
			if (tipoCaracter==2){
				return "B";
			}
			return "GT9";						//Recibo o.c en estado 2
		case 3:
			if (tipoCaracter==4){
				return "GT10";
			}return "3";
		case 4:
			if(tipoCaracter==1 || tipoCaracter == 2
			|| tipoCaracter == 16){
				return "L,C";
			}
			return "GT18";							//Recibo o.c. en 4
		case 5: 
			if(tipoCaracter==5){
				return "GT11";
		}return "L,C";
		/*case 6:									//Estado fin de cadena
			if(tipoCaracter==5){
				return "GT11";
			}return "6";*/
		case 7:
			if(tipoCaracter==14){
				return "GT12";
			}return "7";
		case 8:
			if (tipoCaracter==6){
				return "GT13";
			}return "8";
		}						
		return "Estado > 8";
	}

	public int estado (int estado, int tipoCaracter){	
		if(tipoCaracter==-1){
			return estado;
		}
		switch(estado){
		case 0:											//Estado 0
			switch(tipoCaracter){						
			case 0: return 0;							
			case 1: return 1;
			case 2: return 2;
			case 4: return 3;
			case 5: return 5;
			case 6: return 9;
			case 7: return 10;
			case 8: return 11;
			case 9: return 12;
			case 10: return 13;
			case 11: return 14;
			case 12: return 15;
			case 13: return 16;
			case 14: return 7;
			case 15: return 8;
			case 16: return 0;
			case 17: return 0;
			}
		case 1:
			if(tipoCaracter==1){
				return 1;
			}if(tipoCaracter == 2 || tipoCaracter == 16){
				return 4;
			}
			return 17;
		case 2: 
			if (tipoCaracter==2){
				return 2;
			}
			return 19;
		case 3:
			if (tipoCaracter==4){
				return 20;
			}return 0;
		case 4:
			if(tipoCaracter==1 || tipoCaracter == 2
			|| tipoCaracter == 16){
				return 4;
			}
			return 18;
		case 5: 
			if (tipoCaracter==5){
				return 21;
			}return 5;
		case 6:													//Estado fin de cadena
			if(tipoCaracter==17){
				return 21;
			}
			return -1;
		case 7:
			if(tipoCaracter==14){
				return 22;
			}
			return 0;
		case 8:
			if (tipoCaracter==6){
				return 23;
			}
			return -1;
		}
		return -1;
	}

}
