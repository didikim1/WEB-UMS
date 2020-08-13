package com.inbiznetcorp.acs.web.info.user.act;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/info/user")
public class UserInfoAct
{
	/**
	 * 회원 정보 페이지
	 */
	@RequestMapping(value= {"", "/", "/UserInfo"})
	public String UserInfo()
	{
		return "/info/user/UserInfo";
	}
	
	/**
	 * 회원 정보 상세 페이지
	 */
	@RequestMapping("/UserInfoDetail")
	public String UserInfoDetail()
	{
		return "/info/user/UserInfoDetail";
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
