package com.ilkaygunel.service;

import com.ilkaygunel.entities.PasswordResetToken;
import com.ilkaygunel.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public void savePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setPasswordResetToken(token);

        LocalDateTime passwordResetTokenExpDate = LocalDateTime.now().plusDays(1);
        passwordResetToken.setExpireDate(passwordResetTokenExpDate);

        passwordResetTokenRepository.save(passwordResetToken);
    }
}
