package aran.dulzurasdelcarmelo.presentacion.controladores;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.*;

import aran.dulzurasdelcarmelo.entidades.*;
import aran.dulzurasdelcarmelo.servicios.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final Logger log = LoggerFactory.getLogger(UsuarioController.class); 
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/registroUsuario")
	public String registroUsuario() {
		return "usuario/registro";
	}
	
	@PostMapping("/guardarUsuario")
	public String guardarUsuario(Usuario usuario) {
		log.info("Usuario registro: {}", usuario);
		usuario.setTipo("USER");
		String contraEncript = passwordEncoder.encode(usuario.getPassword());
		usuario.setPassword(contraEncript);
		usuarioService.guardarUsuario(usuario);
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "usuario/login";
	}
	
	@GetMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session) {
		log.info("Accesos: {}", usuario);
		Usuario user = usuarioService.verUsuarioPorId(Long.parseLong(session.getAttribute("idusuario").toString()));
		log.info("Usuario de db: {}", user);
		
		if (user != null) {
			session.setAttribute("idusuario", user.getId());
			if (user.getTipo().equals("ADMIN")) {
				return "redirect:/admin";
			} else {
				return "redirect:/";
			}
		} else {
			log.info("Usuario no existe");
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/obtenerCompras")
	public String obtenerCompras(HttpSession session, Model modelo) {
		
		modelo.addAttribute("sesion", session.getAttribute("idusuario"));
		
		Usuario usuario = usuarioService.verUsuarioPorId(Long.parseLong(session.getAttribute("idusuario").toString()));
		List<Pedido> pedidos = pedidoService.listarPedidosPorUsuario(usuario);
		
		modelo.addAttribute("pedidos", pedidos);
		
		return "usuario/compras";
	}
	
	@GetMapping("/detalleCompras/{id}")
	public String detalleCompras(@PathVariable Long id, HttpSession session, Model modelo) {
		
		log.info("Id del pedido: {}", id);
		Pedido pedido = pedidoService.verPedidoPorId(id);
		
		modelo.addAttribute("detalles", pedido.getDetallePedido());
		
		modelo.addAttribute("sesion", session.getAttribute("idusuario"));
		
		return "usuario/detalleCompras";
	}
	
	@GetMapping("/cerrarSesion")
	public String logout(HttpSession session) {
		session.removeAttribute("idusuario");
		return "redirect:/";
	}
}
