package com.ilkaygunel.service;

import com.ilkaygunel.entities.JWTBlackList;
import com.ilkaygunel.repository.JWTBlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {

    @Autowired
    private JWTBlackListRepository jwtBlackListRepository;

    public void logout(JWTBlackList jwtBlackList) {
        jwtBlackListRepository.save(jwtBlackList);
    }

    public JWTBlackList findByTokenAndEMailAddress(String emailAddress, String token) {
        return jwtBlackListRepository.findByTokenAndUser(token, emailAddress);
    }
}
