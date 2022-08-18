package com.inbiznetcorp.acs.web.collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collection")
public class CollectionAct
{
	@RequestMapping("/VoiceList")
	public String MessageList()
	{
		return "/collection/MessageList";
	}
	
	@RequestMapping("/SMSList")
	public String SMSMessageList()
	{
		return "/collection/SMSMessageList";
	}

	@RequestMapping("/SurveyList")
	public String SurveyList()
	{
		return "/collection/SurveyList";
	}

	@RequestMapping("/fileSearch")
	public String fileSearch()
	{
		return "/collection/fileSearch";
	}
}
