package auxiliares;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Venta;

public class AuxGanancia {
	
	private Venta venta;
	private float preciocompra;
	private float precioventa;
	private float ganancia;
	private Date fecha;
	
	
	
	public Venta getVenta() {
		return venta;
	}
	public int getIdVenta() {
		return venta.getId();
	}
	public void setVenta(Venta venta) {
		this.venta = venta;
	}
	public float getPreciocompra() {
		return preciocompra;
	}
	public void setPreciocompra(float preciocompra) {
		this.preciocompra = preciocompra;
	}
	public float getPrecioventa() {
		return precioventa;
	}
	public void setPrecioventa(float precioventa) {
		this.precioventa = precioventa;
	}
	public float getGanancia() {
		return ganancia;
	}
	public void setGanancia(float ganancia) {
		this.ganancia = ganancia;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String getPrecioCompraString(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(preciocompra);
		return "$"+valor;
	}
	
	public String getPrecioVentaString(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(precioventa);
		return "$"+valor;
	}
	
	public String getGananciaString(){
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(ganancia);
		return "$"+valor;
	}
	
	public String getStringFecha(){
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(fecha);
	}
	
	public String getCliente(){
		String nombreCliente = "";
		if(venta != null){
			nombreCliente = venta.getCliente().getNombre();
		}
		return nombreCliente;
	}

}
