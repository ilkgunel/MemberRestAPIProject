package com.ilkaygunel.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ilkaygunel.entities.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
	Member findByFirstName(String firstName);

	Member findByEmail(String email);
}
