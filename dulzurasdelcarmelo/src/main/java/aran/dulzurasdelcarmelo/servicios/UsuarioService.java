package aran.dulzurasdelcarmelo.servicios;

import java.util.*;

import aran.dulzurasdelcarmelo.entidades.*;

public interface UsuarioService {
	
	default List<Usuario> listarUsuarios() {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
	default Usuario verUsuarioPorId(Long id) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
	default Usuario guardarUsuario(Usuario usuario) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}
	
	default Usuario buscarUsuarioPorEmail(String email) {
		throw new UnsupportedOperationException("NO IMPLEMENTADO");
	}

	

}
