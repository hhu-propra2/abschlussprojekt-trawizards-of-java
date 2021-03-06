package de.trawizardsOfJava.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private SecurityPersonenService userDetailsService;

	@Bean
	public static PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/anmeldung");
		http.formLogin().loginProcessingUrl("/anmeldung");
		http.formLogin().defaultSuccessUrl("/");
		http.formLogin().failureUrl("/anmeldung?error");
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/registrierung").permitAll();
		http.authorizeRequests().antMatchers("/detail/{id}").permitAll();
		http.authorizeRequests().antMatchers("/search").permitAll();
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/zugriffVerweigert");
		http.formLogin().permitAll();
		http.logout().permitAll();
		http.csrf().disable();
		http.authorizeRequests().anyRequest().authenticated();
		http.userDetailsService(this.userDetailsService);
	}
}