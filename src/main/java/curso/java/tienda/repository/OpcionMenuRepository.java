package curso.java.tienda.repository;

import java.util.List;   

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.model.OpcionMenu;

public interface OpcionMenuRepository extends JpaRepository<OpcionMenu, Integer>{

	OpcionMenu findById(int id);
	
	List<OpcionMenu> findAll();	
}
