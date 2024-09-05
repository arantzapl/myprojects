package aran.dulzurasdelcarmelo.presentacion.controladores;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import aran.dulzurasdelcarmelo.entidades.*;
import aran.dulzurasdelcarmelo.servicios.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ProductoService productoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PedidoService pedidoService;

	@GetMapping("")
	public String homeAdmin(Model modelo) {
		modelo.addAttribute("productos", productoService.listarProductos());
		return "administrador/homeAdmin";
	}

	@GetMapping("/verUsuarios")
	public String verUsuarios(Model modelo) {

		modelo.addAttribute("usuarios", usuarioService.listarUsuarios());

		return "administrador/verUsuarios";
	}

	@GetMapping("/verPedidos")
	public String verPedidos(Model modelo) {

		modelo.addAttribute("pedidos", pedidoService.listarPedidos());

		return "administrador/verPedidos";
	}

	@GetMapping("/verDetallePedidos/{id}")
	public String verDetallePedidos(@PathVariable Long id, Model modelo) {

		Pedido pedido = pedidoService.verPedidoPorId(id);

		modelo.addAttribute("detalles", pedido.getDetallePedido());

		return "administrador/verDetallePedidos";
	}
}
