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

	@RequestMapping(value = "/updateOneMember", method = RequestMethod.PUT)
	private ResponseEntity<MemberOperationPojo> updateOneMember(@RequestBody Member member) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		memberOperationPojo.setMember(member);
		try {
			memberUpdateServices.updateOneMember(member);
			memberOperationPojo.setResult("Successfull");
		} catch (Exception ex) {
			memberOperationPojo.setResult("Failed! The problem is:" + ex);
		}
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateBulkMember", method = RequestMethod.PUT)
	private ResponseEntity<MemberOperationPojo> updateBulkMember(@RequestBody List<Member> memberListForUpdate) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		memberOperationPojo.setMemberList(memberListForUpdate);
		try {
			memberUpdateServices.updateBulkMember(memberListForUpdate);
			memberOperationPojo.setResult("Successfull");
		} catch (Exception e) {
			memberOperationPojo.setResult("Failed! The problem is:" + e);
		}
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}

}
