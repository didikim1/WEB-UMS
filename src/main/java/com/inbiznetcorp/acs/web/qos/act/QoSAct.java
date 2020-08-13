package com.inbiznetcorp.acs.web.qos.act;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qos")
public class QoSAct
{
	/**
	 * 고객사별 QoS 설정 페이지
	 */
	@RequestMapping(value= {"", "/"})
	public String QoSList()
	{
		return "/qos/QoSList";
	}
	
	/**
	 * 고객사 신규 등록 및 변경
	 */
	@RequestMapping("RegistAndModify")
	public String RegistAndModify()
	{
		return "/qos/RegistAndModify";
	}
}
