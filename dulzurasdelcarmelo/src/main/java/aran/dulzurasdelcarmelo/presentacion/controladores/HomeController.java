package aran.dulzurasdelcarmelo.presentacion.controladores;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import aran.dulzurasdelcarmelo.entidades.*;
import aran.dulzurasdelcarmelo.servicios.*;

@Controller
@RequestMapping("/")
public class HomeController {

	private final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ProductoService productoService;

	// Para almacenar los detalles del pedido
	List<DetallePedido> detalles = new ArrayList<DetallePedido>();

	// Datos del pedido
	Pedido pedido = new Pedido();

	@GetMapping("")
	public String homeUsuario(Model modelo) {

		modelo.addAttribute("productos", productoService.listarProductos());
		return "usuario/homeUsuario";
	}

	@GetMapping("/homeProductos/{id}")
	public String homeProductos(@PathVariable Long id, Model modelo) {

		log.info("Id enviado como parámetro: {}", id);

		modelo.addAttribute("producto", productoService.verProductoPorId(id));

		return "usuario/homeProductos";
	}

	@PostMapping("/carrito")
	public String anadirCarrito(@RequestParam Long id, @RequestParam Integer cantidad, Model modelo) {

		DetallePedido detallePedido = new DetallePedido();
		Producto producto = new Producto();
		double sumaTotal = 0;

		producto = productoService.verProductoPorId(id);
		log.info("Producto añadido: {}", producto);
		log.info("Cantidad: {}", cantidad);

		detallePedido.setCantidad(cantidad);
		detallePedido.setPrecio(producto.getPrecio());
		detallePedido.setNombre(producto.getNombre());
		detallePedido.setPrecioTotal(producto.getPrecio() * cantidad);
		detallePedido.setProducto(producto);
		
		//Validar que el producto no se añada dos veces
		Long idProducto = producto.getId();		
		boolean introducido = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
		
		if(!introducido) {
			detalles.add(detallePedido);
		}		

		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getPrecioTotal()).sum();

		pedido.setPrecioTotal(sumaTotal);

		modelo.addAttribute("carrito", detalles);
		modelo.addAttribute("pedido", pedido);

		return "usuario/carrito";
	}

	// Eliminar un producto del carrito
	@GetMapping("/borrar/carrito/{id}")
	public String eliminarProductoCarrito(@PathVariable Long id, Model modelo) {

		// Lista nueva de productos
		List<DetallePedido> pedidoNuevo = new ArrayList<DetallePedido>();

		for (DetallePedido detallePedido : detalles) {
			if (detallePedido.getProducto().getId() != id) {
				pedidoNuevo.add(detallePedido);
			}
		}

		// Nueva lista con los productos restantes
		detalles = pedidoNuevo;

		double sumaTotal = 0;

		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getPrecioTotal()).sum();

		pedido.setPrecioTotal(sumaTotal);

		modelo.addAttribute("carrito", detalles);
		modelo.addAttribute("pedido", pedido);

		return "usuario/carrito";
	}
	
	@GetMapping("/obtenerCarrito")
	public String obtenerCarrito(Model modelo) {
		
		modelo.addAttribute("carrito", detalles);
		modelo.addAttribute("pedido", pedido);

		
		return "usuario/carrito";
	}

}
