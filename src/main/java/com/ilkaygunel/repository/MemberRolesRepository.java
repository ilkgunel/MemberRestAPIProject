package com.ilkaygunel.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ilkaygunel.entities.MemberRoles;

@Repository
public interface MemberRolesRepository extends CrudRepository<MemberRoles, Long> {
    MemberRoles findByEmail(String email);
}
