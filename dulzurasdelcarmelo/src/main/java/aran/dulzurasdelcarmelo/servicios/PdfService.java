package aran.dulzurasdelcarmelo.servicios;

import java.io.*;
import java.util.List;

import org.springframework.stereotype.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import aran.dulzurasdelcarmelo.entidades.*;

@Service
public class PdfService {

	public void generarPdfPedido(Usuario usuario, Pedido pedido, List<DetallePedido> detalles)
			throws FileNotFoundException {
		// Crear un documento de iText 5.x
		Document document = new Document();

		try {
			// Crear una instancia de PdfWriter para escribir en el archivo
			PdfWriter.getInstance(document, new FileOutputStream("pedido_" + pedido.getId() + ".pdf"));

			// Abrir el documento para empezar a escribir en él
			document.open();

			List<DetallePedido> carrito = detalles;

			// Agregar contenido al documento
			document.add(new Paragraph("Detalles del Pedido"));
			document.add(new Paragraph("ID del Pedido: " + pedido.getId()));
			document.add(new Paragraph("Nombre del Cliente: " + usuario.getNombre()));
			document.add(new Paragraph("Email del Cliente: " + usuario.getEmail()));
			document.add(new Paragraph("Número de teléfono del Cliente: " + usuario.getNumTelefono()));
			document.add(new Paragraph("Fecha del Pedido: " + pedido.getFechaCreacion()));
			document.add(new Paragraph("Productos:"));

			if (carrito != null) {
				for (DetallePedido producto : carrito) {
					document.add(new Paragraph("Producto: " + producto.getProducto().getNombre() + ", Cantidad: "
							+ producto.getCantidad() + ", Precio: " + producto.getProducto().getPrecio()));
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
}
