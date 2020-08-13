package com.inbiznetcorp.acs.web.message.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.mapper.message.FileinfoMapper;

@Service("com.inbiznetcorp.acs.web.message.service.FileinfoService")
public class FileinfoService
{
	@Resource(name="com.inbiznetcorp.acs.mapper.message.FileinfoMapper")
	FileinfoMapper fileinfoMapper;

	public int RegisterData(MyMap paramMap)
	{
		return fileinfoMapper.RegisterData(paramMap);
	}

}
