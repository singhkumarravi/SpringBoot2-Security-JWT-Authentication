package com.olive.user.repositiory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olive.model.User;

public interface UserRepositiory extends JpaRepository<User, Integer>{

   Optional<User> findByUserName(String username);	
}
