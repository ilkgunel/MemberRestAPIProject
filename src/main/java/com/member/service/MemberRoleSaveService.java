package com.member.service;

import org.springframework.stereotype.Service;

import com.member.entity.MemberRoles;

@Service
public class MemberRoleSaveService extends BaseService {

	public MemberRoles getMemberRoleWithEmail(String email) {
		return memberRolesRepository.findByEmail(email);
	}
}
