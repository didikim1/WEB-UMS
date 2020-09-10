package com.inbiznetcorp.acs.web.info.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.mapper.userinfo.UserInfoMapper;

@Service("com.inbiznetcorp.acs.web.info.user.service.UserInfoService")
public class UserInfoService
{
	@Resource(name="com.inbiznetcorp.acs.mapper.userinfo.UserInfoMapper")
	UserInfoMapper mUserInfoMapper;

	public MyCamelMap FindUserInfo(MyMap paramMap)
	{
		String grade = paramMap.getStr("SESSION_GRADE"); 	// A:관리자, B:사용자
		
		return grade.equals("A") ? mUserInfoMapper.FindAdminInfo(paramMap) : mUserInfoMapper.FindUserInfo(paramMap);
	}

	public int ModifyUserInfo(MyMap paramMap)
	{
		String grade 		= paramMap.getStr("SESSION_GRADE"); 	// A:관리자, B:사용자
		String userPw 		= paramMap.getStr("password1", "");
		String userName 	= paramMap.getStr("userName");
		String tel 			= paramMap.getStr("tel1") + paramMap.getStr("tel2") + paramMap.getStr("tel3");
		String phonenumber 	= paramMap.getStr("phone1") + paramMap.getStr("phone2") + paramMap.getStr("phone3");
		String email 		= paramMap.getStr("email1") + "@" + paramMap.getStr("email2");
		String eventAgree 	= paramMap.getStr("eventAgree", "N");
		
		if(!userPw.equals("")) { paramMap.put("userPw", userPw); }
		
		paramMap.put("userName", 	userName);
		paramMap.put("tel", 		tel);
		paramMap.put("phonenumber", phonenumber);
		paramMap.put("email", 		email);
		paramMap.put("eventAgree", 	eventAgree);
		
		return grade.equals("A") ? mUserInfoMapper.ModifyAdminInfo(paramMap) : mUserInfoMapper.ModifyUserInfo(paramMap);
	}

}
