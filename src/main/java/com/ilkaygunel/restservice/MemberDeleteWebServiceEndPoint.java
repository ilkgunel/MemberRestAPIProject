package com.ilkaygunel.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.service.MemberDeleteService;

@RestController
@RequestMapping("/memberDeleteWebServiceEndPoint")
public class MemberDeleteWebServiceEndPoint {
	@Autowired
	public MemberDeleteService memberDeleteService;

	@RequestMapping(value = "/deleteOneUserMember/{memberid}", method = RequestMethod.DELETE)
	public ResponseEntity<MemberOperationPojo> deleteOneUserMember(@PathVariable("memberid") long memberId) {
		MemberOperationPojo memberOperationPojo = memberDeleteService.deleteOneUserMember(memberId);
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteOneAdminMember/{memberid}", method = RequestMethod.DELETE)
	public ResponseEntity<MemberOperationPojo> deleteOneAdminMember(@PathVariable("memberid") long memberId) {
		MemberOperationPojo memberOperationPojo = memberDeleteService.deleteOneAdminMember(memberId);
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}
}
