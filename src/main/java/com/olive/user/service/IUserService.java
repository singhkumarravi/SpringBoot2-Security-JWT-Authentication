package com.olive.user.service;

import com.olive.model.User;

public interface IUserService {
	
public Integer saveUser(User user);
public User findUserByUserName(String username);
}
