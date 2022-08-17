package com.inbiznetcorp.acs.web.smssender.biz;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.thread.UmsExecutorService;
import com.inbiznetcorp.acs.framework.thread.runnable.SmsSendRunnable;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
import com.inbiznetcorp.acs.web.servlet.RequestProcJSON;

@Service
public class SmsSenderService {
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SmsSenderService.class);
	private final static String PROJECT_ID = "IBZ_UMS";
	
	public int directSend(MyMap paramMap) {
		String sendType = paramMap.getStr("sendType");
//		JSONObject resultJSON =null;
		int failCnt =0;
		switch(sendType) {
		case "A" :
			failCnt = smsSend(paramMap);
			break;
		case "B" :
			failCnt = lmsSend(paramMap);
			break;
		case "C" :
			failCnt = mmsSend(paramMap);
			break;
		}
		return failCnt;
	}
	
	@SuppressWarnings("unchecked")
	public void sendASYNC(MyMap paramMap) {
		String dst = "https://api-gemtek.ring2pay.com:48040/api/async/sender/sms";
		
		JSONObject sendMap = new JSONObject();
		JSONParser jPaser = new JSONParser();
		JSONArray targetAry = null;
		try {
			targetAry = (JSONArray) jPaser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("targetArr")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String phones = "";
		for(Object o1 : targetAry) {
			JSONObject jObj = (JSONObject)o1;
			sendMap.put("phone",      		jObj.get("phonenumber"));
			phones += jObj.get("phonenumber").toString();
		}
		JSONObject resultObj = new RequestProcJSON().sendPacket(dst,PROJECT_ID, sendMap.toString());
	}
	
	@SuppressWarnings("unchecked")
	public int smsSend(MyMap paramMap) {
		
//		String dst = "http://127.0.0.1:9090/api/async/sender/sms";
//		String dst = "https://api-gemtek.ring2pay.com:48040/api/async/sender/sms";
		String dst = "https://api-gemtek.ring2pay.com:48040/api/sender/sync/sms";
		
		int failCnt =0;
		
		JSONObject sendMap = new JSONObject();
		JSONParser jPaser = new JSONParser();
		JSONArray targetAry = null;
		
		try {
			targetAry = (JSONArray) jPaser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("targetArr")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int totalCnt = targetAry.size(); 
		if(totalCnt < 1) return -1;
		
		LOGGER.info("SMS전송 요청 :: url > " + dst );
		
//		sendMap.put("request_id",    	"InbizTest_"+FrameworkUtils.generateSessionID());
//		sendMap.put("request_time",		FrameworkUtils.getCurrentDate("yyyyMMddHHmmss"));
//		sendMap.put("callback",     	paramMap.getStr("callerID"));
//		sendMap.put("message",      	paramMap.getStr("ttsMent1"));
//		sendMap.put("return_type",		"ASYNC");
		
		String req_id 	= "InbizTest_"+FrameworkUtils.generateSessionID();
		String req_time = FrameworkUtils.getCurrentDate("yyyyMMddHHmmss");
		String callback = paramMap.getStr("callerID");
		String msg		= paramMap.getStr("ttsMent1");
		String r_type	= "ASYNC";
				
		
		for(Object o1 : targetAry) {
			JSONObject jObj = (JSONObject)o1;
			sendMap.put("phone",      		jObj.get("phonenumber"));
			String phone = jObj.get("phonenumber").toString();
//			JSONObject resultObj = new RequestProcJSON().sendPacket(dst,PROJECT_ID, sendMap.toString());
			UmsExecutorService.addCallRunable(new SmsSendRunnable(req_id, req_time, callback, msg, r_type, phone));
		}
		if((totalCnt - failCnt) == 0)failCnt = -99;
		return failCnt;
	}
	
	
	@SuppressWarnings("unchecked")
	public int lmsSend(MyMap paramMap) {
		
//		String dst = "http://127.0.0.1:9090/api/async/sender/lms";
//		String dst = "https://api-gemtek.ring2pay.com:48040/api/async/sender/lms";
		String dst = "https://api-gemtek.ring2pay.com:48040/api/sender/sync/lms";
		
		int failCnt = 0;
		
		JSONObject sendMap = new JSONObject();
		JSONParser jPaser = new JSONParser();
		JSONArray targetAry = null;
		
		try {
			targetAry = (JSONArray) jPaser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("targetArr")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int totalCnt = targetAry.size(); 
		if(totalCnt < 1) return -1;
		
		LOGGER.info("LMS전송 요청 :: url > " + dst );
		sendMap.put("request_id",    	"InbizTest_"+FrameworkUtils.generateSessionID());
		sendMap.put("request_time",		FrameworkUtils.getCurrentDate("yyyyMMddHHmmss"));
		sendMap.put("callback",     	paramMap.getStr("callerID"));
		sendMap.put("subject",      	paramMap.getStr("ttsMent0"));
		sendMap.put("message",      	paramMap.getStr("ttsMent1"));
		sendMap.put("return_type",		"ASYNC");
		for(Object o1 : targetAry) {
			JSONObject jObj = (JSONObject)o1;
			sendMap.put("phone",      		jObj.get("phonenumber"));
			JSONObject resultObj = new RequestProcJSON().sendPacket(dst,PROJECT_ID, sendMap.toString());
			System.out.println(resultObj);
			//TODO 결과 디비에 입력 필요!
			
			if(!resultObj.get("code").equals("200"))failCnt++;
		}
		if((totalCnt - failCnt) == 0)failCnt = -99;
		return failCnt;
	}
	
	
	private int mmsSend(MyMap paramMap) {
		// TODO Auto-generated method stub
		String dst = "https://api-gemtek.ring2pay.com:48040/api/sender/sync/mms";
		int failCnt =0;
		return failCnt;
	}
}
