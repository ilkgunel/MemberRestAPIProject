package com.ilkaygunel.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ilkaygunel.constants.ConstantFields;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.repository.MemberRepository;

@Service
public class MemberUpdateService extends BaseService{

	public MemberOperationPojo updateOneUserMember(Member memberForUpdate) {
		return updateOneMember(memberForUpdate, ConstantFields.ROLE_USER.getConstantField());
	}

	public MemberOperationPojo updateOneAdminMember(Member memberForUpdate) {
		return updateOneMember(memberForUpdate, ConstantFields.ROLE_ADMIN.getConstantField());
	}

	public MemberOperationPojo updateBulkUserMember(List<Member> memberListForUpdate) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		for (Member member : memberListForUpdate) {
			MemberOperationPojo temporaryMemberOperationPojo = updateOneMember(member, ConstantFields.ROLE_USER.getConstantField());
			memberOperationPojo.setResult(ObjectUtils.getDisplayString(memberOperationPojo.getResult()) + " "
					+ environment.getProperty(ConstantFields.ROLE_USER + "_bulkMemberDeletingSuccessfull")
					+ temporaryMemberOperationPojo.getMember());
		}
		return memberOperationPojo;
	}

	public MemberOperationPojo updateBulkAdminMember(List<Member> memberListForUpdate) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		for (Member member : memberListForUpdate) {
			MemberOperationPojo temporaryMemberOperationPojo = updateOneMember(member, ConstantFields.ROLE_ADMIN.getConstantField());
			memberOperationPojo.setResult(ObjectUtils.getDisplayString(memberOperationPojo.getResult()) + " "
					+ environment.getProperty(ConstantFields.ROLE_ADMIN + "_bulkMemberDeletingSuccessfull")
					+ temporaryMemberOperationPojo.getMember());
		}
		return memberOperationPojo;
	}

	public MemberOperationPojo updateOneMember(Member memberForUpdate, String roleForCheck) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		memberOperationPojo.setMember(memberForUpdate);
		Logger LOGGER = loggingUtil.getLoggerForMemberUpdating(this.getClass());
		try {
			LOGGER.log(Level.INFO, resourceBundleMessageManager.getValueOfProperty(roleForCheck + "_memberUpdatingMethod",memberForUpdate.getMemberLanguageCode()));
			LOGGER.log(Level.INFO, resourceBundleMessageManager.getValueOfProperty(roleForCheck + "_memberInformationBeforeUpdate",memberForUpdate.getMemberLanguageCode()));

			memberUtil.checkMember(memberForUpdate.getId(), roleForCheck);
			memberRepository.save(memberForUpdate);
			LOGGER.log(Level.INFO, resourceBundleMessageManager.getValueOfProperty(roleForCheck + "_memberUpdatingSuccessful",memberForUpdate.getMemberLanguageCode())
					+ memberRepository.findOne(memberForUpdate.getId()));
			memberOperationPojo.setResult(resourceBundleMessageManager.getValueOfProperty(roleForCheck + "_memberUpdatingMethod",memberForUpdate.getMemberLanguageCode())
					+ memberRepository.findOne(memberForUpdate.getId()));
		} catch (CustomException e) {
			LOGGER.log(Level.SEVERE, resourceBundleMessageManager.getValueOfProperty(roleForCheck + "_memberUpdatingFailed",memberForUpdate.getMemberLanguageCode()) + e.getErrorCode()
					+ " " + e.getErrorMessage());
			memberOperationPojo.setErrorCode(e.getErrorCode());
			memberOperationPojo.setResult(e.getErrorMessage());
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, resourceBundleMessageManager.getValueOfProperty(roleForCheck + "_memberUpdatingFailed",memberForUpdate.getMemberLanguageCode())
					+ memberRepository.findOne(memberForUpdate.getId()) + ex.getMessage());
		}
		return memberOperationPojo;
	}
}
