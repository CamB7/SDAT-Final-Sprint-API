package com.keyin.rest.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private final AuthService authService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationController(AuthService authService, AuthenticationManager authenticationManager) {
		this.authService = authService;
		this.authenticationManager = authenticationManager;
	}

	public static class LoginRequest {
		private String username;
		private String password;

		public String getUsername() { return username; }
		public void setUsername(String username) { this.username = username; }
		public String getPassword() { return password; }
		public void setPassword(String password) { this.password = password; }
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest req, HttpServletRequest request) {
		try {
			AuthRequest ar = new AuthRequest(req.getUsername(), req.getPassword());
			AuthResponse resp = authService.login(ar, request);
			return ResponseEntity.ok(resp);
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid username or password"));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Authentication failed", "detail", ex.getMessage()));
		}
	}

	@GetMapping("/adminConfirmation")
	public ResponseEntity<CurrentUserResponse> currentUser(Authentication authentication) {
		return ResponseEntity.ok(authService.currentUser(authentication));
	}

	@GetMapping("/me")
	public ResponseEntity<CurrentUserResponse> me(Authentication authentication) {
		return ResponseEntity.ok(authService.currentUser(authentication));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest httpRequest) {
		authService.logout(httpRequest);
		return ResponseEntity.noContent().build();
	}
}