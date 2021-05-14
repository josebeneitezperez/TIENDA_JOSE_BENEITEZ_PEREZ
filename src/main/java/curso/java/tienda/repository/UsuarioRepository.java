package curso.java.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import curso.java.tienda.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	Usuario findById(int id);
	
	Usuario findByNombre(String nombre);
	
	Usuario findByEmail(String email);
	
	List<Usuario> findAll();	
}
