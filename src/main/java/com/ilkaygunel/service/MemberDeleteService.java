package com.ilkaygunel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ilkaygunel.constants.ConstantFields;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.wrapper.MemberIdWrapp;

@Service
public class MemberDeleteService extends BaseService {

	public MemberOperationPojo deleteUserMember(List<MemberIdWrapp> memberIdList) {
		return deleteBulkMember(memberIdList, ConstantFields.ROLE_USER.getConstantField());
	}

	public MemberOperationPojo deleteAdminMember(List<MemberIdWrapp> memberIdList) {
		return deleteBulkMember(memberIdList, ConstantFields.ROLE_ADMIN.getConstantField());
	}

	public MemberOperationPojo deleteOneMember(long memberId, String roleOfMember) throws CustomException, Exception {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Member memberForDelete = memberUtil.checkMember(memberId, roleOfMember);
		memberRepository.delete(memberId);
		memberOperationPojo.setMember(memberForDelete);
		memberOperationPojo
				.setResult(resourceBundleMessageManager.getValueOfProperty(roleOfMember + "_memberDeletingSuccessfull",
						memberForDelete.getMemberLanguageCode()) + memberForDelete);
		return memberOperationPojo;
	}

	private MemberOperationPojo deleteBulkMember(List<MemberIdWrapp> memberIdList, String roleForCheck) {
		Logger LOGGER = loggingUtil.getLoggerForMemberDeleting(this.getClass());
		MemberOperationPojo deleteMemberOpertaionPojo = memberUtil.checkMemberExistenceOnMemberList(memberIdList,
				roleForCheck);
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		try {
			if (ObjectUtils.isEmpty(deleteMemberOpertaionPojo.getErrorCode())) {
				List<Member> deletedMemberList = new ArrayList<>();
				for (MemberIdWrapp memberIdWrapp : memberIdList) {
					MemberOperationPojo temporaryMemberOperationPojo = deleteOneMember(memberIdWrapp.getId(),
							roleForCheck);
					deletedMemberList.add(temporaryMemberOperationPojo.getMember());
				}
				memberOperationPojo.setResult(
						resourceBundleMessageManager.getValueOfProperty(roleForCheck + "_bulkMemberDeletingSuccessfull",
								deletedMemberList.get(0).getMemberLanguageCode()));
				memberOperationPojo.setMemberList(deletedMemberList);

			} else {
				memberOperationPojo.setErrorCode(deleteMemberOpertaionPojo.getErrorCode());
				memberOperationPojo.setResult(deleteMemberOpertaionPojo.getResult());
			}
		} catch (CustomException e) {
			LOGGER.log(Level.SEVERE, environment.getProperty(roleForCheck + "_memberDeletingFailed") + e.getErrorCode()
					+ " " + e.getErrorMessage());
			memberOperationPojo.setErrorCode(e.getErrorCode());
			memberOperationPojo.setResult(e.getErrorMessage());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, environment.getProperty(roleForCheck + "_memberDeletingFailed") + e.getMessage());
			memberOperationPojo.setResult(e.getMessage());
		}

		return memberOperationPojo;
	}

}
