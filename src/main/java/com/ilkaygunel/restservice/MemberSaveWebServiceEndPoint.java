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
import com.ilkaygunel.service.MemberSaveService;

@RestController
@RequestMapping("/memberPostWebServiceEndPoint")
public class MemberSaveWebServiceEndPoint {
	@Autowired
	private MemberSaveService memberSaveService;

	@RequestMapping(value = "/saveUserMember", method = RequestMethod.POST)
	public ResponseEntity<MemberOperationPojo> saveUserMember(@RequestBody List<Member> memberList) {
		MemberOperationPojo memberOperationPojo = memberSaveService.addUserMember(memberList);
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveAdminMember", method = RequestMethod.POST)
	public ResponseEntity<MemberOperationPojo> saveAdminMember(@RequestBody List<Member> memberList) {
		MemberOperationPojo memberOperationPojo = memberSaveService.addAdminMember(memberList);
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}
}
