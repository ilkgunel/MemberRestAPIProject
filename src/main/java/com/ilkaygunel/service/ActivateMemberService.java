package com.ilkaygunel.service;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.ErrorCodes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActivateMemberService extends BaseService{

    public String activateMember(String activationToken){
        try {
            Member existingMember = memberRepository.findByActivationToken(activationToken);
            if (existingMember==null){
                throw new CustomException(ErrorCodes.ERROR_08.getErrorCode(), environment.getProperty(ErrorCodes.ERROR_08.getErrorCode()));
            }
            if (LocalDateTime.now().isAfter(existingMember.getActivationTokenExpDate())){
                throw new CustomException(ErrorCodes.ERROR_09.getErrorCode(), environment.getProperty(ErrorCodes.ERROR_09.getErrorCode()));
            }
            existingMember.setEnabled(true);
            memberRepository.save(existingMember);
            return "Activating member is successfull";
        }
        catch (CustomException e){
            return "An error occured while activating member:"+e.getErrorCode()+" "+ e.getErrorMessage();
        }
        catch (Exception e){
            return "An error occured while activating member:"+e.getMessage();
        }
    }
}
