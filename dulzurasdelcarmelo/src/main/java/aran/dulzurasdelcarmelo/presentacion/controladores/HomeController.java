package aran.dulzurasdelcarmelo.presentacion.controladores;

import java.io.*;
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
	private DetallePedidoService detallePedidoService;
	@Autowired
	private ResenaService resenaService;

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
		modelo.addAttribute("resenas", resenaService.listarResenasPorProducto(productoService.verProductoPorId(id)));

		return "usuario/homeProductos";
	}
	
	@PostMapping("/carrito")
	public String anadirCarrito(@RequestParam Long id, 
	                             @RequestParam Integer cantidad, 
	                             @RequestParam String tipo, 
	                             Model modelo) {
	    if (cantidad <= 0) {
	        // Manejar la cantidad no válida (puedes redirigir con un mensaje de error)
	        modelo.addAttribute("error", "La cantidad debe ser mayor que cero.");
	        return "usuario/carrito"; // Redirigir o mostrar un mensaje de error en la misma vista
	    }

	    Producto producto = productoService.verProductoPorId(id);
	    if (producto == null) {
	        // Manejar el caso en que el producto no existe
	        modelo.addAttribute("error", "Producto no encontrado.");
	        return "usuario/carrito"; // Redirigir o mostrar un mensaje de error
	    }

	    DetallePedido detallePedido = crearDetallePedido(producto, cantidad, tipo);
	    actualizarCarrito(detallePedido, modelo);

	    return "usuario/carrito"; // Redirige a la vista del carrito
	}

	private DetallePedido crearDetallePedido(Producto producto, Integer cantidad, String tipo) {
	    DetallePedido detallePedido = new DetallePedido();
	    double precio = obtenerPrecioPorTipo(producto, tipo);

	    detallePedido.setTipoPresentacion(tipo != null && !tipo.isEmpty() ? tipo : "Precio único");
	    detallePedido.setCantidad(cantidad);
	    detallePedido.setPrecioTotal(precio * cantidad);
	    detallePedido.setProducto(producto);

	    return detallePedido;
	}

	private double obtenerPrecioPorTipo(Producto producto, String tipo) {
	    if ("bandeja".equals(tipo)) {
	        return producto.getPrecioBandeja();
	    } else if ("caja".equals(tipo)) {
	        return producto.getPrecioCaja();
	    }
	    return producto.getPrecioUnidad(); // Precio por unidad por defecto
	}

	private void actualizarCarrito(DetallePedido detallePedido, Model modelo) {
	    Long idProducto = detallePedido.getProducto().getId();
	    DetallePedido introducido = null;

	    // Busca si el producto ya está en el carrito
	    for (DetallePedido dp : detalles) {
	        if (dp.getProducto().getId().equals(idProducto) && 
	            dp.getTipoPresentacion().equals(detallePedido.getTipoPresentacion())) {
	            introducido = dp;
	            break;
	        }
	    }

	    // Si el producto ya está en el carrito, actualiza la cantidad y el precio
	    if (introducido != null) {
	        introducido.setCantidad(introducido.getCantidad() + detallePedido.getCantidad());
	        introducido.setPrecioTotal(introducido.getPrecioTotal() + detallePedido.getPrecioTotal());
	    } else {
	        // Si no está en el carrito, lo añade
	        detalles.add(detallePedido);
	    }

	    // Calcular la suma total del carrito
	    double sumaTotal = detalles.stream().mapToDouble(DetallePedido::getPrecioTotal).sum();
	    
	    // Actualizar el total del pedido
	    pedido.setPrecioTotal(sumaTotal);

	    // Añadir atributos al modelo para que sean accesibles en la vista
	    modelo.addAttribute("carrito", detalles);
	    modelo.addAttribute("pedido", pedido);
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
	@PostMapping("/guardarPedido")
	public String guardarPedido(Model modelo) {
	    // Guardar detalles del pedido
	    for (DetallePedido dt : detalles) {
	        dt.setPedido(pedido);
	        
	        Producto producto = productoService.verProductoPorId(dt.getProducto().getId());
	        
	        // Calcular el precio basado en la presentación seleccionada
	        if ("bandeja".equals(dt.getTipoPresentacion())) {
	            dt.setPrecio(producto.getPrecioBandeja());
	        } else if ("caja".equals(dt.getTipoPresentacion())) {
	            dt.setPrecio(producto.getPrecioCaja());
	        } else {
	            dt.setPrecio(producto.getPrecioUnidad());
	        }
	        
	        dt.setPrecioTotal(dt.getPrecio() * dt.getCantidad());

	        // Guardar el detalle del pedido en la base de datos
	        detallePedidoService.guardarDetallePedido(dt);
	    }

	    // Generar el PDF del pedido
	    generarPdf(pedido, detalles);

	    // Limpiar la lista de detalles y el pedido
	    pedido = new Pedido();
	    detalles.clear();

	    return "redirect:/";
	}
	
	private void generarPdf(Pedido pedido, List<DetallePedido> detalles) {
	    Document document = new Document();
	    try {
	        // Crear una instancia de PdfWriter para escribir en el archivo
	        PdfWriter.getInstance(document, new FileOutputStream("pedido_" + pedido.getId() + ".pdf"));

	        // Abrir el documento para empezar a escribir en él
	        document.open();

	        // Agregar contenido al documento
	        document.add(new Paragraph("Detalles del Pedido"));
	        document.add(new Paragraph("Pedido nº: " + pedido.getId()));
	        document.add(new Paragraph("Nombre del Cliente: " + pedido.getUsuario().getNombre()));
	        document.add(new Paragraph("Email del Cliente: " + pedido.getUsuario().getEmail()));
	        document.add(new Paragraph("Número de teléfono del Cliente: " + pedido.getUsuario().getNumTelefono()));
	        document.add(new Paragraph("Fecha del Pedido: " + pedido.getFechaCreacion()));
	        document.add(new Paragraph("Productos:"));

	        for (DetallePedido detalle : detalles) {
	            document.add(new Paragraph("Producto: " + detalle.getProducto().getNombre() +
	                    "\n Cantidad: " + detalle.getCantidad() +
	                    "\n Precio: " + detalle.getPrecio() +
	                    "\n Suma: " + (detalle.getPrecio() * detalle.getCantidad())));
	        }

	        document.add(new Paragraph("Total: " + pedido.getPrecioTotal()));
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // Asegurarse de cerrar el documento
	        if (document.isOpen()) {
	            document.close();
	        }
	    }
	}
	
//	@GetMapping("/guardarPedido")
//	public String guardarPedido(HttpSession session) {
//		LocalDate fechaCreacion = LocalDate.now();
//		pedido.setFechaCreacion(fechaCreacion);
//		String numPedido = pedidoService.generarNumeroPedido();
//		pedido.setNumero(numPedido);
//		
//
//		// Usuario
//		Usuario usuario = usuarioService.verUsuarioPorId(Long.parseLong(session.getAttribute("idusuario").toString()));
//
//		pedido.setUsuario(usuario);
//		pedidoService.guardarPedido(pedido);
//
//		// Guardar detalles
//		for (DetallePedido dt : detalles) {
//			dt.setPedido(pedido);
//			Producto prod = productoService.verProductoPorId(dt.getProducto().getId());
//			if ("bandeja".equals(dt.getTipoPresentacion())) {
//	            dt.setPrecio(prod.getPrecioBandeja());
//	        } else if ("caja".equals(dt.getTipoPresentacion())) {
//	            dt.setPrecio(prod.getPrecioCaja());
//	        } else {
//	            dt.setPrecio(prod.getPrecioFijo());
//	        }
//			detallePedidoService.guardarDetallePedido(dt);
//			Document document = new Document();
//
//			try {
//				// Crear una instancia de PdfWriter para escribir en el archivo
//				PdfWriter.getInstance(document, new FileOutputStream("pedido_" + pedido.getId() + ".pdf"));
//
//				// Abrir el documento para empezar a escribir en él
//				document.open();
//
//				List<DetallePedido> carrito = detalles;
//
//				// Agregar contenido al documento
//				document.add(new Paragraph("Detalles del Pedido"));
//				document.add(new Paragraph("Pedido nº: " + numPedido));
//				document.add(new Paragraph("Nombre del Cliente: " + usuario.getNombre()));
//				document.add(new Paragraph("Email del Cliente: " + usuario.getEmail()));
//				document.add(new Paragraph("Número de teléfono del Cliente: " + usuario.getNumTelefono()));
//				document.add(new Paragraph("Fecha del Pedido: " + pedido.getFechaCreacion()));
//				document.add(new Paragraph("Productos:"));
//
//				if (carrito != null) {
//					for (DetallePedido producto : carrito) {
//						document.add(new Paragraph("Producto: " + producto.getProducto().getNombre() + "\n Cantidad: "
//								+ producto.getCantidad() + "\n Precio: " + producto.getProducto().getPrecio() + "\n Suma: " + (producto.getProducto().getPrecio()*producto.getCantidad())));
//					}
//					document.add(new Paragraph("Total: " + pedido.getPrecioTotal()));
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				// Asegurarse de cerrar el documento
//				document.close();
//			}
//		}
//
//		// Limpiar lista y pedido
//		pedido = new Pedido();
//		detalles.clear();
//
//		return "redirect:/";
//	}
	
	@GetMapping("/buscar")
    public String buscarProductos(@RequestParam String nombre, Model model) {
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        model.addAttribute("productos", productos);
        return "usuario/homeUsuario";
    }

}
