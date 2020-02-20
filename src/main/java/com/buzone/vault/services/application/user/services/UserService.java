package com.buzone.vault.services.application.user.services;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.buzone.vault.infrastructure.MailService;
import com.buzone.vault.services.application.exceptions.CodeExceptions;
import com.buzone.vault.services.application.exceptions.ErrorException;
import com.buzone.vault.services.application.exceptions.InvalidException;
import com.buzone.vault.services.application.exceptions.NotFoundException;
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
import com.buzone.vault.services.domain.user.aggregates.PasswordRecovery;
import com.buzone.vault.services.domain.user.aggregates.PasswordRecoveryRepository;
import com.buzone.vault.services.domain.user.aggregates.User;
import com.buzone.vault.services.domain.user.aggregates.UserRepository;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class UserService {
	@Autowired
	private PasswordService passwordService;

	@Autowired
	private MailService mailService;

	@Autowired
	private Configuration freemarkerConfiguration;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordRecoveryRepository passwordRecoveryRepository;

	@Value("${user.password.attemps.maximum}")
	private Integer maximumPasswordAttemps;

	@Value("${user.password.change.maximum}")
	private Integer maximumPasswordChange;

	@Value("${mail.from}")
	private String fromEmail;

	@Value("${mail.from.recovery.subject}")
	private String subjectRecoveryEmail;

	public List<GetUserResponse> findAll() {
		List<User> users = userRepository.findAll();

		return users.stream().sorted(Comparator.comparing(User::getId)).map(user -> new GetUserResponse(user))
				.collect(Collectors.toList());
	}

	public GetUserResponse findById(Long id) throws NotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);

		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.NOT_FOUND_USER,
					CodeExceptions.ERRORS.get(CodeExceptions.NOT_FOUND_USER));
		} else {
			return new GetUserResponse(optionalUser.get());
		}
	}

	public AddUserResponse add(AddUserRequest request) throws InvalidException {
		User user = new User();
		user.setName(request.getName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setNumberAttemps(0);
		user.setLastChangeDate(new Date());
		user.setIsLocked(false);
		user.setIsActive(request.getIsActive());
		user.setRole(request.getRole());

		passwordService.validate(request.getPassword());

		byte[] passwordBytes = getSHA(request.getPassword());
		String password = toHexString(passwordBytes);

		user.setPassword(password);

		userRepository.save(user);

		AddUserResponse response = new AddUserResponse(user);

		return response;
	}

	public UpdateUserResponse update(Long id, UpdateUserRequest request) throws NotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);

		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.NOT_FOUND_USER,
					CodeExceptions.ERRORS.get(CodeExceptions.NOT_FOUND_USER));
		} else {
			User user = optionalUser.get();
			user.setName(request.getName());
			user.setLastName(request.getLastName());
			user.setEmail(request.getEmail());
			user.setIsLocked(request.getIsLocked());
			user.setIsActive(request.getIsActive());
			user.setRole(request.getRole());

			userRepository.save(user);

			UpdateUserResponse response = new UpdateUserResponse(user);

			return response;
		}
	}

	public ChangePasswordUserResponse changePassword(ChangePasswordUserRequest request)
			throws NotFoundException, InvalidException, ErrorException {
		Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.NOT_FOUND_USER,
					CodeExceptions.ERRORS.get(CodeExceptions.NOT_FOUND_USER));
		} else {
			User user = optionalUser.get();

			if (!user.getIsActive()) {
				throw new InvalidException(CodeExceptions.USER_INACTIVE,
						CodeExceptions.ERRORS.get(CodeExceptions.USER_INACTIVE));
			}

			if (user.getIsLocked()) {
				throw new InvalidException(CodeExceptions.USER_BLOCKED,
						CodeExceptions.ERRORS.get(CodeExceptions.USER_BLOCKED));
			}

			byte[] oldPasswordBytes = getSHA(request.getOldPassword());
			String oldPassword = toHexString(oldPasswordBytes);

			if (!oldPassword.equals(user.getPassword())) {
				throw new InvalidException(CodeExceptions.PASSWORD_INVALID,
						CodeExceptions.ERRORS.get(CodeExceptions.PASSWORD_INVALID));
			} else {
				byte[] newPasswordBytes = getSHA(request.getNewPassword());
				String newPassword = toHexString(newPasswordBytes);

				user.setLastChangeDate(new Date());
				user.setPassword(newPassword);

				userRepository.save(user);

				ChangePasswordUserResponse response = new ChangePasswordUserResponse(user);

				return response;
			}
		}
	}

	public RecoveryPasswordUserResponse recoveryPassword(RecoveryPasswordUserRequest request)
			throws NotFoundException, InvalidException, ErrorException {
		Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.NOT_FOUND_USER,
					CodeExceptions.ERRORS.get(CodeExceptions.NOT_FOUND_USER));
		} else {
			User user = optionalUser.get();

			if (!user.getIsActive()) {
				throw new InvalidException(CodeExceptions.USER_INACTIVE,
						CodeExceptions.ERRORS.get(CodeExceptions.USER_INACTIVE));
			}

			if (user.getIsLocked()) {
				throw new InvalidException(CodeExceptions.USER_BLOCKED,
						CodeExceptions.ERRORS.get(CodeExceptions.USER_BLOCKED));
			}

			UUID key = UUID.randomUUID();

			PasswordRecovery passwordRecovery = new PasswordRecovery();
			passwordRecovery.setKey(key.toString());
			passwordRecovery.setUser(user);

			passwordRecoveryRepository.save(passwordRecovery);

			try {
				final Template template = freemarkerConfiguration.getTemplate("email-recovery-password.ftl");

				final Map<String, Object> model = new HashMap<>();
				model.put("key", passwordRecovery.getKey());
				model.put("user", passwordRecovery.getUser().getName());

				String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

				mailService.send(fromEmail, user.getEmail(), subjectRecoveryEmail, text);
			} catch (Exception exception) {
				throw new ErrorException(CodeExceptions.ERROR, CodeExceptions.ERRORS.get(CodeExceptions.ERROR));
			}
			
			RecoveryPasswordUserResponse response = new RecoveryPasswordUserResponse(passwordRecovery);
			
			return response;
		}
	}
	
	public NewPasswordUserResponse newPassword(NewPasswordUserRequest request)
			throws NotFoundException, InvalidException, ErrorException {
		Optional<PasswordRecovery> optionalPasswordRecovery = passwordRecoveryRepository.findByKey(request.getKey());

		if (!optionalPasswordRecovery.isPresent()) {
			throw new NotFoundException(CodeExceptions.NOT_FOUND_PASSWORD_RECOVERY,
					CodeExceptions.ERRORS.get(CodeExceptions.NOT_FOUND_PASSWORD_RECOVERY));
		} else {
			PasswordRecovery passwordRecovery = optionalPasswordRecovery.get();
			User user = passwordRecovery.getUser();

			passwordService.validate(request.getPassword());

			byte[] passwordBytes = getSHA(request.getPassword());
			String password = toHexString(passwordBytes);

			user.setPassword(password);

			userRepository.save(user);
			
			passwordRecoveryRepository.delete(passwordRecovery);

			NewPasswordUserResponse response = new NewPasswordUserResponse(user);

			return response;
		}
	}

	public LoginUserResponse login(LoginUserRequest request)
			throws NotFoundException, InvalidException, ErrorException {
		Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.NOT_FOUND_USER,
					CodeExceptions.ERRORS.get(CodeExceptions.NOT_FOUND_USER));
		} else {
			User user = optionalUser.get();

			if (!user.getIsActive()) {
				throw new InvalidException(CodeExceptions.USER_INACTIVE,
						CodeExceptions.ERRORS.get(CodeExceptions.USER_INACTIVE));
			}

			if (user.getIsLocked()) {
				throw new InvalidException(CodeExceptions.USER_BLOCKED,
						CodeExceptions.ERRORS.get(CodeExceptions.USER_BLOCKED));
			}

			byte[] passwordBytes = getSHA(request.getPassword());
			String password = toHexString(passwordBytes);

			if (!password.equals(user.getPassword())) {
				Integer numberAttemps = user.getNumberAttemps();
				numberAttemps++;

				if (numberAttemps == maximumPasswordAttemps) {
					user.setIsLocked(true);
				}

				user.setNumberAttemps(numberAttemps);

				userRepository.save(user);

				throw new InvalidException(CodeExceptions.PASSWORD_INVALID,
						CodeExceptions.ERRORS.get(CodeExceptions.PASSWORD_INVALID));
			} else {
				Date lastChangeDate = user.getLastChangeDate();
				Date today = new Date();

				long diff = today.getTime() - lastChangeDate.getTime();
				long days = diff / (24 * 60 * 60 * 1000);

				if (days >= maximumPasswordChange) {
					throw new InvalidException(CodeExceptions.MUST_CHANGE_PASSWORD,
							CodeExceptions.ERRORS.get(CodeExceptions.MUST_CHANGE_PASSWORD));
				} else {
					user.setNumberAttemps(0);

					userRepository.save(user);

					LoginUserResponse response = new LoginUserResponse(user);

					return response;
				}
			}
		}
	}

	private byte[] getSHA(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			return md.digest(input.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException exception) {
			throw new ErrorException(CodeExceptions.ERROR, CodeExceptions.ERRORS.get(CodeExceptions.ERROR));
		}
	}

	private String toHexString(byte[] hash) {
		BigInteger number = new BigInteger(1, hash);

		StringBuilder hexString = new StringBuilder(number.toString(16));

		while (hexString.length() < 32) {
			hexString.insert(0, '0');
		}

		return hexString.toString();
	}
}
