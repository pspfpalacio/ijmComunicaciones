package auxiliares;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;




public class ExPlataforma {

	private String plataforma;
	private String stringFecha;
	private Date dateFecha;
	private String usuario;
	private String stringMonto;
	private float doubleMonto;	
	
	public String getPlataforma() {
		return plataforma;
	}
	
	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}
	
	public String getStringFecha() {
		return stringFecha;
	}
	
	public void setStringFecha(String stringFecha) {
		this.stringFecha = stringFecha;
	}
	
	public Date getDateFecha() {
		return dateFecha;
	}
	
	public void setDateFecha(Date dateFecha) {
		this.dateFecha = dateFecha;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getStringMonto() {
		return stringMonto;
	}
	
	public void setStringMonto(String stringMonto) {
		this.stringMonto = stringMonto;
	}
	
	public float getDoubleMonto() {
		return doubleMonto;
	}
	
	public void setDoubleMonto(float doubleMonto) {
		this.doubleMonto = doubleMonto;
	}
	
	public void convertir(){
		stringMonto = stringMonto.trim();
		if(plataforma.equals("Pinweb")){
			stringMonto = stringMonto.replace(".", "");
			stringMonto = stringMonto.replace(",", ".");
			stringMonto = stringMonto.substring(1, stringMonto.length());
		}		
		if(plataforma.equals("Pinweb Mercader444")){
			stringMonto = stringMonto.replace(".", "");
			stringMonto = stringMonto.replace(",", ".");
			stringMonto = stringMonto.substring(1, stringMonto.length());
		}
		if(plataforma.equals("Codigo Pagos")){
			stringMonto = stringMonto.replace(".", "");
		}		
		if(stringMonto.indexOf("-") != -1){
			doubleMonto = Float.parseFloat(stringMonto.substring(1, stringMonto.length()));
		}else{
			doubleMonto = Float.parseFloat(stringMonto);
		}		
		if(plataforma.equals("Pinweb")){
			SimpleDateFormat formatoBarra = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			SimpleDateFormat formatoGuion = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");			
			try {
				dateFecha = formatoBarra.parse(stringFecha.trim());
			} catch (java.text.ParseException e) {
				try {
					dateFecha = formatoGuion.parse(stringFecha.trim());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}	
		if(plataforma.equals("Pinweb Mercader444")){
			SimpleDateFormat formatoBarra = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			SimpleDateFormat formatoGuion = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");			
			try {
				dateFecha = formatoBarra.parse(stringFecha.trim());
			} catch (java.text.ParseException e) {
				try {
					dateFecha = formatoGuion.parse(stringFecha.trim());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if(plataforma.equals("Codigo Pagos")){
			SimpleDateFormat formatoBarra = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");
			SimpleDateFormat formatoGuion = new SimpleDateFormat("dd-MM-yyyy - hh:mm:ss");			
			try {				
				dateFecha = formatoBarra.parse(stringFecha.trim());
			} catch (java.text.ParseException e) {
				try {
					dateFecha = formatoGuion.parse(stringFecha.trim());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}		
		if(plataforma.equals("Re Virtual")){
			SimpleDateFormat formatoBarra = new SimpleDateFormat("dd/MM/yy hh:mm");
			SimpleDateFormat formatoGuion = new SimpleDateFormat("dd-MM-yy hh:mm");
			try {
				dateFecha = formatoBarra.parse(stringFecha.trim());
			} catch (java.text.ParseException e) {
				try {
					dateFecha = formatoGuion.parse(stringFecha.trim());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}		
		if(plataforma.equals("Telerecargas")){ // consultar cuando este el excel
			SimpleDateFormat formatoBarra = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			SimpleDateFormat formatoGuion = new SimpleDateFormat("dd-MM-yyyy hh:mm");
//			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
//			Este formato fue para 
//			pasar el archivo excel anterior, si sigue el problema hay que formatear de otra forma la fecha
			try {
				dateFecha = formatoBarra.parse(stringFecha.trim());
			} catch (java.text.ParseException e) {
				try {
					dateFecha = formatoGuion.parse(stringFecha.trim());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}	
}
