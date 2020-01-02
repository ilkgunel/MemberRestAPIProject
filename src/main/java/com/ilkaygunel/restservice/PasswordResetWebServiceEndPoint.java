package com.ilkaygunel.restservice;

import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.pojo.CheckResetPasswordToken;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.pojo.PasswordResetPojo;
import com.ilkaygunel.pojo.PasswordResetTokenPojo;
import com.ilkaygunel.service.PasswordChangeService;
import com.ilkaygunel.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/resetPassword")
public class PasswordResetWebServiceEndPoint {

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private PasswordChangeService passwordChangeService;

    @RequestMapping(value = "/resetToken", method = RequestMethod.POST)
    public ResponseEntity<MemberOperationPojo> sendResetPasswordToken(@Valid @RequestBody PasswordResetTokenPojo passwordResetTokenPojo) throws CustomException, MessagingException {
        MemberOperationPojo memberOperationPojo = passwordChangeService.sendPasswordResetMail(passwordResetTokenPojo.getEmail(),passwordResetTokenPojo.getLocale());
        return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
    }

    @RequestMapping(value = "/checkToken", method = RequestMethod.POST)
    public ResponseEntity<MemberOperationPojo> checkResetPasswordToken(@Valid @RequestBody CheckResetPasswordToken checkResetPasswordToken) throws CustomException {
        MemberOperationPojo memberOperationPojo = passwordResetService.checkToken(checkResetPasswordToken.getToken(), checkResetPasswordToken.getLocale());
        return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ResponseEntity<MemberOperationPojo> resetPassword(@Valid @RequestBody PasswordResetPojo passwordResetPojo) throws CustomException {
        MemberOperationPojo memberOperationPojo = null;

        //In front-end project, token is validated but at calling rest api, need to be check again!
        passwordResetService.checkToken(passwordResetPojo.getToken(),passwordResetPojo.getLocale());

        if (passwordResetService.checkNewAndRepeatedPassword(passwordResetPojo.getNewPassword(), passwordResetPojo.getRepeatedNewPassword(), passwordResetPojo.getLocale())) {
            memberOperationPojo = passwordResetService.resetPassword(passwordResetPojo.getNewPassword(), passwordResetPojo.getLocale(), passwordResetPojo.getToken());
        }
        return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
    }
}
