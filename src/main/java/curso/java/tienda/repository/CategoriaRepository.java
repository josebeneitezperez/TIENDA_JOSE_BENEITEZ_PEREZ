package curso.java.tienda.repository;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	Categoria findById(int id);
	
	List<Categoria> findAll();	
}
