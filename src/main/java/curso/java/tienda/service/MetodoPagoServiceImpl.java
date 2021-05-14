package curso.java.tienda.service;


import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.MetodoPago;
import curso.java.tienda.repository.MetodoPagoRepository;

@Service
public class MetodoPagoServiceImpl implements MetodoPagoService{

	@Autowired
	private MetodoPagoRepository metodoPagoRepository;

	@Override
	public MetodoPago getById(int id) {
		return metodoPagoRepository.findById(id);
	}

	@Override
	public List<MetodoPago> getLista() {
		return metodoPagoRepository.findAll();	
	}
}
	