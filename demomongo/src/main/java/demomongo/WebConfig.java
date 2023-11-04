package demomongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers("/").permitAll();
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();

//		httpSecurity.cors().and() // ajout de la configuration CORS
//				.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll() // autoriser
//																												// les
//																												// requêtes
//																												// OPTIONS
//																												// pour
//																												// /api/**
//				.anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/home")
//				.permitAll().and().logout().permitAll();
	}

//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		final CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("*")); // autoriser toutes les origines
//		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // autoriser les
//																									// méthodes HTTP
//		configuration.setAllowCredentials(false); // autoriser l'envoi des cookies
//		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // autoriser
//																											// les
//																											// headers
//
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
}
