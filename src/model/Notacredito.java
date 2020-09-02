package model;

import java.io.Serializable;

import javax.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the notacredito database table.
 * 
 */
@Entity
@Table(name="notacredito")
@NamedQuery(name="Notacredito.findAll", query="SELECT n FROM Notacredito n")
public class Notacredito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date fechaAlta;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idUsuario")
	private Usuario usuario;

	//bi-directional many-to-one association to Venta
	@ManyToOne
	@JoinColumn(name="idVenta")
	private Venta venta;

	public Notacredito() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Venta getVenta() {
		return this.venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}
	
	public String getFechaString(){
		SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
		return simple.format(fechaAlta);		
	}

}