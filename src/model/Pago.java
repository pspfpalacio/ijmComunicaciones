package model;

import java.io.Serializable;

import javax.persistence.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the pagos database table.
 * 
 */
@Entity
@Table(name="pagos")
@NamedQuery(name="Pago.findAll", query="SELECT p FROM Pago p")
public class Pago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String descripcion;

	@Temporal(TemporalType.DATE)
	private Date fechaAlta;

	private float monto;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente1;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_proveedor")
	private Cliente cliente2;

	public Pago() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public float getMonto() {
		return this.monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public Cliente getCliente1() {
		return this.cliente1;
	}

	public void setCliente1(Cliente cliente1) {
		this.cliente1 = cliente1;
	}

	public Cliente getCliente2() {
		return this.cliente2;
	}

	public void setCliente2(Cliente cliente2) {
		this.cliente2 = cliente2;
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

}