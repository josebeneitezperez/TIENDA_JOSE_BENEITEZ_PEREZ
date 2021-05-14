package curso.java.tienda.service;

import java.util.HashMap;
import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.Usuario;
import curso.java.tienda.repository.UsuarioRepository;
import curso.java.tienda.util.Cifrador;

@Service
public class UsuarioServiceImpl implements UsuarioService { // logica, comprobaciones, etc

	public static final int ROL_ADMIN = 1;
	public static final int ROL_EMPLEADO = 2;
	public static final int ROL_CLIENTE = 3;
	public static final int ROL_ANONIMO = 4;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario getByNombre(String nombre) {
		return usuarioRepository.findByNombre(nombre);
	}

	@Override
	public Usuario getById(int id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public List<Usuario> getLista() {
		return usuarioRepository.findAll();
	}

	@Override
	public void insertar(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	@Override
	public void borrar(int id) {
		usuarioRepository.deleteById(id);
	}

	@Override
	public void actualizar(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	public Usuario getByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	public boolean compararClaves(Usuario usuario, String claveIntroducida) {

		String claveDescifrada = Cifrador.descifrar(usuario.getClave());
		
		if (claveIntroducida.equals(claveDescifrada)) {
			//logger.info("Login correcto");
			return true;
		} else {
			//logger.info("Login incorrecto");
			return false;
		}
	}
}











