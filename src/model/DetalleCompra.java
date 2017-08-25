package model;

import java.io.Serializable;
import java.text.DecimalFormat;

import javax.persistence.*;


/**
 * The persistent class for the detalle_compra database table.
 * 
 */
@Entity
@Table(name="detalle_compra")
@NamedQuery(name="DetalleCompra.findAll", query="SELECT d FROM DetalleCompra d")
public class DetalleCompra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int cantidad;

	@Column(name="precio_unitario")
	private float precioUnitario;

	@Column(name="ptos_descuento")
	private float ptosDescuento;

	private float subtotal;

	//bi-directional many-to-one association to Compra
	@ManyToOne
	@JoinColumn(name="idCompra")
	private Compra compra;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="idProducto")
	private Producto producto;

	public DetalleCompra() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecioUnitario() {
		return this.precioUnitario;
	}

	public void setPrecioUnitario(float precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public float getPtosDescuento() {
		return this.ptosDescuento;
	}

	public void setPtosDescuento(float ptosDescuento) {
		this.ptosDescuento = ptosDescuento;
	}

	public float getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public Compra getCompra() {
		return this.compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public String getPrecioString(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(precioUnitario);
		return valor;
	}
	
	public String getSubtotalString(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(subtotal);
		return valor;
	}
	
	public String getProd(){
		return producto.getNombre();
	}

}