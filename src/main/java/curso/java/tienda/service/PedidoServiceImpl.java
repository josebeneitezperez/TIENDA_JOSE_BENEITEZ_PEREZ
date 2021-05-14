package curso.java.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.Pedido;
import curso.java.tienda.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService{

	public static final String PEPENDIENTE = "Pendiente";
	public static final String ENVIADO = "Enviado";
	public static final String CANCELADO = "Cancelado";
	public static final String SOLICITANDO_CANCELACION = "Solicitando cancelación";
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Override
	public Pedido getById(int id) {
		return pedidoRepository.findById(id);
	}

	@Override
	public List<Pedido> getLista() {
		return pedidoRepository.findAllByOrderByFecha();
	}

	@Override
	public Pedido insertar(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

	@Override
	public void borrar(int id) {
		pedidoRepository.deleteById(id);
	}

	@Override
	public void actualizar(Pedido pedido) {
		pedidoRepository.save(pedido);
	}
	
	public List<Pedido> getListaIdUsuario(int idUsuario) {
		return pedidoRepository.findAllByidUsuarioOrderByFecha(idUsuario);
	}
	
	public List<Pedido> getListaEstado(String estado) {
		return pedidoRepository.findAllByEstado(estado);
	}
}









