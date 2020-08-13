package com.inbiznetcorp.acs.web.tax;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tax")
public class TaxAct
{
	@RequestMapping(value= {"", "/"})
	public String TaxHistory()
	{
		return "/tax/TaxHistory";
	}
}
