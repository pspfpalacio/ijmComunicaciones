package model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the stock database table.
 * 
 */
@Entity
@Table(name="stock")
@NamedQuery(name="Stock.findAll", query="SELECT s FROM Stock s")
public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int cantidad;

	@Column(name="precio_unitario")
	private float precioUnitario;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="id_producto")
	private Producto producto;

	//bi-directional many-to-one association to StockDetalle
	@OneToMany(mappedBy="stock")
	private List<StockDetalle> stockDetalles;

	public Stock() {
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

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public List<StockDetalle> getStockDetalles() {
		return this.stockDetalles;
	}

	public void setStockDetalles(List<StockDetalle> stockDetalles) {
		this.stockDetalles = stockDetalles;
	}

	public StockDetalle addStockDetalle(StockDetalle stockDetalle) {
		getStockDetalles().add(stockDetalle);
		stockDetalle.setStock(this);

		return stockDetalle;
	}

	public StockDetalle removeStockDetalle(StockDetalle stockDetalle) {
		getStockDetalles().remove(stockDetalle);
		stockDetalle.setStock(null);

		return stockDetalle;
	}

}