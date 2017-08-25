package DAO.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.CargaVirtual;
import model.Cliente;
import model.ClientePlataforma;
import model.MovimientoVirtual;


public class DAOExcel implements Serializable {

	private static final long serialVersionUID = 1L;	
	
	public DAOExcel() {
		super();
	}
	
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

	
	public List<ClientePlataforma> getListadoCliPla(String plataforma){
		inicializar();
		String consulta = "SELECT c FROM ClientePlataforma c WHERE c.plataforma = :pPlataforma";
		Query locQuery = em.createQuery(consulta, ClientePlataforma.class);
		locQuery.setParameter("pPlataforma" , plataforma);
		List<ClientePlataforma> lista = locQuery.getResultList();
		
		return lista;
	}
	
	public Cliente getClientexUsuarioPlataforma(String usuario, String plataforma){
		inicializar();
		try{
			ClientePlataforma clip = new ClientePlataforma();
			String consulta = "SELECT c FROM ClientePlataforma c WHERE c.usuario = :pUsuario AND c.plataforma = :pPlataforma";
			Query locQuery = em.createQuery(consulta, ClientePlataforma.class);
			locQuery.setParameter("pUsuario" , usuario);
			locQuery.setParameter("pPlataforma" , plataforma);
			clip = (ClientePlataforma) locQuery.getSingleResult();
			cerrarInstancia();
			Cliente cli = new Cliente();
			cli = clip.getCliente();
			return cli;
		} catch (Exception e) {
			cerrarInstancia();
			return null;
		}
		
	}
	
	public List<Cliente> getListaCliPorPlataforma(String plataforma){
		inicializar();

		List<Cliente> clip = new ArrayList<Cliente>();
		String consulta = "SELECT c.cliente FROM ClientePlataforma c WHERE c.plataforma = :pPlataforma";
		Query locQuery = em.createQuery(consulta, Cliente.class);
		locQuery.setParameter("pPlataforma" , plataforma);
		clip = locQuery.getResultList();
		
		return clip;
		
	}
	
	public CargaVirtual getCargaVirtual(String plataforma){
		
		int idPla =  0;
		if (plataforma.equals("Pinweb")){
			idPla=1;
		}else{
			if (plataforma.equals("Re Virtual")){
				idPla = 2;
			}else{
				if (plataforma.equals("Telerecargas")){
					idPla = 3;
				}else{
					if (plataforma.equals("Codigo Pagos")){
						idPla = 4;
					}else{
						if(plataforma.equals("Pinweb Mercader444")){
							idPla = 5;
						}else{
							idPla = 0;
						}						
					}
				}
			}
		}		
		CargaVirtual pla = new CargaVirtual();		
		if (idPla>0 && idPla<=5){		
			inicializar();			
			String consulta = "SELECT c FROM CargaVirtual c WHERE c.id = :pID";
			Query locQuery = em.createQuery(consulta, CargaVirtual.class);
			locQuery.setParameter("pID", idPla);
			pla = (CargaVirtual) locQuery.getSingleResult();
		}else{
			pla = null;
		}
		return pla;
	}

	public void updateCargaVirtual (CargaVirtual cv){
		
		inicializar();
		EntityTransaction tx = em.getTransaction();
		try {
			String consulta = "UPDATE CargaVirtual c SET c.cantidadMonto = :pMonto "
					+ "WHERE c.id = :pId";
			Query locQuery = em.createQuery(consulta);
			locQuery.setParameter("pId", cv.getId());
			locQuery.setParameter("pMonto", cv.getCantidadMonto());
			tx.begin();
			locQuery.executeUpdate();
			tx.commit();
			System.out.println("try update cargavirtual");
//			cerrarInstancia();
		} catch (Exception e) {
			tx.rollback();
			System.out.println("catch update cargavirtual");
		}
	}
	
	public CargaVirtual getCVporId(int id){
		CargaVirtual cv = new CargaVirtual();
		
		
		if (id>0 && id <5){
			
			inicializar();
			
			String consulta = "SELECT c FROM CargaVirtual c WHERE c.id = :pID";
			Query locQuery = em.createQuery(consulta, CargaVirtual.class);
			locQuery.setParameter("pID", id);
			cv = (CargaVirtual) locQuery.getSingleResult();
		}else{
			cv = null;
		}
		
		return cv;
	}
	
	public int insertMovimientoVirtual(MovimientoVirtual mv){
		int retorno;
		inicializar();
		
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(mv);
			tx.commit();
			retorno = mv.getId();
			System.out.println("try insertmovimiento virtual");
//			cerrarInstancia();
		}catch (Exception e){
			System.out.println(e.getMessage());
			tx.rollback();
			System.out.println("catch insermovimientovirtual");
			retorno = 0;
//			cerrarInstancia();
		}
		return retorno;

	}
	
	public List<CargaVirtual> getListaPlataformas(){
		List<CargaVirtual> lista = new ArrayList<CargaVirtual>();
		inicializar();
		String consulta = "SELECT c FROM CargaVirtual c";
		Query locQuery = em.createQuery(consulta, CargaVirtual.class);
		lista = locQuery.getResultList();		
		return lista;
	}
	
	public List<MovimientoVirtual> getVentasVirtualPorCliente(Cliente cli){
		
		List<MovimientoVirtual> mov = new ArrayList<MovimientoVirtual>();
		
		inicializar();

		List<Cliente> clip = new ArrayList<Cliente>();
		String consulta = "SELECT m FROM MovimientoVirtual m WHERE m.cliente = :pCli";
		Query locQuery = em.createQuery(consulta, MovimientoVirtual.class);
		locQuery.setParameter("pCli" , cli);
		mov = locQuery.getResultList();
		return mov;
	}
	
	public List<MovimientoVirtual> getVentasVirtualPorClienteYFecha(Cliente cli, Date fi, Date ff){
		
		List<MovimientoVirtual> mov = new ArrayList<MovimientoVirtual>();
		
		inicializar();

		List<Cliente> clip = new ArrayList<Cliente>();
		String consulta = "SELECT m FROM MovimientoVirtual m WHERE m.cliente = :pCli AND m.fecha <= :pff AND m.fecha >= :pfi";
		Query locQuery = em.createQuery(consulta, MovimientoVirtual.class);
		locQuery.setParameter("pCli" , cli);
		locQuery.setParameter("pff" , ff);
		locQuery.setParameter("pfi" , fi);
		mov = locQuery.getResultList();
		return mov;
	}
	
	public int getUltimoIdMovimiento(){
		inicializar();
		String consulta = "SELECT MAX (m.id) FROM MovimientoVirtual m";
		Query locQuery = em.createQuery(consulta);
		return (Integer) locQuery.getSingleResult();
	}
	
	public List<MovimientoVirtual> getLista(CargaVirtual cargaVirtual) {
		inicializar();
		Query locQuery = em.createQuery("SELECT m FROM MovimientoVirtual m WHERE m.cargaVirtual = :pCargaVirtual", MovimientoVirtual.class);
		locQuery.setParameter("pCargaVirtual", cargaVirtual);
		List<MovimientoVirtual> lista = locQuery.getResultList();
		return lista;
	}
	
}
