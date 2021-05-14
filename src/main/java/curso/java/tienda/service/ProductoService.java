package curso.java.tienda.service;

import java.util.List;

import curso.java.tienda.model.Producto;

public interface ProductoService {

	public Producto getByNombre(String producto);
	public Producto getById(int id);
	public List<Producto> getLista();
	public void insertar(Producto producto);
	public void borrar(int id);
	public void actualizar(Producto producto);
}
