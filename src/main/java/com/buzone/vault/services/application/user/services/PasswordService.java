package com.buzone.vault.services.application.user.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.buzone.vault.services.application.exceptions.CodeExceptions;
import com.buzone.vault.services.application.exceptions.InvalidException;

@Service
public class PasswordService {
	@Value("${password.length.minimum}")
	private Integer minimumPasswordLength;
	
	@Value("${password.digits.minimum}")
	private Integer minimumPasswordDigits;
	
	@Value("${password.upperCase.minimum}")
	private Integer minimumPasswordUpperCase;
	
	@Value("${password.lowerCase.minimum}")
	private Integer minimumPasswordLowerCase;
	
	@Value("${password.specials.minimum}")
	private Integer minimumPasswordSpecials;
	
	public void validate(String password) throws InvalidException {
		int digits = 0;
		int upperCase = 0;
		int lowerCase = 0;
		int specials = 0;
		String specialCharacters = "/*!@#$%^&*()\"{}_[]|\\?<>,.";

		if (password.length() < minimumPasswordLength) {
			throw new InvalidException(CodeExceptions.PASSWORD_INVALID, "La contraseña no cumple la longitud mínima");
		}

		char element;
		for (int index = 0; index < password.length(); index++) {

			element = password.charAt(index);

			if (Character.isDigit(element)) {
				digits++;
			} else if (Character.isUpperCase(element)) {
				upperCase++;
			} else if (Character.isLowerCase(element)) {
				lowerCase++;
			} else if (specialCharacters.contains(password.substring(index, index + 1))) {
				specials++;
			}
		}

		if (digits < minimumPasswordDigits) {
			throw new InvalidException(CodeExceptions.PASSWORD_INVALID, "La contraseña no cumple con los dígitos mínimos");
		}

		if (upperCase < minimumPasswordUpperCase) {
			throw new InvalidException(CodeExceptions.PASSWORD_INVALID, "La contraseña no cumple con las mayúsculas mínimas");
		}

		if (lowerCase < minimumPasswordLowerCase) {
			throw new InvalidException(CodeExceptions.PASSWORD_INVALID, "La contraseña no cumple con las minúsculas mínimas");
		}

		if (specials < minimumPasswordSpecials) {
			throw new InvalidException(CodeExceptions.PASSWORD_INVALID, "La contraseña no cumple con los caractéres especiales mínimos");
		}
	}
}
