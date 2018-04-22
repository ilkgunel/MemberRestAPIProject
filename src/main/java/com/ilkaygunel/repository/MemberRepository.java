package com.ilkaygunel.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ilkaygunel.entities.Member;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor=Exception.class)
public interface MemberRepository extends CrudRepository<Member, Long> {
	Member findByFirstName(String firstName);

	Member findByEmail(String email);

	Member findByActivationToken(String activationToken);
}
