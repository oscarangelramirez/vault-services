package com.buzone.vault.services.data.user;

import java.util.Date;

import com.buzone.vault.services.domain.user.aggregates.User;
import com.fasterxml.jackson.annotation.JsonView;

public class GetUserResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private String name;
	
	@JsonView
	private String lastName;
	
	@JsonView
	private String email;
	
	@JsonView
	private Date lastChangeDate;
	
	@JsonView
	private Integer numberAttemps;
	
	@JsonView
	private Boolean isLocked;
	
	@JsonView
	private Boolean isActive;
	
	@JsonView
	private String role;
	
	public GetUserResponse(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.lastChangeDate = user.getLastChangeDate();
		this.numberAttemps = user.getNumberAttemps();
		this.isLocked = user.getIsLocked();
		this.isActive = user.getIsActive();
		this.role = user.getRole();
	}
}
