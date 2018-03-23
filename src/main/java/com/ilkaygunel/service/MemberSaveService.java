package com.ilkaygunel.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;

import com.ilkaygunel.constants.ConstantFields;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.ErrorCodes;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.repository.MemberRepository;
import com.ilkaygunel.util.LoggingUtil;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:errorMeanings.properties")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messageTexts.properties")
@Service
public class MemberSaveService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberRoleSaveService memberRoleSaveService;

	@Autowired
	private Environment environment;

	public MemberOperationPojo addOneUserMember(Member member) {
		member.setRole(ConstantFields.ROLE_USER);
		MemberOperationPojo memberOperationPojo = addOneMember(member);
		return memberOperationPojo;

	}

	public MemberOperationPojo addOneAdminMember(Member member) {
		member.setRole(ConstantFields.ROLE_ADMIN);
		return addOneMember(member);
	}

	public MemberOperationPojo addBulkUserMember(List<Member> memberList) {
		memberList.forEach(member -> member.setRole(ConstantFields.ROLE_USER));
		return addBulkMember(memberList);
	}

	public MemberOperationPojo addBulkAdminMember(List<Member> memberList) {
		memberList.forEach(member -> member.setRole(ConstantFields.ROLE_ADMIN));
		return addBulkMember(memberList);
	}

	public MemberOperationPojo addOneMember(Member member) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Logger LOGGER = new LoggingUtil().getLoggerForMemberSaving(this.getClass());
		try {
			LOGGER.log(Level.INFO, environment.getProperty(member.getRole() + "_memberAddingMethod"));
			checkMemberFields(member);
			member.setPassword(getHashedPassword(member.getPassword()));
			member.setEnabled(true);// In future development, this field will be false and e-mail activation will be
									// required!
			memberRepository.save(member);
			memberRoleSaveService.saveMemberWithRole(member.getEmail(), member.getRole());
			memberOperationPojo
					.setResult(environment.getProperty(member.getRole() + "_memberAddingSuccessfull") + member);
			LOGGER.log(Level.INFO, environment.getProperty(member.getRole() + "_memberAddingSuccessfull") + member);
		} catch (CustomException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.log(Level.SEVERE, environment.getProperty(member.getRole() + "_memberAddingFaled") + e.getErrorCode()
					+ " " + e.getErrorMessage());
			memberOperationPojo.setErrorCode(e.getErrorCode());
			memberOperationPojo.setResult(e.getErrorMessage());
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.log(Level.SEVERE, environment.getProperty(member.getRole() + "_memberAddingFaled") + e.getMessage());
			memberOperationPojo.setResult(e.getMessage());
		}
		return memberOperationPojo;
	}

	public MemberOperationPojo addBulkMember(List<Member> memberList) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Logger LOGGER = new LoggingUtil().getLoggerForMemberSaving(this.getClass());
		try {
			LOGGER.log(Level.INFO, environment.getProperty(memberList.get(0).getRole() + "_bulkMemberAddingMethod"));
			for (Member member : memberList) {
				checkMemberFields(member);
				member.setPassword(getHashedPassword(member.getPassword()));
				member.setEnabled(true);// In future development, this field will be false and e-mail activation will be
				// required!
				memberRepository.save(member);
				memberRoleSaveService.saveMemberWithRole(member.getEmail(), member.getRole());
			}
			memberOperationPojo.setResult(
					environment.getProperty(memberList.get(0).getRole() + "_bulkMemberAddingSuccessfull") + memberList);
			LOGGER.log(Level.INFO,
					environment.getProperty(memberList.get(0).getRole() + "_bulkMemberAddingSuccessfull") + memberList);
		} catch (CustomException e) {
			LOGGER.log(Level.SEVERE, environment.getProperty(memberList.get(0).getRole() + "_bulkMemberAddingFaled")
					+ e.getErrorCode() + " " + e.getErrorMessage());
			memberOperationPojo.setErrorCode(e.getErrorCode());
			memberOperationPojo.setResult(e.getErrorMessage());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,
					environment.getProperty(memberList.get(0).getRole() + "_bulkMemberAddingFaled") + e.getMessage());
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
