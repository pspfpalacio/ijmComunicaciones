package managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ar.com.clases.Cajas;
import ar.com.clases.CuentasCorrientes;
import auxiliares.AuxGanancia;
import auxiliares.AuxGananciaProd;
import auxiliares.AuxListapreciosProducto;
import auxiliares.AuxiliarProducto;
import auxiliares.ExPlataforma;
import auxiliares.Helper;
import auxiliares.RankinVirtual;
import auxiliares.RankingCliente;
import auxiliares.RankingProducto;

import com.csvreader.CsvReader;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Header;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

import model.Caja;
import model.CargaVirtual;
import model.Cliente;
import model.ClientePlataforma;
import model.Compra;
import model.CompraCargavirtual;
import model.Cuenta;
import model.Cuentascorriente;
import model.DetalleCompra;
import model.Excel;
import model.Listaprecio;
import model.ListapreciosProducto;
import model.MovimientoVirtual;
import model.Notacredito;
import model.Pago;
import model.Producto;
import model.RolVista;
import model.Role;
import model.Rubro;
import model.Stock;
import model.StockDetalle;
import model.Usuario;
import model.Venta;
import model.VentasProducto;
import model.Vista;
import DAO.archivo.DAOArchivo;
import DAO.caja.DAOcaja;
import DAO.cargasvirtuales.DAOCargaVirtual;
import DAO.cliente.DAOcliente;
import DAO.compra.DAOCompra;
import DAO.cuentas.DAOCuentas;
import DAO.cuentascorriente.DAOcuentascorriente;
import DAO.excel.DAOExcel;
import DAO.listaPrecio.DAOListaPrecio;
import DAO.pago.DaoPago;
import DAO.producto.DAOProducto;
import DAO.proveedor.DAOproveedor;
import DAO.rubro.DAOrubro;
import DAO.usuario.DAOusuario;
import DAO.venta.DAOventa;


@ManagedBean(name = "MBean")
@SessionScoped
public class Managed implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date currentDate;

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	// /////////////////////////////////////////////////RUBRO///////////////////////////////////////////////////////////////////
	private Rubro rubro = new Rubro();
	private List<Rubro> listadoRubros;
	private List<Rubro> filteredListadoRubros;
	private DAOrubro DAORubro;
	private int estadoRubro = 2;
	private String buscarRubro;
	
	public String goRubro(){
		System.out.println("Metodo goRubro()");
		rubro = new Rubro();
		DAORubro = new DAOrubro();
		listadoRubros = new ArrayList<Rubro>();
		filteredListadoRubros = new ArrayList<Rubro>();
		listadoRubros = DAORubro.getListadoRubros();
		filteredListadoRubros = listadoRubros;
		estadoRubro = 2;
		buscarRubro = "";
		return "rubro";
	}

	public String getBuscarRubro() {
		return buscarRubro;
	}

	public void setBuscarRubro(String buscarRubro) {
		this.buscarRubro = buscarRubro;
	}

	public int getEstadoRubro() {
		return estadoRubro;
	}

	public void setEstadoRubro(int estado) {
		this.estadoRubro = estado;
	}

	public DAOrubro getDAORubro() {
		return DAORubro;
	}

	public void setDAO(DAOrubro dAO) {
		DAORubro = dAO;
	}

	public List<Rubro> getFilteredListadoRubros() {
		return filteredListadoRubros;
	}

	public void setFilteredListadoRubros(List<Rubro> filteredListadoRubros) {
		this.filteredListadoRubros = filteredListadoRubros;
	}

	public List<Rubro> getListadoRubros() {
		DAORubro = new DAOrubro();
		listadoRubros = DAORubro.getListadoRubros();
		return listadoRubros;
	}

	public void setListadoRubros(List<Rubro> listadoRubros) {
		this.listadoRubros = listadoRubros;
	}

	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	public String buscarTablaRubro() {
		System.out.println("Metodo buscarTablaRubro()");
		DAORubro = new DAOrubro();
		List<Rubro> listAux = new ArrayList<Rubro>();
		if (getEstadoRubro() == 2) {
			for (Rubro rubro : getListadoRubros()) {
				if (rubro.getNombre().contains(getBuscarRubro())) {
					listAux.add(rubro);
				}
			}
		} else {
			for (Rubro rubro : filteredListadoRubros) {
				if (rubro.getNombre().contains(getBuscarRubro())) {
					listAux.add(rubro);
				}
			}
		}

		listadoRubros = null;
		filteredListadoRubros = null;
		// lista = DAORubro.getRubrosLike(getBuscarRubro());
		setFilteredListadoRubros(listAux);
		return "rubro";
	}

	public String onRubroView() {
		System.out.println("Metodo onRubroView()");
		listadoRubros = null;
		filteredListadoRubros = null;
		DAORubro = new DAOrubro();
		List<Rubro> lista = new ArrayList<Rubro>();
		lista = DAORubro.getListadoRubros();
		setListadoRubros(lista);
		setFilteredListadoRubros(lista);
		return "rubro";
	}

	public void guardarRubro() {
		System.out.println("Metodo guardarRubro()");
		FacesMessage msg = null;
		if(!rubro.getNombre().isEmpty() && rubro.getNombre() != null && rubro.getNombre() != ""){
			rubro.setEnabled(true);
			setCurrentDate(new Date());
			rubro.setFechaModificacion(getCurrentDate());
			rubro.setFechaAlta(getCurrentDate());
			rubro.setUsuario1(usuario);
			rubro.setUsuario3(usuario);
			DAORubro = new DAOrubro();
			int valor = DAORubro.insertRubro(rubro);
			if (valor != 0) {
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rubro: "
						+ rubro.getNombre() + " Guardado correctamente.", null);
				rubro = new Rubro();
				onRubroView();
			} else {
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Error: El Rubro: " + rubro.getNombre()
								+ " no se ha podido Guardar.", null);
				rubro = new Rubro();
				onRubroView();
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error!", "El campo Nombre es obligatorio");
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);		
	}

	public List<Rubro> getRubrosXEstado(Integer estado) {
		System.out.println("Metodo getRubrosXEstado()");

		DAORubro = new DAOrubro();
		filteredListadoRubros = null;
		List<Rubro> lista = new ArrayList<Rubro>();
		if (estado == 2) {
			lista = DAORubro.getListadoRubros();
			filteredListadoRubros = lista;
			setEstadoRubro(2);
		} else if (estado == 0) {
			setEstadoRubro(0);
			lista = DAORubro.getListadoRubrosDesact();
			filteredListadoRubros = lista;
		} else {
			setEstadoRubro(1);
			lista = DAORubro.getListadoRubrosAct();
			filteredListadoRubros = lista;
		}
		return filteredListadoRubros;
	}

	public void onEditRubro(Rubro rubro2) {
		System.out.println("Metodo onEditRubro()");
		rubro = new Rubro();
		rubro = rubro2;
	}

	public String activar(Rubro rubro) {
		System.out.println("Metodo activar()");
		FacesMessage msg = null;
		setCurrentDate(new Date());
		rubro.setEnabled(true);
		rubro.setFechaModificacion(getCurrentDate());
		rubro.setUsuario3(usuario);
		int valor = DAORubro.updateEstadoRubro(rubro);
		if (valor != 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rubro: "
					+ rubro.getNombre() + " Activado correctamente.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			rubro = null;
			getListadoRubros();
			return "rubro";
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: El Rubro: " + rubro.getNombre()
							+ " no se ha podido Activar.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}
	}

	/**
	 * 
	 * @param client
	 * @return
	 */
	public String desactivar(Rubro rubro) {
		System.out.println("Metodo desactivar()");
		DAORubro = new DAOrubro();
		FacesMessage msg = null;
		if (!DAORubro.consultarListaAsoc(rubro)) {
			setCurrentDate(new Date());
			rubro.setEnabled(false);
			rubro.setFechaBaja(getCurrentDate());
			rubro.setUsuario2(usuario);
			int valor = DAORubro.updateEstadoRubro(rubro);
			if (valor == 1) {
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rubro: "
						+ rubro.getNombre() + " Desactivado correctamente.",
						null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				rubro = null;
				getListadoRubros();
				return "rubro";
			} else {
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Error: El Rubro: " + rubro.getNombre()
								+ " no se ha podido Desactivar.", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "";
			}
		} else {
			msg = new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					"Error: El Rubro: "
							+ rubro.getNombre()
							+ " no se ha podido desactivar por tener productos asociados.",
					null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}
	}

	public String cancelarRubro() {
		System.out.println("Metodo cancelarRubro()");
		rubro = new Rubro();
		return "rubro";
	}

	// ///////////////////////////////////////////////////PROVEEDORES///////////////////////////////////////////////////////

	private List<Cliente> proveedores;
	private List<Cliente> filteredProveedores;
	private Cliente proveedor = new Cliente();
	private DAOproveedor DAOProveedor;
	private int estadoProveedor = 2;
	private float saldoProveedor;
	private Cliente proveedorParaFiltro = new Cliente();
	
	public String goProveedores(){
		System.out.println("Metodo goProveedores()");
		proveedores = new ArrayList<Cliente>();
		filteredProveedores = new ArrayList<Cliente>();
		proveedor = new Cliente();
		DAOProveedor = new DAOproveedor();
		proveedores = DAOProveedor.getListadoProveedores();
		filteredProveedores = proveedores;
		estadoProveedor = 2;
		saldoProveedor = 0;
		return "proveedor";
	}

	public float getSaldoProveedor() {
		return saldoProveedor;
	}

	public void setSaldoProveedor(float saldoProveedor) {
		this.saldoProveedor = saldoProveedor;
	}

	public int getEstadoProveedor() {
		return estadoProveedor;
	}

	public void setEstadoProveedor(int estado) {
		this.estadoProveedor = estado;
	}

	public List<Cliente> getProveedores() {
		DAOProveedor = new DAOproveedor();
		proveedores = DAOProveedor.getListadoProveedores();
		return proveedores;
	}

	public void setProveedores(List<Cliente> proveedores) {
		this.proveedores = proveedores;
	}

	public List<Cliente> getFilteredProveedores() {
		return filteredProveedores;
	}

	public void setFilteredProveedores(List<Cliente> filteredProveedores) {
		this.filteredProveedores = filteredProveedores;
	}

	public Cliente getProveedor() {
		return proveedor;
	}

	public void setProveedor(Cliente proveedor) {
		this.proveedor = proveedor;
	}

	public DAOproveedor getDAOProveedor() {
		return DAOProveedor;
	}

	public void setDAOProveedor(DAOproveedor dAO) {
		DAOProveedor = dAO;
	}

	public String onProveedorView() {
		System.out.println("Metodo onProveedorView()");
		proveedor = new Cliente();
		proveedores = null;
		filteredProveedores = null;
		DAOProveedor = new DAOproveedor();
		List<Cliente> lista = new ArrayList<Cliente>();
		lista = DAOProveedor.getListadoProveedores();
		setProveedores(lista);
		setFilteredProveedores(lista);
		return "proveedor";
	}

	/**
	 * 
	 * @param estado
	 * @return
	 */
	public List<Cliente> getProveedoresXEstado(Integer estado) {
		System.out.println("Metodo getProveedorXEstado()");
		DAOProveedor = new DAOproveedor();
		filteredProveedores = null;
		List<Cliente> lista = new ArrayList<Cliente>();
		if (estado == 2) {
			lista = DAOProveedor.getListadoProveedores();
			setFilteredProveedores(lista);
			setEstadoProveedor(2);
		} else if (estado == 0) {
			setEstadoProveedor(0);
			lista = DAOProveedor.getListadoProveedoresDesact();
			setFilteredProveedores(lista);
		} else {
			setEstadoProveedor(1);
			lista = DAOProveedor.getListadoProveedoresAct();
			setFilteredProveedores(lista);
		}
		return filteredProveedores;
	}

	/**
	 * 
	 * @param client
	 */
	public void onEditProveedor(Cliente proveedore) {
		System.out.println("Metodo onEditProveedor()");
		proveedor = new Cliente();
		proveedor = proveedore;
		if (proveedor.getSaldo() != 0) {
			setSaldoProveedor(proveedor.getSaldo());
		}
	}

	/**
	 * 
	 * @return
	 */
	public void guardarProveedor() {
		System.out.println("Metodo guardarProveedor");
		DAOcc = new DAOcuentascorriente();
		cuentaDAO = new DAOCuentas();
		FacesMessage msg = null;
		if((!proveedor.getApellido().isEmpty() && proveedor.getApellido() != null && proveedor.getApellido() != "") 
				&& (!proveedor.getDireccion().isEmpty() && proveedor.getDireccion() != null && proveedor.getDireccion() != "") 
				&&(!proveedor.getMail().isEmpty() && proveedor.getMail() != null && proveedor.getMail() != "") 
				&& (!proveedor.getNombre().isEmpty() && proveedor.getNombre() != null && proveedor.getNombre() != "") 
				&& (!proveedor.getTelefono().isEmpty() && proveedor.getTelefono() != null && proveedor.getTelefono() != "")){
			
			if (proveedor.getId() != 0) {
				setCurrentDate(new Date());
				proveedor.setUsuario3(usuario);
				proveedor.setFechaModificacion(getCurrentDate());
				proveedor.setTipo("p");
				DAOProveedor = new DAOproveedor();
				int valor = 0;
				valor = DAOProveedor.updateProveedor(proveedor);
				if (proveedor.getSaldo() != getSaldoProveedor()) {
					Cuenta cuenta = new Cuenta();
					cuenta.setCliente(proveedor);
					cuenta.setFecha(new Date());
					cuenta.setDetalle("Modificación de Cuenta Corriente");
					cuenta.setUsuario(usuario);
					if(proveedor.getSaldo() > getSaldoProveedor()){
						float dDebe = proveedor.getSaldo() - getSaldoProveedor();
						float debe = dDebe;
						cuenta.setDebe(debe);
						cuenta.setMonto(debe);
					}else{
						float dHaber = getSaldoProveedor() - proveedor.getSaldo();
						float haber = dHaber;
						cuenta.setHaber(haber);
						cuenta.setMonto(haber);
					}
					cuenta.setSaldo(proveedor.getSaldo());
					cuentaDAO.insertar(cuenta);
				}
				if (valor != 0) {
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Proveedor: " + proveedor.getNombre() + " "
									+ proveedor.getApellido()
									+ " Modificado correctamente.", null);
					proveedor = new Cliente();
					onProveedorView();
				} else {
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Error: El Proveedor: " + proveedor.getNombre() + " "
									+ proveedor.getApellido()
									+ " no se pudo Guardar.", null);
					proveedor = new Cliente();
					onProveedorView();
				}
			} else {
				setCurrentDate(new Date());
				proveedor.setUsuario1(usuario);
				proveedor.setFechaAlta(getCurrentDate());
				proveedor.setEnabled(true);
				proveedor.setTipo("p");
				DAOProveedor = new DAOproveedor();
				int valor = 0;
				valor = DAOProveedor.insertProveedor(proveedor);
				if (proveedor.getSaldo() != 0) {
					Cuenta cuenta = new Cuenta();
					cuenta.setCliente(proveedor);
					cuenta.setFecha(new Date());
					cuenta.setDetalle("Inicialización de Cuenta Corriente");
					cuenta.setUsuario(usuario);
					cuenta.setDebe(proveedor.getSaldo());
					cuenta.setMonto(proveedor.getSaldo());					
					cuenta.setSaldo(proveedor.getSaldo());
					cuentaDAO.insertar(cuenta);
				}
				if (valor != 0) {
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Proveedor: " + proveedor.getNombre() + " "
									+ proveedor.getApellido()
									+ " Guardado correctamente.", null);
					proveedor = new Cliente();
					onProveedorView();
				} else {
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Error: El Proveedor: " + proveedor.getNombre() + " "
									+ proveedor.getApellido()
									+ " no se pudo Guardar.", null);
					proveedor = new Cliente();
					onProveedorView();
				}
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error!", "Todos los campos son obligatorios");
			proveedor = new Cliente();
			onProveedorView();
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * 
	 * @param client
	 * @return
	 */
	public String activarP(Cliente proveedor) {
		System.out.println("Metodo activarP");
		FacesMessage msg = null;
		setCurrentDate(new Date());
		proveedor.setEnabled(true);
		proveedor.setFechaModificacion(getCurrentDate());
		proveedor.setUsuario3(usuario);
		int valor = DAOProveedor.updateEstadoProveedor(proveedor);
		if (valor == 1) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Proveedor: "
					+ proveedor.getNombre() + " " + proveedor.getApellido()
					+ " Activado correctamente.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			proveedor = null;
			onProveedorView();
			return "";
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: El Proveedor: " + proveedor.getNombre() + " "
							+ proveedor.getApellido()
							+ " no se ha podido Activar.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}
	}

	/**
	 * 
	 * @param client
	 * @return
	 */
	public String desactivarP(Cliente proveedor) {
		System.out.println("Metodo desactivarP");
		FacesMessage msg = null;
		setCurrentDate(new Date());
		proveedor.setEnabled(false);
		proveedor.setFechaBaja(getCurrentDate());
		proveedor.setUsuario2(usuario);
		int valor = DAOProveedor.updateEstadoProveedor(proveedor);
		if (valor == 1) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Proveedor: "
					+ proveedor.getNombre() + " " + proveedor.getApellido()
					+ " Desactivado correctamente.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			proveedor = null;
			onProveedorView();
			return "";
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: El Proveedor: " + proveedor.getNombre() + " "
							+ proveedor.getApellido()
							+ " no se ha podido Desactivar.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}
	}

	public String cancelarProveedor() {
		System.out.println("Metodo cancelarProveedor()");
		proveedor = new Cliente();
		setSaldoProveedor(0);
		return "proveedor";
	}

	public String onViewCCP(Cliente proveedore) {
		System.out.println("Metodo onView()");
		proveedorParaFiltro = proveedore;
		DAOcc = new DAOcuentascorriente();
		FacesMessage msg = null;
		List<Cuentascorriente> lista = DAOcc.getListaCCProveedor(proveedore);
		Integer cantidad = 0;
		float monto = 0;
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		volverVistaCC = "cuentacorrienteProveedor";
		if (lista.isEmpty()) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Error: El Proveedor: " + proveedore.getNombre() + " "
							+ proveedore.getApellido()
							+ " no posee cuenta corriente.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		} else {
			for (Cuentascorriente cuentascorriente : lista) {
				cantidad++;
				if(cuentascorriente.getTipoMovimiento().equals("Inicializacion Cuenta Corriente") || 
						cuentascorriente.getTipoMovimiento().equals("COMPRA")){
					monto = cuentascorriente.getMonto() + monto;
				}else{
					monto = monto - cuentascorriente.getMonto();
				}				
			}
			String valor = formatear.format(monto);
			setProveedorCC(proveedore);
			setMontoTotalCC(monto);
			setMontoTotalCCString(valor);
			setCantidadCC(cantidad);
			setListaCuentasCorrienteProveedor(lista);
			return "cuentacorrienteProveedor";
		}
	}

	// ////////////////////////////////////////////////PRODUCTOS//////////////////////////////////////////////////////////

	private Producto producto = new Producto();
	private List<Producto> listadoProductos;
	private List<Producto> filteredListadoProductos;
	private DAOProducto DAOProducto;
	private int estadoProducto = 2;
	private Integer idRubroProducto;
	private Rubro rubroProducto = new Rubro();
	private HashMap<String, Integer> listaRubro;
	private List<Producto> listaProductoRubro;
	private String buscarProducto;
	private boolean precioCompraProd;
	private boolean stockProd;
	private List<Producto> pStockMinimo;
	private List<Producto> filteredpStockMinimo;
	private boolean renderedStockMinimo;

	public String goProductos() {
		System.out.println("Metodo goProdcutos()");
		producto = new Producto();
		listadoProductos = new ArrayList<Producto>();
		filteredListadoProductos = new ArrayList<Producto>();
		DAOProducto = new DAOProducto();
		listadoProductos = DAOProducto.getListadoProductos();
		filteredListadoProductos = listadoProductos;
		estadoProducto = 2;
		idRubroProducto = 0;
		rubroProducto = new Rubro();
//		HashMap<String, Integer> listaRubro = new LinkedHashMap<String, Integer>();
		listaProductoRubro = new ArrayList<Producto>();
		buscarProducto = "";
		precioCompraProd = false;
		stockProd = false;
		return "producto";
	}

	public List<Producto> getFilteredpStockMinimo() {
		return filteredpStockMinimo;
	}

	public void setFilteredpStockMinimo(List<Producto> filteredpStockMinimo) {
		this.filteredpStockMinimo = filteredpStockMinimo;
	}

	public boolean isRenderedStockMinimo() {
		return renderedStockMinimo;
	}

	public void setRenderedStockMinimo(boolean renderedStockMinimo) {
		this.renderedStockMinimo = renderedStockMinimo;
	}

	public List<Producto> getpStockMinimo() {
		return pStockMinimo;
	}

	public void setpStockMinimo(List<Producto> pStockMinimo) {
		this.pStockMinimo = pStockMinimo;
	}

	public boolean isPrecioCompraProd() {
		return precioCompraProd;
	}

	public void setPrecioCompraProd(boolean precioCompraProd) {
		this.precioCompraProd = precioCompraProd;
	}

	public boolean isStockProd() {
		return stockProd;
	}

	public void setStockProd(boolean stockProd) {
		this.stockProd = stockProd;
	}

	public List<Producto> getListaProductoRubro() {
		return listaProductoRubro;
	}

	public void setListaProductoRubro(List<Producto> listaProductoRubro) {
		this.listaProductoRubro = listaProductoRubro;
	}

	public String getBuscarProducto() {
		return buscarProducto;
	}

	public void setBuscarProducto(String buscarProducto) {
		this.buscarProducto = buscarProducto;
	}

	public Integer getIdRubroProducto() {
		return idRubroProducto;
	}

	public void setIdRubroProducto(Integer idRubroProducto) {
		this.idRubroProducto = idRubroProducto;
	}

	public HashMap<String, Integer> getListaRubro() {
		DAORubro = new DAOrubro();
		listaRubro = DAORubro.getHashMapRubrosAct();
		return listaRubro;
	}

	public void setListaRubro(HashMap<String, Integer> listaRubro) {
		this.listaRubro = listaRubro;
	}

	public Rubro getRubroProducto() {
		return rubroProducto;
	}

	public void setRubroProducto(Rubro rubro) {
		this.rubroProducto = rubro;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public List<Producto> getListadoProductos() {
		DAOProducto = new DAOProducto();
		listadoProductos = DAOProducto.getListadoProductos();
		return listadoProductos;
	}

	public void setListadoProductos(List<Producto> listadoProductos) {
		this.listadoProductos = listadoProductos;
	}

	public List<Producto> getFilteredListadoProductos() {
		return filteredListadoProductos;
	}

	public void setFilteredListadoProductos(
			List<Producto> filteredListadoProductos) {
		this.filteredListadoProductos = filteredListadoProductos;
	}

	public DAOProducto getDAOProducto() {
		return DAOProducto;
	}

	public void setDAO(DAOProducto dAO) {
		DAOProducto = dAO;
	}

	public int getEstadoProducto() {
		return estadoProducto;
	}

	public void setEstadoProducto(int estado) {
		this.estadoProducto = estado;
	}

	public void guardarProducto() {
		System.out.println("Metodo guardarProducto()");
		FacesMessage msg = null;
		if ((!producto.getNombre().isEmpty() && producto.getNombre() != null && producto
				.getNombre() != "")
				&& (idRubroProducto != null && idRubroProducto != 0)) {

			DAOProducto = new DAOProducto();
			DAORubro = new DAOrubro();
			DAOcompra = new DAOCompra();
			rubroProducto = DAORubro.getRubroXId(idRubroProducto);
			producto.setRubro(rubroProducto);
			producto.setEnabled(true);
			setCurrentDate(new Date());
			producto.setFechaAlta(getCurrentDate());
			producto.setFechaModificacion(getCurrentDate());
			producto.setUsuario1(usuario);
			producto.setUsuario3(usuario);
			boolean valorS = false;
			if (producto.getId() == 0) {
				if (producto.getStock() != 0 && producto.getPrecioCompra() != 0) {
					valorS = true;
				} else {
					producto.setStock(0);
					producto.setPrecioCompra(0);
				}
			}
			int valor = DAOProducto.insertProducto(producto);
			boolean valorSt = true;
			if (valorS) {
				Producto prod = new Producto();
				prod.setId(valor);
				Stock stock = new Stock();
				stock.setCantidad(producto.getStock());
				stock.setPrecioUnitario(producto.getPrecioCompra());
				stock.setProducto(prod);
				int valorSto = DAOcompra.insertUpdateStock(stock);
				if(valorSto == 0){
					valorSt = false;
				}
			}
			if (valor != 0 && valorSt) {
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto: "
						+ producto.getNombre() + " Guardado correctamente.",
						null);
				producto = new Producto();
				checkStockMinimo();
				onProductoView();
			} else {
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Error: El Producto: " + producto.getNombre()
								+ " no se pudo Guardar.", null);
				producto = new Producto();
				onProductoView();
			}
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error!",
					"El campo Nombre es obligatorio");
			producto = new Producto();
			onProductoView();
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onProductoView() {
		System.out.println("Metodo onProductoView()");
		precioCompraProd = false;
		stockProd = false;
		listadoProductos = null;
		filteredListadoProductos = null;
		DAOProducto = new DAOProducto();
		List<Producto> lista = new ArrayList<Producto>();
		lista = DAOProducto.getListadoProductos();
		setListadoProductos(lista);
		setFilteredListadoProductos(lista);
		return "producto";
	}

	public String buscarTablaProducto() {
		System.out.println("Metodo buscarTablaProducto()");
		DAOProducto = new DAOProducto();
		List<Producto> lista = new ArrayList<Producto>();
		List<Producto> listAux = new ArrayList<Producto>();
		if (idRubroProducto == 0 && estadoProducto == 2) {
			lista = DAOProducto.getListadoProductos();
			for (Producto producto : lista) {
				if (producto.getNombre().contains(getBuscarProducto())) {
					listAux.add(producto);
				}
			}
		}
		if (idRubroProducto == 0 && estadoProducto == 1) {
			lista = DAOProducto.getListadoProductosAct();
			for (Producto producto : lista) {
				if (producto.getNombre().contains(getBuscarProducto())) {
					listAux.add(producto);
				}
			}
		}
		if (idRubroProducto == 0 && estadoProducto == 0) {
			lista = DAOProducto.getListadoProductosDesact();
			for (Producto producto : lista) {
				if (producto.getNombre().contains(getBuscarProducto())) {
					listAux.add(producto);
				}
			}
		}
		if (idRubroProducto != 0) {
			for (Producto producto : getListaProductoRubro()) {
				if (producto.getNombre().contains(getBuscarProducto())) {
					listAux.add(producto);
				}
			}
		}
		listadoProductos = null;
		filteredListadoProductos = null;
		setFilteredListadoProductos(listAux);
		return "producto";
	}

	public List<Producto> getProductosXRubro(Integer rubro) {
		System.out.println("Metodo getProductosXRubro()");
		DAOProducto = new DAOProducto();
		List<Producto> lista = new ArrayList<Producto>();
		if (rubro == 0 && estadoProducto == 2) {
			lista = DAOProducto.getListadoProductos();
		}
		if (rubro == 0 && estadoProducto == 1) {
			lista = DAOProducto.getListadoProductosAct();
		}
		if (rubro == 0 && estadoProducto == 0) {
			lista = DAOProducto.getListadoProductosDesact();
		}
		if (rubro != 0 && estadoProducto == 2) {
			for (Producto producto : getListadoProductos()) {
				if (producto.getRubro().getId() == rubro) {
					lista.add(producto);
				}
			}
		}
		if (rubro != 0 && estadoProducto == 1) {
			List<Producto> listAux = new ArrayList<Producto>();
			listAux = DAOProducto.getListadoProductosAct();
			for (Producto producto : listAux) {
				if (producto.getRubro().getId() == rubro) {
					lista.add(producto);
				}
			}
		}
		if (rubro != 0 && estadoProducto == 0) {
			List<Producto> listAux = new ArrayList<Producto>();
			listAux = DAOProducto.getListadoProductosDesact();
			for (Producto producto : listAux) {
				if (producto.getRubro().getId() == rubro) {
					lista.add(producto);
				}
			}
		}
		filteredListadoProductos = null;
		setListaProductoRubro(lista);
		setFilteredListadoProductos(lista);
		return filteredListadoProductos;
	}

	public List<Producto> getProductosXEstado(Integer estado) {
		System.out.println("Metodo getProductosXEstado()");
		DAOProducto = new DAOProducto();
		filteredListadoProductos = null;
		List<Producto> lista = new ArrayList<Producto>();
		if (estado == 2) {
			lista = DAOProducto.getListadoProductos();
			filteredListadoProductos = lista;
			setEstadoProducto(2);
		} else if (estado == 0) {
			setEstadoProducto(0);
			lista = DAOProducto.getListadoProductosDesact();
			filteredListadoProductos = lista;
		} else {
			setEstadoProducto(1);
			lista = DAOProducto.getListadoProductosAct();
			filteredListadoProductos = lista;
		}
		return filteredListadoProductos;
	}

	public void onEdit(Producto producto2) {
		
		System.out.println("Metodo onEdit()");
		producto = new Producto();
		producto = producto2;
		precioCompraProd = true;
		stockProd = true;
		idRubroProducto = producto2.getRubro().getId();
	}

	public String activar(Producto producto) {
		System.out.println("Metodo activar()");
		FacesMessage msg = null;
		setCurrentDate(new Date());
		producto.setEnabled(true);
		producto.setFechaModificacion(getCurrentDate());
		producto.setUsuario3(usuario);
		int valor = DAOProducto.updateEstadoProducto(producto);
		checkStockMinimo();
		if (valor != 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto: "
					+ producto.getNombre() + " Activado correctamente.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			producto = null;
			getListadoProductos();
			return "producto";
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: El Producto: " + producto.getNombre()

					+ " no se ha podido Activar.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}
	}

	/**
	 * 
	 * @param client
	 * @return
	 */
	public String desactivar(Producto producto) {
		System.out.println("Metodo desactivar()");
		FacesMessage msg = null;
		setCurrentDate(new Date());
		producto.setEnabled(false);
		producto.setFechaBaja(getCurrentDate());
		producto.setUsuario2(usuario);
		int valor = DAOProducto.updateEstadoProducto(producto);
		checkStockMinimo();
		if (valor != 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto: "
					+ producto.getNombre() + " Desactivado correctamente.",
					null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			producto = null;
			getListadoProductos();
			getFilteredListadoProductos();
			return "producto";
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: El producto: " + producto.getNombre()
							+ " no se ha podido Desactivar.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}
	}

	public String cancelarProducto() {
		System.out.println("Metodo cancelarProducto()");
		producto = new Producto();
		precioCompraProd = false;
		stockProd = false;
		idRubroProducto = 0;
		return "producto";
	}

	public void checkStockMinimo() {
		System.out.println("Metodo checkStockMinimo()");
		DAOProducto daop = new DAOProducto();
		List<Producto> lista = new ArrayList<Producto>();
		List<Producto> listaAux = new ArrayList<Producto>();
		lista = daop.getListadoProductosAct();

		for (Producto pr : lista) {
			if (pr.getStock() < pr.getStockMinimo()) {
				listaAux.add(pr);
			}
		}

		pStockMinimo = listaAux;
		filteredpStockMinimo = pStockMinimo;
		if (pStockMinimo.size() > 0) {
			renderedStockMinimo = true;
		} else {
			renderedStockMinimo = false;
		}

	}

	public String goStockMinimo() {
		System.out.println("goStockMinimo()");
		return "stockMinimo";
	}


	/////////////////////////////////////////////////////CLIENTE///////////////////////////////////////////////////////

	private List<Cliente> listadoClientes;
	private List<Cliente> filteredListadoClientes;
	private Cliente cliente = new Cliente();
	private DAOcliente DAOCliente;
	private int estadoCliente = 2;
	private Listaprecio listaPrecioCliente;
	private HashMap<String, Integer> listasPrecioCliente;
	private Integer idListaPrecioCliente;
	private float saldoCliente;
	private boolean boolPlataforma;
	private String codigoPagos;
	private String ventasPinWeb;
	private String pinwebMercader444;
	private String sistemaReVirtual;
	private String telerecarga;
	private Cliente clienteParaFiltro;
	
	public String goCliente(){
		System.out.println("Metodo goCliente()");
		listadoClientes = new ArrayList<Cliente>();
		filteredListadoClientes= new ArrayList<Cliente>();
		cliente = new Cliente();
		DAOCliente = new DAOcliente();
		listadoClientes = DAOCliente.getListadoClientes();
		filteredListadoClientes = listadoClientes;
		estadoCliente = 2;
		listaPrecioCliente = new Listaprecio();
//		listasPrecioCliente = new LinkedHashMap<String, Integer>();
		idListaPrecioCliente = 0;
		saldoCliente = 0;
		boolPlataforma = false;
		codigoPagos = "";
		ventasPinWeb ="";
		pinwebMercader444 = "";
		sistemaReVirtual="";
		telerecarga="";
		return "cliente";
	}
	
	

	public Cliente getClienteParaFiltro() {
		return clienteParaFiltro;
	}

	public void setClienteParaFiltro(Cliente clienteParaFiltro) {
		this.clienteParaFiltro = clienteParaFiltro;
	}

	public boolean isBoolPlataforma() {
		return boolPlataforma;
	}

	public void setBoolPlataforma(boolean boolPlataforma) {
		this.boolPlataforma = boolPlataforma;
	}

	public String getCodigoPagos() {
		return codigoPagos;
	}

	public void setCodigoPagos(String codigoPagos) {
		this.codigoPagos = codigoPagos;
	}

	public String getVentasPinWeb() {
		return ventasPinWeb;
	}

	public void setVentasPinWeb(String ventasPinWeb) {
		this.ventasPinWeb = ventasPinWeb;
	}

	public String getPinwebMercader444() {
		return pinwebMercader444;
	}

	public void setPinwebMercader444(String pinwebMercader444) {
		this.pinwebMercader444 = pinwebMercader444;
	}

	public String getSistemaReVirtual() {
		return sistemaReVirtual;
	}

	public void setSistemaReVirtual(String sistemaReVirtual) {
		this.sistemaReVirtual = sistemaReVirtual;
	}

	public String getTelerecarga() {
		return telerecarga;
	}

	public void setTelerecarga(String telerecarga) {
		this.telerecarga = telerecarga;
	}

	public float getSaldoCliente() {
		return saldoCliente;
	}

	public void setSaldoCliente(float saldoCliente) {
		this.saldoCliente = saldoCliente;
	}

	public Integer getIdListaPrecioCliente() {
		return idListaPrecioCliente;
	}

	public void setIdListaPrecioCliente(Integer idListaPrecioCliente) {
		this.idListaPrecioCliente = idListaPrecioCliente;
	}

	public HashMap<String, Integer> getListasPrecioCliente() {
		DAOCliente = new DAOcliente();
		listasPrecioCliente = DAOCliente.getHashMapListas();
		return listasPrecioCliente;
	}

	public void setListasPrecioCliente(HashMap<String, Integer> listasPrecio) {
		this.listasPrecioCliente = listasPrecio;
	}

	public Listaprecio getListaPrecioCliente() {
		return listaPrecioCliente;
	}

	public void setListaPrecioCliente(Listaprecio listaPrecio) {
		this.listaPrecioCliente = listaPrecio;
	}

	public int getEstadoCliente() {
		return estadoCliente;
	}

	public void setEstadoCliente(int estado) {
		this.estadoCliente = estado;
	}

	public List<Cliente> getFilteredListadoClientes() {
		return filteredListadoClientes;
	}

	public void setFilteredListadoClientes(List<Cliente> filteredListadoClientes) {
		this.filteredListadoClientes = filteredListadoClientes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public DAOcliente getDAOCliente() {
		return DAOCliente;
	}

	public void setDAOCliente(DAOcliente dAO) {
		DAOCliente = dAO;
	}

	public List<Cliente> getListadoClientes() {
		DAOCliente = new DAOcliente();
		listadoClientes = DAOCliente.getListadoClientes();
		return listadoClientes;
	}

	public void setListadoClientes(List<Cliente> listadoClientes) {
		this.listadoClientes = listadoClientes;
	}

	public String onClienteView() {
		System.out.println("Metodo onClienteView()");
		cliente = new Cliente();
		listadoClientes = null;
		filteredListadoClientes = null;
		DAOCliente = new DAOcliente();
		List<Cliente> lista = new ArrayList<Cliente>();
		lista = DAOCliente.getListadoClientes();
		setSaldoCliente(0);
		setListadoClientes(lista);
		setFilteredListadoClientes(lista);
		codigoPagos = null;
		ventasPinWeb = null;
		sistemaReVirtual = null;
		telerecarga = null;
		boolPlataforma = false;
		return "cliente";
	}

	/**
	 * 
	 * @param estado
	 * @return
	 */
	public List<Cliente> getClientesXEstado(Integer estado) {
		System.out.println("Metodo getClienteXEstado()");
		DAOCliente = new DAOcliente();
		filteredListadoClientes = null;
		List<Cliente> lista = new ArrayList<Cliente>();
		if (estado == 2) {
			lista = DAOCliente.getListadoClientes();
			setFilteredListadoClientes(lista);
			setEstadoCliente(2);
		} else if (estado == 0) {
			setEstadoCliente(0);
			lista = DAOCliente.getListadoClientesDesact();
			setFilteredListadoClientes(lista);
		} else {
			setEstadoCliente(1);
			lista = DAOCliente.getListadoClientesAct();
			setFilteredListadoClientes(lista);
		}
		return filteredListadoClientes;
	}

	/**
	 * 
	 * @param client
	 */
	public void onEditCliente(Cliente client) {
		System.out.println("Metodo onEditCliente()");
		DAOCliente = new DAOcliente();
		cliente = new Cliente();
		cliente = client;
		codigoPagos = null;
		ventasPinWeb = null;
		sistemaReVirtual = null;
		telerecarga = null;
		boolPlataforma = false;
		List<ClientePlataforma> listClientePlataform = new ArrayList<ClientePlataforma>();
		listClientePlataform = DAOCliente.getPlataformasCliente(client);
		for (ClientePlataforma clientePlataforma : listClientePlataform) {
			if(clientePlataforma.getPlataforma().equals("codigo_pagos")){
				codigoPagos = clientePlataforma.getUsuario();
			}
			if(clientePlataforma.getPlataforma().equals("venta_pinweb")){
				ventasPinWeb = clientePlataforma.getUsuario();
			}
			if(clientePlataforma.getPlataforma().equals("sistema_revirtual")){
				sistemaReVirtual = clientePlataforma.getUsuario();
			}
			if(clientePlataforma.getPlataforma().equals("telerecarga_reporte")){
				telerecarga = clientePlataforma.getUsuario();
			}
		}
		idListaPrecioCliente = client.getListaprecio().getId();
		if (cliente.getSaldo() != 0) {
			setSaldoCliente(cliente.getSaldo());
		}
	}

	/**
	 * 
	 * @return
	 */
	public void guardarCliente() {
		System.out.println("Metodo guardarCliente()");
		FacesMessage msg = null;
		ClientePlataforma clientePlataform = new ClientePlataforma();
		Usuario usuarioCliente = new Usuario();
		DAOcc = new DAOcuentascorriente();
		cuentaDAO = new DAOCuentas();
		DAOCliente = new DAOcliente();
		DAOuser = new DAOusuario();
		Listaprecio listaPreCli = new Listaprecio();
		Integer id = cliente.getId();
		if(!cliente.getApellido().isEmpty() && !cliente.getDireccion().isEmpty() && !cliente.getMail().isEmpty()
				&& !cliente.getNombre().isEmpty() && !cliente.getTelefono().isEmpty() 
				&& idListaPrecioCliente != 0 && idListaPrecioCliente != null){
			
			if (id != 0) {
				setCurrentDate(new Date());
				cliente.setFechaModificacion(getCurrentDate());
				cliente.setUsuario3(usuario);
				cliente.setTipo("c");
				listaPreCli.setId(getIdListaPrecioCliente());
				cliente.setListaprecio(listaPreCli);
				int valor = 0;
				boolean insertar;
				Role role = new Role();
				role.setId(2);
				usuarioCliente = DAOuser.getUsuarioPorCliente(cliente);
				if(usuarioCliente.getId() != 0){
					insertar = false;
				}else{
					insertar = true;
				}
				if(insertar){
					usuarioCliente.setEnabled(true);
					usuarioCliente.setPassword("WdUjxKFLegTkGNtfAJLVzAEZvIr4i5xSpct/V6SF9lkTA4gxrhsmONnCTOw3ic+i3LGYL58gWxOJnzZtmg1Ypw==");
					usuarioCliente.setFechaRegistro(getCurrentDate());
					usuarioCliente.setRole(role);
				}
				usuarioCliente.setNombre(cliente.getApellido());
				usuarioCliente.setUsername(cliente.getMail());
				usuarioCliente.setCliente(cliente);
				
				valor = DAOCliente.updateCliente(cliente);

				if (cliente.getSaldo() != getSaldoCliente()) {
//					cuentasCorriente.setCliente(cliente);
//					cuentasCorriente.setFechaAlta(getCurrentDate());
//					cuentasCorriente.setUsuario(usuario);
//					cuentasCorriente.setIdMovimiento(1);
//					cuentasCorriente.setMonto(cliente.getSaldo());
//					cuentasCorriente
//							.setTipoMovimiento("Modificacion Cuenta Corriente");
//					DAOcc.insertCuentaCorriente(cuentasCorriente);
					
					Cuenta cuentas = new Cuenta();
					cuentas.setCliente(cliente);
					cuentas.setDetalle("Modificación de Cuenta Corriente");
					cuentas.setFecha(new Date());
					if(cliente.getSaldo() > getSaldoCliente()){
						float dDebe = cliente.getSaldo() - getSaldoCliente();
						float debe = dDebe;
						cuentas.setDebe(debe);
						cuentas.setMonto(debe);
					}else{
						float dHaber = getSaldoCliente() - cliente.getSaldo();
						float haber = dHaber;
						cuentas.setHaber(haber);
						cuentas.setMonto(haber);
					}
					float sldo = cliente.getSaldo();
					cuentas.setSaldo(sldo);
					cuentas.setUsuario(usuario);
					cuentaDAO.insertar(cuentas);
				}				
				if(boolPlataforma){
					if(!codigoPagos.isEmpty() || !ventasPinWeb.isEmpty() 
							|| !sistemaReVirtual.isEmpty() || !telerecarga.isEmpty() || !pinwebMercader444.isEmpty()){
						DAOCliente.deletePlataforma(cliente);
						if(!codigoPagos.isEmpty()){
							clientePlataform = new ClientePlataforma();
							clientePlataform.setCliente(cliente);
							clientePlataform.setPlataforma("codigo_pagos");
							clientePlataform.setUsuario(codigoPagos);
							DAOCliente.insertClientePlataforma(clientePlataform);
						}
						if(!ventasPinWeb.isEmpty()){
							clientePlataform = new ClientePlataforma();
							clientePlataform.setCliente(cliente);
							clientePlataform.setPlataforma("venta_pinweb");
							clientePlataform.setUsuario(ventasPinWeb);
							DAOCliente.insertClientePlataforma(clientePlataform);
						}
						if(!sistemaReVirtual.isEmpty()){
							clientePlataform = new ClientePlataforma();
							clientePlataform.setCliente(cliente);
							clientePlataform.setPlataforma("sistema_revirtual");
							clientePlataform.setUsuario(sistemaReVirtual);
							DAOCliente.insertClientePlataforma(clientePlataform);
						}
						if(!telerecarga.isEmpty()){
							clientePlataform = new ClientePlataforma();
							clientePlataform.setCliente(cliente);
							clientePlataform.setPlataforma("telerecarga_reporte");
							clientePlataform.setUsuario(telerecarga);
							DAOCliente.insertClientePlataforma(clientePlataform);
						}
						if(!pinwebMercader444.isEmpty()){
							clientePlataform = new ClientePlataforma();
							clientePlataform.setCliente(cliente);
							clientePlataform.setPlataforma("pinweb_mercader444");
							clientePlataform.setUsuario(pinwebMercader444);
							DAOCliente.insertClientePlataforma(clientePlataform);
						}
					}
				}

				if (valor != 0) {
					int valorU = 0;
					if(insertar){
						valorU = DAOuser.agregarUsser(usuarioCliente);
					}else{
						valorU = DAOuser.updateUsuario(usuarioCliente);
					}
					String msgUsuario = "";
					if(valorU == 0){
						msgUsuario = "El usuario no se pudo crear, algun campo es incorrecto";
					}
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente: "
							+ cliente.getNombre() + " " + cliente.getApellido()
							+ " Guardado correctamente. " + msgUsuario, null);
					cliente = new Cliente();
					onClienteView();
				} else {
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Error: El Cliente: " + cliente.getNombre() + " "
									+ cliente.getApellido()
									+ " no se pudo Guardar.", null);
					cliente = new Cliente();
					onClienteView();
				}
			} else {
				setCurrentDate(new Date());
				cliente.setFechaAlta(getCurrentDate());
				cliente.setUsuario1(usuario);
				cliente.setEnabled(true);
				cliente.setTipo("c");
				listaPreCli.setId(getIdListaPrecioCliente());
				cliente.setListaprecio(listaPreCli);
				int valor = 0;
				valor = DAOCliente.insertCliente(cliente);
				String pass = "WdUjxKFLegTkGNtfAJLVzAEZvIr4i5xSpct/V6SF9lkTA4gxrhsmONnCTOw3ic+i3LGYL58gWxOJnzZtmg1Ypw==";
				Role role = new Role();
				role.setId(2);
				usuarioCliente.setNombre(cliente.getApellido());
				usuarioCliente.setEnabled(true);
				usuarioCliente.setUsername(cliente.getMail());
				usuarioCliente.setPassword(pass);
				usuarioCliente.setFechaRegistro(getCurrentDate());
				usuarioCliente.setRole(role);
				usuarioCliente.setCliente(cliente);

				if (cliente.getSaldo() != 0) {
//					cuentasCorriente.setFechaAlta(getCurrentDate());
//					cuentasCorriente.setUsuario(usuario);
//					cuentasCorriente.setCliente(cliente);
//					cuentasCorriente.setIdMovimiento(1);
//					cuentasCorriente.setTipoMovimiento("Inicializacion Cuenta Corriente");
//					cuentasCorriente.setMonto(cliente.getSaldo());
//					DAOcc.insertCuentaCorriente(cuentasCorriente);
					
					Cuenta cuentas = new Cuenta();
					cuentas.setCliente(cliente);
					cuentas.setDetalle("Inicialización de Cuenta Corriente");
					cuentas.setFecha(new Date());
					float sldo = cliente.getSaldo();
					cuentas.setDebe(sldo);
					cuentas.setMonto(sldo);
					cuentas.setSaldo(sldo);
					cuentas.setUsuario(usuario);
					cuentaDAO.insertar(cuentas);
				}
				if(boolPlataforma){
					if(!codigoPagos.isEmpty()){
						clientePlataform = new ClientePlataforma();
						clientePlataform.setCliente(cliente);
						clientePlataform.setPlataforma("codigo_pagos");
						clientePlataform.setUsuario(codigoPagos);
						DAOCliente.insertClientePlataforma(clientePlataform);
					}
					if(!ventasPinWeb.isEmpty()){
						clientePlataform = new ClientePlataforma();
						clientePlataform.setCliente(cliente);
						clientePlataform.setPlataforma("venta_pinweb");
						clientePlataform.setUsuario(ventasPinWeb);
						DAOCliente.insertClientePlataforma(clientePlataform);
					}
					if(!sistemaReVirtual.isEmpty()){
						clientePlataform = new ClientePlataforma();
						clientePlataform.setCliente(cliente);
						clientePlataform.setPlataforma("sistema_revirtual");
						clientePlataform.setUsuario(sistemaReVirtual);
						DAOCliente.insertClientePlataforma(clientePlataform);
					}
					if(!telerecarga.isEmpty()){
						clientePlataform = new ClientePlataforma();
						clientePlataform.setCliente(cliente);
						clientePlataform.setPlataforma("telerecarga_reporte");
						clientePlataform.setUsuario(telerecarga);
						DAOCliente.insertClientePlataforma(clientePlataform);
					}
					if(!pinwebMercader444.isEmpty()){
						clientePlataform = new ClientePlataforma();
						clientePlataform.setCliente(cliente);
						clientePlataform.setPlataforma("pinweb_mercader444");
						clientePlataform.setUsuario(pinwebMercader444);
						DAOCliente.insertClientePlataforma(clientePlataform);
					}
				}
				if (valor != 0) {
					int valorU = DAOuser.agregarUsser(usuarioCliente);
					String msgUsuario = "";
					if(valorU == 0){
						msgUsuario = "El usuario no se pudo crear, algun campo es incorrecto";
					}
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente: "
							+ cliente.getNombre() + " " + cliente.getApellido()
							+ " Guardado correctamente. " + msgUsuario, null);
					cliente = new Cliente();
					onClienteView();
					
				} else {
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Error: El Cliente: " + cliente.getNombre() + " "
									+ cliente.getApellido()
									+ " no se ha podido Guardar.", null);
					cliente = new Cliente();
					onClienteView();
				}
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: El Cliente: " + cliente.getNombre() + " "
							+ cliente.getApellido()
							+ " no se ha podido Guardar.", null);
			cliente = new Cliente();
			onClienteView();
		}
		
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * 
	 * @param client
	 * @return
	 */
	public String activar(Cliente client) {
		System.out.println("Metodo activar()");
		FacesMessage msg = null;
		setCurrentDate(new Date());
		client.setEnabled(true);
		client.setFechaModificacion(getCurrentDate());
		client.setUsuario3(usuario);
		int valor = DAOCliente.updateEstadoCliente(client);
		if (valor == 1) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente: "
					+ client.getNombre() + " " + client.getApellido()
					+ " Activado correctamente.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			client = new Cliente();
			getListadoClientes();
			return "cliente";
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: El Cliente: " + client.getNombre() + " "
							+ client.getApellido()
							+ " no se ha podido Activar.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}
	}

	/**
	 * 
	 * @param client
	 * @return
	 */
	public String desactivar(Cliente client) {
		System.out.println("Metodo desactivar()");
		FacesMessage msg = null;
		setCurrentDate(new Date());
		client.setEnabled(false);
		client.setFechaBaja(getCurrentDate());
		client.setUsuario2(usuario);
		int valor = DAOCliente.updateEstadoCliente(client);
		if (valor == 1) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente: "
					+ client.getNombre() + " " + client.getApellido()
					+ " Desactivado correctamente.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			client = new Cliente();
			getListadoClientes();
			return "cliente";
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: El Cliente: " + client.getNombre() + " "
							+ client.getApellido()
							+ " no se ha podido Desactivar.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}
	}

	public String onViewCC(Cliente client) {
		System.out.println("Metodo onViewCC()");
		clienteParaFiltro = client;
		DAOcc = new DAOcuentascorriente();
		FacesMessage msg = null;
		List<Cuentascorriente> lista = DAOcc.getListaCCCliente(client);
		Integer cantidad = 0;
		float monto = 0;
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		volverVistaCC = "cuentacorrienteCliente";
		if (lista.isEmpty()) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Error: El Cliente: " + client.getNombre() + " "
							+ client.getApellido()
							+ " no posee cuenta corriente.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		} else {
			for (Cuentascorriente cuentascorriente : lista) {
				cantidad++;
				if(cuentascorriente.getTipoMovimiento().equals("VENTA") || 
						cuentascorriente.getTipoMovimiento().equals("Inicializacion Cuenta Corriente")){
					monto = cuentascorriente.getMonto() + monto;
				}else{
					monto = monto - cuentascorriente.getMonto();
				}
				
			}			
			String valor = formatear.format(client.getSaldo());
			setClienteCC(client);
			setMontoTotalCC(client.getSaldo());
			setMontoTotalCCString(valor);
			setCantidadCC(cantidad);
			setListaCuentasCorrienteCliente(lista);
			return "cuentacorrienteCliente";
		}
	}

	public String cancelar() {
		System.out.println("Metodo cancelar()");
		cliente = new Cliente();
		idListaPrecioCliente = 0;
		saldoCliente = 0;
		codigoPagos = null;
		ventasPinWeb = null;
		sistemaReVirtual = null;
		telerecarga = null;
		boolPlataforma = false;
		return "cliente";
	}

	// ////////////////////////////////////////////////////LISTAPRECIOS//////////////////////////////////////////
	private HashMap<String, Integer> listRubroLP;
	private int idRubroLP;
	private List<Listaprecio> listadoListaPrecio;
	private List<Listaprecio> FilteredlistadoListaPrecio;
	private Listaprecio lp = new Listaprecio();
	private DAOListaPrecio DAOLP;
	private int estadoLP = 2;
	private List<Producto> productosLista = new ArrayList<Producto>();
	private List<Producto> productosSeleted = new ArrayList<Producto>();
	private boolean porcentaje;
	private boolean descuento;
	private ListapreciosProducto LPP = new ListapreciosProducto();
	private List<ListapreciosProducto> ListLPP = new ArrayList<ListapreciosProducto>();
	private boolean chSelect;
	
	
	public String goListaPrecios(){
		System.out.println("Metodo goListaPrecios()");
		listRubroLP = new LinkedHashMap<String, Integer>();
		idRubroLP =0;
		listadoListaPrecio = new ArrayList<Listaprecio>();
		FilteredlistadoListaPrecio = new ArrayList<Listaprecio>();
		lp = null;
		lp = new Listaprecio();
		DAOLP = new DAOListaPrecio();
		listadoListaPrecio = DAOLP.getListadoListaprecio();
		FilteredlistadoListaPrecio = listadoListaPrecio;
		estadoLP = 2;
		productosLista = new ArrayList<Producto>();
		productosSeleted = new ArrayList<Producto>();
		porcentaje = false;
		descuento = false;
		LPP = new ListapreciosProducto();
		ListLPP = new ArrayList<ListapreciosProducto>();
		chSelect = false;
		return "listaPrecios";
	}

	public int getIdRubroLP() {
		return idRubroLP;
	}

	public void setIdRubroLP(int idRubroLP) {
		this.idRubroLP = idRubroLP;
	}

	public HashMap<String, Integer> getListRubroLP() {
		DAOLP = new DAOListaPrecio();
		listRubroLP = DAOLP.getHashMapRubros();
		return listRubroLP;
	}

	public void setListRubroLP(HashMap<String, Integer> listRubroLP) {
		this.listRubroLP = listRubroLP;
	}

	public boolean isChSelect() {
		return chSelect;
	}

	public void setChSelect(boolean chSelect) {
		this.chSelect = chSelect;
	}

	public List<ListapreciosProducto> getListLPP() {
		DAOLP = new DAOListaPrecio();
		ListLPP = DAOLP.getListadoLPP(lp);
		return ListLPP;
	}

	public void setListLPP(List<ListapreciosProducto> listLPP) {
		ListLPP = listLPP;
	}

	public ListapreciosProducto getLPP() {
		return LPP;
	}

	public void setLPP(ListapreciosProducto lPP) {
		LPP = lPP;
	}

	public boolean isPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(boolean porcentaje) {
		this.porcentaje = porcentaje;
	}

	public boolean isDescuento() {
		return descuento;
	}

	public void setDescuento(boolean descuento) {
		this.descuento = descuento;
	}

	public List<Producto> getProductosSeleted() {
		return productosSeleted;
	}

	public void setProductosSeleted(List<Producto> productosSeleted) {
		this.productosSeleted = productosSeleted;
	}

	public List<Producto> getProductosLista() {
		return productosLista;
	}

	public void setProductosLista(List<Producto> productosLista) {
		this.productosLista = productosLista;
	}

	public int getEstadoLP() {
		return estadoLP;
	}

	public void setEstadoLP(int estado) {
		this.estadoLP = estado;
	}

	public List<Listaprecio> getListadoListaPrecio() {
		DAOLP = new DAOListaPrecio();
		listadoListaPrecio = DAOLP.getListadoListaprecio();
		return listadoListaPrecio;
	}

	public void setListadoListaPrecio(List<Listaprecio> listadoListaPrecio) {
		this.listadoListaPrecio = listadoListaPrecio;
	}

	public List<Listaprecio> getFilteredlistadoListaPrecio() {
		return FilteredlistadoListaPrecio;
	}

	public void setFilteredlistadoListaPrecio(
			List<Listaprecio> filteredlistadoListaPrecio) {
		FilteredlistadoListaPrecio = filteredlistadoListaPrecio;
	}

	public Listaprecio getLp() {
		return lp;
	}

	public void setLp(Listaprecio lp) {
		this.lp = lp;
	}

	public DAOListaPrecio getDAO() {
		return DAOLP;
	}

	public void setDAO(DAOListaPrecio dAO) {
		DAOLP = dAO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public void onChangeRubroLP(){
		chSelect = false;
		DAOLP = new DAOListaPrecio();
		productosLista = DAOLP.getListadoProductosAct(lp, idRubroLP);
	}
	

	public String onListaPrecioView() {
		System.out.println("Metodo onListaPreciosView()");
		listadoListaPrecio = null;
		FilteredlistadoListaPrecio = null;
		DAOLP = new DAOListaPrecio();
		List<Listaprecio> lista = new ArrayList<Listaprecio>();
		lista = DAOLP.getListadoListaprecio();
		setListadoListaPrecio(lista);
		setFilteredlistadoListaPrecio(lista);
		return "listaprecio";
	}

	public void guardarLP() {
		System.out.println("Metodo guardarLP()");
		FacesMessage msg = null;
		if(!lp.getNombre().isEmpty() && lp.getNombre() != null){
			setCurrentDate(new Date());
			lp.setEnabled(true);
			lp.setFechaAlta(getCurrentDate());
			lp.setFechaModificacion(getCurrentDate());
			lp.setUsuario1(usuario);
			lp.setUsuario3(usuario);
			DAOLP = new DAOListaPrecio();
			int valor = DAOLP.insertListaprecio(lp);
			if (valor != 0) {
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Lista de Precios: " + lp.getNombre()
								+ " Guardado correctamente.", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				lp = new Listaprecio();
				onListaPrecioView();
			} else {
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Error: La lista de precios: " + lp.getNombre()
								+ " no se ha podido Guardar.", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				lp = new Listaprecio();
				onListaPrecioView();
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: Se requiere un nombre de lista", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			lp = new Listaprecio();
			onListaPrecioView();
		}
		
	}

	public List<Listaprecio> getLPXEstado(int estado) {
		System.out.println("Metodo getLPCEstado()");
		DAOLP = new DAOListaPrecio();
		FilteredlistadoListaPrecio = null;
		List<Listaprecio> lista = new ArrayList<Listaprecio>();
		if (estado == 2) {
			lista = DAOLP.getListadoListaprecio();
			FilteredlistadoListaPrecio = lista;
			setEstadoLP(2);
		} else if (estado == 0) {
			setEstadoLP(0);
			lista = DAOLP.getListadoListaprecioDesact();
			FilteredlistadoListaPrecio = lista;
		} else {
			setEstadoLP(1);
			lista = DAOLP.getListadoListaprecioAct();
			FilteredlistadoListaPrecio = lista;
		}
		return FilteredlistadoListaPrecio;
	}

	public void onEditListaprecio(Listaprecio lp2) {
		System.out.println("Metodo onEditListaprecio()");
		lp = new Listaprecio();
		lp = lp2;
	}

	public String activar(Listaprecio lp) {
		System.out.println("Metodo activar()");
		FacesMessage msg = null;
		setCurrentDate(new Date());
		lp.setEnabled(true);
		lp.setFechaModificacion(getCurrentDate());
		lp.setUsuario3(usuario);
		int valor = DAOLP.updateEstadoListaprecio(lp);
		if (valor != 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Lista de precios: " + lp.getNombre()
							+ " Activado correctamente.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			lp = null;
			onListaPrecioView();
			return "listaPrecios";
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: La lista de precios: " + lp.getNombre()
							+ " no se ha podido Activar.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}
	}

	public void desactivar(Listaprecio lp) {
		System.out.println("Metodo desactivar()");
		FacesMessage msg = null;
		setCurrentDate(new Date());
		lp.setEnabled(false);
		lp.setFechaBaja(getCurrentDate());
		lp.setUsuario2(usuario);
		if(lp.getListadoClientes().isEmpty()){
			int valor = DAOLP.updateEstadoListaprecio(lp);
			if (valor == 1) {
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Lista de precios: " + lp.getNombre()
								+ " Desactivado correctamente.", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				lp = new Listaprecio();
				onListaPrecioView();
			}
			if (valor == 0) {
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Error: La lista de precios: " + lp.getNombre()
								+ " no se ha podido Desactivar.", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				lp = new Listaprecio();
				onListaPrecioView();
			}
		}else{
			msg = new FacesMessage(
					FacesMessage.SEVERITY_FATAL,
					"La lista de precios no se puede desactivar tiene clientes asociados",
					null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			lp = new Listaprecio();
			onListaPrecioView();
		}

	}

	public String cancelarLP() {
		System.out.println("Metodo cancelarLP()");
		lp = new Listaprecio();
		return "listaprecio";
	}

	public String agregarProducto(Listaprecio lp2) {
		System.out.println("Metodo agregarProducto()");
		lp = new Listaprecio();
		lp = lp2;
		productosSeleted = new ArrayList<Producto>();
		return "productoLista";
	}

	public void checkProducto(Producto PL) {
		System.out.println("Metodo checkProducto()");
		boolean encontrado = false;
		List<Producto> listAux = new ArrayList<Producto>();
		if (productosSeleted.isEmpty()) {
			productosSeleted.add(PL);
			listAux.add(PL);
		} else {
			for (Producto pro : productosSeleted) {
				if (pro.getId() != PL.getId()) {
					listAux.add(pro);
				} else {
					encontrado = true;
				}
			}

			if (!encontrado) {
				listAux.add(PL);
			}
			productosSeleted = new ArrayList<Producto>();
			productosSeleted = listAux;
		}

	}

	public void checkPorcentaje() {
		if (porcentaje) {
			descuento = false;
		}
	}

	public void checkDescuento() {
		if (descuento) {
			porcentaje = false;
		}
	}

	public void agregarProductoLista() {
		
		System.out.println("Metodo agregarProductoLista()");
		
		DAOLP = new DAOListaPrecio();
		FacesMessage msg = null;
		
		
		if ((LPP.getDescuento() == 0) & (LPP.getPorcentaje() == 0)) {
			productosSeleted = new ArrayList<Producto>();
			chSelect = false;
			msg = new FacesMessage(
					FacesMessage.SEVERITY_FATAL,
					"Se debe ingresar un descuento o porcentaje para agregar un producto a la lista.",
					null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			boolean condicional = true;
			if ((LPP.getDescuento() != 0)){
				for (Producto prod : productosSeleted) {
					if (prod.getPrecioNominal() == 0){
						condicional = false;
					}
				}
				if (condicional){
					
					for (Producto prod : productosSeleted) {
						if (prod.getPrecioCompra() != 0){
							ListapreciosProducto aux = new ListapreciosProducto();
							aux.setListaprecio(lp);
							aux.setProducto(prod);
							aux.setDescuento(LPP.getDescuento());
							aux.setPorcentaje(LPP.getPorcentaje());
							DAOLP.insertLPP(aux);
							productosSeleted = new ArrayList<Producto>();
							chSelect = false;
						}
					}
				}else{
					msg = new FacesMessage(
							FacesMessage.SEVERITY_FATAL,
							"Uno o mas productos no posee precio nominal",
							null);
					FacesContext.getCurrentInstance().addMessage(null, msg);
					productosSeleted = new ArrayList<Producto>();
					chSelect = false;
				}		

			}else{
				
				for (Producto prod : productosSeleted) {
					if (prod.getPrecioCompra() != 0){
						ListapreciosProducto aux = new ListapreciosProducto();
						aux.setListaprecio(lp);
						aux.setProducto(prod);
						aux.setDescuento(LPP.getDescuento());
						aux.setPorcentaje(LPP.getPorcentaje());
						DAOLP.insertLPP(aux);
						productosSeleted = new ArrayList<Producto>();
						chSelect = false;
					}
				}
			}
			
		}
		LPP = new ListapreciosProducto();
		descuento = false;
		porcentaje = false;
		chSelect = false;
		DAOLP = new DAOListaPrecio();
		productosLista = DAOLP.getListadoProductosAct(lp, idRubroLP);
		
	}

	public void deleteLPP(ListapreciosProducto lpp) {
		System.out.println("Metodo deleteLPP()");
		DAOLP = new DAOListaPrecio();
		DAOLP.deleteListapreciosProducto(lpp);
	}

	///////////////////////////////////////////////CUENTAS CORRIENTES/////////////////////////////////////////////////////////

	private Cuentascorriente cuentasCorriente;
	private List<Cuentascorriente> listaCuentasCorrienteCliente;
	private List<Cuentascorriente> filteredListadoCCCliente;
	private List<Cuentascorriente> listaCuentasCorrienteProveedor;
	private List<Cuentascorriente> filteredListadoCCProveedor;
	private List<Cuentascorriente> listaCuentasCorrienteIJM;
	private List<Cuentascorriente> filteredListadoCCIJM;
	private DAOcuentascorriente DAOcc;
	private Integer cantidadCC;
	private float montoTotalCC;
	private String montoTotalCCString;
	private Cliente clienteCC;
	private Cliente proveedorCC;
	private CompraCargavirtual compraCargaVirtualCuCo;
	private boolean ventaCuCo;
	private boolean compraCuCo;
	private boolean pagoCuCo;
	private boolean notaCredCuCo;
	private boolean compraCVCuCo;
	private String ventaCCNombre;
	private String ventaCCApellido;
	private String ventaCCDireccion;
	private List<VentasProducto> listVentasProductosCC;
	private String ventaCCNro;
	private String ventaCCFecha;
	private String ventaCCMonto;
	private String ventaCCEstado;
	private String pagoCCApellido1;
	private String pagoCCApellido2;
	private String pagoCCFecha;
	private String pagoCCNro;
	private String pagoCCMonto;
	private String pagoCCDescripcion;
	private String compraCCApellido;
	private String compraCCNombre;
	private String compraCCDireccion;
	private List<DetalleCompra> listCompraDetalleCC;
	private String compraCCFecha;
	private String compraCCNro;
	private String compraCCMonto;
	private String volverVistaCC;
	private String notaCredCCNro;
	private String notaCredCCFecha;
	private String notaCredCCApellido;
	private String notaCredCCNombre;
	private String notaCredCCDireccion;
	private List<VentasProducto> listNotaCredCCDetVent;
	private String notaCredCCNroVenta;
	private String notaCredCCFechaVenta;
	private String notaCredCCMontoVenta;
	private Date inicioCCIJM;
	private Date finalCCIJM;
	private Date inicioCCP;
	private Date finalCCP;
	private Date inicioCCC;
	private Date finalCCC;

	public CompraCargavirtual getCompraCargaVirtualCuCo() {
		return compraCargaVirtualCuCo;
	}

	public void setCompraCargaVirtualCuCo(CompraCargavirtual compraCargaVirtualCuCo) {
		this.compraCargaVirtualCuCo = compraCargaVirtualCuCo;
	}

	public boolean isCompraCVCuCo() {
		return compraCVCuCo;
	}

	public void setCompraCVCuCo(boolean compraCVCuCo) {
		this.compraCVCuCo = compraCVCuCo;
	}

	public String getPagoCCDescripcion() {
		return pagoCCDescripcion;
	}

	public void setPagoCCDescripcion(String pagoCCDescripcion) {
		this.pagoCCDescripcion = pagoCCDescripcion;
	}

	public Date getInicioCCC() {
		return inicioCCC;
	}

	public void setInicioCCC(Date inicioCCC) {
		this.inicioCCC = inicioCCC;
	}

	public Date getFinalCCC() {
		return finalCCC;
	}

	public void setFinalCCC(Date finalCCC) {
		this.finalCCC = finalCCC;
	}

	public Date getInicioCCP() {
		return inicioCCP;
	}

	public void setInicioCCP(Date inicioCCP) {
		this.inicioCCP = inicioCCP;
	}

	public Date getFinalCCP() {
		return finalCCP;
	}

	public void setFinalCCP(Date finalCCP) {
		this.finalCCP = finalCCP;
	}

	public Date getInicioCCIJM() {
		return inicioCCIJM;
	}

	public void setInicioCCIJM(Date inicioCCIJM) {
		this.inicioCCIJM = inicioCCIJM;
	}

	public Date getFinalCCIJM() {
		return finalCCIJM;
	}

	public void setFinalCCIJM(Date finalCCIJM) {
		this.finalCCIJM = finalCCIJM;
	}

	public String getVentaCCEstado() {
		return ventaCCEstado;
	}

	public void setVentaCCEstado(String ventaCCEstado) {
		this.ventaCCEstado = ventaCCEstado;
	}

	public String getNotaCredCCNro() {
		return notaCredCCNro;
	}

	public void setNotaCredCCNro(String notaCredCCNro) {
		this.notaCredCCNro = notaCredCCNro;
	}

	public String getNotaCredCCFecha() {
		return notaCredCCFecha;
	}

	public void setNotaCredCCFecha(String notaCredCCFecha) {
		this.notaCredCCFecha = notaCredCCFecha;
	}

	public String getNotaCredCCApellido() {
		return notaCredCCApellido;
	}

	public void setNotaCredCCApellido(String notaCredCCApellido) {
		this.notaCredCCApellido = notaCredCCApellido;
	}

	public String getNotaCredCCNombre() {
		return notaCredCCNombre;
	}

	public void setNotaCredCCNombre(String notaCredCCNombre) {
		this.notaCredCCNombre = notaCredCCNombre;
	}

	public String getNotaCredCCDireccion() {
		return notaCredCCDireccion;
	}

	public void setNotaCredCCDireccion(String notaCredCCDireccion) {
		this.notaCredCCDireccion = notaCredCCDireccion;
	}

	public List<VentasProducto> getListNotaCredCCDetVent() {
		return listNotaCredCCDetVent;
	}

	public void setListNotaCredCCDetVent(List<VentasProducto> listNotaCredCCDetVent) {
		this.listNotaCredCCDetVent = listNotaCredCCDetVent;
	}

	public String getNotaCredCCNroVenta() {
		return notaCredCCNroVenta;
	}

	public void setNotaCredCCNroVenta(String notaCredCCNroVenta) {
		this.notaCredCCNroVenta = notaCredCCNroVenta;
	}

	public String getNotaCredCCFechaVenta() {
		return notaCredCCFechaVenta;
	}

	public void setNotaCredCCFechaVenta(String notaCredCCFechaVenta) {
		this.notaCredCCFechaVenta = notaCredCCFechaVenta;
	}

	public String getNotaCredCCMontoVenta() {
		return notaCredCCMontoVenta;
	}

	public void setNotaCredCCMontoVenta(String notaCredCCMontoVenta) {
		this.notaCredCCMontoVenta = notaCredCCMontoVenta;
	}

	public boolean isNotaCredCuCo() {
		return notaCredCuCo;
	}

	public void setNotaCredCuCo(boolean notaCredCuCo) {
		this.notaCredCuCo = notaCredCuCo;
	}

	public String getVolverVistaCC() {
		return volverVistaCC;
	}

	public void setVolverVistaCC(String volverVistaCC) {
		this.volverVistaCC = volverVistaCC;
	}

	public String getVentaCCNombre() {
		return ventaCCNombre;
	}

	public void setVentaCCNombre(String ventaCCNombre) {
		this.ventaCCNombre = ventaCCNombre;
	}

	public String getVentaCCApellido() {
		return ventaCCApellido;
	}

	public void setVentaCCApellido(String ventaCCApellido) {
		this.ventaCCApellido = ventaCCApellido;
	}

	public String getVentaCCDireccion() {
		return ventaCCDireccion;
	}

	public void setVentaCCDireccion(String ventaCCDireccion) {
		this.ventaCCDireccion = ventaCCDireccion;
	}

	public List<VentasProducto> getListVentasProductosCC() {
		return listVentasProductosCC;
	}

	public void setListVentasProductosCC(List<VentasProducto> listVentasProductosCC) {
		this.listVentasProductosCC = listVentasProductosCC;
	}

	public String getVentaCCNro() {
		return ventaCCNro;
	}

	public void setVentaCCNro(String ventaCCNro) {
		this.ventaCCNro = ventaCCNro;
	}

	public String getVentaCCFecha() {
		return ventaCCFecha;
	}

	public void setVentaCCFecha(String ventaCCFecha) {
		this.ventaCCFecha = ventaCCFecha;
	}

	public String getVentaCCMonto() {
		return ventaCCMonto;
	}

	public void setVentaCCMonto(String ventaCCMonto) {
		this.ventaCCMonto = ventaCCMonto;
	}

	public String getPagoCCApellido1() {
		return pagoCCApellido1;
	}

	public void setPagoCCApellido1(String pagoCCApellido1) {
		this.pagoCCApellido1 = pagoCCApellido1;
	}

	public String getPagoCCApellido2() {
		return pagoCCApellido2;
	}

	public void setPagoCCApellido2(String pagoCCApellido2) {
		this.pagoCCApellido2 = pagoCCApellido2;
	}

	public String getPagoCCFecha() {
		return pagoCCFecha;
	}

	public void setPagoCCFecha(String pagoCCFecha) {
		this.pagoCCFecha = pagoCCFecha;
	}

	public String getPagoCCNro() {
		return pagoCCNro;
	}

	public void setPagoCCNro(String pagoCCNro) {
		this.pagoCCNro = pagoCCNro;
	}

	public String getPagoCCMonto() {
		return pagoCCMonto;
	}

	public void setPagoCCMonto(String pagoCCMonto) {
		this.pagoCCMonto = pagoCCMonto;
	}

	public String getCompraCCApellido() {
		return compraCCApellido;
	}

	public void setCompraCCApellido(String compraCCApellido) {
		this.compraCCApellido = compraCCApellido;
	}

	public String getCompraCCNombre() {
		return compraCCNombre;
	}

	public void setCompraCCNombre(String compraCCNombre) {
		this.compraCCNombre = compraCCNombre;
	}

	public String getCompraCCDireccion() {
		return compraCCDireccion;
	}

	public void setCompraCCDireccion(String compraCCDireccion) {
		this.compraCCDireccion = compraCCDireccion;
	}

	public List<DetalleCompra> getListCompraDetalleCC() {
		return listCompraDetalleCC;
	}

	public void setListCompraDetalleCC(List<DetalleCompra> listCompraDetalleCC) {
		this.listCompraDetalleCC = listCompraDetalleCC;
	}

	public String getCompraCCFecha() {
		return compraCCFecha;
	}

	public void setCompraCCFecha(String compraCCFecha) {
		this.compraCCFecha = compraCCFecha;
	}

	public String getCompraCCNro() {
		return compraCCNro;
	}

	public void setCompraCCNro(String compraCCNro) {
		this.compraCCNro = compraCCNro;
	}

	public String getCompraCCMonto() {
		return compraCCMonto;
	}

	public void setCompraCCMonto(String compraCCMonto) {
		this.compraCCMonto = compraCCMonto;
	}

	public boolean isVentaCuCo() {
		return ventaCuCo;
	}

	public void setVentaCuCo(boolean ventaCuCo) {
		this.ventaCuCo = ventaCuCo;
	}

	public boolean isCompraCuCo() {
		return compraCuCo;
	}

	public void setCompraCuCo(boolean compraCuCo) {
		this.compraCuCo = compraCuCo;
	}

	public boolean isPagoCuCo() {
		return pagoCuCo;
	}

	public void setPagoCuCo(boolean pagoCuCo) {
		this.pagoCuCo = pagoCuCo;
	}

	public List<Cuentascorriente> getListaCuentasCorrienteIJM() {
		return listaCuentasCorrienteIJM;
	}

	public void setListaCuentasCorrienteIJM(
			List<Cuentascorriente> listaCuentasCorrienteIJM) {
		this.listaCuentasCorrienteIJM = listaCuentasCorrienteIJM;
	}

	public List<Cuentascorriente> getFilteredListadoCCIJM() {
		return filteredListadoCCIJM;
	}

	public void setFilteredListadoCCIJM(List<Cuentascorriente> filteredListadoCCIJM) {
		this.filteredListadoCCIJM = filteredListadoCCIJM;
	}

	public String getMontoTotalCCString() {
		return montoTotalCCString;
	}

	public void setMontoTotalCCString(String montoTotalCCString) {
		this.montoTotalCCString = montoTotalCCString;
	}

	public List<Cuentascorriente> getListaCuentasCorrienteProveedor() {
		return listaCuentasCorrienteProveedor;
	}

	public void setListaCuentasCorrienteProveedor(
			List<Cuentascorriente> listaCuentasCorrienteProveedor) {
		this.listaCuentasCorrienteProveedor = listaCuentasCorrienteProveedor;
	}

	public List<Cuentascorriente> getFilteredListadoCCProveedor() {
		return filteredListadoCCProveedor;
	}

	public void setFilteredListadoCCProveedor(
			List<Cuentascorriente> filteredListadoCCProveedor) {
		this.filteredListadoCCProveedor = filteredListadoCCProveedor;
	}

	public Cliente getProveedorCC() {
		return proveedorCC;
	}

	public void setProveedorCC(Cliente proveedorCC) {
		this.proveedorCC = proveedorCC;
	}

	public Cliente getClienteCC() {
		return clienteCC;
	}

	public void setClienteCC(Cliente clienteCC) {
		this.clienteCC = clienteCC;
	}

	public float getMontoTotalCC() {
		return montoTotalCC;
	}

	public void setMontoTotalCC(float montoTotalCC) {
		this.montoTotalCC = montoTotalCC;
	}

	public Integer getCantidadCC() {
		return cantidadCC;
	}

	public void setCantidadCC(Integer cantidadCC) {
		this.cantidadCC = cantidadCC;
	}

	public List<Cuentascorriente> getFilteredListadoCCCliente() {
		return filteredListadoCCCliente;
	}

	public void setFilteredListadoCCCliente(
			List<Cuentascorriente> filteredListadoCCCliente) {
		this.filteredListadoCCCliente = filteredListadoCCCliente;
	}

	public DAOcuentascorriente getDAOcc() {
		return DAOcc;
	}

	public void setDAOcc(DAOcuentascorriente dAOcc) {
		DAOcc = dAOcc;
	}

	public List<Cuentascorriente> getListaCuentasCorrienteCliente() {
		return listaCuentasCorrienteCliente;
	}

	public void setListaCuentasCorrienteCliente(
			List<Cuentascorriente> listaCuentasCorrienteCliente) {
		this.listaCuentasCorrienteCliente = listaCuentasCorrienteCliente;
	}

	public Cuentascorriente getCuentasCorriente() {
		return cuentasCorriente;
	}

	public void setCuentasCorriente(Cuentascorriente cuentasCorriente) {
		this.cuentasCorriente = cuentasCorriente;
	}
	
	public String goCuentaCorrienteIJM(){
		System.out.println("Metodo getListaCuentasCorrienteIJM()");
		DAOcc = new DAOcuentascorriente();
		listaCuentasCorrienteIJM = null;
		filteredListadoCCIJM = null;
		listaCuentasCorrienteIJM = DAOcc.getListaCCIJM();
		float monto = 0;
		int cantidad = 0;
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		for (Cuentascorriente cuentascorriente : listaCuentasCorrienteIJM) {
			cantidad++;
			monto = cuentascorriente.getCliente().getSaldo();
		}
		String valor = formatear.format(monto);
		inicioCCIJM = null;
		finalCCIJM = null;
		montoTotalCC = monto;
		montoTotalCCString = valor;
		cantidadCC = cantidad;
		volverVistaCC = "cuentacorrienteIJM";
		return "cuentacorrienteIJM";
	}

	public void preProcessPDF(Document document) throws IOException,
	
			BadElementException, DocumentException {
		
		System.out.println("Metodo preProccessPDF()");
		
		Document pdf = document;

		pdf.open();
		pdf.add(new Header("a", "b"));
		pdf.setPageSize(PageSize.A4);
		document.setMargins(30f, 30f, 100f, 100f);
		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String logo = servletContext.getRealPath("") + File.separator
				+ "images" + File.separator + "icono.ico";
		Image image = Image.getInstance(logo);
		image.setAlignment(Image.ALIGN_CENTER);
	}
	
	public String volverACC(){
		return volverVistaCC;
	}
	
	public String volverACCCliente(){
		return "cuentacorriente";
	}
	
	public String onViewDetCC(Cuentascorriente cuenta){
		System.out.println("Metodo onViewDetCC()");
		DAOVTA = new DAOventa();
		daoPago = new DaoPago();
		DAOcompra = new DAOCompra();
		Venta ventaCueCo = new Venta();
		Pago pagoCueCo = new Pago();
		Compra compraCueCo = new Compra();
		Notacredito notaCueCo = new Notacredito();
		if(cuenta.getTipoMovimiento().startsWith("VENTA")){
			ventaCueCo = DAOVTA.getVentaPorId(cuenta.getIdMovimiento());
			ventaCuCo = true;
			compraCuCo = false;
			pagoCuCo = false;
			notaCredCuCo = false;
			compraCVCuCo = false;
			ventaCCApellido = ventaCueCo.getCliente().getApellido();
			ventaCCNombre = ventaCueCo.getCliente().getNombre();
			ventaCCDireccion = ventaCueCo.getCliente().getDireccion();
			listVentasProductosCC = ventaCueCo.getDetalleVenta();
			ventaCCNro = Integer.toString(ventaCueCo.getId());
			ventaCCFecha = ventaCueCo.getStringFecha();
			ventaCCMonto = ventaCueCo.getStringMonto();
			if(ventaCueCo.getEnabled()){
				ventaCCEstado = "Habilitada";
			}else{
				ventaCCEstado = "Deshabilitada";
			}
		}
		if(cuenta.getTipoMovimiento().equals("PAGO")){
			pagoCueCo = daoPago.getPagoPorId(cuenta.getIdMovimiento());
			ventaCuCo = false;
			compraCuCo = false;
			pagoCuCo = true;
			notaCredCuCo = false;
			compraCVCuCo = false;
			pagoCCApellido1 = pagoCueCo.getCliente1().getApellido();
			pagoCCApellido2 = pagoCueCo.getCliente2().getApellido();
			pagoCCFecha = pagoCueCo.getFechaString();
			pagoCCNro = Integer.toString(pagoCueCo.getId());
			pagoCCMonto = pagoCueCo.getMontoString();
			pagoCCDescripcion = pagoCueCo.getDescripcion();
		}
		if(cuenta.getTipoMovimiento().equals("COMPRA")){
			compraCueCo = DAOcompra.getCompraPorId(cuenta.getIdMovimiento());
			ventaCuCo = false;
			compraCuCo = true;
			pagoCuCo = false;
			notaCredCuCo = false;
			compraCVCuCo = false;
			compraCCApellido = compraCueCo.getCliente().getApellido();
			compraCCNombre = compraCueCo.getCliente().getNombre();
			compraCCDireccion = compraCueCo.getCliente().getDireccion();
			listCompraDetalleCC = compraCueCo.getDetalleDeCompra();
			compraCCFecha = compraCueCo.getFechaString();
			compraCCNro = Integer.toString(compraCueCo.getId());
			compraCCMonto = compraCueCo.getMontoString();
		}
		if(cuenta.getTipoMovimiento().equals("NOTA CREDITO")){
			notaCueCo = DAOVTA.getNotaCreditoPorId(cuenta.getIdMovimiento());
			ventaCuCo = false;
			compraCuCo = false;
			pagoCuCo = false;
			notaCredCuCo = true;
			compraCVCuCo = false;
			notaCredCCNro = Integer.toString(notaCueCo.getId());
			notaCredCCFecha = notaCueCo.getFechaString();
			notaCredCCNroVenta = Integer.toString(notaCueCo.getVenta().getId());
			notaCredCCApellido = notaCueCo.getVenta().getCliente().getApellido();
			notaCredCCNombre = notaCueCo.getVenta().getCliente().getNombre();
			notaCredCCDireccion = notaCueCo.getVenta().getCliente().getDireccion();
			listNotaCredCCDetVent = notaCueCo.getVenta().getDetalleVenta();
			notaCredCCFechaVenta = notaCueCo.getVenta().getStringFecha();
			notaCredCCMontoVenta = notaCueCo.getVenta().getStringMonto();
		}
		return "itemCuentaCorriente";
	}
	
	public String goMovimientoCuenta(Cuenta cuenta){
		String retorno = "";
		volverVistaCC = "cuenta";
		DAOVTA = new DAOventa();
		daoPago = new DaoPago();
		DAOcompra = new DAOCompra();
		Venta ventaCueCo = new Venta();
		Pago pagoCueCo = new Pago();
		Compra compraCueCo = new Compra();
		Notacredito notaCueCo = new Notacredito();
		if(cuenta.getNombreTabla().equals("venta")){
			ventaCueCo = DAOVTA.getVentaPorId(cuenta.getIdMovimiento());
			ventaCuCo = true;
			compraCuCo = false;
			pagoCuCo = false;
			notaCredCuCo = false;
			compraCVCuCo = false;
			ventaCCApellido = ventaCueCo.getCliente().getApellido();
			ventaCCNombre = ventaCueCo.getCliente().getNombre();
			ventaCCDireccion = ventaCueCo.getCliente().getDireccion();
			listVentasProductosCC = ventaCueCo.getDetalleVenta();
			ventaCCNro = Integer.toString(ventaCueCo.getId());
			ventaCCFecha = ventaCueCo.getStringFecha();
			ventaCCMonto = ventaCueCo.getStringMonto();
			if(ventaCueCo.getEnabled()){
				ventaCCEstado = "Habilitada";
			}else{
				ventaCCEstado = "Deshabilitada";
			}
			retorno = "itemCuentaCorriente";
		}
		if(cuenta.getNombreTabla().equals("pago")){
			pagoCueCo = daoPago.getPagoPorId(cuenta.getIdMovimiento());
			ventaCuCo = false;
			compraCuCo = false;
			pagoCuCo = true;
			notaCredCuCo = false;
			compraCVCuCo = false;
			pagoCCApellido1 = pagoCueCo.getCliente1().getApellido();
			pagoCCApellido2 = pagoCueCo.getCliente2().getApellido();
			pagoCCFecha = pagoCueCo.getFechaString();
			pagoCCNro = Integer.toString(pagoCueCo.getId());
			pagoCCMonto = pagoCueCo.getMontoString();
			pagoCCDescripcion = pagoCueCo.getDescripcion();
			retorno = "itemCuentaCorriente";
		}
		if(cuenta.getNombreTabla().equals("compra")){
			compraCueCo = DAOcompra.getCompraPorId(cuenta.getIdMovimiento());
			ventaCuCo = false;
			compraCuCo = true;
			pagoCuCo = false;
			notaCredCuCo = false;
			compraCVCuCo = false;
			compraCCApellido = compraCueCo.getCliente().getApellido();
			compraCCNombre = compraCueCo.getCliente().getNombre();
			compraCCDireccion = compraCueCo.getCliente().getDireccion();
			listCompraDetalleCC = compraCueCo.getDetalleDeCompra();
			compraCCFecha = compraCueCo.getFechaString();
			compraCCNro = Integer.toString(compraCueCo.getId());
			compraCCMonto = compraCueCo.getMontoString();
			retorno = "itemCuentaCorriente";
		}
		if(cuenta.getNombreTabla().equals("notacredito")){
			notaCueCo = DAOVTA.getNotaCreditoPorId(cuenta.getIdMovimiento());
			ventaCuCo = false;
			compraCuCo = false;
			pagoCuCo = false;
			notaCredCuCo = true;
			compraCVCuCo = false;
			notaCredCCNro = Integer.toString(notaCueCo.getId());
			notaCredCCFecha = notaCueCo.getFechaString();
			notaCredCCNroVenta = Integer.toString(notaCueCo.getVenta().getId());
			notaCredCCApellido = notaCueCo.getVenta().getCliente().getApellido();
			notaCredCCNombre = notaCueCo.getVenta().getCliente().getNombre();
			notaCredCCDireccion = notaCueCo.getVenta().getCliente().getDireccion();
			listNotaCredCCDetVent = notaCueCo.getVenta().getDetalleVenta();
			notaCredCCFechaVenta = notaCueCo.getVenta().getStringFecha();
			notaCredCCMontoVenta = notaCueCo.getVenta().getStringMonto();
			retorno = "itemCuentaCorriente";
		}
		if(cuenta.getNombreTabla().equals("movimiento_virtual")){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Los Movimientos de compra virtual no tienen vista!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			retorno = "";
		}
		return retorno;
	}
	
	public String onViewDetCCCliente(Cuentascorriente cuenta){
		System.out.println("Metodo onViewCCCliente()");
		DAOVTA = new DAOventa();
		daoPago = new DaoPago();
		DAOcompra = new DAOCompra();
		Venta ventaCueCo = new Venta();
		Pago pagoCueCo = new Pago();
		Compra compraCueCo = new Compra();
		Notacredito notaCueCo = new Notacredito();
		if(cuenta.getTipoMovimiento().equals("VENTA")){
			ventaCueCo = DAOVTA.getVentaPorId(cuenta.getIdMovimiento());
			ventaCuCo = true;
			compraCuCo = false;
			pagoCuCo = false;
			notaCredCuCo = false;
			ventaCCApellido = ventaCueCo.getCliente().getApellido();
			ventaCCNombre = ventaCueCo.getCliente().getNombre();
			ventaCCDireccion = ventaCueCo.getCliente().getDireccion();
			listVentasProductosCC = ventaCueCo.getDetalleVenta();
			ventaCCNro = Integer.toString(ventaCueCo.getId());
			ventaCCFecha = ventaCueCo.getStringFecha();
			ventaCCMonto = ventaCueCo.getStringMonto();
			if(ventaCueCo.getEnabled()){
				ventaCCEstado = "Habilitada";
			}else{
				ventaCCEstado = "Deshabilitada";
			}
		}
		if(cuenta.getTipoMovimiento().equals("PAGO")){
			pagoCueCo = daoPago.getPagoPorId(cuenta.getIdMovimiento());
			ventaCuCo = false;
			compraCuCo = false;
			pagoCuCo = true;
			notaCredCuCo = false;
			pagoCCApellido1 = pagoCueCo.getCliente1().getApellido();
			pagoCCApellido2 = pagoCueCo.getCliente2().getApellido();
			pagoCCFecha = pagoCueCo.getFechaString();
			pagoCCNro = Integer.toString(pagoCueCo.getId());
			pagoCCMonto = pagoCueCo.getMontoString();
			pagoCCDescripcion = pagoCueCo.getDescripcion();
		}
		if(cuenta.getTipoMovimiento().equals("COMPRA")){
			compraCueCo = DAOcompra.getCompraPorId(cuenta.getIdMovimiento());
			ventaCuCo = false;
			compraCuCo = true;
			pagoCuCo = false;
			notaCredCuCo = false;
			compraCCApellido = compraCueCo.getCliente().getApellido();
			compraCCNombre = compraCueCo.getCliente().getNombre();
			compraCCDireccion = compraCueCo.getCliente().getDireccion();
			listCompraDetalleCC = compraCueCo.getDetalleDeCompra();
			compraCCFecha = compraCueCo.getFechaString();
			compraCCNro = Integer.toString(compraCueCo.getId());
			compraCCMonto = compraCueCo.getMontoString();
		}
		if(cuenta.getTipoMovimiento().equals("NOTA CREDITO")){
			notaCueCo = DAOVTA.getNotaCreditoPorId(cuenta.getIdMovimiento());
			ventaCuCo = false;
			compraCuCo = false;
			pagoCuCo = false;
			notaCredCuCo = true;
			notaCredCCNro = Integer.toString(notaCueCo.getId());
			notaCredCCFecha = notaCueCo.getFechaString();
			notaCredCCNroVenta = Integer.toString(notaCueCo.getVenta().getId());
			notaCredCCApellido = notaCueCo.getVenta().getCliente().getApellido();
			notaCredCCNombre = notaCueCo.getVenta().getCliente().getNombre();
			notaCredCCDireccion = notaCueCo.getVenta().getCliente().getDireccion();
			listNotaCredCCDetVent = notaCueCo.getVenta().getDetalleVenta();
			notaCredCCFechaVenta = notaCueCo.getVenta().getStringFecha();
			notaCredCCMontoVenta = notaCueCo.getVenta().getStringMonto();
		}
		return "itemCCCliente";
	}
	
	public void onfilteredDateCC(){
		System.out.println("Metodo onfilteredDateCC()");
		
		if(inicioCCIJM != null && finalCCIJM != null){
			listaCuentasCorrienteIJM = DAOcc.getListaCCIJM(inicioCCIJM, finalCCIJM);
			filteredListadoCCIJM = listaCuentasCorrienteIJM;
		}		
	}
	
	public void onfilteredDateCCProveedor(){
		System.out.println("Metodo onfilteredDateCCProovedor()");
		if(inicioCCP != null && finalCCP!= null){

			
			listaCuentasCorrienteProveedor= DAOcc.getListaCCProveedor(proveedorParaFiltro, inicioCCP, finalCCP);
			filteredListadoCCProveedor= listaCuentasCorrienteProveedor;
			
		}
		
	}
	
	public void onfilteredDateCCC(){
		System.out.println("Metodo onfilteredDateCCC");
		if(inicioCCC != null && finalCCC!= null){

			
			listaCuentasCorrienteCliente= DAOcc.getListaCCCliente(clienteParaFiltro, inicioCCC, finalCCC);
			filteredListadoCCCliente= listaCuentasCorrienteCliente;
			
		}
	}
	
	

	///////////////////////////////////////////////////////////VENTA////////////////////////////////////////////////////////////

	private int idClienteVenta;
	private int idListaPrecio;
	private HashMap<String, Integer> listVentaCliente;
	private HashMap<String, Integer> listaPrecioVtaCliente;
	private DAOventa DAOVTA;
	private List<AuxListapreciosProducto> productosParaVenta;
	private int cantidadProductos;
	private boolean chSelectVenta = false;
	private List<VentasProducto> listaDetalle = new ArrayList<VentasProducto>();
	private ListapreciosProducto prodVenta = null;
	private Venta venta = new Venta();
	private boolean activListaVenta = false;
	private List<Venta> listaVentas = new ArrayList<Venta>();
	private String tipoPago;
	private boolean value1 = false;
	private boolean value2 = false;
	private float ventaCC;
	private float ventaCTDO;
	private Date fechaVta;
	private String nombreNegocio;
	private String direccion;
	private String mail;
	private String telefono;
	private List<AuxListapreciosProducto> selectedItemVenta;
	private List<AuxListapreciosProducto> filteredProductosParaVenta;
	private int nroVenta;
	private String pagoEfectivo;
	private DAOCuentas cuentaDAO = new DAOCuentas();

	public String goVenta() {
		System.out.println("Metodo goVenta()");
		idClienteVenta = 0;
		idListaPrecio = 0;
		listVentaCliente = new HashMap<String, Integer>();
		listaPrecioVtaCliente = new HashMap<String, Integer>();
		productosParaVenta = new ArrayList<AuxListapreciosProducto>();
		selectedItemVenta = new ArrayList<AuxListapreciosProducto>();
		cantidadProductos = 0;
		chSelectVenta = false;
		listaDetalle = new ArrayList<VentasProducto>();
		prodVenta = null;
		venta = new Venta();
		activListaVenta = false;
		nombreNegocio = "";
		telefono = "";
		mail = "";
		direccion = "";
		listVentaCliente = new LinkedHashMap<String, Integer>();
		listaPrecioVtaCliente = new LinkedHashMap<String, Integer>();
		DAOVTA = new DAOventa();
		listaVentas = new ArrayList<Venta>();
		tipoPago = "";
		value1 = false;
		value2 = false;
		ventaCC = 0;
		ventaCTDO = 0;
		fechaVta = null;
		return "venta";

	}

	public DAOCuentas getCuentaDAO() {
		return cuentaDAO;
	}

	public void setCuentaDAO(DAOCuentas cuentaDAO) {
		this.cuentaDAO = cuentaDAO;
	}

	public String getPagoEfectivo() {
		return pagoEfectivo;
	}

	public void setPagoEfectivo(String pagoEfectivo) {
		this.pagoEfectivo = pagoEfectivo;
	}

	public int getNroVenta() {
		return nroVenta;
	}

	public void setNroVenta(int nroVenta) {
		this.nroVenta = nroVenta;
	}

	public List<AuxListapreciosProducto> getFilteredProductosParaVenta() {
		return filteredProductosParaVenta;
	}

	public void setFilteredProductosParaVenta(
			List<AuxListapreciosProducto> filteredProductosParaVenta) {
		this.filteredProductosParaVenta = filteredProductosParaVenta;
	}

	public List<AuxListapreciosProducto> getSelectedItemVenta() {
		return selectedItemVenta;
	}

	public void setSelectedItemVenta(List<AuxListapreciosProducto> selectedItemVenta) {
		this.selectedItemVenta = selectedItemVenta;
	}

	public String getNombreNegocio() {
		return nombreNegocio;
	}

	public void setNombreNegocio(String nombreNegocio) {
		this.nombreNegocio = nombreNegocio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getFechaVta() {
		return fechaVta;
	}

	public void setFechaVta(Date fechaVta) {
		this.fechaVta = fechaVta;
	}

	public float getVentaCC() {
		return ventaCC;
	}

	public void setVentaCC(float ventaCC) {
		this.ventaCC = ventaCC;
	}

	public float getVentaCTDO() {
		return ventaCTDO;
	}

	public void setVentaCTDO(float ventaCTDO) {
		this.ventaCTDO = ventaCTDO;
	}

	public boolean isValue1() {
		return value1;
	}

	public void setValue1(boolean value1) {
		this.value1 = value1;
	}

	public boolean isValue2() {
		return value2;
	}

	public void setValue2(boolean value2) {
		this.value2 = value2;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public List<Venta> getListaVentas() {
		DAOVTA = new DAOventa();
		listaVentas = DAOVTA.getListadoVentas();
		return listaVentas;
	}

	public void setListaVentas(List<Venta> listaVentas) {
		this.listaVentas = listaVentas;
	}

	public boolean isActivListaVenta() {
		return activListaVenta;
	}

	public void setActivListaVenta(boolean activListaVenta) {
		this.activListaVenta = activListaVenta;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public ListapreciosProducto getProdVenta() {
		return prodVenta;
	}

	public void setProdVenta(ListapreciosProducto prodVenta) {
		this.prodVenta = prodVenta;
	}

	public List<VentasProducto> getListaDetalle() {
		return listaDetalle;
	}

	public void setListaDetalle(List<VentasProducto> listaDetalle) {
		this.listaDetalle = listaDetalle;
	}

	public boolean getChSelectVenta() {
		return chSelectVenta;
	}

	public void setChSelectVenta(boolean chSelectVenta) {
		this.chSelectVenta = chSelectVenta;
	}

	public int getCantidadProductos() {
		return cantidadProductos;
	}

	public void setCantidadProductos(int cantidadProductos) {
		this.cantidadProductos = cantidadProductos;
	}

	public List<AuxListapreciosProducto> getProductosParaVenta() {
//		productosParaVenta = new ArrayList<AuxListapreciosProducto>();
//		List<ListapreciosProducto> lista = new ArrayList<ListapreciosProducto>();
//		List<AuxListapreciosProducto> auxLista = new ArrayList<AuxListapreciosProducto>();
//		if (idListaPrecio != 0) {
//			DAOVTA = new DAOventa();
//			lista = DAOVTA.getListadoProductosVenta(idListaPrecio);
//			for (ListapreciosProducto listapreciosProducto : lista) {
//				AuxListapreciosProducto auxiliar = new AuxListapreciosProducto();
//				auxiliar.setId(listapreciosProducto.getId());
//				auxiliar.setDescuento(listapreciosProducto.getDescuento());
//				auxiliar.setListaprecio(listapreciosProducto.getListaprecio());
//				auxiliar.setPorcentaje(listapreciosProducto.getPorcentaje());
//				auxiliar.setProducto(listapreciosProducto.getProducto());
//				auxLista.add(auxiliar);
//			}
//			productosParaVenta = auxLista;
//			filteredProductosParaVenta = auxLista;
////			productosParaVenta = DAOVTA.getListadoProductosVenta(idListaPrecio);
//		}
		return productosParaVenta;
	}

	public void setProductosParaVenta(
			List<AuxListapreciosProducto> productosParaVenta) {
		this.productosParaVenta = productosParaVenta;
	}

	public int getIdListaPrecio() {
		return idListaPrecio;
	}

	public void setIdListaPrecio(int idListaPrecio) {
		this.idListaPrecio = idListaPrecio;
	}

	public void setListaPrecioVtaCliente(
			HashMap<String, Integer> listaPrecioVtaCliente) {
		this.listaPrecioVtaCliente = listaPrecioVtaCliente;
	}

	public DAOventa getDAOVTA() {
		return DAOVTA;
	}

	public void setDAOVTA(DAOventa dAOVTA) {
		DAOVTA = dAOVTA;
	}

	public int getIdClienteVenta() {
		return idClienteVenta;
	}

	public void setIdClienteVenta(int idClienteVenta) {
		this.idClienteVenta = idClienteVenta;
	}

	public HashMap<String, Integer> getListVentaCliente() {
		DAOVTA = new DAOventa();
		listVentaCliente = DAOVTA.getHashMapListas();
		return listVentaCliente;
	}

	public void setListVentaCliente(HashMap<String, Integer> listVentaCliente) {
		this.listVentaCliente = listVentaCliente;
	}
	
	public void onCellEditVenta(AuxListapreciosProducto auxProducto) {
		System.out.println("Metodo onCellEditVenta()");
		List<AuxListapreciosProducto> lista = filteredProductosParaVenta;
		List<AuxListapreciosProducto> listAux = new ArrayList<AuxListapreciosProducto>();
		for (AuxListapreciosProducto auxiliar : lista) {
			AuxListapreciosProducto aux = new AuxListapreciosProducto();
			if (auxiliar.getId() == auxProducto.getId()) {
				aux.setCantidad(auxProducto.getCantidad());
				aux.setDescuento(auxiliar.getDescuento());
				aux.setId(auxiliar.getId());
				aux.setListaprecio(aux.getListaprecio());
				aux.setPorcentaje(auxiliar.getPorcentaje());
				aux.setProducto(auxiliar.getProducto());
			}else{
				aux.setCantidad(auxiliar.getCantidad());
				aux.setDescuento(auxiliar.getDescuento());
				aux.setId(auxiliar.getId());
				aux.setListaprecio(aux.getListaprecio());
				aux.setPorcentaje(auxiliar.getPorcentaje());
				aux.setProducto(auxiliar.getProducto());
			}
			listAux.add(aux);
		}
		filteredProductosParaVenta = null;
//		productosParaVenta = null;
		filteredProductosParaVenta = listAux;
//		productosParaVenta = listAux;
	}

	public void onChangeClienteVenta() {
		System.out.println("Metodo onChangeClienteVenta()");
		cantidadProductos = 0;
		chSelectVenta = false;
		listaDetalle = new ArrayList<VentasProducto>();
		prodVenta = null;
		venta = new Venta();
		mail = "";
		direccion = "";
		telefono = "";
		nombreNegocio = "";
		ventaCC = 0;
		ventaCTDO = 0;

		activListaVenta = false;
		if (idClienteVenta != 0) {
			Cliente cli = new Cliente();
			cli = DAOVTA.obtenerClientePorId(idClienteVenta);
			idListaPrecio = cli.getListaprecio().getId();

			activListaVenta = true;
			venta = new Venta();
			DAOVTA = new DAOventa();
			venta.setCliente(cli);
			setCurrentDate(new Date());
			venta.setFecha(getCurrentDate());
			mail = cli.getMail();
			telefono = cli.getTelefono();
			direccion = cli.getDireccion();
			nombreNegocio = cli.getApellido();
			
			filteredProductosParaVenta = new ArrayList<AuxListapreciosProducto>();
			productosParaVenta = new ArrayList<AuxListapreciosProducto>();
			List<ListapreciosProducto> lista = new ArrayList<ListapreciosProducto>();
			List<AuxListapreciosProducto> auxLista = new ArrayList<AuxListapreciosProducto>();
			if (idListaPrecio != 0) {
				DAOVTA = new DAOventa();
				lista = DAOVTA.getListadoProductosVenta(idListaPrecio);
				for (ListapreciosProducto listapreciosProducto : lista) {
					AuxListapreciosProducto auxiliar = new AuxListapreciosProducto();
					auxiliar.setId(listapreciosProducto.getId());
					auxiliar.setDescuento(listapreciosProducto.getDescuento());
					auxiliar.setListaprecio(listapreciosProducto.getListaprecio());
					auxiliar.setPorcentaje(listapreciosProducto.getPorcentaje());
					auxiliar.setProducto(listapreciosProducto.getProducto());
					auxLista.add(auxiliar);
				}
				productosParaVenta = auxLista;
				filteredProductosParaVenta = auxLista;
			}
		} else {
			activListaVenta = false;
			listaPrecioVtaCliente.clear();
			productosParaVenta = new ArrayList<AuxListapreciosProducto>();
			filteredProductosParaVenta = new ArrayList<AuxListapreciosProducto>();
		}

	}

	public void agregarProductoVenta() {
		System.out.println("Metodo agregarProductoVenta()");
		FacesMessage msg = null;
		boolean ninguno = true;
		boolean stockIns = false;
		String nomProd = "";
		for (AuxListapreciosProducto auxiliar : filteredProductosParaVenta) {
			if(auxiliar.getCantidad() > 0){
				ninguno = false;
				if(!controladorStock(auxiliar)){
					stockIns = true;
					nomProd = nomProd + auxiliar.getProducto().getNombre();
				}
			}
		}
		if(ninguno){
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe colocar una cantidad", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		if(stockIns){
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Productos con stock insuficiente: " + nomProd, null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			filteredProductosParaVenta = new ArrayList<AuxListapreciosProducto>();
			productosParaVenta = new ArrayList<AuxListapreciosProducto>();
			List<ListapreciosProducto> lista = new ArrayList<ListapreciosProducto>();
			List<AuxListapreciosProducto> auxLista = new ArrayList<AuxListapreciosProducto>();
			DAOVTA = new DAOventa();
			lista = DAOVTA.getListadoProductosVenta(idListaPrecio);
			for (ListapreciosProducto listapreciosProducto : lista) {
				AuxListapreciosProducto auxiliar = new AuxListapreciosProducto();
				auxiliar.setId(listapreciosProducto.getId());
				auxiliar.setDescuento(listapreciosProducto.getDescuento());
				auxiliar.setListaprecio(listapreciosProducto.getListaprecio());
				auxiliar.setPorcentaje(listapreciosProducto.getPorcentaje());
				auxiliar.setProducto(listapreciosProducto.getProducto());
				auxLista.add(auxiliar);
			}
			productosParaVenta = auxLista;
			filteredProductosParaVenta = auxLista;
		}
	}

	public void checkProductoVenta() {
		chSelectVenta = true;
	}
	
	public String goConfirmarVenta(){		
		String retorno = "";
		if(!listaDetalle.isEmpty()){
			DecimalFormat formatear = new DecimalFormat("##,##0.00");
			pagoEfectivo = formatear.format(ventaCTDO);
			nroVenta = DAOVTA.getLastNroVenta();
			venta.setFecha(fechaVta);
			retorno = "comprobanteVenta";
		}else{
			FacesMessage msg = null;
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "La lista de productos no puede estar vacia!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}		
		return retorno;
	}

	public void confirmarVenta() {
		System.out.println("Metodo confirmarVenta()");
		FacesMessage msg = null;
		DAOVTA = new DAOventa();
		DAOProducto = new DAOProducto();
		DAOCliente = new DAOcliente();
		cuentaDAO = new DAOCuentas();
		CuentasCorrientes cuentaCC = new CuentasCorrientes();
		Cuenta cuenta = new Cuenta();
		venta.setUsuario(usuario);
		venta.setEnabled(true);
		idVenta = DAOVTA.insetarVenta(venta);	
		if(idVenta != 0){
			venta.setId(idVenta);
			for (VentasProducto ventasProducto : listaDetalle) {
				ventasProducto.setVenta(venta);
				int idDetalle = DAOVTA.insertarDetalleVenta(ventasProducto);
				ventasProducto.setId(idDetalle);
				
				Producto prod = ventasProducto.getProducto();
				int stockProducto = prod.getStock();
				int cantidadVenta = ventasProducto.getCantidad();
				stockProducto = stockProducto - cantidadVenta;
				prod.setStock(stockProducto);
				DAOProducto.updateProducto(prod);
				
				Stock stock = DAOVTA.obtenerStockMasViejo(prod);
				StockDetalle stockDetalle = new StockDetalle();
				int cantidadStock = stock.getCantidad();
				if(cantidadStock >= cantidadVenta){
					cantidadStock = cantidadStock - cantidadVenta;
					stock.setCantidad(cantidadStock);
					stockDetalle.setCantidad(cantidadVenta);
					stockDetalle.setStock(stock);
					stockDetalle.setVentasProducto(ventasProducto);
					DAOVTA.updateStock(stock);
					DAOVTA.insertarStockDetalle(stockDetalle);
				}else{
					stock.setCantidad(0);
					stockDetalle.setCantidad(cantidadStock);
					stockDetalle.setStock(stock);
					stockDetalle.setVentasProducto(ventasProducto);
					DAOVTA.updateStock(stock);
					DAOVTA.insertarStockDetalle(stockDetalle);
					cantidadVenta = cantidadVenta - cantidadStock;
					while(cantidadVenta > 0){
						stock = new Stock();
						stockDetalle = new StockDetalle();
						stock = DAOVTA.obtenerStockMasViejo(prod);
						cantidadStock = stock.getCantidad();
						if(cantidadStock >= cantidadVenta){
							cantidadStock = cantidadStock - cantidadVenta;
							stock.setCantidad(cantidadStock);
							stockDetalle.setCantidad(cantidadVenta);
							stockDetalle.setStock(stock);
							stockDetalle.setVentasProducto(ventasProducto);
							DAOVTA.updateStock(stock);
							DAOVTA.insertarStockDetalle(stockDetalle);
							cantidadVenta = 0;
						}else{
							stock.setCantidad(0);
							stockDetalle.setCantidad(cantidadStock);
							stockDetalle.setStock(stock);
							stockDetalle.setVentasProducto(ventasProducto);
							DAOVTA.updateStock(stock);
							DAOVTA.insertarStockDetalle(stockDetalle);
							cantidadVenta = cantidadVenta - cantidadStock;
						}
					}
				}
			}
			Cliente cli = venta.getCliente();
			cuenta.setCliente(cli);
			cuenta.setDebe(venta.getMonto());
			cuenta.setDetalle("Venta Nro: " + idVenta);
			cuenta.setFecha(venta.getFecha());
			cuenta.setIdMovimiento(idVenta);
			cuenta.setMonto(venta.getMonto());
			cuenta.setNombreTabla("venta");
			cuenta.setUsuario(usuario);
			float fSaldo = cli.getSaldo();
			fSaldo = fSaldo + venta.getMonto();
			cuenta.setSaldo(fSaldo);
			cli.setSaldo(fSaldo);
//			DAOCliente.updateCliente(cli);
//			insertarCuentaCorriente(cuenta);
			cuentaCC.insertarCCC(cuenta);
			
			if(ventaCTDO != 0){
				cuentaCC = new CuentasCorrientes();
				cuenta = new Cuenta();
				cuenta.setCliente(cli);
				cuenta.setHaber(ventaCTDO);
				cuenta.setDetalle("Venta Nro: " + idVenta + ", Pago en efectivo");
				cuenta.setIdMovimiento(venta.getId());
				cuenta.setNombreTabla("venta");
				cuenta.setMonto(ventaCTDO);
				cuenta.setUsuario(usuario);
				cuenta.setFecha(venta.getFecha());
				fSaldo = cli.getSaldo() - ventaCTDO;
				cuenta.setSaldo(fSaldo);
				cli.setSaldo(fSaldo);
				DAOCliente.updateCliente(cli);
				cuentaCC.insertarCCC(cuenta);
//				insertarCuentaCorriente(cuenta);
				//INSERCION EN CAJA			
				Caja cajaNew = new Caja();
				cajaNew.setConcepto("PAGO DE CONTADO - RECIBIDO (" + cli.getApellido() + ") - VENTA (" + venta.getId() + ")");
				cajaNew.setFecha(new Date());
				cajaNew.setIdMovimiento(venta.getId());
				cajaNew.setMonto(ventaCTDO);
				cajaNew.setTipo("Entrada");
				cajaNew.setUsuarioBean(usuario);
				guardarCaja(cajaNew);
			}
			generarFactura(listaDetalle, venta);
			vaciarFormVenta();
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Venta guardada con éxito!", null);
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ocurrió un error al guardar la venta!", null);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void generarFactura(List<VentasProducto> lista, Venta ven) {
		System.out.println("Metodo generarFactura()");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("numero", ven.getId());
		parameters.put("apellido", ven.getCliente().getApellido());
		parameters.put("nombre", ven.getCliente().getNombre());
		parameters.put("fecha", ven.getStringFecha());
		parameters.put("monto", ven.getMontoString());
		parameters.put("direccion", ven.getCliente().getDireccion());
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("reportes/comprobante.jrxml");
			JasperReport jasperReport;
			jasperReport = JasperCompileManager.compileReport(reportStream);
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(
					lista);

			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			facesContext.responseComplete();
			response.setContentType("application/pdf");

			response.addHeader("Content-disposition", "inline;filename="
					+ "comprobante.pdf");
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, source);
			JasperExportManager.exportReportToPdfStream(print,
					servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean controladorStock(AuxListapreciosProducto PV) {
		System.out.println("Metodo cotroladorStock()");
		List<VentasProducto> listaAux = new ArrayList<VentasProducto>();
		DecimalFormat formato = new DecimalFormat("#.00");
		boolean retorno = false;
		if ((listaDetalle.size() == 0)) {
			if (PV.getProducto().getStock() < PV.getCantidad()) {
				retorno = false;
			} else {
				VentasProducto detalle = new VentasProducto();
				detalle.setCantidad(PV.getCantidad());
				detalle.setProducto(PV.getProducto());
				detalle.setPrecioUnitario(PV.getPreciovta());
				detalle.setSubtotal(detalle.getPrecioUnitario()
						* detalle.getCantidad());
				venta.setMonto(detalle.getSubtotal());
				listaAux.add(detalle);
				retorno = true;
			}
		} else {
			int cantComparador = PV.getCantidad();
			boolean condicion = true;
			for (VentasProducto LD : listaDetalle) {
				if (LD.getProducto().getId() == PV.getProducto().getId()) {
					cantComparador = LD.getCantidad() + cantComparador;
					condicion = false;
					LD.setCantidad(cantComparador);
					LD.setProducto(PV.getProducto());
					LD.setPrecioUnitario(PV.getPreciovta());
					LD.setSubtotal(PV.getPreciovta() * cantComparador);
					venta.setMonto(venta.getMonto() + LD.getSubtotal());
				}
				if (PV.getProducto().getStock() < cantComparador) {
					retorno = false;
				} else {
					listaAux.add(LD);
					retorno = true;
				}
			}

			if (condicion & retorno) {
				VentasProducto detalle = new VentasProducto();
				detalle.setCantidad(PV.getCantidad());
				detalle.setProducto(PV.getProducto());
				detalle.setPrecioUnitario(PV.getPreciovta());
				detalle.setSubtotal(detalle.getPrecioUnitario()
						* detalle.getCantidad());
				venta.setMonto(detalle.getSubtotal() + venta.getMonto());
				listaAux.add(detalle);
				retorno = true;
			}
		}
		if (retorno) {
			listaDetalle = new ArrayList<VentasProducto>();
			listaDetalle = listaAux;
		}
		String montoVenta = formato.format(venta.getMonto());
		System.out.println(montoVenta);
		montoVenta = montoVenta.replace(",", ".");
		float montoTot = Float.parseFloat(montoVenta);
		setVentaCC(montoTot);
		return retorno;
	}

	public void vaciarFormVenta() {
		System.out.println("Metodo vaciarFormVenta()");
		idClienteVenta = 0;
		idListaPrecio = 0;
		listVentaCliente = new HashMap<String, Integer>();
		listaPrecioVtaCliente = new HashMap<String, Integer>();
		productosParaVenta = new ArrayList<AuxListapreciosProducto>();
		filteredProductosParaVenta = new ArrayList<AuxListapreciosProducto>();
		cantidadProductos = 0;
		chSelectVenta = false;
		listaDetalle = new ArrayList<VentasProducto>();
		prodVenta = null;
		venta = new Venta();
		activListaVenta = false;
		nombreNegocio = "";
		telefono = "";
		mail = "";
		direccion = "";

	}

	public String volverVenta() {
		vaciarFormVenta();
		return "venta";
	}

	public void deleteVenta(Venta ven) {
		System.out.println("Metodo deleteVenta()");
		FacesMessage msg = null;
		boolean retorno = true;
		DAOVTA = new DAOventa();
		if (ven != null) {
			if (DAOVTA.bajaVenta(ven)) {
				venta = ven;
				List<VentasProducto> vp = new ArrayList<VentasProducto>();
				vp = ven.getVentasProductos();
				listaDetalle = vp;
				for (VentasProducto det : vp) {
					Producto prod = new Producto();
					DAOProducto = new DAOProducto();
					prod = DAOProducto
							.getProductoXId(det.getProducto().getId());
					prod.setStock(det.getProducto().getStock()
							+ prod.getStock());
					if (DAOProducto.updateProducto(prod) == 0) {
						retorno = false;
					} else {

					}

				}
			}
		}
		if (retorno) {

		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"No se ha podido dar de baja la venta", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
	}

	public String nuevaVenta() {
		System.out.println("Metodo nuevaVenta()");
		idClienteVenta = 0;
		idListaPrecio = 0;
		float valorVenta = 0;
		ventaCTDO = valorVenta;
		ventaCC = valorVenta;
		listVentaCliente = new HashMap<String, Integer>();
		listaPrecioVtaCliente = new HashMap<String, Integer>();
		productosParaVenta = new ArrayList<AuxListapreciosProducto>();
		filteredProductosParaVenta = new ArrayList<AuxListapreciosProducto>();
		cantidadProductos = 0;
		chSelectVenta = false;
		listaDetalle = new ArrayList<VentasProducto>();
		prodVenta = null;
		venta = new Venta();
		activListaVenta = false;
		fechaVta = null;
		fechaVta = new Date();
		venta.setFecha(new Date());
		direccion = "";
		mail = "";
		telefono = "";
		nombreNegocio = "";
		return "venta";
	}
	
	public void onBlurMontoCtdo(){
		DecimalFormat formato = new DecimalFormat("#.00");
		String mto = formato.format(venta.getMonto());
		mto = mto.replace(",", ".");
		float montoVenta = Float.parseFloat(mto);
		float ventCC = montoVenta - ventaCTDO;
		String cc = formato.format(ventCC);
		cc = cc.replace(",", ".");
		ventaCC = Float.parseFloat(cc);
	}
	
	public void onItemVentaDelete(VentasProducto product) {
		System.out.println("Metodo onItemVentaDelete()");
		DecimalFormat formato = new DecimalFormat("#.00");
		float montoTot;
		List<VentasProducto> lista = new ArrayList<VentasProducto>();
		for (VentasProducto ventasProducto : listaDetalle) {
			if(ventasProducto.getProducto().getId() != product.getProducto().getId()){
				lista.add(ventasProducto);
			}
		}
		
		montoTot = venta.getMonto() - product.getSubtotal();
		venta.setMonto(montoTot);
		String montoVenta = formato.format(montoTot);
		System.out.println(montoVenta);
		montoVenta = montoVenta.replace(",", ".");
		float montoCC = Float.parseFloat(montoVenta);
		setVentaCC(montoCC);
		listaDetalle = new ArrayList<VentasProducto>();
		listaDetalle = lista;
	}

	// //////////////////////////BAJAVENTA///////////////////////////////////

	private int idClienteBajaVentas;
	private List<Cliente> listaClientesVentas;
	private Integer estadoVentas = 2;
	private List<Venta> listaVentasBaja;
	private List<Venta> filteredVentasBaja;
	private Date inicioVenta;
	private Date finalVenta;

	public String goBajaVenta() {
		System.out.println("Metodo goBjajaVenta()");
		DAOVTA = new DAOventa();
		idClienteBajaVentas = 0;
		listaClientesVentas = new ArrayList<Cliente>();
		estadoVentas = 2;
		listaVentasBaja = new ArrayList<Venta>();
		filteredVentasBaja = new ArrayList<Venta>();
		listaVentasBaja = DAOVTA.getListadoVentas();
		filteredVentasBaja = listaVentasBaja;
		inicioVenta = null;
		finalVenta = null;
		return "bajaVenta";
	}

	public Date getInicioVenta() {
		return inicioVenta;
	}

	public void setInicioVenta(Date inicioVenta) {
		this.inicioVenta = inicioVenta;
	}

	public Date getFinalVenta() {
		return finalVenta;
	}

	public void setFinalVenta(Date finalVenta) {
		this.finalVenta = finalVenta;
	}

	public int getIdClienteBajaVentas() {
		return idClienteBajaVentas;
	}

	public void setIdClienteBajaVentas(int idClienteBajaVenta) {
		this.idClienteBajaVentas = idClienteBajaVenta;
	}

	public List<Cliente> getListaClientesVentas() {
		DAOCliente = new DAOcliente();
		listaClientesVentas = DAOCliente.getListadoClientes();
		return listaClientesVentas;
	}

	public void setListaClientesVentas(List<Cliente> listaClientesVentas) {
		this.listaClientesVentas = listaClientesVentas;
	}

	public Integer getEstadoVentas() {
		return estadoVentas;
	}

	public void setEstadoVentas(Integer estadoVentas) {
		this.estadoVentas = estadoVentas;
	}

	public List<Venta> getListaVentasBaja() {
		DAOVTA = new DAOventa();
		listaVentasBaja = DAOVTA.getListVentasOrderFecha();
		return listaVentasBaja;
	}

	public void setListaVentasBaja(List<Venta> listaVentasBaja) {
		this.listaVentasBaja = listaVentasBaja;
	}

	public List<Venta> getFilteredVentasBaja() {
		return filteredVentasBaja;
	}

	public void setFilteredVentasBaja(List<Venta> filteredVentasBaja) {
		this.filteredVentasBaja = filteredVentasBaja;
	}

	public void onClienteVentasCheck() {
		System.out.println("Metodo onClienteVentasCheck()");
		DAOVTA = new DAOventa();
		filteredVentasBaja = new ArrayList<Venta>();
		List<Venta> lista = new ArrayList<Venta>();
		if (idClienteBajaVentas == 0 && estadoVentas == 2) {
			lista = DAOVTA.getListVentasOrderFecha();
			if (inicioVenta != null && finalVenta != null) {
				lista = DAOVTA.getListVentasOrderFecha(inicioVenta, finalVenta);
			}
		}
		if (idClienteBajaVentas != 0 && estadoVentas == 2) {
			Cliente client = new Cliente();
			client.setId(idClienteBajaVentas);
			lista = DAOVTA.getVentasPorCliente(client);
			if (inicioVenta != null && finalVenta != null) {
				lista = DAOVTA.getVentasPorCliente(client, inicioVenta,
						finalVenta);
			}
		}
		if (idClienteBajaVentas == 0 && estadoVentas != 2) {
			Cliente client = new Cliente();
			client.setId(idClienteBajaVentas);
			lista = DAOVTA.getVentasPorEstado(estadoVentas);
			if (inicioVenta != null && finalVenta != null) {
				lista = DAOVTA.getVentasPorEstado(estadoVentas, inicioVenta,
						finalVenta);
			}
		}
		if (idClienteBajaVentas != 0 && estadoVentas != 2) {
			Cliente client = new Cliente();
			client.setId(idClienteBajaVentas);
			lista = DAOVTA.getListVentasOrderFecha(client, estadoVentas);
			if (inicioVenta != null && finalVenta != null) {
				lista = DAOVTA.getListVentasOrderFecha(client, estadoVentas,
						inicioVenta, finalVenta);
			}
		}
		filteredVentasBaja = lista;
	}

	@SuppressWarnings("deprecation")
	public void bajarVenta(Venta venta1) {
		System.out.println("Metodo bajarVenta()");
		FacesMessage msg = null;
		setCurrentDate(new Date());
		DAOVTA = new DAOventa();
		DAOcc = new DAOcuentascorriente();
		DAOCliente = new DAOcliente();
		DAOProducto = new DAOProducto();
		cuentaDAO = new DAOCuentas();
		Notacredito notaCredito = new Notacredito();
		notaCredito.setFechaAlta(getCurrentDate());
		notaCredito.setUsuario(usuario);
		notaCredito.setVenta(venta1);
		int idNotaCredito = DAOVTA.insertNotaCredito(notaCredito);
		if (idNotaCredito != 0) {
			notaCredito.setId(idNotaCredito);
			venta1.setEnabled(false);
			if (DAOVTA.updateVenta(venta1)) {
				List<VentasProducto> detalleVen = venta1.getDetalleVenta();
//				List<Cuentascorriente> listCuentas = DAOcc.getListCuentaCorrientePorVenta(venta1.getId());
				List<Cuenta> listaCuenta = cuentaDAO.getLista(venta1.getId(), "venta");
				boolean valorC = true;
				boolean valorCC = true;
				for (Cuenta cuenta : listaCuenta) {
					Cuenta cuentaNueva = new Cuenta();
					Cliente cli = cuenta.getCliente();					
					float dSaldo = cli.getSaldo();
					float fMonto = cli.getSaldo();					
					cuentaNueva.setCliente(cli);
					cuentaNueva.setIdMovimiento(idNotaCredito);
					cuentaNueva.setNombreTabla("notacredito");
					Date fechaHoy = new Date();
					int minute = fechaHoy.getMinutes();					
					cuentaNueva.setUsuario(usuario);					
					if(cuenta.getHaber() != 0){
						Caja cajaNew = new Caja();
						cajaNew.setConcepto("NOTA CREDITO");
						cajaNew.setFecha(new Date());
						cajaNew.setIdMovimiento(idNotaCredito);
						cajaNew.setMonto(cuenta.getHaber());
						cajaNew.setTipo("Salida");
						cajaNew.setUsuarioBean(usuario);
						guardarCaja(cajaNew);						
						dSaldo = dSaldo + cuenta.getHaber();
						fMonto = fMonto + cuenta.getHaber();						
						cuentaNueva.setDetalle("Nota de Crédito - Venta: " + venta1.getId() + ", descuento pago en efectivo");
						cuentaNueva.setDebe(cuenta.getHaber());
						cuentaNueva.setMonto(cuenta.getHaber());
						minute = minute + 1;
						fechaHoy.setMinutes(minute);
					}
					if(cuenta.getDebe() != 0){
						dSaldo = dSaldo - cuenta.getDebe();
						fMonto = fMonto - cuenta.getDebe();						
						cuentaNueva.setDetalle("Nota de Crédito - Venta: " + venta1.getId());
						cuentaNueva.setHaber(cuenta.getDebe());
						cuentaNueva.setMonto(cuenta.getDebe());
					}
					cuentaNueva.setFecha(fechaHoy);
					cli.setSaldo(dSaldo);
					cuentaNueva.setSaldo(fMonto);
					if(cuentaDAO.insertar(cuentaNueva) == 0){
						valorCC = false;
					}
					if(DAOCliente.updateCliente(cli) == 0){
						valorC = false;
					}
				}
				boolean valorS = true;
				boolean valorSP = true;
				for (VentasProducto ventasProducto : detalleVen) {
					Producto prod = new Producto();
					prod = ventasProducto.getProducto();
					int stockProd = prod.getStock()
							+ ventasProducto.getCantidad();
					prod.setStock(stockProd);
					List<StockDetalle> lista = new ArrayList<StockDetalle>();
					lista = ventasProducto.getListStockDetalle();
					for (StockDetalle stockDetalle : lista) {
						Stock stock = new Stock();
						stock = stockDetalle.getStock();
						int cant = stock.getCantidad();
						cant = cant + stockDetalle.getCantidad();
						stock.setCantidad(cant);
						if (!DAOVTA.updateStock(stock)) {
							valorS = false;
						}
						checkStockMinimo();
					}
					int valorSProd = DAOProducto.updateProducto(prod);
					if (valorSProd == 0) {
						valorSP = false;
					}
				}
				if (valorC && valorCC && valorS && valorSP) {
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"La Venta se dio de baja con exito!", null);
				} else {
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Ocurrio un problema al dar de baja la venta, "
									+ "intentelo nuevamente", null);
				}
			} else {
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Ocurrio un problema al dar de baja la venta, "
								+ "intentelo nuevamente", null);
			}

		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Ocurrio un problema al dar de baja la venta, "
							+ "intentelo nuevamente", null);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
		generarNotaCredito(venta1, notaCredito);
		
	}

	public void generarNotaCredito(Venta venta1, Notacredito notaCredito) {
		System.out.println("Metodo generarNotaCredito()");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("numero", venta1.getId());
		parameters.put("apellido", venta1.getCliente().getApellido());
		parameters.put("nombre", venta1.getCliente().getNombre());
		parameters.put("fecha", venta1.getStringFecha());
		parameters.put("monto", venta1.getMontoString());
		parameters.put("idNC", notaCredito.getId());
		parameters.put("fechaNC", notaCredito.getFechaString());

		List<VentasProducto> listaDetalle1 = new ArrayList<VentasProducto>();
		listaDetalle1 = venta1.getDetalleVenta();

		String formaPrinteo;
		formaPrinteo = "attachment;filename=";

		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("reportes/notaCredito.jrxml");
			JasperReport jasperReport;
			jasperReport = JasperCompileManager.compileReport(reportStream);
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(
					listaDetalle1);

			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			facesContext.responseComplete();
			response.setContentType("application/pdf");

			response.addHeader("Content-disposition", formaPrinteo
					+ "notaCredito.pdf");
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, source);
			JasperExportManager.exportReportToPdfStream(print,
					servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	///////////////////////////////////////////////PEDIDO-COMPRA////////////////////////////////////////////////////////////
	
	private List<Cliente> listaProveedoresCompra;
	private Integer idProveedorCompra;
	private String nombreProveedorCompra;
	private String direccionProveedorCompra;
	private List<Rubro> listaRubrosCompra;
	private Integer idRubroCompra;
	private List<AuxiliarProducto> listaProductosCompra;
	private List<AuxiliarProducto> listaProductosCompraCheck = new ArrayList<AuxiliarProducto>();
	private boolean checkProductosCompra = false;
	private DAOCompra DAOcompra;
	private Integer cantidad;
	private float precio;
	private float montoCompra;
	private String montoCompraString;
	private boolean checkPtosDescuento = false;
	private Date inicioPedidos;
	private Date finalPedidos;
	
	
	
	
	public Date getInicioPedidos() {
		return inicioPedidos;
	}

	public void setInicioPedidos(Date inicioPedidos) {
		this.inicioPedidos = inicioPedidos;
	}

	public Date getFinalPedidos() {
		return finalPedidos;
	}

	public void setFinalPedidos(Date finalPedidos) {
		this.finalPedidos = finalPedidos;
	}

	public String goCompra(){
		System.out.println("Metodo goCompra()");
		listaProveedoresCompra = new ArrayList<Cliente>();
		idProveedorCompra=0;
		nombreProveedorCompra ="";
		direccionProveedorCompra="";
		listaRubrosCompra = new ArrayList<Rubro>();
		idRubroCompra=0;
		listaProductosCompra = new ArrayList<AuxiliarProducto>();
		listaProductosCompraCheck = new ArrayList<AuxiliarProducto>();
		checkProductosCompra = false;
		DAOcompra = new DAOCompra();
		cantidad=0;
		precio=0;
		montoCompra=0;
		montoCompraString="";
		checkPtosDescuento = false;
		return "compra";
	}


	public String getMontoCompraString() {
		return montoCompraString;
	}

	public void setMontoCompraString(String montoCompraString) {
		this.montoCompraString = montoCompraString;
	}

	public boolean isCheckPtosDescuento() {
		return checkPtosDescuento;
	}

	public void setCheckPtosDescuento(boolean checkPtosDescuento) {
		this.checkPtosDescuento = checkPtosDescuento;
	}

	public float getMontoCompra() {
		return montoCompra;
	}

	public void setMontoCompra(float montoCompra) {
		this.montoCompra = montoCompra;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public DAOCompra getDAOcompra() {
		return DAOcompra;
	}

	public void setDAOcompra(DAOCompra dAOcompra) {
		DAOcompra = dAOcompra;
	}

	public boolean isCheckProductosCompra() {
		return checkProductosCompra;
	}

	public void setCheckProductosCompra(boolean checkProductosCompra) {
		this.checkProductosCompra = checkProductosCompra;
	}

	public List<AuxiliarProducto> getListaProductosCompraCheck() {
		return listaProductosCompraCheck;
	}

	public void setListaProductosCompraCheck(
			List<AuxiliarProducto> listaProductosCompraCheck) {
		this.listaProductosCompraCheck = listaProductosCompraCheck;
	}

	public List<AuxiliarProducto> getListaProductosCompra() {
		return listaProductosCompra;
	}

	public void setListaProductosCompra(
			List<AuxiliarProducto> listaProductosCompra) {
		this.listaProductosCompra = listaProductosCompra;
	}

	public Integer getIdRubroCompra() {
		return idRubroCompra;
	}

	public void setIdRubroCompra(Integer idRubroCompra) {
		this.idRubroCompra = idRubroCompra;
	}

	public List<Rubro> getListaRubrosCompra() {
		DAOrubro daoRubro = new DAOrubro();
		listaRubrosCompra = daoRubro.getListadoRubrosAct();
		return listaRubrosCompra;
	}

	public void setListaRubrosCompra(List<Rubro> listaRubrosCompra) {
		this.listaRubrosCompra = listaRubrosCompra;
	}

	public String getDireccionProveedorCompra() {
		return direccionProveedorCompra;
	}

	public void setDireccionProveedorCompra(String direccionProveedorCompra) {
		this.direccionProveedorCompra = direccionProveedorCompra;
	}

	public String getNombreProveedorCompra() {
		return nombreProveedorCompra;
	}

	public void setNombreProveedorCompra(String nombreProveedorCompra) {
		this.nombreProveedorCompra = nombreProveedorCompra;
	}

	public Integer getIdProveedorCompra() {
		return idProveedorCompra;
	}

	public void setIdProveedorCompra(Integer idProveedorCompra) {
		this.idProveedorCompra = idProveedorCompra;
	}

	public List<Cliente> getListaProveedoresCompra() {
		DAOproveedor daoProveedor = new DAOproveedor();
		listaProveedoresCompra = daoProveedor.getListadoProveedoresAct();
		return listaProveedoresCompra;
	}

	public void setListaProveedoresCompra(
			List<Cliente> listaProveedoresCompra) {
		this.listaProveedoresCompra = listaProveedoresCompra;
	}

	public void onProveedorCompraCheck() {
		System.out.println("Metodo onProveedorCompraCheck()");
		DAOproveedor daoProveedore = new DAOproveedor();
		if (idProveedorCompra == 0) {
			nombreProveedorCompra = null;
			direccionProveedorCompra = null;
		} else {
			Cliente prov = new Cliente();
			prov = daoProveedore.getProveedorXId(idProveedorCompra);
			nombreProveedorCompra = prov.getNombre();
			direccionProveedorCompra = prov.getDireccion();
		}

	}

	public void onRubroCompraCheck() {
		System.out.println("Metodo onRubroCompraCheck()");
		DAOcompra = new DAOCompra();
		if (idRubroCompra == 0) {
			listaProductosCompra = new ArrayList<AuxiliarProducto>();
		} else {
			List<Producto> lista = DAOcompra.getProductosXRubro(idRubroCompra);
			List<AuxiliarProducto> listAux = new ArrayList<AuxiliarProducto>();
			for (Producto producto : lista) {
				AuxiliarProducto auxiliar = new AuxiliarProducto();
				auxiliar.setId(producto.getId());
				auxiliar.setNombre(producto.getNombre());
				listAux.add(auxiliar);
			}
			listaProductosCompra = listAux;
		}
	}

	public void onCellEditPedido(AuxiliarProducto product) {
		System.out.println("Metodo onCellEditPedido()");
		List<AuxiliarProducto> lista = listaProductosCompra;
		List<AuxiliarProducto> listAux = new ArrayList<AuxiliarProducto>();
		for (AuxiliarProducto auxiliar : lista) {
			if (auxiliar.getId() == product.getId()) {
				auxiliar.setCantidad(product.getCantidad());
				auxiliar.setPrecio(product.getPrecio());
			}
			listAux.add(auxiliar);
		}
		listaProductosCompra = new ArrayList<AuxiliarProducto>();
		listaProductosCompra = listAux;
	}

	public void onCheckProduct(AuxiliarProducto product) {
		System.out.println("Metodo onCheckProduct()");
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		if (product.getCantidad() != 0
				&& product.getPrecio() != 0) {
			List<AuxiliarProducto> listaCompra = new ArrayList<AuxiliarProducto>();
			if (listaProductosCompraCheck.isEmpty()) {
				listaCompra.add(product);
			} else {
				listaCompra = listaProductosCompraCheck;
				listaCompra.add(product);
			}
			listaProductosCompraCheck = new ArrayList<AuxiliarProducto>();
			listaProductosCompraCheck = listaCompra;
			checkProductosCompra = true;
			List<AuxiliarProducto> lista = new ArrayList<AuxiliarProducto>();
			if (!listaProductosCompra.isEmpty()) {
				for (AuxiliarProducto auxiliarProducto : listaProductosCompra) {
					if (product.getId() != auxiliarProducto.getId()) {
						lista.add(auxiliarProducto);
					}
				}
				float valor = product.getCantidad() * product.getPrecio();
				montoCompra = montoCompra + valor;
				montoCompraString = formatear.format(montoCompra);
				listaProductosCompra = new ArrayList<AuxiliarProducto>();
				listaProductosCompra = lista;
			}

		}
	}

	public void onProductDelete(AuxiliarProducto product) {
		System.out.println("Metodo onProductoDelete()");
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		List<AuxiliarProducto> lista = new ArrayList<AuxiliarProducto>();
		for (AuxiliarProducto auxiliarProducto : listaProductosCompraCheck) {
			if (product.getId() != auxiliarProducto.getId()) {
				lista.add(auxiliarProducto);
			}
		}
		if (lista.isEmpty()) {
			checkProductosCompra = false;
		}
		float valor = product.getCantidad() * product.getPrecio();
		montoCompra = montoCompra - valor;
		montoCompraString = formatear.format(montoCompra);
		listaProductosCompraCheck = new ArrayList<AuxiliarProducto>();
		listaProductosCompraCheck = lista;
	}

	public void guardarCompra() {
		System.out.println("Metodo guardarCompra()");
		FacesMessage msg = null;
		if (listaProductosCompraCheck.isEmpty()){
			if(idProveedorCompra != 0){
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Error!" +
						"Debe seleccionar un proveedor y llenar la lista del pedido", null);
			}else{
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Error!" +
						"Debe seleccionar un proveedor y llenar la lista del pedido", null);
			}
			
		} else {
			setCurrentDate(new Date());
			DAOcompra = new DAOCompra();
			Cliente proveedore = new Cliente();
			Compra compra = new Compra();
			proveedore.setId(idProveedorCompra);
			compra.setEnabled(true);
			compra.setEstado("Pendiente");
			compra.setFechaAlta(getCurrentDate());
			compra.setMonto(montoCompra);
			compra.setCliente(proveedore);
			compra.setUsuario1(usuario);
			int idCompra = DAOcompra.insertPedidoCompra(compra);
			if (idCompra != 0) {
				int idDetalleCompra = 0;
				for (AuxiliarProducto auxiliar : listaProductosCompraCheck) {
					DetalleCompra detalle = new DetalleCompra();
					Producto product = new Producto();
					product.setId(auxiliar.getId());
					detalle.setCantidad(auxiliar.getCantidad());
					detalle.setCompra(compra);
					detalle.setPrecioUnitario(auxiliar.getPrecio());
					detalle.setProducto(product);
					float subtotal = auxiliar.getCantidad()
							* auxiliar.getPrecio();
					detalle.setSubtotal(subtotal);
					idDetalleCompra = DAOcompra
							.insertDetallePedidoCompra(detalle);
				}
				if (idDetalleCompra != 0) {
					listaProductosCompra = new ArrayList<AuxiliarProducto>();
					listaProductosCompraCheck = new ArrayList<AuxiliarProducto>();
					idProveedorCompra = 0;
					nombreProveedorCompra = null;
					direccionProveedorCompra = null;
					idRubroCompra = 0;
					montoCompra = 0;
					checkProductosCompra = false;
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Pedido guardado correctamente", "Exito");
				} else {
					msg = new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							"Error",
							"El Pedido no se guardo, intentelo nuevamente");
				}
			} else {
				msg = new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						"Error",
						"La lista de productos seleccionados no puede estar vacia");
			}
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void cancelarCompra() {
		System.out.println("Metodo cancelarCompra()");
		listaProductosCompra = new ArrayList<AuxiliarProducto>();
		listaProductosCompraCheck = new ArrayList<AuxiliarProducto>();
		idProveedorCompra = 0;
		nombreProveedorCompra = null;
		direccionProveedorCompra = null;
		idRubroCompra = 0;
		montoCompra = 0;
		checkProductosCompra = false;
		checkPtosDescuento = false;
	}

	// ////////////////////PEDIDOS////////////////////////////////

	private Integer idProveedorPedidos;
	private String estadoPedidos = "";
	private List<Cliente> listaProveedoresPedidos;
	private List<Compra> listaPedidos;
	private List<Compra> filteredPedidos;
	private Compra compra = new Compra();
	private Integer idProductoPedidos;
	private Integer cantidadPedidos;
	private float precioCompraPedidos;
	private float ptosDescuentoPedidos;
	private float montoPedidos;
	private String montoPedidosString;
	private List<Producto> productosListaPedidos;
	private List<DetalleCompra> detallesPedidos;

	public String goPedidos() {
		System.out.println("Metodo goPedidos()");
		idProveedorPedidos = 0;
		estadoPedidos = "";
		listaProveedoresPedidos = new ArrayList<Cliente>();
		listaPedidos = new ArrayList<Compra>();
		filteredPedidos = new ArrayList<Compra>();
		compra = new Compra();
		DAOcompra = new DAOCompra();
		listaPedidos = DAOcompra.getPedidos();
		filteredPedidos = listaPedidos;
		idProductoPedidos = 0;
		cantidadPedidos = 0;
		precioCompraPedidos = 0;
		ptosDescuentoPedidos = 0;
		montoPedidos = 0;
		montoPedidosString = "";
		productosListaPedidos = new ArrayList<Producto>();
		detallesPedidos = new ArrayList<DetalleCompra>();
		return "pedidos";
	}

	public String getMontoPedidosString() {
		return montoPedidosString;
	}

	public void setMontoPedidosString(String montoPedidosString) {
		this.montoPedidosString = montoPedidosString;
	}

	public String getEstadoPedidos() {
		return estadoPedidos;
	}

	public void setEstadoPedidos(String estadoPedidos) {
		this.estadoPedidos = estadoPedidos;
	}

	public float getMontoPedidos() {
		return montoPedidos;
	}

	public void setMontoPedidos(float montoPedidos) {
		this.montoPedidos = montoPedidos;
	}

	public List<DetalleCompra> getDetallesPedidos() {
		return detallesPedidos;
	}

	public void setDetallesPedidos(List<DetalleCompra> detallesPedidos) {
		this.detallesPedidos = detallesPedidos;
	}

	public List<Producto> getProductosListaPedidos() {
		DAOProducto = new DAOProducto();
		productosListaPedidos = DAOProducto.getListadoProductosAct();
		return productosListaPedidos;
	}

	public void setProductosListaPedidos(List<Producto> productosListaPedidos) {
		this.productosListaPedidos = productosListaPedidos;
	}

	public float getPrecioCompraPedidos() {
		return precioCompraPedidos;
	}

	public void setPrecioCompraPedidos(float precioCompraPedidos) {
		this.precioCompraPedidos = precioCompraPedidos;
	}

	public float getPtosDescuentoPedidos() {
		return ptosDescuentoPedidos;
	}

	public void setPtosDescuentoPedidos(float ptosDescuentoPedidos) {
		this.ptosDescuentoPedidos = ptosDescuentoPedidos;
	}

	public Integer getCantidadPedidos() {
		return cantidadPedidos;
	}

	public void setCantidadPedidos(Integer cantidadPedidos) {
		this.cantidadPedidos = cantidadPedidos;
	}

	public Integer getIdProductoPedidos() {
		return idProductoPedidos;
	}

	public void setIdProductoPedidos(Integer idProductoPedidos) {
		this.idProductoPedidos = idProductoPedidos;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Integer getIdProveedorPedidos() {
		return idProveedorPedidos;
	}

	public void setIdProveedorPedidos(Integer idProveedorPedidos) {
		this.idProveedorPedidos = idProveedorPedidos;
	}

	public List<Cliente> getListaProveedoresPedidos() {
		DAOProveedor = new DAOproveedor();
		listaProveedoresPedidos = DAOProveedor.getListadoProveedoresAct();
		return listaProveedoresPedidos;
	}

	public void setListaProveedoresPedidos(List<Cliente> listaProveedoresPedidos) {
		this.listaProveedoresPedidos = listaProveedoresPedidos;
	}

	public List<Compra> getListaPedidos() {
		DAOcompra = new DAOCompra();
		listaPedidos = DAOcompra.getPedidos();
		return listaPedidos;
	}

	public void setListaPedidos(List<Compra> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}

	public List<Compra> getFilteredPedidos() {
		return filteredPedidos;
	}

	public void setFilteredPedidos(List<Compra> filteredPedidos) {
		this.filteredPedidos = filteredPedidos;
	}

	public void onProveedorPedidosCheck() {
		System.out.println("Metodo onProvvedorPedidosCheck()");
		DAOcompra = new DAOCompra();
		filteredPedidos = new ArrayList<Compra>();
		List<Compra> lista = new ArrayList<Compra>();
		if (idProveedorPedidos == 0 && estadoPedidos == "") {
			lista = DAOcompra.getPedidos();
			if (inicioPedidos != null && finalPedidos != null) {
				lista = DAOcompra.getPedidos(inicioPedidos, finalPedidos);
			}
		}
		if (idProveedorPedidos != 0 && estadoPedidos == "") {
			Cliente proveedore = new Cliente();
			proveedore.setId(idProveedorPedidos);
			lista = DAOcompra.getPedidosXProveedor(proveedore);
			if (inicioPedidos != null && finalPedidos != null) {
				lista = DAOcompra.getPedidosXProveedor(proveedore,
						inicioPedidos, finalPedidos);
			}
		}
		if (idProveedorPedidos == 0 && estadoPedidos != "") {
			Cliente proveedore = new Cliente();
			proveedore.setId(idProveedorPedidos);
			lista = DAOcompra.getPedidosXEstado(estadoPedidos);
			if (inicioPedidos != null && finalPedidos != null) {
				lista = DAOcompra.getPedidosXEstado(estadoPedidos,
						inicioPedidos, finalPedidos);
			}

		}
		if (idProveedorPedidos != 0 && estadoPedidos != "") {
			Cliente proveedore = new Cliente();
			proveedore.setId(idProveedorPedidos);
			lista = DAOcompra.getPedidos(proveedore, estadoPedidos);
			if (inicioPedidos != null && finalPedidos != null) {
				lista = DAOcompra.getPedidos(proveedore, estadoPedidos,
						inicioPedidos, finalPedidos);
			}
		}
		filteredPedidos = lista;
	}

	public void onEditPedido(Compra pedido) {
		System.out.println("Metodo onEditPedido()");
		idProductoPedidos = 0;
		cantidadPedidos = 0;
		precioCompraPedidos = 0;
		compra = pedido;
		DAOcompra = new DAOCompra();
		detallesPedidos = DAOcompra.getDetallesXCompra(pedido.getId());
		montoPedidos = pedido.getMonto();
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		montoPedidosString = formatear.format(montoPedidos);
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("confirmarPedido.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onCancelPedido(Compra pedido) {
		System.out.println("Metodo onCancelPedido()");
		FacesMessage msg = null;
		setCurrentDate(new Date());
		DAOcompra = new DAOCompra();
		pedido.setEstado("Cancelado");
		pedido.setEnabled(false);
		pedido.setFechaBaja(getCurrentDate());
		pedido.setUsuario2(usuario);
		int valor1 = DAOcompra.updateCompra(pedido);
		if (valor1 != 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito! El pedido se cancelo",
					null);
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error! El pedido no pudo cancelarse, intentelo nuevamente",
					null);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
		listaPedidos = DAOcompra.getPedidos();
		filteredPedidos = listaPedidos;
		estadoPedidos = "";
		idProveedorPedidos = 0;
	}
	
	public void onBajaCompra(Compra comp) {
		System.out.println("Metodo onBajaCompra()");
		FacesMessage msg = null;
		try {
			setCurrentDate(new Date());
			DAOcompra = new DAOCompra();
			DAOProducto = new DAOProducto();
			DAOVTA = new DAOventa();
			DAOCliente = new DAOcliente();
			DAOcc = new DAOcuentascorriente();
			cuentaDAO = new DAOCuentas();
			comp.setEstado("Cancelado");
			comp.setEnabled(false);
			comp.setFechaBaja(getCurrentDate());
			comp.setUsuario2(usuario);
			boolean alcanza = true;
			String nombProd = "";
			String precComp = "";
			Cliente prov = comp.getCliente();
			float importe = comp.getMonto();
			float saldoCC = prov.getSaldo();
			saldoCC = saldoCC - importe;
			prov.setSaldo(saldoCC);
			Cuenta cuenta = new Cuenta();
			cuenta.setCliente(prov);
			cuenta.setDetalle("Nota de Dédito - Nro de compra: " + comp.getId());
			cuenta.setFecha(new Date());
			cuenta.setHaber(comp.getMonto());
			cuenta.setMonto(comp.getMonto());
			cuenta.setSaldo(saldoCC);
			cuenta.setUsuario(usuario);			
			
			List<DetalleCompra> listaDetalle = comp.getDetalleDeCompra();
			for (DetalleCompra detalleCompra : listaDetalle) {
				int st = detalleCompra.getCantidad();
				float pc = detalleCompra.getPrecioUnitario();
				Stock stk = new Stock();
				stk = DAOcompra.getStockPorProd(pc, detalleCompra.getProducto());
				if(st > stk.getCantidad()){
					alcanza = false;
					nombProd = detalleCompra.getProducto().getNombre();
					precComp = Float.toString(pc);
				}
			}
			if(alcanza){
				int updateCC = cuentaDAO.insertar(cuenta);
				int updateProv = DAOCliente.updateCliente(prov);			
				int valor = 0;
				boolean valorS = false;
				for (DetalleCompra detalleCompra : listaDetalle) {
					Producto prod = new Producto();
					prod = detalleCompra.getProducto();
					int cant = detalleCompra.getCantidad();
					int stk = prod.getStock();
					stk = stk - cant;
					float pc = detalleCompra.getPrecioUnitario();
					Stock st = new Stock();
					st = DAOcompra.getStockPorProd(pc, prod);
					cant = st.getCantidad() - cant;
					st.setCantidad(cant);
					prod.setStock(stk);
					valor = DAOProducto.updateProducto(prod);
					valorS = DAOVTA.updateStock(st);
				}
				int valor1 = DAOcompra.updateCompra(comp);
				if(valor1 != 0 && valor != 0 && valorS && updateProv != 0 && updateCC != 0){
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito! La compra se cancelo",
							null);
				}else{
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error! La compra no pudo cancelarse, intentelo nuevamente",
							null);
				}
			}else{
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "El stock del producto: " + nombProd + " no es suficiente con el precio: " + precComp + "! "
						+ "No se pudo dar de baja la compra!", null);
			}
			listaPedidos = null;
			filteredPedidos = null;
			FacesContext.getCurrentInstance().addMessage(null, msg);
			listaPedidos = DAOcompra.getPedidos();
			filteredPedidos = listaPedidos;
			estadoPedidos = "";
			idProveedorPedidos = 0;
		} catch (Exception e) {
			e.printStackTrace();
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ocurrió un error al dar de baja la compra! Error: " + e.getMessage(), null);
			listaPedidos = null;
			filteredPedidos = null;
			FacesContext.getCurrentInstance().addMessage(null, msg);
			listaPedidos = DAOcompra.getPedidos();
			filteredPedidos = listaPedidos;
			estadoPedidos = "";
			idProveedorPedidos = 0;
		}		
	}

	public void onConfPedido(Compra pedido) {
		System.out.println("Metodo onConfPedido()");
		FacesMessage msg = null;
		DAOcompra = new DAOCompra();
		DAOProveedor = new DAOproveedor();
		DAOcc = new DAOcuentascorriente();
		DAOProducto = new DAOProducto();
		DAOCliente = new DAOcliente();
		cuentaDAO = new DAOCuentas();
		setCurrentDate(new Date());
//		Cuentascorriente cuenta = new Cuentascorriente();
		CuentasCorrientes cuentaCC = new CuentasCorrientes();
		Cuenta cuentas = new Cuenta();
		Cliente proveedor = new Cliente();
		pedido.setEstado("Confirmado");
		pedido.setFechaAlta(getCurrentDate());
		pedido.setUsuario1(usuario);
		List<DetalleCompra> listDetalles = pedido.getDetalleDeCompra();
		int valor1 = DAOcompra.updateCompra(pedido);
		proveedor = pedido.getCliente();
		float saldo = proveedor.getSaldo();
		saldo = saldo + pedido.getMonto();
		proveedor.setSaldo(saldo);
		int valorP = DAOProveedor.updateProveedor(proveedor);
		cuentas.setCliente(proveedor);
		cuentas.setFecha(new Date());
		float debe = pedido.getMonto();
		cuentas.setDebe(debe);
		cuentas.setDetalle("Compra de Productos - Nro: " + pedido.getId());
		cuentas.setIdMovimiento(pedido.getId());
		cuentas.setMonto(debe);
		cuentas.setNombreTabla("compra");
//		cuentas.setSaldo(saldo);
		cuentas.setUsuario(usuario);
//		int valorC = cuentaDAO.insertar(cuentas);
		int valorC = cuentaCC.insertarCCP(cuentas);
		boolean valorS = true;
		boolean valorProd = true;
		for (DetalleCompra detalleCompra : listDetalles) {
			Producto prod = new Producto();
			Stock stock = DAOcompra.getStockPorProd(
					detalleCompra.getPrecioUnitario(),
					detalleCompra.getProducto());
			prod = detalleCompra.getProducto();
			int cantProd = prod.getStock() + detalleCompra.getCantidad();
			prod.setStock(cantProd);
			prod.setPrecioCompra(detalleCompra.getPrecioUnitario());
			//System.out.println(stock.getId());
			if (stock.getId() != 0) {
				//System.out.println("Stock id true");
				int cant = stock.getCantidad();
				cant = cant + detalleCompra.getCantidad();
				stock.setCantidad(cant);
			} else {
				//System.out.println("stock id false");
				stock.setCantidad(detalleCompra.getCantidad());
				stock.setPrecioUnitario(detalleCompra.getPrecioUnitario());
				stock.setProducto(detalleCompra.getProducto());
			}
			int c = 0;
			int cP = 0;
			c = DAOcompra.insertUpdateStock(stock);
			cP = DAOProducto.updateProducto(prod);
			if (c == 0) {
				valorS = false;
			}
			if (cP == 0) {
				valorProd = false;
			}
			checkStockMinimo();
		}
		if (valor1 != 0 && valorP != 0 && valorC != 0
				&& valorS && valorProd) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito!",
					"La compra de productos se confirmo con Exito");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			listaPedidos = DAOcompra.getPedidos();
			filteredPedidos = listaPedidos;
			estadoPedidos = "";
			idProveedorPedidos = 0;
			generarcomprobanteCompra(pedido);
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
					"La compra de productos no se confirmo");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			listaPedidos = DAOcompra.getPedidos();
			filteredPedidos = listaPedidos;
			estadoPedidos = "";
			idProveedorPedidos = 0;
		}

	}

	public void agregarProductosPedidos() {
		System.out.println("Metodo agregarProductosPedidos()");
		//System.out.println("agregar Productos");
		if (idProductoPedidos != 0 && cantidadPedidos != 0
				&& precioCompraPedidos != 0) {
			//System.out.println("paso el if de id, cantidad, etc");
			DAOProducto = new DAOProducto();
			Producto product = new Producto();
			DetalleCompra detalle = new DetalleCompra();
			List<DetalleCompra> listAux = new ArrayList<DetalleCompra>();
			listAux = detallesPedidos;
			product = DAOProducto.getProductoXId(idProductoPedidos);
			detalle.setCantidad(cantidadPedidos);
			detalle.setCompra(compra);
			detalle.setPrecioUnitario(precioCompraPedidos);
			detalle.setProducto(product);
			float subtotal = precioCompraPedidos * cantidadPedidos;
			detalle.setSubtotal(subtotal);
			listAux.add(detalle);
			montoPedidos = montoPedidos + subtotal;
			DecimalFormat formatear = new DecimalFormat("##,##0.00");
			montoPedidosString = formatear.format(montoPedidos);
			detallesPedidos = new ArrayList<DetalleCompra>();
			detallesPedidos = listAux;
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error!",
					"Debe seleccionar producto, ingresar cantidad, y precio unitario.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void onDeleteDetallePedido(DetalleCompra detalle) {
		System.out.println("Metodo onDeleteDetallePedido()");
		List<DetalleCompra> lista = detallesPedidos;
		List<DetalleCompra> listAux = new ArrayList<DetalleCompra>();
		for (DetalleCompra detalleCompra : lista) {
			if (detalleCompra.getId() != detalle.getId()) {
				listAux.add(detalleCompra);
			} else {
				montoPedidos = montoPedidos - detalleCompra.getSubtotal();
				DecimalFormat formatear = new DecimalFormat("##,##0.00");
				montoPedidosString = formatear.format(montoPedidos);
			}
		}
		detallesPedidos = new ArrayList<DetalleCompra>();
		detallesPedidos = listAux;
	}

	public void confirmarCompra() {
		System.out.println("Metodo confirmarCompra()");
		FacesMessage msg = null;
		if (!detallesPedidos.isEmpty()) {
			DAOcompra = new DAOCompra();
			DAOProveedor = new DAOproveedor();
			DAOCliente = new DAOcliente();
			DAOcc = new DAOcuentascorriente();
			cuentaDAO = new DAOCuentas();
			DAOProducto = new DAOProducto();
			setCurrentDate(new Date());
			Cuenta cuentas = new Cuenta();
//			Cuentascorriente cuenta = new Cuentascorriente();
			Cliente proveedor = new Cliente();
			int valor = DAOcompra.deleteDetallesCompra(compra);
			if (valor != 0) {
				compra.setMonto(montoPedidos);
				compra.setFechaAlta(getCurrentDate());
				compra.setUsuario1(usuario);
				compra.setEstado("Confirmado");
				proveedor = compra.getCliente();
				int valor1 = DAOcompra.updateCompra(compra);
				float saldo = proveedor.getSaldo();
				saldo = saldo + montoPedidos;
				proveedor.setSaldo(saldo);
				int valorP = DAOProveedor.updateProveedor(proveedor);
//				cuenta.setCliente(proveedor);
//				cuenta.setFechaAlta(getCurrentDate());
//				cuenta.setIdMovimiento(compra.getId());
//				cuenta.setMonto(montoPedidos);
//				cuenta.setTipoMovimiento("COMPRA");
//				cuenta.setUsuario(usuario);
//				int valorC = DAOcc.insertCuentaCorriente(cuenta);				
				cuentas.setCliente(proveedor);
				float debe = montoPedidos;
				cuentas.setDebe(debe);
				cuentas.setDetalle("Compra de Productos - Nro: " + compra.getId());
				cuentas.setFecha(new Date());
				cuentas.setIdMovimiento(compra.getId());
				cuentas.setMonto(debe);
				cuentas.setNombreTabla("compra");
				cuentas.setUsuario(usuario);
				float sld = saldo;
				cuentas.setSaldo(sld);
				int valorC = cuentaDAO.insertar(cuentas);
				
				if (valor1 != 0 && valorP != 0 && valorC != 0) {
					boolean valorD = true;
					boolean valorS = true;
					boolean valorProd = true;
					for (DetalleCompra detalleCompra : detallesPedidos) {
						int valor2 = 0;
						valor2 = DAOcompra
								.insertDetallePedidoCompra(detalleCompra);
						if (valor2 == 0) {
							valorD = false;
						}
						Producto prod = new Producto();
						Stock stock = DAOcompra.getStockPorProd(
								detalleCompra.getPrecioUnitario(),
								detalleCompra.getProducto());
						prod = detalleCompra.getProducto();
						int cantProd = prod.getStock()
								+ detalleCompra.getCantidad();
						prod.setStock(cantProd);
						prod.setPrecioCompra(detalleCompra.getPrecioUnitario());
						if (stock.getId() != 0) {
							int cant = stock.getCantidad();
							cant = cant + detalleCompra.getCantidad();
							stock.setCantidad(cant);
						} else {
							stock.setCantidad(detalleCompra.getCantidad());
							stock.setPrecioUnitario(detalleCompra
									.getPrecioUnitario());
							stock.setProducto(detalleCompra.getProducto());
						}
						int c = 0;
						int p = 0;
						c = DAOcompra.insertUpdateStock(stock);
						p = DAOProducto.updateProducto(prod);
						if (c == 0) {
							valorS = false;
						}
						if (p == 0) {
							valorProd = false;
						}
						checkStockMinimo();
					}
					if (valorD && valorS && valorProd) {
						msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Exito!",
								"La compra de productos se confirmo con Exito");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						try {
							FacesContext.getCurrentInstance()
									.getExternalContext()
									.redirect("comprobantePedidos.xhtml");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Error",
								"La compra de productos no se confirmo");
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
				} else {
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
							"La compra de productos no se confirmo");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} else {
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
						"La compra de productos no se confirmo");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error!",
					"La lista de productos no puede estar vacia");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void volverCompra() {
		System.out.println("Metodo volverCompra()");

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("pedidos.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void generarcomprobanteCompra(Compra compra1) {
		System.out.println("Metodo generarcomprobanteCompra()");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("numero", compra1.getId());
		parameters.put("apellido", compra1.getCliente().getApellido());
		parameters.put("nombre", compra1.getCliente().getNombre());
		parameters.put("fecha", compra1.getFechaString());
		parameters.put("monto", compra1.getMontoString());

		String formaPrinteo = "attachment;filename=";
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("reportes/comprobanteCompra.jrxml");
			JasperReport jasperReport;
			jasperReport = JasperCompileManager.compileReport(reportStream);
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(
					compra1.getDetalleDeCompra());

			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			facesContext.responseComplete();
			response.setContentType("application/pdf");

			response.addHeader("Content-disposition", formaPrinteo
					+ "comprobanteCompra.pdf");
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, source);
			JasperExportManager.exportReportToPdfStream(print,
					servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generarcomprobanteCompra(String tipo) {
		System.out.println("Metodo generarcomprobanteCompra()");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("numero", compra.getId());
		parameters.put("apellido", compra.getCliente().getApellido());
		parameters.put("nombre", compra.getCliente().getNombre());
		parameters.put("fecha", compra.getFechaString());
		parameters.put("monto", compra.getMontoString());

		String formaPrinteo;
		if (tipo.equals("Compra")) {
			formaPrinteo = "inline;filename=";
		} else {
			formaPrinteo = "attachment;filename=";
		}
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("reportes/comprobanteCompra.jrxml");
			JasperReport jasperReport;
			jasperReport = JasperCompileManager.compileReport(reportStream);
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(
					detallesPedidos);

			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			facesContext.responseComplete();
			response.setContentType("application/pdf");

			response.addHeader("Content-disposition", formaPrinteo
					+ "comprobanteCompra.pdf");
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, source);
			JasperExportManager.exportReportToPdfStream(print,
					servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cancelarConfCompra() {
		System.out.println("Metodo cancelar ConfCompra()");
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("pedidos.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// -----------------------------------> Atributos Login y Usuarios<---------------------------------

	private Integer usuarioID;
	private String nombreUsuario;
	private Integer estadoUsuario = 2;
	private String username;
	private String password;
	private String emailUsuario;
	private String nombreLogin;
	private String passLogin;
	private boolean logeado;
	private Integer idUsuario;
	private boolean checkPass;
	private String newPass;
	private String oldPass;
	private String confirmNewPass;
	private String textOK;
	private boolean checkButtonPass;
	private List<Usuario> listadoUsuarios;
	private List<Usuario> filteredListadoUsuarios;
	private DAOusuario DAOuser;
	private Integer idRolUsuario;
	private List<Role> listRolesUser;
	private Usuario usuario;
	private int idRol;
	private String nombreRol;
	private List<Role> listadoRoles;
	private List<Role> filteredListadoRoles;
	private List<String> selectedVistas;
	private List<String> listadoVistas;
	private boolean vistaRubro;
	private boolean vistaProducto;
	private boolean vistaCliente;
	private boolean vistaProveedor;
	private boolean vistaLP;
	private boolean vistaUsuario;
	private boolean vistaRol;
	private boolean vistaNuevaVenta;
	private boolean vistaVentas;
	private boolean vistaNuevoPedido;
	private boolean vistaCompras;
	private boolean vistaExcel;
	private boolean vistaCCIJM;
	private boolean vistaCargaVirtual;
	private boolean vistaCompraCargaVirtual;
	private boolean vistaPagos;
	private boolean vistaCredito;
	private boolean vistaDebito;
	private boolean vistaMenuCargaVirtual;
	private boolean vistaMenuVenta;
	private boolean vistaMenuCompra;
	private boolean vistaParametros;
	private boolean vistaReportes;
	private boolean vistaCaja;
	private boolean vistaIJM;
	private boolean vistaMovimientos;
	private boolean disabledRolUser;

	public boolean isVistaMovimientos() {
		return vistaMovimientos;
	}

	public void setVistaMovimientos(boolean vistaMovimientos) {
		this.vistaMovimientos = vistaMovimientos;
	}

	public boolean isVistaCredito() {
		return vistaCredito;
	}

	public void setVistaCredito(boolean vistaCredito) {
		this.vistaCredito = vistaCredito;
	}

	public boolean isVistaDebito() {
		return vistaDebito;
	}

	public void setVistaDebito(boolean vistaDebito) {
		this.vistaDebito = vistaDebito;
	}

	public boolean isDisabledRolUser() {
		return disabledRolUser;
	}

	public void setDisabledRolUser(boolean disabledRolUser) {
		this.disabledRolUser = disabledRolUser;
	}

	public boolean isVistaIJM() {
		return vistaIJM;
	}

	public void setVistaIJM(boolean vistaIJM) {
		this.vistaIJM = vistaIJM;
	}

	public boolean isVistaCaja() {
		return vistaCaja;
	}

	public void setVistaCaja(boolean vistaCaja) {
		this.vistaCaja = vistaCaja;
	}

	public boolean isVistaReportes() {
		return vistaReportes;
	}

	public void setVistaReportes(boolean vistaReportes) {
		this.vistaReportes = vistaReportes;
	}

	public boolean isVistaMenuCargaVirtual() {
		return vistaMenuCargaVirtual;
	}

	public void setVistaMenuCargaVirtual(boolean vistaMenuCargaVirtual) {
		this.vistaMenuCargaVirtual = vistaMenuCargaVirtual;
	}

	public boolean isVistaMenuVenta() {
		return vistaMenuVenta;
	}

	public void setVistaMenuVenta(boolean vistaMenuVenta) {
		this.vistaMenuVenta = vistaMenuVenta;
	}

	public boolean isVistaMenuCompra() {
		return vistaMenuCompra;
	}

	public void setVistaMenuCompra(boolean vistaMenuCompra) {
		this.vistaMenuCompra = vistaMenuCompra;
	}

	public boolean isVistaParametros() {
		return vistaParametros;
	}

	public void setVistaParametros(boolean vistaParametros) {
		this.vistaParametros = vistaParametros;
	}

	public boolean isVistaExcel() {
		return vistaExcel;
	}

	public void setVistaExcel(boolean vistaExcel) {
		this.vistaExcel = vistaExcel;
	}

	public boolean isVistaCCIJM() {
		return vistaCCIJM;
	}

	public void setVistaCCIJM(boolean vistaCCIJM) {
		this.vistaCCIJM = vistaCCIJM;
	}

	public boolean isVistaCargaVirtual() {
		return vistaCargaVirtual;
	}

	public void setVistaCargaVirtual(boolean vistaCargaVirtual) {
		this.vistaCargaVirtual = vistaCargaVirtual;
	}

	public boolean isVistaCompraCargaVirtual() {
		return vistaCompraCargaVirtual;
	}

	public void setVistaCompraCargaVirtual(boolean vistaCompraCargaVirtual) {
		this.vistaCompraCargaVirtual = vistaCompraCargaVirtual;
	}

	public boolean isVistaPagos() {
		return vistaPagos;
	}

	public void setVistaPagos(boolean vistaPagos) {
		this.vistaPagos = vistaPagos;
	}

	public boolean isVistaRubro() {
		return vistaRubro;
	}

	public void setVistaRubro(boolean vistaRubro) {
		this.vistaRubro = vistaRubro;
	}

	public boolean isVistaProducto() {
		return vistaProducto;
	}

	public void setVistaProducto(boolean vistaProducto) {
		this.vistaProducto = vistaProducto;
	}

	public boolean isVistaCliente() {
		return vistaCliente;
	}

	public void setVistaCliente(boolean vistaCliente) {
		this.vistaCliente = vistaCliente;
	}

	public boolean isVistaProveedor() {
		return vistaProveedor;
	}

	public void setVistaProveedor(boolean vistaProveedor) {
		this.vistaProveedor = vistaProveedor;
	}

	public boolean isVistaLP() {
		return vistaLP;
	}

	public void setVistaLP(boolean vistaLP) {
		this.vistaLP = vistaLP;
	}

	public boolean isVistaUsuario() {
		return vistaUsuario;
	}

	public void setVistaUsuario(boolean vistaUsuario) {
		this.vistaUsuario = vistaUsuario;
	}

	public boolean isVistaRol() {
		return vistaRol;
	}

	public void setVistaRol(boolean vistaRol) {
		this.vistaRol = vistaRol;
	}

	public boolean isVistaNuevaVenta() {
		return vistaNuevaVenta;
	}

	public void setVistaNuevaVenta(boolean vistaNuevaVenta) {
		this.vistaNuevaVenta = vistaNuevaVenta;
	}

	public boolean isVistaVentas() {
		return vistaVentas;
	}

	public void setVistaVentas(boolean vistaVentas) {
		this.vistaVentas = vistaVentas;
	}

	public boolean isVistaNuevoPedido() {
		return vistaNuevoPedido;
	}

	public void setVistaNuevoPedido(boolean vistaNuevoPedido) {
		this.vistaNuevoPedido = vistaNuevoPedido;
	}

	public boolean isVistaCompras() {
		return vistaCompras;
	}

	public void setVistaCompras(boolean vistaCompras) {
		this.vistaCompras = vistaCompras;
	}

	public List<Usuario> getListadoUsuarios() {
		return listadoUsuarios;
	}

	public void setListadoUsuarios(List<Usuario> listadoUsuarios) {
		this.listadoUsuarios = listadoUsuarios;
	}

	public List<Usuario> getFilteredListadoUsuarios() {
		return filteredListadoUsuarios;
	}

	public void setFilteredListadoUsuarios(List<Usuario> filteredListadoUsuarios) {
		this.filteredListadoUsuarios = filteredListadoUsuarios;
	}

	public Integer getEstadoUsuario() {
		return estadoUsuario;
	}

	public void setEstadoUsuario(Integer estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}

	public List<Role> getListadoRoles() {
		return listadoRoles;
	}

	public void setListadoRoles(List<Role> listadoRoles) {
		this.listadoRoles = listadoRoles;
	}

	public List<Role> getFilteredListadoRoles() {
		return filteredListadoRoles;
	}

	public void setFilteredListadoRoles(List<Role> filteredListadoRoles) {
		this.filteredListadoRoles = filteredListadoRoles;
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	
	public String goUsuarios(){
		estadoUsuario = 2;
		listadoUsuarios = null;
		filteredListadoUsuarios = null;
		DAOuser = new DAOusuario();
		listadoUsuarios = DAOuser.getListUsuarios();
		return "usuario";
	}
	
	public String goRoles(){
		listadoRoles = null;
		filteredListadoRoles = null;
		DAOuser = new DAOusuario();
		listadoRoles = DAOuser.getListRoles();
		return "rol";
	}

	public List<String> getListadoVistas() {
		DAOuser = new DAOusuario();
		List<String> list = new ArrayList<String>();
		List<Vista> lista = new ArrayList<Vista>();
		lista = DAOuser.getListVistas();
		for (Vista vista : lista) {
			String nombre = vista.getNombre();
			list.add(nombre);
		}
		listadoVistas = list;
		return listadoVistas;
	}

	public void setListadoVistas(List<String> listadoVistas) {
		this.listadoVistas = listadoVistas;
	}

	public Integer getIdRolUsuario() {
		return idRolUsuario;
	}

	public void setIdRolUsuario(Integer idRolUsuario) {
		this.idRolUsuario = idRolUsuario;
	}

	public List<Role> getListRolesUser() {
		DAOuser = new DAOusuario();
		listRolesUser = DAOuser.getListRoles();
		return listRolesUser;
	}

	public void setListRolesUser(List<Role> listRolesUser) {
		this.listRolesUser = listRolesUser;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public DAOusuario getDAOuser() {
		return DAOuser;
	}

	public void setDAOuser(DAOusuario dAOuser) {
		DAOuser = dAOuser;
	}

	public Integer getUsuarioID() {
		return usuarioID;
	}

	public void setUsuarioID(Integer usuarioID) {
		this.usuarioID = usuarioID;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getNombreLogin() {
		return nombreLogin;
	}

	public void setNombreLogin(String nombreLogin) {
		this.nombreLogin = nombreLogin;
	}

	public String getPassLogin() {
		return passLogin;
	}

	public void setPassLogin(String passLogin) {
		this.passLogin = passLogin;
	}

	public boolean isLogeado() {
		return logeado;
	}

	public void setLogeado(boolean logeado) {
		this.logeado = logeado;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public boolean isCheckPass() {
		return checkPass;
	}

	public void setCheckPass(boolean checkPass) {
		this.checkPass = checkPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getConfirmNewPass() {
		return confirmNewPass;
	}

	public void setConfirmNewPass(String confirmNewPass) {
		this.confirmNewPass = confirmNewPass;
	}

	public String getTextOK() {
		return textOK;
	}

	public void setTextOK(String textOK) {
		this.textOK = textOK;
	}

	public boolean isCheckButtonPass() {
		return checkButtonPass;
	}

	public void setCheckButtonPass(boolean checkButtonPass) {
		this.checkButtonPass = checkButtonPass;
	}

	public List<String> getSelectedVistas() {
		return selectedVistas;
	}

	public void setSelectedVistas(List<String> selectedVistas) {
		this.selectedVistas = selectedVistas;
	}
	
	public String goIndex(){
		return "index";
	}
	
	public String modificarUsuario() {
		FacesMessage msg = null;
		DAOuser = new DAOusuario();
		Usuario user = new Usuario();
		Role rol = new Role();
		user.setId(getUsuarioID());
		user.setNombre(getNombreUsuario());
		user.setUsername(getUsername());
		Helper helper = new Helper();
		String hash = helper.EncodePassword(getNewPass());
		user.setPassword(hash);
		user.setRole(rol);
		int resultadoUsuario = DAOuser.updateUsuario(user);
		
		if (resultadoUsuario == 1) {
			usuario = new Usuario();
			usuario = DAOuser.getUsuarioPorId(idUsuario);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario: "
					+ getNombreUsuario() + " Modificado correctamente.",
					null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			setUsuarioID(null);
			setNombreUsuario(null);
			setUsername(null);
			setEmailUsuario(null);
			setNewPass(null);
			setConfirmNewPass(null);
			return "listarUsuario";
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: El Usuario: " + getNombreUsuario()
							+ " no se ha podido Modificar.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "modificarUsuario";
		}

	}

	public String nuevoUsuario() {
		setUsuarioID(null);
		setNombreUsuario(null);
		setUsername(null);
		setEmailUsuario(null);
		setNewPass(null);
		setConfirmNewPass(null);
		return "nuevoUsuario";
	}

	public void agregarUsuario() {
		FacesMessage msg = null;
		RequestContext context = RequestContext.getCurrentInstance();
		boolean valueIn = false;
		DAOuser = new DAOusuario();			
		Usuario usuario = new Usuario();
		Role rol = new Role();
		if((nombreUsuario != null || nombreUsuario != "" || !nombreUsuario.isEmpty()) && 
				(username != null || username != "" || !username.isEmpty()) && 
				(idRolUsuario != 0 || idRolUsuario != null)){		
			if(usuarioID != null){
				usuario.setNombre(nombreUsuario);
				if(confirmNewPass != null && !confirmNewPass.isEmpty() && textOK == "Ok"){
					Helper helper = new Helper();
					String hash = helper.EncodePassword(confirmNewPass);
					usuario.setPassword(hash);
				}else{
					usuario.setPassword(password);
				}			
				usuario.setUsername(username);
				rol.setId(idRolUsuario);
				usuario.setRole(rol);
				usuario.setId(usuarioID);
				int respuesta = DAOuser.updateUsuario(usuario);
				if (respuesta == 1) {
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario: "
							+ nombreUsuario + " registrado correctamente.",
							null);
					valueIn = true;	
					setNombreUsuario(null);
					setUsername(null);
					setPassword(null);
					setUsuarioID(null);
					setCheckPass(false);
					setCheckButtonPass(false);
					setIdRolUsuario(0);							
				} else {
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Error: El Usuario: " + nombreUsuario
									+ " no se pudo modificar", null);
					valueIn = false;	
					setNombreUsuario(null);
					setUsername(null);
					setPassword(null);
					setUsuarioID(null);
					setCheckPass(false);
					setCheckButtonPass(false);
				}
			}else{	
				if(confirmNewPass != null && !confirmNewPass.isEmpty() && confirmNewPass != ""  && textOK.equals("Ok")){
					Helper helper = new Helper();
					String hash = helper.EncodePassword(newPass);
					usuario.setPassword(hash);
					setCurrentDate(new Date());
					usuario.setFechaRegistro(getCurrentDate());
					usuario.setEnabled(true);
					usuario.setNombre(nombreUsuario);
					usuario.setUsername(username);
					rol.setId(idRolUsuario);
					usuario.setRole(rol);
					int respuesta = DAOuser.agregarUsser(usuario);
					if (respuesta == 1) {
						msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario: "
								+ nombreUsuario + " registrado correctamente.",
								nombreUsuario);
						valueIn = true;	
						setNombreUsuario(null);
						setUsername(null);
						setPassword(null);
						setUsuarioID(null);
						setCheckPass(false);
						setCheckButtonPass(false);
						setIdRolUsuario(0);							
					} else {
						msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Error: El Usuario: " + nombreUsuario
										+ " ya se encuentra registrado", nombreUsuario);
						valueIn = false;	
						setNombreUsuario(null);
						setUsername(null);
						setPassword(null);
						setUsuarioID(null);
						setCheckPass(false);
						setCheckButtonPass(false);
					}
				}else{
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
							"Todos los campos son obligatorios");
					valueIn = false;	
					setNombreUsuario(null);
					setUsername(null);
					setPassword(null);
					setUsuarioID(null);
					setCheckPass(false);
					setCheckButtonPass(false);
				}			
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
					"Todos los campos son obligatorios");
			valueIn = false;	
			setNombreUsuario(null);
			setUsername(null);
			setPassword(null);
			setUsuarioID(null);
			setCheckPass(false);
			setCheckButtonPass(false);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);				
		context.addCallbackParam("valueIn", valueIn);		
	}
	
	public void cancelarUsuario() {
		setUsuarioID(null);
		setUsername(null);
		setNombreUsuario(null);
		setPassword(null);
		setCheckPass(false);
		setCheckButtonPass(false);
		setIdRolUsuario(0);
	}

	public void onEditUsuario(Usuario user) {
		DAOuser = new DAOusuario();
		setUsuarioID(user.getId());
		setUsername(user.getUsername());
		setNombreUsuario(user.getNombre());
		setPassword(user.getPassword());
		setIdRolUsuario(user.getRole().getId());
	}

	public String activarUsuario(Usuario user) {
		DAOuser = new DAOusuario();
		FacesMessage msg = null;
		user.setEnabled(true);
		int resultado = DAOuser.activarUsuario(user);
		if (resultado == 1) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario: "
					+ user.getUsername() + " Activado correctamente.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			user = null;
			return "";
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: El Usuario: " + user.getUsername()
							+ " no se ha podido Activar.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}

	}
	
	public List<Usuario> getUsuariosXEstado(Integer estado) {
		DAOuser = new DAOusuario();
		filteredListadoUsuarios = null;
		List<Usuario> lista = new ArrayList<Usuario>();
		if (estado == 2) {
			lista = DAOuser.getListUsuarios();
			filteredListadoUsuarios = lista;
			setEstadoRubro(2);
		}
		if (estado == 1) {
			setEstadoRubro(0);
			lista = DAOuser.getListUsuariosAct();
			filteredListadoUsuarios = lista;
		}
		if (estado == 0) {
			setEstadoRubro(1);
			lista = DAOuser.getListUsuariosDesact();
			filteredListadoUsuarios = lista;
		}
		return filteredListadoUsuarios;
	}

	public String desactivarUsuario(Usuario user) {
		DAOuser = new DAOusuario();
		FacesMessage msg = null;
		user.setEnabled(false);
		int resultado = DAOuser.desactivarUsuario(user);
		if (resultado == 1) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario: "
					+ user.getUsername() + " Desactivado correctamente.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			user = null;
			return "";
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: El Usuario: " + user.getUsername()
							+ " no se ha podido Desactivar.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}

	}

	public void login() {
		vistaRubro = false;
		vistaProducto = false;
		vistaCliente = false;
		vistaProveedor = false;
		vistaLP = false;
		vistaUsuario = false;
		vistaRol = false;
		vistaNuevaVenta = false;
		vistaVentas = false;
		vistaNuevoPedido = false;
		vistaCompras = false;
		vistaExcel = false;
		vistaCCIJM = false;
		vistaCargaVirtual = false;
		vistaCompraCargaVirtual = false;
		vistaPagos = false;
		vistaCredito = false;
		vistaDebito = false;
		vistaMenuCargaVirtual = true;
		vistaMenuVenta = true;
		vistaMenuCompra = true;
		vistaParametros = true;
		vistaIJM = true;
		vistaMovimientos = true;
		vistaReportes = false;
		vistaCaja = false;		
		String pagina = "";
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = null;
		DAOuser = new DAOusuario();
		usuario = new Usuario();
		boolean valor = DAOuser.Autenticar(nombreLogin, passLogin);
		if (valor) {
			logeado = true;
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ " + nombreLogin,
					null);
		} else {
			logeado = false;
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error! User y Pass no validas",
					null);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("estaLogeado", logeado);
		if (logeado) {
			idUsuario = DAOuser.getIdUser(nombreLogin, passLogin);
			usuario = DAOuser.getUsuarioPorId(idUsuario);
			Role rol = usuario.getRole();
			if(rol.getId() == 1){
				checkStockMinimo();
				vistaRubro = true;
				vistaProducto = true;
				vistaCliente = true;
				vistaProveedor = true;
				vistaLP = true;
				vistaUsuario = true;
				vistaRol = true;
				vistaNuevaVenta = true;
				vistaVentas = true;
				vistaNuevoPedido = true;
				vistaCompras = true;
				vistaExcel = true;
				vistaCCIJM = true;
				vistaCargaVirtual = true;
				vistaCompraCargaVirtual = true;
				vistaPagos = true;
				vistaCredito = true;
				vistaDebito = true;
				vistaMenuCargaVirtual = true;
				vistaMenuVenta = true;
				vistaMenuCompra = true;
				vistaParametros = true;
				vistaReportes = true;
				vistaCaja = true;
				vistaIJM = true;
				pagina = "index";
			}
			if(rol.getId() == 2){
				renderedStockMinimo = false;
				pagina = "vistacliente";
				clienteVista = usuario.getCliente();
			}
			if(rol.getId() != 1 && rol.getId() != 2){
				checkStockMinimo();
				List<RolVista> vistasRoles = new ArrayList<RolVista>();
				vistasRoles = rol.getVistasDeRol();
				for (RolVista rolVista : vistasRoles) {
					Vista vista = new Vista();
					vista = rolVista.getVista();
					if(vista.getNombre().equals("Rubros")){
						vistaRubro = true;
					}
					if(vista.getNombre().equals("Productos")){
						vistaProducto = true;
					}
					if(vista.getNombre().equals("Clientes")){
						vistaCliente = true;
					}
					if(vista.getNombre().equals("Proveedores")){
						vistaProveedor = true;
					}
					if(vista.getNombre().equals("Listas de Precios")){
						vistaLP = true;
					}
					if(vista.getNombre().equals("Usuarios")){
						vistaUsuario = true;
					}
					if(vista.getNombre().equals("Roles")){
						vistaRol = true;
					}
					if(vista.getNombre().equals("Nueva Venta")){
						vistaNuevaVenta = true;
					}
					if(vista.getNombre().equals("Ventas")){
						vistaVentas = true;
					}
					if(vista.getNombre().equals("Nuevo Pedido")){
						vistaNuevoPedido = true;
					}
					if(vista.getNombre().equals("Pedidos/Compras")){
						vistaCompras = true;
					}
					if(vista.getNombre().equals("Excels")){
						vistaExcel = true;
					}
					if(vista.getNombre().equals("CuentaCorrienteIJM")){
						vistaNuevaVenta = true;
					}
					if(vista.getNombre().equals("Carga Virtual")){
						vistaVentas = true;
					}
					if(vista.getNombre().equals("Compra Carga Virtual")){
						vistaNuevoPedido = true;
					}
					if(vista.getNombre().equals("Pagos")){
						vistaPagos = true;
					}
					if(vista.getNombre().equals("Nota Credito")){
						vistaPagos = true;
					}
					if(vista.getNombre().equals("Nota Debito")){
						vistaPagos = true;
					}
					if(vista.getNombre().equals("Reportes")){
						vistaReportes = true;
					}
					if(vista.getNombre().equals("Caja")){
						vistaCaja = true;
					}
				}
				if(!vistaRubro && !vistaProducto && !vistaLP && !vistaProveedor && !vistaCliente && !vistaRol && !vistaUsuario){
					vistaParametros = false;
				}
				if(!vistaNuevoPedido && !vistaCompras){
					vistaMenuCompra = false;
				}
				if(!vistaNuevaVenta && !vistaVentas){
					vistaMenuVenta = false;
				}
				if(!vistaCargaVirtual && !vistaCompraCargaVirtual && !vistaExcel){
					vistaMenuCargaVirtual = false;
				}
				if(!vistaCCIJM && !vistaCaja){
					vistaIJM = false;
				}
				if(!vistaPagos && !vistaCredito && !vistaDebito){
					vistaMovimientos = false;
				}
				pagina = "index";
			}
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(pagina + ".xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void logout() {
		String host = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestServerName();
		int port = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestServerPort();
		StringBuffer url = new StringBuffer("http://");
		url.append(host);
		url.append(":");
		url.append(port);
		url.append("/login.xhtml");
		// //System.out.println(url.toString());
		String urlFinal = url.toString();
		FacesContext contexto = FacesContext.getCurrentInstance();
		try {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			session.invalidate();
			usuario = new Usuario();
			logeado = false;
			vistaRubro = false;
			vistaProducto = false;
			vistaCliente = false;
			vistaProveedor = false;
			vistaLP = false;
			vistaUsuario = false;
			vistaRol = false;
			vistaNuevaVenta = false;
			vistaVentas = false;
			vistaNuevoPedido = false;
			vistaCompras = false;
			contexto.getExternalContext().redirect(urlFinal);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	public void pruebaLogin() {
//		Helper helper = new Helper();
//		String encriptado = helper.EncodePassword(passLogin);
//		//System.out.println(encriptado);
//	}

	public void handleKeyEvent() {
		if (newPass.equals(confirmNewPass)) {
			textOK = "Ok";
			checkButtonPass = true;
		} else {
			textOK = "No";
			checkButtonPass = false;
		}
	}

	public void ChangePass() {
		FacesMessage msg = null;
		DAOuser = new DAOusuario();
		Usuario user = new Usuario();
		user = DAOuser.getUser(nombreLogin, oldPass);
		if(user.getId() != 0){
			if (DAOuser.cambiarPass(idUsuario, newPass)) {
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Exito al cambiarla " + nombreLogin ,null);
				checkPass = false;
				checkButtonPass = false;
				textOK = "";
			} else {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Intente nuevamente mas tarde", "Error!");
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Intente la contraseÃ±Â¡Â Â¡nterior ingresada no es correcta, Error!", null);
		}
		try {
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void settingUsuario(){
		DAOuser = new DAOusuario();
		Usuario user = new Usuario();
		user = DAOuser.getUsuarioPorId(idUsuario);
		username = user.getUsername();
		nombreUsuario = user.getNombre();
		password = user.getPassword();
		idRolUsuario = user.getRole().getId();
		if(idRolUsuario != 1){
			disabledRolUser = true;
		}else{
			disabledRolUser = false;
		}
		usuarioID = idUsuario;
	}
	
	public void guardarRol(){
		FacesMessage msg = null;
		if(nombreRol != null && nombreRol != "" && !nombreRol.isEmpty()){
			DAOuser = new DAOusuario();
			Role rol = new Role();
			RolVista rolVista = new RolVista();
			List<Vista> listVistas = new ArrayList<Vista>();
			listVistas = DAOuser.getListVistas();
			if(idRol != 0){
				rol.setId(idRol);
				rol.setNombre(nombreRol);
				int deletes = DAOuser.deleteRolVista(rol);
				int resultado = DAOuser.updateRol(rol);
				if(resultado == 1 && deletes == 1){
					for (String selectVista : selectedVistas) {
						for (Vista vista : listVistas) {
							if(selectVista.equals(vista.getNombre())){
								rolVista.setVista(vista);
								rolVista.setRole(rol);
								DAOuser.insertRolVista(rolVista);
							}
						}
					}
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito! El Rol "
							+ nombreRol + " se registro correctamente", null);
					nombreRol = null;
					idRol = 0;
					selectedVistas = new ArrayList<String>();
				}else{
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error! El Rol "
							+ nombreRol + " ya se encuentra registrado", null);
					nombreRol = null;
					idRol = 0;
					selectedVistas = new ArrayList<String>();
				}
			}else{
				rol.setNombre(nombreRol);
				int deletes = DAOuser.deleteRolVista(rol);
				int resultado = DAOuser.insertRol(rol);
				if(resultado == 1 && deletes == 1){
					for (String selectVista : selectedVistas) {
						for (Vista vista : listVistas) {
							if(selectVista.equals(vista.getNombre())){
								rolVista.setVista(vista);
								rolVista.setRole(rol);
								DAOuser.insertRolVista(rolVista);
							}
						}
					}
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito! El Rol "
							+ nombreRol + " se registro correctamente", null);
					nombreRol = null;
					idRol = 0;
					selectedVistas = new ArrayList<String>();
				}else{
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error! El Rol "
							+ nombreRol + " ya se encuentra registrado", null);
					nombreRol = null;
					idRol = 0;
					selectedVistas = new ArrayList<String>();
				}
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! Todos los campos son obligatorios", null);
			nombreRol = null;
			idRol = 0;
			selectedVistas = new ArrayList<String>();
		}
		listadoRoles = DAOuser.getListRoles();
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void cancelarRol(){
		idRol = 0;
		nombreRol = null;
		selectedVistas = new ArrayList<String>();
	}
	
	public void onEditRol(Role role){
		DAOuser = new DAOusuario();
		nombreRol = role.getNombre();
		idRol = role.getId();
		List<RolVista> lista = new ArrayList<RolVista>();
		List<String> list = new ArrayList<String>();
		lista = DAOuser.getListRolVista(role);
		for (RolVista rolVista : lista) {
			String nombre = rolVista.getVista().getNombre();
			list.add(nombre);
		}
		selectedVistas = list;
	}
	
	public void onElimRol(Role role){
		DAOuser = new DAOusuario();
		FacesMessage msg = null;
		if(!DAOuser.existeRolUsuario(role)){
			int resultado = DAOuser.deleteRol(role);
			int delete = DAOuser.deleteRolVista(role);
			if(resultado == 1 && delete == 1){
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito! Rol eliminado con exito", null);
			}else{
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error! El Rol no pudo ser eliminado, intentelo nuevamente", null);
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se puede eliminar el rol! Tiene usuarios relacionados", null);
		}		
		listadoRoles = DAOuser.getListRoles();
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	////////////////////////////////////////////////////PAGOS///////////////////////////////////////////////////////////

	private float montoPagoVenta;
	private int idVenta;
	private int formaPago;
	private String descContado;
	private boolean boolTerceros = false;
	private boolean boolContado = false;
	private float montoContado;
	private float montoTerceros;
	private DaoPago daoPago = new DaoPago();
	private static HashMap<String, Integer> listaClientes;
	private String descTerceros;
	private int idClientePago;
	private int idClientePagoTE;
	private int idClientePagoRE;
	private Date fechaPago;
	
	public String goPagos(){
		System.out.println("Metodo goPagos()");
		montoPagoVenta=0;
		idVenta=0;
		formaPago=0;
		descContado="";
		boolTerceros = false;
		boolContado = false;
		montoContado =0;
		montoTerceros=0;
		daoPago = new DaoPago();
//		listaClientes = new LinkedHashMap<String, Integer>();
		descTerceros = "";
		idClientePago=0;
		idClientePagoTE=0;
		idClientePagoRE=0;	
		fechaPago = new Date();
		return "pago";
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getDescContado() {
		return descContado;
	}

	public void setDescContado(String descContado) {
		this.descContado = descContado;
	}

	public String getDescTerceros() {
		return descTerceros;
	}

	public void setDescTerceros(String descTerceros) {
		this.descTerceros = descTerceros;
	}

	public float getMontoPagoVenta() {
		return montoPagoVenta;
	}

	public void setMontoPagoVenta(float montoPagoVenta) {
		this.montoPagoVenta = montoPagoVenta;
	}

	public int getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}

	public void setIdClientePagoRE(int idClientePagoRE) {
		this.idClientePagoRE = idClientePagoRE;
	}

	public int getIdClientePagoRE() {
		return idClientePagoRE;
	}

	public int getIdClientePagoTE() {
		return idClientePagoTE;
	}

	public void setIdClientePagoTE(int idClientePagoTE) {
		this.idClientePagoTE = idClientePagoTE;
	}

	public int getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(int formaPago) {
		this.formaPago = formaPago;
	}

	public int getIdClientePago() {
		return idClientePago;
	}

	public void setIdClientePago(int idClientePago) {
		this.idClientePago = idClientePago;
	}

	public HashMap<String, Integer> getListaClientes() {
		daoPago = new DaoPago();
		listaClientes = daoPago.getHashMapClientesAct();
		return listaClientes;
	}

	@SuppressWarnings("static-access")
	public void setListaClientes(HashMap<String, Integer> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public DaoPago getDaoPago() {
		return daoPago;
	}

	public void setDaoPago(DaoPago daoPago) {
		this.daoPago = daoPago;
	}

	public boolean isBoolTerceros() {
		return boolTerceros;
	}

	public void setBoolTerceros(boolean boolTerceros) {
		this.boolTerceros = boolTerceros;
	}

	public boolean isBoolContado() {
		return boolContado;
	}

	public void setBoolContado(boolean boolContado) {
		this.boolContado = boolContado;
	}

	public float getMontoContado() {
		return montoContado;
	}

	public void setMontoContado(float montoContado) {
		this.montoContado = montoContado;
	}

	public float getMontoTerceros() {
		return montoTerceros;
	}

	public void setMontoTerceros(float montoTerceros) {
		this.montoTerceros = montoTerceros;
	}

	public void listenerPago() {
		System.out.println("Metodo listenerPago()");
		if (formaPago == 0) {
			boolContado = false;
			boolTerceros = false;
		}
		if (formaPago == 1) {
			boolContado = true;
			boolTerceros = false;
		}
		if (formaPago == 3) {
			boolContado = false;
			boolTerceros = true;
		}

	}

	public void aceptarPagoContado() {
		System.out.println("Metodo aceptarPagoContado()");
		FacesMessage msg = null;
		if ((idClientePago != 0) && (montoContado != 0) && fechaPago != null) {
			setCurrentDate(new Date());
			Cliente roberto = new Cliente();
			Cliente cli = new Cliente();
			daoPago = new DaoPago();
			DAOcuentascorriente daoCC = new DAOcuentascorriente();
			cuentaDAO = new DAOCuentas();
			roberto = daoPago.getIJM();
			
			cli = daoPago.getCliente(idClientePago);
			cli.setSaldo(cli.getSaldo() - montoContado);
			roberto.setSaldo(roberto.getSaldo() + montoContado);

			DAOCliente = new DAOcliente();
			DAOCliente.updateCliente(roberto);
			DAOCliente.updateCliente(cli);

			Pago pago = new Pago();
			pago.setCliente1(roberto);
			pago.setCliente2(cli);
			pago.setFechaAlta(fechaPago);
			pago.setMonto(montoContado);
			pago.setDescripcion(descContado);
			pago.setId(daoPago.insertPago(pago));
			Cuentascorriente ccr = new Cuentascorriente();	

			ccr.setIdMovimiento(pago.getId());
			ccr.setTipoMovimiento("PAGO");
			ccr.setCliente(roberto);			
			ccr.setFechaAlta(fechaPago);
			ccr.setMonto(montoContado);
			ccr.setUsuario(usuario);

			//INSERCION EN CAJA			
			Caja cajaNew = new Caja();
			cajaNew.setConcepto("PAGO - RECIBIDO (" + cli.getApellido() + ")" );
			cajaNew.setFecha(new Date());
			cajaNew.setIdMovimiento(pago.getId());
			cajaNew.setMonto(montoContado);
			cajaNew.setTipo("Entrada");
			cajaNew.setUsuarioBean(usuario);
			guardarCaja(cajaNew);
			daoCC.insertCuentaCorriente(ccr);
			
			CuentasCorrientes cuentaCC = new CuentasCorrientes();
			Cuenta cuentaCliente = new Cuenta();
			cuentaCliente.setCliente(cli);
			cuentaCliente.setDetalle("Pago en efectivo - Descripción: " + descContado);
			cuentaCliente.setFecha(fechaPago);
			cuentaCliente.setHaber(montoContado);
			cuentaCliente.setIdMovimiento(pago.getId());
			cuentaCliente.setMonto(montoContado);
			cuentaCliente.setNombreTabla("pago");
			cuentaCliente.setUsuario(usuario);
			cuentaCC.insertarCCC(cuentaCliente);

			idClientePago = 0;
			montoContado = 0;
			descContado = "";
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pago asentado con exito", "Ok!");
		} else {
			msg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar un cliente o proveedor e ingresar un monto para el pago",
					"Error!");
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	// en el pago el cliente uno es el que recibe el pago y el cliente 2 es el
	// que emite el pago
	public void aceptarPagoDeposito() {
		System.out.println("Metodo aceptarPagoDeposito()");
		FacesMessage msg = null;
		if ((idClientePagoTE != 0) && (montoTerceros != 0)
				&& (idClientePagoRE != 0) && fechaPago != null) {
			Cliente emisor = new Cliente();
			Cliente receptor = new Cliente();
			setCurrentDate(new Date());
			daoPago = new DaoPago();

			float aux1, aux2;
			receptor = daoPago.getCliente(idClientePagoRE);
			emisor = daoPago.getCliente(idClientePagoTE);

			DAOCliente = new DAOcliente();
			cuentaDAO = new DAOCuentas();

			aux1 = emisor.getSaldo();
			aux2 = (aux1 - montoTerceros);
			emisor.setSaldo(aux2);
//			DAOCliente.updateCliente(emisor);

			aux1 = receptor.getSaldo();
			aux2 = (aux1 - montoTerceros);
			receptor.setSaldo(aux2);
//			DAOCliente.updateCliente(receptor);

			Pago pago = new Pago();
			pago.setCliente1(receptor);
			pago.setCliente2(emisor);
			pago.setFechaAlta(fechaPago);
			pago.setMonto(montoTerceros);
			pago.setDescripcion(descTerceros);
			pago.setId(daoPago.insertPago(pago));

			CuentasCorrientes cuentaCC = new CuentasCorrientes();
			Cuenta cuentaEmisor = new Cuenta();
			Cuenta cuentaReceptor = new Cuenta();
			
			cuentaEmisor.setCliente(emisor);
			cuentaEmisor.setDetalle("Deposito realizado a " + receptor.getApellido() + " - Descripción: " + descTerceros);
			cuentaEmisor.setFecha(fechaPago);
			cuentaEmisor.setHaber(montoTerceros);
			cuentaEmisor.setIdMovimiento(pago.getId());
			cuentaEmisor.setMonto(montoTerceros);
			cuentaEmisor.setNombreTabla("pago");
			cuentaEmisor.setUsuario(usuario);
			
			cuentaReceptor.setCliente(receptor);
			cuentaReceptor.setDetalle("Pago recibido de " + emisor.getApellido() + " - Descripción: " + descTerceros);
			cuentaReceptor.setFecha(fechaPago);
			cuentaReceptor.setHaber(montoTerceros);
			cuentaReceptor.setIdMovimiento(pago.getId());
			if(!receptor.getTipo().equals("v")){
				cuentaReceptor.setMonto(montoTerceros);
			}			
			cuentaReceptor.setNombreTabla("pago");
			cuentaReceptor.setUsuario(usuario);
			
			cuentaCC.insertarCCC(cuentaEmisor);
			cuentaCC.insertarCCP(cuentaReceptor);
			
			//INSERCION EN CAJA	
			if(emisor.getId() == 1){
				Caja cajaNew = new Caja();
				cajaNew.setConcepto("PAGO - REALIZADO (" + receptor.getApellido() + ")");
				cajaNew.setFecha(new Date());
				cajaNew.setIdMovimiento(pago.getId());
				cajaNew.setMonto(montoTerceros);
				cajaNew.setTipo("Salida");
				cajaNew.setUsuarioBean(usuario);
				guardarCaja(cajaNew);
			}			

			idClientePagoRE = 0;
			idClientePagoTE = 0;
			montoTerceros = 0;
			descTerceros = "";
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Deposito asentado con exito", "Ok!");
		} else {
			msg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar un cliente o proveedor que emitan y reciban el pago, e ingresar un monto para el pago",
					"Error!");
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void aceptarPagoContadoVenta() {
		System.out.println("Metodo aceptarPagoContadoVenta");
		//System.out.println("aceptarpagocontado");
		Cliente roberto = new Cliente();
		Cliente cli = new Cliente();
		daoPago = new DaoPago();
		DAOcuentascorriente daoCC = new DAOcuentascorriente();
		roberto = daoPago.getIJM();
		roberto.setSaldo(roberto.getSaldo() + ventaCTDO);
		DAOCliente = new DAOcliente();
		DAOCliente.updateCliente(roberto);
		Cuentascorriente ccr = new Cuentascorriente();
		
		cli = daoPago.getCliente(idClienteVenta);

		ccr.setIdMovimiento(idVenta);
		ccr.setTipoMovimiento("VENTA - " + cli.getApellido());
		ccr.setCliente(roberto);
		Date hoy = new Date();
		ccr.setFechaAlta(hoy);
		ccr.setMonto(ventaCTDO);
		ccr.setUsuario(usuario);

		daoCC.insertCuentaCorriente(ccr);
		
		//INSERCION EN CAJA
		
		Caja cajaNew = new Caja();
		cajaNew.setConcepto("VENTA - " + cli.getApellido());
		cajaNew.setFecha(hoy);
		cajaNew.setIdMovimiento(idVenta);
		cajaNew.setMonto(ventaCTDO);
		cajaNew.setTipo("Entrada");
		cajaNew.setUsuarioBean(usuario);
		guardarCaja(cajaNew);
	}

	public void aceptarPagoCCVenta() {
		System.out.println("Metodo aceptarPagoCCVenta()");
		Cliente cliente = new Cliente();
		daoPago = new DaoPago();
		DAOcuentascorriente daoCC = new DAOcuentascorriente();
		cliente = daoPago.getCliente(idClienteVenta);
		cliente.setSaldo(cliente.getSaldo() + ventaCC);
		DAOCliente = new DAOcliente();
		DAOCliente.updateCliente(cliente);

		Cuentascorriente cc = new Cuentascorriente();

		cc.setIdMovimiento(idVenta);
		cc.setTipoMovimiento("VENTA");
		cc.setCliente(cliente);
		Date hoy = new Date();
		cc.setFechaAlta(hoy);
		cc.setMonto(ventaCC);
		cc.setUsuario(usuario);

		daoCC.insertCuentaCorriente(cc);

	}

	public void cancelarPago() {
		boolTerceros = false;
		boolContado = false;
		montoContado = 0;
		montoTerceros = 0;
		idClientePago = 0;
	}

	// //////////////////////////////////////////////////////////////EXCEL//////////////////////////////////////////////////////////////////

	private UploadedFile file;
	private boolean archivoCargado = false;
	private int idPlataforma;
	private List<ExPlataforma> exPlataforma;
	private boolean selectedPlataforma = false;
	private String plataformaSelected;
	private String mjeRetorno;
	private boolean noNumerico = false;
	private List<ClientePlataforma> listadoCliPla;
	private String advertencia = "";
	private DAOExcel DAOexcel;
	
	public DAOExcel getDAOexcel() {
		return DAOexcel;
	}

	public void setDAOexcel(DAOExcel dAOexcel) {
		DAOexcel = dAOexcel;
	}

	public void onChangePlataforma(){
		System.out.println("Metodo onchangePlataforma()");
		if (idPlataforma == 0){
			advertencia ="";
			selectedPlataforma = false;
		}else{
			selectedPlataforma = true;
			advertencia ="";
		}
	}	

	public String getAdvertencia() {
		return advertencia;
	}

	public void setAdvertencia(String advertencia) {
		this.advertencia = advertencia;
	}

	public List<ClientePlataforma> getListadoCliPla() {
		return listadoCliPla;
	}

	public void setListadoCliPla(List<ClientePlataforma> listadoCliPla) {
		this.listadoCliPla = listadoCliPla;
	}

	public String getMjeRetorno() {
		return mjeRetorno;
	}

	public void setMjeRetorno(String mjeRetorno) {
		this.mjeRetorno = mjeRetorno;
	}

	public boolean isNoNumerico() {
		return noNumerico;
	}

	public void setNoNumerico(boolean noNumerico) {
		this.noNumerico = noNumerico;
	}

	public String getPlataformaSelected() {
		return plataformaSelected;
	}

	public void setPlataformaSelected(String plataformaSelected) {
		this.plataformaSelected = plataformaSelected;
	}

	public boolean isSelectedPlataforma() {
		return selectedPlataforma;
	}

	public void setSelectedPlataforma(boolean selectedPlataforma) {
		this.selectedPlataforma = selectedPlataforma;
	}

	public List<ExPlataforma> getExPlataforma() {
		return exPlataforma;
	}

	public void setExPlataforma(List<ExPlataforma> exPlataforma) {
		this.exPlataforma = exPlataforma;
	}

	public String goExcel() {
		System.out.println("Metodo goExcel()");
		file = null;
		archivoCargado = false;
		idPlataforma = 0;
		exPlataforma = new ArrayList<ExPlataforma>();
		selectedPlataforma = false;
		plataformaSelected = "";
		advertencia = "";
		return "upload";
	}

	public int getIdPlataforma() {
		return idPlataforma;
	}

	public void setIdPlataforma(int idPlataforma) {
		this.idPlataforma = idPlataforma;
	}

	public boolean isArchivoCargado() {
		return archivoCargado;
	}

	public void setArchivoCargado(boolean archivoCargado) {
		this.archivoCargado = archivoCargado;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void upload(FileUploadEvent event) {
		System.out.println("Metodo upload()");
		try {
			file = event.getFile();

		
			copyFile(file.getFileName(), file.getInputstream());
			archivoCargado = true;
			FacesMessage message = new FacesMessage("Exito", event.getFile()
					.getFileName() + " fue cargado.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyFile(String fileName, InputStream in) {
		System.out.println("Metodo copyFile()");
		try {

//			String destinoArmado = System.getProperty("user.home")
//					+ System.getProperty("file.separator");
//
//			String rutaArchivo = System.getProperty("user.home")
//					+ System.getProperty("file.separator") + fileName;
			
			ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String path = contexto.getRealPath("/excels_");
			String nom = fileName;
			int largoNombre = 0;
			largoNombre = nom.length();
			char letra = nom.charAt(largoNombre - 1);
			
			String nombre_archivo = "";
			if(idPlataforma == 1){
				nombre_archivo = "pinweb";
			}
			if(idPlataforma == 2){
				nombre_archivo = "revirtual";
			}
			if(idPlataforma == 4){
				nombre_archivo = "telerecarga";
			}
			if(idPlataforma == 5){
				nombre_archivo = "codigo_pagos";
			}
			if(idPlataforma == 6){
				nombre_archivo = "pinweb_mercader444";
			}
			if(letra == 's'){
				nombre_archivo = nombre_archivo + ".csv";
			}
			if(letra == 'v'){
				nombre_archivo = nombre_archivo + ".xls";
			}

			OutputStream out = new FileOutputStream(new File(path
					+ nombre_archivo));
			ruta = path + nombre_archivo;

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();
		} catch (IOException e) {
		}
	}

	public String importFile() {
		System.out.println("Metodo importFile()");
		plataformaSelected = "";
		if (archivoCargado) {
			String nom;			
			nom = file.getFileName();
			int largoNombre = 0;
			largoNombre = nom.length();
			char letra;
			letra = nom.charAt(largoNombre - 1);
			if (letra == 's') {
				if(idPlataforma == 1){
					exPlataforma = readExcel1();
					plataformaSelected = "venta_pinweb";
				}				
				if (idPlataforma == 2) {
					exPlataforma = readExcel2();
					plataformaSelected = "sistema_revirtual";
				}
				if (idPlataforma == 4) {
					exPlataforma = readExcel4();
					plataformaSelected = "telerecarga_reporte";
				}
				if (idPlataforma == 6) {
					exPlataforma = readExcel6();
					plataformaSelected = "pinweb_mercader444";
				}
			} else {
				if (letra == 'v') {
					if (idPlataforma == 1) {
						try {
							exPlataforma = readCSVid1();
							plataformaSelected = "venta_pinweb";
						} catch (Exception e) {
							// Auto-generated catch block esta parte de aca es
							// codigo generado para que me deje llamar al metodo
							e.printStackTrace();
						}
					}
					if (idPlataforma == 5) {
						try {
							exPlataforma = readCSVid5();
							plataformaSelected = "codigo_pagos";
						} catch (Exception e) {
							// Auto-generated catch block esta parte de aca es
							// codigo generado para que me deje llamar al metodo
							e.printStackTrace();
						}
					}
					if (idPlataforma == 6) {
						try {
							exPlataforma = readCSVid6();
							plataformaSelected = "pinweb_mercader444";
						} catch (Exception e) {
							// Auto-generated catch block esta parte de aca es
							// codigo generado para que me deje llamar al metodo
							e.printStackTrace();
						}
					}
				}
			}			
			if (validarExcel(exPlataforma)) {
//				System.out.println("llega hasta validar plataforma y pasa");
				for (ExPlataforma ex : exPlataforma) {
					DAOexcel = new DAOExcel();
					DAOCliente = new DAOcliente();
					DAOcc = new DAOcuentascorriente();
					cuentaDAO = new DAOCuentas();
					CuentasCorrientes cuentaCC = new CuentasCorrientes();
					Cliente cli = new Cliente();
					CargaVirtual cv = new CargaVirtual();
					MovimientoVirtual mv = new MovimientoVirtual();
					cli = DAOexcel.getClientexUsuarioPlataforma(ex.getUsuario(), plataformaSelected);					
					if (cli != null) {
//						System.out.println(ex.getUsuario() + " - " + ex.getStringMonto());
//						System.out.println(" ");						
						mv.setCliente(cli);
						mv.setFecha(ex.getDateFecha());
						mv.setMonto(ex.getDoubleMonto());
						cv = DAOexcel.getCargaVirtual(ex.getPlataforma());						
//						System.out.println("Monto de plataforma - " + cv.getCantidadMontoString());						
						cv.setCantidadMonto(cv.getCantidadMonto()
								- ex.getDoubleMonto());
//						System.out.println("Monto plataforma modificada - " + cv.getCantidadMontoString());						
						DAOexcel.updateCargaVirtual(cv);						
						// ------------------//
//						System.out.println("");
//						System.out.println("carga virtual a guardar");
//						System.out.println(mv.getMontoString());
//						System.out.println(mv.getCliente().getNombre());						
						mv.setCargaVirtual(cv);
						DAOexcel.insertMovimientoVirtual(mv);
						// bloque para generar el movimiento						
						cli.setSaldo(cli.getSaldo() + ex.getDoubleMonto());						
						DAOCliente.updateCliente(cli);
						Cuenta cuenta = new Cuenta();
						cuenta.setCliente(cli);
						cuenta.setDebe(ex.getDoubleMonto());
						cuenta.setDetalle("Compra de carga de carga virtual - Plataforma: " + ex.getPlataforma());
						cuenta.setFecha(ex.getDateFecha());
						cuenta.setIdMovimiento(DAOexcel.getUltimoIdMovimiento());
						cuenta.setMonto(ex.getDoubleMonto());
						cuenta.setNombreTabla("movimiento_virtual");
						cuenta.setUsuario(usuario);
//						insertarCuentaCorriente(cuenta);
						cuentaCC.insertarCCC(cuenta);
					} else {
//						System.out.println("cliente nulo");
					}					
				}				
				redirectExcel("Archivo cargado y cambios aplicados");
				return null;
			} else {				
				redirectExcel(mjeRetorno);
				return null;
			}
		} else {			
			redirectExcel("Debe cargar un archivo");
			return null;
		}
	}

	public boolean validarExcel(List<ExPlataforma> listaex) {
		System.out.println("Metodo validarExcel()");
		boolean v1 = false, v2 = true, v3 = true;

		if (listaex.isEmpty() || listaex == null) {
			v1 = false;
		} else {
			v1 = true;
		}
		List<ClientePlataforma> lCliPla = new ArrayList<ClientePlataforma>();
		DAOExcel daoe = new DAOExcel();
		lCliPla = daoe.getListadoCliPla(plataformaSelected);
		String rechazados = "";
		mjeRetorno = "";
		int contador = 0;
		for (ExPlataforma ex : listaex) {
			boolean existe = false;
			for (ClientePlataforma cp : lCliPla) {
				if(cp.getUsuario().trim().equals(ex.getUsuario().trim())) {
					existe = true;					
				}
			}			
			if (!existe){
				contador ++;
				rechazados = rechazados + " " + ex.getUsuario();
			}
		}		
		if (contador > 0){
			v3 = false;
		}		
		for (ExPlataforma ex : listaex) {
			if (ex.getPlataforma() == "" || ex.getPlataforma() == null) {
				v1 = false;
			}
			if (ex.getUsuario() == "" || ex.getUsuario() == null) {
				v1 = false;
			}

			if (ex.getDoubleMonto() == 0) {
				v1 = false;
			}

			if (ex.getDateFecha() == null) {
				v1 = false;
			}
		}
		if (v1) {
			if (v2) {
				if (v3) {
					listadoCliPla = new ArrayList<ClientePlataforma>();
					listadoCliPla = lCliPla;
					return true;
				} else {
					mjeRetorno = "Error " +  rechazados;
					return false;
				}
			} else {
				mjeRetorno = "Faltan campos en el archivo";
				return false;
			}
		} else {
			mjeRetorno = "El archivo esta vacio";
			if(noNumerico){
				mjeRetorno = mjeRetorno + ", Valor de campo incorrecto!";
			}
			return false;
		}
	}
	
	private String ruta;
	
	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
	public void redirectExcel(String mje){
		System.out.println("Metodo redirectExcel()");
		advertencia = mje;
		ruta = "";
		file = null;
		archivoCargado = false;
		idPlataforma = 0;
		exPlataforma = new ArrayList<ExPlataforma>();
		selectedPlataforma = false;
		plataformaSelected ="";
		mjeRetorno ="";
		listadoCliPla = new ArrayList<ClientePlataforma>();
		
		 try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("upload.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	private static boolean isNumeric(String cadena){
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}

	public List<ExPlataforma> readCSVid1() throws Exception {
		System.out.println("Metodo readCSVid1()");
		List<ExPlataforma> listaEx = new ArrayList<ExPlataforma>();

		ExPlataforma ex = new ExPlataforma();
		CsvReader reader = new CsvReader(ruta);
		noNumerico = false;
		try {
			
			int cont = 0;
			while (reader.readRecord()) {

				
				if (cont != 0){
					String[] arreglo;
					arreglo = null;
					String renglon;
					renglon = null;
					renglon = reader.get(0);
					arreglo = renglon.split(",");
					
					
					ex = new ExPlataforma();
					String tipo = arreglo[1].trim();
//					if ((tipo.indexOf("mercader333") != -1) || (tipo.indexOf("c c") != -1) || (tipo.indexOf("C c") != -1) || 
//							(tipo.indexOf("ACREDITACION AUTOMATICA DE SALDO") != -1) || (tipo.indexOf("C C") != -1)) {
					if(tipo.indexOf("mercader333") != 1){
						ex.setPlataforma("Pinweb");
						System.out.println(arreglo[0].trim());
						ex.setStringFecha(arreglo[0].trim());
						String montoS = arreglo[5].trim();
						montoS = montoS.replace(".", "");
						montoS = montoS.replace(",", ".");
						montoS = montoS.substring(1, montoS.length());
						if(isNumeric(montoS)){
							ex.setStringMonto(arreglo[5].trim());
						}else{
							ex.setStringMonto("0.0");
							noNumerico = true;
						}
						ex.setUsuario(arreglo[4].trim());
						ex.convertir();
						listaEx.add(ex);
					}
				}
				cont ++;
			}
			reader.close();
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(noNumerico){
			listaEx = null;
			listaEx = new ArrayList<ExPlataforma>();
		}

		return listaEx;

	}
	
	public List<ExPlataforma> readCSVid6() throws Exception {
		System.out.println("Metodo readCSVid6()");
		List<ExPlataforma> listaEx = new ArrayList<ExPlataforma>();
		ExPlataforma ex = new ExPlataforma();
		CsvReader reader = new CsvReader(ruta);
		noNumerico = false;
		try {			
			int cont = 0;
			while (reader.readRecord()) {				
				if (cont != 0){
					String[] arreglo;
					arreglo = null;
					String renglon;
					renglon = null;
					renglon = reader.get(0);
					arreglo = renglon.split(",");				
					ex = new ExPlataforma();
					String tipo = arreglo[1].trim();
//					if ((tipo.indexOf("mercader333") != -1) || (tipo.indexOf("c c") != -1) || (tipo.indexOf("C c") != -1) || 
//							(tipo.indexOf("ACREDITACION AUTOMATICA DE SALDO") != -1) || (tipo.indexOf("C C") != -1)) {
					if(tipo.indexOf("mercader444") != 1){
						ex.setPlataforma("Pinweb Mercader444");						
						ex.setStringFecha(arreglo[0].trim());
						String montoS = arreglo[5].trim();
						montoS = montoS.replace(".", "");
						montoS = montoS.replace(",", ".");
						montoS = montoS.substring(1, montoS.length());
						if(isNumeric(montoS)){
							ex.setStringMonto(arreglo[5].trim());
						}else{
							ex.setStringMonto("0.0");
							noNumerico = true;
						}
						ex.setUsuario(arreglo[4].trim());
						ex.convertir();
						listaEx.add(ex);
					}
				}
				cont ++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		if(noNumerico){
			listaEx = null;
			listaEx = new ArrayList<ExPlataforma>();
		}
		return listaEx;
	}
	
	public List<ExPlataforma> readExcel1() { //Metodo creado para que cargue excels de pinweb
		System.out.println("Metodo readExcel1()");

		ExPlataforma ex = new ExPlataforma();
		List<ExPlataforma> listaEx = new ArrayList<ExPlataforma>();
		noNumerico = false;
		try {
			Workbook workbook = Workbook.getWorkbook(new File(ruta));
			Sheet sheet = workbook.getSheet(0);
			for (int fila = 1; fila < sheet.getRows(); fila++) {
				ex = new ExPlataforma();
				for (int columna = 0; columna < sheet.getColumns(); columna++) {
					if (columna == 0) {
						String[] arreglo;
						arreglo = null;
						String renglon = sheet.getCell(columna, fila)
								.getContents();
						arreglo = renglon.split(",");
						String tipo = arreglo[1].trim();
						if(tipo.indexOf("mercader333") != 1){
							ex.setPlataforma("Pinweb");
							System.out.println(arreglo[0].trim());
							ex.setStringFecha(arreglo[0].trim());
							String montoS = arreglo[5].trim();
							montoS = montoS.replace(".", "");
							montoS = montoS.replace(",", ".");
							montoS = montoS.substring(1, montoS.length());
							if(isNumeric(montoS)){
								ex.setStringMonto(arreglo[5].trim());
							}else{
								ex.setStringMonto("0.0");
								noNumerico = true;
							}							
							ex.setUsuario(arreglo[4].trim());
							ex.convertir();
							listaEx.add(ex);
						}
					}
				}
			}

			File f = new File(ruta);
			f.delete();

		} catch (BiffException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		if(noNumerico){
			listaEx = null;
			listaEx = new ArrayList<ExPlataforma>();
		}

		return listaEx;
	}
	
	public List<ExPlataforma> readExcel6() { //Metodo creado para que cargue excels de pinweb_mercader444
		System.out.println("Metodo readExcel1()");

		ExPlataforma ex = new ExPlataforma();
		List<ExPlataforma> listaEx = new ArrayList<ExPlataforma>();
		noNumerico = false;
		try {
			Workbook workbook = Workbook.getWorkbook(new File(ruta));
			Sheet sheet = workbook.getSheet(0);
			for (int fila = 1; fila < sheet.getRows(); fila++) {
				ex = new ExPlataforma();
				for (int columna = 0; columna < sheet.getColumns(); columna++) {
					if (columna == 0) {
						String[] arreglo;
						arreglo = null;
						String renglon = sheet.getCell(columna, fila)
								.getContents();
						arreglo = renglon.split(",");
						String tipo = arreglo[1].trim();
						if(tipo.indexOf("mercader444") != 1){
							ex.setPlataforma("Pinweb Mercader444");
							ex.setStringFecha(arreglo[0].trim());
							String montoS = arreglo[5].trim();
							montoS = montoS.replace(".", "");
							montoS = montoS.replace(",", ".");
							montoS = montoS.substring(1, montoS.length());
							if(isNumeric(montoS)){
								ex.setStringMonto(arreglo[5].trim());
							}else{
								ex.setStringMonto("0.0");
								noNumerico = true;
							}							
							ex.setUsuario(arreglo[4].trim());
							ex.convertir();
							listaEx.add(ex);
						}
					}
				}
			}

			File f = new File(ruta);
			f.delete();

		} catch (BiffException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		if(noNumerico){
			listaEx = null;
			listaEx = new ArrayList<ExPlataforma>();
		}

		return listaEx;
	}

	public List<ExPlataforma> readCSVid5() throws Exception {
		System.out.println("Metodo readCSVid5()");

		ExPlataforma ex = new ExPlataforma();
		List<ExPlataforma> listaEx = new ArrayList<ExPlataforma>();
		CsvReader reader = new CsvReader(ruta);
		noNumerico = false;
		try {
			int cont = 0;
			while (reader.readRecord()) {
				
				
				
				if (cont != 0){
					String[] arreglo;
					arreglo = null;
					String renglon;
					renglon = null;
					renglon = reader.get(0);
					
					if (renglon.indexOf(";") != -1){
					
						arreglo = renglon.split("\\;");
						
						
					
						ex = new ExPlataforma();
						if (arreglo.length == 6){
							String tipo = arreglo[2].trim();
							//System.out.println(tipo);
								if (tipo.indexOf("ROBERTITO PEREZ MERCADER") != -1) {
				
									ex.setPlataforma("Codigo Pagos");
									ex.setStringFecha(arreglo[0].trim());
									String montoS = arreglo[5].trim();
									montoS = montoS.replace(".", "");
									if(isNumeric(montoS)){
										ex.setStringMonto(arreglo[5].trim());
									}else{
										ex.setStringMonto("0.0");
										noNumerico = true;
									}									
									int inicio = arreglo[4].trim().indexOf("-");
									ex.setUsuario(arreglo[4].trim().substring(inicio + 1,
											arreglo[4].trim().length()));
									ex.convertir();
				
									listaEx.add(ex);
								}
							}
					}
				}
				cont ++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(noNumerico){
			listaEx = null;
			listaEx = new ArrayList<ExPlataforma>();
		}

		return listaEx;

	}

	public List<ExPlataforma> readExcel4() {
		System.out.println("Metodo readExcel4()");
		
		ExPlataforma ex = new ExPlataforma();
		List<ExPlataforma> listaEx = new ArrayList<ExPlataforma>();
		String valor;
		noNumerico = false;
		try {
			Workbook book = Workbook.getWorkbook(new File(ruta));
			Sheet sheet = book.getSheet(0);
			for (int fila = 1; fila < sheet.getRows(); fila++) {
				ex = new ExPlataforma();

				for (int columna = 0; columna < sheet.getColumns(); columna++) {
					valor = sheet.getCell(columna, fila).getContents();
					ex.setPlataforma("Telerecargas");
					switch (columna) {
					case 5:
						ex.setStringFecha(valor);
						break;
					case 3:
						ex.setUsuario(valor);
						break;
					case 7:
						ex.setStringMonto(valor);
						break;
					default:
						break;
					}
				}
				ex.convertir();
				listaEx.add(ex);

			}

		} catch (BiffException e) {
			advertencia = "Archivo no compatible";
			
		} catch (IOException e) {

			advertencia = "Archivo no compatible";
		}
		
		return listaEx;
	}

	public List<ExPlataforma> readExcel2() { // ver en este metodo tema
	// de los contadores
	// para que cargue bien
	// la info pero por ahi
	// va la idea
		
		System.out.println("Metodo readExcel2()");
		String valor;

		ExPlataforma ex = new ExPlataforma();
		List<ExPlataforma> listaEx = new ArrayList<ExPlataforma>();
		noNumerico = false;
		try {
			Workbook workbook = Workbook.getWorkbook(new File(ruta));
			Sheet sheet = workbook.getSheet(0);
			for (int fila = 1; fila < sheet.getRows(); fila++) {
				ex = new ExPlataforma();
				for (int columna = 0; columna < sheet.getColumns(); columna++) {
					if (columna == 2) {
						String tipo = sheet.getCell(columna, fila)
								.getContents();
						int i, f;
						i = tipo.indexOf("(");
						f = tipo.indexOf(")");
						if ((tipo.indexOf("por cobro") != -1)
								&& (i != -1 && f != -1)) {
							for (int columna2 = 0; columna2 < sheet
									.getColumns(); columna2++) {
								ex.setPlataforma("Re Virtual");
								switch (columna2) {
								case 0:

									valor = sheet.getCell(columna2, fila)
											.getContents();
									ex.setStringFecha(valor.trim());
									break;
								case 2:
									valor = sheet.getCell(columna2, fila)
											.getContents();
									int inicio = valor.indexOf("(");
									int fin = valor.indexOf(")");
									ex.setUsuario(valor.substring(inicio + 1,
											fin));
									break;
								case 4:

									valor = sheet.getCell(columna2, fila)
											.getContents();
									ex.setStringMonto(valor.trim());									
									break;
								default:
									break;
								}
							}
							ex.convertir();
							listaEx.add(ex);
						}
					}
				}
			}

			File f = new File(ruta);
			f.delete();

		} catch (BiffException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return listaEx;
	}

	public void cancelForm() {
//		UploadedFile file = null;
		archivoCargado = false;
		ruta = null;
		idPlataforma = 0;
		advertencia = "";
		selectedPlataforma = false;
	}
	
	public String goUpload(){
		archivoCargado = false;
		ruta = null;
		idPlataforma = 0;
		advertencia = "";
		exPlataforma = new ArrayList<ExPlataforma>();
		selectedPlataforma = false;
		return "upload";
	}
	
	//////////////////////////////////////////////////CARGAS VIRTUALES//////////////////////////////////////////////////////////

	private CargaVirtual cargaVirtual = new CargaVirtual();
	private Cliente cvParaFiltro;
	private Cliente plataf;
	private CompraCargavirtual compraCargaVirtual = new CompraCargavirtual();
	private List<CargaVirtual> listadoCargas;
	private List<CargaVirtual> filteredListadoCargas;
	private List<CompraCargavirtual> listadoCompraCargas;
	private List<CompraCargavirtual> filteredListadoCompraCargas;
	private DAOCargaVirtual DAOcargaVirtual;
	private int estadoCarga = 2;
	private int estadoCompraCarga = 2;
	private int cantidadCCCV;
	private int tipoCV;
	private Integer idCVCompra;
	private Date inicioCV;
	private Date finalCV;
	private List<Cuentascorriente> listaCVCuentas;
	private List<Cuentascorriente> filteredCVCuentas;
	private float cantidadMontoCV;
	private float montoTotalCCCV;
	private String montoTotalCCCVString;
	private String conceptoCV;

	public String getConceptoCV() {
		return conceptoCV;
	}

	public void setConceptoCV(String conceptoCV) {
		this.conceptoCV = conceptoCV;
	}

	public int getTipoCV() {
		return tipoCV;
	}

	public void setTipoCV(int tipoCV) {
		this.tipoCV = tipoCV;
	}

	public float getCantidadMontoCV() {
		return cantidadMontoCV;
	}

	public void setCantidadMontoCV(float cantidadMontoCV) {
		this.cantidadMontoCV = cantidadMontoCV;
	}

	public float getMontoTotalCCCV() {
		return montoTotalCCCV;
	}

	public void setMontoTotalCCCV(float montoTotalCCCV) {
		this.montoTotalCCCV = montoTotalCCCV;
	}

	public String getMontoTotalCCCVString() {
		return montoTotalCCCVString;
	}

	public void setMontoTotalCCCVString(String montoTotalCCCVString) {
		this.montoTotalCCCVString = montoTotalCCCVString;
	}

	public int getCantidadCCCV() {
		return cantidadCCCV;
	}

	public void setCantidadCCCV(int cantidadCCCV) {
		this.cantidadCCCV = cantidadCCCV;
	}

	public Cliente getPlataf() {
		return plataf;
	}

	public void setPlataf(Cliente plataf) {
		this.plataf = plataf;
	}

	public Cliente getCvParaFiltro() {
		return cvParaFiltro;
	}

	public void setCvParaFiltro(Cliente cvParaFiltro) {
		this.cvParaFiltro = cvParaFiltro;
	}

	public List<Cuentascorriente> getListaCVCuentas() {
		return listaCVCuentas;
	}

	public void setListaCVCuentas(List<Cuentascorriente> listaCVCuentas) {
		this.listaCVCuentas = listaCVCuentas;
	}

	public List<Cuentascorriente> getFilteredCVCuentas() {
		return filteredCVCuentas;
	}

	public void setFilteredCVCuentas(List<Cuentascorriente> filteredCVCuentas) {
		this.filteredCVCuentas = filteredCVCuentas;
	}

	public Date getInicioCV() {
		return inicioCV;
	}

	public void setInicioCV(Date inicioCV) {
		this.inicioCV = inicioCV;
	}

	public Date getFinalCV() {
		return finalCV;
	}

	public void setFinalCV(Date finalCV) {
		this.finalCV = finalCV;
	}

	public Integer getIdCVCompra() {
		return idCVCompra;
	}

	public void setIdCVCompra(Integer idCVCompra) {
		this.idCVCompra = idCVCompra;
	}

	public CompraCargavirtual getCompraCargaVirtual() {
		return compraCargaVirtual;
	}

	public void setCompraCargaVirtual(CompraCargavirtual compraCargaVirtual) {
		this.compraCargaVirtual = compraCargaVirtual;
	}

	public List<CompraCargavirtual> getListadoCompraCargas() {
		return listadoCompraCargas;
	}

	public void setListadoCompraCargas(List<CompraCargavirtual> listadoCompraCargas) {
		this.listadoCompraCargas = listadoCompraCargas;
	}

	public List<CompraCargavirtual> getFilteredListadoCompraCargas() {
		return filteredListadoCompraCargas;
	}

	public void setFilteredListadoCompraCargas(
			List<CompraCargavirtual> filteredListadoCompraCargas) {
		this.filteredListadoCompraCargas = filteredListadoCompraCargas;
	}

	public int getEstadoCompraCarga() {
		return estadoCompraCarga;
	}

	public void setEstadoCompraCarga(int estadoCompraCarga) {
		this.estadoCompraCarga = estadoCompraCarga;
	}

	public CargaVirtual getCargaVirtual() {
		return cargaVirtual;
	}

	public void setCargaVirtual(CargaVirtual cargaVirtual) {
		this.cargaVirtual = cargaVirtual;
	}

	public List<CargaVirtual> getListadoCargas() {
		return listadoCargas;
	}

	public void setListadoCargas(List<CargaVirtual> listadoCargas) {
		this.listadoCargas = listadoCargas;
	}

	public List<CargaVirtual> getFilteredListadoCargas() {
		return filteredListadoCargas;
	}

	public void setFilteredListadoCargas(List<CargaVirtual> filteredListadoCargas) {
		this.filteredListadoCargas = filteredListadoCargas;
	}

	public DAOCargaVirtual getDAOcargaVirtual() {
		return DAOcargaVirtual;
	}

	public void setDAOcargaVirtual(DAOCargaVirtual dAOcargaVirtual) {
		DAOcargaVirtual = dAOcargaVirtual;
	}

	public int getEstadoCarga() {
		return estadoCarga;
	}

	public void setEstadoCarga(int estadoCarga) {
		this.estadoCarga = estadoCarga;
	}
	
	public String goCargaVirtual(){
		estadoCarga = 2;
		listadoCargas = null;
		filteredListadoCargas = null;
		DAOcargaVirtual = new DAOCargaVirtual();
		listadoCargas = DAOcargaVirtual.getListadoCargas();
		return "cargaVirtual"; 
	}
	
	public String goCompraCargaVirtual(){
		estadoCompraCarga = 2;
		listadoCompraCargas = new ArrayList<CompraCargavirtual>();
		DAOcargaVirtual = new DAOCargaVirtual();
		listadoCompraCargas = DAOcargaVirtual.getListadoCompraCargas();
		return "compraCargaVirtual";
	}
	
	public String onViewCCCV(Cliente proveedore) {
		System.out.println("Metodo onViewCCCV()");
		plataf = new Cliente();
		plataf = proveedore;
		DAOcc = new DAOcuentascorriente();
		FacesMessage msg = null;
		List<Cuentascorriente> lista = DAOcc.getListaCCProveedor(proveedore);
		Integer cantidad = 0;
		float monto = 0;
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		volverVistaCC = "cuentacorrienteCV";
		if (lista.isEmpty()) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Error: El Proveedor: " + proveedore.getNombre() + " "
							+ proveedore.getApellido()
							+ " no posee cuenta corriente.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		} else {
			for (Cuentascorriente cuentascorriente : lista) {
				cantidad++;
				monto = cuentascorriente.getMonto() + monto;				
			}
			String valor = formatear.format(monto);
			setPlataf(proveedore);
			setMontoTotalCCCV(monto);
			setMontoTotalCCCVString(valor);
			setCantidadCCCV(cantidad);
			setListaCVCuentas(lista);
			return "cuentacorrienteCV";
		}
	}
	
	public String onViewDetCCCV(Cuentascorriente cuenta){
		System.out.println("Metodo onViewCCCV()");
		DAOcargaVirtual = new DAOCargaVirtual();
		daoPago = new DaoPago();
		CompraCargavirtual compraCarga = new CompraCargavirtual();
		Pago pagoCueCo = new Pago();
		if(cuenta.getTipoMovimiento().startsWith("COMPRA")){
			ventaCuCo = false;
			compraCuCo = false;
			pagoCuCo = false;
			notaCredCuCo = false;
			compraCVCuCo = true;
			compraCarga = DAOcargaVirtual.getCompraPorId(cuenta.getIdMovimiento());			
			compraCargaVirtualCuCo = new CompraCargavirtual();
			compraCargaVirtualCuCo = compraCarga;
		}else{
			ventaCuCo = false;
			compraCuCo = false;
			pagoCuCo = true;
			notaCredCuCo = false;
			compraCVCuCo = false;
			pagoCueCo = daoPago.getPagoPorId(cuenta.getIdMovimiento());
			pagoCCApellido1 = pagoCueCo.getCliente1().getApellido();
			pagoCCApellido2 = pagoCueCo.getCliente2().getApellido();
			pagoCCFecha = pagoCueCo.getFechaString();
			pagoCCNro = Integer.toString(pagoCueCo.getId());
			pagoCCMonto = pagoCueCo.getMontoString();
			pagoCCDescripcion = pagoCueCo.getDescripcion();
		}
		return "itemCuentaCorriente";
	}
	
	public void imprimirReporteCCCV(){
		reporteCC(plataf);
	}	
	
	public void onfilteredDateCCVirtual(){
		System.out.println("Metodo onfilteredDateCCVirtual()");
		if(inicioCV != null && finalCV != null){			
			listaCVCuentas = DAOcc.getListaCCProveedor(plataf, inicioCV, finalCV);
			filteredCVCuentas = listaCVCuentas;			
		}		
	}

	public void guardarCarga() {
		System.out.println("Metodo guardarCarga()");
		FacesMessage msg = null;
		DAOcargaVirtual = new DAOCargaVirtual();
		DAOcc = new DAOcuentascorriente();
		cuentaDAO = new DAOCuentas();
		if(cargaVirtual.getNombre() != null && cargaVirtual.getNombre() != "" && !cargaVirtual.getNombre().isEmpty() && cantidadMontoCV != 0){
//			Cuentascorriente cuenta = new Cuentascorriente();
			Cuenta cuenta = new Cuenta();
			setCurrentDate(new Date());
			String concept;
			float montoForm = cantidadMontoCV;
			float monto = cargaVirtual.getCantidadMonto();
			if(tipoCV == 2){
				montoForm = montoForm * (-1);
				concept = "Debito ";
			}else{
				concept = "Acreditacion ";
			}
			monto = monto + montoForm;
			cargaVirtual.setCantidadMonto(monto);
			float fSaldo = cargaVirtual.getCliente().getSaldo();
			cuenta.setSaldo(fSaldo);
			cuenta.setCliente(cargaVirtual.getCliente());
			cuenta.setFecha(new Date());
			cuenta.setMonto(cantidadMontoCV);
			cuenta.setDetalle(concept + "- " + conceptoCV);
			cuenta.setUsuario(usuario);
			int valorCC = cuentaDAO.insertar(cuenta);
			int valor = DAOcargaVirtual.updateCargaVirtual(cargaVirtual);
			if(valor != 0 && valorCC != 0){
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Carga Virtual " + cargaVirtual.getNombre() +
						" guardada correctamente", null);
				onCargaView();
			}else{
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Carga Virtual " + cargaVirtual.getNombre() +
						" no se pudo guardar", null);
				onCargaView();
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error: Se requiere un monto", null);
			onCargaView();
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onCargaView() {
		System.out.println("Metodo onCargaView()");
		listadoCargas = null;
		filteredListadoCargas = null;
		cargaVirtual = new CargaVirtual();
		cantidadMontoCV = 0;
		tipoCV = 1;
		conceptoCV = null;
		DAOcargaVirtual = new DAOCargaVirtual();
		List<CargaVirtual> lista = new ArrayList<CargaVirtual>();
		lista = DAOcargaVirtual.getListadoCargas();
		setListadoCargas(lista);
		setFilteredListadoCargas(lista);
		return "cargaVirtual";
	}

	public List<CargaVirtual> getCargasXEstado(Integer estado) {
		System.out.println("Metodo getCargasXEstado()");
		DAOcargaVirtual = new DAOCargaVirtual();
		filteredListadoCargas = null;
		List<CargaVirtual> lista = new ArrayList<CargaVirtual>();
		if (estado == 2) {
			lista = DAOcargaVirtual.getListadoCargas();
			filteredListadoCargas = lista;
			setEstadoCarga(2);
		} else if (estado == 0) {
			lista = DAOcargaVirtual.getListadoCargassDesact();
			filteredListadoCargas = lista;
			setEstadoCarga(0);;
		} else {
			lista = DAOcargaVirtual.getListadoCargasAct();
			filteredListadoCargas = lista;
			setEstadoCarga(1);
		}
		return filteredListadoCargas;
	}

	public void onEditCarga(CargaVirtual carga) {
		cargaVirtual = new CargaVirtual();
		cargaVirtual = carga;
	}

	public String activarCarga(CargaVirtual carga) {
		System.out.println("Metodo activarCarga()");
		DAOcargaVirtual = new DAOCargaVirtual();
		FacesMessage msg = null;
		setCurrentDate(new Date());
		carga.setEnabled(true);
		int valor = DAOcargaVirtual.updateEstadoCargaVirtual(carga);
		if (valor != 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Carga Virtual: "
					+ carga.getNombre() + " Activada correctamente.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			onCargaView();
			return "cargaVirtual";
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: La Carga Virtual: " + carga.getNombre()

					+ " no se ha podido Activar.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "cargaVirtual";
		}
	}

	/**
	 * 
	 * @param client
	 * @return
	 */
	public String desactivarCarga(CargaVirtual carga) {
		System.out.println("Metodo desactivarCarga()");
		DAOcargaVirtual = new DAOCargaVirtual();
		FacesMessage msg = null;
		carga.setEnabled(false);
		int valor = DAOcargaVirtual.updateEstadoCargaVirtual(carga);
		if (valor != 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Carga Virtual: "
					+ carga.getNombre() + " Desactivada correctamente.",
					null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			onCargaView();
			return "cargaVirtual";
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: Carga Virtual: " + carga.getNombre()
							+ " no se ha podido Desactivar.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "cargaVirtual";
		}
		
	}

	public String cancelarCarga() {
		cargaVirtual = new CargaVirtual();
		return "cargaVirtual";
	}
	
	public void guardarCompraCarga() {
		System.out.println("Metodo guardarCompraCarga()");
		FacesMessage msg = null;
		if(compraCargaVirtual.getFechaAlta() != null && !compraCargaVirtual.getFechaAlta().equals("") &&
				compraCargaVirtual.getCantidadMonto() != 0 && idCVCompra != null &&
				idCVCompra != 0){
			DAOcargaVirtual = new DAOCargaVirtual();
			DAOProveedor = new DAOproveedor();
//			DAOcc = new DAOcuentascorriente();
			cuentaDAO = new DAOCuentas();
			CuentasCorrientes cuentaCC = new CuentasCorrientes();
			Cuenta cuentas = new Cuenta();
			CargaVirtual carga = new CargaVirtual();
//			Cuentascorriente cuenta = new Cuentascorriente();
			carga = DAOcargaVirtual.getCargaVirtualXId(idCVCompra);
			Cliente plataforma = carga.getCliente();
			float saldo = plataforma.getSaldo();
			saldo = saldo + compraCargaVirtual.getCantidadMonto();
			float monto = carga.getCantidadMonto();
			monto = monto + compraCargaVirtual.getCantidadMonto();
//			plataforma.setSaldo(saldo);			
			carga.setCantidadMonto(monto);
			carga.setEnabled(carga.getEnabled());
			carga.setNombre(carga.getNombre());
			carga.setUsuario(carga.getUsuario());
			compraCargaVirtual.setUsuario1(usuario);
			setCurrentDate(new Date());
			compraCargaVirtual.setEnabled(true);
			int valorC = DAOcargaVirtual.updateCargaVirtual(carga);
			compraCargaVirtual.setCargaVirtual(carga);
			int valor = DAOcargaVirtual.insertCompraCargaVirtual(compraCargaVirtual);
//			cuenta.setCliente(plataforma);
//			cuenta.setFechaAlta(getCurrentDate());
//			cuenta.setIdMovimiento(valor);
//			cuenta.setMonto(compraCargaVirtual.getCantidadMonto());
//			cuenta.setTipoMovimiento("COMPRA - " + carga.getNombre());
//			cuenta.setUsuario(usuario);
			cuentas.setCliente(plataforma);
			cuentas.setDebe(compraCargaVirtual.getCantidadMonto());
			cuentas.setDetalle("Compra de Carga Virtual - Plataforma: " + carga.getNombre());
			cuentas.setFecha(compraCargaVirtual.getFechaAlta());
			cuentas.setIdMovimiento(valor);
			cuentas.setNombreTabla("compra_cargavirtual");
			cuentas.setUsuario(usuario);
//			int valorCC = insertarCuentaCorriente(cuentas);
			int valorCC = cuentaCC.insertarCCP(cuentas);
			int valorPl = DAOProveedor.updateProveedor(plataforma);
//			int valorCC = DAOcc.insertCuentaCorriente(cuenta);
			if(valor != 0 && valorC != 0 && valorPl != 0 && valorCC != 0){
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra guardada correctamente", null);
				onCompraCargaView();
			}else{
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Error al cargar la compra", null);
				onCompraCargaView();
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Error! Debe completar todos los campos", null);
			onCompraCargaView();
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onCompraCargaView() {
		System.out.println("Metodo onCompraCargaView()");
		listadoCompraCargas = null;
		filteredListadoCompraCargas = null;
		compraCargaVirtual = new CompraCargavirtual();
		DAOcargaVirtual = new DAOCargaVirtual();
		List<CompraCargavirtual> lista = new ArrayList<CompraCargavirtual>();
		lista = DAOcargaVirtual.getListadoCompraCargas();
		setListadoCompraCargas(lista);
		setFilteredListadoCompraCargas(lista);
		return "compraCargaVirtual";
	}

	public List<CompraCargavirtual> getCompraCargasXEstado(Integer estado) {
		System.out.println("Metodo getCompraCargasXEstado(Integer estado)");
		DAOcargaVirtual = new DAOCargaVirtual();
		filteredListadoCompraCargas = null;
		List<CompraCargavirtual> lista = new ArrayList<CompraCargavirtual>();
		if (estado == 2) {
			lista = DAOcargaVirtual.getListadoCompraCargas();
			filteredListadoCompraCargas = lista;
			setEstadoCompraCarga(2);
		} else if (estado == 0) {
			lista = DAOcargaVirtual.getListadoCompraCargassDesact();
			filteredListadoCompraCargas = lista;
			setEstadoCompraCarga(0);;
		} else {
			lista = DAOcargaVirtual.getListadoCompraCargasAct();
			filteredListadoCompraCargas = lista;
			setEstadoCompraCarga(1);
		}
		return filteredListadoCompraCargas;
	}

	/**
	 * 
	 * @param client
	 * @return
	 */
	public String desactivarCompraCarga(CompraCargavirtual carga) {
		System.out.println("Metodo desactivarCompraCarga()");
		FacesMessage msg = null;
		DAOcargaVirtual = new DAOCargaVirtual();
		DAOProveedor = new DAOproveedor();
		DAOcc = new DAOcuentascorriente();
		setCurrentDate(new Date());
		carga.setEnabled(false);
		carga.setFechaBaja(getCurrentDate());
		carga.setUsuario2(usuario);
		CargaVirtual cargaV = new CargaVirtual();
		cargaV = carga.getCargaVirtual();
		float monto = cargaV.getCantidadMonto();
		monto = monto - carga.getCantidadMonto();
		cargaV.setCantidadMonto(monto);
		Cliente plataforma = cargaV.getCliente();
		float saldo = plataforma.getSaldo();
		saldo = saldo - carga.getCantidadMonto();
		plataforma.setSaldo(saldo);
		Cuentascorriente cuenta = new Cuentascorriente();
		cuenta.setCliente(plataforma);
		cuenta.setFechaAlta(getCurrentDate());
		cuenta.setIdMovimiento(carga.getId());
		cuenta.setMonto(carga.getCantidadMonto());
		cuenta.setTipoMovimiento("BAJA COMPRA - Nro:" + carga.getId() + " " + cargaV.getNombre());
		cuenta.setUsuario(usuario);
		int valorC = DAOcargaVirtual.updateCargaVirtual(cargaV);
		int valor = DAOcargaVirtual.updateEstadoCompraCargaVirtual(carga);
		int valorPl = DAOProveedor.updateProveedor(plataforma);
		int valorCC = DAOcc.insertCuentaCorriente(cuenta);
		if (valor != 0 && valorC != 0 && valorPl != 0 && valorCC != 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra desactivada correctamente.",
					null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			onCompraCargaView();
			return "compraCargaVirtual";
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error: No se ha podido Desactivar.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "compraCargaVirtual";
		}
	}

	public String cancelarCompraCarga() {
		compraCargaVirtual = new CompraCargavirtual();
		return "compraCargaVirtual";
	}

	// /////////////////////////////////////REPORTES///////////////////////////////////////
	private List<RankingProducto> filteredRankingVentaProducto;
	private List<RankingProducto> listaRankingProducto;
	private List<RankingProducto> filteredRankingCompraProducto;
	private Date fechaInicioRankingProducto;
	private Date fechaFinalRankingProducto;
	private List<RankingCliente> filteredRankingVentaCliente;
	private List<RankingCliente> listaRankingCliente;
	private List<RankingCliente> filteredRankingCompraCliente;
	private Date fechaInicioRankingCliente;
	private Date fechaFinalRankingCliente;
	private float gananciaTotal;
	private Date inicioGanancia;
	private Date finalGanancia;
	private List<AuxGanancia> listaAuxGanancia;
	private List<AuxGananciaProd> listaAuxGananciaProd;
	private int idProdFiltro;
	private boolean activadorListasGanancia = false;
	private boolean activadorListasGananciaProd = false;
	private List<Producto> listaProductosGanancia;
	private float gananciaTotalProd;
	private String nombreProductoReporte;

	// reporte de carga virtual
	private List<CargaVirtual> listaCVirtual = new ArrayList<CargaVirtual>();
	private Date fechaICV, fechaFCV;
	private int selecetedCVirtual;
	private List<RankinVirtual> listaRankingV = new ArrayList<RankinVirtual>();
	private boolean activadorRankingVirtual = false;
	private boolean enabledBotonRanking = false;
	private String tipoReporte;

	// hasta aca, abajo los setter y getter

	public List<CargaVirtual> getListaCVirtual() {
		DAOExcel daoe = new DAOExcel();

		listaCVirtual = daoe.getListaPlataformas();
		return listaCVirtual;
	}

	public String getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(String tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

	public boolean isEnabledBotonRanking() {
		return enabledBotonRanking;
	}

	public void setEnabledBotonRanking(boolean enabledBotonRanking) {
		this.enabledBotonRanking = enabledBotonRanking;
	}

	public boolean isActivadorRankingVirtual() {
		return activadorRankingVirtual;
	}

	public void setActivadorRankingVirtual(boolean activadorRankingVirtual) {
		this.activadorRankingVirtual = activadorRankingVirtual;
	}

	public List<RankinVirtual> getListaRankingV() {
		return listaRankingV;
	}

	public void setListaRankingV(List<RankinVirtual> listaRankingV) {
		this.listaRankingV = listaRankingV;
	}

	public void setListaCVirtual(List<CargaVirtual> listaCVirtual) {
		this.listaCVirtual = listaCVirtual;
	}

	public Date getFechaICV() {
		return fechaICV;
	}

	public void setFechaICV(Date fechaICV) {
		this.fechaICV = fechaICV;
	}

	public Date getFechaFCV() {
		return fechaFCV;
	}

	public void setFechaFCV(Date fechaFCV) {
		this.fechaFCV = fechaFCV;
	}

	public int getSelecetedCVirtual() {
		return selecetedCVirtual;
	}

	public void setSelecetedCVirtual(int selecetedCVirtual) {
		this.selecetedCVirtual = selecetedCVirtual;
	}

	public String getNombreProductoReporte() {
		return nombreProductoReporte;
	}

	public void setNombreProductoReporte(String nombreProductoReporte) {
		this.nombreProductoReporte = nombreProductoReporte;
	}

	public float getGananciaTotalProd() {
		return gananciaTotalProd;
	}

	public String getGananciaTotalProdString() {
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(gananciaTotalProd);
		return valor;
	}

	public void setGananciaTotalProd(float gananciaTotalProd) {
		this.gananciaTotalProd = gananciaTotalProd;
	}

	public boolean isActivadorListasGanancia() {
		return activadorListasGanancia;
	}

	public void setActivadorListasGanancia(boolean activadorListasGanancia) {
		this.activadorListasGanancia = activadorListasGanancia;
	}

	public boolean isActivadorListasGananciaProd() {
		return activadorListasGananciaProd;
	}

	public void setActivadorListasGananciaProd(
			boolean activadorListasGananciaProd) {
		this.activadorListasGananciaProd = activadorListasGananciaProd;
	}

	public List<AuxGananciaProd> getListaAuxGananciaProd() {
		return listaAuxGananciaProd;
	}

	public void setListaAuxGananciaProd(
			List<AuxGananciaProd> listaAuxGananciaProd) {
		this.listaAuxGananciaProd = listaAuxGananciaProd;
	}

	public List<Producto> getListaProductosGanancia() {
		DAOProducto daop = new DAOProducto();
		listaProductosGanancia = daop.getListadoProductosAct();
		return listaProductosGanancia;
	}

	public void setListaProductosGanancia(List<Producto> listaProductosGanancia) {
		this.listaProductosGanancia = listaProductosGanancia;
	}

	public int getIdProdFiltro() {
		return idProdFiltro;
	}

	public void setIdProdFiltro(int idProdFiltro) {
		this.idProdFiltro = idProdFiltro;
	}

	public float getGananciaTotal() {
		return gananciaTotal;
	}

	public String getGananciaTotalString() {
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		String valor = formatear.format(gananciaTotal);
		return valor;
	}

	public void setGananciaTotal(float gananciaTotal) {
		this.gananciaTotal = gananciaTotal;
	}

	public List<AuxGanancia> getListaAuxGanancia() {
		return listaAuxGanancia;
	}

	public void setListaAuxGanancia(List<AuxGanancia> listaAuxGanancia) {
		this.listaAuxGanancia = listaAuxGanancia;
	}

	public Date getInicioGanancia() {
		return inicioGanancia;
	}

	public void setInicioGanancia(Date inicioGanancia) {
		this.inicioGanancia = inicioGanancia;
	}

	public Date getFinalGanancia() {
		return finalGanancia;
	}

	public void setFinalGanancia(Date finalGanancia) {
		this.finalGanancia = finalGanancia;
	}

	public List<RankingCliente> getFilteredRankingVentaCliente() {
		return filteredRankingVentaCliente;
	}

	public void setFilteredRankingVentaCliente(
			List<RankingCliente> filteredRankingVentaCliente) {
		this.filteredRankingVentaCliente = filteredRankingVentaCliente;
	}

	public List<RankingCliente> getListaRankingCliente() {
		return listaRankingCliente;
	}

	public void setListaRankingCliente(List<RankingCliente> listaRankingCliente) {
		this.listaRankingCliente = listaRankingCliente;
	}

	public List<RankingCliente> getFilteredRankingCompraCliente() {
		return filteredRankingCompraCliente;
	}

	public void setFilteredRankingCompraCliente(
			List<RankingCliente> filteredRankingCompraCliente) {
		this.filteredRankingCompraCliente = filteredRankingCompraCliente;
	}

	public Date getFechaInicioRankingCliente() {
		return fechaInicioRankingCliente;
	}

	public void setFechaInicioRankingCliente(Date fechaInicioRankingCliente) {
		this.fechaInicioRankingCliente = fechaInicioRankingCliente;
	}

	public Date getFechaFinalRankingCliente() {
		return fechaFinalRankingCliente;
	}

	public void setFechaFinalRankingCliente(Date fechaFinalRankingCliente) {
		this.fechaFinalRankingCliente = fechaFinalRankingCliente;
	}

	public List<RankingProducto> getFilteredRankingCompraProducto() {
		return filteredRankingCompraProducto;
	}

	public void setFilteredRankingCompraProducto(
			List<RankingProducto> filteredRankingCompraProducto) {
		this.filteredRankingCompraProducto = filteredRankingCompraProducto;
	}

	public Date getFechaInicioRankingProducto() {
		return fechaInicioRankingProducto;
	}

	public void setFechaInicioRankingProducto(Date fechaInicioRankingProducto) {
		this.fechaInicioRankingProducto = fechaInicioRankingProducto;
	}

	public Date getFechaFinalRankingProducto() {
		return fechaFinalRankingProducto;
	}

	public void setFechaFinalRankingProducto(Date fechaFinalRankingProducto) {
		this.fechaFinalRankingProducto = fechaFinalRankingProducto;
	}

	public List<RankingProducto> getListaRankingProducto() {
		return listaRankingProducto;
	}

	public void setListaRankingProducto(
			List<RankingProducto> listaRankingProducto) {
		this.listaRankingProducto = listaRankingProducto;
	}

	public List<RankingProducto> getFilteredRankingVentaProducto() {
		return filteredRankingVentaProducto;
	}

	public void setFilteredRankingVentaProducto(
			List<RankingProducto> filteredRankingVentaProducto) {
		this.filteredRankingVentaProducto = filteredRankingVentaProducto;
	}
	
	public String goReporteGanancias(){
		inicioGanancia = null;
		finalGanancia = null;
		idProdFiltro = 0;
		listaAuxGanancia = null;
		listaAuxGananciaProd = null;
		activadorListasGanancia = false;
		activadorListasGananciaProd = false;
		return "reporteGanancias";
	}
	
	public String goRankingCargaVirtual(){
		listaRankingV = null;
		activadorRankingVirtual = false;
		enabledBotonRanking = false;
		fechaICV = null;
		fechaFCV = null;
		selecetedCVirtual = 0;
		return "rankingCargaVirtual";
	}

	public void imprimirReporteIJM() {
		System.out.println("Metodo imprimirReporteIJM()");
		daoPago = new DaoPago();
		Cliente roberto = new Cliente();
		roberto = daoPago.getIJM();
		reporteCC(roberto);
	}

	public void imprimirReporteCliente() {
		reporteCC(clienteParaFiltro);
	}

	public void imprimirReporteProveedor() {
		reporteCC(proveedorParaFiltro);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void browseRankingCVirtual() {
		System.out.println("Metodo browseRankinVirtual()");
		CargaVirtual cv = new CargaVirtual();
		DAOExcel daoe = new DAOExcel();
		cv = daoe.getCVporId(selecetedCVirtual);
		String nombrePlataforma = "";
		if (cv.getNombre().equals("Pinweb")) {
			nombrePlataforma = "venta_pinweb";
			tipoReporte = "Ranking de ventas de Pinweb";
		}
		if (cv.getNombre().equals("Re Virtual")) {
			nombrePlataforma = "sistema_revirtual";
			tipoReporte = "Ranking de ventas de Re Virtual";
		}
		if (cv.getNombre().equals("Telerecargas")) {
			nombrePlataforma = "telerecarga_reporte";
			tipoReporte = "Ranking de ventas de Telerecargas";
		}
		if (cv.getNombre().equals("Codigo Pagos")) {
			nombrePlataforma = "codigo_pagos";
			tipoReporte = "Ranking de ventas de Codigo Pagos";
		}
		if (cv.getNombre().equals("Pinweb Mercader444")) {
			nombrePlataforma = "pinweb_mercader444";
			tipoReporte = "Ranking de ventas de Pinweb Mercader444";
		}
		DecimalFormat formato = new DecimalFormat("##,##0.00");
		List<Cliente> listacli = daoe
				.getListaCliPorPlataforma(nombrePlataforma);
		List<RankinVirtual> listarank = new ArrayList<RankinVirtual>();

		for (Cliente cli : listacli) {
			RankinVirtual rv = new RankinVirtual();
			rv.setCli(cli.getApellido());
			List<MovimientoVirtual> listamov = new ArrayList<MovimientoVirtual>();
			if (fechaICV != null && fechaFCV != null) {
				listamov = daoe.getVentasVirtualPorClienteYFecha(cli, fechaICV,
						fechaFCV);
			} else {
				listamov = daoe.getVentasVirtualPorCliente(cli);
			}
			float monto = 0;
			for (MovimientoVirtual movV : listamov) {
				monto = monto + movV.getMonto();
			}
			rv.setMonto(monto);
			rv.setMon(formato.format(monto));
			rv.setMon("$" + rv.getMon());
			if (rv.getMonto() > 0) {
				listarank.add(rv);
			}
		}

		if (listarank.size() > 0) {
			activadorRankingVirtual = true;
			Collections.sort(listarank, new Comparator() {
				@Override
				public int compare(Object p1, Object p2) {
					// TODO Auto-generated method stub
					return new Float(((RankinVirtual) p2).getMonto())
							.compareTo(new Float(((RankinVirtual) p1)
									.getMonto()));
				}
			});

			listaRankingV = listarank;
		} else {
			activadorRankingVirtual = false;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"No se han realizado ventas de carga virtual", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void onchangePlataformaRanking() {
		System.out.println("Metodo onchangePlataformaRaking()");
		if (selecetedCVirtual == 0) {
			enabledBotonRanking = false;
			fechaICV = null;
			fechaFCV = null;
		} else {
			enabledBotonRanking = true;
			fechaICV = null;
			fechaFCV = null;
		}
	}

	public void generarReporteRankingVirtual() {
		System.out.println("Metodo generarReporteRankingVirtual()");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("tipo", tipoReporte);

		String ini, fin;
		if (fechaICV != null && fechaFCV != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			ini = "Desde " + sdf.format(fechaICV);
			fin = " - Hasta " + sdf.format(fechaFCV);
		} else {
			ini = "";
			fin = "";
		}

		parameters.put("fechai", ini);
		parameters.put("fechaf", fin);

		String formaPrinteo = "attachment;filename=";

		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("reportes/rankingVirtual.jrxml");
			JasperReport jasperReport;
			jasperReport = JasperCompileManager.compileReport(reportStream);
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(
					listaRankingV);

			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			facesContext.responseComplete();
			response.setContentType("application/pdf");

			response.addHeader("Content-disposition", formaPrinteo
					+ "rankingCargaVirtual.pdf");
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, source);
			JasperExportManager.exportReportToPdfStream(print,
					servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void reporteCC(Cliente cli) {
		System.out.println("Metodo reporteCC()");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<Cuentascorriente> listaReporte = new ArrayList<Cuentascorriente>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nombre", cli.getNombre());
		parameters.put("apellido", cli.getApellido());
		parameters.put("telefono", cli.getTelefono());
		parameters.put("email", cli.getMail());
		parameters.put("monto", cli.getSaldoString());
		parameters.put("fechai", " ");
		parameters.put("fechaf", " ");

		if (cli.getTipo().equals("i")) {
			if (filteredListadoCCIJM == null) {
				listaReporte = listaCuentasCorrienteIJM;
			} else {
				listaReporte = filteredListadoCCIJM;
			}
			if (inicioCCIJM != null && finalCCIJM != null) {
				parameters.put("fechai", "Desde: " + sdf.format(inicioCCIJM));
				parameters.put("fechaf", " - Hasta: " + sdf.format(finalCCIJM));
			}
		}

		if (cli.getTipo().equals("p")) {
			if (filteredListadoCCProveedor == null) {
				listaReporte = listaCuentasCorrienteProveedor;
			} else {
				listaReporte = filteredListadoCCProveedor;
			}
			if (inicioCCP != null && finalCCP != null) {
				parameters.put("fechai", "Desde: " + sdf.format(inicioCCP));
				parameters.put("fechaf", " - Hasta: " + sdf.format(finalCCP));
			}
		}

		if (cli.getTipo().equals("c")) {
			if (filteredListadoCCCliente == null) {
				listaReporte = listaCuentasCorrienteCliente;
			} else {
				listaReporte = filteredListadoCCCliente;
			}
			if (inicioCCC != null && finalCCC != null) {
				parameters.put("fechai", "Desde: " + sdf.format(inicioCCC));
				parameters.put("fechaf", " - Hasta: " + sdf.format(finalCCC));
			}
		}

		if (cli.getTipo().equals("v")) {
			if (filteredCVCuentas == null) {
				listaReporte = listaCVCuentas;
			} else {
				listaReporte = filteredCVCuentas;
			}
			if (inicioCCC != null && finalCCC != null) {
				parameters.put("fechai", "Desde: " + sdf.format(inicioCV));
				parameters.put("fechaf", " - Hasta: " + sdf.format(finalCV));
			}
		}

		String formaPrinteo = "attachment;filename=";

		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("reportes/CuentaCorriente.jrxml");
			JasperReport jasperReport;
			jasperReport = JasperCompileManager.compileReport(reportStream);
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(
					listaReporte);

			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			facesContext.responseComplete();
			response.setContentType("application/pdf");

			response.addHeader("Content-disposition", formaPrinteo
					+ "resumenCuentaCorriente.pdf");
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, source);
			JasperExportManager.exportReportToPdfStream(print,
					servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void reporteCompraVenta(String tipo) {
		
		System.out.println("Metodo reporteCompraVenta()");

		//System.out.println(tipo);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<Compra> reporteCompra = new ArrayList<Compra>();
		List<Venta> reporteVenta = new ArrayList<Venta>();
		String nombreReporte = "";
		String nombrePDF = "";

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("tipo", "Reporte de " + tipo);
		parameters.put("fechai", " ");
		parameters.put("fechaf", " ");

		if (tipo.equals("Compras")) {
			nombreReporte = "reporteCompra.jrxml";
			nombrePDF = "reporteCompra.pdf";
			if (inicioPedidos != null && finalPedidos != null) {
				parameters.put("fechai", "Desde: " + sdf.format(inicioPedidos));
				parameters.put("fechaf",
						" - Hasta: " + sdf.format(finalPedidos));
			}

			if (filteredPedidos == null) {
				reporteCompra = listaPedidos;
			} else {
				reporteCompra = filteredPedidos;
			}

		}

		if (tipo.equals("Ventas")) {
			nombreReporte = "reporteVenta.jrxml";
			nombrePDF = "reporteVenta.pdf";
			if (inicioVenta != null && finalVenta != null) {
				parameters.put("fechai", "Desde: " + sdf.format(inicioVenta));
				parameters.put("fechaf", " - Hasta: " + sdf.format(finalVenta));
			}
			if (filteredVentasBaja == null) {
				reporteVenta = listaVentasBaja;
			} else {
				reporteVenta = filteredVentasBaja;
			}

		}

		String formaPrinteo = "attachment;filename=";

		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("reportes/" + nombreReporte);
			JasperReport jasperReport;
			jasperReport = JasperCompileManager.compileReport(reportStream);
			JRBeanCollectionDataSource source = null;
			if (tipo.equals("Compras")) {
				source = new JRBeanCollectionDataSource(reporteCompra);
			} else {
				//System.out.println("entra");
				source = new JRBeanCollectionDataSource(reporteVenta);
			}

			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			facesContext.responseComplete();
			response.setContentType("application/pdf");

			response.addHeader("Content-disposition", formaPrinteo + nombrePDF);
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, source);
			JasperExportManager.exportReportToPdfStream(print,
					servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void browseGanancias() {		
		System.out.println("Metodo browseGanancias()");		
		FacesMessage msg = null;
		final long MILISEGUNDOS_POR_DIA = 24 * 60 * 60 * 1000;
		if (idProdFiltro == 0) {
			if (inicioGanancia != null && finalGanancia != null) {
				long diferencia = (finalGanancia.getTime() - inicioGanancia.getTime()) / MILISEGUNDOS_POR_DIA;
				if(diferencia <= 30){
					listaAuxGanancia = new ArrayList<AuxGanancia>();
					List<Venta> listaventa = new ArrayList<Venta>();
					DAOVTA = new DAOventa();
					listaventa = DAOVTA.getVentasPorFecha(inicioGanancia, finalGanancia);
					if (listaventa.size() > 0) {
						AuxGanancia auxG = new AuxGanancia();
						for (Venta venta : listaventa) {
							//System.out.println("esta iterando las ventas");
							auxG = new AuxGanancia();
							auxG.setFecha(venta.getFecha());
							auxG.setPrecioventa(venta.getMonto());
							auxG.setVenta(venta);
							List<VentasProducto> listadetalle = new ArrayList<VentasProducto>();
							listadetalle = DAOVTA.getDetallesDeVentas(venta.getId());
							float precioCompra = 0;
							for (VentasProducto vP : listadetalle) {
								//System.out.println("esta iterando los detalles");
								List<StockDetalle> listastockdetalle = new ArrayList<StockDetalle>();
								listastockdetalle = DAOVTA.getStockDetalleXDetalle(vP);
								for (StockDetalle sD : listastockdetalle) {
									Stock stock = new Stock();
									stock = sD.getStock();
									precioCompra = precioCompra	+ (sD.getCantidad() * stock.getPrecioUnitario());
								}
							}
							auxG.setPreciocompra(precioCompra);
							auxG.setGanancia(auxG.getPrecioventa() - auxG.getPreciocompra());
							listaAuxGanancia.add(auxG);
							gananciaTotal = 0;
							for (AuxGanancia vProducto : listaAuxGanancia) {
								gananciaTotal = gananciaTotal + vProducto.getGanancia();
							}
						}
						activadorListasGanancia = true;
						activadorListasGananciaProd = false;
					} else {
						msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "No hay ventas realizadas en este rango de fechas",	null);
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
				}else{
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "La diferencia es de: " + diferencia + "! La cantidad de dias "
							+ "no puede superar los 30", null);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}			
			} else {
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe ingresar las dos fechas", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}			
//			if (inicioGanancia != null && finalGanancia != null) {
//				listaAuxGanancia = new ArrayList<AuxGanancia>();
//				List<Venta> listaventa = new ArrayList<Venta>();
//				DAOVTA = new DAOventa();
//				listaventa = DAOVTA.getVentasPorFecha(inicioGanancia, finalGanancia);
//				if (listaventa.size() > 0) {
//					AuxGanancia auxG = new AuxGanancia();
//					for (Venta venta : listaventa) {
//						//System.out.println("esta iterando las ventas");
//						auxG = new AuxGanancia();
//						auxG.setFecha(venta.getFecha());
//						auxG.setPrecioventa(venta.getMonto());
//						auxG.setVenta(venta);
//						List<VentasProducto> listadetalle = new ArrayList<VentasProducto>();
//						listadetalle = DAOVTA.getDetallesDeVentas(venta.getId());
//						float precioCompra = 0;
//						for (VentasProducto vP : listadetalle) {
//							//System.out.println("esta iterando los detalles");
//							List<StockDetalle> listastockdetalle = new ArrayList<StockDetalle>();
//							listastockdetalle = DAOVTA.getStockDetalleXDetalle(vP);
//							for (StockDetalle sD : listastockdetalle) {
//								Stock stock = new Stock();
//								stock = sD.getStock();
//								precioCompra = precioCompra	+ (sD.getCantidad() * stock.getPrecioUnitario());
//							}
//						}
//						auxG.setPreciocompra(precioCompra);
//						auxG.setGanancia(auxG.getPrecioventa() - auxG.getPreciocompra());
//						listaAuxGanancia.add(auxG);
//						gananciaTotal = 0;
//						for (AuxGanancia vProducto : listaAuxGanancia) {
//							gananciaTotal = gananciaTotal + vProducto.getGanancia();
//						}
//					}
//					activadorListasGanancia = true;
//					activadorListasGananciaProd = false;
//				} else {
//					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "No hay ventas realizadas en este rango de fechas",	null);
//					FacesContext.getCurrentInstance().addMessage(null, msg);
//				}
//			} else {
//				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe ingresar las dos fechas", null);
//				FacesContext.getCurrentInstance().addMessage(null, msg);
//			}
		} else {
			if (inicioGanancia != null && finalGanancia != null) {
				Producto prod = new Producto();
				DAOProducto daop = new DAOProducto();
				prod = daop.getProductoXId(idProdFiltro);
				nombreProductoReporte = prod.getNombre();
				listaAuxGananciaProd = new ArrayList<AuxGananciaProd>();
				DAOVTA = new DAOventa();
				List<VentasProducto> listadetalle = new ArrayList<VentasProducto>();
				listadetalle = DAOVTA.getListadoDetallePorProd(prod, inicioGanancia, finalGanancia);
				if (listadetalle.size() > 0) {
					AuxGananciaProd aux = new AuxGananciaProd();
					for (VentasProducto vP : listadetalle) {
						aux = new AuxGananciaProd();
						List<StockDetalle> listastockdetalle = new ArrayList<StockDetalle>();
						DAOVTA = new DAOventa();
						listastockdetalle = DAOVTA.getStockDetalleXDetalle(vP);
						aux.setIdVenta(vP.getVenta().getId());
						aux.setSubTotal(vP.getSubtotal());
						aux.setCantidad(vP.getCantidad());
						aux.setFecha(vP.getVenta().getFecha());
						aux.setProducto(vP.getProducto());
						for (StockDetalle sD : listastockdetalle) {
							Stock stock = new Stock();
							stock = sD.getStock();
							aux.setPreCom(sD.getCantidad() * stock.getPrecioUnitario());
						}
						aux.setMonGanancia(aux.getSubTotal() - aux.getPreCom());
						listaAuxGananciaProd.add(aux);
					}
					gananciaTotalProd = 0;
					for (AuxGananciaProd pr : listaAuxGananciaProd) {
						gananciaTotalProd = gananciaTotalProd + pr.getMonGanancia();
					}
					activadorListasGanancia = false;
					activadorListasGananciaProd = true;
				} else {
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "No existe ventas realizadas con estos criterios de busqueda", null);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} else {
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe ingresar las dos fechas", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

	public void generarReporteGanancia(String tipo) {
		
		System.out.println("Metodo generarReporteGanancia()");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String ini = sdf.format(inicioGanancia);
		String fin = sdf.format(finalGanancia);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fechai", " ");
		parameters.put("fechaf", " ");
		String nombrepdf = "";
		String nombrerep = "";
		if (tipo.equals("simple")) {
			parameters.put("tipo", "Reporte de ganancias");
			parameters.put("montoTotal", getGananciaTotalString());
			nombrepdf = "reporteGanancia.pdf";
			nombrerep = "reporteGanancia.jrxml";
			if (inicioGanancia != null && finalGanancia != null) {
				parameters.put("fechai", "Desde: " + ini);
				parameters.put("fechaf", " - Hasta: " + fin);
			}
		} else {
			parameters.put("tipo", "Reporte de ganancias por producto");
			parameters.put("nombreProducto", nombreProductoReporte);
			parameters.put("montoTotal", getGananciaTotalProdString());
			nombrepdf = "reporteGananciaProducto.pdf";
			nombrerep = "reporteGananciaProducto.jrxml";
			if (inicioGanancia != null && finalGanancia != null) {
				parameters.put("fechai", "Desde: " + ini);
				parameters.put("fechaf", " - Hasta: " + fin);
			}
		}

		String formaPrinteo;
		formaPrinteo = "attachment;filename=";

		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("reportes/" + nombrerep);
			JasperReport jasperReport;
			jasperReport = JasperCompileManager.compileReport(reportStream);
			JRBeanCollectionDataSource source = null;

			if (tipo.equals("simple")) {
				source = new JRBeanCollectionDataSource(listaAuxGanancia);
			} else {
				source = new JRBeanCollectionDataSource(listaAuxGananciaProd);
			}

			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			facesContext.responseComplete();
			response.setContentType("application/pdf");

			response.addHeader("Content-disposition", formaPrinteo + nombrepdf);
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, source);
			JasperExportManager.exportReportToPdfStream(print,
					servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// nuevo franco
	private String montosTotales;
	private Integer cantidadTotales;
	private String montoTotalCaja;
	private String montoTotalProveedores;
	private String montoTotalProductos;
	private String montoTotalCCVirtual;
	private String montoTotalClientes;
	private String montoTotalStockVirtual;
	private String totalActivos;
	private String totalPasivos;
	private String totalPatrimonio;

	public String getMontoTotalCaja() {
		return montoTotalCaja;
	}

	public void setMontoTotalCaja(String montoTotalCaja) {
		this.montoTotalCaja = montoTotalCaja;
	}

	public String getMontoTotalProveedores() {
		return montoTotalProveedores;
	}

	public void setMontoTotalProveedores(String montoTotalProveedores) {
		this.montoTotalProveedores = montoTotalProveedores;
	}

	public String getMontoTotalProductos() {
		return montoTotalProductos;
	}

	public void setMontoTotalProductos(String montoTotalProductos) {
		this.montoTotalProductos = montoTotalProductos;
	}

	public String getMontoTotalCCVirtual() {
		return montoTotalCCVirtual;
	}

	public void setMontoTotalCCVirtual(String montoTotalCCVirtual) {
		this.montoTotalCCVirtual = montoTotalCCVirtual;
	}

	public String getMontoTotalClientes() {
		return montoTotalClientes;
	}

	public void setMontoTotalClientes(String montoTotalClientes) {
		this.montoTotalClientes = montoTotalClientes;
	}

	public String getMontoTotalStockVirtual() {
		return montoTotalStockVirtual;
	}

	public void setMontoTotalStockVirtual(String montoTotalStockVirtual) {
		this.montoTotalStockVirtual = montoTotalStockVirtual;
	}

	public String getTotalActivos() {
		return totalActivos;
	}

	public void setTotalActivos(String totalActivos) {
		this.totalActivos = totalActivos;
	}

	public String getTotalPasivos() {
		return totalPasivos;
	}

	public void setTotalPasivos(String totalPasivos) {
		this.totalPasivos = totalPasivos;
	}

	public String getTotalPatrimonio() {
		return totalPatrimonio;
	}

	public void setTotalPatrimonio(String totalPatrimonio) {
		this.totalPatrimonio = totalPatrimonio;
	}

	public Integer getCantidadTotales() {
		return cantidadTotales;
	}

	public void setCantidadTotales(Integer cantidadTotales) {
		this.cantidadTotales = cantidadTotales;
	}

	public String getMontosTotales() {
		return montosTotales;
	}

	public void setMontosTotales(String montosTotales) {
		this.montosTotales = montosTotales;
	}

	public void generarReporteRanking(String tipo) {
		System.out.println("Metodo generarReporteRanking()");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String ini = "";
		String fin = "";

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fechai", " ");
		parameters.put("fechaf", " ");
		String nombrepdf = "";
		String nombrerep = "";

		if (tipo.equals("compraProveedor")) {
			parameters.put("tipo", "Ranking - Compras - Proveedor");
			parameters.put("montoTotal", montosTotales);
			nombrepdf = "reporteRankingCliente.pdf";
			nombrerep = "reporteRankingCliente.jrxml";
			if (fechaInicioRankingCliente != null
					&& fechaFinalRankingCliente != null) {
				ini = sdf.format(fechaInicioRankingCliente);
				fin = sdf.format(fechaFinalRankingCliente);
				parameters.put("fechai", "Desde: " + ini);
				parameters.put("fechaf", " - Hasta: " + fin);
			}
		}
		if (tipo.equals("ventaCliente")) {
			parameters.put("tipo", "Ranking - Ventas - Cliente");
			parameters.put("montoTotal", montosTotales);
			nombrepdf = "reporteRankingCliente.pdf";
			nombrerep = "reporteRankingCliente.jrxml";
			if (fechaInicioRankingCliente != null
					&& fechaFinalRankingCliente != null) {
				ini = sdf.format(fechaInicioRankingCliente);
				fin = sdf.format(fechaFinalRankingCliente);
				parameters.put("fechai", "Desde: " + ini);
				parameters.put("fechaf", " - Hasta: " + fin);
			}
		}
		if (tipo.equals("compraProductoMonto")) {
			parameters.put("tipo", "Ranking - Compras - Producto");
			parameters.put("montoTotal", montosTotales);
			nombrepdf = "reporteRankingProducto.pdf";
			nombrerep = "reporteRankingProductoMonto.jrxml";
			if (fechaInicioRankingProducto != null
					&& fechaFinalRankingProducto != null) {
				ini = sdf.format(fechaInicioRankingProducto);
				fin = sdf.format(fechaFinalRankingProducto);
				parameters.put("fechai", "Desde: " + ini);
				parameters.put("fechaf", " - Hasta: " + fin);
			}
		}
		if (tipo.equals("compraProductoCantidad")) {
			parameters.put("tipo", "Ranking - Compras - Producto");
			parameters.put("cantidadTotal", cantidadTotales);
			nombrepdf = "reporteRankingProducto.pdf";
			nombrerep = "reporteRankingProductoCantidad.jrxml";
			if (fechaInicioRankingProducto != null
					&& fechaFinalRankingProducto != null) {
				ini = sdf.format(fechaInicioRankingProducto);
				fin = sdf.format(fechaFinalRankingProducto);
				parameters.put("fechai", "Desde: " + ini);
				parameters.put("fechaf", " - Hasta: " + fin);
			}
		}
		if (tipo.equals("ventaProductoMonto")) {
			parameters.put("tipo", "Ranking - Ventas - Producto");
			parameters.put("montoTotal", montosTotales);
			nombrepdf = "reporteRankingProducto.pdf";
			nombrerep = "reporteRankingProductoMonto.jrxml";
			if (fechaInicioRankingProducto != null
					&& fechaFinalRankingProducto != null) {
				ini = sdf.format(fechaInicioRankingProducto);
				fin = sdf.format(fechaFinalRankingProducto);
				parameters.put("fechai", "Desde: " + ini);
				parameters.put("fechaf", " - Hasta: " + fin);
			}
		}
		if (tipo.equals("ventaProductoCantidad")) {
			parameters.put("tipo", "Ranking - Ventas - Producto");
			parameters.put("cantidadTotal", cantidadTotales);
			nombrepdf = "reporteRankingProducto.pdf";
			nombrerep = "reporteRankingProductoCantidad.jrxml";
			if (fechaInicioRankingProducto != null
					&& fechaFinalRankingProducto != null) {
				ini = sdf.format(fechaInicioRankingProducto);
				fin = sdf.format(fechaFinalRankingProducto);
				parameters.put("fechai", "Desde: " + ini);
				parameters.put("fechaf", " - Hasta: " + fin);
			}
		}

		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("reportes/" + nombrerep);
			JasperReport jasperReport;
			jasperReport = JasperCompileManager.compileReport(reportStream);
			JRBeanCollectionDataSource source = null;

			if (tipo.equals("compraProveedor")) {
				source = new JRBeanCollectionDataSource(
						filteredRankingCompraCliente);
			}
			if (tipo.equals("ventaCliente")) {
				source = new JRBeanCollectionDataSource(
						filteredRankingVentaCliente);
			}
			if (tipo.equals("compraProductoMonto")) {
				source = new JRBeanCollectionDataSource(
						filteredRankingCompraProducto);
			}
			if (tipo.equals("compraProductoCantidad")) {
				source = new JRBeanCollectionDataSource(
						filteredRankingCompraProducto);
			}
			if (tipo.equals("ventaProductoMonto")) {
				source = new JRBeanCollectionDataSource(
						filteredRankingVentaProducto);
			}
			if (tipo.equals("ventaProductoCantidad")) {
				source = new JRBeanCollectionDataSource(
						filteredRankingVentaProducto);
			}

			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			facesContext.responseComplete();
			response.setContentType("application/pdf");

			response.addHeader("Content-disposition", "attachment;filename="
					+ nombrepdf);
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, source);
			JasperExportManager.exportReportToPdfStream(print,
					servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String goRankingVentaProductoCant() {
		
		System.out.println("Metodo goRankingVentaProductoCant()");
		
		DAOProducto = new DAOProducto();
		DAOVTA = new DAOventa();
		List<RankingProducto> listaAuxRanking = new ArrayList<RankingProducto>();
		List<Producto> listaProd = DAOProducto.getListadoProductos();
		listaRankingProducto = new ArrayList<RankingProducto>();
		filteredRankingVentaProducto = new ArrayList<RankingProducto>();
		fechaInicioRankingProducto = null;
		fechaFinalRankingProducto = null;
		int cantidadTotal = 0;
		for (Producto producto : listaProd) {
			RankingProducto ranking = new RankingProducto();
			ranking.setProducto(producto);
			int cantidad = 0;
			List<Venta> listaVent = new ArrayList<Venta>();
			List<VentasProducto> lista = DAOVTA
					.getListadoDetallePorProd(producto);
			for (VentasProducto ventasProducto : lista) {
				cantidad = cantidad + ventasProducto.getCantidad();
				listaVent.add(ventasProducto.getVenta());
			}
			cantidadTotal = cantidadTotal + cantidad;
			ranking.setCantidad(cantidad);
			ranking.setListaVenta(listaVent);
			listaAuxRanking.add(ranking);
		}

		Collections.sort(listaAuxRanking, new Comparator() {

			@Override
			public int compare(Object p1, Object p2) {
				// TODO Auto-generated method stub
				return new Integer(((RankingProducto) p2).getCantidad())
						.compareTo(new Integer(((RankingProducto) p1)
								.getCantidad()));
			}
		});

		//System.out.println("se ejecuto");
		cantidadTotales = cantidadTotal;
		filteredRankingVentaProducto = listaAuxRanking;
		listaRankingProducto = listaAuxRanking;
		return "rankingVentaProducto";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void buscarRankingVentaProductoCant() {
		
		System.out.println("Metodo buscarRankingVentaProductoCant()");
		
		DAOProducto = new DAOProducto();
		DAOVTA = new DAOventa();
		List<RankingProducto> listaAuxRanking = new ArrayList<RankingProducto>();
		List<Producto> listaProd = DAOProducto.getListadoProductos();
		int cantidadTotal = 0;
		for (Producto producto : listaProd) {
			RankingProducto ranking = new RankingProducto();
			ranking.setProducto(producto);
			int cantidad = 0;
			List<Venta> listaVent = new ArrayList<Venta>();
			List<VentasProducto> lista = DAOVTA.getListadoDetallePorProd(
					producto, fechaInicioRankingProducto,
					fechaFinalRankingProducto);
			for (VentasProducto ventasProducto : lista) {
				cantidad = cantidad + ventasProducto.getCantidad();
				listaVent.add(ventasProducto.getVenta());
			}
			cantidadTotal = cantidadTotal + cantidad;
			ranking.setCantidad(cantidad);
			ranking.setListaVenta(listaVent);
			listaAuxRanking.add(ranking);
		}

		Collections.sort(listaAuxRanking, new Comparator() {

			@Override
			public int compare(Object p1, Object p2) {
				// TODO Auto-generated method stub
				return new Integer(((RankingProducto) p2).getCantidad())
						.compareTo(new Integer(((RankingProducto) p1)
								.getCantidad()));
			}
		});

		cantidadTotales = cantidadTotal;
		filteredRankingVentaProducto = listaAuxRanking;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String goRankingCompraProductoCant() {
		
		System.out.println("Metodo goRankingCompraProductoCant()");
		
		DAOProducto = new DAOProducto();
		DAOcompra = new DAOCompra();
		List<RankingProducto> listaAuxRanking = new ArrayList<RankingProducto>();
		List<Producto> listaProd = DAOProducto.getListadoProductos();
		listaRankingProducto = new ArrayList<RankingProducto>();
		filteredRankingCompraProducto = new ArrayList<RankingProducto>();
		fechaInicioRankingProducto = null;
		fechaFinalRankingProducto = null;
		int cantidadTotal = 0;
		for (Producto producto : listaProd) {
			RankingProducto ranking = new RankingProducto();
			ranking.setProducto(producto);
			int cantidad = 0;
			List<Compra> listaComp = new ArrayList<Compra>();
			List<DetalleCompra> lista = DAOcompra
					.getListadoDetallePorProd(producto);
			for (DetalleCompra detalleCompra : lista) {
				cantidad = cantidad + detalleCompra.getCantidad();
				listaComp.add(detalleCompra.getCompra());
			}
			cantidadTotal = cantidadTotal + cantidad;
			ranking.setCantidad(cantidad);
			ranking.setListaCompra(listaComp);
			listaAuxRanking.add(ranking);
		}

		Collections.sort(listaAuxRanking, new Comparator() {

			@Override
			public int compare(Object p1, Object p2) {
				// TODO Auto-generated method stub
				return new Integer(((RankingProducto) p2).getCantidad())
						.compareTo(new Integer(((RankingProducto) p1)
								.getCantidad()));
			}
		});

		cantidadTotales = cantidadTotal;
		filteredRankingCompraProducto = listaAuxRanking;
		listaRankingProducto = listaAuxRanking;
		return "rankingCompraProducto";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void buscarRankingCompraProductoCant() {
		
		System.out.println("Metodo buscarRankingCompraProductoCant()");
		DAOProducto = new DAOProducto();
		DAOcompra = new DAOCompra();
		List<RankingProducto> listaAuxRanking = new ArrayList<RankingProducto>();
		List<Producto> listaProd = DAOProducto.getListadoProductos();
		int cantidadTotal = 0;
		for (Producto producto : listaProd) {
			RankingProducto ranking = new RankingProducto();
			ranking.setProducto(producto);
			int cantidad = 0;
			List<Compra> listaComp = new ArrayList<Compra>();
			List<DetalleCompra> lista = DAOcompra.getListadoDetallePorProd(
					producto, fechaInicioRankingProducto,
					fechaFinalRankingProducto);
			for (DetalleCompra detalleCompra : lista) {
				cantidad = cantidad + detalleCompra.getCantidad();
				listaComp.add(detalleCompra.getCompra());
			}
			cantidadTotal = cantidadTotal + cantidad;
			ranking.setCantidad(cantidad);
			ranking.setListaCompra(listaComp);
			listaAuxRanking.add(ranking);
		}

		Collections.sort(listaAuxRanking, new Comparator() {

			@Override
			public int compare(Object p1, Object p2) {
				// TODO Auto-generated method stub
				return new Integer(((RankingProducto) p2).getCantidad())
						.compareTo(new Integer(((RankingProducto) p1)
								.getCantidad()));
			}
		});

		cantidadTotales = cantidadTotal;
		filteredRankingCompraProducto = listaAuxRanking;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String goRankingVentaProductoMonto() {
		
		System.out.println("Metodo goRankingVentaProductoMonto()");
		
		DAOProducto = new DAOProducto();
		DAOVTA = new DAOventa();
		List<RankingProducto> listaAuxRanking = new ArrayList<RankingProducto>();
		List<Producto> listaProd = DAOProducto.getListadoProductos();
		listaRankingProducto = new ArrayList<RankingProducto>();
		filteredRankingVentaProducto = new ArrayList<RankingProducto>();
		fechaInicioRankingProducto = null;
		fechaFinalRankingProducto = null;
		DecimalFormat formato = new DecimalFormat("##,##0.00");
		float montoTotal = 0;
		for (Producto producto : listaProd) {
			RankingProducto ranking = new RankingProducto();
			ranking.setProducto(producto);
			float monto = 0;
			List<Venta> listaVent = new ArrayList<Venta>();
			List<VentasProducto> lista = DAOVTA
					.getListadoDetallePorProd(producto);
			for (VentasProducto ventasProducto : lista) {
				monto = monto + ventasProducto.getSubtotal();
				listaVent.add(ventasProducto.getVenta());
			}
			montoTotal = montoTotal + monto;
			ranking.setMonto(monto);
			ranking.setListaVenta(listaVent);
			listaAuxRanking.add(ranking);
		}

		Collections.sort(listaAuxRanking, new Comparator() {

			@Override
			public int compare(Object p1, Object p2) {
				// TODO Auto-generated method stub
				return new Float(((RankingProducto) p2).getMonto())
						.compareTo(new Float(((RankingProducto) p1).getMonto()));
			}
		});

		montosTotales = "$" + formato.format(montoTotal);
		filteredRankingVentaProducto = listaAuxRanking;
		listaRankingProducto = listaAuxRanking;
		return "rankingVentaProductoM";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void buscarRankingVentaProductoMonto() {
		
		System.out.println("Metodo buscarRankingVentaProductoMonto()");
		
		DAOProducto = new DAOProducto();
		DAOVTA = new DAOventa();
		List<RankingProducto> listaAuxRanking = new ArrayList<RankingProducto>();
		List<Producto> listaProd = DAOProducto.getListadoProductos();
		float montoTotal = 0;
		DecimalFormat formato = new DecimalFormat("##,##0.00");
		for (Producto producto : listaProd) {
			RankingProducto ranking = new RankingProducto();
			ranking.setProducto(producto);
			float monto = 0;
			List<Venta> listaVent = new ArrayList<Venta>();
			List<VentasProducto> lista = DAOVTA.getListadoDetallePorProd(
					producto, fechaInicioRankingProducto,
					fechaFinalRankingProducto);
			for (VentasProducto ventasProducto : lista) {
				monto = monto + ventasProducto.getSubtotal();
				listaVent.add(ventasProducto.getVenta());
			}
			montoTotal = montoTotal + monto;
			ranking.setMonto(monto);
			ranking.setListaVenta(listaVent);
			listaAuxRanking.add(ranking);
		}

		Collections.sort(listaAuxRanking, new Comparator() {

			@Override
			public int compare(Object p1, Object p2) {
				// TODO Auto-generated method stub
				return new Float(((RankingProducto) p2).getMonto())
						.compareTo(new Float(((RankingProducto) p1).getMonto()));
			}
		});

		montosTotales = "$" + formato.format(montoTotal);
		filteredRankingVentaProducto = listaAuxRanking;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String goRankingCompraProductoMonto() {
		
		System.out.println("Metodo goRankingCompraProductoMonto()");
		
		DAOProducto = new DAOProducto();
		DAOcompra = new DAOCompra();
		List<RankingProducto> listaAuxRanking = new ArrayList<RankingProducto>();
		List<Producto> listaProd = DAOProducto.getListadoProductos();
		listaRankingProducto = new ArrayList<RankingProducto>();
		filteredRankingCompraProducto = new ArrayList<RankingProducto>();
		fechaInicioRankingProducto = null;
		fechaFinalRankingProducto = null;
		float montoTotal = 0;
		DecimalFormat formato = new DecimalFormat("##,##0.00");
		for (Producto producto : listaProd) {
			RankingProducto ranking = new RankingProducto();
			ranking.setProducto(producto);
			float monto = 0;
			List<Compra> listaComp = new ArrayList<Compra>();
			List<DetalleCompra> lista = DAOcompra
					.getListadoDetallePorProd(producto);
			for (DetalleCompra detalleCompra : lista) {
				monto = monto + detalleCompra.getSubtotal();
				listaComp.add(detalleCompra.getCompra());
			}
			montoTotal = montoTotal + monto;
			ranking.setMonto(monto);
			ranking.setListaCompra(listaComp);
			listaAuxRanking.add(ranking);
		}

		Collections.sort(listaAuxRanking, new Comparator() {

			@Override
			public int compare(Object p1, Object p2) {
				// TODO Auto-generated method stub
				return new Float(((RankingProducto) p2).getMonto())
						.compareTo(new Float(((RankingProducto) p1).getMonto()));
			}
		});

		montosTotales = "$" + formato.format(montoTotal);
		filteredRankingCompraProducto = listaAuxRanking;
		listaRankingProducto = listaAuxRanking;
		return "rankingCompraProductoM";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void buscarRankingCompraProductoMonto() {
		
		System.out.println("Metodo buscarRankingCompraProductoMonto()");
		
		DAOProducto = new DAOProducto();
		DAOcompra = new DAOCompra();
		List<RankingProducto> listaAuxRanking = new ArrayList<RankingProducto>();
		List<Producto> listaProd = DAOProducto.getListadoProductos();
		float montoTotal = 0;
		DecimalFormat formato = new DecimalFormat("##,##0.00");
		for (Producto producto : listaProd) {
			RankingProducto ranking = new RankingProducto();
			ranking.setProducto(producto);
			float monto = 0;
			List<Compra> listaComp = new ArrayList<Compra>();
			List<DetalleCompra> lista = DAOcompra.getListadoDetallePorProd(
					producto, fechaInicioRankingProducto,
					fechaFinalRankingProducto);
			for (DetalleCompra detalleCompra : lista) {
				monto = monto + detalleCompra.getSubtotal();
				listaComp.add(detalleCompra.getCompra());
			}
			montoTotal = montoTotal + monto;
			ranking.setMonto(monto);
			ranking.setListaCompra(listaComp);
			listaAuxRanking.add(ranking);
		}

		Collections.sort(listaAuxRanking, new Comparator() {

			@Override
			public int compare(Object p1, Object p2) {
				// TODO Auto-generated method stub
				return new Float(((RankingProducto) p2).getMonto())
						.compareTo(new Float(((RankingProducto) p1).getMonto()));
			}
		});

		montosTotales = "$" + formato.format(montoTotal);
		filteredRankingCompraProducto = listaAuxRanking;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String goRankingVentaClienteMonto() {
		
		System.out.println("Metodo goRankingVentaClienteMonto()");
		
		DAOCliente = new DAOcliente();
		DAOVTA = new DAOventa();
		List<RankingCliente> listaAuxRanking = new ArrayList<RankingCliente>();
		List<Cliente> listaCli = DAOCliente.getListadoClientes();
		listaRankingCliente = new ArrayList<RankingCliente>();
		filteredRankingVentaCliente = new ArrayList<RankingCliente>();
		fechaInicioRankingCliente = null;
		fechaFinalRankingCliente = null;
		float montoTotal = 0;
		DecimalFormat formato = new DecimalFormat("##,##0.00");
		for (Cliente cliente : listaCli) {
			RankingCliente ranking = new RankingCliente();
			ranking.setCliente(cliente);
			float monto = 0;
			List<Venta> listaVent = DAOVTA.getVentasActPorCliente(cliente);
			for (Venta ventas : listaVent) {
				monto = monto + ventas.getMonto();
			}
			montoTotal = montoTotal + monto;
			ranking.setMonto(monto);
			ranking.setListaVenta(listaVent);
			listaAuxRanking.add(ranking);
		}

		Collections.sort(listaAuxRanking, new Comparator() {

			@Override
			public int compare(Object p1, Object p2) {
				// TODO Auto-generated method stub
				return new Float(((RankingCliente) p2).getMonto())
						.compareTo(new Float(((RankingCliente) p1).getMonto()));
			}
		});

		montosTotales = "$" + formato.format(montoTotal);
		filteredRankingVentaCliente = listaAuxRanking;
		listaRankingCliente = listaAuxRanking;
		return "rankingVentaCliente";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void buscarRankingVentaClienteMonto() {
		
		System.out.println("Metodo buscarRankingVentaClienteMonto()");
		
		DAOCliente = new DAOcliente();
		DAOVTA = new DAOventa();
		List<RankingCliente> listaAuxRanking = new ArrayList<RankingCliente>();
		List<Cliente> listaCli = DAOCliente.getListadoClientes();
		float montoTotal = 0;
		DecimalFormat formato = new DecimalFormat("##,##0.00");
		for (Cliente cliente : listaCli) {
			RankingCliente ranking = new RankingCliente();
			ranking.setCliente(cliente);
			float monto = 0;
			List<Venta> listaVent = DAOVTA.getVentasActPorCliente(cliente,
					fechaInicioRankingCliente, fechaFinalRankingCliente);
			for (Venta ventas : listaVent) {
				monto = monto + ventas.getMonto();
			}
			montoTotal = montoTotal + monto;
			ranking.setMonto(monto);
			ranking.setListaVenta(listaVent);
			listaAuxRanking.add(ranking);
		}

		Collections.sort(listaAuxRanking, new Comparator() {

			@Override
			public int compare(Object p1, Object p2) {
				// TODO Auto-generated method stub
				return new Float(((RankingCliente) p2).getMonto())
						.compareTo(new Float(((RankingCliente) p1).getMonto()));
			}
		});

		montosTotales = "$" + formato.format(montoTotal);
		filteredRankingVentaCliente = listaAuxRanking;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String goRankingCompraClienteMonto() {
		
		System.out.println("Metodo goRankingCompraMonto()");
		
		DAOProveedor = new DAOproveedor();
		DAOcompra = new DAOCompra();
		listaRankingCliente = new ArrayList<RankingCliente>();
		filteredRankingCompraCliente = new ArrayList<RankingCliente>();
		fechaInicioRankingCliente = null;
		fechaFinalRankingCliente = null;
		List<RankingCliente> listaAuxRanking = new ArrayList<RankingCliente>();
		List<Cliente> listaCli = DAOProveedor.getListadoProveedores();
		DecimalFormat formato = new DecimalFormat("##,##0.00");
		float montoTotal = 0;
		for (Cliente cliente : listaCli) {
			RankingCliente ranking = new RankingCliente();
			ranking.setCliente(cliente);
			float monto = 0;
			List<Compra> listaComp = DAOcompra.getComprasXProveedor(cliente);
			for (Compra compras : listaComp) {
				monto = monto + compras.getMonto();
			}
			montoTotal = montoTotal + monto;
			ranking.setMonto(monto);
			ranking.setListaCompra(listaComp);
			listaAuxRanking.add(ranking);
		}

		Collections.sort(listaAuxRanking, new Comparator() {

			@Override
			public int compare(Object p1, Object p2) {
				// TODO Auto-generated method stub
				return new Float(((RankingCliente) p2).getMonto())
						.compareTo(new Float(((RankingCliente) p1).getMonto()));
			}
		});

		montosTotales = "$" + formato.format(montoTotal);
		filteredRankingCompraCliente = listaAuxRanking;
		listaRankingCliente = listaAuxRanking;
		return "rankingCompraCliente";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void buscarRankingCompraClienteMonto() {
		
		System.out.println("Metodo buscarRankingCompraClienteMonto()");
		
		DAOProveedor = new DAOproveedor();
		DAOcompra = new DAOCompra();
		List<RankingCliente> listaAuxRanking = new ArrayList<RankingCliente>();
		List<Cliente> listaCli = DAOProveedor.getListadoProveedores();
		DecimalFormat formato = new DecimalFormat("##,##0.00");
		float montoTotal = 0;
		for (Cliente cliente : listaCli) {
			RankingCliente ranking = new RankingCliente();
			ranking.setCliente(cliente);
			float monto = 0;
			List<Compra> listaComp = DAOcompra.getComprasXProveedor(cliente,
					fechaInicioRankingCliente, fechaFinalRankingCliente);
			for (Compra compras : listaComp) {
				monto = monto + compras.getMonto();
			}
			montoTotal = montoTotal + monto;
			ranking.setMonto(monto);
			ranking.setListaCompra(listaComp);
			;
			listaAuxRanking.add(ranking);
		}
		montosTotales = "$" + formato.format(montoTotal);

		Collections.sort(listaAuxRanking, new Comparator() {

			@Override
			public int compare(Object p1, Object p2) {
				// TODO Auto-generated method stub
				return new Float(((RankingCliente) p2).getMonto())
						.compareTo(new Float(((RankingCliente) p1).getMonto()));
			}
		});

		filteredRankingCompraCliente = listaAuxRanking;
	}
	
	public String goPatrimonio(){
		
		System.out.println("Metodo goPatrimonio()");
		
		DecimalFormat formatear = new DecimalFormat("##,##0.00");
		montoTotalCaja = null;
		montoTotalCCCVString = null;
		montoTotalCCVirtual = null;
		montoTotalClientes = null;
		montoTotalProductos = null;
		montoTotalProveedores = null;
		montoTotalStockVirtual = null;
		totalActivos = null;
		totalPasivos = null;
		totalPatrimonio = null;
		DAOCaja = new DAOcaja();
		DAOProveedor = new DAOproveedor();
		DAOProducto = new DAOProducto();
		DAOcargaVirtual = new DAOCargaVirtual();
		DAOCliente = new DAOcliente();
		Caja cajaN = new Caja();
		cajaN = DAOCaja.obtenerUlt();
		float montoCaja = cajaN.getTotal();
		montoTotalCaja = formatear.format(montoCaja);
		List<Cliente> listaProveedores = new ArrayList<Cliente>();
		listaProveedores = DAOProveedor.getListadoProveedores();
		float montoProveedor = 0;
		for (Cliente cliente : listaProveedores) {
			montoProveedor = montoProveedor + cliente.getSaldo();
		}
		montoTotalProveedores = formatear.format(montoProveedor);
		List<Producto> listaProducto = new ArrayList<Producto>();
		listaProducto = DAOProducto.getListadoProductosAct();
		float montoProducto = 0;
		for (Producto producto : listaProducto) {
			float valor = producto.getStock() * producto.getPrecioCompra();
			montoProducto = montoProducto + valor;
		}
		montoTotalProductos = formatear.format(montoProducto);
		List<Cliente> listaCargaVirtual = new ArrayList<Cliente>();
		listaCargaVirtual = DAOcargaVirtual.getListCargaProv();
		float montoCVCC = 0;
		for (Cliente cliente : listaCargaVirtual) {
			montoCVCC = montoCVCC + cliente.getSaldo();
		}
		montoTotalCCVirtual = formatear.format(montoCVCC);
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		listaClientes = DAOCliente.getListadoClientes();
		float montoClientes = 0;
		for (Cliente cliente : listaClientes) {
			montoClientes = montoClientes + cliente.getSaldo();
		}
		montoTotalClientes = formatear.format(montoClientes);
		List<CargaVirtual> listaCargaVirtualS = new ArrayList<CargaVirtual>();
		listaCargaVirtualS = DAOcargaVirtual.getListadoCargas();
		float montoCargaVirtualS = 0;
		for (CargaVirtual cargaVirtual : listaCargaVirtualS) {
			montoCargaVirtualS = montoCargaVirtualS + cargaVirtual.getCantidadMonto();
		}
		montoTotalStockVirtual = formatear.format(montoCargaVirtualS);
		float activos = 0;
		float pasivos = 0;
		float patri = 0;
		activos = montoCaja + montoProducto + montoClientes + montoCargaVirtualS;
		pasivos = montoProveedor + montoCVCC;
		patri = activos - pasivos;
		totalActivos = formatear.format(activos);
		totalPasivos = formatear.format(pasivos);
		totalPatrimonio = formatear.format(patri);
		return "reportePatrimonio";
	}
	
	public void generarReportePatrimonio(){
		
		System.out.println("Metodo generarReportePatrimonio()");

		Map<String, Object> parameters = new HashMap<String, Object>();
		//System.out.println(montoTotalCaja + " " + montoTotalProductos + " " + montoTotalClientes + " " + montoTotalStockVirtual + " " + montoTotalProveedores + " " + 
		//" " + montoTotalCCVirtual + " " + totalActivos + " " + totalPasivos + " " + totalPatrimonio);
		parameters.put("caja", montoTotalCaja);
		parameters.put("productos", montoTotalProductos);
		parameters.put("cccli", montoTotalClientes);
		parameters.put("stockvirtual", montoTotalStockVirtual);
		parameters.put("ccpro", montoTotalProveedores);
		parameters.put("ccvirtual", montoTotalCCVirtual);
		parameters.put("subtotalactivo", totalActivos);
		parameters.put("subtotalpasivo", totalPasivos);
		parameters.put("total", totalPatrimonio);
		List<ExPlataforma> lista = new ArrayList<ExPlataforma>();
		ExPlataforma aux = new ExPlataforma();
		aux.setPlataforma(" ");
		lista.add(aux);

		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("reportes/reportePatrimonio.jrxml");
			JasperReport jasperReport;
			jasperReport = JasperCompileManager.compileReport(reportStream);
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(lista);

			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			facesContext.responseComplete();
			response.setContentType("application/pdf");

			response.addHeader("Content-disposition", "attachment;filename="
					+ "ReportePatrimonio.pdf");
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, source);
			JasperExportManager.exportReportToPdfStream(print,
					servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//////////////////////////////////////////////////////VISTACUENTASCORRIENTES///////////////////////////////////////////////
	
	private Cliente clienteVista;
	private Date inicioVistaCC;
	private Date finalVistaCC;
	private List<Cuentascorriente> listaVistaCuentasCorriente;
	private List<Cuentascorriente> filteredListadoVistaCC;

	public Cliente getClienteVista() {
		return clienteVista;
	}

	public void setClienteVista(Cliente clienteVista) {
		this.clienteVista = clienteVista;
	}

	public Date getInicioVistaCC() {
		return inicioVistaCC;
	}

	public void setInicioVistaCC(Date inicioVistaCC) {
		this.inicioVistaCC = inicioVistaCC;
	}

	public Date getFinalVistaCC() {
		return finalVistaCC;
	}

	public void setFinalVistaCC(Date finalVistaCC) {
		this.finalVistaCC = finalVistaCC;
	}

	public List<Cuentascorriente> getListaVistaCuentasCorriente() {
		return listaVistaCuentasCorriente;
	}

	public void setListaVistaCuentasCorriente(
			List<Cuentascorriente> listaVistaCuentasCorriente) {
		this.listaVistaCuentasCorriente = listaVistaCuentasCorriente;
	}

	public List<Cuentascorriente> getFilteredListadoVistaCC() {
		return filteredListadoVistaCC;
	}

	public void setFilteredListadoVistaCC(
			List<Cuentascorriente> filteredListadoVistaCC) {
		this.filteredListadoVistaCC = filteredListadoVistaCC;
	}
	
	public String goVistaCC(){
		DAOcc = new DAOcuentascorriente();
//		inicioVistaCC = new Date();
//		finalVistaCC = new Date();
		listaVistaCuentasCorriente = DAOcc.getListaCCCliente(clienteVista);
		return "cuentacorriente";
	}
	
	public void imprimirReporteVistaCC() {
		
		System.out.println("Metodo imprimirReporteVistaCC()");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<Cuentascorriente> listaReporte = new ArrayList<Cuentascorriente>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nombre", clienteVista.getNombre());
		parameters.put("apellido", clienteVista.getApellido());
		parameters.put("telefono", clienteVista.getTelefono());
		parameters.put("email", clienteVista.getMail());
		parameters.put("monto", clienteVista.getSaldoString());
		parameters.put("fechai", " ");
		parameters.put("fechaf", " ");

		if (inicioCCIJM != null && finalCCIJM != null) {
			parameters.put("fechai", "Desde: " + sdf.format(inicioCCIJM));
			parameters.put("fechaf", " - Hasta: " + sdf.format(finalCCIJM));
		}

		if (filteredListadoVistaCC == null) {
			listaReporte = listaVistaCuentasCorriente;
		} else {
			listaReporte = filteredListadoVistaCC;
		}

		String formaPrinteo = "attachment;filename=";

		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("reportes/CuentaCorriente.jrxml");
			JasperReport jasperReport;
			jasperReport = JasperCompileManager.compileReport(reportStream);
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(
					listaReporte);

			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			facesContext.responseComplete();
			response.setContentType("application/pdf");

			response.addHeader("Content-disposition", formaPrinteo
					+ "resumenCuentaCorriente.pdf");
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, source);
			JasperExportManager.exportReportToPdfStream(print,
					servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onfilteredDateVistaCC(){
		
		System.out.println("Metodo onfilteredDateVistaCC()");
		
		if(inicioVistaCC != null && finalVistaCC != null){
			listaVistaCuentasCorriente = DAOcc.getListaVistaCC(inicioVistaCC, finalVistaCC, clienteVista);
			filteredListadoVistaCC = listaVistaCuentasCorriente;
		}		
	}
	
	public void agregarUsuarioVista() {
		
		System.out.println("Metodo agregarUsuarioVista()");
		
		FacesMessage msg = null;
		RequestContext context = RequestContext.getCurrentInstance();
		boolean valueIn = false;
		DAOuser = new DAOusuario();			
		Usuario usuario = new Usuario();
		Role rol = new Role();
		if((nombreUsuario != null || nombreUsuario != "" || !nombreUsuario.isEmpty()) && 
				(username != null || username != "" || !username.isEmpty())){		
			if(usuarioID != null){
				usuario.setNombre(nombreUsuario);
				if(confirmNewPass != null && !confirmNewPass.isEmpty() && textOK == "Ok"){
					Helper helper = new Helper();
					String hash = helper.EncodePassword(confirmNewPass);
					usuario.setPassword(hash);
				}else{
					usuario.setPassword(password);
				}			
				usuario.setUsername(username);
				rol.setId(2);
				usuario.setRole(rol);
				usuario.setId(usuarioID);
				int respuesta = DAOuser.updateUsuario(usuario);
				if (respuesta == 1) {
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario: "
							+ nombreUsuario + " registrado correctamente.",
							null);
					valueIn = true;	
					setNombreUsuario(null);
					setUsername(null);
					setPassword(null);
					setUsuarioID(null);
					setCheckPass(false);
					setCheckButtonPass(false);
					setIdRolUsuario(0);							
				} else {
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Error: El Usuario: " + username
									+ " ya se encuentra registrado", null);
					valueIn = false;	
					setNombreUsuario(null);
					setUsername(null);
					setPassword(null);
					setUsuarioID(null);
					setCheckPass(false);
					setCheckButtonPass(false);
				}
			}else{	
				if(confirmNewPass != null && !confirmNewPass.isEmpty() && confirmNewPass != ""  && textOK.equals("Ok")){
					Helper helper = new Helper();
					String hash = helper.EncodePassword(newPass);
					usuario.setPassword(hash);
					setCurrentDate(new Date());
					usuario.setFechaRegistro(getCurrentDate());
					usuario.setEnabled(true);
					usuario.setNombre(nombreUsuario);
					usuario.setUsername(username);
					rol.setId(2);
					usuario.setRole(rol);
					int respuesta = DAOuser.agregarUsser(usuario);
					if (respuesta == 1) {
						msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario: "
								+ nombreUsuario + " registrado correctamente.",
								nombreUsuario);
						valueIn = true;	
						setNombreUsuario(null);
						setUsername(null);
						setPassword(null);
						setUsuarioID(null);
						setCheckPass(false);
						setCheckButtonPass(false);
						setIdRolUsuario(0);							
					} else {
						msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Error: El Usuario: " + nombreUsuario
										+ " ya se encuentra registrado", nombreUsuario);
						valueIn = false;	
						setNombreUsuario(null);
						setUsername(null);
						setPassword(null);
						setUsuarioID(null);
						setCheckPass(false);
						setCheckButtonPass(false);
					}
				}else{
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
							"Todos los campos son obligatorios");
					valueIn = false;	
					setNombreUsuario(null);
					setUsername(null);
					setPassword(null);
					setUsuarioID(null);
					setCheckPass(false);
					setCheckButtonPass(false);
				}			
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
					"Todos los campos son obligatorios");
			valueIn = false;	
			setNombreUsuario(null);
			setUsername(null);
			setPassword(null);
			setUsuarioID(null);
			setCheckPass(false);
			setCheckButtonPass(false);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);				
		context.addCallbackParam("valueIn", valueIn);		
	}
	
	/////////////////////////////////////////CAJA//////////////////////////////////////////////////////////////////
	
	private List<Caja> listadoCaja;
	private List<Caja> filteredCaja;
	private Caja caja;
	private DAOcaja DAOCaja;
	private int tipoCaja;
	private Date fechaInicioCaja;
	private Date fechaFinCaja;
	private String montoActualCaja;

	public String getMontoActualCaja() {
		return montoActualCaja;
	}

	public void setMontoActualCaja(String montoActualCaja) {
		this.montoActualCaja = montoActualCaja;
	}

	public Date getFechaInicioCaja() {
		return fechaInicioCaja;
	}

	public void setFechaInicioCaja(Date fechaInicioCaja) {
		this.fechaInicioCaja = fechaInicioCaja;
	}

	public Date getFechaFinCaja() {
		return fechaFinCaja;
	}

	public void setFechaFinCaja(Date fechaFinCaja) {
		this.fechaFinCaja = fechaFinCaja;
	}

	public int getTipoCaja() {
		return tipoCaja;
	}

	public void setTipoCaja(int tipoCaja) {
		this.tipoCaja = tipoCaja;
	}

	public DAOcaja getDAOCaja() {
		return DAOCaja;
	}

	public void setDAOCaja(DAOcaja dAOCaja) {
		DAOCaja = dAOCaja;
	}

	public List<Caja> getListadoCaja() {
		return listadoCaja;
	}

	public void setListadoCaja(List<Caja> listadoCaja) {
		this.listadoCaja = listadoCaja;
	}

	public List<Caja> getFilteredCaja() {
		return filteredCaja;
	}

	public void setFilteredCaja(List<Caja> filteredCaja) {
		this.filteredCaja = filteredCaja;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}
	
	public String goCaja(){
		caja = new Caja();
		DAOCaja = new DAOcaja();
		Caja cajaN = new Caja();
		cajaN = DAOCaja.obtenerUlt();
		montoActualCaja = cajaN.getTotalString();
		listadoCaja = DAOCaja.getListadoCaja();
		filteredCaja = listadoCaja;
		tipoCaja = 0;
		fechaInicioCaja = null;
		fechaFinCaja = null;
		return "cajaIJM";
	}
	
	public void guardarExtraccion() {
		System.out.println("Metodo guardarExtraccion()");
		FacesMessage msg = null;
		if(caja.getMonto() != 0 && caja.getConcepto() != null && caja.getConcepto() != "" && !caja.getConcepto().isEmpty()){
			setCurrentDate(new Date());
			DAOCaja = new DAOcaja();
			Caja cajaN = new Caja();
			cajaN = DAOCaja.obtenerUlt();
			caja.setFecha(getCurrentDate());
			caja.setIdMovimiento(1);
			caja.setUsuarioBean(usuario);
			float montoForm = 0;
			int valor = 0;
			float monto = 0;
			montoForm = caja.getMonto();
			if(caja.getTipo().equals("Salida")){
				montoForm = montoForm * (-1);
			}
			if(cajaN.getId() != 0){
				monto = cajaN.getTotal();
			}
			monto = monto + montoForm;
			caja.setTotal(monto);
			valor = DAOCaja.insertCaja(caja);
			if (valor != 0) {
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Movimiento Guardado correctamente.", null);
				caja = new Caja();
				goCaja();
			} else {
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Error: El Movimiento no se pudo Guardar.", null);
				caja = new Caja();
				goCaja();
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error! Todos los campos son obligatorios", null);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);		
	}
	
	public String cancelarExtraccion() {
		caja = new Caja();
		return "cajaIJM";
	}
	
	public List<Caja> getCajasXTipo(int estado){
		System.out.println("Metodo getCajasXTipo()");
		DAOCaja = new DAOcaja();
		filteredCaja = new ArrayList<Caja>();
		listadoCaja = new ArrayList<Caja>();
		List<Caja> lista = new ArrayList<Caja>();
		if (estado == 0) {
			lista = DAOCaja.getListadoCaja();
			setFilteredCaja(lista);
			setTipoCaja(0);
		} else if (estado == 1) {
			lista = DAOCaja.getListadoCajaSalida();
			setFilteredCaja(lista);
			setTipoCaja(1);
		} else {
			lista = DAOCaja.getListadoCajaEntrada();
			setFilteredCaja(lista);
			setTipoCaja(2);
		}
		return filteredCaja;
	}
	
	public void buscarCajas(){
		
		System.out.println("Metodo buscarCajas()");
		DAOCaja = new DAOcaja();
		List<Caja> lista = new ArrayList<Caja>();
		if(fechaInicioCaja != null && fechaFinCaja != null){
			if(tipoCaja == 0){
				lista = DAOCaja.getListadoCaja(fechaInicioCaja, fechaFinCaja);
			}
			if(tipoCaja == 1){
				lista = DAOCaja.getListadoCajaSalida(fechaInicioCaja, fechaFinCaja);
			}
			if(tipoCaja == 2){
				lista = DAOCaja.getListadoCajaEntrada(fechaInicioCaja, fechaFinCaja);
			}
			listadoCaja = new ArrayList<Caja>();
			filteredCaja = new ArrayList<Caja>();
			filteredCaja = lista;
		}else{
			FacesMessage msg = new FacesMessage("Debe Seleccionar ambas fechas");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public int guardarCaja(Caja cajaN){
		
		System.out.println("Metodo guardarCaja()");
		
		int valor = 0;
		if(cajaN.getMonto() != 0 && cajaN.getConcepto() != null && cajaN.getConcepto() != "" && !cajaN.getConcepto().isEmpty()){
			setCurrentDate(new Date());
			DAOCaja = new DAOcaja();
			Caja cajaNueva = new Caja();
			cajaNueva = DAOCaja.obtenerUlt();
			float montoMov = 0;
			float monto = 0;
			montoMov = cajaN.getMonto();
			if(cajaN.getTipo().equals("Salida")){
				montoMov = montoMov * (-1);
			}
			if(cajaNueva.getId() != 0){
				monto = cajaNueva.getTotal();
			}
			monto = monto + montoMov;
			cajaN.setTotal(monto);
			valor = DAOCaja.insertCaja(cajaN);
		}else{
			valor = 0;
		}
		return valor;
	}
	
	//////////////////////////////////////////////DEBITO-CREDITO////////////////////////////////////////////////////////////
	
	private List<Cliente> listaClientesDebito;
	private List<Cliente> listaClientesCredito;
	private float montoCredito;
	private float montoDebito;
	private String conceptoDebito;
	private String conceptoCredito;
	private int idClienteCredito;
	private int idClienteDebito;

	public int getIdClienteCredito() {
		return idClienteCredito;
	}

	public void setIdClienteCredito(int idClienteCredito) {
		this.idClienteCredito = idClienteCredito;
	}

	public int getIdClienteDebito() {
		return idClienteDebito;
	}

	public void setIdClienteDebito(int idClienteDebito) {
		this.idClienteDebito = idClienteDebito;
	}

	public List<Cliente> getListaClientesDebito() {
		DAOCliente = new DAOcliente();
		listaClientesDebito = DAOCliente.getListadoClientesProveedores();
		return listaClientesDebito;
	}

	public void setListaClientesDebito(List<Cliente> listaClientesDebito) {
		this.listaClientesDebito = listaClientesDebito;
	}

	public List<Cliente> getListaClientesCredito() {
		DAOCliente = new DAOcliente();
		listaClientesCredito = new ArrayList<Cliente>();
		listaClientesCredito = DAOCliente.getListadoClientesProveedores();
		return listaClientesCredito;
	}

	public void setListaClientesCredito(List<Cliente> listaClientesCredito) {
		this.listaClientesCredito = listaClientesCredito;
	}

	public float getMontoCredito() {
		return montoCredito;
	}

	public void setMontoCredito(float montoCredito) {
		this.montoCredito = montoCredito;
	}

	public float getMontoDebito() {
		return montoDebito;
	}

	public void setMontoDebito(float montoDebito) {
		this.montoDebito = montoDebito;
	}

	public String getConceptoDebito() {
		return conceptoDebito;
	}

	public void setConceptoDebito(String conceptoDebito) {
		this.conceptoDebito = conceptoDebito;
	}

	public String getConceptoCredito() {
		return conceptoCredito;
	}

	public void setConceptoCredito(String conceptoCredito) {
		this.conceptoCredito = conceptoCredito;
	}
	
	public String goCredito(){
		idClienteCredito = 0;
		montoCredito = 0;
		conceptoCredito = "";
		fechaPago = new Date();
		return "notaCredito";
	}
	
	public String goDebito(){
		idClienteDebito = 0;
		montoDebito = 0;
		conceptoDebito = "";
		fechaPago = new Date();
		return "notaDebito";
	}
	
	public void guardarCredito(){
		FacesMessage msg = null;
		if(idClienteCredito != 0 && montoCredito != 0){
			setCurrentDate(new Date());
			DAOcc = new DAOcuentascorriente();
			DAOCliente = new DAOcliente();
			cuentaDAO = new DAOCuentas();
			CuentasCorrientes cuentaCC = new CuentasCorrientes();
			Cuenta cuentaPersona = new Cuenta();
			Cliente cli = DAOCliente.getClientePorId(idClienteCredito);
			float saldo = cli.getSaldo();
			float monto;
			if(cli.getTipo().equals("c")){
				monto = saldo - montoCredito;
			}else{
				monto = saldo + montoCredito;
			}
			cuentaPersona.setCliente(cli);
			cuentaPersona.setDetalle("Crédito - Concepto: " + conceptoCredito);
			cuentaPersona.setFecha(fechaPago);
			cuentaPersona.setUsuario(usuario);
			if(cli.getTipo().equals("c")){
				cuentaPersona.setHaber(montoCredito);
			}else{
				cuentaPersona.setDebe(montoCredito);
			}
			cuentaPersona.setMonto(montoCredito);
			cuentaPersona.setSaldo(monto);
			cli.setSaldo(monto);
			int cc = cuentaCC.insertarCCC(cuentaPersona);
			if(cc != 0){
				idClienteCredito = 0;
				montoCredito = 0;
				conceptoCredito = "";
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "El credito se guardo con Éxito", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error, no se pudo guardar el cambio", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "El cliente o proveedor y el monto son obligatorios", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void guardarDebito(){
		FacesMessage msg = null;
		if(idClienteDebito != 0 && montoDebito != 0){
			setCurrentDate(new Date());
			DAOcc = new DAOcuentascorriente();
			cuentaDAO = new DAOCuentas();
			DAOCliente = new DAOcliente();
			CuentasCorrientes cuentaCC = new CuentasCorrientes();
			Cuenta cuentaPersona = new Cuenta();
			Cliente cli = DAOCliente.getClientePorId(idClienteDebito);
			float saldo = cli.getSaldo();
			float monto;
			if(cli.getTipo().equals("c")){
				monto = saldo + montoDebito;
			}else{
				monto = saldo - montoDebito;
			}
			cuentaPersona.setCliente(cli);
			cuentaPersona.setDetalle("Débito - Concepto: " + conceptoDebito);
			cuentaPersona.setFecha(fechaPago);
			cuentaPersona.setMonto(montoDebito);
			cuentaPersona.setUsuario(usuario);
			if(cli.getTipo().equals("c")){
				cuentaPersona.setDebe(montoDebito);
			}else{
				cuentaPersona.setHaber(montoDebito);
			}
			cli.setSaldo(monto);
			int cc = cuentaCC.insertarCCC(cuentaPersona);
			if(cc != 0){
				idClienteDebito = 0;
				montoDebito = 0;
				conceptoDebito = "";
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "El debito se guardo con Éxito", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error, no se pudo guardar el cambio", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "El cliente o proveedor y el monto son obligatorios", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void cancelarCreditoDebito(){
		idClienteCredito = 0;
		idClienteDebito = 0;
		montoCredito = 0;
		montoDebito = 0;
		conceptoCredito = "";
		conceptoDebito = "";
		fechaPago = new Date();
	}

	/**
	 * @param Object Cuenta
	 * @return int 1 si es correcto 0 si no se pudo insertar
	 * metodo que realiza el proceso de incersion de cuenta corriente y actualizacion de los campos que siguen.
	 */
//	@SuppressWarnings("deprecation")
//	private int insertarCuentaCorriente(Cuenta cuenta){
//		Date fechaFinal = new Date();
//		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
//		String fechaIni = formato.format(cuenta.getFecha());
//		Date fechaPrincipio = null;
//		try {
//			fechaPrincipio = formato.parse(fechaIni);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
//		int month = fechaPrincipio.getMonth();
//		month = month - 6;		
//		fechaPrincipio.setMonth(month);
//		List<Cuenta> listAux = new ArrayList<Cuenta>();
//		listAux = cuentaDAO.getListaOrdenadaPorFecha(fechaPrincipio, fechaFinal, cuenta.getCliente());
//		List<Cuenta> listAux2 = new ArrayList<Cuenta>();
//		Cuenta cuentaAnterior = new Cuenta();
//		for (Cuenta cuenta2 : listAux) {
//			if(cuenta2.getFecha().compareTo(cuenta.getFecha()) == 1){
//				listAux2.add(cuenta2);
//			}else{
//				cuentaAnterior = new Cuenta();
//				cuentaAnterior = cuenta2;
//			}
//		}
//		float saldo = cuentaAnterior.getSaldo();
//		if(cuenta.getDebe() != 0){
//			saldo = saldo + cuenta.getDebe();
//		}
//		if(cuenta.getHaber() != 0){
//			saldo = saldo - cuenta.getHaber();
//		}
//		cuenta.setSaldo(saldo);
//		int insert = cuentaDAO.insertar(cuenta);
//		if(insert != 0){
//			boolean update = true;
//			for(Cuenta cuenta2 : listAux2){
//				float sldo = cuenta2.getSaldo();
//				if(cuenta.getDebe() != 0){					
//					sldo = sldo + cuenta.getDebe();
//				}
//				if(cuenta.getHaber() != 0){
//					sldo = sldo - cuenta.getHaber();
//				}
//				cuenta2.setSaldo(sldo);
//				if(cuentaDAO.update(cuenta2) == 0){
//					update = false;
//				}
//			}
//			if(update){
//				return 1;
//			}else{
//				return 0;
//			}
//		}else{
//			return 0;
//		}
//	}
	
	public void Proceso2(){
		DAOCliente = new DAOcliente();
		DAOcc = new DAOcuentascorriente();
		cuentaDAO = new DAOCuentas();
		boolean correcto = true;
		Cliente cli = DAOCliente.getClientePorId(69);
		float fSaldo = 0;
		List<Cuentascorriente> listaCuentas = DAOcc.getListaCCCliente(cli);
		for (Cuentascorriente cuentascorriente : listaCuentas) {
			Cuenta cuenta = new Cuenta();
			cuenta.setCliente(cli);
			cuenta.setFecha(cuentascorriente.getFechaAlta());
			cuenta.setUsuario(cuentascorriente.getUsuario());
			float dMonto = cuentascorriente.getMonto();
			float fMonto = dMonto;
			
			if(cuentascorriente.getTipoMovimiento().equals("Inicializacion Cuenta Corriente")){
				cuenta.setDebe(fMonto);
				cuenta.setMonto(fMonto);
				cuenta.setSaldo(fMonto);
				cuenta.setDetalle("Inicializacion de Cuenta Corriente");
				fSaldo = fMonto;
			}
			if(cuentascorriente.getTipoMovimiento().equals("VENTA")){
				fSaldo = fSaldo + fMonto;
				cuenta.setDebe(fMonto);
				cuenta.setMonto(fMonto);					
				cuenta.setIdMovimiento(cuentascorriente.getIdMovimiento());
				cuenta.setNombreTabla("venta");
				cuenta.setDetalle("Venta Nro: " + cuentascorriente.getIdMovimiento());
				cuenta.setSaldo(fSaldo);
			}
			if(cuentascorriente.getTipoMovimiento().equals("NOTA CREDITO")){
				fSaldo = fSaldo - fMonto;
				cuenta.setHaber(fMonto);
				cuenta.setMonto(fMonto);					
				cuenta.setIdMovimiento(cuentascorriente.getIdMovimiento());
				cuenta.setNombreTabla("notacredito");
				cuenta.setDetalle("Nota de Crédito");
				cuenta.setSaldo(fSaldo);
			}
			if(cuentascorriente.getTipoMovimiento().equals("PAGO")){
				fSaldo = fSaldo - fMonto;
				cuenta.setHaber(fMonto);
				cuenta.setMonto(fMonto);
				cuenta.setIdMovimiento(cuentascorriente.getIdMovimiento());
				cuenta.setNombreTabla("pago");
				cuenta.setDetalle("Pago");
				cuenta.setSaldo(fSaldo);
			}
			if(cuentascorriente.getTipoMovimiento().contains("COMPRA CARGA VIRTUAL -")){
				String mvto = cuentascorriente.getTipoMovimiento();
				mvto = mvto.replace("COMPRA CARGA VIRTUAL -", "");
				fSaldo = fSaldo + fMonto;
				cuenta.setDebe(fMonto);
				cuenta.setMonto(fMonto);
				cuenta.setIdMovimiento(cuentascorriente.getIdMovimiento());
				cuenta.setNombreTabla("movimiento_virtual");
				cuenta.setDetalle("Compra de carga de carga virtual - Plataforma: " + mvto);
				cuenta.setSaldo(fSaldo);
			}
			if(cuentascorriente.getTipoMovimiento().contains("DEBITO -")){
				String mvto = cuentascorriente.getTipoMovimiento();
				mvto = mvto.replace("DEBITO -", "");
				fSaldo = fSaldo + fMonto;
				cuenta.setDebe(fMonto);
				cuenta.setDetalle("Débito - Concepto: " + mvto);
				cuenta.setMonto(fMonto);
				cuenta.setSaldo(fSaldo);
			}
			if(cuentascorriente.getTipoMovimiento().contains("CREDITO -")){
				String mvto = cuentascorriente.getTipoMovimiento();
				mvto = mvto.replace("CREDITO -", "");
				fSaldo = fSaldo - fMonto;
				cuenta.setHaber(fMonto);
				cuenta.setDetalle("Crédito - Concepto: " + mvto);
				cuenta.setMonto(fMonto);
				cuenta.setSaldo(fSaldo);
			}
			if(cuentaDAO.insertar(cuenta) == 0){
				correcto = false;
			}
		}
		FacesMessage msg = null;
		if(correcto){
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se actualizo la cuenta corriente!", null);
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrio un error!", null);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void Proceso(){
		DAOCliente = new DAOcliente();
		DAOProveedor = new DAOproveedor();
		DAOcargaVirtual = new DAOCargaVirtual();
		DAOcc = new DAOcuentascorriente();
		cuentaDAO = new DAOCuentas();
		List<Cliente> listaAux = DAOCliente.getListadoClientes();		
		boolean correcto = true;
		for (Cliente cliente : listaAux) {
			float fSaldo = 0;
			List<Cuentascorriente> listaCuentas = DAOcc.getListaCCCliente(cliente);
			for (Cuentascorriente cuentascorriente : listaCuentas) {
				Cuenta cuenta = new Cuenta();
				cuenta.setCliente(cliente);
				cuenta.setFecha(cuentascorriente.getFechaAlta());
				cuenta.setUsuario(cuentascorriente.getUsuario());
				float dMonto = cuentascorriente.getMonto();
				float fMonto = dMonto;
				
				if(cuentascorriente.getTipoMovimiento().equals("Inicializacion Cuenta Corriente")){
					cuenta.setDebe(fMonto);
					cuenta.setMonto(fMonto);
					cuenta.setSaldo(fMonto);
					cuenta.setDetalle("Inicializacion de Cuenta Corriente");
					fSaldo = fMonto;
				}
				if(cuentascorriente.getTipoMovimiento().equals("VENTA")){
					fSaldo = fSaldo + fMonto;
					cuenta.setDebe(fMonto);
					cuenta.setMonto(fMonto);					
					cuenta.setIdMovimiento(cuentascorriente.getIdMovimiento());
					cuenta.setNombreTabla("venta");
					cuenta.setDetalle("Venta Nro: " + cuentascorriente.getIdMovimiento());
					cuenta.setSaldo(fSaldo);
				}
				if(cuentascorriente.getTipoMovimiento().equals("NOTA CREDITO")){
					fSaldo = fSaldo - fMonto;
					cuenta.setHaber(fMonto);
					cuenta.setMonto(fMonto);					
					cuenta.setIdMovimiento(cuentascorriente.getIdMovimiento());
					cuenta.setNombreTabla("notacredito");
					cuenta.setDetalle("Nota de Crédito");
					cuenta.setSaldo(fSaldo);
				}
				if(cuentascorriente.getTipoMovimiento().equals("PAGO")){
					fSaldo = fSaldo - fMonto;
					cuenta.setHaber(fMonto);
					cuenta.setMonto(fMonto);
					cuenta.setIdMovimiento(cuentascorriente.getIdMovimiento());
					cuenta.setNombreTabla("pago");
					cuenta.setDetalle("Pago");
					cuenta.setSaldo(fSaldo);
				}
				if(cuentascorriente.getTipoMovimiento().contains("COMPRA CARGA VIRTUAL -")){
					String mvto = cuentascorriente.getTipoMovimiento();
					mvto = mvto.replace("COMPRA CARGA VIRTUAL -", "");
					fSaldo = fSaldo + fMonto;
					cuenta.setDebe(fMonto);
					cuenta.setMonto(fMonto);
					cuenta.setIdMovimiento(cuentascorriente.getIdMovimiento());
					cuenta.setNombreTabla("movimiento_virtual");
					cuenta.setDetalle("Compra de carga de carga virtual - Plataforma: " + mvto);
					cuenta.setSaldo(fSaldo);
				}
				if(cuentascorriente.getTipoMovimiento().contains("DEBITO -")){
					String mvto = cuentascorriente.getTipoMovimiento();
					mvto = mvto.replace("DEBITO -", "");
					fSaldo = fSaldo + fMonto;
					cuenta.setDebe(fMonto);
					cuenta.setDetalle("Débito - Concepto: " + mvto);
					cuenta.setMonto(fMonto);
					cuenta.setSaldo(fSaldo);
				}
				if(cuentascorriente.getTipoMovimiento().contains("CREDITO -")){
					String mvto = cuentascorriente.getTipoMovimiento();
					mvto = mvto.replace("CREDITO -", "");
					fSaldo = fSaldo - fMonto;
					cuenta.setHaber(fMonto);
					cuenta.setDetalle("Crédito - Concepto: " + mvto);
					cuenta.setMonto(fMonto);
					cuenta.setSaldo(fSaldo);
				}
				if(cuentaDAO.insertar(cuenta) == 0){
					correcto = false;
				}
			}
		}
		List<Cliente> listAuxProv = DAOProveedor.getListadoProveedores();
		boolean correcto2 = true;
		for (Cliente cliente : listAuxProv) {
			float fSaldo = 0;
			List<Cuentascorriente> listaCuentas = DAOcc.getListaCCProveedor(cliente);
			for (Cuentascorriente cuentascorriente : listaCuentas) {
				Cuenta cuenta = new Cuenta();
				cuenta.setCliente(cliente);
				cuenta.setFecha(cuentascorriente.getFechaAlta());
				cuenta.setUsuario(cuentascorriente.getUsuario());
				float dMonto = cuentascorriente.getMonto();
				float fMonto = dMonto;				
				if(cuentascorriente.getTipoMovimiento().equals("Inicializacion Cuenta Corriente")){
					cuenta.setDebe(fMonto);
					cuenta.setMonto(fMonto);
					cuenta.setSaldo(fMonto);
					cuenta.setDetalle("Inicializacion de Cuenta Corriente");
					fSaldo = fMonto;
				}
				if(cuentascorriente.getTipoMovimiento().equals("COMPRA")){
					fSaldo = fSaldo + fMonto;
					cuenta.setDebe(fMonto);
					cuenta.setMonto(fMonto);					
					cuenta.setIdMovimiento(cuentascorriente.getIdMovimiento());
					cuenta.setNombreTabla("compra");
					cuenta.setDetalle("Compra Nro: " + cuentascorriente.getIdMovimiento());
					cuenta.setSaldo(fSaldo);
				}
				if(cuentascorriente.getTipoMovimiento().equals("PAGO")){
					fSaldo = fSaldo - fMonto;
					cuenta.setHaber(fMonto);
					cuenta.setMonto(fMonto);
					cuenta.setIdMovimiento(cuentascorriente.getIdMovimiento());
					cuenta.setNombreTabla("pago");
					cuenta.setDetalle("Pago");
					cuenta.setSaldo(fSaldo);
				}
				if(cuentascorriente.getTipoMovimiento().contains("DEBITO -")){
					String mvto = cuentascorriente.getTipoMovimiento();
					mvto = mvto.replace("DEBITO -", "");
					fSaldo = fSaldo - fMonto;
					cuenta.setHaber(fMonto);
					cuenta.setDetalle("Débito - Concepto: " + mvto);
					cuenta.setMonto(fMonto);
					cuenta.setSaldo(fSaldo);
				}
				if(cuentascorriente.getTipoMovimiento().contains("CREDITO -")){
					String mvto = cuentascorriente.getTipoMovimiento();
					mvto = mvto.replace("CREDITO -", "");
					fSaldo = fSaldo + fMonto;
					cuenta.setDebe(fMonto);
					cuenta.setDetalle("Crédito - Concepto: " + mvto);
					cuenta.setMonto(fMonto);
					cuenta.setSaldo(fSaldo);
				}
				if(cuentaDAO.insertar(cuenta) == 0){
					correcto2 = false;
				}
			}
		}
		List<Cliente> listAuxVirtual = DAOcargaVirtual.getListCargaProv();
		boolean correcto3 = true;
		for (Cliente cliente : listAuxVirtual) {
			float fSaldo = 0;
			List<Cuentascorriente> listaCuentas = DAOcc.getListaCCProveedor(cliente);
			for (Cuentascorriente cuentascorriente : listaCuentas) {
				Cuenta cuenta = new Cuenta();
				cuenta.setCliente(cliente);
				cuenta.setFecha(cuentascorriente.getFechaAlta());
				cuenta.setUsuario(cuentascorriente.getUsuario());
				float dMonto = cuentascorriente.getMonto();
				float fMonto = dMonto;				
				if(cuentascorriente.getTipoMovimiento().contains("Acreditacion -")){
					cuenta.setMonto(fMonto);
					cuenta.setSaldo(fSaldo);
					cuenta.setDetalle(cuentascorriente.getTipoMovimiento());
				}
				if(cuentascorriente.getTipoMovimiento().contains("Debito -")){
					cuenta.setMonto(fMonto);
					cuenta.setSaldo(fSaldo);
					cuenta.setDetalle(cuentascorriente.getTipoMovimiento());
				}
				if(cuentascorriente.getTipoMovimiento().contains("COMPRA -")){
					fSaldo = fSaldo + fMonto;
					cuenta.setDebe(fMonto);					
					cuenta.setIdMovimiento(cuentascorriente.getIdMovimiento());
					cuenta.setNombreTabla("compra_cargavirtual");
					cuenta.setDetalle("Compra Nro: " + cuentascorriente.getIdMovimiento());
					cuenta.setSaldo(fSaldo);
				}
				if(cuentascorriente.getTipoMovimiento().equals("PAGO")){
					fSaldo = fSaldo - fMonto;
					cuenta.setHaber(fMonto);
					cuenta.setIdMovimiento(cuentascorriente.getIdMovimiento());
					cuenta.setNombreTabla("pago");
					cuenta.setDetalle("Pago");
					cuenta.setSaldo(fSaldo);
				}
				if(cuentaDAO.insertar(cuenta) == 0){
					correcto3 = false;
				}
			}
		}
		FacesMessage msg = null;
		if(correcto && correcto2 && correcto3){
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se actualizo la cuenta corriente!", null);
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrio un error!", null);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	private StreamedContent file2;
	private UploadedFile fileBackup;
	private boolean backupCargado;
	
	public StreamedContent getFile2() {
		generarBackup();
		return file2;
	}

	public void setFile2(StreamedContent file2) {
		this.file2 = file2;
	}

	public UploadedFile getFileBackup() {
		return fileBackup;
	}

	public void setFileBackup(UploadedFile fileBackup) {
		this.fileBackup = fileBackup;
	}

	public boolean isBackupCargado() {
		return backupCargado;
	}

	public void setBackupCargado(boolean backupCargado) {
		this.backupCargado = backupCargado;
	}
	
	public void onClickBackup() {
		backupCargado = false;
	}

	public void generarBackup(){
		Runtime runtime = Runtime.getRuntime();
		try {
			ServletContext contexto = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
			String path = contexto.getRealPath("/");
			System.out.println(path);
			Process child = runtime.exec("mysqldump --opt --password=37zoBUsupo --user=root --databases ijmdb -r " + path + "backup.sql");
//			Process child = runtime.exec("C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin\\mysqldump.exe --opt "
//					+ "--password=root --user=root --databases ijmdb -r " + path + "backup.sql");
			if(child.waitFor() == 0){
				System.out.println("Proceso completo");
				InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).
						getResourceAsStream("backup.sql");
				SimpleDateFormat dateformat = new SimpleDateFormat("ddMMyy");
				Date ahora = new Date();
				String nombre_archivo = "backup_" + dateformat.format(ahora) + ".sql";
				file2 = new DefaultStreamedContent(stream, "application/xml-dtd", nombre_archivo);
			}else{
				System.out.println("Proceso incompleto");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String goBackup() {
		fileBackup = null;
		backupCargado = false;
		return "backup";
	}
	
	public void uploadBackup(FileUploadEvent event) {
		System.out.println("Metodo uploadBackup()");
		try {
			fileBackup = null;
			fileBackup = event.getFile();		
			copyBackup(fileBackup.getFileName(), fileBackup.getInputstream());
			backupCargado = true;
			FacesMessage message = new FacesMessage("Exito "+ event.getFile()
					.getFileName() + " fue cargado.", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void copyBackup(String fileName, InputStream in) {
		try {
			ServletContext contexto = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();
			String path = contexto.getRealPath("/");
			String nombre_archivo = "copia_backup.sql";
			OutputStream out = new FileOutputStream(new File(path
					+ nombre_archivo));
			ruta = path + nombre_archivo;
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			in.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void restaurarBackup() {
		System.out.println("restaurarBackup()");
		Runtime runtime = Runtime.getRuntime();
		try {
			ServletContext contexto = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
			String path = contexto.getRealPath("/");
			System.out.println(path);
			Process child = runtime.exec("mysql --password=37zoBUsupo --user=root ijmdb");
//			Process child = runtime.exec("C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin\\mysql.exe "
//					+ "--password=root --user=root ijmdb");
			
			final InputStream es = child.getErrorStream();
			Thread hiloError = new Thread() {
			   public void run() {
			      try {
			         byte[] buffer = new byte[1024];
			         int leido = es.read(buffer);
			         while (leido > 0) {
//			            System.out.println(new String(buffer, 0, leido));
			            leido = es.read(buffer);
			         }
			         es.close();
			      } catch (Exception e) {
			    	 System.out.println("Error aca!");
			         e.printStackTrace();
			      }
			   }
			};
			hiloError.start();
			
			OutputStream os = child.getOutputStream();
			FileInputStream fis = new FileInputStream(path + "copia_backup.sql");

			byte buffer[] = new byte[1024];
			int leido = fis.read(buffer);
			while (leido > 0) {
//			   System.out.println(leido);
			   os.write(buffer, 0, leido);
			   leido = fis.read(buffer);
			}
			os.close();
			fis.close();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Base de Datos restaurada con Éxito!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}catch(Exception e){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un Error al restaurar la Base de Datos!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			System.out.println("Error aqui!");
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////CARGA DESCARGA EXCELS/////////////////////////////////////////////
	
	private UploadedFile fileExcelUno;
	private UploadedFile fileExcelDos;
	private UploadedFile fileExcelTres;
	private StreamedContent ctacteUno;
	private StreamedContent ctacteDos;
	private StreamedContent ctacteTres;
	private StreamedContent ctacteCuatro;
	private StreamedContent stocksUno;
	private StreamedContent stocksDos;
	private StreamedContent stocksTres;
	private StreamedContent stocksCuatro;
	private StreamedContent ventasUno;
	private StreamedContent ventasDos;
	private StreamedContent ventasTres;
	private StreamedContent ventasCuatro;
	private DAOArchivo archivoDAO = new DAOArchivo();
	private Excel archivoUno;
	private Excel archivoDos;
	private Excel archivoTres;
	private Excel archivoCuatro;

	public UploadedFile getFileExcelUno() {
		return fileExcelUno;
	}

	public void setFileExcelUno(UploadedFile fileExcelUno) {
		this.fileExcelUno = fileExcelUno;
	}
	
	public UploadedFile getFileExcelDos() {
		return fileExcelDos;
	}

	public void setFileExcelDos(UploadedFile fileExcelDos) {
		this.fileExcelDos = fileExcelDos;
	}

	public UploadedFile getFileExcelTres() {
		return fileExcelTres;
	}

	public void setFileExcelTres(UploadedFile fileExcelTres) {
		this.fileExcelTres = fileExcelTres;
	}

	public StreamedContent getCtacteUno() {
		try {
			Excel archi = archivoDAO.get(1);
			InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).
					getResourceAsStream(archi.getCtacte());
			if(stream != null) {
				ctacteUno = new DefaultStreamedContent(stream, "application/xml-dtd", archi.getCtacte());
			}else{
				ctacteUno = null;
			}
		} catch (Exception e) {
			System.out.println("No existe ningun archivo");
		}
		return ctacteUno;
	}

	public void setCtacteUno(StreamedContent ctacteUno) {
		this.ctacteUno = ctacteUno;
	}

	public StreamedContent getCtacteDos() {
		try {
			Excel archi = archivoDAO.get(2);
			InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).
					getResourceAsStream(archi.getCtacte());
			if(stream != null) {
				ctacteDos = new DefaultStreamedContent(stream, "application/xml-dtd", archi.getCtacte());
			}else{
				ctacteDos = null;
			}
		} catch (Exception e) {
			System.out.println("No existe ningun archivo");
		}
		return ctacteDos;
	}

	public void setCtacteDos(StreamedContent ctacteDos) {
		this.ctacteDos = ctacteDos;
	}

	public StreamedContent getCtacteTres() {
		try {
			Excel archi = archivoDAO.get(3);
			InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).
					getResourceAsStream(archi.getCtacte());
			if(stream != null) {
				ctacteTres = new DefaultStreamedContent(stream, "application/xml-dtd", archi.getCtacte());
			}else{
				ctacteTres = null;
			}
		} catch (Exception e) {
			System.out.println("No existe ningun archivo");
		}
		return ctacteTres;
	}

	public void setCtacteTres(StreamedContent ctacteTres) {
		this.ctacteTres = ctacteTres;
	}

	public StreamedContent getCtacteCuatro() {
		try {
			Excel archi = archivoDAO.get(4);
			InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).
					getResourceAsStream(archi.getCtacte());
			if(stream != null) {
				ctacteCuatro = new DefaultStreamedContent(stream, "application/xml-dtd", archi.getCtacte());
			}else{
				ctacteCuatro = null;
			}
		} catch (Exception e) {
			System.out.println("No existe ningun archivo");
		}
		return ctacteCuatro;
	}

	public void setCtacteCuatro(StreamedContent ctacteCuatro) {
		this.ctacteCuatro = ctacteCuatro;
	}

	public StreamedContent getStocksUno() {
		try {
			Excel archi = archivoDAO.get(1);
			InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).
					getResourceAsStream(archi.getStock());
			if(stream != null) {
				stocksUno = new DefaultStreamedContent(stream, "application/xml-dtd", archi.getStock());
			}else{
				stocksUno = null;
			}
		} catch (Exception e) {
			System.out.println("No existe ningun archivo");
		}
		return stocksUno;
	}

	public void setStocksUno(StreamedContent stocksUno) {
		this.stocksUno = stocksUno;
	}

	public StreamedContent getStocksDos() {
		try {
			Excel archi = archivoDAO.get(2);
			InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).
					getResourceAsStream(archi.getStock());
			if(stream != null) {
				stocksDos = new DefaultStreamedContent(stream, "application/xml-dtd", archi.getStock());
			}else{
				stocksDos = null;
			}
		} catch (Exception e) {
			System.out.println("No existe ningun archivo");
		}
		return stocksDos;
	}

	public void setStocksDos(StreamedContent stocksDos) {
		this.stocksDos = stocksDos;
	}

	public StreamedContent getStocksTres() {
		try {
			Excel archi = archivoDAO.get(3);
			InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).
					getResourceAsStream(archi.getStock());
			if(stream != null) {
				stocksTres = new DefaultStreamedContent(stream, "application/xml-dtd", archi.getStock());
			}else{
				stocksTres = null;
			}
		} catch (Exception e) {
			System.out.println("No existe ningun archivo");
		}
		return stocksTres;
	}

	public void setStocksTres(StreamedContent stocksTres) {
		this.stocksTres = stocksTres;
	}

	public StreamedContent getStocksCuatro() {
		try {
			Excel archi = archivoDAO.get(4);
			InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).
					getResourceAsStream(archi.getStock());
			if(stream != null) {
				stocksCuatro = new DefaultStreamedContent(stream, "application/xml-dtd", archi.getStock());
			}else{
				stocksCuatro = null;
			}
		} catch (Exception e) {
			System.out.println("No existe ningun archivo");
		}
		return stocksCuatro;
	}

	public void setStocksCuatro(StreamedContent stocksCuatro) {
		this.stocksCuatro = stocksCuatro;
	}

	public StreamedContent getVentasUno() {
		try {
			Excel archi = archivoDAO.get(1);
			InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).
					getResourceAsStream(archi.getVentas());
			if(stream != null) {
				ventasUno = new DefaultStreamedContent(stream, "application/xml-dtd", archi.getVentas());
			}else{
				ventasUno = null;
			}
		} catch (Exception e) {
			System.out.println("No existe ningun archivo");
		}
		return ventasUno;
	}

	public void setVentasUno(StreamedContent ventasUno) {
		this.ventasUno = ventasUno;
	}

	public StreamedContent getVentasDos() {
		try {
			Excel archi = archivoDAO.get(2);
			InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).
					getResourceAsStream(archi.getVentas());
			if(stream != null) {
				ventasDos = new DefaultStreamedContent(stream, "application/xml-dtd", archi.getVentas());
			}else{
				ventasDos = null;
			}
		} catch (Exception e) {
			System.out.println("No existe ningun archivo");
		}
		return ventasDos;
	}

	public void setVentasDos(StreamedContent ventasDos) {
		this.ventasDos = ventasDos;
	}

	public StreamedContent getVentasTres() {
		try {
			Excel archi = archivoDAO.get(3);
			InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).
					getResourceAsStream(archi.getVentas());
			if(stream != null) {
				ventasTres = new DefaultStreamedContent(stream, "application/xml-dtd", archi.getVentas());
			}else{
				ventasTres = null;
			}
		} catch (Exception e) {
			System.out.println("No existe ningun archivo");
		}
		return ventasTres;
	}

	public void setVentasTres(StreamedContent ventasTres) {
		this.ventasTres = ventasTres;
	}

	public StreamedContent getVentasCuatro() {
		try {
			Excel archi = archivoDAO.get(4);
			InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).
					getResourceAsStream(archi.getVentas());
			if(stream != null) {
				ventasCuatro = new DefaultStreamedContent(stream, "application/xml-dtd", archi.getVentas());
			}else{
				ventasCuatro = null;
			}
		} catch (Exception e) {
			System.out.println("No existe ningun archivo");
		}
		return ventasCuatro;
	}

	public void setVentasCuatro(StreamedContent ventasCuatro) {
		this.ventasCuatro = ventasCuatro;
	}

	public DAOArchivo getArchivoDAO() {
		return archivoDAO;
	}

	public void setArchivoDAO(DAOArchivo archivoDAO) {
		this.archivoDAO = archivoDAO;
	}

	public Excel getArchivoUno() {
		archivoUno = archivoDAO.get(1);
		return archivoUno;
	}

	public void setArchivoUno(Excel archivoUno) {
		this.archivoUno = archivoUno;
	}

	public Excel getArchivoDos() {
		archivoDos = archivoDAO.get(2);
		return archivoDos;
	}

	public void setArchivoDos(Excel archivoDos) {
		this.archivoDos = archivoDos;
	}

	public Excel getArchivoTres() {
		archivoTres = archivoDAO.get(3);
		return archivoTres;
	}

	public void setArchivoTres(Excel archivoTres) {
		this.archivoTres = archivoTres;
	}

	public Excel getArchivoCuatro() {
		archivoCuatro = archivoDAO.get(4);
		return archivoCuatro;
	}

	public void setArchivoCuatro(Excel archivoCuatro) {
		this.archivoCuatro = archivoCuatro;
	}

	public String goCargaExcel() {
		System.out.println("Metodo goCargaExcel()");
		fileExcelUno = null;
		fileExcelDos = null;
		fileExcelTres = null;
		return "carga";
	}
	
	public void uploadExcelUno(FileUploadEvent event) {
		System.out.println("Metodo uploadExcelUno()");
		try {
			fileExcelUno = event.getFile();	
			copyFileTipoUno(fileExcelUno.getFileName(), fileExcelUno.getInputstream());
			FacesMessage message = new FacesMessage("Exito", event.getFile()
					.getFileName() + " fue cargado.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyFileTipoUno(String fileName, InputStream in) {
		System.out.println("Metodo copyFileExcelUno()");
		try {			
			ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String path = contexto.getRealPath("/");
			Excel ctacte_uno = archivoDAO.get(1);
			Excel ctacte_dos = archivoDAO.get(2);
			Excel ctacte_tres = archivoDAO.get(3);
			Excel ctacte_cuatro = archivoDAO.get(4);
			File file1 = new File(path + ctacte_uno.getCtacte());
			File file2 = new File(path + ctacte_dos.getCtacte());
			File file3 = new File(path + ctacte_tres.getCtacte());
			File file4 = new File(path + ctacte_cuatro.getCtacte());
			if(file1.exists()) {
				file1.delete();
			}
			if(file2.exists()) {
				ctacte_uno.setFecha(new Date());
				ctacte_uno.setCtacte(ctacte_dos.getCtacte());
				archivoDAO.update(ctacte_uno);
			}
			if(file3.exists()) {
				ctacte_dos.setFecha(new Date());
				ctacte_dos.setCtacte(ctacte_tres.getCtacte());
				archivoDAO.update(ctacte_dos);
			}
			if(file4.exists()) {
				ctacte_tres.setFecha(new Date());
				ctacte_tres.setCtacte(ctacte_cuatro.getCtacte());
				archivoDAO.update(ctacte_tres);
			}
			
			int indice = fileName.lastIndexOf(".");
			String extension = fileName.substring(indice + 1);
			SimpleDateFormat dateformat = new SimpleDateFormat("ddMMyy-hhmmss");
			Date ahora = new Date();			
			
			String nombre_archivo = "ctacte_" + dateformat.format(ahora) + "." + extension;
			ctacte_cuatro.setFecha(new Date());
			ctacte_cuatro.setCtacte(nombre_archivo);
			archivoDAO.update(ctacte_cuatro);

			OutputStream out = new FileOutputStream(new File(path
					+ nombre_archivo));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void uploadExcelDos(FileUploadEvent event) {
		System.out.println("Metodo uploadExcelDos()");
		try {
			fileExcelDos = event.getFile();		
			copyFileTipoDos(fileExcelDos.getFileName(), fileExcelDos.getInputstream());
			FacesMessage message = new FacesMessage("Exito", event.getFile()
					.getFileName() + " fue cargado.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyFileTipoDos(String fileName, InputStream in) {
		System.out.println("Metodo copyFileExcelDos()");
		try {			
			ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String path = contexto.getRealPath("/");
			Excel stock_uno = archivoDAO.get(1);
			Excel stock_dos = archivoDAO.get(2);
			Excel stock_tres = archivoDAO.get(3);
			Excel stock_cuatro = archivoDAO.get(4);
			File file1 = new File(path + stock_uno.getStock());
			File file2 = new File(path + stock_dos.getStock());
			File file3 = new File(path + stock_tres.getStock());
			File file4 = new File(path + stock_cuatro.getStock());
			if(file1.exists()) {
				file1.delete();
			}
			if(file2.exists()) {
				stock_uno.setFecha(new Date());
				stock_uno.setStock(stock_dos.getStock());
				archivoDAO.update(stock_uno);
			}
			if(file3.exists()) {
				stock_dos.setFecha(new Date());
				stock_dos.setStock(stock_tres.getStock());
				archivoDAO.update(stock_dos);
			}
			if(file4.exists()) {
				stock_tres.setFecha(new Date());
				stock_tres.setStock(stock_cuatro.getStock());
				archivoDAO.update(stock_tres);
			}
			
			int indice = fileName.lastIndexOf(".");
			String extension = fileName.substring(indice + 1);
			SimpleDateFormat dateformat = new SimpleDateFormat("ddMMyy-hhmmss");
			Date ahora = new Date();			
			
			String nombre_archivo = "stock_" + dateformat.format(ahora) + "." + extension;
			stock_cuatro.setFecha(new Date());
			stock_cuatro.setStock(nombre_archivo);
			archivoDAO.update(stock_cuatro);

			OutputStream out = new FileOutputStream(new File(path
					+ nombre_archivo));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void uploadExcelTres(FileUploadEvent event) {
		System.out.println("Metodo uploadExcelTres()");
		try {
			fileExcelTres = event.getFile();		
			copyFileTipoTres(fileExcelTres.getFileName(), fileExcelTres.getInputstream());
			FacesMessage message = new FacesMessage("Exito", event.getFile()
					.getFileName() + " fue cargado.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyFileTipoTres(String fileName, InputStream in) {
		System.out.println("Metodo copyFileExcelTres()");
		try {			
			ServletContext contexto = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String path = contexto.getRealPath("/");
			Excel venta_uno = archivoDAO.get(1);
			Excel venta_dos = archivoDAO.get(2);
			Excel venta_tres = archivoDAO.get(3);
			Excel venta_cuatro = archivoDAO.get(4);
			File file1 = new File(path + venta_uno.getVentas());
			File file2 = new File(path + venta_dos.getVentas());
			File file3 = new File(path + venta_tres.getVentas());
			File file4 = new File(path + venta_cuatro.getVentas());
			if(file1.exists()) {
				file1.delete();
			}
			if(file2.exists()) {
				venta_uno.setFecha(new Date());
				venta_uno.setVentas(venta_dos.getVentas());
				archivoDAO.update(venta_uno);
			}
			if(file3.exists()) {
				venta_dos.setFecha(new Date());
				venta_dos.setVentas(venta_tres.getVentas());
				archivoDAO.update(venta_dos);
			}
			if(file4.exists()) {
				venta_tres.setFecha(new Date());
				venta_tres.setVentas(venta_cuatro.getVentas());
				archivoDAO.update(venta_tres);
			}
			
			int indice = fileName.lastIndexOf(".");
			String extension = fileName.substring(indice + 1);
			SimpleDateFormat dateformat = new SimpleDateFormat("ddMMyy-hhmmss");
			Date ahora = new Date();			
			
			String nombre_archivo = "ventas_" + dateformat.format(ahora) + "." + extension;
			venta_cuatro.setFecha(new Date());
			venta_cuatro.setVentas(nombre_archivo);
			archivoDAO.update(venta_cuatro);

			OutputStream out = new FileOutputStream(new File(path
					+ nombre_archivo));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			
			in.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void procesoSaldos() {
		CuentasCorrientes cuenta = new CuentasCorrientes();
		int proc = cuenta.procesoActualizaSaldos();
		FacesMessage msg = null;
		if (proc != 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizo", null);
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "NO Actualizo", null);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void procesoSaldos1() {
		CuentasCorrientes cuenta = new CuentasCorrientes();
		int proc = cuenta.procesoActualizaSaldosVirtuales();
		FacesMessage msg = null;
		if (proc != 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizo", null);
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "NO Actualizo", null);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void procesoSaldos2() {
		CuentasCorrientes cuenta = new CuentasCorrientes();
		int proc = cuenta.procesoActualizaBolsaVirtual();
		FacesMessage msg = null;
		if (proc != 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizo", null);
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "NO Actualizo", null);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void procesoSaldos3() {
		Cajas cajas = new Cajas();
		int proc = cajas.actualizaCaja();
		FacesMessage msg = null;
		if (proc != 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizo", null);
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "NO Actualizo", null);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
