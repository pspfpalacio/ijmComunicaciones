package model;

import java.io.Serializable;

import javax.persistence.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the movimiento_virtual database table.
 * 
 */
@Entity
@Table(name="movimiento_virtual")
@NamedQuery(name="MovimientoVirtual.findAll", query="SELECT m FROM MovimientoVirtual m")
public class MovimientoVirtual implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private float monto;

	//bi-directional many-to-one association to CargaVirtual
	@ManyToOne
	@JoinColumn(name="id_plataforma")
	private CargaVirtual cargaVirtual;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	public MovimientoVirtual() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public float getMonto() {
		return this.monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public CargaVirtual getCargaVirtual() {
		return this.cargaVirtual;
	}

	public void setCargaVirtual(CargaVirtual cargaVirtual) {
		this.cargaVirtual = cargaVirtual;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

}