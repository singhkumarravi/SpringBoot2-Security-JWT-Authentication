package com.olive.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.olive.utils.JwtUtils;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
	private JwtUtils jwt;
	
    @Autowired
    private UserDetailsService detailsService;
    
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {

		       String token = request.getHeader("Authorization");
		       if(token!=null) {
		    	   //read username from token
		    	   String username = jwt.getUsername(token);
		    	   //username exit but user did not login before
		    	   if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
		    		   //load spring security user object from db
		            	UserDetails user=detailsService.loadUserByUsername(username);
		    	      //create Authentication impl obj
		            	UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(
		            			user.getUsername(),
		            			user.getPassword(),
		            			user.getAuthorities()
		            			);
		            	//link with request
		            	authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		            	//link Authentication into Security Context
		            	SecurityContextHolder.getContext().setAuthentication(authToken);
		    	   }
		       }
		       filterChain.doFilter(request, response);
	}

}
