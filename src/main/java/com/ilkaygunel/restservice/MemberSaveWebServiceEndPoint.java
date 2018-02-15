package com.ilkaygunel.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.pojo.MemberRegisterPojo;
import com.ilkaygunel.service.MemberService;

@RestController
public class MemberSaveWebServiceEndPoint {
	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/saveMember", method = RequestMethod.POST)
	public ResponseEntity<MemberRegisterPojo> saveMember(@RequestBody Member member) {
		MemberRegisterPojo memberRegisterPojo = new MemberRegisterPojo();
		memberRegisterPojo.setMember(member);
		try {
			memberService.addMember(member);
			memberRegisterPojo.setRegistirationResult("Successfull");
		} catch (Exception ex) {
			memberRegisterPojo.setRegistirationResult("Failed! The problem is:" + ex);
		}
		return new ResponseEntity<MemberRegisterPojo>(memberRegisterPojo, HttpStatus.OK);
	}
}
