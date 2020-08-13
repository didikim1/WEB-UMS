//package com.inbiznetcorp.acs.framework.websocket.message;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//
//import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
//import com.inbiznetcorp.acs.framework.common.ServiceCommon;
//import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
//import com.inbiznetcorp.acs.framework.websocket.IWebSocketConstant;
//import com.inbiznetcorp.acs.framework.websocket.WebSoektClient;
//import com.inbiznetcorp.acs.framework.websocket.bean.BeanIVR0002;
//import com.inbiznetcorp.acs.framework.websocket.bean.BeanIVR0001;
//
//public class RequestMessage
//{
//	final int	 iSendWaitingMaxCnt 	= 3;
//	final int	 iSendWaitingSleep		= 1;
//	final int	 iIVRResponseTimeOut    = 180;
//
//	final String kKey_CMD 				= "CMD";
//	final String kKey_APIKEY 			= "APIKEY";
//	final String kKey_CALL_SCENARIO 	= "CALL_SCENARIO";
//	final String kKey_CALL_TO 			= "CALL_TO";
//	final String kKey_CALL_FROM 		= "CALL_FROM";
//	final String kKey_CALL_WAIT_TIME 	= "CALL_WAIT_TIME";
//	final String kKey_RETRY_COUNT 		= "RETRY_COUNT";
//	final String kKey_AUTH_ARRAY		= "AUTH_ARRAY";
//	final String kKey_JSON_ALL_MENTS 	= "JSON_ALL_MENTS";
//	final String kKey_MENT_INTRO 		= "MENT_INTRO";
//	final String kKey_WS_CONNECTION_ID 	= "WS_CONNECTION_ID";
//	final String kKey_IVR_URL 			= "IVR_URL";
//
//	final static String TTS_DELAY 		=  "<VTML_PAUSE TIME=\"700\" />";
//	final static String TTS_DELAY_500 	=  "<VTML_PAUSE TIME=\\\"500\\\" />";
//	final static String TTS_DELAY_300 	=  "<VTML_PAUSE TIME=\"300\" />";
//	final static String TTS_DELAY_100 	=  "<VTML_PAUSE TIME=\"100\" />";
//	final static String TTS_DELAY_10 	=  "<VTML_PAUSE TIME=\"10\" />";
//
//	public RequestMessage(String callScenario, String userSessionID, BeanIVR0002 beanIVRCheck)
//	{
//		mUserSessionID	= userSessionID;
//		mCallScenario	= callScenario;
//		mBeanIVRCheck	= beanIVRCheck;
//	}
//
//	public RequestMessage(String callScenario, String userSessionID, BeanIVR0001 beanIVRNotice)
//	{
//		mUserSessionID	= userSessionID;
//		mCallScenario	= callScenario;
//		mBeanIVRNotice	= beanIVRNotice;
//	}
//
//	public boolean connection()
//	{
//		mWebSoektClient = new WebSoektClient();
//
//		mWsConnectionID = mWebSoektClient.connect(mRequest_URL, mUserSessionID);
//
//		return (mWsConnectionID != null);
//	}
//
//	public boolean disconnect()
//	{
//		mWebSoektClient.disconnect();
//
//		return true;
//	}
//
//	@SuppressWarnings("unchecked")
//	public boolean sendMessage()
//	{
//		ServiceCommon serviceCommon = (ServiceCommon) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.common.ServiceCommon");
//
//		String IVR_URL = "";
//
//		if(serviceCommon.getProfilesActiveName().equals("prd"))
//		{
//			IVR_URL = "http://211.61.220.43";
//		}
//		else
//		{
//			IVR_URL = "http://211.61.220.43";
//		}
//
//		int 	iWatingCnt 	= 0;
//
//		mMainObject = new JSONObject();
//
//
//		mMainObject.put(kKey_WS_CONNECTION_ID, 	mWsConnectionID);
//		mMainObject.put(kKey_CMD, 			  	IWebSocketConstant.COMMAND);
//		mMainObject.put(kKey_APIKEY, 		  	IWebSocketConstant.APIKEY);
//		mMainObject.put(kKey_CALL_WAIT_TIME, 	45);
//		mMainObject.put(kKey_CALL_SCENARIO, 	mCallScenario);
//		mMainObject.put(kKey_IVR_URL, 		  	IVR_URL);
//
//		switch (mCallScenario)
//		{
//		case IWebSocketConstant.CALL_SCENARIO_MENT_AND_HANGUP:
//
////			mMainObject.put(kKey_CALL_FROM, 		mBeanIVRNotice.CALL_FROM);
////			mMainObject.put(kKey_CALL_TO, 			mBeanIVRNotice.CALL_TO);
//
//			String mentIntro = "";
////			mentIntro = mBeanIVRNotice.MENT_INTRO_01;
//
//			mMainObject.put("MENT_INTRO", mentIntro);
//			break;
//		case IWebSocketConstant.CALL_SCENARIO_MENT_AND_SELECTION:
//
//			JSONArray authArray = new JSONArray();
//			authArray.add("1");
//			authArray.add("2");
//			authArray.add("3");
//			authArray.add("4");
//			authArray.add("5");
//			authArray.add("6");
//			authArray.add("7");
//			authArray.add("8");
//			authArray.add("9");
////			authArray.add("1234567890123");
////			authArray.add("1234567890123");
//
//			mMainObject.put(kKey_CALL_FROM, 		mBeanIVRCheck.CALL_FROM);
//			mMainObject.put(kKey_CALL_TO, 			mBeanIVRCheck.CALL_TO);
//
//			mMainObject.put("RETRY_COUNT", mBeanIVRCheck.RETRY_COUNT);
//			mMainObject.put("AUTH_ARRAY",  (mBeanIVRCheck.AUTH_ARRAY == null) ? authArray : mBeanIVRCheck.AUTH_ARRAY);
//
//			String ttsIntro = "";
//			if(mBeanIVRCheck.VOICE_RECEIVE.equals("Y"))
//			{
//				ttsIntro += mBeanIVRCheck.TTS_MENT_INTRO_01 + ", 음성회신을 하시려면 0번을, 다시 들으시려면 별표를, 종료는 #버튼을 눌러주세요.";
//			}
//			else
//			{
//				ttsIntro += mBeanIVRCheck.TTS_MENT_INTRO_01 + ", 다시 들으시려면 별표를, 종료는 #버튼을 눌러주세요.";
//			}
//
//			JSONObject jsonAllMents = new JSONObject();
//			jsonAllMents.put("TTS_INTRO", 			FrameworkUtils.NVL(ttsIntro, 		  						"죄송합니다. 잘못된 메세지 입니다."));
//			jsonAllMents.put("TTS_INPUT_RANGE_IN",  FrameworkUtils.NVL(mBeanIVRCheck.TTS_MENT_INPUT_RANGE_IN,	"감사합니다. 추가 안내사항 입니다."));
//			jsonAllMents.put("TTS_INPUT_RANGE_OUT", FrameworkUtils.NVL(mBeanIVRCheck.TTS_MENT_INPUT_RANGE_OUT, 	"잘못 입력하였습니다."));
//			jsonAllMents.put("TTS_INPUT_RETRY",  	FrameworkUtils.NVL(mBeanIVRCheck.TTS_MENT_INPUT_RETRY, 	  	"다시 입력해 주십시오."));
//			jsonAllMents.put("TTS_INPUT_NONE",  	FrameworkUtils.NVL(mBeanIVRCheck.TTS_MENT_INPUT_NONE, 	  	"입력 시간이 초과되었습니다."));
//			jsonAllMents.put("TTS_INPUT_LACK",  	FrameworkUtils.NVL(mBeanIVRCheck.TTS_MENT_INPUT_LACK, 	  	"입력 내용이 부족합니다."));
//			jsonAllMents.put("TTS_RETRY_OVER",  	FrameworkUtils.NVL(mBeanIVRCheck.TTS_MENT_RETRY_OVER, 	  	"허용된 회수 이상 잘못 입력하셔서 통화를 종료합니다."));
//
//			mMainObject.put("JSON_ALL_MENTS", jsonAllMents);
//
//			break;
//		default:
//			break;
//		}
//
//		mWebSoektClient.sendPacket(mMainObject);
//
//
//
//		while( mWebSoektClient.isCallSend() == false )
//		{
//			if( iWatingCnt >= iSendWaitingMaxCnt) break;
//
//			try { Thread.sleep(1000*iSendWaitingSleep); } catch (InterruptedException e) {e.printStackTrace();}
//
//			iWatingCnt++;
//		}
//
//		return mWebSoektClient.isCallSend();
//	}
//
//	public boolean ivrResponseMsgWating()
//	{
//		int 	iWatingCnt 	= 0;
//		while(mWebSoektClient.isCallEnd() == false)
//		{
////			System.out.println("iWating.. " + iWatingCnt+"/ " +iIVRResponseTimeOut);
//			if( iWatingCnt >=  iIVRResponseTimeOut)
//			{
//				 break;
//			}
//			try { Thread.sleep(1000*iSendWaitingSleep); } catch (InterruptedException e) {e.printStackTrace();}
//			iWatingCnt++;
//		}
////		System.out.println("isCallEnd = " + mWebSoektClient.isCallEnd());
//		return mWebSoektClient.isCallEnd();
//	}
//
//	public JSONObject getIVRResponseMsg()
//	{
//		return mWebSoektClient.getIVRResMsg();
//	}
//
//	private String 		   mUserSessionID   = null;
//	private String		   mWsConnectionID  = null;
//
//	private BeanIVR0002   mBeanIVRCheck    = null;
//	private BeanIVR0001  mBeanIVRNotice   = null;
//
//	private String		   mCallScenario	= "";
//
//	private String		   mRequest_URL		= "ws://211.61.220.43:3000/";
//
//	private WebSoektClient mWebSoektClient  = null;
//	private JSONObject	   mMainObject	    = null;
//}
