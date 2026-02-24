package com.aizen.book.api.auth.service;

import com.aizen.book.api.auth.dto.AuthenticationRequest;
import com.aizen.book.api.auth.dto.AuthenticationResponse;
import com.aizen.book.api.auth.dto.RegistrationRequest;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
    void register(RegistrationRequest request) throws MessagingException;

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void activateAccount(String token) throws MessagingException;

}
