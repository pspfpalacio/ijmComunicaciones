package DAO.cuentas;

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
import model.Cuenta;

public class DAOCuentas implements Serializable {

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
	
	public int insertar(Cuenta cuenta){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(cuenta);
			tx.commit();
			return cuenta.getId();
		}catch(Exception e){
			System.out.println(e.getMessage());
			tx.rollback();
			return 0;
		}
	}
	
	public int update(Cuenta cuenta){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			String consulta = "UPDATE Cuenta c SET c.cliente = :pCliente, c.debe = :pDebe, "
					+ "c.detalle = :pDetalle, c.fecha = :pFecha, c.haber = :pHaber, c.idMovimiento = :pMovimiento, "
					+ "c.monto = :pMonto, c.nombreTabla = :pNombreTabla, c.saldo = :pSaldo, c.usuario = :pUsuario "
					+ "WHERE c.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pCliente", cuenta.getCliente());
			locQuery.setParameter("pDebe", cuenta.getDebe());
			locQuery.setParameter("pDetalle", cuenta.getDetalle());
			locQuery.setParameter("pFecha", cuenta.getFecha());
			locQuery.setParameter("pHaber", cuenta.getHaber());
			locQuery.setParameter("pMovimiento", cuenta.getIdMovimiento());
			locQuery.setParameter("pMonto", cuenta.getMonto());
			locQuery.setParameter("pNombreTabla", cuenta.getNombreTabla());
			locQuery.setParameter("pSaldo", cuenta.getSaldo());
			locQuery.setParameter("pUsuario", cuenta.getUsuario());
			locQuery.setParameter("pId", cuenta.getId());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			return cuenta.getId();
		}catch (Exception e){
			System.out.println(e.getMessage());
			tx.rollback();
			return 0;
		}	
	}
	
	public List<Cuenta> getLista(){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Cuenta c ORDER BY c.fecha DESC, c.id DESC", Cuenta.class);
		List<Cuenta> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Cuenta> getLista(Cliente cliente){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Cuenta c WHERE c.cliente = :pCliente ORDER BY c.fecha DESC, c.id DESC", Cuenta.class);
		locQuery.setParameter("pCliente", cliente);
		List<Cuenta> lista = new ArrayList<Cuenta>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Cuenta> getLista(Date fechaInicio, Date fechaFin, Cliente cliente){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Cuenta c WHERE c.cliente = :pCliente AND c.fecha BETWEEN :pFechaInicio "
				+ "AND :pFechaFin ORDER BY c.fecha DESC, c.id DESC", Cuenta.class);
		locQuery.setParameter("pCliente", cliente);
		locQuery.setParameter("pFechaInicio", fechaInicio);
		locQuery.setParameter("pFechaFin", fechaFin);
		List<Cuenta> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Cuenta> getLista(int idMovimiento, String nombreTabla){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Cuenta c WHERE c.idMovimiento = :pIdMovimiento AND "
				+ "c.nombreTabla = :pNombreTabla ", Cuenta.class);
		locQuery.setParameter("pIdMovimiento", idMovimiento);
		locQuery.setParameter("pNombreTabla", nombreTabla);
		List<Cuenta> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Cuenta> getListaOrdenadaPorFecha(Date fechaInicio, Date fechaFin, Cliente cliente){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Cuenta c WHERE c.cliente = :pCliente AND c.fecha BETWEEN :pFechaInicio "
				+ "AND :pFechaFin ORDER BY c.fecha", Cuenta.class);
		locQuery.setParameter("pCliente", cliente);
		locQuery.setParameter("pFechaInicio", fechaInicio);
		locQuery.setParameter("pFechaFin", fechaFin);
		List<Cuenta> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Cuenta> getListaClienteOrdenadaPorFecha(Cliente cliente) {
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Cuenta c WHERE c.cliente = :pCliente "
				+ "ORDER BY c.fecha, c.id", Cuenta.class);
		locQuery.setParameter("pCliente", cliente);
		List<Cuenta> lista = locQuery.getResultList();
		return lista;
	}

}
