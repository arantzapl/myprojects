package aran.dulzurasdelcarmelo.servicios;

import aran.dulzurasdelcarmelo.entidades.*;

public interface DetallePedidoService {

	default DetallePedido guardarDetallePedido(DetallePedido detallePedido) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
}
