package aran.dulzurasdelcarmelo.servicios;

import java.util.*;

import org.springframework.data.repository.query.*;

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
	
	default List<Producto> buscarPorNombre(@Param("nombre") String nombre) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
}
