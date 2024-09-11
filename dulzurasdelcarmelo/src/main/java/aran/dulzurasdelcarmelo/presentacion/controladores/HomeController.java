package aran.dulzurasdelcarmelo.presentacion.controladores;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.http.*;

import aran.dulzurasdelcarmelo.entidades.*;
import aran.dulzurasdelcarmelo.servicios.*;

@Controller
@RequestMapping("/")
public class HomeController {

	private final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ProductoService productoService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private DetallePedidoService detallePedidoService;

	// Para almacenar los detalles del pedido
	List<DetallePedido> detalles = new ArrayList<DetallePedido>();

	// Datos del pedido
	Pedido pedido = new Pedido();

	@GetMapping("/")
	public String homeUsuario(Model modelo, HttpSession session) {

		log.info("Sesión del usuario: {}", session.getAttribute("idusuario"));
		
		modelo.addAttribute("productos", productoService.listarProductos());
		
		//Session
		modelo.addAttribute("sesion", session.getAttribute("idusuario"));
		
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
		detallePedido.setPrecioTotal(producto.getPrecio() * cantidad);
		detallePedido.setProducto(producto);

		// Validar que el producto no se añada dos veces
		Long idProducto = producto.getId();
		DetallePedido introducido = null;

		for (DetallePedido dp : detalles) {
			if (dp.getProducto().getId().equals(idProducto)) {
				introducido = dp;
				break;
			}
		}

		if (introducido != null) {
			introducido.setCantidad(introducido.getCantidad() + detallePedido.getCantidad());
			introducido.setPrecioTotal(introducido.getPrecioTotal() + detallePedido.getPrecioTotal());
		} else {
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
	public String obtenerCarrito(Model modelo, HttpSession session) {

		modelo.addAttribute("carrito", detalles);
		modelo.addAttribute("pedido", pedido);
		
		//Sesion
		modelo.addAttribute("sesion", session.getAttribute("idusuario"));

		return "usuario/carrito";
	}

	@GetMapping("/resumenPedido")
	public String resumenPedido(Model modelo, HttpSession session) {

		Usuario usuario = usuarioService.verUsuarioPorId(Long.parseLong(session.getAttribute("idusuario").toString()));

		modelo.addAttribute("carrito", detalles);
		modelo.addAttribute("pedido", pedido);
		modelo.addAttribute("usuario", usuario);

		return "usuario/resumenPedido";
	}

	// Guardar pedido
	@GetMapping("/guardarPedido")
	public String guardarPedido(HttpSession session) {
		LocalDate fechaCreacion = LocalDate.now();
		pedido.setFechaCreacion(fechaCreacion);
		String numPedido = pedidoService.generarNumeroPedido();
		pedido.setNumero(numPedido);
		

		// Usuario
		Usuario usuario = usuarioService.verUsuarioPorId(Long.parseLong(session.getAttribute("idusuario").toString()));

		pedido.setUsuario(usuario);
		pedidoService.guardarPedido(pedido);

		// Guardar detalles
		for (DetallePedido dt : detalles) {
			dt.setPedido(pedido);
			productoService.verProductoPorId(dt.getProducto().getId());
			dt.setPrecio(dt.getProducto().getPrecio());
			detallePedidoService.guardarDetallePedido(dt);
			Document document = new Document();

			try {
				// Crear una instancia de PdfWriter para escribir en el archivo
				PdfWriter.getInstance(document, new FileOutputStream("pedido_" + pedido.getId() + ".pdf"));

				// Abrir el documento para empezar a escribir en él
				document.open();

				List<DetallePedido> carrito = detalles;

				// Agregar contenido al documento
				document.add(new Paragraph("Detalles del Pedido"));
				document.add(new Paragraph("Pedido nº: " + numPedido));
				document.add(new Paragraph("Nombre del Cliente: " + usuario.getNombre()));
				document.add(new Paragraph("Email del Cliente: " + usuario.getEmail()));
				document.add(new Paragraph("Número de teléfono del Cliente: " + usuario.getNumTelefono()));
				document.add(new Paragraph("Fecha del Pedido: " + pedido.getFechaCreacion()));
				document.add(new Paragraph("Productos:"));

				if (carrito != null) {
					for (DetallePedido producto : carrito) {
						document.add(new Paragraph("Producto: " + producto.getProducto().getNombre() + "\n Cantidad: "
								+ producto.getCantidad() + "\n Precio: " + producto.getProducto().getPrecio() + "\n Suma: " + (producto.getProducto().getPrecio()*producto.getCantidad())));
					}
					document.add(new Paragraph("Total: " + pedido.getPrecioTotal()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// Asegurarse de cerrar el documento
				document.close();
			}
		}

		// Limpiar lista y pedido
		pedido = new Pedido();
		detalles.clear();

		return "redirect:/";
	}
	
	@GetMapping("/buscar")
    public String buscarProductos(@RequestParam String nombre, Model model) {
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        model.addAttribute("productos", productos);
        return "usuario/homeUsuario";
    }

}
