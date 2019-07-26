package com.ilkaygunel.restservice;

import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.pojo.PasswordUpdatePojo;
import com.ilkaygunel.service.PasswordChangeService;
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

    @PostMapping(value = "/admin")
    public ResponseEntity<MemberOperationPojo> changeAdminOrUserPassword(@Valid @RequestBody PasswordUpdatePojo passwordUpdatePojo) throws CustomException {
        MemberOperationPojo memberOperationPojo = passwordChangeService.changeAdminOrUserPassword(passwordUpdatePojo.getEmail(), passwordUpdatePojo.getOldPassword(), passwordUpdatePojo.getNewPassword());
        return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
    }

    @PostMapping(value = "/user")
    public ResponseEntity<MemberOperationPojo> changeUserPassword(@Valid @RequestBody PasswordUpdatePojo passwordUpdatePojo) throws CustomException {
        MemberOperationPojo memberOperationPojo = passwordChangeService.changeUserPassword(passwordUpdatePojo.getEmail(), passwordUpdatePojo.getOldPassword(), passwordUpdatePojo.getNewPassword());
        return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ResponseEntity<MemberOperationPojo> resetPassword(@NotNull @RequestBody String email) throws CustomException, MessagingException {
        passwordChangeService.sendPasswordResetMail(email);
        return new ResponseEntity<>(new MemberOperationPojo(), HttpStatus.OK);
    }
}
