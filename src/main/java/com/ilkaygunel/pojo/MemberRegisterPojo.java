package com.ilkaygunel.pojo;

import com.ilkaygunel.entities.Member;

public class MemberRegisterPojo {
	private Member member;
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
}
