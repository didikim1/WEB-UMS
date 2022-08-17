package com.inbiznetcorp.acs.web.smssender.act;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.result.ResultCode;
import com.inbiznetcorp.acs.framework.result.ResultMessage;
import com.inbiznetcorp.acs.web.smssender.biz.SmsSenderService;

@RequestMapping("/smsSender")
@Controller
public class SmsSenderAct {
	
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SmsSenderAct.class);
	
	@Autowired
	SmsSenderService mService;
	
	@RequestMapping("/directSend")
	@ResponseBody
	public ResultMessage smsSend() {
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		String msg = "Success! :)";
		String code = ResultCode.RESULT_OK;
		
		
		int failCnt =  mService.directSend(paramMap);
		
//		if(failCnt == -1) {
//			msg = "전송대상자 없음;;";
//		}else if(failCnt == -99) {
//			msg = "전송실패... :<";
//		}else if(failCnt > 0) {
//			msg = "전체 중 " + failCnt + "만큼 실패...";
//		}
		return new ResultMessage(code, msg, paramMap);
	}
	
}
