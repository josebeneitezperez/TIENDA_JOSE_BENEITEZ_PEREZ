package curso.java.tienda.model;

import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="usuarios")
public class Usuario implements java.io.Serializable {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="id_rol", unique = true)
	private int idRol;
	
	@Email(message="No es un correo electrónico válido")
	@NotBlank(message="El correo electrónico es obligatorio")
	private String email;
	
	@NotBlank(message="La contraseña es obligatoria")
	private String clave;
	
	@NotBlank(message="El nombre es obligatorio")
	private String nombre;
	
	@NotBlank(message="El apellido es obligatorio")
	private String apellido1;
	
	private String apellido2;
	
	@NotBlank(message="La dirección es obligatoria")
	private String direccion;
	
	@NotNull(message="Debe seleccionar una provincia")
	private String provincia;
	
	@NotNull(message="Debe seleccionar una localidad")
	private String localidad;
	
	@Size(min=9, max=9, message="El teléfono debe tener 9 dígitos")
	private String telefono;	//¿Error anotaciones no int??
	
	@NotBlank(message="El DNI es obligatorio")
	private String dni;

	public Usuario() {
	}

	public Usuario(int idRol, String email, String clave, String nombre, String apellido1, String apellido2,
			String direccion, String provincia, String localidad, String telefono, String dni) {
		this.idRol = idRol;
		this.email = email;
		this.clave = clave;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.direccion = direccion;
		this.provincia = provincia;
		this.localidad = localidad;
		this.telefono = telefono;
		this.dni = dni;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return this.apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return this.apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
}
