package com.ilkaygunel.restservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.service.MemberGetService;

@RestController
@RequestMapping("/memberGetWebServiceEndPoint")
public class MemberGetWebServiceEndPoint {
	@Autowired
	private MemberGetService memberGetService;

	@RequestMapping(value = "/getAllMembers", method = RequestMethod.GET)
	public List<Member> getAllMemberList() {
		return memberGetService.getAllMemberList();
	}
}
