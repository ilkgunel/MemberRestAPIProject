package com.ilkaygunel.restservice;

import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.wrapper.MemberWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.service.MemberSaveService;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/memberPostWebServiceEndPoint")
public class MemberSaveWebServiceEndPoint {
    @Autowired
    private MemberSaveService memberSaveService;

    @PostMapping("/saveUserMember")
    public ResponseEntity<MemberOperationPojo> saveUserMember(@Valid @RequestBody MemberWrapper memberWrapper) throws CustomException, MessagingException {
        MemberOperationPojo memberOperationPojo = memberSaveService.addUserMember(memberWrapper.getMemberList());
        return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
    }

    @RequestMapping(value = "/saveAdminMember", method = RequestMethod.POST)
    public ResponseEntity<MemberOperationPojo> saveAdminMember(@Valid @RequestBody MemberWrapper memberWrapper) throws CustomException, MessagingException {
        MemberOperationPojo memberOperationPojo = memberSaveService.addAdminMember(memberWrapper.getMemberList());
        return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
    }
}
