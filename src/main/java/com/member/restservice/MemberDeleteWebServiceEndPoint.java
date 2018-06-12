package com.member.restservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.member.pojo.MemberOperationPojo;
import com.member.service.MemberDeleteService;
import com.member.wrapper.MemberIdWrapp;

@RestController
@RequestMapping("/memberDeleteWebServiceEndPoint")
public class MemberDeleteWebServiceEndPoint {

	@Autowired
	private MemberDeleteService memberDeleteService;

	@RequestMapping(value = "/deleteUserMember", method = RequestMethod.DELETE)
	public ResponseEntity<MemberOperationPojo> deleteUserMember(@RequestBody List<MemberIdWrapp> memberIdList) {
		MemberOperationPojo memberOperationPojo = memberDeleteService.deleteUserMember(memberIdList);
		return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteAdminMember", method = RequestMethod.DELETE)
	public ResponseEntity<MemberOperationPojo> deleteAdminMember(@RequestBody List<MemberIdWrapp> memberIdList) {
		MemberOperationPojo memberOperationPojo = memberDeleteService.deleteAdminMember(memberIdList);
		return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
	}
}
