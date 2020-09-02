package DAO.cargasvirtuales;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.CargaVirtual;
import model.Cliente;
import model.CompraCargavirtual;

public class DAOCargaVirtual implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public DAOCargaVirtual() {
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
	
	public List<CargaVirtual> getListadoCargas(){
		inicializar();
		String consulta = "SELECT c FROM CargaVirtual c";
		Query locQuery = em.createQuery(consulta, CargaVirtual.class);
		List<CargaVirtual> lista = locQuery.getResultList();	
		return lista;
	}
	
	public List<CargaVirtual> getListadoCargasAct(){
		inicializar();
		String consulta = "SELECT c FROM CargaVirtual c WHERE c.enabled = true";
		Query locQuery = em.createQuery(consulta, CargaVirtual.class);
		List<CargaVirtual> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<CargaVirtual> getListadoCargassDesact(){
		inicializar();
		String consulta = "SELECT c FROM CargaVirtual c WHERE c.enabled = false";
		Query locQuery = em.createQuery(consulta, CargaVirtual.class);
		List<CargaVirtual> lista = locQuery.getResultList();
		return lista;
	}
	
	public int insertCargaVirtual(CargaVirtual carga){
		int retorno;		
		inicializar();		
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(carga);
			tx.commit();
			retorno = carga.getId();
			cerrarInstancia();
		}catch (Exception e){
			tx.rollback();
			retorno = 0;
			cerrarInstancia();
		}
		return retorno;		
	}
	
	public int updateCargaVirtual(CargaVirtual carga){
		
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE CargaVirtual c SET c.enabled = :pEstado, c.cantidadMonto = :pCantidad, c.nombre = :pNombre, "
					+ "c.usuario = :pUsuario "
					+ "WHERE c.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pEstado", carga.getEnabled());
			locQuery.setParameter("pCantidad", carga.getCantidadMonto());
			locQuery.setParameter("pNombre", carga.getNombre());
			locQuery.setParameter("pUsuario", carga.getUsuario());
			locQuery.setParameter("pId", carga.getId());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			cerrarInstancia();
			return carga.getId();
		} catch (Exception e) {
			tx.rollback();
			return 0;
		}
		 
	}

	public int updateEstadoCargaVirtual(CargaVirtual carga){	
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE CargaVirtual c SET c.enabled = :pEstado "
					+ "WHERE c.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", carga.getId());
			locQuery.setParameter("pEstado", carga.getEnabled());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			int retorno = carga.getId();
			cerrarInstancia();
			return retorno;
		} catch (Exception e) {
			tx.rollback();
			cerrarInstancia();
			return 0;
		}
	}
	
	public CargaVirtual getCargaVirtualXId(int idCargaVirtual){
		inicializar();
		CargaVirtual carga = new CargaVirtual();
		Query locQuery = em.createQuery("SELECT c FROM CargaVirtual c WHERE c.id = :pId", CargaVirtual.class);
		locQuery.setParameter("pId", idCargaVirtual);
		carga = (CargaVirtual) locQuery.getSingleResult();
		return carga;
	}
	
	public List<CompraCargavirtual> getListadoCompraCargas(){
		inicializar();
		String consulta = "SELECT c FROM CompraCargavirtual c ORDER BY c.fechaAlta DESC";
		Query locQuery = em.createQuery(consulta, CargaVirtual.class);
		List<CompraCargavirtual> lista = locQuery.getResultList();	
		return lista;
	}
	
	public List<CompraCargavirtual> getListadoCompraCargas(CargaVirtual cargaVirtual){
		inicializar();
		String consulta = "SELECT c FROM CompraCargavirtual c WHERE c.cargaVirtual = :pCargaVirtual";
		Query locQuery = em.createQuery(consulta, CargaVirtual.class);
		locQuery.setParameter("pCargaVirtual", cargaVirtual);
		List<CompraCargavirtual> lista = locQuery.getResultList();	
		return lista;
	}
	
	public List<CompraCargavirtual> getListadoCompraCargasAct(){
		inicializar();
		String consulta = "SELECT c FROM CompraCargavirtual c WHERE c.enabled = true ORDER BY c.fechaAlta DESC";
		Query locQuery = em.createQuery(consulta, CargaVirtual.class);
		List<CompraCargavirtual> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<CompraCargavirtual> getListadoCompraCargassDesact(){
		inicializar();
		String consulta = "SELECT c FROM CompraCargavirtual c WHERE c.enabled = false ORDER BY c.fechaAlta DESC";
		Query locQuery = em.createQuery(consulta, CargaVirtual.class);
		List<CompraCargavirtual> lista = locQuery.getResultList();
		return lista;
	}
	
	public int updateEstadoCompraCargaVirtual(CompraCargavirtual carga){	
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE CompraCargavirtual c SET c.enabled = :pEstado, c.fechaAlta = :pFechaAlta, "
					+ "c.fechaBaja = :pFechaBaja, c.usuario1 = :pUsuarioAlta, c.usuario2 = :pUsuarioBaja "
					+ "WHERE c.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", carga.getId());
			locQuery.setParameter("pEstado", carga.getEnabled());
			locQuery.setParameter("pFechaAlta", carga.getFechaAlta());
			locQuery.setParameter("pFechaBaja", carga.getFechaBaja());
			locQuery.setParameter("pUsuarioAlta", carga.getUsuario1());
			locQuery.setParameter("pUsuarioBaja", carga.getUsuario2());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			int retorno = carga.getId();
			cerrarInstancia();
			return retorno;
		} catch (Exception e) {
			tx.rollback();
			cerrarInstancia();
			return 0;
		}
	}
	
	public int insertCompraCargaVirtual(CompraCargavirtual carga){
		int retorno;		
		inicializar();		
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(carga);
			tx.commit();
			retorno = carga.getId();
			cerrarInstancia();
		}catch (Exception e){
			tx.rollback();
			retorno = 0;
			cerrarInstancia();
		}
		return retorno;		
	}
	
	public CompraCargavirtual getCompraPorId(int id){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM CompraCargavirtual c WHERE c.id = :pId");
		locQuery.setParameter("pId", id);
		CompraCargavirtual compra = new CompraCargavirtual();
		compra = (CompraCargavirtual) locQuery.getSingleResult();
		return compra;
	}
	
	public List<Cliente> getListCargaProv(){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Cliente c WHERE c.tipo = 'v'");
		List<Cliente> lista = new ArrayList<Cliente>();
		lista = locQuery.getResultList();
		return lista;
	}

}
