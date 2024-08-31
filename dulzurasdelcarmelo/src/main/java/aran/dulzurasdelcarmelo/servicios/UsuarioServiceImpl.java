package aran.dulzurasdelcarmelo.servicios;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import aran.dulzurasdelcarmelo.entidades.*;
import aran.dulzurasdelcarmelo.repositorios.*;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Usuario verUsuarioPorId(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	

}
