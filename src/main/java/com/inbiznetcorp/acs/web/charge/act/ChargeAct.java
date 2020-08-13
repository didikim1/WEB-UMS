package com.inbiznetcorp.acs.web.charge.act;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/charge")
public class ChargeAct
{
	@RequestMapping(value= {"", "/"})
	public String Charge()
	{
		return "/charge/Charge";
	}
	
	@RequestMapping("/history")
	public String History()
	{
		return "/charge/ChargeHistory";
	}
}
