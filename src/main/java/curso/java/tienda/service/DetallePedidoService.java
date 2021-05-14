package curso.java.tienda.service;

import java.util.List; 

import curso.java.tienda.model.DetallePedido;

public interface DetallePedidoService {

	public DetallePedido getById(int id);
	public List<DetallePedido> getLista();
	public DetallePedido insertar(DetallePedido detallePedido);
	public void borrar(int id);
	public void actualizar(DetallePedido detallePedido);
}
