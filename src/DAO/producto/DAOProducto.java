package DAO.producto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Producto;
import model.Rubro;

public class DAOProducto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public DAOProducto() {
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
	
	public List<Producto> getListadoProductos(){
		inicializar();
		String consulta = "SELECT p FROM Producto p";
		Query locQuery = em.createQuery(consulta, Producto.class);
		List<Producto> lista = locQuery.getResultList();	
		return lista;
	}
	
	public List<Producto> getListadoProductosAct(){
		inicializar();
		String consulta = "SELECT p FROM Producto p WHERE p.enabled = true";
		Query locQuery = em.createQuery(consulta, Producto.class);
		List<Producto> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Producto> getListadoProductosDesact(){
		inicializar();
		String consulta = "SELECT p FROM Producto p WHERE p.enabled = false";
		Query locQuery = em.createQuery(consulta, Producto.class);
		List<Producto> lista = locQuery.getResultList();
		return lista;
	}
	
	public int insertProducto(Producto producto){
		int retorno;
		if(producto.getId() != 0){			
			retorno = updateProducto(producto);
			return retorno;
		}
		
		inicializar();		
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(producto);
			tx.commit();
			retorno = producto.getId();
			cerrarInstancia();
		}catch (Exception e){
			tx.rollback();
			retorno = 0;
			cerrarInstancia();
		}
		return retorno;
		
	}
	
	public int updateProducto(Producto producto){
		
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Producto p SET p.enabled = :pEstado, p.fechaModificacion = :pFechaMod, p.usuario3 = :pUserMod,"
					+ " p.nombre = :pNombre, p.rubro = :pRubro, p.descripcion = :pDescripcion, p.precioNominal = :pPrecio, "
					+ "p.stock = :pStock, p.stockMinimo = :pStockMinimo, p.precioCompra = :pPrecioCompra "
					+ "WHERE p.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", producto.getId());
			locQuery.setParameter("pRubro", producto.getRubro());
			locQuery.setParameter("pEstado", producto.getEnabled());
			locQuery.setParameter("pFechaMod", producto.getFechaModificacion());
			locQuery.setParameter("pUserMod", producto.getUsuario2());
			locQuery.setParameter("pNombre", producto.getNombre());
			locQuery.setParameter("pDescripcion", producto.getDescripcion());
			locQuery.setParameter("pPrecio", producto.getPrecioNominal());
			locQuery.setParameter("pStock", producto.getStock());
			locQuery.setParameter("pStockMinimo", producto.getStockMinimo());
			locQuery.setParameter("pPrecioCompra", producto.getPrecioCompra());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			cerrarInstancia();
			return producto.getId();
		} catch (Exception e) {
			tx.rollback();
			return 0;
		}
		 
	}

	public int updateEstadoProducto(Producto producto){
		
		
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Producto p SET p.enabled = :pEstado, p.fechaBaja = :pFechaBaja,"
					+ " p.fechaAlta = :pFechaAlta, p.usuario2 = :pUsuarioBaja, p.usuario3 = :pUsuarioModif "
					+ "WHERE p.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", producto.getId());
			locQuery.setParameter("pEstado", producto.getEnabled());
			locQuery.setParameter("pUsuarioBaja", producto.getUsuario2());
			locQuery.setParameter("pUsuarioModif", producto.getUsuario3());
			locQuery.setParameter("pFechaBaja", producto.getFechaBaja());
			locQuery.setParameter("pFechaAlta", producto.getFechaAlta());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			int retorno = producto.getId();
			cerrarInstancia();
			return retorno;
		} catch (Exception e) {
			tx.rollback();
			cerrarInstancia();
			return 0;
		}
	}
	
	public Producto getProductoXId(int idProducto){
		inicializar();
		Producto producto = new Producto();
		Query locQuery = em.createQuery("SELECT p FROM Producto p WHERE p.id = :pId", Producto.class);
		locQuery.setParameter("pId", idProducto);
		producto = (Producto) locQuery.getSingleResult();
		return producto;
	}



}
