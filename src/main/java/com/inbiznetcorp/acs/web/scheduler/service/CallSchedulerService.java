package com.inbiznetcorp.acs.web.scheduler.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper;

@Service("com.inbiznetcorp.acs.web.scheduler.service.CallSchedulerService")
public class CallSchedulerService
{
	@Resource(name="com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper")
	SchedulerMapper schedulerMapper;

	public int RegisterData(MyMap paramMap)
	{
		return schedulerMapper.RegisterData(paramMap);
	}

}
