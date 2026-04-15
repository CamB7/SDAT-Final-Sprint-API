package com.keyin.rest.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private final AuthService authService;
	private final AuthenticationManager authenticationManager; // <-- Added this

	// Inject AuthenticationManager along with AuthService
	public AuthenticationController(AuthService authService, AuthenticationManager authenticationManager) {
		this.authService = authService;
		this.authenticationManager = authenticationManager;
	}

	// A simple container to catch the { "username": "", "password": "" } JSON from React
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
		// 1. Check credentials
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
		);

		// 2. Tell Spring Security this user is authenticated for THIS request
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(auth);
		SecurityContextHolder.setContext(context);

		// 3. Save it to the HttpSession so it remembers you on the NEXT request (AddFlight)
		HttpSession session = request.getSession(true);
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

		return ResponseEntity.ok("Logged in successfully");
	}

	@GetMapping("/adminConfirmation")
	public ResponseEntity<CurrentUserResponse> currentUser(Authentication authentication) {
		return ResponseEntity.ok(authService.currentUser(authentication));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest httpRequest) {
		authService.logout(httpRequest);
		return ResponseEntity.noContent().build();
	}
}