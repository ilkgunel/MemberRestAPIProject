package com.ilkaygunel.restservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.service.MemberUpdateService;

@RestController
@RequestMapping("/memberUpdateWebServiceEndpoint")
public class MemberUpdateWebServiceEndpoint {

	@Autowired
	private MemberUpdateService memberUpdateServices;

	@RequestMapping(value = "/updateOneUserMember", method = RequestMethod.PUT)
	private ResponseEntity<MemberOperationPojo> updateOneUserMember(@RequestBody Member member) {
		MemberOperationPojo memberOperationPojo = memberUpdateServices.updateOneUserMember(member);
		return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateOneAdminMember", method = RequestMethod.PUT)
	private ResponseEntity<MemberOperationPojo> updateOneAdminMember(@RequestBody Member member) {
		MemberOperationPojo memberOperationPojo = memberUpdateServices.updateOneAdminMember(member);
		return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateBulkUserMember", method = RequestMethod.PUT)
	private ResponseEntity<MemberOperationPojo> updateBulkUserMember(@RequestBody List<Member> memberListForUpdate) {
		MemberOperationPojo memberOperationPojo = memberUpdateServices.updateBulkUserMember(memberListForUpdate);
		return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateBulkAdminMember", method = RequestMethod.PUT)
	private ResponseEntity<MemberOperationPojo> updateBulkAdminMember(@RequestBody List<Member> memberListForUpdate) {
		MemberOperationPojo memberOperationPojo = memberUpdateServices.updateBulkUserMember(memberListForUpdate);
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}

}
