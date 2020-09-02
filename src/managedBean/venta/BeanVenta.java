package managedBean.venta;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import model.Venta;
import model.VentasProducto;
import DAO.venta.DAOventa;

@ManagedBean
@SessionScoped
public class BeanVenta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DAOventa ventaDAO = new DAOventa();
	
	private List<VentasProducto> listaDetalle;
	private Venta venta;
	private int nroVenta;

	public DAOventa getVentaDAO() {
		return ventaDAO;
	}

	public void setVentaDAO(DAOventa ventaDAO) {
		this.ventaDAO = ventaDAO;
	}
	
	public List<VentasProducto> getListaDetalle() {
		return listaDetalle;
	}

	public void setListaDetalle(List<VentasProducto> listaDetalle) {
		this.listaDetalle = listaDetalle;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public int getNroVenta() {
		return nroVenta;
	}

	public void setNroVenta(int nroVenta) {
		this.nroVenta = nroVenta;
	}

	public String goConfirmarVenta(List<VentasProducto> detalle, Venta vent){
		FacesMessage msg = null;
		String retorno = "";
		if(!detalle.isEmpty()){
			retorno = "comprobanteVenta";
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "La lista de productos no puede estar vacia!", null);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
		return retorno;
	}

}
