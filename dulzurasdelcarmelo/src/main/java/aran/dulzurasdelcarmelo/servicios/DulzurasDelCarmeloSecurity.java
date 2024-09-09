package aran.dulzurasdelcarmelo.servicios;

import javax.sql.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;

@Configuration
@EnableWebSecurity
public class DulzurasDelCarmeloSecurity {

	// AUTENTICACIÓN
	private DataSource dataSource;

	private PasswordEncoder passwordEncoder;

	public DulzurasDelCarmeloSecurity(DataSource dataSource) {
		this.dataSource = dataSource;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder).usersByUsernameQuery("""
				SELECT email,password
				FROM usuarios
				WHERE email = ?
				""").authoritiesByUsernameQuery("""
				SELECT email, CONCAT('ROLE_',tipo)
				FROM dulzurasdelcarmelo.usuarios
				WHERE email = ?
				""");
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return passwordEncoder;
	}

	// AUTORIZACIÓN
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(x -> x.disable())
				.authorizeHttpRequests(requests -> requests.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/css/**", "/js/**").permitAll().requestMatchers("/**").permitAll()
						.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/usuario/login").permitAll()).logout(LogoutConfigurer::permitAll);

		return http.build();
	}
}
