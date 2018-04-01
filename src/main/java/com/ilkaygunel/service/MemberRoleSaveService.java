package com.ilkaygunel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ilkaygunel.entities.MemberRoles;
import com.ilkaygunel.repository.MemberRolesRepository;

@Service
public class MemberRoleSaveService extends BaseService {

	public MemberRoles getMemberRoleWithEmail(String email){
		return memberRolesRepository.findByEmail(email);
	}
}
