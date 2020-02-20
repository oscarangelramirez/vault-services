package com.buzone.vault.services.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buzone.vault.services.application.exceptions.ErrorException;
import com.buzone.vault.services.application.exceptions.InvalidException;
import com.buzone.vault.services.application.exceptions.NotFoundException;
import com.buzone.vault.services.application.user.services.UserService;
import com.buzone.vault.services.data.user.AddUserRequest;
import com.buzone.vault.services.data.user.AddUserResponse;
import com.buzone.vault.services.data.user.ChangePasswordUserRequest;
import com.buzone.vault.services.data.user.ChangePasswordUserResponse;
import com.buzone.vault.services.data.user.GetUserResponse;
import com.buzone.vault.services.data.user.LoginUserRequest;
import com.buzone.vault.services.data.user.LoginUserResponse;
import com.buzone.vault.services.data.user.NewPasswordUserRequest;
import com.buzone.vault.services.data.user.NewPasswordUserResponse;
import com.buzone.vault.services.data.user.RecoveryPasswordUserRequest;
import com.buzone.vault.services.data.user.RecoveryPasswordUserResponse;
import com.buzone.vault.services.data.user.UpdateUserRequest;
import com.buzone.vault.services.data.user.UpdateUserResponse;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("")
    public ResponseEntity<List<GetUserResponse>> findAll() {
		List<GetUserResponse> response = userService.findAll();
		return ResponseEntity.ok(response);
    }
	
	@GetMapping("{id}")
    public ResponseEntity<GetUserResponse> findById(@PathVariable Long id) {
		try {
			GetUserResponse response = userService.findById(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
    }
	
	@PostMapping("")
	public ResponseEntity<AddUserResponse> add(@Valid @RequestBody AddUserRequest request) {
		try {
			AddUserResponse response = userService.add(request);
			return ResponseEntity.ok(response);
		} catch (InvalidException exception) {
			throw exception;
		} catch (ErrorException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UpdateUserResponse> update(@PathVariable Long id,
			@Valid @RequestBody UpdateUserRequest request) {
		try {
			UpdateUserResponse response = userService.update(id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
	
	@PostMapping("changepassword")
	public ResponseEntity<ChangePasswordUserResponse> changePassword(@Valid @RequestBody ChangePasswordUserRequest request) {
		try {
			ChangePasswordUserResponse response = userService.changePassword(request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		} catch (ErrorException exception) {
			throw exception;
		}
	}
	
	@PostMapping("recoverypassword")
	public ResponseEntity<RecoveryPasswordUserResponse> recoveryPassword(@Valid @RequestBody RecoveryPasswordUserRequest request) {
		try {
			RecoveryPasswordUserResponse response = userService.recoveryPassword(request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		} catch (ErrorException exception) {
			throw exception;
		}
	}
	
	@PostMapping("newpassword")
	public ResponseEntity<NewPasswordUserResponse> newPassword(@Valid @RequestBody NewPasswordUserRequest request) {
		try {
			NewPasswordUserResponse response = userService.newPassword(request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		} catch (ErrorException exception) {
			throw exception;
		}
	}
	
	@PostMapping("login")
	public ResponseEntity<LoginUserResponse> login(@Valid @RequestBody LoginUserRequest request) {
		try {
			LoginUserResponse response = userService.login(request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		} catch (ErrorException exception) {
			throw exception;
		}
	}
}
