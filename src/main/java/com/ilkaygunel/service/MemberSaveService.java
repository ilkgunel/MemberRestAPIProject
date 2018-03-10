package com.ilkaygunel.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilkaygunel.constants.ConstantFields;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.logging.LoggingUtil;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.repository.MemberRepository;

@Service
public class MemberSaveService {

	@Autowired
	private MemberRepository memberRepository;

	public MemberOperationPojo addOneUserMember(Member member) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Logger LOGGER = new LoggingUtil().getLoggerForMemberSaving(this.getClass());
		try {
			LOGGER.log(Level.INFO, "One member adding method is running!");
			member.setRole(ConstantFields.ROLE_USER);
			memberRepository.save(member);
			memberOperationPojo.setResult("One user member saving is successfull. Member info is:" + member);
			LOGGER.log(Level.INFO, "One user member saving is successfull. Member info is:" + member);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "An error occured while deleting user member. Error is:" + e.getMessage());
			memberOperationPojo.setResult(e.getMessage());
		}
		return memberOperationPojo;

	}

	public MemberOperationPojo addOneAdminMember(Member member) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Logger LOGGER = new LoggingUtil().getLoggerForMemberSaving(this.getClass());
		try {
			LOGGER.log(Level.INFO, "One member adding method is running!");
			member.setRole(ConstantFields.ROLE_ADMIN);
			memberRepository.save(member);
			memberOperationPojo.setResult("One admin member saving is successfull. Member info is:" + member);
			LOGGER.log(Level.INFO, "One admin member saving is successfull. Member info is:" + member);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "An error occured while saving member. Error is:" + ex.getMessage());
			memberOperationPojo.setResult(ex.getMessage());
		}
		return memberOperationPojo;
	}

	public MemberOperationPojo addBulkUserMember(List<Member> memberList) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Logger LOGGER = new LoggingUtil().getLoggerForMemberSaving(this.getClass());
		try {
			LOGGER.log(Level.INFO, "Bulk member adding method is running!");
			for (Member member : memberList) {
				member.setRole(ConstantFields.ROLE_USER);
			}
			memberRepository.save(memberList);
			memberOperationPojo.setResult(
					"Bulk user member saving is successfull. Informations of user members are:" + memberList);
			LOGGER.log(Level.INFO,
					"Bulk user member saving is successfull. Informations of user members are:" + memberList);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "An error occured while saving member. Error is:" + ex.getMessage());
			memberOperationPojo.setResult(ex.getMessage());
		}
		return memberOperationPojo;
	}

	public MemberOperationPojo addBulkAdminMember(List<Member> memberList) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Logger LOGGER = new LoggingUtil().getLoggerForMemberSaving(this.getClass());
		try {
			LOGGER.log(Level.INFO, "Bulk member adding method is running!");
			for (Member member : memberList) {
				member.setRole(ConstantFields.ROLE_ADMIN);
			}
			memberRepository.save(memberList);
			memberOperationPojo.setResult(
					"Bulk admin member saving is successfull. Informations of admin members are:" + memberList);
			LOGGER.log(Level.INFO,
					"Bulk admin member saving is successfull. Informations of admin members are:" + memberList);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "An error occured while saving member. Error is:" + ex.getMessage());
			memberOperationPojo.setResult(ex.getMessage());
		}
		return memberOperationPojo;
	}
}
