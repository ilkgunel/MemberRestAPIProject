package com.ilkaygunel.restservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.service.MemberDeleteService;
import com.ilkaygunel.wrapper.MemberIdWrapp;

@RestController
@RequestMapping("/memberDeleteWebServiceEndPoint")
public class MemberDeleteWebServiceEndPoint {

	@Autowired
	private MemberDeleteService memberDeleteService;

	@RequestMapping(value = "/deleteBulkUserMember", method = RequestMethod.DELETE)
	public ResponseEntity<MemberOperationPojo> deleteBulkUserMember(@RequestBody List<MemberIdWrapp> memberIdList) {
		MemberOperationPojo memberOperationPojo = memberDeleteService.deleteUserMember(memberIdList);
		return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteBulkAdminMember", method = RequestMethod.DELETE)
	public ResponseEntity<MemberOperationPojo> deleteBulkAdminMember(@RequestBody List<MemberIdWrapp> memberIdList) {
		MemberOperationPojo memberOperationPojo = memberDeleteService.deleteAdminMember(memberIdList);
		return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
	}
}
