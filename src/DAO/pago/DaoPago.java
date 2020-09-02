package DAO.pago;

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
import model.Cuentascorriente;
import model.Pago;
import model.Rubro;

public class DaoPago implements Serializable {

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
	
	public HashMap<String,Integer> getHashMapClientesAct(){

		inicializar();
		HashMap<String, Integer> auxiliar = new LinkedHashMap<String, Integer>();
		List<Cliente> lista = new ArrayList<Cliente>();
		String consulta = "SELECT DISTINCT c FROM Cliente c WHERE c.enabled = true";
		Query locQuery = em.createQuery(consulta, Cliente.class);
		lista = locQuery.getResultList();
		for (Cliente cli : lista) {
			String nombre = cli.getNombre();
			Integer id = cli.getId();
			auxiliar.put(nombre, id);
		}
		return auxiliar;
	}
	
	
	public Cliente getCliente(int idCli){
		Cliente cliente = new Cliente();
		inicializar();
		String consulta = "SELECT c FROM Cliente c where c.id = :pid";
		Query locQuery = em.createQuery(consulta, Cliente.class);
		locQuery.setParameter("pid", idCli);
		cliente = (Cliente) locQuery.getSingleResult();
		cerrarInstancia();
		return cliente;
	}
	
	
	public Cliente getIJM(){
		Cliente cliente = new Cliente();
		inicializar();
		String consulta = "SELECT c FROM Cliente c WHERE c.tipo = 'i'";
		Query locQuery = em.createQuery(consulta, Cliente.class);
		cliente = (Cliente) locQuery.getSingleResult();
		cerrarInstancia();
		return cliente;
	}
	
	public boolean insertCC(Cuentascorriente cc){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(cc);
			tx.commit();
			cerrarInstancia();
			inicializar();
			return true;
		} catch (Exception e) {
			tx.rollback();
			cerrarInstancia();
			return false;
		}
		
	}
	
	public int insertPago(Pago pago){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(pago);
			tx.commit();
			cerrarInstancia();
			inicializar();
			return pago.getId();
		} catch (Exception e) {
			tx.rollback();
			cerrarInstancia();
			return 0;
		}		
	}
	
	//ESTO LO HICE YO
	
	public Pago getPagoPorId(Integer id){
		inicializar();
		Query locQuery = em.createQuery("SELECT p FROM Pago p WHERE p.id = :pID", Pago.class);
		locQuery.setParameter("pID", id);
		Pago pago = new Pago();
		pago = (Pago) locQuery.getSingleResult();
		return pago;
	}
}
