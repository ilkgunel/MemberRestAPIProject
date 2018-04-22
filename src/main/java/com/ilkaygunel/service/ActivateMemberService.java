package com.ilkaygunel.service;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.CustomException;
import org.springframework.stereotype.Service;

@Service
public class ActivateMemberService extends BaseService{

    public String activateMember(String activationToken){
        try {
            Member existingMember = memberRepository.findByActivationToken(activationToken);
            if (existingMember==null){
                throw new CustomException("ERROR-08", "There is no member to activate with this email address!");
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
