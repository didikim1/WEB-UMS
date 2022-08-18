package com.inbiznetcorp.acs.web.smssender.act;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		mService.sendASYNC(paramMap);
		
//		int failCnt =  mService.directSend(paramMap);
//		if(failCnt == -1) {
//			msg = "전송대상자 없음;;";
//		}else if(failCnt == -99) {
//			msg = "전송실패... :<";
//		}else if(failCnt > 0) {
//			msg = "전체 중 " + failCnt + "만큼 실패...";
//		}
		return new ResultMessage(code, msg, paramMap);
	}
	
	@PostMapping(value = "/fin", consumes = {"application/json" })
	public ResponseEntity<String> returnResult(@RequestBody String requestBody, HttpServletRequest request) {
		HttpHeaders resHeaders = new HttpHeaders();
	    resHeaders.add("Content-Type", "application/json");
		LOGGER.info("SendResult Recived :: "+ requestBody);
		
		MyMap resultMap = new MyMap();
		JSONParser jparser = new JSONParser();
		JSONObject jobj = null;
		try {jobj = (JSONObject) jparser.parse(requestBody);} catch (ParseException e) {e.printStackTrace();}
		
		mService.insertResult(jobj);
		
		
		return new ResponseEntity<String>(new JSONObject().toString(), resHeaders, HttpStatus.OK) ;
	}
	
	
}
