package com.ilkaygunel.util;

import com.ilkaygunel.entities.MemberRoles;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.repository.MemberRolesRepository;
import com.ilkaygunel.service.MemberRoleSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ilkaygunel.constants.ConstantFields;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.ErrorCodes;
import com.ilkaygunel.repository.MemberRepository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:errorMeanings.properties")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messageTexts.properties")
@Component
public class MemberUtil {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private MemberRoleSaveService memberRoleSaveService;

    public Member checkMember(Long memberId, String roleForCheck) throws Exception {
        Member member = memberRepository.findOne(memberId);
        MemberRoles memberRoles = memberRoleSaveService.getMemberRoleWithEmail(member.getEmail());
        if (member == null) {
            if (ConstantFields.ROLE_USER.equals(roleForCheck)) {
                throw new CustomException(ErrorCodes.ERROR_01.getErrorCode(), environment.getProperty(ErrorCodes.ERROR_01.getErrorCode()));
            } else {
                throw new CustomException(ErrorCodes.ERROR_02.getErrorCode(), environment.getProperty(ErrorCodes.ERROR_02.getErrorCode()));
            }
        } else if (ConstantFields.ROLE_USER.equals(roleForCheck)
                && !ConstantFields.ROLE_USER.equals(memberRoles.getRole())) {
            throw new CustomException(ErrorCodes.ERROR_03.getErrorCode(), environment.getProperty(ErrorCodes.ERROR_03.getErrorCode()));
        } else if (ConstantFields.ROLE_ADMIN.equals(roleForCheck)
                && !ConstantFields.ROLE_ADMIN.equals(memberRoles.getRole())) {
            throw new CustomException(ErrorCodes.ERROR_04.getErrorCode(), environment.getProperty(ErrorCodes.ERROR_04.getErrorCode()));
        }
        return member;
    }

    public boolean isValidEmailAddress(String emailAddress) {
        Pattern emailPattern = Pattern.compile(ConstantFields.EMAIL_CHECK_PATTERN.getConstantField());
        Matcher emailMatcher = emailPattern.matcher(emailAddress);
        return emailMatcher.matches();
    }

    public MemberOperationPojo checkEmailAddress(Member member, Logger LOGGER) {
        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
        try {
            if (ObjectUtils.isEmpty(member.getEmail())) {
                throw new CustomException(ErrorCodes.ERROR_05.getErrorCode(), environment.getProperty(ErrorCodes.ERROR_05.getErrorCode()));
            } else if (memberRepository.findByEmail(member.getEmail()) != null) {
                throw new CustomException(ErrorCodes.ERROR_06.getErrorCode(), environment.getProperty(ErrorCodes.ERROR_06.getErrorCode()) + " " + member.getEmail());
            } else if (!isValidEmailAddress(member.getEmail())) {
                throw new CustomException(ErrorCodes.ERROR_07.getErrorCode(), environment.getProperty(ErrorCodes.ERROR_07.getErrorCode()) + " " + member.getEmail());
            }
        } catch (CustomException customException) {
            LOGGER.log(Level.SEVERE,
                    environment.getProperty(ConstantFields.ROLE_USER.getConstantField() + "_memberAddingFaled") + customException.getErrorCode() + " "
                            + customException.getErrorMessage());
            memberOperationPojo.setErrorCode(customException.getErrorCode());
            memberOperationPojo.setResult(customException.getErrorMessage());
        }
        return memberOperationPojo;
    }

    public void checkEmailAddressOnMemberList(List<Member> memberList, Logger LOGGER) throws CustomException {
        MemberOperationPojo memberOperationPojo = null;
        for (Member member : memberList) {
            memberOperationPojo = checkEmailAddress(member, LOGGER);
            if (!ObjectUtils.isEmpty(memberOperationPojo.getErrorCode())){
                throw new CustomException(memberOperationPojo.getErrorCode(), environment.getProperty(memberOperationPojo.getErrorCode()));
            }
        }
    }
}
