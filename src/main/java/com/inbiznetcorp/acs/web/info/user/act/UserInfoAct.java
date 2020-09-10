package com.inbiznetcorp.acs.web.info.user.act;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.web.info.user.service.UserInfoService;

@Controller
@RequestMapping("/info/user")
public class UserInfoAct
{
	@Resource(name="com.inbiznetcorp.acs.web.info.user.service.UserInfoService")
	UserInfoService mUserInfoService;
	
	/**
	 * 회원 정보 페이지
	 */
	@RequestMapping(value= {"", "/", "/UserInfo"})
	public String UserInfo(Model model)
	{
		MyMap 		paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		MyCamelMap 	userInfo = mUserInfoService.FindUserInfo(paramMap);
		
		model.addAttribute("userInfo", userInfo);
		
		return "/info/user/UserInfo";
	}
	
	/**
	 * 회원 정보 상세 페이지
	 */
	@RequestMapping("/UserInfoDetail")
	public String UserInfoDetail(Model model)
	{
		MyMap 		paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		MyCamelMap 	userInfo = mUserInfoService.FindUserInfo(paramMap);
		
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("paramMap", paramMap);
		
		return "/info/user/UserInfoDetail";
	}
	
	/**
	 * 회원 정보 수정
	 */
	@RequestMapping("/ModifyUserInfo")
	public String ModifyUserInfo()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		mUserInfoService.ModifyUserInfo(paramMap);
		
		return "redirect:/info/user/UserInfo";
	}
	
	/**
	 * 서비스 사용 내역 페이지
	 */
	@RequestMapping("/ServiceHistory")
	public String ServiceHistory()
	{
		return "/info/user/ServiceHistory";
	}
	
	/**
	 * 서비스 사용 요금 페이지
	 */
	@RequestMapping("/ServiceCharge")
	public String ServiceCharge()
	{
		return "/info/user/ServiceCharge";
	}
	
	/**
	 * 서비스 사용 내역 상세 리스트 페이지
	 */
	@RequestMapping("/ServiceHistoryDetail")
	public String ServiceHistoryDetail()
	{
		return "/info/user/ServiceHistoryDetail";
	}
}
