package com.aizen.book.auth.service.impl;

import com.aizen.book.auth.dto.AuthenticationRequest;
import com.aizen.book.auth.dto.AuthenticationResponse;
import com.aizen.book.auth.dto.RegistrationRequest;
import com.aizen.book.auth.service.AuthenticationService;
import com.aizen.book.email.EmailService;
import com.aizen.book.email.EmailTemplateName;
import com.aizen.book.role.repository.RoleRepository;
import com.aizen.book.security.JwtService;
import com.aizen.book.user.model.Token;
import com.aizen.book.user.model.User;
import com.aizen.book.user.repository.TokenRepository;
import com.aizen.book.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticatonServiceImpl implements AuthenticationService {

    private final RoleRepository roleRepository;
    private  final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Override
    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        //send email

        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"

        );

        
    }

    private String generateAndSaveActivationToken(User user) {
        // genereate a token
        String generateToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generateToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generateToken;
    }

    private String generateActivationCode(int length){
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for ( int i = 0; i < length; i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();

    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request){

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));
       if(LocalDateTime.now().isAfter(savedToken.getExpiredAt())){
           sendValidationEmail(savedToken.getUser());
           throw  new RuntimeException("Activation token has expired, a new token has been sent to this email");
       }
       var user = userRepository.findById(savedToken.getUser().getId())
               .orElseThrow(() -> new UsernameNotFoundException("User not found"));
       user.setEnabled(true);
       userRepository.save(user);
       savedToken.setValidatedAt(LocalDateTime.now());
       tokenRepository.save(savedToken);

    }


}
