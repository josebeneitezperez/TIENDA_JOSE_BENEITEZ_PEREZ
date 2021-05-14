package curso.java.tienda.repository;

import java.util.List;  

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import curso.java.tienda.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
	
	Pedido findById(int id);
	
	List<Pedido> findAllByidUsuarioOrderByFecha(int id);
	
	List<Pedido> findAll();
	
	List<Pedido> findAllByOrderByFecha();	

	List<Pedido> findAllByEstado(String estado);
}