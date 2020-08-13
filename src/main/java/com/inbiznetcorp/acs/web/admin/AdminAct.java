package com.inbiznetcorp.acs.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminAct
{
	@RequestMapping(value= {"", "/"})
	public String CheckPW()
	{
		return "/admin/CheckPW";
	}
	
	@RequestMapping("/tax/history")
	public String TaxHistory()
	{
		return "/admin/TaxHistory";
	}
}
