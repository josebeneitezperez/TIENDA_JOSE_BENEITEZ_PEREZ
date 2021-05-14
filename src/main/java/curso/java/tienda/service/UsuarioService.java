package curso.java.tienda.service;

import java.util.List;

import curso.java.tienda.model.Usuario;

public interface UsuarioService {

	public Usuario getByNombre(String nombre);
	public Usuario getById(int id);
	public List<Usuario> getLista();
	public void insertar(Usuario usuario);
	public void borrar(int id);
	public void actualizar(Usuario usuario);
}
