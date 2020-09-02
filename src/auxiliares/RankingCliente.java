package auxiliares;

import java.text.DecimalFormat;
import java.util.List;

import model.Cliente;
import model.Compra;
import model.Venta;

public class RankingCliente {
	private Cliente cliente;
	private float monto;
	private int cantidad;
	private List<Venta> listaVenta;
	private List<Compra> listaCompra;

	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public float getMonto() {
		return monto;
	}
	public void setMonto(float monto) {
		this.monto = monto;
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
	public String getMontoString(){
		DecimalFormat formato = new DecimalFormat("##,##0.00");
		String valor = formato.format(monto);
		return valor;
	}
	public String getCantidadString(){
		String valor = Integer.toString(cantidad);
		return valor;
	}
	public String getApellidoCliente(){
		String nombre = cliente.getApellido();
		return nombre;
	}
	public String getNegocioCliente(){
		String nombre = cliente.getNombre();
		return nombre;
	}

}
