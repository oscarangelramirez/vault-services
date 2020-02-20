package com.buzone.vault.services.data.user;

import com.buzone.vault.services.domain.user.aggregates.User;
import com.fasterxml.jackson.annotation.JsonView;

public class AddUserResponse {
	@JsonView
	private Long id;
	
	public AddUserResponse(User user) {
		this.id = user.getId();
	}
}
