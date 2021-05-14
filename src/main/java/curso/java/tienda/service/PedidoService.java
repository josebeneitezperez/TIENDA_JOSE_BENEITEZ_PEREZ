package curso.java.tienda.service;

import java.util.List;

import curso.java.tienda.model.Pedido;

public interface PedidoService {

	public Pedido getById(int id);
	public List<Pedido> getLista();
	public Pedido insertar(Pedido pedido);
	public void borrar(int id);
	public void actualizar(Pedido pedido);
}
