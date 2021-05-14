package curso.java.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.DetallePedido;
import curso.java.tienda.repository.DetallePedidoRepository;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService{

	@Autowired
	private DetallePedidoRepository detallePedidoRepository;
	
	@Override
	public DetallePedido getById(int id) {
		return detallePedidoRepository.findById(id);
	}

	@Override
	public List<DetallePedido> getLista() {
		return detallePedidoRepository.findAll();
	}

	@Override
	public DetallePedido insertar(DetallePedido detallePedido) {
		return detallePedidoRepository.save(detallePedido);
	}

	@Override
	public void borrar(int id) {
		detallePedidoRepository.deleteById(id);
	}

	@Override
	public void actualizar(DetallePedido detallePedido) {
		detallePedidoRepository.save(detallePedido);
	}

	public List<DetallePedido> getListaIdPedido(int id) {
		return detallePedidoRepository.findAllByIdPedido(id);
	}
}