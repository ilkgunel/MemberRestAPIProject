package com.ilkaygunel.restservice;

import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.pojo.CheckResetPasswordToken;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.pojo.PasswordResetPojo;
import com.ilkaygunel.pojo.PasswordUpdatePojo;
import com.ilkaygunel.service.PasswordChangeService;
import com.ilkaygunel.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/changePassword")
public class PasswordChangeWebServiceEndPoint {

    @Autowired
    private PasswordChangeService passwordChangeService;

    @RequestMapping(value = "/change")
    public ResponseEntity<MemberOperationPojo> changePassword(@Valid @RequestBody PasswordUpdatePojo passwordUpdatePojo) throws CustomException {
        MemberOperationPojo memberOperationPojo = passwordChangeService.changePassword(passwordUpdatePojo.getEmail(), passwordUpdatePojo.getOldPassword(), passwordUpdatePojo.getNewPassword());
        return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
    }

}
