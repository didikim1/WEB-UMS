package com.inbiznetcorp.acs.web.received;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/received")
public class ReceivedAct
{
	@RequestMapping(value= {"", "/"})
	public String ReceivedList()
	{
		return "/received/ReceivedList";
	}
	
}
