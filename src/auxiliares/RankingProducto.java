package auxiliares;

import java.text.DecimalFormat;
import java.util.List;

import model.Compra;
import model.Producto;
import model.Venta;

public class RankingProducto {
	private Producto producto;
	private int cantidad;
	private float monto;
	private List<Venta> listaVenta;
	private List<Compra> listaCompra;

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
	public List<Venta> getListaVenta() {
		return listaVenta;
	}
	public void setListaVenta(List<Venta> listaVenta) {
		this.listaVenta = listaVenta;
	}
	public List<Compra> getListaCompra() {
		return listaCompra;
	}
	public void setListaCompra(List<Compra> listaCompra) {
		this.listaCompra = listaCompra;
	}
	public float getMonto() {
		return monto;
	}
	public void setMonto(float monto) {
		this.monto = monto;
	}
	public String getMontoString(){
		DecimalFormat formato = new DecimalFormat("##,##0.00");
		String valor = formato.format(monto);
		return valor;
	}
	public String getCantidadString(){
		String valor = Integer.toString(cantidad);
		return valor;
	}
	public String getNombreProducto(){
		return producto.getNombre();
	}

}
