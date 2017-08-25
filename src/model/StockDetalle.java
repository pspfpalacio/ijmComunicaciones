package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the stock_detalle database table.
 * 
 */
@Entity
@Table(name="stock_detalle")
@NamedQuery(name="StockDetalle.findAll", query="SELECT s FROM StockDetalle s")
public class StockDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int cantidad;

	//bi-directional many-to-one association to Stock
	@ManyToOne
	@JoinColumn(name="idstock")
	private Stock stock;

	//bi-directional many-to-one association to VentasProducto
	@ManyToOne
	@JoinColumn(name="iddetelle")
	private VentasProducto ventasProducto;

	public StockDetalle() {
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

	public Stock getStock() {
		return this.stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public VentasProducto getVentasProducto() {
		return this.ventasProducto;
	}

	public void setVentasProducto(VentasProducto ventasProducto) {
		this.ventasProducto = ventasProducto;
	}

}