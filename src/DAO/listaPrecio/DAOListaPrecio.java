package DAO.listaPrecio;

import java.io.Serializable;
import java.util.ArrayList;
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
import model.Producto;
import model.Rubro;

public class DAOListaPrecio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public DAOListaPrecio() {
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
	
	public List<Listaprecio> getListadoListaprecio(){
		inicializar();
		String consulta = "SELECT l FROM Listaprecio l";
		Query locQuery = em.createQuery(consulta, Listaprecio.class);
		List<Listaprecio> lista = locQuery.getResultList();
		
		return lista;
	}
	
	public List<Listaprecio> getListadoListaprecioAct(){
		//System.out.println("llega dao");
		inicializar();
		String consulta = "SELECT l FROM Listaprecio l WHERE l.enabled = true";
		Query locQuery = em.createQuery(consulta, Listaprecio.class);
		List<Listaprecio> lista = locQuery.getResultList();
		
		return lista;
	}
	
	public List<Listaprecio> getListadoListaprecioDesact(){
		//System.out.println("llega dao");
		inicializar();
		String consulta = "SELECT l FROM Listaprecio l WHERE l.enabled = false";
		Query locQuery = em.createQuery(consulta, Listaprecio.class);
		List<Listaprecio> lista = locQuery.getResultList();
		
		return lista;
	}
	
	public int insertListaprecio(Listaprecio lp){
		int retorno;
		if(lp.getId() != 0){
			
			retorno = updateListaprecio(lp);
			return retorno;
		}
		
		
		inicializar();
		
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(lp);
			tx.commit();
			retorno = lp.getId();
			cerrarInstancia();
		}catch (Exception e){
			tx.rollback();
			retorno = 0;
			cerrarInstancia();
		}
		return retorno;
		
	}
	
	public int updateListaprecio(Listaprecio lp){
		
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Listaprecio l SET l.enabled = :pEstado, l.fechaModificacion = :pFechaMod, l.usuario3 = :pusuariomod,"
					+ "l.nombre = :pnombre "
					+ "WHERE l.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", lp.getId());
			locQuery.setParameter("pEstado", lp.getEnabled());
			locQuery.setParameter("pFechaMod", lp.getFechaModificacion());
			locQuery.setParameter("pusuariomod", lp.getUsuario3());
			locQuery.setParameter("pnombre", lp.getNombre());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();

			cerrarInstancia();
			return lp.getId();
		} catch (Exception e) {
			tx.rollback();
			return 0;
		}
		 
	}
	
	public int updateEstadoListaprecio(Listaprecio lp){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta2 = "UPDATE Listaprecio l SET l.enabled = :pEstado, l.fechaBaja = :pFechaBaja,"
					+ " l.fechaAlta = :pFechaAlta, l.usuario2 = :pUsuarioBaja, l.usuario3 = :pUsuarioModif "
					+ "WHERE l.id = :pId";
			Query locQuery2 = em.createQuery(consulta2);
			locQuery2.setParameter("pId", lp.getId());
			locQuery2.setParameter("pEstado", lp.getEnabled());
			locQuery2.setParameter("pUsuarioBaja", lp.getUsuario2());
			locQuery2.setParameter("pUsuarioModif", lp.getUsuario3());
			locQuery2.setParameter("pFechaBaja", lp.getFechaBaja());
			locQuery2.setParameter("pFechaAlta", lp.getFechaAlta());
			tx.begin();
			locQuery2.executeUpdate();
			tx.commit();
			cerrarInstancia();
			return 1;
		} catch (Exception e) {
			tx.rollback();
			return 0;
		}
	}
	
	public int insertLPP(ListapreciosProducto lpp){
		inicializar();
		int retorno;
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(lpp);
			tx.commit();
			retorno = lpp.getId();
			cerrarInstancia();
		}catch (Exception e){
			tx.rollback();
			retorno = 0;
			cerrarInstancia();
		}
		return retorno;
		
	}
	
	public List<Producto> getListadoProductosAct(Listaprecio lp, int idRubro){
		Rubro rubro = new Rubro();
		rubro.setId(idRubro);
		inicializar();
		//String consulta = "SELECT p FROM Producto p WHERE p.enabled = true";
		String consulta = "SELECT p FROM Producto p WHERE p NOT IN (SELECT l.producto FROM ListapreciosProducto l WHERE l.listaprecio = :pLP) AND p.enabled = true AND p.rubro = :pRubro";
//		String consulta ="SELECT DISTINCT l.producto FROM ListapreciosProducto l WHERE l.listaprecio <> :pLP";
		Query locQuery = em.createQuery(consulta);
		locQuery.setParameter("pRubro", rubro);
		locQuery.setParameter("pLP", lp);
		List<Producto> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<ListapreciosProducto> getListadoLPP(Listaprecio lp){
		inicializar();
		String consulta = "SELECT l FROM ListapreciosProducto l where l.listaprecio = :pdlista";
		Query locQuery = em.createQuery(consulta, ListapreciosProducto.class);
		locQuery.setParameter("pdlista", lp);
		List<ListapreciosProducto> lista = locQuery.getResultList();
		return lista;		
	}
	
	public List<Cliente> getListadoClientesLPP(int lp){
		inicializar();
		Listaprecio listaPrecio = new Listaprecio();
		listaPrecio.setId(lp);
		String consulta = "SELECT c FROM Cliente c WHERE c.listaprecio = :pLPP";
		Query locQuery = em.createQuery(consulta, Cliente.class);
		locQuery.setParameter("pLPP", listaPrecio);
		List<Cliente> lista = locQuery.getResultList();
		return lista;		
	}
	
	public void deleteListapreciosProducto(ListapreciosProducto lpp){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		String consulta = "DELETE FROM ListapreciosProducto l WHERE l.id = :pId";
		Query locQuery = em.createQuery(consulta);
		locQuery.setParameter("pId", lpp.getId());				
		tx.begin();
		locQuery.executeUpdate();
		tx.commit();
		cerrarInstancia();
	}
	
	public HashMap<String, Integer> getHashMapRubros(){
		HashMap<String, Integer> listRubroLP = new LinkedHashMap<String, Integer>();

		Rubro rubro = new Rubro();

		inicializar();
		List<Rubro> lista = new ArrayList<Rubro>();
		String consulta = "SELECT r FROM Rubro r WHERE r.enabled = true";
		Query locQuery = em.createQuery(consulta, Rubro.class);
		lista = locQuery.getResultList();
		for (Rubro r : lista) {
			String nombre = r.getNombre();
			Integer id = r.getId();
			listRubroLP.put(nombre, id);
		}
		return listRubroLP;
	}
}
