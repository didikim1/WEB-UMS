package com.inbiznetcorp.acs.framework.thread.runnable;

import org.json.simple.JSONObject;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.web.servlet.RequestProcJSON;
import com.inbiznetcorp.acs.web.smssender.biz.SmsSenderService;

public class SmsSendRunnable implements Runnable {
	
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SmsSendRunnable.class);
	
	private String PROJECT_ID = "IBZ_UMS";
	
	private String req_id;
	private String request_time;
	private String callback;
	private String message;
	private String return_type;
	private String dstPhone;
	
	
	
	public SmsSendRunnable(String req_id, String request_time, String callback, String message, String return_type,
			String dstPhone) {
		super();
		this.req_id = req_id;
		this.request_time = request_time;
		this.callback = callback;
		this.message = message;
		this.return_type = return_type;
		this.dstPhone = dstPhone;
	}



	@SuppressWarnings("unchecked")
	@Override
	public void run() {
//		String dstUrl = "http://127.0.0.1:9090/api/async/sender/sms";
//		String dstUrl = "https://api-gemtek.ring2pay.com:48040/api/async/sender/sms";
		String dstUrl = "https://api-gemtek.ring2pay.com:48040/api/sender/sync/sms";
		
		JSONObject 	jObj 		= null;
		
		JSONObject 	sendMap		= new JSONObject();
//		JSONParser 	jPaser		= new JSONParser();
//		JSONArray 	targetAry 	= null;
		
		sendMap.put("request_id",    	this.req_id);
		sendMap.put("request_time",		this.request_time);
		sendMap.put("callback",     	this.callback);
		sendMap.put("message",      	this.message);
		sendMap.put("return_type",		this.return_type);
		sendMap.put("return_type",		this.dstPhone);
		
		JSONObject resultObj = new RequestProcJSON().sendPacket(dstUrl,PROJECT_ID, sendMap.toString());
		
		LOGGER.info("SMS RESULT :: " + resultObj.toJSONString());
		//TODO 결과 디비에 입력 필요!
		SmsSenderService mService = (SmsSenderService) FrameworkBeans.findBean("com.inbiznetcorp.acs.web.smssender.biz.SmsSenderService"); 
		
	}

}
 