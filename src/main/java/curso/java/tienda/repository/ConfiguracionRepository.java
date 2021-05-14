package curso.java.tienda.repository;

import java.util.List;  

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.model.Configuracion;

public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer>{

	Configuracion findById(int id);
	
	Configuracion findByClave(String clave);
	
	List<Configuracion> findAll();	
}
