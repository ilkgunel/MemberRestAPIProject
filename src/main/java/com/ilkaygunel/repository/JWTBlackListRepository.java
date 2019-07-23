package com.ilkaygunel.repository;

import com.ilkaygunel.entities.JWTBlackList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JWTBlackListRepository extends CrudRepository<JWTBlackList, Long> {
    JWTBlackList findByTokenAndUser(String token, String user);
}
