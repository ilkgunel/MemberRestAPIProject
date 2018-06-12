package com.member.restservice;

import com.member.wrapper.MemberWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.member.pojo.MemberOperationPojo;
import com.member.service.MemberSaveService;

import com.member.exception.ErrorCodes;

import javax.validation.Valid;

@RestController
@RequestMapping("/memberPostWebServiceEndPoint")
public class MemberSaveWebServiceEndPoint {
    @Autowired
    private MemberSaveService memberSaveService;

    @PostMapping("/saveUserMember")
    public ResponseEntity<MemberOperationPojo> saveUserMember(@Valid @RequestBody MemberWrapper memberWrapper, Errors errors) {
        MemberOperationPojo memberOperationPojo;
        if (errors.hasErrors()) {
            memberOperationPojo = new MemberOperationPojo();
            memberOperationPojo.setErrorCode(ErrorCodes.ERROR_10.getErrorCode());
            memberOperationPojo.setResult(errors.getFieldError().getDefaultMessage());
            return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.BAD_REQUEST);
        }
        memberOperationPojo = memberSaveService.addUserMember(memberWrapper.getMemberList());
        return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
    }

    @RequestMapping(value = "/saveAdminMember", method = RequestMethod.POST)
    public ResponseEntity<MemberOperationPojo> saveAdminMember(@Valid @RequestBody MemberWrapper memberWrapper, Errors errors) {
        MemberOperationPojo memberOperationPojo;
        if (errors.hasErrors()) {
            memberOperationPojo = new MemberOperationPojo();
            memberOperationPojo.setErrorCode(ErrorCodes.ERROR_10.getErrorCode());
            memberOperationPojo.setResult(errors.getFieldError().getDefaultMessage());
            return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.BAD_REQUEST);
        }
        memberOperationPojo = memberSaveService.addAdminMember(memberWrapper.getMemberList());
        return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
    }
}
