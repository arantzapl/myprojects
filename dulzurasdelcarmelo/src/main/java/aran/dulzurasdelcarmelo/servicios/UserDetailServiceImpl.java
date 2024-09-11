package aran.dulzurasdelcarmelo.servicios;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import jakarta.servlet.http.*;

import aran.dulzurasdelcarmelo.entidades.*;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	HttpSession session;


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario user = usuarioService.buscarUsuarioPorEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		} else {
			session.setAttribute("idusuario", user.getId());
			return User.builder()
					.username(user.getEmail())
					.password(user.getPassword())
					.roles(user.getTipo()).build();
		}
	}

}