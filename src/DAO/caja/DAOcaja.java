package DAO.caja;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Caja;
import model.Cliente;

public class DAOcaja implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	public List<Caja> getListadoCaja(){
		inicializar();
		String consulta = "SELECT c FROM Caja c ORDER BY c.id DESC";
		Query locQuery = em.createQuery(consulta, Caja.class);
		List<Caja> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Caja> getListadoCajaOrdenadoParaControl(){
		inicializar();
		String consulta = "SELECT c FROM Caja c ORDER BY c.fecha";
		Query locQuery = em.createQuery(consulta, Caja.class);
		List<Caja> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Caja> getListadoCaja(Date fechai, Date fechaf){
		inicializar();
		String consulta = "SELECT c FROM Caja c WHERE c.fecha BETWEEN :start AND :end ORDER BY c.fecha DESC";
		Query locQuery = em.createQuery(consulta, Caja.class);
		locQuery.setParameter("start", fechai);
		locQuery.setParameter("end", fechaf);
		List<Caja> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Caja> getListadoCajaEntrada(){
		inicializar();
		String consulta = "SELECT c FROM Caja c WHERE c.tipo = 'Entrada' ORDER BY c.fecha DESC";
		Query locQuery = em.createQuery(consulta, Caja.class);
		List<Caja> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Caja> getListadoCajaEntrada(Date fechai, Date fechaf){
		inicializar();
		String consulta = "SELECT c FROM Caja c WHERE c.tipo = 'Entrada' AND c.fecha BETWEEN :start AND :end ORDER BY c.fecha DESC";
		Query locQuery = em.createQuery(consulta, Caja.class);
		locQuery.setParameter("start", fechai);
		locQuery.setParameter("end", fechaf);
		List<Caja> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Caja> getListadoCajaSalida(){
		inicializar();
		String consulta = "SELECT c FROM Caja c WHERE c.tipo = 'Salida' ORDER BY c.fecha DESC";
		Query locQuery = em.createQuery(consulta, Caja.class);
		List<Caja> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Caja> getListadoCajaSalida(Date fechai, Date fechaf){
		inicializar();
		String consulta = "SELECT c FROM Caja c WHERE c.tipo = 'Salida' AND c.fecha BETWEEN :start AND :end ORDER BY c.fecha DESC";
		Query locQuery = em.createQuery(consulta, Caja.class);
		locQuery.setParameter("start", fechai);
		locQuery.setParameter("end", fechaf);
		List<Caja> lista = locQuery.getResultList();
		return lista;
	}
	
	public int insertCaja(Caja caja){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(caja);
			tx.commit();
			int retorno = caja.getId();
			cerrarInstancia();
			return retorno;
		}catch (Exception e){
			tx.rollback();
			cerrarInstancia();
			return 0;
		}	
	}
	
	public Caja obtenerUlt(){
		inicializar();
		Query locQuery = em.createQuery("SELECT MAX(c.id) FROM Caja c");
		Integer id = (Integer) locQuery.getSingleResult();
		Caja caja = new Caja();
		if(id != null){
			Query locQuery2 = em.createQuery("SELECT c FROM Caja c WHERE c.id = :pId");
			locQuery2.setParameter("pId", id);
			caja = (Caja) locQuery2.getSingleResult();
		}
		return caja;
	}
	
	public int update(Caja caja){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			String consulta = "UPDATE Caja c SET c.concepto = :pConcepto, c.fecha = :pFecha, "
					+ "c.idMovimiento = :pIdMovimiento, c.monto = :pMonto, c.tipo = :pTipo, "
					+ "c.total = :pTotal "
					+ "WHERE c.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pConcepto", caja.getConcepto());
			locQuery.setParameter("pFecha", caja.getFecha());
			locQuery.setParameter("pIdMovimiento", caja.getIdMovimiento());
			locQuery.setParameter("pMonto", caja.getMonto());
			locQuery.setParameter("pTipo", caja.getTipo());
			locQuery.setParameter("pTotal", caja.getTotal());
			locQuery.setParameter("pId", caja.getId());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			int retorno = caja.getId();
			System.out.println("try updatecaja");
//			cerrarInstancia();
			return retorno;
		}catch (Exception e){
			System.out.println(e.getMessage());
			tx.rollback();
			System.out.println("catch updatecaja");
//			cerrarInstancia();
			return 0;
		}	
	}

}
