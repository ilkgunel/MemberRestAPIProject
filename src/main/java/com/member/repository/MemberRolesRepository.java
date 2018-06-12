package com.member.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.member.entity.MemberRoles;

@Repository
public interface MemberRolesRepository extends CrudRepository<MemberRoles, Long> {
    MemberRoles findByEmail(String email);
}
