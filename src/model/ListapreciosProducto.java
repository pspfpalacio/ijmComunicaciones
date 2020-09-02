package model;

import java.io.Serializable;
import java.text.DecimalFormat;

import javax.persistence.*;


/**
 * The persistent class for the listaprecios_productos database table.
 * 
 */
@Entity
@Table(name="listaprecios_productos")
@NamedQuery(name="ListapreciosProducto.findAll", query="SELECT l FROM ListapreciosProducto l")
public class ListapreciosProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private float descuento;

	private float porcentaje;

	//bi-directional many-to-one association to Listaprecio
	@ManyToOne
	@JoinColumn(name="id_listaPrecios")
	private Listaprecio listaprecio;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="id_producto")
	private Producto producto;

	public ListapreciosProducto() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getDescuento() {
		return this.descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}

	public float getPorcentaje() {
		return this.porcentaje;
	}

	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}

	public Listaprecio getListaprecio() {
		return this.listaprecio;
	}

	public void setListaprecio(Listaprecio listaprecio) {
		this.listaprecio = listaprecio;
	}

	public Producto getProducto() {
		return this.producto;
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