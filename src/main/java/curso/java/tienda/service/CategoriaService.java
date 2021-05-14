package curso.java.tienda.service;

import java.util.List;

import curso.java.tienda.model.Categoria;

public interface CategoriaService {
	
	public Categoria getById(int id);
	public List<Categoria> getLista();
	public void insertar(Categoria categoria);
	public void borrar(int id);
	public void actualizar(Categoria categoria);
}
