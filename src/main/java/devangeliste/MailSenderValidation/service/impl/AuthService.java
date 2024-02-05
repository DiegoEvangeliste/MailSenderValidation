package devangeliste.MailSenderValidation.service.impl;

import devangeliste.MailSenderValidation.model.auth.AuthResponse;
import devangeliste.MailSenderValidation.model.auth.LoginRequest;
import devangeliste.MailSenderValidation.model.auth.RegisterRequest;
import devangeliste.MailSenderValidation.model.entity.UserEntity;
import devangeliste.MailSenderValidation.model.enums.Role;
import devangeliste.MailSenderValidation.repository.UserRepository;
import devangeliste.MailSenderValidation.service.IEmailService;
import devangeliste.MailSenderValidation.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IEmailService emailService;

    public AuthResponse submitRegistrationRequest(RegisterRequest request) {
        String token = jwtService.getToken(request.getUsername());

        emailService.sendSimpleEmail(
                new String[]{request.getUsername()},
                "Verificacion de usuario para registro",
                token);

        return AuthResponse.builder()
                .token(token)
                .registerRequest(request)
                .build();
    }

    public String validateUserRegistration(AuthResponse authResponse, String token) {
        if (authResponse.getToken().equals(token)){
            RegisterRequest request = authResponse.getRegisterRequest();
            UserEntity user = UserEntity.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .country(request.getCountry())
                    .role(Role.USER)
                    .build();

            userRepository.save(user);

            return jwtService.getToken(user.getUsername());
        }
        return "NO SE PUDO VERIFICAR CON EXITO EL REGISTRO DEL USUARIO";
    }

    public String login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        return jwtService.getToken(request.getUsername());
    }
}
