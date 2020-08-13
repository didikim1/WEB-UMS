package com.inbiznetcorp.acs.web.cps.act;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.result.ResultMessage;
import com.inbiznetcorp.acs.web.cps.service.CPSService;


@Controller
@RequestMapping("/cps")
public class CPSAct
{
	@Resource(name="com.inbiznetcorp.acs.web.cps.service.CPSService")
	CPSService cpsService;
	
	/**
	 * IVR 서버 List 페이지
	 */
	@RequestMapping(value= {"", "/"})
	public String IVRList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		BasicBean result = cpsService.SelectIVRServerList(paramMap);
		
		model.addAttribute("paramMap", 			paramMap);
		model.addAttribute("serverList", 		result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());
		
		return "/cps/IVRList";
	}
	
	/**
	 * IVR 서버 신규 등록, 수정 페이지
	 */
	@RequestMapping("/RegistAndModify")
	public String RegistAndModify(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
//		MyCamelMap 	server 		= null;
		String 		isRegist 	= paramMap.getStr("isRegist", "Y");
//		if(isRegist.equals("N"))
//		{
//			server = cpsService.SelectIVRServer(paramMap);
//			model.addAttribute("server", server);
//		}
		
		model.addAttribute("isRegist", isRegist);
		
		return "/cps/RegistAndModify";
	}
	
	/**
	 * IVR 서버 일괄 수정
	 */
	@RequestMapping("/Modify")
	public @ResponseBody ResultMessage IVRServerModify()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		cpsService.IVRServerModify(paramMap);
		
		return new ResultMessage("200", "success!!");
	}
	
	/**
	 * IVR 서버 신규 등록
	 */
	@RequestMapping("/InsertIVRServer")
	public @ResponseBody ResultMessage InsertIVRServer()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		cpsService.InsertIVRServer(paramMap);
		
		return new ResultMessage("200", "success!!");
	}
	
	/**
	 * IVR 서버 변경
	 */
	@RequestMapping("/UpdateIVRServer")
	public @ResponseBody ResultMessage UpdateIVRServer()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		cpsService.UpdateIVRServer(paramMap);
		
		return new ResultMessage("200", "success!!");
	}
}
