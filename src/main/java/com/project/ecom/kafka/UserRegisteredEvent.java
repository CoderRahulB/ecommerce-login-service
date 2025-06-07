package com.project.ecom.kafka;

import java.time.Instant;
import java.util.Set;

import com.project.ecom.model.Role;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserRegisteredEvent {

	 private int userId;
	    private String email;
	    private Role roles; // e.g., ["USER", "SELLER"]
	    private Instant timestamp;
	    
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public Role getRoles() {
			return roles;
		}
		public void setRoles(Role roles) {
			this.roles = roles;
		}
		public Instant getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(Instant timestamp) {
			this.timestamp = timestamp;
		}
	    
	    
}
