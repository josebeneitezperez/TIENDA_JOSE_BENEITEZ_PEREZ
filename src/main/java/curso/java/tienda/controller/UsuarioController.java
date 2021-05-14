package curso.java.tienda.controller;

import java.util.ArrayList; 
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import curso.java.tienda.model.Configuracion;
import curso.java.tienda.model.DetallePedido;
import curso.java.tienda.model.Pedido;
import curso.java.tienda.model.Producto;
import curso.java.tienda.model.ProductoDetalle;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.model.UsuarioBasico;
import curso.java.tienda.service.ConfiguracionServiceImpl;
import curso.java.tienda.service.DetallePedidoServiceImpl;
import curso.java.tienda.service.OpcionMenuServiceImpl;
import curso.java.tienda.service.PedidoServiceImpl;
import curso.java.tienda.service.ProductoServiceImpl;
import curso.java.tienda.service.UsuarioServiceImpl;
import curso.java.tienda.util.Cifrador;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private PedidoServiceImpl pedidoService;
	
	@Autowired
	private DetallePedidoServiceImpl detallepedidoService;

	@Autowired
	private ProductoServiceImpl productoService;

	@Autowired
	private OpcionMenuServiceImpl opcionMenuService;
	
	@Autowired
	private ConfiguracionServiceImpl configuracionService;
	
	
	@PostConstruct // cargar al inicio
	public void prepararCifrador() {
		Cifrador.fijarPatronCifrado();
	}
	
	@GetMapping("/login")
	public String login(Model model, HttpSession session) {
		
		if (session.getAttribute("rol")==null || ((int)session.getAttribute("rol"))==UsuarioServiceImpl.ROL_ANONIMO) {

			model.addAttribute("usuarioBasico", new UsuarioBasico());
			model.addAttribute("usuario", new Usuario());
			return "usuario/registro";
		}
		return "usuario/perfil";
	}

	@PostMapping("/login/validar")
	public String validarLogin(@Valid @ModelAttribute("usuarioBasico") UsuarioBasico usuarioBasico,
			BindingResult bindingResult, HttpSession session, Model model) {
		if (bindingResult.hasErrors()) {
			// el atributo del error se mantiene, pero este hay que añadirlo de nuevo
			model.addAttribute("usuario", new Usuario());
			return "usuario/registro";
		}

		String email = usuarioBasico.getEmail();
		String clave = usuarioBasico.getClave();
		Usuario usuarioBD;
		if ((usuarioBD = usuarioService.getByEmail(email)) != null) { // el usuario existe

			if (usuarioService.compararClaves(usuarioBD, clave)) {
				
				session.setAttribute("usuario", usuarioBD);
				session.setAttribute("rol", usuarioBD.getIdRol());
				session.setAttribute("listaOpcionMenu", opcionMenuService.getOpcionesMenu(session));
				
				return "redirect:/";
			}
		}
		model.addAttribute("mensajeError", "El usuario o la contraseña no son correctos");
		model.addAttribute("usuario", new Usuario()); // el atributo del error se mantiene, pero este otro no
		return "usuario/registro";
	}

	@PostMapping("/registro/validar")
	public String validarRegistro(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			// el atributo del error se mantiene, pero este hay que añadirlo de nuevo
			model.addAttribute("usuarioBasico", new UsuarioBasico());
			return "usuario/registro";
		}

		if ((usuarioService.getByEmail(usuario.getEmail())) == null) {

			usuario.setIdRol(UsuarioServiceImpl.ROL_CLIENTE);
			usuario.setClave(Cifrador.cifrar(usuario.getClave()));
			usuarioService.insertar(usuario);
			return "redirect:/";
		}

		model.addAttribute("mensajeError", "Ya existe un usuario con el correo electrónico " + usuario.getEmail());
		model.addAttribute("usuarioBasico", new UsuarioBasico());// el atributo del error se mantiene, pero este otro no
		return "usuario/registro";
	}
	
	
	
	
	
	
	@GetMapping("/actualizar/{id}")
	public String actualizar(@PathVariable String id, Model model) {
		
		Usuario usuario;
		if((usuario=usuarioService.getById(Integer.parseInt(id)))!=null) {	
			
			model.addAttribute("usuarioBasico", new UsuarioBasico(usuario.getId(), usuario.getEmail(), usuario.getClave()));
			model.addAttribute("usuario", usuario);
			return "usuario/actualizar";
		}
		return "redirect:/";
	}

	@PostMapping("/actualizar/validar")
	public String actualizarConfirmar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, HttpSession session, Model model) {
		
		if (bindingResult.hasErrors()) {
			
			// el atributo del error se mantiene, pero este hay que añadirlo de nuevo
			model.addAttribute("usuarioBasico", new UsuarioBasico());
			return "usuario/actualizar";
		}
		
		usuario.setClave(Cifrador.cifrar(usuario.getClave()));
		
		usuarioService.actualizar(usuario);
		
		int rolUsuarioSession = ((Usuario)session.getAttribute("usuario")).getIdRol();
		if(rolUsuarioSession==UsuarioServiceImpl.ROL_CLIENTE) {	//redirect distintos según quien haga la actualización
			
			return "redirect:/usuario/login";
		} else if(rolUsuarioSession==UsuarioServiceImpl.ROL_ADMIN) {
			
			if(usuario.getIdRol()==UsuarioServiceImpl.ROL_EMPLEADO) {
				return "redirect:/usuario/gestion/empleados";
			}
		}
		
		return "redirect:/usuario/gestion"; 
	}
	
	@PostMapping("/actualizar/usuarioClave/validar")
	public String actualizarConfirmarUsuarioClave(@Valid @ModelAttribute("usuarioBasico") UsuarioBasico usuarioBasico,
			BindingResult bindingResult, HttpSession session) {
		
		if((usuarioService.getById(usuarioBasico.getId()))==null) {
			return "redirect:/";
		}
		
		int rolUsuarioSession = ((Usuario)session.getAttribute("usuario")).getIdRol();
		if(rolUsuarioSession==UsuarioServiceImpl.ROL_EMPLEADO ||
				rolUsuarioSession==UsuarioServiceImpl.ROL_ADMIN) {	//si quien actualiza es Empleado o Admin
			return "redirect:/usuario/gestion";
		} 
		return "redirect:/usuario/login";
	}
	
	@GetMapping("/gestion")
	public String gestionarClientes(Model model) {
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		for(Usuario usuario : usuarioService.getLista()) {
			
			if(usuario.getIdRol()==UsuarioServiceImpl.ROL_CLIENTE) {
				listaUsuarios.add(usuario);
			}
		}
		model.addAttribute("listaUsuarios", listaUsuarios);
		
		return "usuario/gestion";
	}
	
	// (solo puede gestionarlos el Admin)
	@GetMapping("/gestion/empleados")
	public String gestionarEmpleados(Model model) {
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		for(Usuario usuario : usuarioService.getLista()) {
			
			if(usuario.getIdRol()==UsuarioServiceImpl.ROL_EMPLEADO) {
				listaUsuarios.add(usuario);
			}
		}
		model.addAttribute("listaUsuarios", listaUsuarios);
		
		return "usuario/gestion";
	}
	
	@GetMapping("/eliminar/{id}")
	public String gestionarClientes(@PathVariable String id, Model model, HttpSession session) {
		
		Usuario usuario = usuarioService.getById(Integer.parseInt(id));
		
		if(usuario.getIdRol()==UsuarioServiceImpl.ROL_CLIENTE) {	//si es un cliente, lo borra
			
			usuarioService.borrar(Integer.parseInt(id));
		} else if (usuario.getIdRol()==UsuarioServiceImpl.ROL_EMPLEADO) {	//a un empleado solo puede borrarlo un Admin
		
			int rolUsuarioSession = ((Usuario)session.getAttribute("usuario")).getIdRol();
			if(rolUsuarioSession==UsuarioServiceImpl.ROL_ADMIN) {
				
				usuarioService.borrar(Integer.parseInt(id));
				return "redirect:/usuario/gestion/empleados";
			}
		}
		return "redirect:/usuario/gestion";
	}
	
	@GetMapping("/perfil/pedidos")
	public String verPedidos(Model model, HttpSession session) {

		if (session.getAttribute("rol")==null || ((int)session.getAttribute("rol"))==UsuarioServiceImpl.ROL_ANONIMO) {
			return "redirect:/usuario/login";
		}
		
		int idUsuario = ((Usuario) session.getAttribute("usuario")).getId();
		
		
		List<Pedido> listaPedidos = pedidoService.getListaIdUsuario(idUsuario);
		
		model.addAttribute("listaPedidos", listaPedidos);
		return "usuario/pedidos";
	}

	@GetMapping("/perfil/pedido/{id}")
	public String verPedido(@PathVariable String id, Model model, HttpSession session) {

		Pedido pedido = pedidoService.getById(Integer.parseInt(id));
		
		List<DetallePedido> listaDetallePedido = detallepedidoService.getListaIdPedido(Integer.parseInt(id));
		
		//objeto producto + el detalle asociado al mismo
		List<ProductoDetalle> listaProductoDetalle = new ArrayList<ProductoDetalle>();
		
		for(DetallePedido detallePedido : listaDetallePedido) {
			
			Producto producto = productoService.getById(detallePedido.getIdProducto());
			listaProductoDetalle.add(new ProductoDetalle(producto, detallePedido));
		}
		
		model.addAttribute("listaProductoDetalle", listaProductoDetalle);
		model.addAttribute("pedido", pedido);
		return "usuario/pedido";
	}

	@GetMapping("/perfil/pedido/solicitarCancelacion/{id}")
	public String solicitarCancelacionPedido(@PathVariable String id, Model model, HttpSession session) {

		Pedido pedido = null;
		if((pedido = pedidoService.getById(Integer.parseInt(id)))!=null) {	//si existe en la BD
			
			pedido.setEstado(PedidoServiceImpl.SOLICITANDO_CANCELACION);
			pedidoService.actualizar(pedido);
		}
		return "redirect:/usuario/perfil/pedidos";
	}
	
	@GetMapping("/gestion/pedidos")
	public String gestionarPedidos(Model model, HttpSession session) {

		List<Pedido> listaPedidos = pedidoService.getLista();
		
		model.addAttribute("listaPedidos", listaPedidos);
		return "pedidos/gestion";
	}
	
	@GetMapping("/pedidos/gestion/enviado/{id}")
	public String cambiarEstadoEnviado(@PathVariable String id, Model model, HttpSession session) {

		Pedido pedido = null;
		if((pedido = pedidoService.getById(Integer.parseInt(id)))!=null) {	//si existe en la BD
			pedido.setEstado(PedidoServiceImpl.ENVIADO);
			
			Configuracion conf = configuracionService.getPorClave("contador_factura");
			pedido.setNumFactura(conf.getValor());
			
			int numFacturaSiguiente = Integer.parseInt(conf.getValor())+1;
			conf.setValor(Integer.toString(numFacturaSiguiente));
			
			pedidoService.actualizar(pedido);
			configuracionService.actualizar(conf);
		}
		return "redirect:/usuario/gestion/pedidos";
	}
	
	@GetMapping("/pedidos/gestion/cancelado/{id}")
	public String cambiarEstadoCancelado(@PathVariable String id, Model model, HttpSession session) {

		Pedido pedido = null;
		if((pedido = pedidoService.getById(Integer.parseInt(id)))!=null) {
			pedido.setEstado(PedidoServiceImpl.CANCELADO);
			pedidoService.actualizar(pedido);
		}
		return "redirect:/usuario/gestion/pedidos";
	}
	
	@GetMapping("/salir")
	public String cerrarSesion(HttpSession session) {

		session.setAttribute("carrito", null); //por si acaso
		session.invalidate();
		return "redirect:/usuario/login";
	}
}














