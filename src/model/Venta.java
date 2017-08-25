package model;

import java.io.Serializable;

import javax.persistence.*;

import DAO.venta.DAOventa;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ventas database table.
 * 
 */
@Entity
@Table(name="ventas")
@NamedQuery(name="Venta.findAll", query="SELECT v FROM Venta v")
public class Venta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private Boolean enabled;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	private float monto;

	private String tipo;

	//bi-directional many-to-one association to Notacredito
	@OneToMany(mappedBy="venta")
	private List<Notacredito> notacreditos;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idUsuario")
	private Usuario usuario;

	//bi-directional many-to-one association to VentasProducto
	@OneToMany(mappedBy="venta")
	private List<VentasProducto> ventasProductos;

	public Venta() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public float getMonto() {
		return this.monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Notacredito> getNotacreditos() {
		return this.notacreditos;
	}

	public void setNotacreditos(List<Notacredito> notacreditos) {
		this.notacreditos = notacreditos;
	}

	public Notacredito addNotacredito(Notacredito notacredito) {
		getNotacreditos().add(notacredito);
		notacredito.setVenta(this);

		return notacredito;
	}

	public Notacredito removeNotacredito(Notacredito notacredito) {
		getNotacreditos().remove(notacredito);
		notacredito.setVenta(null);

		return notacredito;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<VentasProducto> getVentasProductos() {
		return this.ventasProductos;
	}

	public void setVentasProductos(List<VentasProducto> ventasProductos) {
		this.ventasProductos = ventasProductos;
	}

	public VentasProducto addVentasProducto(VentasProducto ventasProducto) {
		getVentasProductos().add(ventasProducto);
		ventasProducto.setVenta(this);

		return ventasProducto;
	}

	public VentasProducto removeVentasProducto(VentasProducto ventasProducto) {
		getVentasProductos().remove(ventasProducto);
		ventasProducto.setVenta(null);

		return ventasProducto;
	}
	
	public String getStringFecha(){
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(fecha);
	}
	
	public String getStringMonto(){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(getMonto());
	}
	
	public String getMontoString(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(monto);
		return valor;
	}
	
	public List<VentasProducto> getDetalleVenta(){
		DAOventa DAOVenta = new DAOventa();
		List<VentasProducto> lista = new ArrayList<VentasProducto>();
		lista = DAOVenta.getDetallesDeVentas(id);
		return lista;
	}
	
	public String getNombreCliente(){
		return cliente.getNombre() + " - " + cliente.getApellido();
	}

}