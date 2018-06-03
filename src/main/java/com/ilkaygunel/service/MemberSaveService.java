package com.ilkaygunel.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ilkaygunel.constants.ConstantFields;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.entities.MemberRoles;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.ErrorCodes;
import com.ilkaygunel.pojo.MemberOperationPojo;

@Service
public class MemberSaveService extends BaseService {

	public MemberOperationPojo addUserMember(List<Member> memberList) {
		MemberOperationPojo memberOperationPojo = addBulkMember(memberList,
				ConstantFields.ROLE_USER.getConstantField());
		return memberOperationPojo;
	}

	public MemberOperationPojo addAdminMember(List<Member> memberList) {
		MemberOperationPojo memberOperationPojo = addBulkMember(memberList,
				ConstantFields.ROLE_ADMIN.getConstantField());
		return memberOperationPojo;
	}

	public MemberOperationPojo addBulkMember(List<Member> memberList, String role) {
		Logger LOGGER = loggingUtil.getLoggerForMemberSaving(this.getClass());
		LOGGER.log(Level.INFO, resourceBundleMessageManager.getValueOfProperty(role + "_bulkMemberAddingMethod", "en"));
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		List<Member> savedMemberList = new ArrayList<>();
		try {
			memberUtil.checkEmailAddressAndLanguageOnMemberList(memberList, LOGGER);
			for (Member member : memberList) {
				addOneMember(member, role, LOGGER);
				savedMemberList.add(member);
			}
			memberOperationPojo.setResult(
					resourceBundleMessageManager.getValueOfProperty(role + "_bulkMemberAddingSuccessfull", "en"));
			memberOperationPojo.setMemberList(memberUtil.removeFieldsFromReturningMember(savedMemberList));
			LOGGER.log(Level.INFO,
					resourceBundleMessageManager.getValueOfProperty(role + "_bulkMemberAddingSuccessfull", "en")
							+ memberList);
		} catch (CustomException customException) {
			LOGGER.log(Level.SEVERE,
					resourceBundleMessageManager.getValueOfProperty(role + "_bulkMemberAddingFaled", "en")
							+ customException.getErrorCode() + " " + customException.getErrorMessage());
			memberOperationPojo.setErrorCode(customException.getErrorCode());
			memberOperationPojo.setResult(customException.getErrorMessage());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,
					resourceBundleMessageManager.getValueOfProperty(role + "_bulkMemberAddingFaled", "en")
							+ e.getMessage());
			memberOperationPojo.setErrorCode(ErrorCodes.ERROR_10.getErrorCode());
			memberOperationPojo.setResult(e.getMessage());
		}
		return memberOperationPojo;
	}

	public MemberOperationPojo addOneMember(Member member, String role, Logger LOGGER) throws MessagingException {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		LOGGER.log(Level.INFO, resourceBundleMessageManager.getValueOfProperty(role + "_memberAddingMethod",
				member.getMemberLanguageCode()));
		member.setPassword(getHashedPassword(member.getPassword()));
		member.setEnabled(false);
		addMemberRolesObject(role, member);
		addActivationToken(member);
		memberRepository.save(member);
		//mailUtil.sendActivationMail(member.getEmail(), member.getActivationToken());
		memberOperationPojo.setResult(resourceBundleMessageManager.getValueOfProperty(role + "_memberAddingSuccessfull",
				member.getMemberLanguageCode()));
		LOGGER.log(Level.INFO, resourceBundleMessageManager.getValueOfProperty(role + "_memberAddingSuccessfull",
				member.getMemberLanguageCode()) + member);

		return memberOperationPojo;
	}

	private String getHashedPassword(String rawPassword) {
		return new BCryptPasswordEncoder().encode(rawPassword);
	}

	private void addMemberRolesObject(String role, Member member) {
		MemberRoles rolesOfMember = new MemberRoles();
		rolesOfMember.setRole(role);
		rolesOfMember.setEmail(member.getEmail());
		member.setRoleOfMember(rolesOfMember);
	}

	private void addActivationToken(Member member) {
		String activationToken = UUID.randomUUID().toString();
		member.setActivationToken(activationToken);

		LocalDateTime activationTokenExpDate = LocalDateTime.now().plusDays(1);
		// LocalDateTime activationTokenExpDate = LocalDateTime.now();//Use for expire
		// date test!
		member.setActivationTokenExpDate(activationTokenExpDate);
	}
}
