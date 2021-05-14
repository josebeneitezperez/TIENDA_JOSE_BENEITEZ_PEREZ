package curso.java.tienda.controller;
 
import java.util.ArrayList; 
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.model.Categoria;
import curso.java.tienda.model.DetallePedido;
import curso.java.tienda.model.MetodoPago;
import curso.java.tienda.model.Pedido;
import curso.java.tienda.model.Producto;
import curso.java.tienda.model.ProductoDetalle;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.service.CategoriaServiceImpl;
import curso.java.tienda.service.DetallePedidoServiceImpl;
import curso.java.tienda.service.ImpuestoServiceImpl;
import curso.java.tienda.service.MetodoPagoServiceImpl;
import curso.java.tienda.service.OpcionMenuServiceImpl;
import curso.java.tienda.service.PedidoServiceImpl;
import curso.java.tienda.service.ProductoServiceImpl;
import curso.java.tienda.service.UsuarioServiceImpl;

@Controller			
@RequestMapping("")		
public class ProductoController {

	@Autowired
	private ProductoServiceImpl productoService;
	
	@Autowired
	private CategoriaServiceImpl categoriaService;
	
	@Autowired
	private MetodoPagoServiceImpl metodoPagoService;
	
	@Autowired
	private PedidoServiceImpl pedidoService;
	
	@Autowired
	private DetallePedidoServiceImpl detallePedidoService;
	
	@Autowired
	private ImpuestoServiceImpl impuestoService;
	
	@Autowired
	private OpcionMenuServiceImpl opcionMenuService;
	
	@GetMapping("")	//vacío entra al index.html
	public String index(@RequestParam(name="busqueda", required=false, defaultValue="") String busqueda, HttpSession session, Model model) {
		
		if((session.getAttribute("rol"))==null) {
			
			session.setAttribute("rol", UsuarioServiceImpl.ROL_ANONIMO);
		}
		
		productoService.getCarritoIniciado(session);
		
		List<Producto> listaProductos = null;
    	if(busqueda.equals("")) {
    		listaProductos = productoService.getLista();
    	} else {
    		//comprobar si es necesario añadir .orElse(null);
    		listaProductos = productoService.getListaBusqueda(busqueda);
    	}
    	
    	model.addAttribute("listaCategorias", categoriaService.getLista());
    	model.addAttribute("listaProductos", listaProductos);
    	session.setAttribute("listaOpcionMenu", opcionMenuService.getOpcionesMenu(session));
		return "index";
	}
	
	/*
	@PostConstruct
	public void iniciarCarrito() {
		productoService.iniciarCarrito(session);
	}
	*/
	
	@GetMapping("/productos/categoria/{id}")
	public String vistaCategoriaConcreta(@PathVariable String id, Model model) {
		
		int idCategoria = Integer.parseInt(id);
		List<Producto> listaProductos = new ArrayList<Producto>();
		
    	for (Producto producto: productoService.getLista()) {
    		if(producto.getIdCategoria()==idCategoria) {
    			listaProductos.add(producto);
    		}
    	}

    	model.addAttribute("listaCategorias", categoriaService.getLista());
    	model.addAttribute("listaProductos", listaProductos);
		return "index";
	}
	
	@GetMapping("/productos/orden")
	public String ordenarVista(@RequestParam String orden, Model model) {

		List<Producto> listaProductos = productoService.getListaOrden(orden);
		
		model.addAttribute("listaCategorias", categoriaService.getLista());
		model.addAttribute("listaProductos", listaProductos);
		return "index";
	}
	
	
	@GetMapping("/productos/gestion")
	public String gestionarProductos(Model model) {
		
		Categoria categoria;
		Map<Producto, String> mapProductoNombreCat = new LinkedHashMap<Producto, String>();
		for(Producto producto : productoService.getLista()) {
			categoria = categoriaService.getById(producto.getIdCategoria());
			mapProductoNombreCat.put(producto, categoria.getNombre());
		}
		model.addAttribute("mapProductoNombreCat", mapProductoNombreCat);
		
		return "producto/gestion";
	}
	
	
	@GetMapping("/productos/alta")
	public String altaProducto(Model model) {
		
		model.addAttribute("producto", new Producto());
		model.addAttribute("listaCategorias", categoriaService.getLista());
		model.addAttribute("listaImpuestos", impuestoService.getLista());
		return "producto/alta";
	}
	
	@GetMapping("/productos/alta/validar")
	public String validarAltaProducto(@ModelAttribute Producto producto, Model model) {
		
		if(productoService.getByNombre(producto.getNombre())!=null) { //ese nombre existe
			
			//confirmacion del usuario	"Ya existe un producto con ese nombre, ¿desea crearlo igualmente?"
		} else {
			productoService.insertar(producto);
		}
		model.addAttribute("mensaje", "Producto insertado");
		return "producto/alta";
	}


	@GetMapping("/productos/actualizar/{id}")
	public String actualizar(@PathVariable String id, Model model) {
		
		Producto producto = productoService.getById(Integer.parseInt(id));
		
		model.addAttribute("producto", producto);
		model.addAttribute("listaCategorias", categoriaService.getLista());
		model.addAttribute("listaImpuestos",  impuestoService.getLista());
		return "producto/actualizar";
	}
	
	@GetMapping("/productos/actualizar/validar")
	public String actualizarConfirmar(@ModelAttribute Producto producto, Model model) {
		
		productoService.actualizar(producto);
		return "redirect:/productos/gestion";
	}
	
	@GetMapping("/productos/detalle/{id}")
	public String verDetalleProducto(@PathVariable String id, Model model) {
		
		Producto producto = productoService.getById(Integer.parseInt(id));
		model.addAttribute("producto", producto);
		return "producto/detalle";
	}
	
	
	@GetMapping("/productos/eliminar/{id}")
	public String eliminar(@PathVariable String id, Model model) {

		productoService.borrar(Integer.parseInt(id));
		return "redirect:/productos/gestion";
	}
	
	
	@GetMapping("/productos/carrito")
	public String verCarrito(HttpSession session, Model model) {
		
		productoService.getCarritoIniciado(session);
		return "producto/carrito"; //"redirect:/new"
	}
	
	@GetMapping("/productos/carrito/agregar/{id}")
	public String agregarAlCarrito(@PathVariable String id, HttpSession session, Model model) {

		List<ProductoDetalle> listaProductoDetalle = productoService.getCarritoIniciado(session);
		Pedido pedido = (Pedido) session.getAttribute("pedido");
		Producto producto = productoService.getById(Integer.parseInt(id));
		
		boolean incluido = false;
		for(int i = 0; i<listaProductoDetalle.size(); i++) {
			if(listaProductoDetalle.get(i).getProducto().getId()==producto.getId()) {	//si el producto ya está en el carro
				
				incluido = true;
				int stockEnTienda = productoService.getStockProductoId(Integer.parseInt(id));
				int unidadesEnCarrito = listaProductoDetalle.get(i).getDetallePedido().getUnidades()+1;
				
				if(stockEnTienda>=unidadesEnCarrito) {	//si hay stock
					
					listaProductoDetalle.get(i).getDetallePedido().setUnidades(unidadesEnCarrito);
				} else {
					model.addAttribute("mensajeError", "El stock del producto es insuficiente");
				}
			}
		}
		
		if(!incluido) {	//añadiendo producto Nuevo
			Float precioFloat  = Float.parseFloat(producto.getPrecio().toString());
			
			//hasta que se realice el pedido (compra realizada): idPedido= 0
			DetallePedido detallePedido = new DetallePedido(pedido.getId(), producto.getId(), precioFloat, 1,  producto.getImpuesto(), producto.getPrecio());	
			listaProductoDetalle.add(new ProductoDetalle(producto, detallePedido));
		}
		double total=0.0;
		for(ProductoDetalle productoDetalle: listaProductoDetalle) {
			total+=productoDetalle.getDetallePedido().getTotal();
			System.out.println("+"+total);
		}
		pedido.setTotal(total);
		return "redirect:/"; //en session tenemos: listaProductoDetalle y pedido
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/productos/carrito/redirigir")
	public String redirigirCompra(HttpSession session, Model model) {
		
		if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals(UsuarioServiceImpl.ROL_ANONIMO)) {
			return "redirect:/usuario/login"; 
		}
		
		if((((List<ProductoDetalle>)session.getAttribute("listaProductoDetalle")) ==null) ||
				((List<ProductoDetalle>)session.getAttribute("listaProductoDetalle")).size()==0	) {
			return "redirect:/";
		}
		
		List<MetodoPago> listaMetodosPago = metodoPagoService.getLista();
		
		model.addAttribute("listaMetodosPago", listaMetodosPago);
		model.addAttribute("metodoPago", new MetodoPago());
		return "producto/pago";
	}
	
	@PostMapping("/productos/carrito/comprar")
	public String comprar(@ModelAttribute MetodoPago metodoPago, HttpSession session) {

		String nombreMetodo = metodoPagoService.getById(metodoPago.getId()).getMetodoPago();
		Pedido pedido = (Pedido)session.getAttribute("pedido");
		
		pedido.setEstado("Pendiente");
		pedido.setMetodoPago(nombreMetodo);
		//numFactura 	no se pone hasta que: 	estado = enviado
		//la fecha la pone la BD
		pedido.setTotal(null);	//"total" no se pone hasta que:		estado = enviado
		int idUsuario = ((Usuario)(session.getAttribute("usuario"))).getId();
		pedido.setIdUsuario(idUsuario);
		
		@SuppressWarnings("unchecked")
		List<ProductoDetalle> listaProductoDetalle = (List<ProductoDetalle>)session.getAttribute("listaProductoDetalle");
		
		int idPedido = pedidoService.insertar(pedido).getId(); //inserto el pedido en la BD, y recupero su "id" generado por la BD
		for(ProductoDetalle productoDetalle : listaProductoDetalle) {	//ahora que conozco el "id" del pedido, se lo asigno a sus "detallePedido"
			
			productoDetalle.getDetallePedido().setIdPedido(idPedido);
			detallePedidoService.insertar(productoDetalle.getDetallePedido());
		}
		return "redirect:/usuario/perfil/pedidos";
	}
}
















