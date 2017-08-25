package model;

import java.io.Serializable;

import javax.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the cuentas database table.
 * 
 */
@Entity
@Table(name="cuentas")
@NamedQuery(name="Cuenta.findAll", query="SELECT c FROM Cuenta c")
public class Cuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private float debe;

	private String detalle;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	private float haber;

	@Column(name="id_movimiento")
	private int idMovimiento;

	private float monto;

	@Column(name="nombre_tabla")
	private String nombreTabla;

	private float saldo;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	public Cuenta() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getDebe() {
		return this.debe;
	}

	public void setDebe(float debe) {
		this.debe = debe;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public float getHaber() {
		return this.haber;
	}

	public void setHaber(float haber) {
		this.haber = haber;
	}

	public int getIdMovimiento() {
		return this.idMovimiento;
	}

	public void setIdMovimiento(int idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public float getMonto() {
		return this.monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public String getNombreTabla() {
		return this.nombreTabla;
	}

	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

	public float getSaldo() {
		return this.saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
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
	
	public String getFechaString() {
		SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			String fe = fechaFormat.format(fecha);
			return fe;
		} catch(Exception e) {
			e.getMessage();
			return "";
		}
	}

}