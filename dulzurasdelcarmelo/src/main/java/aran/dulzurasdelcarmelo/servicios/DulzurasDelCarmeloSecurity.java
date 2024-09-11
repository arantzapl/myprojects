package aran.dulzurasdelcarmelo.servicios;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.web.*;

@Configuration
@EnableWebSecurity
public class DulzurasDelCarmeloSecurity {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(requests -> requests.requestMatchers("/administrador/**", "/admin/**").hasRole("ADMIN")
				.requestMatchers("/productos/**").hasRole("ADMIN")
				.requestMatchers("/usuario/registroUsuario", "/usuario/guardarUsuario").permitAll()
				.requestMatchers("/css/**", "/vendor/**", "/js/**", "/").permitAll().anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/usuario/login").permitAll().defaultSuccessUrl("/usuario/acceder",
						true));

		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
}
