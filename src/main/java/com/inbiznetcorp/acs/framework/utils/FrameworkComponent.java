package com.inbiznetcorp.acs.framework.utils;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.inbiznetcorp.acs.scheduled.service.CallScheduled;

@Component
public class FrameworkComponent
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FrameworkComponent.class);
	
	@PostConstruct
	public void initThread()
	{
		CallScheduled callScheduled = new CallScheduled();
		new Thread(callScheduled).start();
	}
}