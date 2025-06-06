package com.project.ecom.dto;

import com.project.ecom.model.Role;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RegisterRequest {

	private String username;
    private String password;
    private Role role;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
    
    
}
