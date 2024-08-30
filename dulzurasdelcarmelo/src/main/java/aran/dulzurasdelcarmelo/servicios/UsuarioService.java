package aran.dulzurasdelcarmelo.servicios;

import aran.dulzurasdelcarmelo.entidades.*;

public interface UsuarioService {
	
	default Usuario verUsuarioPorId(Long id) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}

}
