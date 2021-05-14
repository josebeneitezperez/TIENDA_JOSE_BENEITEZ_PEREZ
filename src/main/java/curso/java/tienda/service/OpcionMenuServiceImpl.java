package curso.java.tienda.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.OpcionMenu;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.repository.OpcionMenuRepository;

@Service
public class OpcionMenuServiceImpl {

	@Autowired
	private OpcionMenuRepository opcionMenuRepository;

	public OpcionMenu getById(int id) {
		return opcionMenuRepository.findById(id);
	}

	public List<OpcionMenu> getLista() {
		return opcionMenuRepository.findAll();
	}

	public List<OpcionMenu> getOpcionesMenu(HttpSession session) {

		List<OpcionMenu> listaOpcionMenuCompleta = opcionMenuRepository.findAll();
		int rolUsuario;
		Usuario usuario;
		if((usuario =((Usuario) session.getAttribute("usuario")))==null) {
			rolUsuario = UsuarioServiceImpl.ROL_ANONIMO;
		} else {
			rolUsuario = usuario.getIdRol();
		}
		
		List<OpcionMenu> listaOpcionMenu = new ArrayList<OpcionMenu>();
		
		for (OpcionMenu opcion : listaOpcionMenuCompleta) {
			if(opcion.getIdRol()==rolUsuario) {
				listaOpcionMenu.add(opcion);
			}
		}
		return listaOpcionMenu;
	}
}
