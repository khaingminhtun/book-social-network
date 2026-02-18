package com.aizen.book.auth.service;

import com.aizen.book.auth.dto.RegistrationRequest;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
    void register(RegistrationRequest request) throws MessagingException;
}
