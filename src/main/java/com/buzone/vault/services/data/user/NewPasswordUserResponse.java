package com.buzone.vault.services.data.user;

import com.buzone.vault.services.domain.user.aggregates.User;
import com.fasterxml.jackson.annotation.JsonView;

public class NewPasswordUserResponse {
	@JsonView
	private Long id;
	
	public NewPasswordUserResponse(User user) {
		this.id = user.getId();
	}
}
