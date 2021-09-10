package com.olive.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.olive.filter.SecurityFilter;
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private UserDetailsService detailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private SecurityFilter filter;
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
    	
    	return super.authenticationManager();
    }
    
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(detailsService)
		.passwordEncoder(passwordEncoder);
	}

	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/user/save","/user/login").permitAll()
		.anyRequest().authenticated()
		
		//no form login /no logout
		
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(authenticationEntryPoint)
		
		//no Http session
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
		//link security filter
		.and()
	    .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}

}
