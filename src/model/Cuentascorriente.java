package model;

import java.io.Serializable;

import javax.persistence.*;

import DAO.pago.DaoPago;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the cuentascorrientes database table.
 * 
 */
@Entity
@Table(name="cuentascorrientes")
@NamedQuery(name="Cuentascorriente.findAll", query="SELECT c FROM Cuentascorriente c")
public class Cuentascorriente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date fechaAlta;

	private int idMovimiento;

	private float monto;

	private String tipoMovimiento;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="idCliente")
	private Cliente cliente;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idUsuarioAlta")
	private Usuario usuario;

	public Cuentascorriente() {
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

	public String getTipoMovimiento() {
		return this.tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
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
	
	public boolean getEstadoItem(){
		boolean valor;
		if(tipoMovimiento.equals("Inicializacion Cuenta Corriente") || tipoMovimiento.equals("Modificacion Cuenta Corriente") 
				|| tipoMovimiento.contains("Debito") || tipoMovimiento.contains("Acreditacion") || tipoMovimiento.contains("COMPRA CARGA VIRTUAL -")){
			valor = true;
		}else{
			valor = false;
		}
		return valor;
	}
	
	public String getTipo(){
		String tipo;
		DaoPago DAOPago = new DaoPago();
		if(tipoMovimiento.equals("PAGO")){
			tipo = "PAGO - ";
			Pago pago = new Pago();
			pago = DAOPago.getPagoPorId(idMovimiento);
			if(cliente.getId() != pago.getCliente1().getId()){
				tipo = tipo + "(Receptor: " + pago.getCliente1().getApellido() + ")";
			}
			if(cliente.getId() != pago.getCliente2().getId()){
				tipo = tipo + "(Emisor: " + pago.getCliente2().getApellido() + ")";
			}
		}else{
			tipo = tipoMovimiento;
		}
		return tipo;
	}

}