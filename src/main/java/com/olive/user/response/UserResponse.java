package com.olive.user.response;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class UserResponse {
	private String token;
	private String notes;
	

}
