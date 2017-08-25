package model;

import java.io.Serializable;

import javax.persistence.*;

import DAO.usuario.DAOusuario;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the roles database table.
 * 
 */
@Entity
@Table(name="roles")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nombre;

	//bi-directional many-to-one association to RolVista
	@OneToMany(mappedBy="role")
	private List<RolVista> rolVistas;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="role")
	private List<Usuario> usuarios;

	public Role() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<RolVista> getRolVistas() {
		return this.rolVistas;
	}

	public void setRolVistas(List<RolVista> rolVistas) {
		this.rolVistas = rolVistas;
	}

	public RolVista addRolVista(RolVista rolVista) {
		getRolVistas().add(rolVista);
		rolVista.setRole(this);

		return rolVista;
	}

	public RolVista removeRolVista(RolVista rolVista) {
		getRolVistas().remove(rolVista);
		rolVista.setRole(null);

		return rolVista;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setRole(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setRole(null);

		return usuario;
	}
	
	public boolean getEstado(){
		boolean value = false;
		if(id == 1){
			value = true;
		}
		if(id == 2){
			value = true;
		}
		return value;
	}
	
	public List<RolVista> getVistasDeRol(){
		DAOusuario DAOUser = new DAOusuario();
		List<RolVista> lista = new ArrayList<RolVista>();
		lista = DAOUser.getListRolVistaPorId(id);
		return lista;
	}

}