package aran.dulzurasdelcarmelo.servicios;

import java.util.*;

import aran.dulzurasdelcarmelo.entidades.*;

public interface PedidoService {
	
	default List<Pedido> listarPedidos() {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
	default Pedido verPedidoPorId(Long id) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
	default Pedido guardarPedido(Pedido pedido) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
	default String generarNumeroPedido() {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
	default List<Pedido> listarPedidosPorUsuario(Usuario usuario) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}

}
