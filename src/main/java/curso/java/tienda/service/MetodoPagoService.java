package curso.java.tienda.service;

import java.util.List;

import curso.java.tienda.model.MetodoPago;

public interface MetodoPagoService {


	public MetodoPago getById(int id);
	public List<MetodoPago> getLista();
}
