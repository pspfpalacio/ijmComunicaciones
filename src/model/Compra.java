package model;

import java.io.Serializable;

import javax.persistence.*;

import DAO.compra.DAOCompra;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the compra database table.
 * 
 */
@Entity
@Table(name="compra")
@NamedQuery(name="Compra.findAll", query="SELECT c FROM Compra c")
public class Compra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private Boolean enabled;

	private String estado;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaBaja;

	private float monto;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="idProvCli")
	private Cliente cliente;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuarioAlta")
	private Usuario usuario1;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuarioBaja")
	private Usuario usuario2;

	//bi-directional many-to-one association to DetalleCompra
	@OneToMany(mappedBy="compra")
	private List<DetalleCompra> detalleCompras;

	public Compra() {
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

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public float getMonto() {
		return this.monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public List<DetalleCompra> getDetalleCompras() {
		return this.detalleCompras;
	}

	public void setDetalleCompras(List<DetalleCompra> detalleCompras) {
		this.detalleCompras = detalleCompras;
	}

	public DetalleCompra addDetalleCompra(DetalleCompra detalleCompra) {
		getDetalleCompras().add(detalleCompra);
		detalleCompra.setCompra(this);

		return detalleCompra;
	}

	public DetalleCompra removeDetalleCompra(DetalleCompra detalleCompra) {
		getDetalleCompras().remove(detalleCompra);
		detalleCompra.setCompra(null);

		return detalleCompra;
	}
	
	public String getFechaString(){
		SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = simple.format(fechaAlta);
		return fecha;
	}
	
	public String getMontoString(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(monto);
		return valor;
	}
	
	public List<DetalleCompra> getDetalleDeCompra(){
		DAOCompra DAO = new DAOCompra();
		List<DetalleCompra> lista = DAO.getDetallesXCompra(id);
		return lista;
	}
	
	public boolean getEstadoEdit(){
		boolean valor = false;
		if(estado.equals("Confirmado")){
			valor = true;
		}
		if(estado.equals("Cancelado")){
			valor = true;
		}
		if(estado.equals("Pendiente")){
			valor = false;
		}
		return valor;
	}
	
	public boolean getEstadoConf(){
		boolean valor = false;
		if(estado.equals("Confirmado")){
			valor = true;
		}
		if(estado.equals("Cancelado")){
			valor = true;
		}
		if(estado.equals("Pendiente")){
			valor = false;
		}
		return valor;
	}
	
	public boolean getEstadoCancel(){
		boolean valor = false;
		if(estado.equals("Confirmado")){
			valor = true;
		}
		if(estado.equals("Cancelado")){
			valor = true;
		}
		if(estado.equals("Pendiente")){
			valor = false;
		}
		return valor;
	}
	
	public boolean getEstadoCancel1(){
		boolean valor = false;
		if(estado.equals("Confirmado")){
			valor = true;
		}
		if(estado.equals("Cancelado")){
			valor = true;
		}
		if(estado.equals("Pendiente")){
			valor = false;
		}
		return valor;
	}
	
	public String getNombreProveedor(){
		return cliente.getNombre() + " - " + cliente.getApellido();
	}

}