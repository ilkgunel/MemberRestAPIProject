package com.member.service;

import com.member.repository.MemberRolesRepository;
import com.member.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.member.application.ResourceBundleMessageManager;
import com.member.repository.MemberRepository;
import com.member.util.LoggingUtil;
import com.member.util.MemberUtil;

@Service
public class BaseService {
	@Autowired
	protected MemberUtil memberUtil;

	@Autowired
	protected MailUtil mailUtil;
	
	@Autowired
	protected Environment environment;

	@Autowired
	protected MemberRepository memberRepository;

	@Autowired
	protected MemberRolesRepository memberRolesRepository;
	
	@Autowired
	public LoggingUtil loggingUtil;
	
	@Autowired
	protected MemberSaveService memberSaveService;
	
	@Autowired
	protected MemberRoleSaveService memberRoleSaveService;
	
	@Autowired
	protected ResourceBundleMessageManager resourceBundleMessageManager;

}
