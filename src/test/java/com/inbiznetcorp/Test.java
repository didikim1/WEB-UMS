package com.inbiznetcorp;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test {

	public static void main(String[] args) {
		System.out.println("Test Start!!!");
		String str = "{\"project_id\":\"IBZ_UMS\",\"request_id\":\"InbizTest_0e58ca940001p\",\"request_time\":\"20220817210732\",\"phone\":\"01064346371\",\"callback\":\"1877-1875\",\"subject\":\"장인제는.....\",\"message\":\"집에 갈수 있을까요???\",\"filecnt\":\"0\",\"fileloc1\":\"\",\"fileloc2\":\"\",\"fileloc3\":\"\",\"insert_time\":\"\",\"send_time\":\"\",\"report_time\":\"\",\"tcprecv_time\":\"\",\"save_time\":\"\",\"telecom\":\"LG\",\"result\":\"0\",\"kakao_method\":\"\",\"kakao_result\":\"\",\"sender_key\":\"\",\"template_code\":\"\",\"replace_text\":\"\",\"failover\":\"\"}";
		JSONParser jparser = new JSONParser();
		JSONObject jobj = null;
		try {jobj = (JSONObject) jparser.parse(str);} catch (ParseException e) {e.printStackTrace();}
		System.out.println(jobj);

	}

}
