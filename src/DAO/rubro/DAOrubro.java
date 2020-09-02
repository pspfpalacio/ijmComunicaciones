package DAO.rubro;

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

import model.Producto;
import model.Rubro;

public class DAOrubro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public DAOrubro() {
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
	
	public List<Rubro> getRubrosLike(String nombre){
		inicializar();
		String consulta = "SELECT r FROM Rubro r WHERE UPPER(r.nombre) LIKE UPPER(:pNombre)";
		Query locQuery = em.createQuery(consulta, Rubro.class);
		locQuery.setParameter("pNombre", "%" +nombre+ "%");
		List<Rubro> lista = new ArrayList<Rubro>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Rubro> getListadoRubros(){
		inicializar();
		String consulta = "SELECT r FROM Rubro r";
		Query locQuery = em.createQuery(consulta, Rubro.class);
		List<Rubro> lista = locQuery.getResultList();
		
		return lista;
	}
	
	public List<Rubro> getListadoRubrosAct(){
		inicializar();
		String consulta = "SELECT r FROM Rubro r WHERE r.enabled = true";
		Query locQuery = em.createQuery(consulta, Rubro.class);
		List<Rubro> lista = locQuery.getResultList();
		return lista;
	}
	
	public Rubro getRubroXId(Integer id){
		inicializar();
		String consulta = "SELECT r FROM Rubro r WHERE r.id = :pId";
		Query locQuery = em.createQuery(consulta, Rubro.class);
		locQuery.setParameter("pId", id);
		Rubro rubro = new Rubro();
		rubro = (Rubro) locQuery.getSingleResult();
		return rubro;
	}
	
	public HashMap<String, Integer> getHashMapRubrosAct(){
		inicializar();
		HashMap<String, Integer> auxiliar = new LinkedHashMap<String, Integer>();
		List<Rubro> lista = new ArrayList<Rubro>();
		String consulta = "SELECT DISTINCT r FROM Rubro r WHERE r.enabled = true";
		Query locQuery = em.createQuery(consulta, Rubro.class);
		lista = locQuery.getResultList();
		for (Rubro rubro : lista) {
			String nombre = rubro.getNombre();
			Integer id = rubro.getId();
			auxiliar.put(nombre, id);
		}
		return auxiliar;
	}
	
	public List<Rubro> getListadoRubrosDesact(){
		inicializar();
		String consulta = "SELECT r FROM Rubro r WHERE r.enabled = false";
		Query locQuery = em.createQuery(consulta, Rubro.class);
		List<Rubro> lista = locQuery.getResultList();
		return lista;
	}
	
	public int insertRubro(Rubro rubro){
		int retorno;
		if(rubro.getId() != 0){			
			retorno = updateRubro(rubro);
			return retorno;
		}		
		inicializar();		
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(rubro);
			tx.commit();
			retorno = rubro.getId();
			cerrarInstancia();
		}catch (Exception e){
			tx.rollback();
			retorno = 0;
			cerrarInstancia();
		}
		return retorno;		
	}
	
	public int updateRubro(Rubro rubro){
		
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Rubro r SET r.enabled = :pEstado, r.fechaModificacion = :pFechaMod, r.usuario3 = :pusuariomod,"
					+ "r.nombre = :pnombre, r.descripcion = :pdescripcion "
					+ "WHERE r.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", rubro.getId());
			locQuery.setParameter("pEstado", rubro.getEnabled());
			locQuery.setParameter("pFechaMod", rubro.getFechaModificacion());
			locQuery.setParameter("pusuariomod", rubro.getUsuario2());
			locQuery.setParameter("pnombre", rubro.getNombre());
			locQuery.setParameter("pdescripcion", rubro.getDescripcion());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			cerrarInstancia();
			return rubro.getId();
		} catch (Exception e) {
			tx.rollback();
			return 0;
		}
		 
	}

	public int updateEstadoRubro(Rubro rubro){		
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Rubro r SET r.enabled = :pEstado, r.fechaBaja = :pFechaBaja,"
					+ " r.fechaAlta = :pFechaAlta, r.usuario2 = :pUsuarioBaja, r.usuario3 = :pUsuarioModif "
					+ "WHERE r.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", rubro.getId());
			locQuery.setParameter("pEstado", rubro.getEnabled());
			locQuery.setParameter("pUsuarioBaja", rubro.getUsuario2());
			locQuery.setParameter("pUsuarioModif", rubro.getUsuario3());
			locQuery.setParameter("pFechaBaja", rubro.getFechaBaja());
			locQuery.setParameter("pFechaAlta", rubro.getFechaAlta());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			cerrarInstancia();
			return 1;
		} catch (Exception e) {
			tx.rollback();
			return 0;
		}
	}
	
	
	public boolean consultarListaAsoc(Rubro rubro){
		inicializar();
		List<Producto> listAux = new ArrayList<Producto>();
		String consulta = "SELECT p FROM Producto p WHERE p.rubro = :prubro";
		Query loQuery = em.createQuery(consulta, Producto.class);
		loQuery.setParameter("prubro", rubro);
		listAux = loQuery.getResultList();
		cerrarInstancia();
		if(listAux.isEmpty()){
			return false;
		}else{
			return true;
		}
		
		
		
	}
}
