package curso.java.tienda.repository;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import curso.java.tienda.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{
	
	Producto findById(int id);
	
	Producto findByNombre(String nombre);

	List<Producto> findAll();	
	
	//findAllByOrderByNombre
	
	List<Producto> findByNombreContainsIgnoreCase(String nombre);
	List<Producto> findByNombreIgnoreCaseContaining(String nombre);
	
	//List<Producto> findByNombreContainsIgnoreCaseOrDescripcionContainsIgnoreCase(String nombre, String descripcion);
	
	List<Producto> findAllByOrderByPrecioDesc();

	List<Producto> findAllByOrderByPrecioAsc();
}