package model;

import java.io.Serializable;

import javax.persistence.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the caja database table.
 * 
 */
@Entity
@Table(name="caja")
@NamedQuery(name="Caja.findAll", query="SELECT c FROM Caja c")
public class Caja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String concepto;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private int idMovimiento;

	private float monto;

	private String tipo;

	private float total;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuario")
	private Usuario usuarioBean;

	public Caja() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConcepto() {
		return this.concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public float getTotal() {
		return this.total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Usuario getUsuarioBean() {
		return this.usuarioBean;
	}

	public void setUsuarioBean(Usuario usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	
	public String getFechaString(){
		SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
		String fechaR = simple.format(fecha);
		return fechaR;
	}
	
	public String getMontoString(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(monto);
		return valor;
	}
	
	public String getTotalString(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(total);
		return valor;
	}

}