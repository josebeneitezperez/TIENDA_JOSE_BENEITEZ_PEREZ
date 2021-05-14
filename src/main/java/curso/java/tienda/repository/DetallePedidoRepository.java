package curso.java.tienda.repository;

import java.util.List;  

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import curso.java.tienda.model.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer>{
	
	DetallePedido findById(int id);
	
	List<DetallePedido> findAllByIdPedido(int id);
	
	List<DetallePedido> findAll();
}