package com.olive.user.request;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UserRequest {
	
	private String username;
	private String password;

}
