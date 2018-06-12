package com.member.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.member.application.ResourceBundleMessageManager;
import com.member.constants.ConstantFields;
import com.member.entity.Member;
import com.member.entity.MemberRoles;
import com.member.exception.CustomException;
import com.member.exception.ErrorCodes;
import com.member.pojo.MemberOperationPojo;
import com.member.repository.MemberRepository;
import com.member.service.MemberRoleSaveService;
import com.member.wrapper.MemberIdWrapp;

@Component
public class MemberUtil {

    private static final Logger logger = Logger.getLogger(MemberUtil.class.getName());

    private final MemberRepository memberRepository;
    private final MemberRoleSaveService memberRoleSaveService;
    private final ResourceBundleMessageManager resourceBundleMessageManager;

    public MemberUtil(MemberRepository memberRepository,
                      MemberRoleSaveService memberRoleSaveService,
                      ResourceBundleMessageManager resourceBundleMessageManager) {
        this.memberRepository = memberRepository;
        this.memberRoleSaveService = memberRoleSaveService;
        this.resourceBundleMessageManager = resourceBundleMessageManager;
    }

    public MemberOperationPojo checkMemberExistenceOnMemberList(List<MemberIdWrapp> memberIdList, String roleForCheck) {
        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
        try {
            for (MemberIdWrapp memberIdWrapp : memberIdList) {
                checkMember(memberIdWrapp.getId(), roleForCheck);
            }
        } catch (CustomException customException) {
            memberOperationPojo.setErrorCode(customException.getErrorCode());
            memberOperationPojo.setResult(customException.getErrorMessage());
        } catch (Exception exception) {
            memberOperationPojo.setErrorCode(ErrorCodes.ERROR_10.getErrorCode());
            memberOperationPojo.setResult(exception.getMessage());
        }
        return memberOperationPojo;
    }

    public void checkEmailAddressAndLanguageOnMemberList(List<Member> memberList) throws CustomException {
        boolean checkMailHasError = memberList.stream()
                .map(this::checkEmailAddress)
                .anyMatch(ObjectUtils::isEmpty);

        if (checkMailHasError) {
            throw new CustomException("", "");//TODO buna neden ihtiyac var.
        }
    }

    public MemberOperationPojo checkMemberListContainPassowrdForUpdate(List<Member> memberList) {
        MemberOperationPojo passwordChecking = new MemberOperationPojo();
        try {
            for (Member checkingMember : memberList) {
                if (!isRequestContainsPassword(checkingMember)) {

                    throw new CustomException(ErrorCodes.ERROR_12.getErrorCode(),
                            resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_12.getErrorCode(),
                                    checkingMember.getMemberLanguageCode()));
                }
            }
        } catch (CustomException customException) {
            passwordChecking.setErrorCode(customException.getErrorCode());
            passwordChecking.setResult(customException.getErrorMessage());
        }
        return passwordChecking;
    }

    public List<MemberIdWrapp> getMemberIdListFromMemberList(List<Member> members) {
        List<MemberIdWrapp> memberIdList = new ArrayList<>();
        for (Member member : members) {
            MemberIdWrapp memberIdWrapp = new MemberIdWrapp();
            memberIdWrapp.setId(member.getId());
            memberIdList.add(memberIdWrapp);
        }
        return memberIdList;
    }

    public List<Member> removeFieldsFromReturningMember(List<Member> memberList) {
        for (Member member : memberList) {
            member.setPassword(null);
        }
        return memberList;
    }

    private boolean isRequestContainsPassword(Member memberForUpdate) {
        return ObjectUtils.isEmpty(memberForUpdate.getPassword());
    }

    private boolean isValidEmailAddress(String emailAddress) {
        Pattern emailPattern = Pattern.compile(ConstantFields.EMAIL_CHECK_PATTERN.getConstantField());
        Matcher emailMatcher = emailPattern.matcher(emailAddress);
        return emailMatcher.matches();
    }

    private MemberOperationPojo checkEmailAddress(Member member) {
        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
        try {
            if (ObjectUtils.isEmpty(member.getEmail())) {
                throw new CustomException(ErrorCodes.ERROR_05.getErrorCode(),
                        resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_05.getErrorCode(), "en"));
            } else if (memberRepository.findByEmail(member.getEmail()) != null) {
                throw new CustomException(ErrorCodes.ERROR_06.getErrorCode(),
                        resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_06.getErrorCode(), "en") + " "
                                + member.getEmail());
            } else if (!isValidEmailAddress(member.getEmail())) {
                throw new CustomException(ErrorCodes.ERROR_07.getErrorCode(),
                        resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_07.getErrorCode(), "en") + " "
                                + member.getEmail());
            }
        } catch (CustomException customException) {
            memberOperationPojo.setErrorCode(customException.getErrorCode());
            memberOperationPojo.setResult(customException.getErrorMessage());
        }
        return memberOperationPojo;
    }

    private Member checkMember(Long memberId, String roleForCheck) throws Exception {
        Member member = memberRepository.findOne(memberId);

        if (member == null) {
            if (ConstantFields.ROLE_USER.getConstantField().equals(roleForCheck)) {
                throw new CustomException(ErrorCodes.ERROR_01.getErrorCode(),
                        resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_01.getErrorCode(), "en"));
            } else {
                throw new CustomException(ErrorCodes.ERROR_02.getErrorCode(),
                        resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_02.getErrorCode(), "en"));
            }
        } else {
            MemberRoles memberRoles = memberRoleSaveService.getMemberRoleWithEmail(member.getEmail());
            if (ConstantFields.ROLE_USER.getConstantField().equals(roleForCheck)
                    && !ConstantFields.ROLE_USER.getConstantField().equals(memberRoles.getRole())) {
                throw new CustomException(ErrorCodes.ERROR_03.getErrorCode(), resourceBundleMessageManager
                        .getValueOfProperty(ErrorCodes.ERROR_03.getErrorCode(), member.getMemberLanguageCode()));
            } else if (ConstantFields.ROLE_ADMIN.getConstantField().equals(roleForCheck)
                    && !ConstantFields.ROLE_ADMIN.getConstantField().equals(memberRoles.getRole())) {
                throw new CustomException(ErrorCodes.ERROR_04.getErrorCode(), resourceBundleMessageManager
                        .getValueOfProperty(ErrorCodes.ERROR_04.getErrorCode(), member.getMemberLanguageCode()));
            }
        }

        return member;
    }
}
