package com.project.ecom.kafka;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RoleChangedEvent {

	 private Long userId;
	    private String oldRole;
	    private String newRole;
	    private Instant timestamp;
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public String getOldRole() {
			return oldRole;
		}
		public void setOldRole(String oldRole) {
			this.oldRole = oldRole;
		}
		public String getNewRole() {
			return newRole;
		}
		public void setNewRole(String newRole) {
			this.newRole = newRole;
		}
		public Instant getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(Instant timestamp) {
			this.timestamp = timestamp;
		}
	    
	    
}
