package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rol_vista database table.
 * 
 */
@Entity
@Table(name="rol_vista")
@NamedQuery(name="RolVista.findAll", query="SELECT r FROM RolVista r")
public class RolVista implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="id_roles")
	private Role role;

	//bi-directional many-to-one association to Vista
	@ManyToOne
	@JoinColumn(name="id_vistas")
	private Vista vista;

	public RolVista() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Vista getVista() {
		return this.vista;
	}

	public void setVista(Vista vista) {
		this.vista = vista;
	}

}