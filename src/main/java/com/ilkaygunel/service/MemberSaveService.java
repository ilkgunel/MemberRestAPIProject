package com.ilkaygunel.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ilkaygunel.entities.MemberRoles;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;

import com.ilkaygunel.constants.ConstantFields;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.ErrorCodes;
import com.ilkaygunel.pojo.MemberOperationPojo;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:errorMeanings.properties")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messageTexts.properties")
@Service
public class MemberSaveService extends BaseService{

	public MemberOperationPojo addOneUserMember(Member member) {
		MemberOperationPojo memberOperationPojo = addOneMember(member);
		return memberOperationPojo;

	}

	public MemberOperationPojo addOneAdminMember(Member member) {
		return addOneMember(member);
	}

	public MemberOperationPojo addBulkUserMember(List<Member> memberList) {
		return addBulkMember(memberList);
	}

	public MemberOperationPojo addBulkAdminMember(List<Member> memberList) {
		return addBulkMember(memberList);
	}

	public MemberOperationPojo addOneMember(Member member) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		MemberRoles memberRoles = memberRoleSaveService.getMemberRoleWithEmail(member.getEmail());
		Logger LOGGER = loggingUtil.getLoggerForMemberSaving(this.getClass());
		try {
			LOGGER.log(Level.INFO, environment.getProperty(memberRoles.getRole() + "_memberAddingMethod"));
			checkMemberFields(member);
			member.setPassword(getHashedPassword(member.getPassword()));
			member.setEnabled(true);// In future development, this field will be false and e-mail activation will be
									// required!
			memberRepository.save(member);
			memberRoleSaveService.saveMemberWithRole(member.getEmail(), memberRoles.getRole());
			memberOperationPojo
					.setResult(environment.getProperty(memberRoles.getRole() + "_memberAddingSuccessfull") + member);
			LOGGER.log(Level.INFO, environment.getProperty(memberRoles.getRole() + "_memberAddingSuccessfull") + member);
		} catch (CustomException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.log(Level.SEVERE, environment.getProperty(memberRoles.getRole() + "_memberAddingFaled") + e.getErrorCode()
					+ " " + e.getErrorMessage());
			memberOperationPojo.setErrorCode(e.getErrorCode());
			memberOperationPojo.setResult(e.getErrorMessage());
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.log(Level.SEVERE, environment.getProperty(memberRoles.getRole() + "_memberAddingFaled") + e.getMessage());
			memberOperationPojo.setResult(e.getMessage());
		}
		return memberOperationPojo;
	}

	public MemberOperationPojo addBulkMember(List<Member> memberList) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		MemberRoles memberRoles = memberRoleSaveService.getMemberRoleWithEmail(memberList.get(0).getEmail());
		Logger LOGGER = loggingUtil.getLoggerForMemberSaving(this.getClass());
		try {
			LOGGER.log(Level.INFO, environment.getProperty(memberRoles.getRole() + "_bulkMemberAddingMethod"));
			for (Member member : memberList) {
				checkMemberFields(member);
				member.setPassword(getHashedPassword(member.getPassword()));
				member.setEnabled(true);// In future development, this field will be false and e-mail activation will be
				// required!
				memberRepository.save(member);
				memberRoleSaveService.saveMemberWithRole(member.getEmail(), memberRoles.getRole());
			}
			memberOperationPojo.setResult(
					environment.getProperty(memberRoles.getRole() + "_bulkMemberAddingSuccessfull") + memberList);
			LOGGER.log(Level.INFO,
					environment.getProperty(memberRoles.getRole() + "_bulkMemberAddingSuccessfull") + memberList);
		} catch (CustomException e) {
			LOGGER.log(Level.SEVERE, environment.getProperty(memberRoles.getRole() + "_bulkMemberAddingFaled")
					+ e.getErrorCode() + " " + e.getErrorMessage());
			memberOperationPojo.setErrorCode(e.getErrorCode());
			memberOperationPojo.setResult(e.getErrorMessage());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,
					environment.getProperty(memberRoles.getRole() + "_bulkMemberAddingFaled") + e.getMessage());
			memberOperationPojo.setResult(e.getMessage());
		}
		return memberOperationPojo;
	}

	public void checkMemberFields(Member member) throws CustomException {
		if (ObjectUtils.isEmpty(member.getEmail())) {
			throw new CustomException(ErrorCodes.ERROR_05, environment.getProperty(ErrorCodes.ERROR_05));
		} else if (memberRepository.findByEmail(member.getEmail()) != null) {
			throw new CustomException(ErrorCodes.ERROR_06, environment.getProperty(ErrorCodes.ERROR_06));
		}
	}

	private String getHashedPassword(String rawPassword) {
		return new BCryptPasswordEncoder().encode(rawPassword);
	}

}
