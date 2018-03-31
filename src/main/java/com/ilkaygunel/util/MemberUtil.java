package com.ilkaygunel.util;

import com.ilkaygunel.entities.MemberRoles;
import com.ilkaygunel.repository.MemberRolesRepository;
import com.ilkaygunel.service.MemberRoleSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ilkaygunel.constants.ConstantFields;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.ErrorCodes;
import com.ilkaygunel.repository.MemberRepository;

@Component
public class MemberUtil {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private Environment environment;

	@Autowired
	private MemberRoleSaveService memberRoleSaveService;

	public Member checkMember(Long memberId, String roleForCheck) throws CustomException, Exception {
		Member member = memberRepository.findOne(memberId);
		MemberRoles memberRoles = memberRoleSaveService.getMemberRoleWithEmail(member.getEmail());
		if (member == null) {
			if (ConstantFields.ROLE_USER.equals(roleForCheck)) {
				throw new CustomException(ErrorCodes.ERROR_01, environment.getProperty(ErrorCodes.ERROR_01));
			} else {
				throw new CustomException(ErrorCodes.ERROR_02, environment.getProperty(ErrorCodes.ERROR_02));
			}
		} else if (ConstantFields.ROLE_USER.equals(roleForCheck)
				&& !ConstantFields.ROLE_USER.equals(memberRoles.getRole())) {
			throw new CustomException(ErrorCodes.ERROR_03, environment.getProperty(ErrorCodes.ERROR_03));
		} else if (ConstantFields.ROLE_ADMIN.equals(roleForCheck)
				&& !ConstantFields.ROLE_ADMIN.equals(memberRoles.getRole())) {
			throw new CustomException(ErrorCodes.ERROR_04, environment.getProperty(ErrorCodes.ERROR_04));
		}
		memberRepository.delete(memberId);
		return member;
	}
}
