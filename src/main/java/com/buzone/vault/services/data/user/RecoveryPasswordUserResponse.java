package com.buzone.vault.services.data.user;

import com.buzone.vault.services.domain.user.aggregates.PasswordRecovery;
import com.fasterxml.jackson.annotation.JsonView;

public class RecoveryPasswordUserResponse {
	@JsonView
	private Long id;
	
	public RecoveryPasswordUserResponse(PasswordRecovery passwordRecovery) {
		this.id = passwordRecovery.getId();
	}
}
