package com.member.service;

import com.member.entity.Member;
import com.member.exception.CustomException;
import com.member.exception.ErrorCodes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ActivateMemberService extends BaseService {

    public String activateMember(String activationToken) {
        try {
            Member member = memberRepository.findByActivationToken(activationToken)
                    .orElseThrow(() -> new CustomException(ErrorCodes.ERROR_08.getErrorCode(), environment.getProperty(ErrorCodes.ERROR_08.getErrorCode())));


            if (LocalDateTime.now().isAfter(member.getActivationTokenExpDate())) {
                throw new CustomException(ErrorCodes.ERROR_09.getErrorCode(), environment.getProperty(ErrorCodes.ERROR_09.getErrorCode()));
            }
            member.setEnabled(true);
            memberRepository.save(member);
            return "Activating member is successfull";
        } catch (CustomException e) {
            return "An error occured while activating member:" + e.getErrorCode() + " " + e.getErrorMessage();
        } catch (Exception e) {
            return "An error occured while activating member:" + e.getMessage();
        }
    }
}
