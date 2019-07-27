package com.ilkaygunel.repository;

import com.ilkaygunel.entities.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {

    public PasswordResetToken findByPasswordResetToken(String token);
}
