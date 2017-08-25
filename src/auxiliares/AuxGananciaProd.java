package auxiliares;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Producto;

public class AuxGananciaProd {
	private int idVenta;
	private float subTotal;
	private float preCom;
	private float monGanancia;
	private int cantidad;
	private Date fecha;
	private Producto producto;
	
	
	
	
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getIdVenta() {
		return idVenta;
	}
	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}
	public float getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(float subTotal) {
		this.subTotal = subTotal;
	}
	public float getPreCom() {
		return preCom;
	}
	public void setPreCom(float preCom) {
		this.preCom = preCom;
	}
	public float getMonGanancia() {
		return monGanancia;
	}
	public void setMonGanancia(float monGanancia) {
		this.monGanancia = monGanancia;
	}
	public String getStringFecha(){
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(fecha);
	}
	
	public String getStringSubtotal(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(subTotal);
		return "$"+valor;
	}
	public String getStringPrecom(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(preCom);
		return "$"+valor;
	}
	
	public String getStringMonganancia(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(monGanancia);
		return "$"+valor;
	}
	
	
	
}
