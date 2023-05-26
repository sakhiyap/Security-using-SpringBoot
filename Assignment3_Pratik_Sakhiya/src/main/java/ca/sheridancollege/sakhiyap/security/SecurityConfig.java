package ca.sheridancollege.sakhiyap.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private LoginAccessDeniedHandler accessDeniedHandler; 
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	public BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		
		//Remove this code before production
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
		http.authorizeHttpRequests((authz) -> authz
				
				.antMatchers(HttpMethod.GET, "/AddTicket").hasRole("VENDOR")
				.antMatchers(HttpMethod.GET, "/ViewData").hasAnyRole("VENDOR","GUEST")
				.antMatchers(HttpMethod.GET, "/edit/**").hasRole("VENDOR")
				.antMatchers(HttpMethod.GET, "/delete/**").hasRole("VENDOR")
				.antMatchers(HttpMethod.POST, "/editTicket").hasRole("VENDOR")
				.antMatchers(HttpMethod.GET, "/procAddTicket").hasRole("VENDOR")
				//.antMatchers(HttpMethod.GET, "/ViewPageUser").hasRole("GUEST")

				// permitAll->Can access without logging in
				.antMatchers(HttpMethod.GET, "/").permitAll()
				.antMatchers(HttpMethod.GET, "/register").permitAll()
				.antMatchers(HttpMethod.POST, "/register").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/css/**", "/images/**").permitAll()
				
				
				.anyRequest().authenticated())
		
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
				
					.logout()
						.invalidateHttpSession(true)
						.clearAuthentication(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/login?logout")
				.permitAll()
				
			.and()
				.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);
		
		return http.build();
	}

	
			
	
	
	@Bean
	public AuthenticationManager authManager(HttpSecurity http)
			throws Exception {
		
		return http.getSharedObject(AuthenticationManagerBuilder.class)
					.userDetailsService(userDetailsService)
					.passwordEncoder(passwordEncoder)
					.and()
					.build();
	}
	
}

