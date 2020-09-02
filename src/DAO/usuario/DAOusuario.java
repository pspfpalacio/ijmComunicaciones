package DAO.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import auxiliares.Helper;
import model.Cliente;
import model.RolVista;
import model.Role;
import model.Usuario;
import model.Usuariosecurity;
import model.Vista;

public class DAOusuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public DAOusuario() {
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
	
	public Integer getIdPermiso(String nombre) {
		inicializar();
		try {
			String consulta = "SELECT p.id FROM Permiso p "
					+ "WHERE p.nombre = :pNombre";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pNombre", nombre);
			Integer id = (Integer) locQuery.getSingleResult();
			cerrarInstancia();
			return id;
		} catch (Exception e) {
			return null;
		}

	}

	public int updateUsuario(Usuario usuario) {
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Usuario u "
					+ "SET u.nombre = :pNombre, u.password = :pPass, u.username = :pUsername, "
					+ "u.role = :pRol "
					+ "WHERE u.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pNombre", usuario.getNombre());
			locQuery.setParameter("pPass", usuario.getPassword());
			locQuery.setParameter("pUsername", usuario.getUsername());
			locQuery.setParameter("pRol", usuario.getRole());
			locQuery.setParameter("pId", usuario.getId());
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

	public int agregarUsser(Usuario usuario) {
		try {
			inicializar();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(usuario);
			tx.commit();
			cerrarInstancia();
			return 1;
		} catch (Exception e) {
			return 0;
		}

	}

	public List<Usuario> getListUsuarios() {
		inicializar();
		List<Usuario> lista = new ArrayList<Usuario>();
		String consulta = "SELECT u FROM Usuario u WHERE u.id <> 1";
		Query locQuery = em.createQuery(consulta, Usuario.class);
		lista = locQuery.getResultList();
		cerrarInstancia();
		return lista;
	}
	
	public List<Usuario> getListUsuariosAct() {
		inicializar();
		List<Usuario> lista = new ArrayList<Usuario>();
		String consulta = "SELECT u FROM Usuario u WHERE u.enabled = true AND u.id <> 1";
		Query locQuery = em.createQuery(consulta, Usuario.class);
		lista = locQuery.getResultList();
		cerrarInstancia();
		return lista;
	}
	
	public List<Usuario> getListUsuariosDesact() {
		inicializar();
		List<Usuario> lista = new ArrayList<Usuario>();
		String consulta = "SELECT u FROM Usuario u WHERE u.enabled = false AND u.id <> 1";
		Query locQuery = em.createQuery(consulta, Usuario.class);
		lista = locQuery.getResultList();
		cerrarInstancia();
		return lista;
	}

	public int activarUsuario(Usuario user) {
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Usuario u SET u.enabled = :pEstado WHERE u.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pEstado", true);
			locQuery.setParameter("pId", user.getId());
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

	public int desactivarUsuario(Usuario user) {
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Usuario u SET u.enabled = :pEstado WHERE u.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pEstado", false);
			locQuery.setParameter("pId", user.getId());
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

	public boolean Autenticar(String user, String pass) {
		inicializar();
		Helper helper = new Helper();
		String hash = helper.EncodePassword(pass);
		// hash = hash.replaceAll("[\n\r]", "");
		// //System.out.println(usuar.getPassword());
		String consulta = "SELECT u FROM Usuario u WHERE u.username = :pUSER AND u.password = :pPASS AND u.enabled = 1";
		// String consulta = "SELECT u FROM Usuario u WHERE u.usuarioID = 1";
		Query locQuery = em.createQuery(consulta, Usuario.class);
		locQuery.setParameter("pUSER", user);
		locQuery.setParameter("pPASS", hash);
		List<Usuario> lista = locQuery.getResultList();
		int cantidad = 0;
		for (Usuario usuario : lista) {
			if (user.equals(usuario.getUsername())
					&& hash.equals(usuario.getPassword())) {
				cantidad++;
			}
		}
		if (cantidad > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int getIdUser(String nameUser, String pass) {
		inicializar();
		Helper helper = new Helper();
		String hash = helper.EncodePassword(pass);
		// hash = hash.replaceAll("[\n\r]", "");
		// //System.out.println(usuar.getPassword());
		String consulta = "SELECT u FROM Usuario u WHERE u.username = :pUSER AND u.password = :pPASS AND u.enabled = 1";
		// String consulta = "SELECT u FROM Usuario u WHERE u.usuarioID = 1";
		Query locQuery = em.createQuery(consulta, Usuario.class);
		locQuery.setParameter("pUSER", nameUser);
		locQuery.setParameter("pPASS", hash);
		List<Usuario> lista = locQuery.getResultList();
		int idUser = 0;
		for (Usuario usuario : lista) {
			if (nameUser.equals(usuario.getUsername())
					&& hash.equals(usuario.getPassword())) {
				idUser = usuario.getId();
			}
		}
		return idUser;
	}

	public boolean cambiarPass(int idUsuario, String newPass) {
		inicializar();
		Helper helper = new Helper();
		String hashNew = helper.EncodePassword(newPass);
		try {
			String consulta = "UPDATE Usuario u "
					+ "SET u.password = :pPass WHERE u.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pPass", hashNew);
			locQuery.setParameter("pId", idUsuario);
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Usuario getUser(String user, String pass) {
		inicializar();
		Helper helper = new Helper();
		String contrasenia = helper.EncodePassword(pass);
		Query locQuery = em
				.createQuery(
						"SELECT u FROM Usuario u WHERE u.username = :pUSER AND u.password = :pPASS AND u.enabled = 1",
						Usuario.class);
		locQuery.setParameter("pUSER", user);
		locQuery.setParameter("pPASS", contrasenia);
		Usuario usuario = new Usuario();
		usuario = (Usuario) locQuery.getSingleResult();
		return usuario;
	}

	public void Security(Usuariosecurity usuarioSecurity) {
		inicializar();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(usuarioSecurity);
		tx.commit();
	}

	public Usuario prConsultaUsuario(String usuario, String password) {
		inicializar();
		Usuario user = new Usuario();
		Helper helper = new Helper();
		String hash = helper.EncodePassword(password);
		String consulta = "SELECT u FROM Usuario u WHERE u.username = :pUSER AND u.password = :pPASS";
		Query locQuery = em.createQuery(consulta, Usuario.class);
		locQuery.setParameter("pUSER", usuario);
		locQuery.setParameter("pPASS", hash);
		List<Usuario> listUsuario = locQuery.getResultList();
		for (Usuario usuario2 : listUsuario) {
			user.setEnabled(usuario2.getEnabled());
			user.setFechaRegistro(usuario2.getFechaRegistro());
			user.setNombre(usuario2.getNombre());
			user.setPassword(usuario2.getPassword());
			user.setUsername(usuario2.getUsername());
			user.setId(usuario2.getId());
		}
		return user;
	}

	public Usuario getUsuarioPorId(int idUsuario) {
		inicializar();
		Query locQuery = em.createQuery(
				"SELECT u FROM Usuario u WHERE u.id = :pId", Usuario.class);
		locQuery.setParameter("pId", idUsuario);
		Usuario usuario = new Usuario();
		usuario = (Usuario) locQuery.getSingleResult();
		return usuario;
	}
	
	public List<Role> getListRoles(){
		inicializar();
		Query locQuery = em.createQuery("SELECT r FROM Role r", Role.class);
		List<Role> lista = new ArrayList<Role>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Role> getListRolesSinClientes(){
		inicializar();
		Query locQuery = em.createQuery("SELECT r FROM Role r WHERE r.id <> 2", Role.class);
		List<Role> lista = new ArrayList<Role>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<Vista> getListVistas(){
		inicializar();
		Query locQuery = em.createQuery("SELECT v FROM Vista v", Vista.class);
		List<Vista> lista = new ArrayList<Vista>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public boolean existeRolUsuario(Role role){
		inicializar();
		Query locQuery = em.createQuery("SELECT u FROM Usuario u WHERE u.role = :pRole", Usuario.class);
		locQuery.setParameter("pRole", role);
		List<Usuario> lista = new ArrayList<Usuario>();
		lista = locQuery.getResultList();
		boolean valor = true;
		if(lista.isEmpty()){
			valor = false;
		}
		return valor;
	}
	
	public int updateRol(Role role) {
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE Role r "
					+ "SET r.nombre = :pNombre "
					+ "WHERE r.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pNombre", role.getNombre());
			locQuery.setParameter("pId", role.getId());
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
	
	public int insertRol(Role role) {
		try {
			inicializar();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(role);
			tx.commit();
			cerrarInstancia();
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public int deleteRolVista(Role role) {
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "DELETE FROM RolVista r WHERE r.role = :pRole";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pRole", role);
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
	
	public int deleteRol(Role role) {
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "DELETE FROM Role r WHERE r.id = :pRole";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pRole", role.getId());
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
	
	public int insertRolVista(RolVista rolVista) {
		try {
			inicializar();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(rolVista);
			tx.commit();
			cerrarInstancia();
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public List<RolVista> getListRolVista(Role role){
		inicializar();
		Query locQuery = em.createQuery("SELECT r FROM RolVista r WHERE r.role = :pRole", RolVista.class);
		locQuery.setParameter("pRole", role);
		List<RolVista> lista = new ArrayList<RolVista>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public List<RolVista> getListRolVistaPorId(int id){
		inicializar();
		Role role = new Role();
		role.setId(id);
		Query locQuery = em.createQuery("SELECT r FROM RolVista r WHERE r.role = :pRole", RolVista.class);
		locQuery.setParameter("pRole", role);
		List<RolVista> lista = new ArrayList<RolVista>();
		lista = locQuery.getResultList();
		return lista;
	}
	
	public Usuario getUsuarioPorCliente(Cliente cliente){
		inicializar();
		Query locQuery = em.createQuery("SELECT u FROM Usuario u WHERE u.cliente = :pCliente", Cliente.class);
		locQuery.setParameter("pCliente", cliente);
		Usuario usuario = new Usuario();
		List<Usuario> lista = locQuery.getResultList();
		for (Usuario usuario2 : lista) {
			usuario = usuario2;
		}
		return usuario;
	}

}
