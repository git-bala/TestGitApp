package com.rest.api.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j2;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	   private static final String[] AUTH_WHITELIST = {
	            // -- Swagger UI v2
			   "/swagger-resources/*", 
			   "*.html", 
			   "/api/v1/swagger.json"
	    };
	   
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(AUTH_WHITELIST)
	        .hasRole("SWAGGER")
			.anyRequest().authenticated()
			.and().httpBasic()
			.and()
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder =  PasswordEncoderFactories.createDelegatingPasswordEncoder();
		log.info("Password encoded {} ", passwordEncoder.encode("test"));
		
		auth.inMemoryAuthentication()
			.withUser("admin")
			.password(passwordEncoder.encode("admin")).authorities("SWAGGER")
			.roles("USER", "ADMIN")
			.and()
			.withUser("user")
			.password(passwordEncoder.encode("user")).authorities("SWAGGER")
			.roles("USER");
	}
	
}