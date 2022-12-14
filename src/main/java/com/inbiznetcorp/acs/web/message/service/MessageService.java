package com.inbiznetcorp.acs.web.message.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkPagingUtils;
import com.inbiznetcorp.acs.mapper.tempgroup.TempGroupMapper;
import com.inbiznetcorp.acs.mapper.temptarget.TempTargetMapper;
import com.inbiznetcorp.acs.mapper.userinfo.UserInfoMapper;

@Service("com.inbiznetcorp.acs.web.message.service.MessageService")
public class MessageService
{
	@Resource(name="com.inbiznetcorp.acs.mapper.tempgroup.TempGroupMapper")
	TempGroupMapper tempGroupMapper;
	
	@Resource(name="com.inbiznetcorp.acs.mapper.temptarget.TempTargetMapper")
	TempTargetMapper tempTargetMapper;
	
	@Resource(name="com.inbiznetcorp.acs.mapper.userinfo.UserInfoMapper")
	UserInfoMapper mUserInfoMapper;

	public BasicBean ListPagingData(MyMap paramMap)
	{
		BasicBean resultBean = null;

		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 10));
//		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 10),
//				mMapper.SelectOnePagingCount(paramMap), mMapper.ListPagingData(paramMap));

		return resultBean;
	}

	public int InsertTempTarget(MyMap paramMap)
	{
		return tempTargetMapper.InsertTempTarget(paramMap);
	}

	public int InsertTempGroup(MyMap paramMap)
	{
		return tempGroupMapper.InsertTempGroup(paramMap);
	}

	public MyCamelMap FindAdminInfo(MyMap paramMap)
	{
		return mUserInfoMapper.FindAdminInfo(paramMap);
	}

	public MyCamelMap FindUserInfo(MyMap paramMap)
	{
		return mUserInfoMapper.FindUserInfo(paramMap);
	}

}
