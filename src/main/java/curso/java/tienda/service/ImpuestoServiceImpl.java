package curso.java.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.Impuesto;
import curso.java.tienda.repository.ImpuestoRepository;


@Service
public class ImpuestoServiceImpl implements ImpuestoService{

	@Autowired
	private ImpuestoRepository impuestoRepository;

	@Override
	public Impuesto getById(int id) {
		return impuestoRepository.findById(id);
	}

	@Override
	public List<Impuesto> getLista() {
		return impuestoRepository.findAll();
	}

	@Override
	public void insertar(Impuesto impuesto) {
		impuestoRepository.save(impuesto);
	}

	@Override
	public void borrar(int id) {
		impuestoRepository.deleteById(id);
	}

	@Override
	public void actualizar(Impuesto impuesto) {
		impuestoRepository.save(impuesto);
	}
	
	
	
}
