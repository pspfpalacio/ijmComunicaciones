package auxiliares;

import java.text.DecimalFormat;

public class AuxiliarProducto {
	
	private int id;
	private String nombre;
	private int cantidad;
	private float precio;
	private String precioString;
	private float ptosDescuento;
	private String subtotal;

	public AuxiliarProducto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public float getPtosDescuento() {
		return ptosDescuento;
	}

	public void setPtosDescuento(float ptosDescuento) {
		this.ptosDescuento = ptosDescuento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public String getSubtotal() {
		double subtot = cantidad * precio;
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		subtotal = formatear.format(subtot);
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	public String getPrecioString() {
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		precioString = formatear.format(precio);
		return precioString;
	}

	public void setPrecioString(String precioString) {
		this.precioString = precioString;
	}

}
