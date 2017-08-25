package managedBean.cuenta;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import managedBean.reporte.BeanReporte;
import model.Cliente;
import model.Cuenta;
import DAO.cuentas.DAOCuentas;

@ManagedBean
@SessionScoped
public class BeanCuenta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DAOCuentas cuentaDAO = new DAOCuentas();
	
	private List<Cuenta> listaCuentas;
	private List<Cuenta> filteredCuentas;
	private Cuenta cuenta;
	private Date fechaInicio;
	private Date fechaFin;
	private Cliente cliente;
	private String pagina;

	public DAOCuentas getCuentaDAO() {
		return cuentaDAO;
	}

	public void setCuentaDAO(DAOCuentas cuentaDAO) {
		this.cuentaDAO = cuentaDAO;
	}

	public List<Cuenta> getListaCuentas() {
		return listaCuentas;
	}

	public void setListaCuentas(List<Cuenta> listaCuentas) {
		this.listaCuentas = listaCuentas;
	}

	public List<Cuenta> getFilteredCuentas() {
		return filteredCuentas;
	}

	public void setFilteredCuentas(List<Cuenta> filteredCuentas) {
		this.filteredCuentas = filteredCuentas;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public String goCuentaCorriente(Cliente persona, String pag){
		String retorno = "";
		pagina = pag;
		cliente = new Cliente();
		listaCuentas = null;
		filteredCuentas = null;
		listaCuentas = new ArrayList<Cuenta>();
		filteredCuentas = new ArrayList<Cuenta>();
		listaCuentas = cuentaDAO.getLista(persona);
		if(!listaCuentas.isEmpty()){
			filteredCuentas = listaCuentas;
			retorno = "cuenta";
		}else{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No posee ningún movimiento"
					+ " en su Cuenta Corriente", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		cliente = persona;
		fechaFin = null;
		fechaInicio = null;
		return retorno;
	}
	
	public String goCuentaCorrienteVirtual(Cliente persona, String pag){
		String retorno = "";
		pagina = pag;
		cliente = new Cliente();
		listaCuentas = null;
		filteredCuentas = null;
		listaCuentas = new ArrayList<Cuenta>();
		filteredCuentas = new ArrayList<Cuenta>();
		listaCuentas = cuentaDAO.getLista(persona);
		if(!listaCuentas.isEmpty()){
			filteredCuentas = listaCuentas;
			retorno = "cuentavirtual";
		}else{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No posee ningún movimiento"
					+ " en su Cuenta Corriente", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		cliente = persona;
		fechaFin = null;
		fechaInicio = null;
		return retorno;
	}
	
	public String goCuentaCorrienteVistaCliente(Cliente persona, String pag){
		String retorno = "";
		pagina = pag;
		cliente = new Cliente();
		listaCuentas = null;
		filteredCuentas = null;
		listaCuentas = new ArrayList<Cuenta>();
		filteredCuentas = new ArrayList<Cuenta>();
		listaCuentas = cuentaDAO.getLista(persona);
		if(!listaCuentas.isEmpty()){
			filteredCuentas = listaCuentas;
			retorno = "cuentavistacliente";
		}else{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No posee ningún movimiento"
					+ " en su Cuenta Corriente", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		cliente = persona;
		fechaFin = null;
		fechaInicio = null;
		return retorno;
	}
	
	public String volver(){
		return pagina;
	}
	
	public void getCuentaCorriente(){
		if(fechaInicio != null && fechaFin != null){
			listaCuentas = new ArrayList<Cuenta>();
			filteredCuentas = new ArrayList<Cuenta>();
			listaCuentas = cuentaDAO.getLista(fechaInicio, fechaFin, cliente);
			filteredCuentas = listaCuentas;
		}else{
			listaCuentas = new ArrayList<Cuenta>();
			filteredCuentas = new ArrayList<Cuenta>();
			listaCuentas = cuentaDAO.getLista(cliente);
			filteredCuentas = listaCuentas;
		}
	}
	
	public void generarReporte(){
		BeanReporte beanReporte = new BeanReporte();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("nombre", cliente.getNombre());
		parameters.put("apellido", cliente.getApellido());
		parameters.put("telefono", cliente.getTelefono());
		parameters.put("email", cliente.getDireccion());
		if(fechaInicio != null && fechaFin != null){
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			parameters.put("fechai", formato.format(fechaInicio));
			parameters.put("fechaf", formato.format(fechaFin));
		}else{
			parameters.put("fechai", "");
			parameters.put("fechaf", "");
		}
		beanReporte.generar(parameters, filteredCuentas, "cuentas", "inline");
	}
	
	public void generarReporteVirtual(){
		BeanReporte beanReporte = new BeanReporte();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("nombre", cliente.getNombre());
		if(fechaInicio != null && fechaFin != null){
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			parameters.put("fechai", formato.format(fechaInicio));
			parameters.put("fechaf", formato.format(fechaFin));
		}else{
			parameters.put("fechai", "");
			parameters.put("fechaf", "");
		}
		beanReporte.generar(parameters, filteredCuentas, "cuentasvirtual", "inline");
	}
	
	public void generarReporteClientes(){
		BeanReporte beanReporte = new BeanReporte();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("nombre", cliente.getNombre());
		parameters.put("apellido", cliente.getApellido());
		parameters.put("telefono", cliente.getTelefono());
		parameters.put("email", cliente.getDireccion());
		if(fechaInicio != null && fechaFin != null){
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			parameters.put("fechai", formato.format(fechaInicio));
			parameters.put("fechaf", formato.format(fechaFin));
		}else{
			parameters.put("fechai", "");
			parameters.put("fechaf", "");
		}
		beanReporte.generar(parameters, filteredCuentas, "cuentascliente", "inline");
	}

}
