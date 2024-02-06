package devangeliste.MailSenderValidation.controller;

import devangeliste.MailSenderValidation.model.auth.AuthResponse;
import devangeliste.MailSenderValidation.model.auth.LoginRequest;
import devangeliste.MailSenderValidation.model.auth.RegisterRequest;
import devangeliste.MailSenderValidation.service.impl.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Submit User Registration Request", description = "Submit a user's registration request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The registration request was successfully submitted.")})
    public ResponseEntity<AuthResponse> submitRegistrationRequest(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.submitRegistrationRequest(request));
    }

    @PostMapping("/validateRegister")
    @Operation(summary = "Validate User Registration Request", description = "Validate a user's registration request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A user's registration request was successfully validated.")})
    public ResponseEntity<String> validateUserRegistration(@RequestBody AuthResponse authResponse, @RequestParam String token) {
            return ResponseEntity.ok(authService.validateUserRegistration(authResponse, token));
    }

    @PostMapping("/login")
    @Operation(summary = "User's login", description = "User's login.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully.")})
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
