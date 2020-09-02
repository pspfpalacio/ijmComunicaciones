package DAO.cliente;

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
import model.ClientePlataforma;
import model.Listaprecio;

public class DAOcliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public DAOcliente() {
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
	
	public List<Cliente> getListadoClientes(){
		inicializar();
		String consulta = "SELECT c FROM Cliente c WHERE c.tipo = 'c'";
		Query locQuery = em.createQuery(consulta, Cliente.class);
		List<Cliente> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Cliente> getListadoClientesAct(){
		inicializar();
		String consulta = "SELECT c FROM Cliente c WHERE c.tipo = 'c' AND c.enabled = true";
		Query locQuery = em.createQuery(consulta, Cliente.class);
		List<Cliente> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Cliente> getListadoClientesDesact(){
		inicializar();
		String consulta = "SELECT c FROM Cliente c WHERE c.tipo = 'c' AND c.enabled = false";
		Query locQuery = em.createQuery(consulta, Cliente.class);
		List<Cliente> lista = locQuery.getResultList();
		return lista;
	}
	
	public int insertCliente(Cliente cliente){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			//System.out.println(cliente.getEnabled());
			em.persist(cliente);
			tx.commit();
			int retorno = cliente.getId();
			cerrarInstancia();
			return retorno;
		}catch (Exception e){
			tx.rollback();
			cerrarInstancia();
			return 0;
		}	
	}
	
	public int updateCliente(Cliente cliente){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			String consulta = "UPDATE Cliente c SET c.apellido = :pApellido, c.direccion = :pDireccion, "
					+ "c.fechaModificacion = :pFechaModificacion, c.mail = :pMail, c.nombre = :pNombre, "
					+ "c.nroTarjeta = :pNroTarjeta, c.tipoTarjeta = :pTipoTarjeta, "
					+ "c.telefono = :pTelefono, c.saldo = :pSaldo, c.listaprecio = :pListaPrecio, c.usuario3 = :pUsuarioMod "
					+ "WHERE c.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", cliente.getId());
			locQuery.setParameter("pApellido", cliente.getApellido());
			locQuery.setParameter("pDireccion", cliente.getDireccion());
			locQuery.setParameter("pFechaModificacion", cliente.getFechaModificacion());
			locQuery.setParameter("pMail", cliente.getMail());
			locQuery.setParameter("pNombre", cliente.getNombre());
			locQuery.setParameter("pNroTarjeta", cliente.getNroTarjeta());
			locQuery.setParameter("pTipoTarjeta", cliente.getTipoTarjeta());
			locQuery.setParameter("pTelefono", cliente.getTelefono());
			locQuery.setParameter("pSaldo", cliente.getSaldo());
			locQuery.setParameter("pListaPrecio", cliente.getListaprecio());
			locQuery.setParameter("pUsuarioMod", cliente.getUsuario3());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			int retorno = cliente.getId();
			System.out.println("try updatecliente");
//			cerrarInstancia();
			return retorno;
		}catch (Exception e){
			System.out.println(e.getMessage());
			tx.rollback();
			System.out.println("catch updatecliente");
//			cerrarInstancia();
			return 0;
		}	
	}
	
	public int updateEstadoCliente(Cliente cliente){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Cliente c SET c.enabled = :pEstado, c.fechaBaja = :pFechaBaja,"
					+ " c.fechaAlta = :pFechaAlta, c.usuario2 = :pUsuarioBaja, c.usuario3 = :pUsuarioModif "
					+ "WHERE c.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", cliente.getId());
			locQuery.setParameter("pEstado", cliente.getEnabled());
			locQuery.setParameter("pUsuarioBaja", cliente.getUsuario2());
			locQuery.setParameter("pUsuarioModif", cliente.getUsuario3());
			locQuery.setParameter("pFechaBaja", cliente.getFechaBaja());
			locQuery.setParameter("pFechaAlta", cliente.getFechaAlta());
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
	
	public List<Listaprecio> getListasPrecio(){
		inicializar();
		List<Listaprecio> lista = new ArrayList<Listaprecio>();
		String consulta = "SELECT l FROM Listaprecio l WHERE l.enabled = true";
		Query locQuery = em.createQuery(consulta, Listaprecio.class);
		lista = locQuery.getResultList();
		return lista;
	}
	
	public HashMap<String,Integer> getHashMapListas(){
		inicializar();
		HashMap<String, Integer> auxiliar = new LinkedHashMap<String, Integer>();
		List<Listaprecio> lista = new ArrayList<Listaprecio>();
		String consulta = "SELECT l FROM Listaprecio l WHERE l.enabled = true";
		Query locQuery = em.createQuery(consulta, Listaprecio.class);
		lista = locQuery.getResultList();
		for (Listaprecio listaprecio : lista) {
			String nombre = listaprecio.getNombre();
			Integer id = listaprecio.getId();
			auxiliar.put(nombre, id);
		}
		return auxiliar;
	}
	
	public int deletePlataforma(Cliente cliente){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "DELETE FROM ClientePlataforma c "
					+ "WHERE c.cliente = :pCliente";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pCliente", cliente);
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
	
	public int insertClientePlataforma(ClientePlataforma clientePlataform){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(clientePlataform);
			tx.commit();
			int retorno = clientePlataform.getId();
			cerrarInstancia();
			return retorno;
		}catch (Exception e){
			tx.rollback();
			cerrarInstancia();
			return 0;
		}	
	}
	
	public List<ClientePlataforma> getPlataformasCliente(Cliente cliente){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM ClientePlataforma c WHERE c.cliente = :pCliente", ClientePlataforma.class);
		locQuery.setParameter("pCliente", cliente);
		List<ClientePlataforma> lista = new ArrayList<ClientePlataforma>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public Cliente getClientePorId(int id){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Cliente c WHERE c.id = :pId", Cliente.class);
		locQuery.setParameter("pId", id);
		Cliente cliente = new Cliente();
		cliente = (Cliente) locQuery.getSingleResult();
		return cliente;
	}
	
	public List<Cliente> getListadoClientesProveedores(){
		inicializar();
		String consulta = "SELECT c FROM Cliente c WHERE c.tipo = 'c' OR c.tipo = 'p'";
		Query locQuery = em.createQuery(consulta, Cliente.class);
		List<Cliente> lista = locQuery.getResultList();
		return lista;
	}

}
