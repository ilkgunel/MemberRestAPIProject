package com.ilkaygunel.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ilkaygunel.entities.Member;

@JsonInclude(Include.NON_NULL)
public class MemberRegisterOrUpdatePojo {
	private Member member;// use for one member save or update
	private List<Member> memberList;// use for bulk member save or update
	private String registirationResult;

	public String getRegistirationResult() {
		return registirationResult;
	}

	public void setRegistirationResult(String registirationResult) {
		this.registirationResult = registirationResult;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}
}
