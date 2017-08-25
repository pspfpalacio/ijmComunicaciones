package DAO.cuentascorriente;

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
import model.Cuentascorriente;
import model.Venta;

public class DAOcuentascorriente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public DAOcuentascorriente() {
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
	
	public List<Cuentascorriente> getListaCCCliente(Cliente cliente){
		inicializar();
		String consulta = "SELECT c FROM Cuentascorriente c WHERE c.cliente = :pCliente ORDER BY c.fechaAlta";
		Query locQuery = em.createQuery(consulta, Cuentascorriente.class);
		locQuery.setParameter("pCliente", cliente);
		List<Cuentascorriente> lista = new ArrayList<Cuentascorriente>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Cuentascorriente> getListaCCCliente(Cliente cliente, Date i, Date f){
		inicializar();
		String consulta = "SELECT c FROM Cuentascorriente c WHERE c.cliente = :pCliente AND c.fechaAlta <= :pfinal AND c.fechaAlta >= :pinicio";
		Query locQuery = em.createQuery(consulta, Cuentascorriente.class);
		locQuery.setParameter("pCliente", cliente);
		locQuery.setParameter("pfinal", f);
		locQuery.setParameter("pinicio", i);
		List<Cuentascorriente> lista = new ArrayList<Cuentascorriente>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public Integer getCantidadMovimientosCliente(Cliente cliente){
		inicializar();
		String consulta = "SELECT COUNT(c) FROM Cuentascorriente c WHERE c.cliente = :pCliente";
		Query locQuery = em.createQuery(consulta);
		locQuery.setParameter("pCliente", cliente);
		Integer valor = (Integer) locQuery.getSingleResult();
		return valor;
	}
	
	public List<Cuentascorriente> getListaCCProveedor(Cliente proveedor){
		inicializar();
		String consulta = "SELECT c FROM Cuentascorriente c WHERE c.cliente = :pProveedor ORDER BY c.fechaAlta";
		Query locQuery = em.createQuery(consulta, Cuentascorriente.class);
		locQuery.setParameter("pProveedor", proveedor);
		List<Cuentascorriente> lista = new ArrayList<Cuentascorriente>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Cuentascorriente> getListaCCProveedor(Cliente proveedor, Date i, Date f){
		inicializar();
		String consulta = "SELECT c FROM Cuentascorriente c WHERE c.cliente = :pProveedor AND c.fechaAlta <= :pfinal AND c.fechaAlta >= :pinicio";
		Query locQuery = em.createQuery(consulta, Cuentascorriente.class);
		locQuery.setParameter("pProveedor", proveedor);
		locQuery.setParameter("pinicio", i);
		locQuery.setParameter("pfinal", f);
		List<Cuentascorriente> lista = new ArrayList<Cuentascorriente>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public int insertCuentaCorriente(Cuentascorriente cuentasCorriente){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(cuentasCorriente);
			tx.commit();
			int retorno = cuentasCorriente.getId();
			System.out.println("try insercc");
//			cerrarInstancia();
			return retorno;
		}catch(Exception e){
			System.out.println(e.getMessage());
			tx.rollback();
			System.out.println("catch insertcc");
//			cerrarInstancia();
			return 0;
		}
	}
	
	//LO HICE YO
	public List<Cuentascorriente> getListCuentaCorrientePorVenta(int venta){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Cuentascorriente c WHERE c.idMovimiento = :pMov "
				+ "AND c.tipoMovimiento = 'VENTA'", Cuentascorriente.class);
		locQuery.setParameter("pMov", venta);
		List<Cuentascorriente> lista = new ArrayList<Cuentascorriente>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Cuentascorriente> getListaCCIJM(){
		inicializar();
		Cliente ijm = new Cliente();
		ijm.setId(1);
		Query locQuery = em.createQuery("SELECT c FROM Cuentascorriente c WHERE c.cliente = :pIJM", Cuentascorriente.class);
		locQuery.setParameter("pIJM", ijm);
		List<Cuentascorriente> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Cuentascorriente> getListaCCIJM(Date i, Date f){
		inicializar();
		Cliente ijm = new Cliente();
		ijm.setId(1);
		Query locQuery = em.createQuery("SELECT c FROM Cuentascorriente c WHERE c.cliente = :pIJM AND c.fechaAlta <= :final AND c.fechaAlta >= :inicio", Cuentascorriente.class);
		locQuery.setParameter("pIJM", ijm);
		locQuery.setParameter("inicio", i);
		locQuery.setParameter("final", f);
		
		List<Cuentascorriente> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Cuentascorriente> getListaVistaCC(Date i, Date f, Cliente cliente){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Cuentascorriente c WHERE c.cliente = :pCliente AND c.fechaAlta BETWEEN :inicio AND :final", Cuentascorriente.class);
		locQuery.setParameter("pCliente", cliente);
		locQuery.setParameter("inicio", i);
		locQuery.setParameter("final", f);		
		List<Cuentascorriente> lista = locQuery.getResultList();
		return lista;
	}
	
	public Cuentascorriente getItemPagoCC(int idCliente, int idMovimiento, String tipo){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Cuentascorriente c WHERE c.cliente.id <> :pIdCliente "
				+ "AND c.idMovimiento = :pIdMovimiento AND c.tipoMovimiento = :pTipoMovimiento");
		locQuery.setParameter("pIdCliente", idCliente);
		locQuery.setParameter("pIdMovimiento", idMovimiento);
		locQuery.setParameter("pTipoMovimiento", tipo);
		Cuentascorriente cuenta = new Cuentascorriente();
		cuenta = (Cuentascorriente) locQuery.getSingleResult();
		return cuenta;
	}
	
	public int deleteRegistroCompra(int idCompra, Cliente proveedor){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "DELETE FROM Cuentascorriente c "
					+ "WHERE c.idMovimiento = :pIdCompra AND c.cliente = :pProveedor AND c.tipoMovimiento = 'COMPRA'";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pIdCompra", idCompra);
			locQuery.setParameter("pProveedor", proveedor);
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

}
