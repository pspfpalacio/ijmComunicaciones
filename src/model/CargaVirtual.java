package model;

import java.io.Serializable;

import javax.persistence.*;

import java.text.DecimalFormat;
import java.util.List;


/**
 * The persistent class for the carga_virtual database table.
 * 
 */
@Entity
@Table(name="carga_virtual")
@NamedQuery(name="CargaVirtual.findAll", query="SELECT c FROM CargaVirtual c")
public class CargaVirtual implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="cantidad_monto")
	private float cantidadMonto;

	private Boolean enabled;

	private String nombre;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	//bi-directional many-to-one association to CompraCargavirtual
	@OneToMany(mappedBy="cargaVirtual")
	private List<CompraCargavirtual> compraCargavirtuals;

	//bi-directional many-to-one association to MovimientoVirtual
	@OneToMany(mappedBy="cargaVirtual")
	private List<MovimientoVirtual> movimientoVirtuals;

	public CargaVirtual() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getCantidadMonto() {
		return this.cantidadMonto;
	}

	public void setCantidadMonto(float cantidadMonto) {
		this.cantidadMonto = cantidadMonto;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public List<CompraCargavirtual> getCompraCargavirtuals() {
		return this.compraCargavirtuals;
	}

	public void setCompraCargavirtuals(List<CompraCargavirtual> compraCargavirtuals) {
		this.compraCargavirtuals = compraCargavirtuals;
	}

	public CompraCargavirtual addCompraCargavirtual(CompraCargavirtual compraCargavirtual) {
		getCompraCargavirtuals().add(compraCargavirtual);
		compraCargavirtual.setCargaVirtual(this);

		return compraCargavirtual;
	}

	public CompraCargavirtual removeCompraCargavirtual(CompraCargavirtual compraCargavirtual) {
		getCompraCargavirtuals().remove(compraCargavirtual);
		compraCargavirtual.setCargaVirtual(null);

		return compraCargavirtual;
	}

	public List<MovimientoVirtual> getMovimientoVirtuals() {
		return this.movimientoVirtuals;
	}

	public void setMovimientoVirtuals(List<MovimientoVirtual> movimientoVirtuals) {
		this.movimientoVirtuals = movimientoVirtuals;
	}

	public MovimientoVirtual addMovimientoVirtual(MovimientoVirtual movimientoVirtual) {
		getMovimientoVirtuals().add(movimientoVirtual);
		movimientoVirtual.setCargaVirtual(this);

		return movimientoVirtual;
	}

	public MovimientoVirtual removeMovimientoVirtual(MovimientoVirtual movimientoVirtual) {
		getMovimientoVirtuals().remove(movimientoVirtual);
		movimientoVirtual.setCargaVirtual(null);

		return movimientoVirtual;
	}
	
	public String getCantidadMontoString(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(cantidadMonto);
		return valor;
	}

}