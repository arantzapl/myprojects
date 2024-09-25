package aran.dulzurasdelcarmelo.servicios;

import aran.dulzurasdelcarmelo.entidades.*;

public interface ResenaService {
	
	default Resena guardarResena(Resena resena) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
	default Iterable<Resena> listarResenas() {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
	default Iterable<Resena> listarResenasPorProducto(Producto producto) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
}
