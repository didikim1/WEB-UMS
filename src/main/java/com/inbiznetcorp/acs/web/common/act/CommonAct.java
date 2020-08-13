package com.inbiznetcorp.acs.web.common.act;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Controller
@RequestMapping("/Common")
public class CommonAct
{
	@RequestMapping("/Confirm")
	public String Confirm(Model model) throws Exception
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		model.addAttribute("type", 		paramMap.getStr("type", ""));
		model.addAttribute("title", 	paramMap.getStr("title", ""));
		model.addAttribute("comment", 	paramMap.getStr("comment", ""));

		return "/Common/Confirm";
	}

	@RequestMapping("/Alert")
	public String Alert(Model model) throws Exception
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		model.addAttribute("title", 	paramMap.getStr("title", ""));
		model.addAttribute("comment", 	paramMap.getStr("comment", ""));

		return "/Common/Alert";
	}
}
