package DAO.proveedor;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Cliente;

public class DAOproveedor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public DAOproveedor() {
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
	
	public List<Cliente> getListadoProveedores(){
		inicializar();
		String consulta = "SELECT c FROM Cliente c WHERE c.tipo = 'p'";
		Query locQuery = em.createQuery(consulta, Cliente.class);
		List<Cliente> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Cliente> getListadoProveedoresAct(){
		inicializar();
		String consulta = "SELECT c FROM Cliente c WHERE c.tipo = 'p' AND c.enabled = true";
		Query locQuery = em.createQuery(consulta, Cliente.class);
		List<Cliente> lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Cliente> getListadoProveedoresDesact(){
		inicializar();
		String consulta = "SELECT c FROM Cliente c WHERE c.tipo = 'p' AND c.enabled = false";
		Query locQuery = em.createQuery(consulta, Cliente.class);
		List<Cliente> lista = locQuery.getResultList();
		return lista;
	}
	
	public int insertProveedor(Cliente proveedor){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(proveedor);
			tx.commit();
			int retorno = proveedor.getId();
			cerrarInstancia();
			return retorno;
		}catch (Exception e){
			tx.rollback();
			cerrarInstancia();
			return 0;
		}	
	}
	
	public int updateProveedor(Cliente proveedor){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try{
			String consulta = "UPDATE Cliente c SET c.apellido = :pApellido, c.direccion = :pDireccion, "
					+ "c.fechaModificacion = :pFechaModificacion, c.mail = :pMail, c.nombre = :pNombre, "
					+ "c.telefono = :pTelefono, c.saldo = :pSaldo, c.usuario3 = :pUsuarioMod "
					+ "WHERE c.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", proveedor.getId());
			locQuery.setParameter("pApellido", proveedor.getApellido());
			locQuery.setParameter("pDireccion", proveedor.getDireccion());
			locQuery.setParameter("pFechaModificacion", proveedor.getFechaModificacion());
			locQuery.setParameter("pMail", proveedor.getMail());
			locQuery.setParameter("pNombre", proveedor.getNombre());
			locQuery.setParameter("pTelefono", proveedor.getTelefono());
			locQuery.setParameter("pUsuarioMod", proveedor.getUsuario3());
			locQuery.setParameter("pSaldo", proveedor.getSaldo());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			int retorno = proveedor.getId();
			cerrarInstancia();
			return retorno;
		}catch (Exception e){
			tx.rollback();
			cerrarInstancia();
			return 0;
		}	
	}
	
	public int updateEstadoProveedor(Cliente proveedor){
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Cliente c SET c.enabled = :pEstado, c.fechaBaja = :pFechaBaja,"
					+ " c.fechaAlta = :pFechaAlta, c.usuario2 = :pUsuarioBaja, c.usuario3 = :pUsuarioModif "
					+ "WHERE c.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", proveedor.getId());
			locQuery.setParameter("pEstado", proveedor.getEnabled());
			locQuery.setParameter("pUsuarioBaja", proveedor.getUsuario2());
			locQuery.setParameter("pUsuarioModif", proveedor.getUsuario3());
			locQuery.setParameter("pFechaBaja", proveedor.getFechaBaja());
			locQuery.setParameter("pFechaAlta", proveedor.getFechaAlta());
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
	
	public Cliente getProveedorXId(Integer id){
		inicializar();
		Query locQuery = em.createQuery("SELECT c FROM Cliente c WHERE c.id = :pId", Cliente.class);
		locQuery.setParameter("pId", id);
		Cliente proveedore = new Cliente();
		proveedore = (Cliente) locQuery.getSingleResult();
		return proveedore;
	}

}
