//package com.inbiznetcorp.acs.framework.websocket;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.inbiznetcorp.acs.framework.mymap.MyMap;
//import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
//import com.inbiznetcorp.acs.web.servlet.RequestProc;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//public class IVRSenderTest
//{
//	final static String TTS_DELAY 		=  "<VTML_PAUSE TIME=\"700\" />";
//	final static String TTS_DELAY_500 	=  "<VTML_PAUSE TIME=\"500\" />";
//	final static String TTS_DELAY_300 	=  "<VTML_PAUSE TIME=\"300\" />";
//	final static String TTS_DELAY_100 	=  "<VTML_PAUSE TIME=\"100\" />";
//	final static String TTS_DELAY_10 	=  "<VTML_PAUSE TIME=\"10\" />";
//	
//	final static String TTS_MENT_SUBINTRO 			= "albert/L8/fixedment/098e81670000f";	// TTS_DELAY_500 + "다시 들으시려면 별표를, 종료는 #버튼을 눌러주세요."
//	final static String TTS_MENT_AUTHOK 			= "albert/L8/fixedment/098e81b80000x";	// "감사합니다."
//	final static String TTSMENTEXCEEDLISTENAGAIN 	= "albert/L8/fixedment/098e81fa0000u";	// "다시 듣기 횟수를 초과하여 통화를 종료합니다."
//	final static String TTSMENTAUTHCANCLE 			= "albert/L8/fixedment/098e82460000w";	// "종료합니다."
//	final static String TTSMENTRETRY 				= "albert/L8/fixedment/098e83730000w";	// "다시 들어주시기 바랍니다." + TTS_DELAY_500
//	final static String TTSMENTDTMFLACK 			= "albert/L8/fixedment/098e9db90000f";	// "입력시간을 초과하였습니다." + TTS_DELAY_500 + "다시 들으시려면 별표를, 종료는 #버튼을 눌러주세요."
//	final static String TTSMENTDTMFALLLACK 			= "albert/L8/fixedment/098e9e660000e";	// "아무입력이 없어서 통화를 종료합니다."
//	final static String TTSMENTMISMATCH 			= "albert/L8/fixedment/098e9f520000v";	// "잘못 입력하셨습니다."
//	final static String TTSMENTDTMFALLMISMATCH 		= "albert/L8/fixedment/098e9fd70000s";	// "입력 횟수가 초과되어 통화를 종료합니다."
//	
//	@Test
//	public void RealTimeTTSMake()
//	{
//		String 					url 			= "http://211.61.220.42:8080/RealTimeTTSMake.do";
//		HashMap<String, String> reqMsg 			= new HashMap<>();
//		MyMap 					resultTTSMake 	= new MyMap();
//
//		reqMsg.put("tid", 			FrameworkUtils.generateSessionID());	// 고유값
//		reqMsg.put("groupName", 	"inbiznet" + "/" + currentDate());							// 고객사 명칭
//		reqMsg.put("msg", 			"1번 또는 2번을 눌러주세요.");			// TTS 생성할 문자열
////		reqMsg.put("msg", 			"입력 횟수가 초과되어 통화를 종료합니다.");			// TTS 생성할 문자열
//
//        JSONObject resMsg = new RequestProc().sendPacket(url, reqMsg);
//
//        System.out.println("RealTimeTTSMake Complete!!");
//        System.out.println("RealTimeTTSMake resMsg : " + resMsg);
//        
//        resultTTSMake.put("tid", 			reqMsg.get("tid").toString());
//        resultTTSMake.put("groupName", 		reqMsg.get("groupName").toString());
//        resultTTSMake.put("result", 		resMsg.get("result"));
//        resultTTSMake.put("fileName", 		resMsg.get("fileName"));
//        resultTTSMake.put("filePath", 		resMsg.get("filePath"));
//        resultTTSMake.put("fileUrl", 		resMsg.get("fileUrl"));
//        resultTTSMake.put("message", 		resMsg.get("message"));
//        
//        TTSFileIVRServerPut(resultTTSMake);
//	}
//	
//	public void TTSFileIVRServerPut(MyMap resultTTSMake)
//	{
//		String 					url 			= "http://211.61.220.42:8080/TTSFileIVRServerPut.do";
//		HashMap<String, String> reqMsg 			= new HashMap<>();
//		MyMap 					resultTTSPut 	= new MyMap();
//
//		reqMsg.put("tid", 			resultTTSMake.getStr("tid"));
//		reqMsg.put("groupName", 	resultTTSMake.getStr("groupName"));
//		reqMsg.put("filePath", 		resultTTSMake.getStr("filePath"));
//		reqMsg.put("fileName", 		resultTTSMake.getStr("fileName"));
//
//		JSONObject resMsg = new RequestProc().sendPacket(url, reqMsg);
//
//		System.out.println("TTSFileIVRServerPut Complete!!");
//		System.out.println("TTSFileIVRServerPut resMsg : " + resMsg);
//		
//		resultTTSPut.put("tid", 				resultTTSMake.getStr("tid"));
//		resultTTSPut.put("result", 				resMsg.get("result"));
//		resultTTSPut.put("message", 			resMsg.get("message"));
//		resultTTSPut.put("filePath", 			resMsg.get("filePath"));
//		resultTTSPut.put("groupName", 			resultTTSMake.getStr("groupName"));
//		resultTTSPut.put("fileName", 			resultTTSMake.getStr("fileName"));
//		resultTTSPut.put("fileUrl", 			resultTTSMake.getStr("fileUrl"));
//		
//		
////		wavPlay0001(resultTTSPut);
//		
//		List<MyMap> resultTTSPutList = new ArrayList<>();
//		resultTTSPutList.add(resultTTSPut);
//		resultTTSPutList.add(resultTTSPut);
//		resultTTSPutList.add(resultTTSPut);
//		wavPlay0002(resultTTSPutList);
//	}
//	
//	public void wavPlay0001(MyMap resultTTSPut)
//	{
//		String 					url 			= "http://211.61.220.42:42102/arsauth/wavplay/Req0001.do";
//		HashMap<String, String> reqMsg 			= new HashMap<>();
//
//		reqMsg.put("arsCommandNumber", 			"L8001");
//		reqMsg.put("authReqNumber", 			"InbizTest;;"+resultTTSPut.getStr("tid"));
//		reqMsg.put("requestTime",        		currentTime());
//		reqMsg.put("companyCode", 				"80001");
//		reqMsg.put("serviceCode", 				"0001");
//		reqMsg.put("serviceType", 				"02");
//		reqMsg.put("callType", 					"02");
//		reqMsg.put("phoneNumber", 				"01071557901");
//		
//		HashMap<String, String> forTTS = new HashMap<>();
//		forTTS.put("TTSMENTINTRO", resultTTSPut.getStr("filePath").split(".wav")[0]);
//		
//		reqMsg.put("ttsMentDynamic", 			forTTS0001(forTTS));
//
//		System.out.println("wavPlay0001 reqMsg : " + reqMsg);
//		
//		JSONObject resMsg = new RequestProc().sendPacket(url, reqMsg);
//
//		System.out.println("wavPlay0001 Complete!!");
//		System.out.println("wavPlay0001 resMsg : " + resMsg);
//	}
//	
//	public void wavPlay0002(List<MyMap> resultTTSPutList)
//	{
//		MyMap 					resultTTSPlay 	= new MyMap();
//		String 					url 			= "http://211.61.220.42:42102/arsauth/wavplay/Req0002.do";
//		HashMap<String, String> reqMsg 			= new HashMap<>();
//
//		reqMsg.put("arsCommandNumber",  			"L8002");
//		reqMsg.put("authReqNumber",     			"InbizTest;;"+resultTTSPutList.get(0).getStr("tid"));
//		reqMsg.put("requestTime",       			currentTime());
//		reqMsg.put("companyCode",       			"80001");
//		reqMsg.put("serviceCode",       			"0001");
//		reqMsg.put("serviceType",       			"03");
//		reqMsg.put("callType",          			"02");
//		reqMsg.put("phoneNumber",					"01071557901");
//		reqMsg.put("arryUserPassword",				forPassword(resultTTSPutList));
//		reqMsg.put("ttsMentDynamic",				forTTS0002(resultTTSPutList));
//		
//		System.out.println("wavPlay0002 reqMsg : " + reqMsg);
//
//		JSONObject resMsg = new RequestProc().sendPacket(url, reqMsg);
//		
//		System.out.println("wavPlay0002 Complete!!");
//		System.out.println("wavPlay0002 resMsg : " + resMsg);
//
//		resultTTSPlay.put("phonenumber", 	"01071557901");
//		resultTTSPlay.put("requestTime", 	resMsg.get("requestTime"));
//		resultTTSPlay.put("callStartTime", 	resMsg.get("callStartTime"));
//		resultTTSPlay.put("callEndTime", 	resMsg.get("callEndTime"));
//		resultTTSPlay.put("callduration", 	resMsg.get("callduration"));
//		resultTTSPlay.put("result", 		resMsg.get("result"));
//		resultTTSPlay.put("message", 		resMsg.get("message"));
//		resultTTSPlay.put("authReqNumber", 	resMsg.get("authReqNumber"));
//		resultTTSPlay.put("recordFilePath", resMsg.get("recordFilePath"));
////		resultTTSPlay.put("wavDirPath",     resMsg.get("ArsServerWavDir"));
////		resultTTSPlay.put("wavFileName",    resMsg.get("ArsServerWavName"));
////		resultTTSPlay.put("fileUrl",    	resMsg.get("fileUrl"));
//
//		String userDTMF 	= resMsg.get("userInputValue").toString();
//		String resultDTMF 	= "";
//		for(int i=0; i<userDTMF.length(); i++)
//		{
//			char c = userDTMF.charAt(i);
//			if(47 < c && c < 58)
//			{
//				resultDTMF += c;
//				resultDTMF += ",";
//			}
//		}
//		resultDTMF = resultDTMF.substring(0, resultDTMF.length()-1);
//		resultTTSPlay.put("userInputValue", resultDTMF);
//		
//		System.out.println("resultTTSPlay : " + resultTTSPlay);
//
//	}
//	
//	public static String currentTime()
//    {
//        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date();
//        return dateFormat.format(date);
//    }
//	
//	public static String currentDate()
//	{
//		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//		Date date = new Date();
//		return dateFormat.format(date);
//	}
//	
//	public static String forPassword(List<MyMap> resultTTSPutList)
//    {
//		int 	length 		= resultTTSPutList.size();
//    	String 	rtnValue 	= "";
//    	String 	password 	= "1,2,3,4,5,6,7,8,9";
//    	String 	separator 	= "^";
//
//    	for(int i=0; i<length; i++)
//    	{
//    		rtnValue += password;
//    		rtnValue += (i != length-1) ? separator : "";
//    	}
//
//    	return rtnValue;
//    }
//	
//	@SuppressWarnings("unchecked")
//	public String forTTS0001(HashMap<String, String> forTTS)
//    {
//        JSONObject      jsonMainTTS    = new JSONObject();
//        JSONArray       jsonSetepArray = new JSONArray();
//        JSONObject      jsonStep01TTS  = new JSONObject();
//
////        jsonStep01TTS.put("TTSMENTINTRO", 	 	 		 "albert/L8002/Sample/1ST_TTSMENTINTRO");		// 안내멘트
////        jsonStep01TTS.put("TTSMENTSUBINTRO", 	 		 "albert/L8002/Sample/1ST_TTSMENTSUBINTRO");	// 안내 후 서브 멘트
////        jsonStep01TTS.put("TTSMENTRETRY", 	 	 		 "albert/L8002/Sample/1ST_TTSMENTRETRY");		// 다시듣기
////        jsonStep01TTS.put("TTSMENTDTMFLACK", 	 	 	 "albert/L8002/Sample/1ST_TTSMENTDTMFLACK");		// 입력값부족
////        jsonStep01TTS.put("TTSMENTDTMFALLLACK", 	 	 "albert/L8002/Sample/1ST_TTSMENTDTMFALLLACK");		// 입력값부족(횟수초과)
////        jsonStep01TTS.put("TTSMENTMISMATCH", 	 	 	 "albert/L8002/Sample/1ST_TTSMENTMISMATCH");		// 입력값잘못입력시
////        jsonStep01TTS.put("TTSMENTDTMFALLMISMATCH", 	 "albert/L8002/Sample/1ST_TTSMENTDTMFALLMISMATCH");   	// 입력값잘못입력시(횟수초과)
////        jsonStep01TTS.put("TTSMENTAUTHCANCLE", 	 	 	 "albert/L8002/Sample/1ST_TTSMENTAUTHCANCLE");   	// 인증취소
////        jsonStep01TTS.put("TTSMENTEXCEEDLISTENAGAIN", 	 "albert/L8002/Sample/1ST_TTSMENTEXCEEDLISTENAGAIN");   	// 다시듣기 횟수초과
////        jsonStep01TTS.put("TTSMENTAUTHOK", 	 		 	 "albert/L8002/Sample/1ST_TTSMENTAUTHOK");   	// 성공
//        
//        jsonStep01TTS.put("TTSMENTINTRO", 	 	 		 forTTS.get("TTSMENTINTRO"));	// 안내멘트
//        jsonStep01TTS.put("TTSMENTSUBINTRO", 	 		 TTS_MENT_SUBINTRO);			// 안내 후 서브 멘트
//        jsonStep01TTS.put("TTSMENTRETRY", 	 	 		 TTSMENTRETRY);					// 다시듣기
//        jsonStep01TTS.put("TTSMENTDTMFLACK", 	 	 	 TTSMENTDTMFLACK);				// 입력값부족
//        jsonStep01TTS.put("TTSMENTDTMFALLLACK", 	 	 TTSMENTDTMFALLLACK);			// 입력값부족(횟수초과)
//        jsonStep01TTS.put("TTSMENTMISMATCH", 	 	 	 TTSMENTMISMATCH);				// 입력값잘못입력시
//        jsonStep01TTS.put("TTSMENTDTMFALLMISMATCH", 	 TTSMENTDTMFALLMISMATCH);   	// 입력값잘못입력시(횟수초과)
//        jsonStep01TTS.put("TTSMENTAUTHCANCLE", 	 	 	 TTSMENTAUTHCANCLE);   			// 인증취소
//        jsonStep01TTS.put("TTSMENTEXCEEDLISTENAGAIN", 	 TTSMENTEXCEEDLISTENAGAIN);   	// 다시듣기 횟수초과
//        jsonStep01TTS.put("TTSMENTAUTHOK", 	 		 	 TTS_MENT_AUTHOK);   			// 성공
//        
//
//        jsonSetepArray.add(jsonStep01TTS);
//
//        jsonMainTTS.put("TTS_MENT_DYNAMIC", jsonSetepArray);
//
//        return jsonMainTTS.toString();
//   }
//	
//	@SuppressWarnings("unchecked")
//	public static String forTTS0002(List<MyMap> resultTTSPutList)
//    {
//         JSONObject      jsonMainTTS  	= new JSONObject();
//         JSONArray       jsonSetepArray = new JSONArray();
//         
//         int length = resultTTSPutList.size();
//         
//         for(int i=0; i<length; i++)
//         {
//        	 JSONObject jsonStepTTS = new JSONObject();
//        	 
////        	 jsonStepTTS.put("TTSMENTINTRO", 	 	 		"albert/L8002/Sample/1ST_TTSMENTINTRO");		// 안내멘트
////             jsonStepTTS.put("TTSMENTSUBINTRO", 	 		"albert/L8002/Sample/1ST_TTSMENTSUBINTRO");	// 안내 후 서브 멘트
////             jsonStepTTS.put("TTSMENTRETRY", 	 	 		"albert/L8002/Sample/1ST_TTSMENTRETRY");		// 다시듣기
////             jsonStepTTS.put("TTSMENTDTMFLACK", 	 	 	"albert/L8002/Sample/1ST_TTSMENTDTMFLACK");		// 입력값부족
////             jsonStepTTS.put("TTSMENTDTMFALLLACK", 	 	 	"albert/L8002/Sample/1ST_TTSMENTDTMFALLLACK");		// 입력값부족(횟수초과)
////             jsonStepTTS.put("TTSMENTMISMATCH", 	 	 	"albert/L8002/Sample/1ST_TTSMENTMISMATCH");		// 입력값잘못입력시
////             jsonStepTTS.put("TTSMENTDTMFALLMISMATCH", 	 	"albert/L8002/Sample/1ST_TTSMENTDTMFALLMISMATCH");   	// 입력값잘못입력시(횟수초과)
////             jsonStepTTS.put("TTSMENTAUTHCANCLE", 	 	 	"albert/L8002/Sample/1ST_TTSMENTAUTHCANCLE");   	// 인증취소
////             jsonStepTTS.put("TTSMENTEXCEEDLISTENAGAIN", 	"albert/L8002/Sample/1ST_TTSMENTEXCEEDLISTENAGAIN");   	// 다시듣기 횟수초과
////             jsonStepTTS.put("TTSMENTAUTHOK", 	 		 	"albert/L8002/Sample/1ST_TTSMENTAUTHOK");   	// 성공
//        	 
//        	 jsonStepTTS.put("TTSMENTINTRO", 	 	 	 resultTTSPutList.get(i).getStr("filePath").split(".wav")[0]);	// 안내멘트
//             jsonStepTTS.put("TTSMENTSUBINTRO", 	 	 TTS_MENT_SUBINTRO);											// 안내 후 서브 멘트
//             jsonStepTTS.put("TTSMENTRETRY", 	 	 	 TTSMENTRETRY);													// 다시듣기
//             jsonStepTTS.put("TTSMENTDTMFLACK", 	 	 TTSMENTDTMFLACK);												// 입력값부족
//             jsonStepTTS.put("TTSMENTDTMFALLLACK", 	 	 TTSMENTDTMFALLLACK);											// 입력값부족(횟수초과)
//             jsonStepTTS.put("TTSMENTMISMATCH", 	 	 TTSMENTMISMATCH);												// 입력값잘못입력시
//             jsonStepTTS.put("TTSMENTDTMFALLMISMATCH", 	 TTSMENTDTMFALLMISMATCH); 										// 입력값잘못입력시(횟수초과)
//             jsonStepTTS.put("TTSMENTAUTHCANCLE", 	 	 TTSMENTAUTHCANCLE);   											// 인증취소
//             jsonStepTTS.put("TTSMENTEXCEEDLISTENAGAIN", TTSMENTEXCEEDLISTENAGAIN); 									// 다시듣기 횟수초과
//             jsonStepTTS.put("TTSMENTAUTHOK", 	 		 TTS_MENT_AUTHOK);   											// 성공
//             
//             jsonSetepArray.add(jsonStepTTS);
//         }
//
//         jsonMainTTS.put("TTS_MENT_DYNAMIC", jsonSetepArray);
//
//         return jsonMainTTS.toString();
//    }
//}
