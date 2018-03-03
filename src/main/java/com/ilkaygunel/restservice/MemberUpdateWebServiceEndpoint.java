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
import com.ilkaygunel.pojo.MemberRegisterPojo;
import com.ilkaygunel.service.MemberUpdateService;

@RestController
@RequestMapping("/memberUpdateWebServiceEndpoint")
public class MemberUpdateWebServiceEndpoint {

	@Autowired
	private MemberUpdateService memberUpdateServices;

	@RequestMapping(value = "/updateOneMember", method = RequestMethod.PUT)
	private ResponseEntity<MemberRegisterPojo> updateOneMember(@RequestBody Member member) {
		MemberRegisterPojo memberRegisterPojo = new MemberRegisterPojo();
		memberRegisterPojo.setMember(member);
		try {
			memberUpdateServices.updateOneMember(member);
			memberRegisterPojo.setRegistirationResult("Successfull");
		} catch (Exception ex) {
			memberRegisterPojo.setRegistirationResult("Failed! The problem is:" + ex);
		}
		return new ResponseEntity<MemberRegisterPojo>(memberRegisterPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateBulkMember", method = RequestMethod.PUT)
	private ResponseEntity<MemberRegisterPojo> updateBulkMember(@RequestBody List<Member> memberListForUpdate) {
		MemberRegisterPojo memberRegisterPojo = new MemberRegisterPojo();
		memberRegisterPojo.setMemberList(memberListForUpdate);
		try {
			memberUpdateServices.updateBulkMember(memberListForUpdate);
			memberRegisterPojo.setRegistirationResult("Successfull");
		} catch (Exception e) {
			memberRegisterPojo.setRegistirationResult("Failed! The problem is:" + e);
		}
		return new ResponseEntity<MemberRegisterPojo>(memberRegisterPojo, HttpStatus.OK);
	}

}
