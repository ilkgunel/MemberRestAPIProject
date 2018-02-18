package com.ilkaygunel.pojo;

import java.util.List;

import com.ilkaygunel.entities.Member;

public class MemberRegisterPojo {
	private Member member;// use for one member save
	private List<Member> memberList;
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
