package com.inbiznetcorp.acs.web.collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collection")
public class CollectionAct
{
	@RequestMapping(value= {"", "/"})
	public String MessageList()
	{
		return "/collection/MessageList";
	}
	
	@RequestMapping("/SurveyList")
	public String SurveyList()
	{
		return "/collection/SurveyList";
	}
}
