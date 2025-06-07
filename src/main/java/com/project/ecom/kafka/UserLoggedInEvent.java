package com.project.ecom.kafka;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserLoggedInEvent {

	private int userId;
	private String tokenId;
	private Instant loginTime;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public Instant getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Instant loginTime) {
		this.loginTime = loginTime;
	}
	

	
}
