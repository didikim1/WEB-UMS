package com.inbiznetcorp.acs.web.server.act;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.web.server.service.ServerService;

@Controller
@RequestMapping("/setting/server")
public class ServerAct
{
	@Resource(name="com.inbiznetcorp.acs.web.server.service.ServerService")
	ServerService serverService;

	@RequestMapping("/ServerList")
	public String ServerList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		BasicBean result = serverService.ListPagingData(paramMap);

		model.addAttribute("searchType", 		paramMap.getStr("searchType", "IVRSERVER_NAME"));
		model.addAttribute("searchWord", 		paramMap.getStr("searchWord", ""));
		model.addAttribute("sidx", 				paramMap.getStr("sidx", "ROW_NUM"));
		model.addAttribute("sord", 				paramMap.getStr("sord", "DESC"));
		model.addAttribute("serverList", 		result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());

		return "/setting/server/ServerList";
	}

	@RequestMapping("/RegistAndModify")
	public String RegistAndModify(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		model.addAttribute("isRegist", paramMap.getStr("isRegist"));

		return "/setting/server/RegistAndModify";
	}
}
