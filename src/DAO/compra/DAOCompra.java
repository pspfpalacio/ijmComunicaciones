package DAO.compra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Cliente;
import model.Compra;
import model.DetalleCompra;
import model.Producto;
import model.Rubro;
import model.Stock;

public class DAOCompra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DAOCompra() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private EntityManagerFactory emf;
	private EntityManager em;

	public void inicializar(){
		emf = Persistence.createEntityManagerFactory("ijmComunicaciones");
		em = emf.createEntityManager();
	}
	
	public void cerrarInstancia() {
		em.close();
		emf.close();
	}
	
	public List<Producto> getProductosXRubro(int idRubro){
		inicializar();
		Rubro rubro = new Rubro();
		rubro.setId(idRubro);
		Query locQuery = em.createQuery("SELECT p FROM Producto p WHERE p.rubro = :pId", Producto.class);
		locQuery.setParameter("pId", rubro);
		List<Producto> lista = new ArrayList<Producto>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public int insertPedidoCompra(Compra compra){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(compra);
			tx.commit();
			int retorno = compra.getId();
			cerrarInstancia();
			return retorno;
		}catch (Exception e){
			tx.rollback();
			cerrarInstancia();
			return 0;
		}	
	}
	
	public int insertDetallePedidoCompra(DetalleCompra detalle){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(detalle);
			tx.commit();
			int retorno = detalle.getId();
			cerrarInstancia();
			return retorno;
		}catch(Exception e){
			tx.rollback();
			cerrarInstancia();
			return 0;
		}
	}
	
	public List<Compra> getPedidos(){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Compra c ORDER BY c.fechaAlta DESC", Compra.class);
		List<Compra> lista = new ArrayList<Compra>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Compra> getPedidos(Date i, Date f){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Compra c WHERE c.fechaAlta >= :pI AND c.fechaAlta <= :pF ORDER BY c.fechaAlta DESC", Compra.class);
		locQuery.setParameter("pF", f);
		locQuery.setParameter("pI", i);
		List<Compra> lista = new ArrayList<Compra>();
		lista = locQuery.getResultList();
		return lista;
	}
	

	
	public List<Compra> getPedidos(Cliente proveedor, String estado){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Compra c WHERE c.estado = :pEstado AND c.cliente = :pProveedor "
				+ "ORDER BY c.fechaAlta DESC", Compra.class);
		locQuery.setParameter("pEstado", estado);
		locQuery.setParameter("pProveedor", proveedor);
		List<Compra> lista = new ArrayList<Compra>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Compra> getPedidos(Cliente proveedor, String estado, Date i, Date f){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Compra c WHERE c.estado = :pEstado AND c.cliente = :pProveedor AND c.fechaAlta <= :pF AND c.fechaAlta >= :pI "
				+ "ORDER BY c.fechaAlta DESC", Compra.class);
		locQuery.setParameter("pEstado", estado);
		locQuery.setParameter("pProveedor", proveedor);
		locQuery.setParameter("pI", i);
		locQuery.setParameter("pF", f);
		List<Compra> lista = new ArrayList<Compra>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Compra> getPedidosXProveedor(Cliente proveedor){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Compra c "
				+ "WHERE c.cliente = :pProveedor ORDER BY c.fechaAlta DESC", Compra.class);
		locQuery.setParameter("pProveedor", proveedor);
		List<Compra> lista = new ArrayList<Compra>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Compra> getPedidosXProveedor(Cliente proveedor, Date i, Date f){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Compra c "
				+ "WHERE c.cliente = :pProveedor AND c.fechaAlta <= :pF AND c.fechaAlta >= :pI ORDER BY c.fechaAlta DESC", Compra.class);
		locQuery.setParameter("pProveedor", proveedor);
		locQuery.setParameter("pI", i);
		locQuery.setParameter("pF", f);
		List<Compra> lista = new ArrayList<Compra>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Compra> getPedidosXEstado(String estado){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Compra c "
				+ "WHERE c.estado = :pEstado ORDER BY c.fechaAlta DESC", Compra.class);
		locQuery.setParameter("pEstado", estado);
		List<Compra> lista = new ArrayList<Compra>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Compra> getPedidosXEstado(String estado, Date i, Date f){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Compra c "
				+ "WHERE c.estado = :pEstado AND c.fechaAlta >= :pI AND c.fechaAlta <= :pF ORDER BY c.fechaAlta DESC", Compra.class);
		locQuery.setParameter("pEstado", estado);
		locQuery.setParameter("pI", i);
		locQuery.setParameter("pF", f);
		List<Compra> lista = new ArrayList<Compra>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<DetalleCompra> getDetallesXCompra(Integer idCompra){
		inicializar();
		Compra compra = new Compra();
		compra.setId(idCompra);
		Query locQuery = em.createQuery("SELECT d FROM DetalleCompra d WHERE d.compra = :pCompra", DetalleCompra.class);
		locQuery.setParameter("pCompra", compra);
		List<DetalleCompra> lista = new ArrayList<DetalleCompra>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public int deleteDetallesCompra(Compra compra){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "DELETE FROM DetalleCompra d "
					+ "WHERE d.compra = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", compra);
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			cerrarInstancia();
			return 1;
		} catch (Exception e) {
			tx.rollback();
			cerrarInstancia();
			return 0;
		}
	}
	
	public int updateCompra(Compra compra){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Compra c SET c.fechaAlta = :pFechaAlta, c.enabled = :pEnabled, c.estado = :pEstado, "
					+ "c.fechaBaja = :pFechaBaja, c.monto = :pMonto, c.cliente = :pProveedor, c.usuario1 = :pUsuarioAlta, "
					+ "c.usuario2 = :pUsuarioBaja "
					+ "WHERE c.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", compra.getId());
			locQuery.setParameter("pFechaAlta", compra.getFechaAlta());
			locQuery.setParameter("pEnabled", compra.getEnabled());
			locQuery.setParameter("pEstado", compra.getEstado());
			locQuery.setParameter("pFechaBaja", compra.getFechaBaja());
			locQuery.setParameter("pMonto", compra.getMonto());
			locQuery.setParameter("pProveedor", compra.getCliente());
			locQuery.setParameter("pUsuarioAlta", compra.getUsuario1());
			locQuery.setParameter("pUsuarioBaja", compra.getUsuario2());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			cerrarInstancia();
			return 1;
		} catch (Exception e) {
			tx.rollback();
			cerrarInstancia();
			return 0;
		}
	}
	
	public Stock getStockPorPrecProd(double precio, Producto producto){
		inicializar();
		Query locQuery = em.createQuery("SELECT s FROM Stock s WHERE s.precioUnitario = :pPrecio "
				+ "AND s.producto = :pProducto", Stock.class);
		locQuery.setParameter("pPrecio", Float.parseFloat(Double.toString(precio)));
		locQuery.setParameter("pProducto", producto);
		Stock stock = new Stock();
		List<Stock> lista = locQuery.getResultList();
		for (Stock stock2 : lista) {
			stock = stock2;
		}
		return stock;
	}
	
	public int insertUpdateStock(Stock stock){
		if(stock.getId() != 0){
			inicializar();
			EntityTransaction tx = em.getTransaction();
			try {
				String consulta = "UPDATE Stock s SET s.cantidad = :pCantidad, "
						+ "s.precioUnitario = :pPrecio, s.producto = :pProducto "
						+ "WHERE s.id = :pId";
				Query locQuery = em.createQuery(consulta);
				locQuery.setParameter("pId", stock.getId());
				locQuery.setParameter("pCantidad", stock.getCantidad());
				locQuery.setParameter("pPrecio", stock.getPrecioUnitario());
				locQuery.setParameter("pProducto", stock.getProducto());
				tx.begin();
				locQuery.executeUpdate();
				tx.commit();
				cerrarInstancia();
				return 1;
			} catch (Exception e) {
				tx.rollback();
				cerrarInstancia();
				return 0;
			}
		}else{
			inicializar();
			EntityTransaction tx = em.getTransaction();
			try{
				tx.begin();
				em.persist(stock);
				tx.commit();
				int retorno = stock.getId();
				cerrarInstancia();
				return retorno;
			}catch(Exception e){
				tx.rollback();
				cerrarInstancia();
				return 0;
			}
		}
	}
	
	public Compra getCompraPorId(Integer id){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Compra c WHERE c.id = :pID", Compra.class);
		locQuery.setParameter("pID", id);
		Compra compra = new Compra();
		compra = (Compra) locQuery.getSingleResult();
		return compra;
	}
	
	//nuevo Franco
	
	public List<DetalleCompra> getListadoDetallePorProd(Producto prod){
		inicializar();
		Query locQuery = em.createQuery("SELECT d FROM DetalleCompra d "
				+ "WHERE d.producto = :pProducto AND d.compra.enabled = true AND d.compra.estado = 'Confirmado'");
		locQuery.setParameter("pProducto", prod);
		List<DetalleCompra> lista = new ArrayList<DetalleCompra>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<DetalleCompra> getListadoDetallePorProd(Producto producto, Date fechai, Date fechaf){
		inicializar();
		Query locQuery = em.createQuery("SELECT d FROM DetalleCompra d "
				+ "WHERE d.producto = :pProducto AND d.compra.enabled = true AND d.compra.estado = 'Confirmado' AND d.compra.fechaAlta BETWEEN :pInicio AND :pFinal");
		locQuery.setParameter("pProducto", producto);
		locQuery.setParameter("pInicio", fechai);
		locQuery.setParameter("pFinal", fechaf);
		List<DetalleCompra> lista = new ArrayList<DetalleCompra>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Compra> getComprasXProveedor(Cliente proveedor){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Compra c "
				+ "WHERE c.cliente = :pProveedor AND c.enabled = true AND c.estado = 'Confirmado'", Compra.class);
		locQuery.setParameter("pProveedor", proveedor);
		List<Compra> lista = new ArrayList<Compra>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Compra> getComprasXProveedor(Cliente proveedor, Date fechaI, Date fechaF){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Compra c "
				+ "WHERE c.cliente = :pProveedor AND c.enabled = true AND c.estado = 'Confirmado' AND c.fechaAlta BETWEEN :pInicio AND :pFinal", Compra.class);
		locQuery.setParameter("pProveedor", proveedor);
		locQuery.setParameter("pInicio", fechaI);
		locQuery.setParameter("pFinal", fechaF);
		List<Compra> lista = new ArrayList<Compra>();
		lista = locQuery.getResultList();
		return lista;
	}

}
