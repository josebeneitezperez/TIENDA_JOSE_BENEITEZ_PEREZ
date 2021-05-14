package curso.java.tienda.service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.Categoria;
import curso.java.tienda.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public Categoria getById(int id) {
		return categoriaRepository.findById(id);
	}

	@Override
	public List<Categoria> getLista() {
		return categoriaRepository.findAll();
	}

	@Override
	public void insertar(Categoria categoria) {
		categoriaRepository.save(categoria);
	}

	@Override
	public void borrar(int id) {
		categoriaRepository.deleteById((long) id);
	}

	@Override
	public void actualizar(Categoria categoria) {
		categoriaRepository.save(categoria);
	}
}
