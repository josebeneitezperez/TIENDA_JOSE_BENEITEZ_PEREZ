package curso.java.tienda.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UsuarioBasico {

	private int id;
	
	@Email(message="No es un correo electrónico válido")
	@NotBlank(message="Introduzca un correo electrónico")
	private String email;
	
	@NotBlank(message="Introduzca una contraseña")
	private String clave;

	public UsuarioBasico() {
		super();
	}
	
	public UsuarioBasico(String email, String clave) {
		super();
		this.email = email;
		this.clave = clave;
	}
	
	public UsuarioBasico(int id,
			@Email(message = "No es un correo electrónico válido") @NotBlank(message = "Introduzca un correo electrónico") String email,
			@NotBlank(message = "Introduzca una contraseña") String clave) {
		super();
		this.id = id;
		this.email = email;
		this.clave = clave;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
}
