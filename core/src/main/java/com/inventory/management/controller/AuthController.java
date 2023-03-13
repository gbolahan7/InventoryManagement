package com.inventory.management.controller;


import com.inventory.management.annotation.ResponseWrapper;
import com.inventory.management.service.AuthenticationService;
import com.inventory.management.vo.dto.CredentialDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@ResponseWrapper
public class AuthController {
	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	@ResponseWrapper(message = "user.login.success")
	public ResponseEntity<CredentialDto> loginUser(@RequestBody CredentialDto credentials) {
		return ResponseEntity.ok().body(authenticationService.loginUser(credentials));
	}

	@ResponseWrapper(message = "user.registration.success")
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser( @RequestBody CredentialDto credentials) {
		return ResponseEntity.ok().body(authenticationService.registerUser(credentials));
	}
}
