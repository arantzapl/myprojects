package aran.dulzurasdelcarmelo.servicios;

import aran.dulzurasdelcarmelo.entidades.*;

public interface ProductoService {
	
	default Producto guardarProducto(Producto producto) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
	default Iterable<Producto> listarProductos() {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
	default Producto verProductoPorId(Long id) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
	default void modificarProducto(Producto productoActualizado) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
	default void borrarProducto(Long id) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
}
