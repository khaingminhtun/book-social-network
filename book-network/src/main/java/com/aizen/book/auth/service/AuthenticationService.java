package com.aizen.book.auth.service;

import com.aizen.book.auth.dto.AuthenticationRequest;
import com.aizen.book.auth.dto.AuthenticationResponse;
import com.aizen.book.auth.dto.RegistrationRequest;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
    void register(RegistrationRequest request) throws MessagingException;

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void activateAccount(String token) throws MessagingException;

}
