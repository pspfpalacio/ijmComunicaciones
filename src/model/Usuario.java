package model;

import java.io.Serializable;

import javax.persistence.*;

import model.Cuenta;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private Boolean enabled;

	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;

	private String nombre;

	private String password;

	private String username;

	//bi-directional many-to-one association to Caja
	@OneToMany(mappedBy="usuarioBean")
	private List<Caja> cajas;

	//bi-directional many-to-one association to CargaVirtual
	@OneToMany(mappedBy="usuario")
	private List<CargaVirtual> cargaVirtuals;

	//bi-directional many-to-one association to Cliente
	@OneToMany(mappedBy="usuario1")
	private List<Cliente> clientes1;

	//bi-directional many-to-one association to Cliente
	@OneToMany(mappedBy="usuario2")
	private List<Cliente> clientes2;

	//bi-directional many-to-one association to Cliente
	@OneToMany(mappedBy="usuario3")
	private List<Cliente> clientes3;

	//bi-directional many-to-one association to Compra
	@OneToMany(mappedBy="usuario1")
	private List<Compra> compras1;

	//bi-directional many-to-one association to Compra
	@OneToMany(mappedBy="usuario2")
	private List<Compra> compras2;

	//bi-directional many-to-one association to CompraCargavirtual
	@OneToMany(mappedBy="usuario1")
	private List<CompraCargavirtual> compraCargavirtuals1;

	//bi-directional many-to-one association to CompraCargavirtual
	@OneToMany(mappedBy="usuario2")
	private List<CompraCargavirtual> compraCargavirtuals2;
	
	//bi-directional many-to-one association to Cuenta
	@OneToMany(mappedBy="usuario")
	private List<Cuenta> cuentas;

	//bi-directional many-to-one association to Cuentascorriente
	@OneToMany(mappedBy="usuario")
	private List<Cuentascorriente> cuentascorrientes;

	//bi-directional many-to-one association to Listaprecio
	@OneToMany(mappedBy="usuario1")
	private List<Listaprecio> listaprecios1;

	//bi-directional many-to-one association to Listaprecio
	@OneToMany(mappedBy="usuario2")
	private List<Listaprecio> listaprecios2;

	//bi-directional many-to-one association to Listaprecio
	@OneToMany(mappedBy="usuario3")
	private List<Listaprecio> listaprecios3;

	//bi-directional many-to-one association to Notacredito
	@OneToMany(mappedBy="usuario")
	private List<Notacredito> notacreditos;

	//bi-directional many-to-one association to Producto
	@OneToMany(mappedBy="usuario1")
	private List<Producto> productos1;

	//bi-directional many-to-one association to Producto
	@OneToMany(mappedBy="usuario2")
	private List<Producto> productos2;

	//bi-directional many-to-one association to Producto
	@OneToMany(mappedBy="usuario3")
	private List<Producto> productos3;

	//bi-directional many-to-one association to Rubro
	@OneToMany(mappedBy="usuario1")
	private List<Rubro> rubros1;

	//bi-directional many-to-one association to Rubro
	@OneToMany(mappedBy="usuario2")
	private List<Rubro> rubros2;

	//bi-directional many-to-one association to Rubro
	@OneToMany(mappedBy="usuario3")
	private List<Rubro> rubros3;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="id_rol")
	private Role role;

	//bi-directional many-to-one association to Usuariosecurity
	@OneToMany(mappedBy="usuario")
	private List<Usuariosecurity> usuariosecurities;

	//bi-directional many-to-one association to Venta
	@OneToMany(mappedBy="usuario")
	private List<Venta> ventas;

	public Usuario() {
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

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Caja> getCajas() {
		return this.cajas;
	}

	public void setCajas(List<Caja> cajas) {
		this.cajas = cajas;
	}

	public Caja addCaja(Caja caja) {
		getCajas().add(caja);
		caja.setUsuarioBean(this);

		return caja;
	}

	public Caja removeCaja(Caja caja) {
		getCajas().remove(caja);
		caja.setUsuarioBean(null);

		return caja;
	}

	public List<CargaVirtual> getCargaVirtuals() {
		return this.cargaVirtuals;
	}

	public void setCargaVirtuals(List<CargaVirtual> cargaVirtuals) {
		this.cargaVirtuals = cargaVirtuals;
	}

	public CargaVirtual addCargaVirtual(CargaVirtual cargaVirtual) {
		getCargaVirtuals().add(cargaVirtual);
		cargaVirtual.setUsuario(this);

		return cargaVirtual;
	}

	public CargaVirtual removeCargaVirtual(CargaVirtual cargaVirtual) {
		getCargaVirtuals().remove(cargaVirtual);
		cargaVirtual.setUsuario(null);

		return cargaVirtual;
	}

	public List<Cliente> getClientes1() {
		return this.clientes1;
	}

	public void setClientes1(List<Cliente> clientes1) {
		this.clientes1 = clientes1;
	}

	public Cliente addClientes1(Cliente clientes1) {
		getClientes1().add(clientes1);
		clientes1.setUsuario1(this);

		return clientes1;
	}

	public Cliente removeClientes1(Cliente clientes1) {
		getClientes1().remove(clientes1);
		clientes1.setUsuario1(null);

		return clientes1;
	}

	public List<Cliente> getClientes2() {
		return this.clientes2;
	}

	public void setClientes2(List<Cliente> clientes2) {
		this.clientes2 = clientes2;
	}

	public Cliente addClientes2(Cliente clientes2) {
		getClientes2().add(clientes2);
		clientes2.setUsuario2(this);

		return clientes2;
	}

	public Cliente removeClientes2(Cliente clientes2) {
		getClientes2().remove(clientes2);
		clientes2.setUsuario2(null);

		return clientes2;
	}

	public List<Cliente> getClientes3() {
		return this.clientes3;
	}

	public void setClientes3(List<Cliente> clientes3) {
		this.clientes3 = clientes3;
	}

	public Cliente addClientes3(Cliente clientes3) {
		getClientes3().add(clientes3);
		clientes3.setUsuario3(this);

		return clientes3;
	}

	public Cliente removeClientes3(Cliente clientes3) {
		getClientes3().remove(clientes3);
		clientes3.setUsuario3(null);

		return clientes3;
	}

	public List<Compra> getCompras1() {
		return this.compras1;
	}

	public void setCompras1(List<Compra> compras1) {
		this.compras1 = compras1;
	}

	public Compra addCompras1(Compra compras1) {
		getCompras1().add(compras1);
		compras1.setUsuario1(this);

		return compras1;
	}

	public Compra removeCompras1(Compra compras1) {
		getCompras1().remove(compras1);
		compras1.setUsuario1(null);

		return compras1;
	}

	public List<Compra> getCompras2() {
		return this.compras2;
	}

	public void setCompras2(List<Compra> compras2) {
		this.compras2 = compras2;
	}

	public Compra addCompras2(Compra compras2) {
		getCompras2().add(compras2);
		compras2.setUsuario2(this);

		return compras2;
	}

	public Compra removeCompras2(Compra compras2) {
		getCompras2().remove(compras2);
		compras2.setUsuario2(null);

		return compras2;
	}

	public List<CompraCargavirtual> getCompraCargavirtuals1() {
		return this.compraCargavirtuals1;
	}

	public void setCompraCargavirtuals1(List<CompraCargavirtual> compraCargavirtuals1) {
		this.compraCargavirtuals1 = compraCargavirtuals1;
	}

	public CompraCargavirtual addCompraCargavirtuals1(CompraCargavirtual compraCargavirtuals1) {
		getCompraCargavirtuals1().add(compraCargavirtuals1);
		compraCargavirtuals1.setUsuario1(this);

		return compraCargavirtuals1;
	}

	public CompraCargavirtual removeCompraCargavirtuals1(CompraCargavirtual compraCargavirtuals1) {
		getCompraCargavirtuals1().remove(compraCargavirtuals1);
		compraCargavirtuals1.setUsuario1(null);

		return compraCargavirtuals1;
	}

	public List<CompraCargavirtual> getCompraCargavirtuals2() {
		return this.compraCargavirtuals2;
	}

	public void setCompraCargavirtuals2(List<CompraCargavirtual> compraCargavirtuals2) {
		this.compraCargavirtuals2 = compraCargavirtuals2;
	}

	public CompraCargavirtual addCompraCargavirtuals2(CompraCargavirtual compraCargavirtuals2) {
		getCompraCargavirtuals2().add(compraCargavirtuals2);
		compraCargavirtuals2.setUsuario2(this);

		return compraCargavirtuals2;
	}

	public CompraCargavirtual removeCompraCargavirtuals2(CompraCargavirtual compraCargavirtuals2) {
		getCompraCargavirtuals2().remove(compraCargavirtuals2);
		compraCargavirtuals2.setUsuario2(null);

		return compraCargavirtuals2;
	}
	
	public List<Cuenta> getCuentas() {
		return this.cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public Cuenta addCuenta(Cuenta cuenta) {
		getCuentas().add(cuenta);
		cuenta.setUsuario(this);

		return cuenta;
	}

	public Cuenta removeCuenta(Cuenta cuenta) {
		getCuentas().remove(cuenta);
		cuenta.setUsuario(null);

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
		cuentascorriente.setUsuario(this);

		return cuentascorriente;
	}

	public Cuentascorriente removeCuentascorriente(Cuentascorriente cuentascorriente) {
		getCuentascorrientes().remove(cuentascorriente);
		cuentascorriente.setUsuario(null);

		return cuentascorriente;
	}

	public List<Listaprecio> getListaprecios1() {
		return this.listaprecios1;
	}

	public void setListaprecios1(List<Listaprecio> listaprecios1) {
		this.listaprecios1 = listaprecios1;
	}

	public Listaprecio addListaprecios1(Listaprecio listaprecios1) {
		getListaprecios1().add(listaprecios1);
		listaprecios1.setUsuario1(this);

		return listaprecios1;
	}

	public Listaprecio removeListaprecios1(Listaprecio listaprecios1) {
		getListaprecios1().remove(listaprecios1);
		listaprecios1.setUsuario1(null);

		return listaprecios1;
	}

	public List<Listaprecio> getListaprecios2() {
		return this.listaprecios2;
	}

	public void setListaprecios2(List<Listaprecio> listaprecios2) {
		this.listaprecios2 = listaprecios2;
	}

	public Listaprecio addListaprecios2(Listaprecio listaprecios2) {
		getListaprecios2().add(listaprecios2);
		listaprecios2.setUsuario2(this);

		return listaprecios2;
	}

	public Listaprecio removeListaprecios2(Listaprecio listaprecios2) {
		getListaprecios2().remove(listaprecios2);
		listaprecios2.setUsuario2(null);

		return listaprecios2;
	}

	public List<Listaprecio> getListaprecios3() {
		return this.listaprecios3;
	}

	public void setListaprecios3(List<Listaprecio> listaprecios3) {
		this.listaprecios3 = listaprecios3;
	}

	public Listaprecio addListaprecios3(Listaprecio listaprecios3) {
		getListaprecios3().add(listaprecios3);
		listaprecios3.setUsuario3(this);

		return listaprecios3;
	}

	public Listaprecio removeListaprecios3(Listaprecio listaprecios3) {
		getListaprecios3().remove(listaprecios3);
		listaprecios3.setUsuario3(null);

		return listaprecios3;
	}

	public List<Notacredito> getNotacreditos() {
		return this.notacreditos;
	}

	public void setNotacreditos(List<Notacredito> notacreditos) {
		this.notacreditos = notacreditos;
	}

	public Notacredito addNotacredito(Notacredito notacredito) {
		getNotacreditos().add(notacredito);
		notacredito.setUsuario(this);

		return notacredito;
	}

	public Notacredito removeNotacredito(Notacredito notacredito) {
		getNotacreditos().remove(notacredito);
		notacredito.setUsuario(null);

		return notacredito;
	}

	public List<Producto> getProductos1() {
		return this.productos1;
	}

	public void setProductos1(List<Producto> productos1) {
		this.productos1 = productos1;
	}

	public Producto addProductos1(Producto productos1) {
		getProductos1().add(productos1);
		productos1.setUsuario1(this);

		return productos1;
	}

	public Producto removeProductos1(Producto productos1) {
		getProductos1().remove(productos1);
		productos1.setUsuario1(null);

		return productos1;
	}

	public List<Producto> getProductos2() {
		return this.productos2;
	}

	public void setProductos2(List<Producto> productos2) {
		this.productos2 = productos2;
	}

	public Producto addProductos2(Producto productos2) {
		getProductos2().add(productos2);
		productos2.setUsuario2(this);

		return productos2;
	}

	public Producto removeProductos2(Producto productos2) {
		getProductos2().remove(productos2);
		productos2.setUsuario2(null);

		return productos2;
	}

	public List<Producto> getProductos3() {
		return this.productos3;
	}

	public void setProductos3(List<Producto> productos3) {
		this.productos3 = productos3;
	}

	public Producto addProductos3(Producto productos3) {
		getProductos3().add(productos3);
		productos3.setUsuario3(this);

		return productos3;
	}

	public Producto removeProductos3(Producto productos3) {
		getProductos3().remove(productos3);
		productos3.setUsuario3(null);

		return productos3;
	}

	public List<Rubro> getRubros1() {
		return this.rubros1;
	}

	public void setRubros1(List<Rubro> rubros1) {
		this.rubros1 = rubros1;
	}

	public Rubro addRubros1(Rubro rubros1) {
		getRubros1().add(rubros1);
		rubros1.setUsuario1(this);

		return rubros1;
	}

	public Rubro removeRubros1(Rubro rubros1) {
		getRubros1().remove(rubros1);
		rubros1.setUsuario1(null);

		return rubros1;
	}

	public List<Rubro> getRubros2() {
		return this.rubros2;
	}

	public void setRubros2(List<Rubro> rubros2) {
		this.rubros2 = rubros2;
	}

	public Rubro addRubros2(Rubro rubros2) {
		getRubros2().add(rubros2);
		rubros2.setUsuario2(this);

		return rubros2;
	}

	public Rubro removeRubros2(Rubro rubros2) {
		getRubros2().remove(rubros2);
		rubros2.setUsuario2(null);

		return rubros2;
	}

	public List<Rubro> getRubros3() {
		return this.rubros3;
	}

	public void setRubros3(List<Rubro> rubros3) {
		this.rubros3 = rubros3;
	}

	public Rubro addRubros3(Rubro rubros3) {
		getRubros3().add(rubros3);
		rubros3.setUsuario3(this);

		return rubros3;
	}

	public Rubro removeRubros3(Rubro rubros3) {
		getRubros3().remove(rubros3);
		rubros3.setUsuario3(null);

		return rubros3;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Usuariosecurity> getUsuariosecurities() {
		return this.usuariosecurities;
	}

	public void setUsuariosecurities(List<Usuariosecurity> usuariosecurities) {
		this.usuariosecurities = usuariosecurities;
	}

	public Usuariosecurity addUsuariosecurity(Usuariosecurity usuariosecurity) {
		getUsuariosecurities().add(usuariosecurity);
		usuariosecurity.setUsuario(this);

		return usuariosecurity;
	}

	public Usuariosecurity removeUsuariosecurity(Usuariosecurity usuariosecurity) {
		getUsuariosecurities().remove(usuariosecurity);
		usuariosecurity.setUsuario(null);

		return usuariosecurity;
	}

	public List<Venta> getVentas() {
		return this.ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public Venta addVenta(Venta venta) {
		getVentas().add(venta);
		venta.setUsuario(this);

		return venta;
	}

	public Venta removeVenta(Venta venta) {
		getVentas().remove(venta);
		venta.setUsuario(null);

		return venta;
	}
	
	public String getFechaString(){
		SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = simple.format(fechaRegistro);
		return fecha;
	}

}