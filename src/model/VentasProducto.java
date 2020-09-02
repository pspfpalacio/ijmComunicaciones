package model;

import java.io.Serializable;

import javax.persistence.*;

import DAO.venta.DAOventa;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the ventas_productos database table.
 * 
 */
@Entity
@Table(name="ventas_productos")
@NamedQuery(name="VentasProducto.findAll", query="SELECT v FROM VentasProducto v")
public class VentasProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int cantidad;

	@Column(name="precio_unitario")
	private float precioUnitario;

	private float subtotal;

	//bi-directional many-to-one association to StockDetalle
	@OneToMany(mappedBy="ventasProducto")
	private List<StockDetalle> stockDetalles;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="id_producto")
	private Producto producto;

	//bi-directional many-to-one association to Venta
	@ManyToOne
	@JoinColumn(name="id_ventas")
	private Venta venta;

	public VentasProducto() {
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

	public float getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public List<StockDetalle> getStockDetalles() {
		return this.stockDetalles;
	}

	public void setStockDetalles(List<StockDetalle> stockDetalles) {
		this.stockDetalles = stockDetalles;
	}

	public StockDetalle addStockDetalle(StockDetalle stockDetalle) {
		getStockDetalles().add(stockDetalle);
		stockDetalle.setVentasProducto(this);

		return stockDetalle;
	}

	public StockDetalle removeStockDetalle(StockDetalle stockDetalle) {
		getStockDetalles().remove(stockDetalle);
		stockDetalle.setVentasProducto(null);

		return stockDetalle;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Venta getVenta() {
		return this.venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}
	
	public String getProd(){
		return producto.getNombre();
	}
	
	public String getStringSubtotal(){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(getSubtotal());
	}
	
	public String getStringPrecioUnitario(){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(getPrecioUnitario());
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
	
	//LO HICE YO
	public List<StockDetalle> getListStockDetalle(){
		DAOventa DAOven = new DAOventa();
		List<StockDetalle> lista = new ArrayList<StockDetalle>();
		lista = DAOven.getStocksDetalle(id);
		return lista;
	}

}