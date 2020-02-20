package com.buzone.vault.services.application.exceptions;

import java.util.HashMap;
import java.util.Map;

public class CodeExceptions {
	public static final int NOT_FOUND_USER = 1;
	public static final int USER_INACTIVE = 2;
	public static final int USER_BLOCKED = 3;
	public static final int PASSWORD_INVALID = 4;
	public static final int MUST_CHANGE_PASSWORD = 5;
	public static final int NOT_FOUND_PASSWORD_RECOVERY = 6;
	public static final int ERROR = 7;
	
	public static Map<Integer, String> ERRORS = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;
		
		{
			put(NOT_FOUND_USER, "No existe el usuario");
			put(USER_INACTIVE, "Usuario inactivo");
			put(USER_BLOCKED, "Usuario bloqueado");
			put(PASSWORD_INVALID, "Contraseña inválida");
			put(MUST_CHANGE_PASSWORD, "Debe de cambiar la contraseña");
			put(NOT_FOUND_PASSWORD_RECOVERY, "No existe la clave para el cambio de contraseña");
			put(ERROR, "Error");
		}
	};
}
