package curso.java.tienda.service;
 
import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.Configuracion;
import curso.java.tienda.repository.ConfiguracionRepository;

@Service
public class ConfiguracionServiceImpl{

	@Autowired
	private ConfiguracionRepository configuracionRepository;
	
	public Configuracion getById(int id) {
		return configuracionRepository.findById(id);
	}

	public List<Configuracion> getLista() {
		return configuracionRepository.findAll();
	}

	public void insertar(Configuracion configuracion) {
		configuracionRepository.save(configuracion);
	}

	public void borrar(int id) {
		configuracionRepository.deleteById(id);
	}

	public void actualizar(Configuracion configuracion) {
		configuracionRepository.save(configuracion);
	}
	
	public Configuracion getPorClave(String clave) {
		return configuracionRepository.findByClave(clave);
	}
}
