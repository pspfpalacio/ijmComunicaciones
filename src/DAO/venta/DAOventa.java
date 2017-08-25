package DAO.venta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Cliente;
import model.Listaprecio;
import model.ListapreciosProducto;
import model.Notacredito;
import model.Producto;
import model.Stock;
import model.StockDetalle;
import model.Venta;
import model.VentasProducto;

public class DAOventa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public DAOventa() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void inicializar(){
		emf = Persistence.createEntityManagerFactory("ijmComunicaciones");
		em = emf.createEntityManager();
	}
	
	public void cerrarInstancia() {
		em.close();
		emf.close();
	}
	
	public HashMap<String,Integer> getHashMapListas(){
		inicializar();
		HashMap<String, Integer> auxiliar = new LinkedHashMap<String, Integer>();
		List<Cliente> lista = new ArrayList<Cliente>();
		String consulta = "SELECT c FROM Cliente c WHERE c.tipo = 'c' AND c.enabled = true";
		Query locQuery = em.createQuery(consulta, Cliente.class);
		lista = locQuery.getResultList();
		for (Cliente cliente : lista) {
			String nombre = cliente.getNombre();
			Integer id = cliente.getId();
			auxiliar.put(nombre, id);
		}
		return auxiliar;
	}
	
	public HashMap<String,Integer> getHashMapListaPrecio(int idCliente){
		Cliente cli = new Cliente();
		cli.setId(idCliente);
		inicializar();
		HashMap<String, Integer> auxiliar = new LinkedHashMap<String, Integer>();
		List<Listaprecio> lista = new ArrayList<Listaprecio>();
		String consulta = "SELECT l FROM ClientesListaprecio c JOIN Listaprecio l on (c.listaprecio = l) WHERE l.enabled = true AND c.cliente = :pidCliente";
		Query locQuery = em.createQuery(consulta, Listaprecio.class);
		locQuery.setParameter("pidCliente", cli);
		lista = locQuery.getResultList();
		for (Listaprecio lp : lista) {
			String nombre = lp.getNombre();
			Integer id = lp.getId();
			auxiliar.put(nombre, id);
		}
		return auxiliar;
	}
	
	public List<ListapreciosProducto> getListadoProductosVenta(int idLista){
		Listaprecio lp = new Listaprecio();
		lp.setId(idLista);
		inicializar();
		List<ListapreciosProducto> listaux = new ArrayList<ListapreciosProducto>();
		String consulta = "SELECT l FROM ListapreciosProducto l WHERE l.listaprecio = :plista";
		Query locQuery = em.createQuery(consulta, ListapreciosProducto.class);
		locQuery.setParameter("plista", lp);
		listaux = locQuery.getResultList();
		return listaux;
	}
	
	public Cliente obtenerClientePorId(int idCli){
		Cliente cli = new Cliente();
		inicializar();
		String consulta = "SELECT c FROM Cliente c WHERE c.id = :pidCli";
		Query locQuery = em.createQuery(consulta, Cliente.class);
		locQuery.setParameter("pidCli", idCli);
		cli = (Cliente) locQuery.getSingleResult();
		return cli;
	}
	
	public int insertarDetalleVenta(VentasProducto det){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(det);
			tx.commit();
			cerrarInstancia();
			return det.getId();
		}catch (Exception e){
			tx.rollback();
			e.printStackTrace();
			return 0;
		}
	}
	
	public VentasProducto obtenerUltimoDetalle(){
		VentasProducto vp = new VentasProducto();
		inicializar();
		String consulta = "SELECT v FROM VentasProducto v WHERE v.id = (SELECT MAX(v.id) FROM VentasProducto v)";  // ver este metodo porque estoy seleccionando MAX(v)
		Query locQuery = em.createQuery(consulta, VentasProducto.class);
		vp = (VentasProducto) locQuery.getSingleResult();
		return vp;
	}
	
	public VentasProducto obtenerDetallePorId(int idDet){
		VentasProducto vp = new VentasProducto();
		List<VentasProducto> aux = new ArrayList<VentasProducto>();
		inicializar();
		String consulta = "SELECT v FROM VentasProducto v WHERE v.id = :pId";  // ver este metodo porque estoy seleccionando MAX(v)
		Query locQuery = em.createQuery(consulta, VentasProducto.class);
		locQuery.setParameter("pId", idDet);
		aux = locQuery.getResultList();
		
		if (aux.size() != 0){
			for (VentasProducto ventasProducto : aux) {
				vp = ventasProducto;
			}
		}
		return vp;
	}
	
	public Stock obtenerStockMasViejo(Producto prod){
		List<Stock> lista = new ArrayList<Stock>();
		Stock stock = new Stock();
		inicializar();
		String consulta = "SELECT s FROM Stock s WHERE s.producto = :pProd AND s.cantidad > 0 ORDER BY s.id DESC";
		Query locQuery = em.createQuery(consulta, Stock.class);
		locQuery.setParameter("pProd", prod);
		lista = locQuery.getResultList();
		for (Stock stock2 : lista) {
			stock = stock2;
		}
		return stock;
	}
	
	
	
	public int insetarVenta(Venta venta){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		int retorno = 0;
		try{
			tx.begin();
			em.persist(venta);
			tx.commit();
			retorno = venta.getId();
			cerrarInstancia();
			return retorno;
		}catch (Exception e){
			tx.rollback();
			e.printStackTrace();
			return retorno;
		}
	}
	
	public int insertarStockDetalle(StockDetalle sd){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		int retorno = 0;
		try{
			tx.begin();
			em.persist(sd);
			tx.commit();
			retorno = sd.getId();
			cerrarInstancia();
			return retorno;
		}catch (Exception e){
			tx.rollback();
			e.printStackTrace();
			return retorno;
		}
	}
	
	public boolean updateStock(Stock stock){
		
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Stock s SET s.cantidad = :pCantidad "
					+ "WHERE s.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pCantidad", stock.getCantidad());
			locQuery.setParameter("pId", stock.getId());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			cerrarInstancia();
			return true;
		} catch (Exception e) {
			tx.rollback();
			return false;
		}

	}
	
	public List<Venta> getListadoVentas(){
		inicializar();
		List<Venta> listaux = new ArrayList<Venta>();
		String consulta = "SELECT v FROM Venta v ORDER BY v.fecha DESC";
		Query locQuery = em.createQuery(consulta, Venta.class);
		listaux = locQuery.getResultList();
		return listaux;
	}
	
	public boolean bajaVenta(Venta ven){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Venta v SET v.enabled = false "
					+ "WHERE v.id = :pIdVenta";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pIdVenta", ven.getId());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			cerrarInstancia();
			return true;
		} catch (Exception e) {
			tx.rollback();
			return false;
		}
	}
	
	//ESTO LO HICE YO
	
	public List<Venta> getListVentasOrderFecha(){
		inicializar();
		List<Venta> lista = new ArrayList<Venta>();
		Query locQuery = em.createQuery("SELECT v FROM Venta v ORDER BY v.fecha DESC", Venta.class);
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Venta> getListVentasOrderFecha(Date ini, Date fin){
		inicializar();
		List<Venta> lista = new ArrayList<Venta>();
		Query locQuery = em.createQuery("SELECT v FROM Venta v WHERE v.fecha >= :fechai AND v.fecha <= :fechaf ORDER BY v.fecha DESC", Venta.class);
		locQuery.setParameter("fechai", ini);
		locQuery.setParameter("fechaf", fin);
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Venta> getListVentasOrderFecha(Cliente cliente, int estado, Date ini, Date fin){
		inicializar();
		List<Venta> lista = new ArrayList<Venta>();
		Query locQuery = em.createQuery("SELECT v FROM Venta v WHERE v.cliente = :pCliente AND v.enabled = :pEstado AND v.fecha >= :fechai AND v.fecha <= :fechaf "
				+ "ORDER BY v.fecha DESC", Venta.class);
		locQuery.setParameter("pCliente", cliente);
		if (estado == 1){
			locQuery.setParameter("pEstado", true);
		}else{
			locQuery.setParameter("pEstado", false);
		}
		locQuery.setParameter("fechai", ini);
		locQuery.setParameter("fechaf", fin);
		lista = locQuery.getResultList();
		return lista;
	}
	public List<Venta> getListVentasOrderFecha(Cliente cliente, int estado){
		inicializar();
		List<Venta> lista = new ArrayList<Venta>();
		Query locQuery = em.createQuery("SELECT v FROM Venta v WHERE v.cliente = :pCliente AND v.enabled = :pEstado "
				+ "ORDER BY v.fecha DESC", Venta.class);
		locQuery.setParameter("pCliente", cliente);
		if (estado == 1){
			locQuery.setParameter("pEstado", true);
		}else{
			locQuery.setParameter("pEstado", false);
		}
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<VentasProducto> getDetallesDeVentas(int id){
		inicializar();
		Venta venta = new Venta();
		venta.setId(id);
		List<VentasProducto> lista = new ArrayList<VentasProducto>();
		Query locQuery = em.createQuery("SELECT v FROM VentasProducto v WHERE v.venta = :pVenta", VentasProducto.class);
		locQuery.setParameter("pVenta", venta);
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Venta> getVentasPorCliente(Cliente cliente, Date ini, Date fin){
		inicializar();
		Query locQuery = em.createQuery("SELECT v FROM Venta v WHERE v.cliente = :pCliente AND v.fecha >= :fechai AND v.fecha <= :fechaf ORDER BY v.fecha DESC", Venta.class);
		locQuery.setParameter("pCliente", cliente);
		locQuery.setParameter("fechai", ini);
		locQuery.setParameter("fechaf", fin);
		List<Venta> lista = new ArrayList<Venta>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Venta> getVentasPorCliente(Cliente cliente){
		inicializar();
		Query locQuery = em.createQuery("SELECT v FROM Venta v WHERE v.cliente = :pCliente ORDER BY v.fecha DESC", Venta.class);
		locQuery.setParameter("pCliente", cliente);
		List<Venta> lista = new ArrayList<Venta>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Venta> getVentasPorEstado(int estado, Date ini, Date fin){
		inicializar();
		boolean enabled = false;
		if(estado == 1){
			enabled = true;
		}
		Query locQuery = em.createQuery("SELECT v FROM Venta v WHERE v.enabled = :pEstado AND v.fecha >= :fechai AND v.fecha <= :fechaf ORDER BY v.fecha DESC", Venta.class);
		if (estado == 1){
			locQuery.setParameter("pEstado", true);
		}else{
			locQuery.setParameter("pEstado", false);
		}
		locQuery.setParameter("fechai", ini);
		locQuery.setParameter("fechaf", fin);
		List<Venta> lista = new ArrayList<Venta>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Venta> getVentasPorEstado(int estado){
		inicializar();
		boolean enabled = false;
		if(estado == 1){
			enabled = true;
		}
		Query locQuery = em.createQuery("SELECT v FROM Venta v WHERE v.enabled = :pEstado ORDER BY v.fecha DESC", Venta.class);
		locQuery.setParameter("pEstado", enabled);
		List<Venta> lista = new ArrayList<Venta>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public int insertNotaCredito(Notacredito notaCredito){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(notaCredito);
			tx.commit();
			int retorno = notaCredito.getId();
			cerrarInstancia();
			return retorno;
		}catch (Exception e){
			tx.rollback();
			e.printStackTrace();
			return 0;
		}
	}
	
	public List<StockDetalle> getStocksDetalle(int idVentaProducto){
		inicializar();
		Query locQuery = em.createQuery("SELECT s FROM StockDetalle s WHERE s.ventasProducto = :pVentProd", StockDetalle.class);
		VentasProducto ventasProd = new VentasProducto();
		ventasProd.setId(idVentaProducto);
		locQuery.setParameter("pVentProd", ventasProd);
		List<StockDetalle> lista = new ArrayList<StockDetalle>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public boolean updateVenta(Venta venta){
		
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Venta v SET v.cliente = :pCliente, v.enabled = :pEstado, v.fecha = :pFecha, "
					+ "v.monto = :pMonto, v.tipo = :pTipo, v.usuario = :pUsuario "
					+ "WHERE v.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pCliente", venta.getCliente());
			locQuery.setParameter("pEstado", venta.getEnabled());
			locQuery.setParameter("pFecha", venta.getFecha());
			locQuery.setParameter("pMonto", venta.getMonto());
			locQuery.setParameter("pTipo", venta.getTipo());
			locQuery.setParameter("pUsuario", venta.getUsuario());
			locQuery.setParameter("pId", venta.getId());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			cerrarInstancia();
			return true;
		} catch (Exception e) {
			tx.rollback();
			return false;
		}
	}
	
	public Venta getVentaPorId(Integer id){
		inicializar();
		Query locQuery = em.createQuery("SELECT v FROM Venta v WHERE v.id = :pID", Venta.class);
		locQuery.setParameter("pID", id);
		Venta venta = new Venta();
		venta = (Venta) locQuery.getSingleResult();
		return venta;
	}
	
	public Notacredito getNotaCreditoPorId(Integer id){
		inicializar();
		Query locQuery = em.createQuery("SELECT n FROM Notacredito n WHERE n.id = :pID", Notacredito.class);
		locQuery.setParameter("pID", id);
		Notacredito notaCredito = new Notacredito();
		notaCredito = (Notacredito) locQuery.getSingleResult();
		return notaCredito;
	}
	
	public List<VentasProducto> getListadoDetallePorProd(Producto prod){
		inicializar();
		Query locQuery = em.createQuery("SELECT v FROM VentasProducto v "
				+ "WHERE v.producto = :pProducto AND v.venta.enabled = true");
		locQuery.setParameter("pProducto", prod);
		List<VentasProducto> lista = new ArrayList<VentasProducto>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	//Nuevo franco
	
	public List<VentasProducto> getListadoDetallePorProd(Producto producto, Date fechai, Date fechaf){
		inicializar();
		Query locQuery = em.createQuery("SELECT v FROM VentasProducto v "
				+ "WHERE v.producto = :pProducto AND v.venta.enabled = true AND v.venta.fecha BETWEEN :pInicio AND :pFinal");
		locQuery.setParameter("pProducto", producto);
		locQuery.setParameter("pInicio", fechai);
		locQuery.setParameter("pFinal", fechaf);
		List<VentasProducto> lista = new ArrayList<VentasProducto>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Venta> getVentasActPorCliente(Cliente cliente){
		inicializar();
		Query locQuery = em.createQuery("SELECT v FROM Venta v WHERE v.cliente = :pCliente AND v.enabled = true", Venta.class);
		locQuery.setParameter("pCliente", cliente);
		List<Venta> lista = new ArrayList<Venta>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Venta> getVentasActPorCliente(Cliente cliente, Date fechaI, Date fechaF){
		inicializar();
		Query locQuery = em.createQuery("SELECT v FROM Venta v "
				+ "WHERE v.cliente = :pCliente AND v.enabled = true AND v.fecha BETWEEN :pInicio AND :pFinal", Venta.class);
		locQuery.setParameter("pCliente", cliente);
		locQuery.setParameter("pInicio", fechaI);
		locQuery.setParameter("pFinal", fechaF);
		List<Venta> lista = new ArrayList<Venta>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	// chuncho ultimo para reporte ganancias
	
	
	public List<Venta> getVentasPorFecha(Date ini, Date fin){
		inicializar();
		List<Venta> lista = new ArrayList<Venta>();
		Query locQuery = em.createQuery("SELECT v FROM Venta v WHERE v.fecha >= :fechai AND v.fecha <= :fechaf AND v.enabled = true", Venta.class);
		locQuery.setParameter("fechai", ini);
		locQuery.setParameter("fechaf", fin);
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<StockDetalle> getStockDetalleXDetalle(VentasProducto detalle){
		inicializar();
		List<StockDetalle> lista = new ArrayList<StockDetalle>();
		Query locQuery = em.createQuery("SELECT s FROM StockDetalle s WHERE s.ventasProducto = :pDetalle", Venta.class);
		locQuery.setParameter("pDetalle", detalle);
		lista = locQuery.getResultList();
		return lista;
	}
	
	public int getLastNroVenta(){
		inicializar();
		Query locQuery = em.createQuery("SELECT v FROM Venta v", Venta.class);
		List<Venta> lista = locQuery.getResultList();
		int nro = 0;
		for (Venta venta : lista) {
			nro = venta.getId();
		}
		return nro;
	}
}
