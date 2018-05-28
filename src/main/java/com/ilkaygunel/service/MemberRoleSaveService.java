package com.ilkaygunel.service;

import org.springframework.stereotype.Service;

import com.ilkaygunel.entities.MemberRoles;

@Service
public class MemberRoleSaveService extends BaseService {

	public MemberRoles getMemberRoleWithEmail(String email) {
		return memberRolesRepository.findByEmail(email);
	}
}
