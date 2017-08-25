package auxiliares;

import java.text.DecimalFormat;

import model.Listaprecio;
import model.Producto;

public class AuxListapreciosProducto {
	private int id;
	private float descuento;
	private float porcentaje;
	private Listaprecio listaprecio;
	private Producto producto;
	private int cantidad;

	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getDescuento() {
		return descuento;
	}
	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}
	public float getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}
	public Listaprecio getListaprecio() {
		return listaprecio;
	}
	public void setListaprecio(Listaprecio listaprecio) {
		this.listaprecio = listaprecio;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public float getPreciovta() {
		float preciovta =0;
		if (porcentaje > 0 & producto.getPrecioCompra() != 0){
			preciovta = producto.getPrecioCompra() + ((producto.getPrecioCompra() / 100) * porcentaje);
		}
		
		if (descuento > 0){
			preciovta = producto.getPrecioNominal() - ((producto.getPrecioNominal() / 100) * descuento);
		}
		return preciovta;
	}
	
	

	public String getStringPreciovta(){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(getPreciovta());
	}
	
	public float getGanancia(){
		float ganancia = 0;
		if (descuento > 0){
			ganancia = ((producto.getPrecioNominal() - ((producto.getPrecioNominal() / 100) * descuento)) - producto.getPrecioCompra());
		}
		
		
		if(porcentaje > 0){
			ganancia = ((producto.getPrecioCompra() / 100) * porcentaje);
		}
		
		return ganancia;
	}
	
	public String getStringGanancia(){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(getGanancia());
	}
	
	public boolean getBoolGanancia(){
		if(getGanancia() == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean getBoolPreciovta(){
		if(getPreciovta() == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean getBoolDescuento(){
		if(getDescuento() == 0){
			return false;
		}else{
			return true;
		}
	}
	public boolean getBoolPorcentaje(){
		if(getPorcentaje() == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public String getParametroAplicado(){
		String retorno = "Ninguno";
		DecimalFormat df = new DecimalFormat("#.##");
		if (porcentaje > 0){
			retorno = "Porcentaje/Precio Vta: " + df.format(porcentaje) + "%";
		}
		if (descuento > 0){
			retorno = "Descuento/V.Nom: " + df.format(descuento) + "%";
		}
		
		return retorno;
	}

}
