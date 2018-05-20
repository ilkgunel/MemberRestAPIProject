package com.ilkaygunel.service;

import com.ilkaygunel.repository.MemberRolesRepository;
import com.ilkaygunel.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ilkaygunel.application.ResourceBundleMessageManager;
import com.ilkaygunel.repository.MemberRepository;
import com.ilkaygunel.util.LoggingUtil;
import com.ilkaygunel.util.MemberUtil;

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
