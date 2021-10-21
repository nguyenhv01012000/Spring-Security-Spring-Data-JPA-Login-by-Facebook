package nguyen.security.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nguyen.security.payload.request.LoginRequest;
import nguyen.security.payload.request.SignupRequest;
import nguyen.security.payload.response.JwtResponse;
import nguyen.security.payload.response.MessageResponse;
import nguyen.security.repository.UserRepository;
import nguyen.security.service.Signin;
import nguyen.security.service.Signup;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	Signin signin;

	@Autowired
	Signup signup;

    @PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		signin.processPostLogin(authentication);

		return ResponseEntity.ok(new JwtResponse(signin.getJwt(), 
												 signin.getUserDetails().getId(), 
												 signin.getUserDetails().getUsername(), 
												 signin.getUserDetails().getEmail(), 
												 signin.getRoles()));
	}

    @PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		signup.processPostSignup(signUpRequest);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
