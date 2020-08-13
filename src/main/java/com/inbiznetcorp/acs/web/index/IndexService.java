package com.inbiznetcorp.acs.web.index;

import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.mapper.userinfo.UserInfoMapper;

@Service("com.inbiznetcorp.acs.web.index.IndexService")
public class IndexService
{
	UserInfoMapper userInfoMapper = (UserInfoMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.userinfo.UserInfoMapper");

	public MyCamelMap FindUserInfo(MyMap paramMap)
	{
		return userInfoMapper.FindUserInfo(paramMap);
	}
	
}
