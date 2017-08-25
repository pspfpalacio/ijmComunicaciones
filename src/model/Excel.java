package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the excels database table.
 * 
 */
@Entity
@Table(name="excels")
@NamedQuery(name="Excel.findAll", query="SELECT e FROM Excel e")
public class Excel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String ctacte;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	private String stock;

	private String ventas;

	public Excel() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCtacte() {
		return this.ctacte;
	}

	public void setCtacte(String ctacte) {
		this.ctacte = ctacte;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getStock() {
		return this.stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getVentas() {
		return this.ventas;
	}

	public void setVentas(String ventas) {
		this.ventas = ventas;
	}

}