package com.ilkaygunel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ilkaygunel.repository.MemberRepository;
import com.ilkaygunel.util.LoggingUtil;
import com.ilkaygunel.util.MemberUtil;

@Service
public class BaseService {
	@Autowired
	protected MemberUtil memberUtil;
	
	@Autowired
	protected Environment environment;

	@Autowired
	protected MemberRepository memberRepository;
	
	@Autowired
	public LoggingUtil loggingUtil;
	
	@Autowired
	protected MemberSaveService memberSaveService;
	
	@Autowired
	protected MemberRoleSaveService memberRoleSaveService;
}
