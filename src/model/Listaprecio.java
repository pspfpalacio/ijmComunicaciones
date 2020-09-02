package model;

import java.io.Serializable;

import javax.persistence.*;

import DAO.listaPrecio.DAOListaPrecio;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the listaprecios database table.
 * 
 */
@Entity
@Table(name="listaprecios")
@NamedQuery(name="Listaprecio.findAll", query="SELECT l FROM Listaprecio l")
public class Listaprecio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private Boolean enabled;

	@Temporal(TemporalType.DATE)
	private Date fechaAlta;

	@Temporal(TemporalType.DATE)
	private Date fechaBaja;

	@Temporal(TemporalType.DATE)
	private Date fechaModificacion;

	private String nombre;

	//bi-directional many-to-one association to Cliente
	@OneToMany(mappedBy="listaprecio")
	private List<Cliente> clientes;

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

	//bi-directional many-to-one association to ListapreciosProducto
	@OneToMany(mappedBy="listaprecio")
	private List<ListapreciosProducto> listapreciosProductos;

	public Listaprecio() {
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

	public List<Cliente> getClientes() {
		return this.clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente addCliente(Cliente cliente) {
		getClientes().add(cliente);
		cliente.setListaprecio(this);

		return cliente;
	}

	public Cliente removeCliente(Cliente cliente) {
		getClientes().remove(cliente);
		cliente.setListaprecio(null);

		return cliente;
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

	public List<ListapreciosProducto> getListapreciosProductos() {
		return this.listapreciosProductos;
	}

	public void setListapreciosProductos(List<ListapreciosProducto> listapreciosProductos) {
		this.listapreciosProductos = listapreciosProductos;
	}

	public ListapreciosProducto addListapreciosProducto(ListapreciosProducto listapreciosProducto) {
		getListapreciosProductos().add(listapreciosProducto);
		listapreciosProducto.setListaprecio(this);

		return listapreciosProducto;
	}

	public ListapreciosProducto removeListapreciosProducto(ListapreciosProducto listapreciosProducto) {
		getListapreciosProductos().remove(listapreciosProducto);
		listapreciosProducto.setListaprecio(null);

		return listapreciosProducto;
	}
	
	public List<Cliente> getListadoClientes(){
		DAOListaPrecio DAOlistaprecio = new DAOListaPrecio();
		List<Cliente> lista = DAOlistaprecio.getListadoClientesLPP(id);
		return lista;
	}

}