package com.inbiznetcorp.acs.web.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.websocket.UMSExecutorService;

@Controller
public class IndexAct
{
	IndexService indexService = (IndexService) FrameworkBeans.findBean("com.inbiznetcorp.acs.web.index.IndexService");
	
	@RequestMapping(value= {"", "/"})
	public String index()
	{
		return "redirect:/msg/SendMessage";
//		return "redirect:/Login";
	}

	@RequestMapping("/Login")
	public String Login()
	{
		return "/main/Login";
	}
	
	@RequestMapping("/LoginSubmit")
	public String LoginSubmit(RedirectAttributes rttr)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		MyCamelMap userInfo = indexService.FindUserInfo(paramMap);
		
		if(userInfo == null)
		{
			MyCamelMap adminInfo = indexService.FindAdminInfo(paramMap);
			
			if(adminInfo == null)
			{
				rttr.addFlashAttribute("message", "등록된 정보가 없습니다.");
				return "redirect:/Login";
			}
			else
			{
				FrameworkBeans.findSessionBean().setCompanyName(adminInfo.getStr("userId"));
				FrameworkBeans.findSessionBean().setGrade(adminInfo.getStr("userId"));
				return "redirect:/msg/SendMessage";
			}
		}
		else
		{
			FrameworkBeans.findSessionBean().setCompanyName(userInfo.getStr("userId"));
			return "redirect:/msg/SendMessage";
		}
	}
	
	@RequestMapping("/Logout")
	public String Logout()
	{
		FrameworkBeans.findSessionBean().setCompanyName("");
		FrameworkBeans.findSessionBean().setGrade("");
		return "redirect:/";
	}
	
	@RequestMapping("/ExecutorStop")
	public String ExecutorStop()
	{
		return "/ExecutorStop";
	}
	
	@RequestMapping("/ExecutorStopSubmit")
	public @ResponseBody String ExecutorStopSubmit()
	{
		UMSExecutorService.executorShutdown();
		return "ExecutorStop!!";
	}
}
