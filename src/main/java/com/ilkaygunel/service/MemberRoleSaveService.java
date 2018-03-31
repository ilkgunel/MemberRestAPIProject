package com.ilkaygunel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ilkaygunel.entities.MemberRoles;
import com.ilkaygunel.repository.MemberRolesRepository;

@Service
public class MemberRoleSaveService extends BaseService {
	@Autowired
	private MemberRolesRepository memberRolesRepository;

	public void saveMemberWithRole(String memberEmail, String memberRole) {
		try {
			MemberRoles memberRoles = new MemberRoles();
			memberRoles.setEmail(memberEmail);
			memberRoles.setRole(memberRole);
			memberRolesRepository.save(memberRoles);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	public MemberRoles getMemberRoleWithEmail(String email){
		return memberRolesRepository.findByEmail(email);
	}
}
