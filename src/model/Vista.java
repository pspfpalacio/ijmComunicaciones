package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the vistas database table.
 * 
 */
@Entity
@Table(name="vistas")
@NamedQuery(name="Vista.findAll", query="SELECT v FROM Vista v")
public class Vista implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nombre;

	//bi-directional many-to-one association to RolVista
	@OneToMany(mappedBy="vista")
	private List<RolVista> rolVistas;

	public Vista() {
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
		rolVista.setVista(this);

		return rolVista;
	}

	public RolVista removeRolVista(RolVista rolVista) {
		getRolVistas().remove(rolVista);
		rolVista.setVista(null);

		return rolVista;
	}

}