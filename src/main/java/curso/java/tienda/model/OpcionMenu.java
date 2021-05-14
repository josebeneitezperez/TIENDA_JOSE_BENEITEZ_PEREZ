package curso.java.tienda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="opciones_menu")
public class OpcionMenu implements java.io.Serializable {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="id_rol", unique = true)
	private int idRol;
	@Column(name="nombre_opcion", unique = true)
	private String nombreOpcion;
	@Column(name="url_opcion", unique = true)
	private String urlOpcion;

	public OpcionMenu() {
	}

	public OpcionMenu(int idRol, String nombreOpcion, String urlOpcion) {
		this.idRol = idRol;
		this.nombreOpcion = nombreOpcion;
		this.urlOpcion = urlOpcion;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdRol() {
		return this.idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getNombreOpcion() {
		return this.nombreOpcion;
	}

	public void setNombreOpcion(String nombreOpcion) {
		this.nombreOpcion = nombreOpcion;
	}

	public String getUrlOpcion() {
		return this.urlOpcion;
	}

	public void setUrlOpcion(String urlOpcion) {
		this.urlOpcion = urlOpcion;
	}

}
