package com.ilkaygunel.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ilkaygunel.entities.Member;

@JsonInclude(Include.NON_NULL)
public class MemberOperationPojo {
	private Member member;// use for one member save or update
	private List<Member> memberList;// use for bulk member save or update
	private String result;
	private String errorCode;// using for only exceptions

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
