package curso.java.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.model.MetodoPago;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer>{

	MetodoPago findById(int id);
	
	List<MetodoPago> findAll();	
}
