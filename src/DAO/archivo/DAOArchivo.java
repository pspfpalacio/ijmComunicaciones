package DAO.archivo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Excel;

public class DAOArchivo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EntityManagerFactory emf;
	private EntityManager em;

	public void inicializar() {
		emf = Persistence.createEntityManagerFactory("ijmComunicaciones");
		em = emf.createEntityManager();
	}
	
	public void cerrarInstancia() {
		em.close();
		emf.close();
	}
	
	public int insertar(Excel excel ){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(excel);
			tx.commit();
			return excel.getId();
		}catch (Exception e){
			tx.rollback();
			return 0;
		}	
	}
	
	public int update(Excel excel) {
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			String consulta = "UPDATE Excel e SET e.ctacte = :pCtaCte, e.fecha = :pFecha, "
					+ "e.stock = :pStock, e.ventas = :pVentas "
					+ "WHERE e.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", excel.getId());
			locQuery.setParameter("pCtaCte", excel.getCtacte());
			locQuery.setParameter("pFecha", excel.getFecha());
			locQuery.setParameter("pStock", excel.getStock());
			locQuery.setParameter("pVentas", excel.getVentas());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			return excel.getId();
		}catch (Exception e){
			System.out.println(e.getMessage());
			tx.rollback();
			return 0;
		}	
	}
	
	public Excel get(int id) {
		inicializar();
		Query locQuery = em.createQuery("SELECT e FROM Excel e WHERE e.id = :pId", Excel.class);
		locQuery.setParameter("pId", id);
		Excel excel = new Excel();
		try {
			excel = (Excel) locQuery.getSingleResult();
		} catch (Exception e) {
			excel = new Excel();
		}
		return excel;
	}
	
	public List<Excel> getLista() {
		inicializar();
		Query locQuery = em.createQuery("SELECT e FROM Excel e", Excel.class);
		List<Excel> lista = new ArrayList<Excel>();
		lista = locQuery.getResultList();
		return lista;
	}

}
