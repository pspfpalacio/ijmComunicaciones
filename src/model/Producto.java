package model;

import java.io.Serializable;

import javax.persistence.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the productos database table.
 * 
 */
@Entity
@Table(name="productos")
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String descripcion;

	private Boolean enabled;

	@Temporal(TemporalType.DATE)
	private Date fechaAlta;

	@Temporal(TemporalType.DATE)
	private Date fechaBaja;

	@Temporal(TemporalType.DATE)
	private Date fechaModificacion;

	private String nombre;

	private float precioCompra;

	private float precioNominal;

	private int stock;

	@Column(name="stock_minimo")
	private int stockMinimo;

	//bi-directional many-to-one association to DetalleCompra
	@OneToMany(mappedBy="producto")
	private List<DetalleCompra> detalleCompras;

	//bi-directional many-to-one association to ListapreciosProducto
	@OneToMany(mappedBy="producto")
	private List<ListapreciosProducto> listapreciosProductos;

	//bi-directional many-to-one association to Rubro
	@ManyToOne
	@JoinColumn(name="id_rubro")
	private Rubro rubro;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idUsuarioAlta")
	private Usuario usuario1;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idUsuarioBaja")
	private Usuario usuario2;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idUsuarioModificacion")
	private Usuario usuario3;

	//bi-directional many-to-one association to Stock
	@OneToMany(mappedBy="producto")
	private List<Stock> stocks;

	//bi-directional many-to-one association to VentasProducto
	@OneToMany(mappedBy="producto")
	private List<VentasProducto> ventasProductos;

	public Producto() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return this.fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecioCompra() {
		return this.precioCompra;
	}

	public void setPrecioCompra(float precioCompra) {
		this.precioCompra = precioCompra;
	}

	public float getPrecioNominal() {
		return this.precioNominal;
	}

	public void setPrecioNominal(float precioNominal) {
		this.precioNominal = precioNominal;
	}

	public int getStock() {
		return this.stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getStockMinimo() {
		return this.stockMinimo;
	}

	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public List<DetalleCompra> getDetalleCompras() {
		return this.detalleCompras;
	}

	public void setDetalleCompras(List<DetalleCompra> detalleCompras) {
		this.detalleCompras = detalleCompras;
	}

	public DetalleCompra addDetalleCompra(DetalleCompra detalleCompra) {
		getDetalleCompras().add(detalleCompra);
		detalleCompra.setProducto(this);

		return detalleCompra;
	}

	public DetalleCompra removeDetalleCompra(DetalleCompra detalleCompra) {
		getDetalleCompras().remove(detalleCompra);
		detalleCompra.setProducto(null);

		return detalleCompra;
	}

	public List<ListapreciosProducto> getListapreciosProductos() {
		return this.listapreciosProductos;
	}

	public void setListapreciosProductos(List<ListapreciosProducto> listapreciosProductos) {
		this.listapreciosProductos = listapreciosProductos;
	}

	public ListapreciosProducto addListapreciosProducto(ListapreciosProducto listapreciosProducto) {
		getListapreciosProductos().add(listapreciosProducto);
		listapreciosProducto.setProducto(this);

		return listapreciosProducto;
	}

	public ListapreciosProducto removeListapreciosProducto(ListapreciosProducto listapreciosProducto) {
		getListapreciosProductos().remove(listapreciosProducto);
		listapreciosProducto.setProducto(null);

		return listapreciosProducto;
	}

	public Rubro getRubro() {
		return this.rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	public Usuario getUsuario1() {
		return this.usuario1;
	}

	public void setUsuario1(Usuario usuario1) {
		this.usuario1 = usuario1;
	}

	public Usuario getUsuario2() {
		return this.usuario2;
	}

	public void setUsuario2(Usuario usuario2) {
		this.usuario2 = usuario2;
	}

	public Usuario getUsuario3() {
		return this.usuario3;
	}

	public void setUsuario3(Usuario usuario3) {
		this.usuario3 = usuario3;
	}

	public List<Stock> getStocks() {
		return this.stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public Stock addStock(Stock stock) {
		getStocks().add(stock);
		stock.setProducto(this);

		return stock;
	}

	public Stock removeStock(Stock stock) {
		getStocks().remove(stock);
		stock.setProducto(null);

		return stock;
	}

	public List<VentasProducto> getVentasProductos() {
		return this.ventasProductos;
	}

	public void setVentasProductos(List<VentasProducto> ventasProductos) {
		this.ventasProductos = ventasProductos;
	}

	public VentasProducto addVentasProducto(VentasProducto ventasProducto) {
		getVentasProductos().add(ventasProducto);
		ventasProducto.setProducto(this);

		return ventasProducto;
	}

	public VentasProducto removeVentasProducto(VentasProducto ventasProducto) {
		getVentasProductos().remove(ventasProducto);
		ventasProducto.setProducto(null);

		return ventasProducto;
	}
	
	public String getFechaString(){
		SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = simple.format(fechaAlta);
		return fecha;
	}
	
	public String getPrecioCompraString(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(precioCompra);
		return valor;
	}
	
	public String getPrecioNominalString(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(precioNominal);
		return valor;
	}

}