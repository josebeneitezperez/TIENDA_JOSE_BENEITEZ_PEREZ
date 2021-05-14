package curso.java.tienda.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.Pedido;
import curso.java.tienda.model.Producto;
import curso.java.tienda.model.ProductoDetalle;
import curso.java.tienda.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	private ProductoRepository productoRepository;

	@Override
	public Producto getByNombre(String nombre) {
		return productoRepository.findByNombre(nombre);
	}

	@Override
	public Producto getById(int id) {
		return productoRepository.findById(id);
	}
	
	@Override
	public List<Producto> getLista() {
		return productoRepository.findAll();
	}

	@Override
	public void insertar(Producto producto) {
		productoRepository.save(producto);
	}

	@Override
	public void borrar(int id) {
		productoRepository.deleteById(id);
	}

	@Override
	public void actualizar(Producto producto) {
		productoRepository.save(producto);
	}
	
	public List<Producto> getListaBusqueda (String busqueda) {
		return productoRepository.findByNombreContainsIgnoreCase(busqueda);
	}
	
	public List<Producto> getListaOrdenPrecioDesc () { 
		return productoRepository.findAllByOrderByPrecioDesc();
	}
	
	public List<Producto> getListaOrdenPrecioAsc () { 
		return productoRepository.findAllByOrderByPrecioAsc();
	}

	public List<Producto> getListaOrden(String orden) {
		
		List<Producto> listaProductos = null;
		switch (orden) {
		
			case "Seleccione uno":{
				
				break;
			}
			case "Precio descendente": {
				listaProductos = getListaOrdenPrecioDesc();
				break;
			}
			case "Precio ascendente": {
				listaProductos = getListaOrdenPrecioAsc();
				break;
			}
			case "Mejor valorados": {

				break;
			}
		}
		return listaProductos;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductoDetalle> getCarritoIniciado(HttpSession session) {
		
		if(session.getAttribute("listaProductoDetalle")==null) {
			session.setAttribute("listaProductoDetalle", new ArrayList<ProductoDetalle>());
			Pedido pedido = new Pedido();
			pedido.setEstado("pendiente");
			session.setAttribute("pedido", pedido);
		}
		
		return (List<ProductoDetalle>) session.getAttribute("listaProductoDetalle");
	}
	
	public int getStockProductoId(int id) {
		
		Producto producto;
		if((producto = getById(id))!=null) {
			return producto.getStock();
		} else {
			return 0;
		}
	}
	
}









