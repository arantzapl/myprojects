package aran.dulzurasdelcarmelo.servicios;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import jakarta.servlet.http.*;

import aran.dulzurasdelcarmelo.entidades.*;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	HttpSession session;

	private PasswordEncoder passwordEncoder;
	
	@Autowired
    public UserDetailServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder; // Inyecta el bean de BCryptPasswordEncoder
    }
	
	private Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		log.info("Este es el username: ");
		
		Usuario user = usuarioService.buscarUsuarioPorEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		} else {
			log.info("Esto es el ID de usuario: {}", user.getId());
			session.setAttribute("idusuario", user.getId());
			return User.builder()
					.username(user.getEmail())
					.password(bCrypt.encode(user.getPassword()))
					.roles(user.getTipo())
					.build();
		}
		
	}
	
}
