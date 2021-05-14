package curso.java.tienda.repository;

import java.util.List;  

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.model.Impuesto;;

public interface ImpuestoRepository extends JpaRepository<Impuesto, Integer>{

	Impuesto findById(int id);
	
	List<Impuesto> findAll();	
}
