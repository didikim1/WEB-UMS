package com.inbiznetcorp.acs.framework.websocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.web.ivr.service.IVRService;
import com.inbiznetcorp.acs.web.servlet.RequestProc;

@Component("com.inbiznetcorp.acs.framework.websocket.IVRSender")
public class IVRSender
{
	final static String TTS_DELAY 		=  "<VTML_PAUSE TIME=\"700\" />";
	final static String TTS_DELAY_500 	=  "<VTML_PAUSE TIME=\"500\" />";
	final static String TTS_DELAY_300 	=  "<VTML_PAUSE TIME=\"300\" />";
	final static String TTS_DELAY_100 	=  "<VTML_PAUSE TIME=\"100\" />";
	final static String TTS_DELAY_10 	=  "<VTML_PAUSE TIME=\"10\" />";
	
	// 파일 경로 : /var/lib/asterisk/sounds/albert/L8/fixedment/
	final static String TTS_MENT_SUBINTRO 			= "albert/L8/fixedment/TTS_MENT_SUBINTRO";			// TTS_DELAY_500 + "다시 들으시려면 별표를, 종료는 #버튼을 눌러주세요."
	final static String TTS_MENT_AUTHOK 			= "albert/L8/fixedment/TTS_MENT_AUTHOK";			// "감사합니다."
	final static String TTS_MENT_EXCEEDLISTENAGAIN 	= "albert/L8/fixedment/TTS_MENT_EXCEEDLISTENAGAIN";	// "다시 듣기 횟수를 초과하여 통화를 종료합니다."
	final static String TTS_MENT_AUTHCANCLE 		= "albert/L8/fixedment/TTS_MENT_AUTHCANCLE";		// "종료합니다."
	final static String TTS_MENT_RETRY 				= "albert/L8/fixedment/TTS_MENT_RETRY";				// "다시 들어주시기 바랍니다." + TTS_DELAY_500
	final static String TTS_MENT_DTMFLACK 			= "albert/L8/fixedment/TTS_MENT_DTMFLACK";			// "입력시간을 초과하였습니다." + TTS_DELAY_500 + "다시 들으시려면 별표를, 종료는 #버튼을 눌러주세요."
	final static String TTS_MENT_DTMFALLLACK 		= "albert/L8/fixedment/TTS_MENT_DTMFALLLACK";		// "아무입력이 없어서 통화를 종료합니다."
	final static String TTS_MENT_MISMATCH 			= "albert/L8/fixedment/TTS_MENT_MISMATCH";			// "잘못 입력하셨습니다."
	final static String TTS_MENT_DTMFALLMISMATCH 	= "albert/L8/fixedment/TTS_MENT_DTMFALLMISMATCH";	// "입력 횟수가 초과되어 통화를 종료합니다."
	
	
	// IVR_URL을 바꾸면 SendMessageScript.jsp에서 WebSocket의 port도 맞게 변경해야한다.(배포시)
//	final static String IVR_URL = "http://211.61.220.42";
//	final static String IVR_URL = "http://211.61.220.54";
	final static String IVR_URL = "http://211.61.220.53";
	
	@Resource(name="com.inbiznetcorp.acs.web.ivr.service.IVRService")
	IVRService mIVRService;
	
	/**
	 * text를 wav파일로 만듦.
	 */
	public MyMap RealTimeTTSMake(String tid, String companyName, String msg)
	{
		LOGGER.info("RealTimeTTSMake..");
		LOGGER.info("RealTimeTTSMake_tid : " + tid);
		
		String 					url 			= IVR_URL + ":8080/RealTimeTTSMake.do";
		HashMap<String, String> reqMsg 			= new HashMap<>();
		MyMap 					resultTTSMake 	= new MyMap();

		reqMsg.put("tid", 			tid);				// 고유값
		reqMsg.put("groupName", 	companyName);		// 고객사 명칭
		reqMsg.put("msg", 			msg);				// TTS 생성할 문자열

		LOGGER.info("RealTimeTTSMake reqMsg : " + reqMsg);
		
        JSONObject resMsg = new RequestProc().sendPacket(url, reqMsg);

        LOGGER.info("RealTimeTTSMake Complete!!");
        LOGGER.info("RealTimeTTSMake resMsg : " + resMsg);
        
        resultTTSMake.put("tid", 			reqMsg.get("tid").toString());
        resultTTSMake.put("groupName", 		reqMsg.get("groupName").toString());
        resultTTSMake.put("result", 		resMsg.get("result"));
        resultTTSMake.put("fileName", 		resMsg.get("fileName"));
        resultTTSMake.put("filePath", 		resMsg.get("filePath"));
        resultTTSMake.put("fileUrl", 		resMsg.get("fileUrl"));
        resultTTSMake.put("message", 		resMsg.get("message"));
        resultTTSMake.put("ttsMent", 		reqMsg.get("msg"));

        mIVRService.RegistMakeTTSHistory(resultTTSMake);
        LOGGER.info("InsertMakeTTSHistory Complete!!");
        LOGGER.info("resultTTSMake : " + resultTTSMake);

        return resultTTSMake;
	}
	
	/**
	 * 사용자가 upload한 wav파일을 IVR서버에 전송
	 */
	public MyMap WAVFileUpload(String filePath, String tid, String groupName)
	{
		MyMap resultWavUpload = new MyMap();
		
		String url = IVR_URL + ":8080/WavFileUploadServlet.do";
		
		HttpClient  	client	 		= new DefaultHttpClient();
		HttpPost 		method 			= new HttpPost( url );
		BufferedReader  bufferedReader 	= null;
		StringBuffer 	returnData 		= new StringBuffer();
		try
		{
			FileBody file = new FileBody(new File(filePath));

			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"));

			reqEntity.addPart("tid", 		 new StringBody( tid ) );
			reqEntity.addPart("groupName", 	 new StringBody( groupName ) );
			reqEntity.addPart("uploadFile",  file);

			method.setEntity(reqEntity);

			HttpResponse response = client.execute(method);

			if (response.getStatusLine().getStatusCode() == 200)
			{
				bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				String line = "";
	            while ((line = bufferedReader.readLine()) != null)
	            {
	                returnData.append(line);
	            }
	            bufferedReader.close();
			}
			
			JSONParser parser 			= new JSONParser();
			JSONObject returnJSONObject = (JSONObject) parser.parse(returnData.toString());
			JSONObject uploadInfo 		= (JSONObject) ((JSONArray) returnJSONObject.get("uploadInfo")).get(0);
			
			resultWavUpload.put("tid", 			returnJSONObject.get("tid"));
			resultWavUpload.put("groupName", 	returnJSONObject.get("groupName"));
			resultWavUpload.put("filePath", 	uploadInfo.get("path"));
			resultWavUpload.put("fileName", 	uploadInfo.get("name"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return resultWavUpload;
	}

	/**
	 * wav파일을 IVR server로 put.
	 */
	public MyMap TTSFileIVRServerPut(MyMap resultTTSMake)
	{
		LOGGER.info("TTSFileIVRServerPut..");
		LOGGER.info("TTSFileIVRServerPut_tid : " + resultTTSMake.getStr("tid"));
		
		String 					url 			= IVR_URL + ":8080/TTSFileIVRServerPut.do";
		HashMap<String, String> reqMsg 			= new HashMap<>();
		MyMap 					resultTTSPut 	= new MyMap();

		reqMsg.put("tid", 			resultTTSMake.getStr("tid"));
		reqMsg.put("groupName", 	resultTTSMake.getStr("groupName"));
		reqMsg.put("filePath", 		resultTTSMake.getStr("filePath"));
		reqMsg.put("fileName", 		resultTTSMake.getStr("fileName"));

		LOGGER.info("TTSFileIVRServerPut reqMsg : " + reqMsg);
		
		JSONObject resMsg = new RequestProc().sendPacket(url, reqMsg);

		LOGGER.info("TTSFileIVRServerPut Complete!!");
        LOGGER.info("TTSFileIVRServerPut resMsg : " + resMsg);
        
		resultTTSPut.put("tid", 				resultTTSMake.getStr("tid"));
		resultTTSPut.put("result", 				resMsg.get("result"));
		resultTTSPut.put("message", 			resMsg.get("message"));
		resultTTSPut.put("filePath", 			resMsg.get("filePath"));
		resultTTSPut.put("groupName", 			resultTTSMake.getStr("groupName"));
		resultTTSPut.put("fileName", 			resultTTSMake.getStr("fileName"));
		resultTTSPut.put("fileUrl", 			resultTTSMake.getStr("fileUrl"));
		resultTTSPut.put("ivrlogseq", 			resultTTSMake.getInt("ivrlogseq", 0));
		resultTTSPut.put("ivrlogmapperseq", 	resultTTSMake.getInt("ivrlogmapperseq", 0));
		resultTTSPut.put("orderNum", 			resultTTSMake.getInt("orderNum", 1));

		mIVRService.RegistTTSFilePutHistory(resultTTSPut);
		LOGGER.info("InsertTTSFilePutHistory Complete!!");
		LOGGER.info("resultTTSPut : " + resultTTSPut);

		return resultTTSPut;
	}
	
	/**
	 * 만들어진 wav파일을 이용하여 콜을 보냄.(알림형)
	 */
	public MyMap wavPlay0001(MyMap resultTTSPut, String phonenumber, String type, String callerID, String requstNumber)
	{
		LOGGER.info("resultTTSPut.. : " + resultTTSPut);
		LOGGER.info("wavPlay0001..");
		LOGGER.info("wavPlay0001_tid : " + resultTTSPut.getStr("tid", ""));
		
		String port = ((phonenumber.charAt(phonenumber.length()-1) % 2) == 0) ? "42103" : "42102";
		
		LOGGER.info("IVR_SERVER : " + IVR_URL);
		LOGGER.info("IVR_PORT : " + port);
		
		MyMap 					resultTTSPlay 	= new MyMap();
		String 					url 			= IVR_URL + ":" + port + "/arsauth/wavplay/Req0001.do";
		HashMap<String, String> reqMsg 			= new HashMap<>();

//		reqMsg.put("arsCommandNumber", 			"L8001");
		reqMsg.put("arsCommandNumber", 			"L8003");
		reqMsg.put("authReqNumber", 			"InbizTest;;"+requstNumber);
		reqMsg.put("requestTime",        		currentTime());
		reqMsg.put("companyCode", 				"80001");
		reqMsg.put("serviceCode", 				"0001");
		reqMsg.put("serviceType", 				"02");
		reqMsg.put("callType", 					"02");
		reqMsg.put("phoneNumber", 				phonenumber);
		reqMsg.put("callerID", 					callerID.replaceAll("-", ""));
		
		HashMap<String, String> forTTS = new HashMap<>();
		forTTS.put("TTSMENTINTRO", resultTTSPut.getStr("filePath").split(".wav")[0]);
		
		reqMsg.put("ttsMentDynamic", 			forTTS0001(forTTS, type));

		LOGGER.info("wavPlay0001 reqMsg : " + reqMsg);
		
		JSONObject resMsg = new RequestProc().sendPacket(url, reqMsg);

		LOGGER.info("wavPlay0001 Complete!!");
        LOGGER.info("wavPlay0001 resMsg : " + resMsg);
        
        if(resMsg != null)
        {
        	String requestTime 		= resMsg.get("requestTime").toString().equals("") ? null : resMsg.get("requestTime").toString();
        	String callStartTime 	= resMsg.get("callStartTime").toString().equals("") ? resMsg.get("callEndTime").toString() : resMsg.get("callStartTime").toString();
        	String callEndTime 		= resMsg.get("callEndTime").toString().equals("") ? null : resMsg.get("callEndTime").toString();
        	String callduration 	= resMsg.get("callduration").toString().equals("") ? null : resMsg.get("callduration").toString();
        	String result 			= resMsg.get("result").toString().equals("") ? null : resMsg.get("result").toString();
        	String message 			= resMsg.get("message").toString().equals("") ? null : resMsg.get("message").toString();
        	String authReqNumber 	= resMsg.get("authReqNumber").toString().equals("") ? null : resMsg.get("authReqNumber").toString();
        	
        	resultTTSPlay.put("phonenumber", 		phonenumber);
        	resultTTSPlay.put("callresponsedate", 	requestTime);
        	resultTTSPlay.put("callstartdate", 		callStartTime);
        	resultTTSPlay.put("callenddate", 		callEndTime);
        	resultTTSPlay.put("callduration", 		callduration);
        	resultTTSPlay.put("result", 			result);
        	resultTTSPlay.put("resultMessage", 		message);
        	resultTTSPlay.put("authReqNumber", 		authReqNumber);
        	
        	String recordFilePath 	= resMsg.get("recordFilePath").toString().equals("") ? null : resMsg.get("recordFilePath").toString();
        	String recFileName 		= recordFilePath != null ? recordFilePath.split("/")[recordFilePath.split("/").length-1] + ".wav" : null;
        	resultTTSPlay.put("recFilePrefix", 		recordFilePath);
        	resultTTSPlay.put("recFileName", 		recFileName);
        }
        else
        {
        	resultTTSPlay = null;
        }
		
		LOGGER.info("resultTTSPlay : " + resultTTSPlay);

		return resultTTSPlay;
	}

	/**
	 * 만들어진 wav파일을 이용하여 콜을 보냄.(응답형)
	 */
	public MyMap wavPlay0002(List<MyMap> resultTTSPutList, String phonenumber, String callerID)
	{
		LOGGER.info("TTSPlay0002..");
		LOGGER.info("TTSPlay0002_tid : " + resultTTSPutList.get(0).getStr("tid"));

		String port = ((phonenumber.charAt(phonenumber.length()-1) % 2) == 0) ? "42103" : "42102";
		
		LOGGER.info("IVR_SERVER : " + IVR_URL);
		LOGGER.info("IVR_PORT : " + port);
		
		MyMap 					resultTTSPlay 	= new MyMap();
		String 					url 			= IVR_URL + ":" + port + "/arsauth/wavplay/Req0002.do";
		HashMap<String, String> reqMsg 			= new HashMap<>();

		reqMsg.put("arsCommandNumber",  		"L8002");
		reqMsg.put("authReqNumber",     		"InbizTest;;"+resultTTSPutList.get(0).getStr("tid"));
		reqMsg.put("requestTime",       		currentTime());
		reqMsg.put("companyCode",       		"80001");
		reqMsg.put("serviceCode",       		"0001");
		reqMsg.put("serviceType",       		"03");
		reqMsg.put("callType",          		"02");
		reqMsg.put("phoneNumber",				phonenumber);
		reqMsg.put("callerID",					callerID.replaceAll("-", ""));
		reqMsg.put("arryUserPassword",			forPassword(resultTTSPutList));
		reqMsg.put("ttsMentDynamic",			forTTS0002(resultTTSPutList));

		LOGGER.info("wavPlay0002 reqMsg : " + reqMsg);

		JSONObject resMsg = new RequestProc().sendPacket(url, reqMsg);
		
		LOGGER.info("wavPlay0002 Complete!!");
		LOGGER.info("wavPlay0002 resMsg : " + resMsg);
		
		if(resMsg != null)
		{
			String requestTime 		= resMsg.get("requestTime").toString().equals("") ? null : resMsg.get("requestTime").toString();
        	String callStartTime 	= resMsg.get("callStartTime").toString().equals("") ? resMsg.get("callEndTime").toString() : resMsg.get("callStartTime").toString();
        	String callEndTime 		= resMsg.get("callEndTime").toString().equals("") ? null : resMsg.get("callEndTime").toString();
        	String callduration 	= resMsg.get("callduration").toString().equals("") ? null : resMsg.get("callduration").toString();
        	String result 			= resMsg.get("result").toString().equals("") ? null : resMsg.get("result").toString();
        	String message 			= resMsg.get("message").toString().equals("") ? null : resMsg.get("message").toString();
        	String authReqNumber 	= resMsg.get("authReqNumber").toString().equals("") ? null : resMsg.get("authReqNumber").toString();
        	
			resultTTSPlay.put("phonenumber", 		phonenumber);
			resultTTSPlay.put("callresponsedate", 	requestTime);
			resultTTSPlay.put("callstartdate", 		callStartTime);
			resultTTSPlay.put("callenddate", 		callEndTime);
			resultTTSPlay.put("callduration", 		callduration);
			resultTTSPlay.put("result", 			result);
			resultTTSPlay.put("resultMessage", 		message);
			resultTTSPlay.put("authReqNumber", 		authReqNumber);
			
			String recordFilePath 	= resMsg.get("recordFilePath").toString().equals("") ? null : resMsg.get("recordFilePath").toString();
        	String recFileName 		= recordFilePath != null ? recordFilePath.split("/")[recordFilePath.split("/").length-1] + ".wav" : null;
        	resultTTSPlay.put("recFilePrefix", 		recordFilePath);
        	resultTTSPlay.put("recFileName", 		recFileName);
			
			LOGGER.info("wavPlay0002 DTMF setting...");
			String userDTMF 	= resMsg.get("userInputValue").toString();
			LOGGER.info("userDTMF : " + userDTMF);
			String resultDTMF 	= "";
			for(int i=0; i<userDTMF.length(); i++)
			{
				char c = userDTMF.charAt(i);
				if(47 < c && c < 58)
				{
					resultDTMF += c;
					resultDTMF += ",";
					LOGGER.info("userDTMF : " + c);
				}
			}
			
			if(!resultDTMF.equals("")) {
				resultDTMF = resultDTMF.substring(0, resultDTMF.length()-1);
			}
			else
			{
				resultDTMF = null;
			}
			resultTTSPlay.put("userDTMF", resultDTMF);
			LOGGER.info("wavPlay0002 DTMF setting complete!!");
		}
		else
		{
			resultTTSPlay = null;
		}

		LOGGER.info("resultTTSPlay : " + resultTTSPlay);
		
		return resultTTSPlay;
	}

	public static String currentTime()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	public static String currentDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static String forPassword(List<MyMap> resultTTSPutList)
    {
		int 	length 		= resultTTSPutList.size();
    	String 	rtnValue 	= "";
    	String 	password 	= "1,2,3,4,5,6,7,8,9";
    	String 	separator 	= "^";

    	for(int i=0; i<length; i++)
    	{
    		rtnValue += password;
    		rtnValue += (i != length-1) ? separator : "";
    	}

    	return rtnValue;
    }
	
	@SuppressWarnings("unchecked")
	public String forTTS0001(HashMap<String, String> forTTS, String type)
    {
        JSONObject      jsonMainTTS    = new JSONObject();
        JSONArray       jsonSetepArray = new JSONArray();
        JSONObject      jsonStep01TTS  = new JSONObject();

        if(type.equals("A"))
        {
        	jsonStep01TTS.put("TTSMENTINTRO", 	 	 		 forTTS.get("TTSMENTINTRO"));	// 안내멘트
            jsonStep01TTS.put("TTSMENTSUBINTRO", 	 		 TTS_MENT_SUBINTRO);			// 안내 후 서브 멘트
            jsonStep01TTS.put("TTSMENTRETRY", 	 	 		 TTS_MENT_RETRY);				// 다시듣기
            jsonStep01TTS.put("TTSMENTDTMFLACK", 	 	 	 TTS_MENT_DTMFLACK);			// 입력값부족
            jsonStep01TTS.put("TTSMENTDTMFALLLACK", 	 	 TTS_MENT_DTMFALLLACK);			// 입력값부족(횟수초과)
            jsonStep01TTS.put("TTSMENTMISMATCH", 	 	 	 TTS_MENT_MISMATCH);			// 입력값잘못입력시
            jsonStep01TTS.put("TTSMENTDTMFALLMISMATCH", 	 TTS_MENT_DTMFALLMISMATCH);   	// 입력값잘못입력시(횟수초과)
            jsonStep01TTS.put("TTSMENTAUTHCANCLE", 	 	 	 TTS_MENT_AUTHCANCLE);   		// 인증취소
            jsonStep01TTS.put("TTSMENTEXCEEDLISTENAGAIN", 	 TTS_MENT_EXCEEDLISTENAGAIN);   // 다시듣기 횟수초과
            jsonStep01TTS.put("TTSMENTAUTHOK", 	 		 	 TTS_MENT_AUTHOK);   			// 성공
        }
        else if(type.equals("B"))
        {
        	jsonStep01TTS.put("TTSMENTINTRO", 	 	 		 forTTS.get("TTSMENTINTRO"));	// 안내멘트
            jsonStep01TTS.put("TTSMENTSUBINTRO", 	 		 null);			// 안내 후 서브 멘트
            jsonStep01TTS.put("TTSMENTRETRY", 	 	 		 null);					// 다시듣기
            jsonStep01TTS.put("TTSMENTDTMFLACK", 	 	 	 null);				// 입력값부족
            jsonStep01TTS.put("TTSMENTDTMFALLLACK", 	 	 null);			// 입력값부족(횟수초과)
            jsonStep01TTS.put("TTSMENTMISMATCH", 	 	 	 null);				// 입력값잘못입력시
            jsonStep01TTS.put("TTSMENTDTMFALLMISMATCH", 	 null);   	// 입력값잘못입력시(횟수초과)
            jsonStep01TTS.put("TTSMENTAUTHCANCLE", 	 	 	 null);   			// 인증취소
            jsonStep01TTS.put("TTSMENTEXCEEDLISTENAGAIN", 	 null);   	// 다시듣기 횟수초과
            jsonStep01TTS.put("TTSMENTAUTHOK", 	 		 	 null);   			// 성공
        }

        jsonSetepArray.add(jsonStep01TTS);

        jsonMainTTS.put("TTS_MENT_DYNAMIC", jsonSetepArray);

        return jsonMainTTS.toString();
   }
	
	@SuppressWarnings("unchecked")
	public static String forTTS0002(List<MyMap> resultTTSPutList)
    {
         JSONObject      jsonMainTTS  	= new JSONObject();
         JSONArray       jsonSetepArray = new JSONArray();
         
         int length = resultTTSPutList.size();
         
         for(int i=0; i<length; i++)
         {
        	 JSONObject jsonStepTTS = new JSONObject();
        	 
        	 jsonStepTTS.put("TTSMENTINTRO", 	 	 	 resultTTSPutList.get(i).getStr("filePath").split(".wav")[0]);	// 안내멘트
             jsonStepTTS.put("TTSMENTSUBINTRO", 	 	 TTS_MENT_SUBINTRO);											// 안내 후 서브 멘트
             jsonStepTTS.put("TTSMENTRETRY", 	 	 	 TTS_MENT_RETRY);												// 다시듣기
             jsonStepTTS.put("TTSMENTDTMFLACK", 	 	 TTS_MENT_DTMFLACK);											// 입력값부족
             jsonStepTTS.put("TTSMENTDTMFALLLACK", 	 	 TTS_MENT_DTMFALLLACK);											// 입력값부족(횟수초과)
             jsonStepTTS.put("TTSMENTMISMATCH", 	 	 TTS_MENT_MISMATCH);											// 입력값잘못입력시
             jsonStepTTS.put("TTSMENTDTMFALLMISMATCH", 	 TTS_MENT_DTMFALLMISMATCH); 									// 입력값잘못입력시(횟수초과)
             jsonStepTTS.put("TTSMENTAUTHCANCLE", 	 	 TTS_MENT_AUTHCANCLE);   										// 인증취소
             jsonStepTTS.put("TTSMENTEXCEEDLISTENAGAIN", TTS_MENT_EXCEEDLISTENAGAIN); 									// 다시듣기 횟수초과
             jsonStepTTS.put("TTSMENTAUTHOK", 	 		 TTS_MENT_AUTHOK);   											// 성공
             
             jsonSetepArray.add(jsonStepTTS);
         }

         jsonMainTTS.put("TTS_MENT_DYNAMIC", jsonSetepArray);

         return jsonMainTTS.toString();
    }

	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(IVRSender.class);
}
