package curso.java.tienda.service;

import java.util.List; 

import curso.java.tienda.model.Impuesto;;

public interface ImpuestoService {
	
	public Impuesto getById(int id);
	public List<Impuesto> getLista();
	public void insertar(Impuesto impuesto);
	public void borrar(int id);
	public void actualizar(Impuesto impuesto);
}
