package model;

import java.io.Serializable;

import javax.persistence.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the compra_cargavirtual database table.
 * 
 */
@Entity
@Table(name="compra_cargavirtual")
@NamedQuery(name="CompraCargavirtual.findAll", query="SELECT c FROM CompraCargavirtual c")
public class CompraCargavirtual implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="cantidad_monto")
	private float cantidadMonto;

	private Boolean enabled;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_alta")
	private Date fechaAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_baja")
	private Date fechaBaja;

	//bi-directional many-to-one association to CargaVirtual
	@ManyToOne
	@JoinColumn(name="id_cargavirtual")
	private CargaVirtual cargaVirtual;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuario_alta")
	private Usuario usuario1;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuario_baja")
	private Usuario usuario2;

	public CompraCargavirtual() {
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

	public CargaVirtual getCargaVirtual() {
		return this.cargaVirtual;
	}

	public void setCargaVirtual(CargaVirtual cargaVirtual) {
		this.cargaVirtual = cargaVirtual;
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
	
	public String getFechaString(){
		SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = simple.format(fechaAlta);
		return fecha;
	}
	
	public String getCantidadMontoString(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(cantidadMonto);
		return valor;
	}

}