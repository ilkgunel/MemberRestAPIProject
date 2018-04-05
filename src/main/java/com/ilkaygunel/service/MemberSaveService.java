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
		MemberOperationPojo memberOperationPojo = addOneMember(member,ConstantFields.ROLE_USER.getConstantField());
		return memberOperationPojo;

	}

	public MemberOperationPojo addOneAdminMember(Member member) {
		return addOneMember(member,ConstantFields.ROLE_ADMIN.getConstantField());
	}

	public MemberOperationPojo addBulkUserMember(List<Member> memberList) {
		return addBulkMember(memberList,ConstantFields.ROLE_USER.getConstantField());
	}

	public MemberOperationPojo addBulkAdminMember(List<Member> memberList) {
		return addBulkMember(memberList,ConstantFields.ROLE_ADMIN.getConstantField());
	}

	public MemberOperationPojo addOneMember(Member member,String role) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Logger LOGGER = loggingUtil.getLoggerForMemberSaving(this.getClass());
		try {
			LOGGER.log(Level.INFO, environment.getProperty(role + "_memberAddingMethod"));
			checkMemberFields(member);
			member.setPassword(getHashedPassword(member.getPassword()));
			member.setEnabled(true);// In future development, this field will be false and e-mail activation will be
									// required!
			addMemberRolesObject(role,member);
			memberRepository.save(member);
			memberOperationPojo
					.setResult(environment.getProperty(role + "_memberAddingSuccessfull") + member);
			LOGGER.log(Level.INFO, environment.getProperty(role + "_memberAddingSuccessfull") + member);
		} catch (CustomException e) {
			LOGGER.log(Level.SEVERE, environment.getProperty(role + "_memberAddingFaled") + e.getErrorCode()
					+ " " + e.getErrorMessage());
			memberOperationPojo.setErrorCode(e.getErrorCode());
			memberOperationPojo.setResult(e.getErrorMessage());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, environment.getProperty(role + "_memberAddingFaled") + e.getMessage());
			memberOperationPojo.setResult(e.getMessage());
		}
		return memberOperationPojo;
	}

	public MemberOperationPojo addBulkMember(List<Member> memberList,String role) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Logger LOGGER = loggingUtil.getLoggerForMemberSaving(this.getClass());
		try {
			LOGGER.log(Level.INFO, environment.getProperty(role + "_bulkMemberAddingMethod"));
			for (Member member : memberList) {
				checkMemberFields(member);
				member.setPassword(getHashedPassword(member.getPassword()));
				member.setEnabled(true);
				addMemberRolesObject(role,member);
				memberRepository.save(member);
			}
			memberOperationPojo.setResult(
					environment.getProperty(role + "_bulkMemberAddingSuccessfull") + memberList);
			LOGGER.log(Level.INFO,
					environment.getProperty(role + "_bulkMemberAddingSuccessfull") + memberList);
		} catch (CustomException e) {
			LOGGER.log(Level.SEVERE, environment.getProperty(role + "_bulkMemberAddingFaled")
					+ e.getErrorCode() + " " + e.getErrorMessage());
			memberOperationPojo.setErrorCode(e.getErrorCode());
			memberOperationPojo.setResult(e.getErrorMessage());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,
					environment.getProperty(role + "_bulkMemberAddingFaled") + e.getMessage());
			memberOperationPojo.setResult(e.getMessage());
		}
		return memberOperationPojo;
	}

	public void checkMemberFields(Member member) throws CustomException {
		if (ObjectUtils.isEmpty(member.getEmail())) {
			throw new CustomException(ErrorCodes.ERROR_05.getErrorCode(), environment.getProperty(ErrorCodes.ERROR_05.getErrorCode()));
		} else if (memberRepository.findByEmail(member.getEmail()) != null) {
			throw new CustomException(ErrorCodes.ERROR_06.getErrorCode(), environment.getProperty(ErrorCodes.ERROR_06.getErrorCode()) + " " + member.getEmail());
		}else if(!memberUtil.isValidEmailAddress(member.getEmail())){
			throw new CustomException(ErrorCodes.ERROR_07.getErrorCode(), environment.getProperty(ErrorCodes.ERROR_07.getErrorCode()) + " " + member.getEmail());
		}
	}

	private String getHashedPassword(String rawPassword) {
		return new BCryptPasswordEncoder().encode(rawPassword);
	}

	private void addMemberRolesObject(String role,Member member){
		MemberRoles rolesOfMember = new MemberRoles();
		rolesOfMember.setRole(role);
		rolesOfMember.setEmail(member.getEmail());
		member.setRoleOfMember(rolesOfMember);
	}
}
