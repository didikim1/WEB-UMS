package com.inbiznetcorp.acs.web.main.act;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.result.ResultCode;
import com.inbiznetcorp.acs.framework.result.ResultMessage;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
import com.inbiznetcorp.acs.mapper.main.MainMapper;
import com.inbiznetcorp.acs.web.address.service.AddressService;
import com.inbiznetcorp.acs.web.main.service.MainService;

@Controller
@RequestMapping("/main")
public class MainAct
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MainAct.class);
	
	final String pagePrefix = "main";
	
	@Resource(name="com.inbiznetcorp.acs.web.main.service.MainService")
	MainService mainService;

	@Resource(name="com.inbiznetcorp.acs.web.message.service.AddressService")
	AddressService addressService;

	/**
	 * 회원가입페이지
	 */
	@RequestMapping("/Join")
	public String Join(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		return pagePrefix + "/Join";
	}
	
	/**
	 * 회원가입
	 */
	@RequestMapping("/RegisterUser")
	public String RegisterUser()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		mainService.RegisterData(paramMap);
		
		System.out.println("param : " + paramMap);
		System.out.println("param : " + paramMap);
		System.out.println("param : " + paramMap);
		System.out.println("param : " + paramMap);
		System.out.println("param : " + paramMap);
		System.out.println("param : " + paramMap);
		System.out.println("param : " + paramMap);
		System.out.println("param : " + paramMap);
		
		return pagePrefix + "/RegisterUser";
	}

}
	