package com.olive.user.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.olive.model.User;
import com.olive.user.repositiory.UserRepositiory;
import com.olive.user.service.IUserService;
@Service
public class UserServiceImpl implements IUserService,UserDetailsService{
	@Autowired
	private UserRepositiory repo;
	@Autowired
	private BCryptPasswordEncoder pwdencode;

	public Integer saveUser(User user) {
		String pwd = user.getPassword();
		user.setPassword(pwdencode.encode(pwd));
		return repo.save(user).getId();
	}


	public  User findUserByUserName(String username) {
		Optional<User> opt = repo.findByUserName(username);
		if(opt.isPresent()) {
		 return opt.get();
		}
		return null;
	}



	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findUserByUserName(username);
		if(user==null)
		{
			throw new UsernameNotFoundException("User Not Exits !!! Examine ");
		}

		
		List<SimpleGrantedAuthority> auth = user.getRoles()
				.stream()
				.map(role->new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());
		return new org.springframework.security.core.userdetails.User(
				user.getUserName(),
				user.getPassword(),
				auth
				);
	}





}
