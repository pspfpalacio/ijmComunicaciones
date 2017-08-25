package model;

import java.io.Serializable;

import javax.persistence.*;

import model.Cuenta;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the clientes database table.
 * 
 */
@Entity
@Table(name="clientes")
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String apellido;

	private String direccion;

	private Boolean enabled;

	@Temporal(TemporalType.DATE)
	private Date fechaAlta;

	@Temporal(TemporalType.DATE)
	private Date fechaBaja;

	@Temporal(TemporalType.DATE)
	private Date fechaModificacion;

	private String mail;

	private String nombre;
	
	@Column(name="nro_tarjeta")
	private String nroTarjeta;

	private float saldo;

	private String telefono;

	private String tipo;
	
	@Column(name="tipo_tarjeta")
	private String tipoTarjeta;

	//bi-directional many-to-one association to CargaVirtual
	@OneToMany(mappedBy="cliente")
	private List<CargaVirtual> cargaVirtuals;

	//bi-directional many-to-one association to ClientePlataforma
	@OneToMany(mappedBy="cliente")
	private List<ClientePlataforma> clientePlataformas;

	//bi-directional many-to-one association to Listaprecio
	@ManyToOne
	@JoinColumn(name="idlistaprecio")
	private Listaprecio listaprecio;

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

	//bi-directional many-to-one association to Compra
	@OneToMany(mappedBy="cliente")
	private List<Compra> compras;
	
	//bi-directional many-to-one association to Cuenta
	@OneToMany(mappedBy="cliente")
	private List<Cuenta> cuentas;

	//bi-directional many-to-one association to Cuentascorriente
	@OneToMany(mappedBy="cliente")
	private List<Cuentascorriente> cuentascorrientes;

	//bi-directional many-to-one association to Pago
	@OneToMany(mappedBy="cliente1")
	private List<Pago> pagos1;

	//bi-directional many-to-one association to Pago
	@OneToMany(mappedBy="cliente2")
	private List<Pago> pagos2;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="cliente")
	private List<Usuario> usuarios;

	//bi-directional many-to-one association to Venta
	@OneToMany(mappedBy="cliente")
	private List<Venta> ventas;

	//bi-directional many-to-one association to MovimientoVirtual
	@OneToMany(mappedBy="cliente")
	private List<MovimientoVirtual> movimientoVirtuals;

	public Cliente() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNroTarjeta() {
		return this.nroTarjeta;
	}

	public void setNroTarjeta(String nroTarjeta) {
		this.nroTarjeta = nroTarjeta;
	}

	public float getSaldo() {
		return this.saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getTipoTarjeta() {
		return this.tipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public List<CargaVirtual> getCargaVirtuals() {
		return this.cargaVirtuals;
	}

	public void setCargaVirtuals(List<CargaVirtual> cargaVirtuals) {
		this.cargaVirtuals = cargaVirtuals;
	}

	public CargaVirtual addCargaVirtual(CargaVirtual cargaVirtual) {
		getCargaVirtuals().add(cargaVirtual);
		cargaVirtual.setCliente(this);

		return cargaVirtual;
	}

	public CargaVirtual removeCargaVirtual(CargaVirtual cargaVirtual) {
		getCargaVirtuals().remove(cargaVirtual);
		cargaVirtual.setCliente(null);

		return cargaVirtual;
	}

	public List<ClientePlataforma> getClientePlataformas() {
		return this.clientePlataformas;
	}

	public void setClientePlataformas(List<ClientePlataforma> clientePlataformas) {
		this.clientePlataformas = clientePlataformas;
	}

	public ClientePlataforma addClientePlataforma(ClientePlataforma clientePlataforma) {
		getClientePlataformas().add(clientePlataforma);
		clientePlataforma.setCliente(this);

		return clientePlataforma;
	}

	public ClientePlataforma removeClientePlataforma(ClientePlataforma clientePlataforma) {
		getClientePlataformas().remove(clientePlataforma);
		clientePlataforma.setCliente(null);

		return clientePlataforma;
	}

	public Listaprecio getListaprecio() {
		return this.listaprecio;
	}

	public void setListaprecio(Listaprecio listaprecio) {
		this.listaprecio = listaprecio;
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

	public List<Compra> getCompras() {
		return this.compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public Compra addCompra(Compra compra) {
		getCompras().add(compra);
		compra.setCliente(this);

		return compra;
	}

	public Compra removeCompra(Compra compra) {
		getCompras().remove(compra);
		compra.setCliente(null);

		return compra;
	}
	
	public List<Cuenta> getCuentas() {
		return this.cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	
	public Cuenta addCuenta(Cuenta cuenta) {
		getCuentas().add(cuenta);
		cuenta.setCliente(this);

		return cuenta;
	}

	public Cuenta removeCuenta(Cuenta cuenta) {
		getCuentas().remove(cuenta);
		cuenta.setCliente(null);

		return cuenta;
	}

	public List<Cuentascorriente> getCuentascorrientes() {
		return this.cuentascorrientes;
	}

	public void setCuentascorrientes(List<Cuentascorriente> cuentascorrientes) {
		this.cuentascorrientes = cuentascorrientes;
	}

	public Cuentascorriente addCuentascorriente(Cuentascorriente cuentascorriente) {
		getCuentascorrientes().add(cuentascorriente);
		cuentascorriente.setCliente(this);

		return cuentascorriente;
	}

	public Cuentascorriente removeCuentascorriente(Cuentascorriente cuentascorriente) {
		getCuentascorrientes().remove(cuentascorriente);
		cuentascorriente.setCliente(null);

		return cuentascorriente;
	}

	public List<Pago> getPagos1() {
		return this.pagos1;
	}

	public void setPagos1(List<Pago> pagos1) {
		this.pagos1 = pagos1;
	}

	public Pago addPagos1(Pago pagos1) {
		getPagos1().add(pagos1);
		pagos1.setCliente1(this);

		return pagos1;
	}

	public Pago removePagos1(Pago pagos1) {
		getPagos1().remove(pagos1);
		pagos1.setCliente1(null);

		return pagos1;
	}

	public List<Pago> getPagos2() {
		return this.pagos2;
	}

	public void setPagos2(List<Pago> pagos2) {
		this.pagos2 = pagos2;
	}

	public Pago addPagos2(Pago pagos2) {
		getPagos2().add(pagos2);
		pagos2.setCliente2(this);

		return pagos2;
	}

	public Pago removePagos2(Pago pagos2) {
		getPagos2().remove(pagos2);
		pagos2.setCliente2(null);

		return pagos2;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setCliente(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setCliente(null);

		return usuario;
	}

	public List<Venta> getVentas() {
		return this.ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public Venta addVenta(Venta venta) {
		getVentas().add(venta);
		venta.setCliente(this);

		return venta;
	}

	public Venta removeVenta(Venta venta) {
		getVentas().remove(venta);
		venta.setCliente(null);

		return venta;
	}

	public List<MovimientoVirtual> getMovimientoVirtuals() {
		return this.movimientoVirtuals;
	}

	public void setMovimientoVirtuals(List<MovimientoVirtual> movimientoVirtuals) {
		this.movimientoVirtuals = movimientoVirtuals;
	}

	public MovimientoVirtual addMovimientoVirtual(MovimientoVirtual movimientoVirtual) {
		getMovimientoVirtuals().add(movimientoVirtual);
		movimientoVirtual.setCliente(this);

		return movimientoVirtual;
	}

	public MovimientoVirtual removeMovimientoVirtual(MovimientoVirtual movimientoVirtual) {
		getMovimientoVirtuals().remove(movimientoVirtual);
		movimientoVirtual.setCliente(null);

		return movimientoVirtual;
	}
	
	public String getFechaString(){
		SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = simple.format(fechaAlta);
		return fecha;
	}
	
	public String getSaldoString(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(saldo);
		return valor;
	}

}