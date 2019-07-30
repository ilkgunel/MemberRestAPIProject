package com.ilkaygunel.service;

import com.ilkaygunel.application.ResourceBundleMessageManager;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.entities.PasswordResetToken;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.ErrorCodes;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.repository.MemberRepository;
import com.ilkaygunel.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private ResourceBundleMessageManager resourceBundleMessageManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void savePasswordResetToken(String token, String email) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setPasswordResetToken(token);
        passwordResetToken.setEmail(email);
        passwordResetToken.setUsed(false);

        LocalDateTime passwordResetTokenExpDate = LocalDateTime.now().plusDays(1);
        passwordResetToken.setExpireDate(passwordResetTokenExpDate);

        passwordResetTokenRepository.save(passwordResetToken);
    }

    public MemberOperationPojo checkToken(String token, String locale) throws CustomException {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByPasswordResetToken(token);
        if (null == passwordResetToken) {
            throw new CustomException(ErrorCodes.ERROR_18.getErrorCode(), resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_18.getErrorCode(), locale), HttpStatus.NOT_FOUND);
        } else if (LocalDateTime.now().isAfter(passwordResetToken.getExpireDate())) {
            throw new CustomException(ErrorCodes.ERROR_19.getErrorCode(), resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_19.getErrorCode(), locale), HttpStatus.FORBIDDEN);
        } else if (passwordResetToken.getUsed()) {
            throw new CustomException(ErrorCodes.ERROR_20.getErrorCode(), resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_20.getErrorCode(), locale), HttpStatus.FORBIDDEN);
        }
        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
        memberOperationPojo.setResult("Password Reset Token Is Valid!");
        return memberOperationPojo;
    }

    public MemberOperationPojo resetPassword(String newPassword, String locale, String token) throws CustomException {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByPasswordResetToken(token);
        Member member = memberRepository.findByEmail(passwordResetToken.getEmail()).get();
        member.setPassword(bCryptPasswordEncoder.encode(newPassword));
        memberRepository.save(member);

        passwordResetToken.setUsed(true);
        passwordResetTokenRepository.save(passwordResetToken);

        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
        memberOperationPojo.setResult(resourceBundleMessageManager.getValueOfProperty("resetPassword.successfull", locale));
        return memberOperationPojo;
    }

    public boolean checkNewAndRepeatedPassword(String newPassword, String repeatedNewPassword, String locale) throws CustomException {
        if (!ObjectUtils.nullSafeEquals(newPassword, repeatedNewPassword)) {
            throw new CustomException(ErrorCodes.ERROR_21.getErrorCode(), resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_21.getErrorCode(), locale), HttpStatus.FORBIDDEN);
        }
        return true;
    }

}
