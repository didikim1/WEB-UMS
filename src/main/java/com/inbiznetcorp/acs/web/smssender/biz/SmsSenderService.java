package com.inbiznetcorp.acs.web.smssender.biz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inbiznetcorp.acs.framework.common.ServiceCommon;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.thread.UmsExecutorService;
import com.inbiznetcorp.acs.framework.thread.runnable.SmsSendRunnable;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
import com.inbiznetcorp.acs.mapper.smssender.SmsSenderMapper;
import com.inbiznetcorp.acs.web.servlet.RequestProcJSON;

@Service
public class SmsSenderService {
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SmsSenderService.class);
	private final static String PROJECT_ID = "IBZ_UMS";
	
	@Autowired
	ServiceCommon commonService;
	@Autowired
	SmsSenderMapper mMapper;
	
	
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
		
		String 		sendType 	= paramMap.getStr("sendType");
		String 		dstURL 		= commonService.getAsyncURL();
		String		returnURL	= commonService.getReturnURL();
		String 		phones 		= "";
		String		req_time	= FrameworkUtils.getCurrentDate("yyyyMMddHHmmss");
		String		req_id		= "InbizTest_"+FrameworkUtils.generateSessionID();
		
		JSONObject 	sendMap 	= new JSONObject();
		JSONParser 	jPaser 		= new JSONParser();
		JSONArray 	targetAry 	= null;
		List<MyMap> insertList  = new ArrayList<>();
		
		switch(sendType) {
		case "A" :dstURL += "sms";break;
		case "B" :dstURL += "lms";break;
		case "C" :dstURL += "mms";break;
		}
		
		try {
			targetAry = (JSONArray) jPaser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("targetArr")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		for(Object o1 : targetAry) {
			String phone = ((JSONObject)o1).get("phonenumber").toString();
			phones +=  phone + ",";
		}
		phones = phones.substring(0, phones.length()-1);
		
		sendMap.put("request_id",    	req_id);
		sendMap.put("request_time",		req_time);
		sendMap.put("callback",     	paramMap.getStr("callerID",""));
		sendMap.put("phone",			phones);
		sendMap.put("subject",      	paramMap.getStr("ttsMent0",""));
		sendMap.put("message",      	paramMap.getStr("ttsMent1",""));
		
		sendMap.put("file_id1",      	paramMap.getStr("file_id1",""));
		sendMap.put("file_id2",      	paramMap.getStr("file_id2",""));
		sendMap.put("file_id3",      	paramMap.getStr("file_id3",""));
		
		sendMap.put("return_type",		"ASYNC");
		sendMap.put("return_url",		returnURL);
		
		new RequestProcJSON().sendPacket(dstURL,PROJECT_ID, sendMap.toString());
		
		paramMap.put("project_id", 	PROJECT_ID);
		paramMap.put("req_time", 	req_time);
		paramMap.put("req_id", 		req_id);
		paramMap.put("send_type", 	sendType);
		for(String phone : phones.split("\\,")) {
			paramMap.put("phone", phone);
			mMapper.insertRequest(paramMap);
		}
	}
	
	@SuppressWarnings("unchecked")
	public int smsSend(MyMap paramMap) {
		
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

	public void insertResult(JSONObject jobj) {
		MyMap resultMap  = new MyMap();
//		resultMap.put("project_id",     jobj.get("project_id").toString());
//		resultMap.put("request_id",     jobj.get("request_id").toString());
//		resultMap.put("result",         jobj.get("result").toString());
//		resultMap.put("subject",        jobj.get("subject").toString());
//		resultMap.put("message",        jobj.get("message").toString());
//		resultMap.put("phone",          jobj.get("phone").toString());
//		resultMap.put("callback",       jobj.get("callback").toString());
//		resultMap.put("telecom",        jobj.get("telecom").toString());
//		// 모든 시간을 YYYYMMDDHHmmss
//		resultMap.put("insert_time",    jobj.get("insert_time").toString());
//		resultMap.put("tcprecv_time",   jobj.get("tcprecv_time").toString());
//		resultMap.put("request_time",   jobj.get("request_time").toString());
//		resultMap.put("send_time",      jobj.get("send_time").toString());
//		resultMap.put("report_time",    jobj.get("report_time").toString());
//		resultMap.put("save_time",      jobj.get("save_time").toString());
//		
//		resultMap.put("filecnt",        jobj.get("filecnt").toString());
//		resultMap.put("fileloc1",       jobj.get("fileloc1").toString());
//		resultMap.put("fileloc2",       jobj.get("fileloc2").toString());
//		resultMap.put("fileloc3",       jobj.get("fileloc3").toString());
//		resultMap.put("sender_key",     jobj.get("sender_key").toString());
//		resultMap.put("template_code",  jobj.get("template_code").toString());
//		resultMap.put("replace_text",   jobj.get("replace_text").toString());
//		resultMap.put("kakao_result",   jobj.get("kakao_result").toString());
//		resultMap.put("failover",       jobj.get("failover").toString());
		try {
			resultMap = new ObjectMapper().readValue(jobj.toJSONString(), MyMap.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int cnt = mMapper.insertResult(resultMap);
		System.out.println("DB에 들어갈 Map 정보"+resultMap.toString());
		
		
	}

}
