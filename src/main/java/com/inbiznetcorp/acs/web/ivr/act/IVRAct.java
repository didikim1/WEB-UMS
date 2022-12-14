package com.inbiznetcorp.acs.web.ivr.act;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.result.ResultCode;
import com.inbiznetcorp.acs.framework.result.ResultMessage;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
import com.inbiznetcorp.acs.framework.websocket.IVRRunnable;
import com.inbiznetcorp.acs.framework.websocket.IVRRunnable_0001;
import com.inbiznetcorp.acs.framework.websocket.IVRRunnable_0002;
import com.inbiznetcorp.acs.framework.websocket.IVRSender;
import com.inbiznetcorp.acs.framework.websocket.UMSExecutorService;
import com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo;
import com.inbiznetcorp.acs.mapper.ttsfileputhistory.TTSfileputhistoryMapper;
import com.inbiznetcorp.acs.web.ivr.service.IVRService;
import com.inbiznetcorp.acs.web.message.service.ArsalarmttsService;
import com.inbiznetcorp.acs.web.scheduler.service.CallSchedulerService;

@Controller
@RequestMapping("/IVR")
public class IVRAct
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(IVRAct.class);

	@Resource(name="com.inbiznetcorp.acs.web.message.service.ArsalarmttsService")
	ArsalarmttsService arsalarmttsService;

	@Resource(name="com.inbiznetcorp.acs.web.ivr.service.IVRService")
	IVRService ivrService;
	
	@Resource(name="com.inbiznetcorp.acs.web.scheduler.service.CallSchedulerService")
	CallSchedulerService callSchedulerService;
	
	@Resource(name="com.inbiznetcorp.acs.mapper.ttsfileputhistory.TTSfileputhistoryMapper")
	TTSfileputhistoryMapper ttsfileputhistoryMapper;
	
	IVRSender ivrSender = (IVRSender) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.IVRSender");
	BasicInfo basicInfo = (BasicInfo) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo");

	/**
	 * ????????? ??????
	 */
	@RequestMapping("/Listen")
	public @ResponseBody ResultMessage Listen(Model model)
	{
		MyMap 	paramMap 		= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		MyMap 	resultTTSMake 	= new MyMap();
		String 	tid 			= FrameworkUtils.generateSessionID();
		String 	companyName   	= paramMap.getStr("SESSION_USER_NAME");
		String 	msg 			= FrameworkUtils.unescapeHtml(paramMap.getStr("msg")).replaceAll("<br/>", ". ").trim();
		System.out.println(paramMap);
		resultTTSMake = ivrSender.RealTimeTTSMake(tid, companyName, msg);
		
		resultTTSMake.put("fileUrl", resultTTSMake.getStr("fileUrl").replace("/Upload/home/asterisk/Lab603/Lab603-TTSMake-Web/Project/", ""));
		System.out.println(resultTTSMake.getStr("fileUrl"));
		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!", resultTTSMake);
	}

	/**
	 * ARS??? ??????
	 */
	@RequestMapping("/IVREntrustingListen")
	public @ResponseBody ResultMessage IVREntrustingListen(Model model)
	{
		MyMap 	paramMap 	= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		String 	tid 		= FrameworkUtils.generateSessionID();
		String 	companyName = paramMap.getStr("SESSION_USER_NAME");
		String 	msg 		= FrameworkUtils.unescapeHtml(paramMap.getStr("msg") + "<VTML_PAUSE TIME=\"500\" />").replaceAll("<br/>", ". ").trim();
		String 	phonenumber = paramMap.getStr("phonenumber").replaceAll("-", "").replaceAll(",", "").trim();
		String 	type 		= "A"; 	// A:????????????, B:??????
		String 	callerid 	= paramMap.getStr("callerid");
		
		UMSExecutorService.addCallRunable(new IVRRunnable_0001(tid, companyName, msg, phonenumber, type, callerid));

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * ??????????????? ??????
	 * @throws ParseException 
	 */
	@RequestMapping("/IVREntrusting")
	public @ResponseBody ResultMessage IVREntrusting(Model model)
	{
		MyMap 	paramMap 	= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		String 	sessionId	= FrameworkUtils.generateSessionID();
		String 	callerID 	= paramMap.getStr("callerID", "");
		
		try
		{
			JSONParser parser = new JSONParser();
			String originTTSMent2 = paramMap.getStr("originTTSMent2");
			String originTTSMent3 = paramMap.getStr("originTTSMent3");
			JSONArray jArray2 = (JSONArray) parser.parse(FrameworkUtils.unescapeHtml(originTTSMent2));
			JSONArray jArray3 = (JSONArray) parser.parse(FrameworkUtils.unescapeHtml(originTTSMent3));
			
			System.out.println("jArray : " + jArray2);
			System.out.println("jArray : " + jArray3);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		
		paramMap.put("tid", sessionId);
		paramMap.put("ttsMentIntro01", paramMap.getStr("ttsMent"));

		// ???????????? ?????? ???
		if(paramMap.getStr("isReceive").equals("Y"))
		{
			//
		}

		// ???????????? ????????? ???
		if(paramMap.getStr("isSave").equals("Y"))
		{
//			resultTTSMake.put("ttsMentTitle", paramMap.getStr("ttsTitle"));
//			arsalarmttsService.RegisterData(resultTTSMake);
		}
		
		String nextcallDate = "";
		if(!paramMap.getStr("sendDay", "").equals(""))
		{
			nextcallDate = paramMap.getStr("sendDay") + " " + paramMap.getStr("sendHour") + ":" + paramMap.getStr("sendMinute");
			paramMap.put("reservationDate", nextcallDate);
			paramMap.put("nextcallDate", nextcallDate);
		}
		else if(!paramMap.getStr("repeatDay", "").equals(""))
		{
			nextcallDate = paramMap.getStr("repeatDay") + " " + paramMap.getStr("repeatHour") + ":" + paramMap.getStr("repeatMinute");
			paramMap.put("reservationDate", nextcallDate);
			paramMap.put("nextcallDate", nextcallDate);
		}
		
		// INSERT IVRLOGMAPPER TABLE
		paramMap.put("callerId", 		paramMap.getStr("callerID"));
		ivrService.RegisterMapperData(paramMap);
		int ivrlogmapperseq = paramMap.getInt("ivrlogmapperseq", 0);

		try
		{
			JSONParser 	parser 		= new JSONParser();
			JSONArray 	targetArr 	= (JSONArray)parser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("targetArr")));
			LOGGER.info("SENDTYPE ::: " + paramMap.getStr("sendType"));
			// TTS ?????? ????????? ??????
			if(paramMap.getStr("sendType").equals("A"))
			{
				String 		companyName 	= paramMap.getStr("SESSION_USER_NAME");
				String 		msg 			= FrameworkUtils.unescapeHtml(paramMap.getStr("ttsMent") + "<VTML_PAUSE TIME=\"500\" />").replaceAll("<br/>", ". ").trim();
				
				LOGGER.info("CALLTYPE ::: " + paramMap.getStr("callType"));
				// ?????????
				if(paramMap.getStr("callType").equals("A"))
				{
					// TTS wav ?????? ??????
					MyMap resultTTSMake 	= ivrSender.RealTimeTTSMake(sessionId, companyName, msg);
					
					// TTS wav ?????? IVR ????????? ??????
					resultTTSMake.put("ivrlogmapperseq", ivrlogmapperseq);
					MyMap resultTTSPut 		= ivrSender.TTSFileIVRServerPut(resultTTSMake);
					
					for(int i=0; i<targetArr.size(); i++)
					{
						String 		userSessionID 	= FrameworkUtils.generateSessionID();
						JSONObject 	target 			= (JSONObject) targetArr.get(i);
						String 		phonenumber 	= target.get("phonenumber").toString().replaceAll("-", "").trim();
						String 		sendTime 		= paramMap.getStr("sendTime");// A:??????, B:??????, C:??????
						String 		type 			= "B";	// A:????????????, B:??????
						
						// INSERT IVRLOG TABLE
						paramMap.put("phonenumber", 		phonenumber);
						paramMap.put("name", 				target.get("name").toString());
						paramMap.put("userSessionID", 		userSessionID);
						paramMap.put("statusCompletion", 	"I");
						ivrService.RegisterData(paramMap);
						
						String ivrlogseq = paramMap.getStr("ivrlogseq");
						UMSExecutorService.addCallRunable(new IVRRunnable_0001(paramMap, sessionId, companyName, msg, ivrlogseq, type, sendTime, resultTTSPut, callerID));
					}
				}
				// ?????????
				else
				{
					JSONArray ment2Arr = (JSONArray)parser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("originTTSMent2")));
					JSONArray ment3Arr = (JSONArray)parser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("originTTSMent3")));
					
					MyMap 		resultTTSMake 		= null;
					MyMap 		resultTTSPut 		= null;
					List<MyMap> resultTTSPutList 	= new ArrayList<>();
					LOGGER.info("????????? ???????????? ?????? ::: "+ment2Arr.toString());
					LOGGER.info("????????? ???????????? ?????? ::: "+ment2Arr.toString());
					String message = paramMap.getStr("originTTSMent1") ;
					// ??? ?????? ??? TTS wav ?????? ??????
					for(int i=0; i<ment2Arr.size(); i++)
					{
						String tid = FrameworkUtils.generateSessionID();
						if(i != 0) message = ""; 
						message += ment2Arr.get(i)+","+ment3Arr.get(i);
						LOGGER.info("ment info :: " + message + " // S");
						// TTS wav ?????? ??????
						resultTTSMake = ivrSender.RealTimeTTSMake(tid, companyName, message);
//						resultTTSMake = ivrSender.RealTimeTTSMake(tid, companyName, mentArr.get(i).toString().replace(temp, ""));
						resultTTSMake.put("ivrlogmapperseq", 	ivrlogmapperseq);
						resultTTSMake.put("orderNum", 			(i+1));
						
						// TTS wav ?????? IVR ????????? ??????
						resultTTSPut = ivrSender.TTSFileIVRServerPut(resultTTSMake);
						resultTTSPutList.add(resultTTSPut);
//						temp += mentArr.get(i).toString();
					}
					
					for(int i=0; i<targetArr.size(); i++)
					{
						String 		userSessionID 	= FrameworkUtils.generateSessionID();
						JSONObject 	target 			= (JSONObject) targetArr.get(i);
						String 		phonenumber 	= target.get("phonenumber").toString().replaceAll("-", "");
						String 		name			= target.get("name").toString().trim();
						String 		sendTime 		= paramMap.getStr("sendTime"); // A:??????, B:??????, C:??????

						// INSERT IVRLOG TABLE
						paramMap.put("phonenumber", 		phonenumber);
						paramMap.put("name", 				name);
						paramMap.put("userSessionID", 		userSessionID);
						paramMap.put("statusCompletion", 	"I");
						ivrService.RegisterData(paramMap);
						
						String ivrlogseq = paramMap.getStr("ivrlogseq");
						
						UMSExecutorService.addCallRunable(new IVRRunnable_0002(sessionId, ivrlogseq, sendTime, callerID));
					}
				}
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}
	
	/**
	 * wav????????? ??? ??????
	 */
	@RequestMapping("/SendCallByWav")
	public @ResponseBody ResultMessage SendCallByWav(Model model)
	{
		MyMap 		paramMap 	= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		JSONParser 	parser 		= new JSONParser();
		
		String callerID =paramMap.getStr("callerID", ""); 
		
		// .wav ?????? ??????
		MyCamelMap fileInfo = ivrService.SelectFile(paramMap);
		
		String filePath		= fileInfo.getStr("uploadPath");
		String tid 			= FrameworkUtils.generateSessionID();
		String groupName 	= paramMap.getStr("SESSION_USER_NAME");
		
		// INSERT IVRLOGMAPPER TABLE
		paramMap.put("reservationDate", paramMap.getStr("nextcallDate", null));
		paramMap.put("round", 			1);
		paramMap.put("callerId", 		paramMap.getStr("callerID"));
		ivrService.RegisterMapperData(paramMap);
		
		// .wav ?????? ????????? ??????
		MyMap resultWavUpload = ivrSender.WAVFileUpload(filePath, tid, groupName);
		if(resultWavUpload == null)
		{
			return new ResultMessage(ResultCode.RESULT_BAD_REQUEST, "fail..");
		}
		else
		{
			// ????????? ????????? .wav ????????? ????????? ???????????? ??????????????? ??????
			resultWavUpload.put("ivrlogmapperseq", paramMap.getInt("ivrlogmapperseq", 0));
			MyMap resultTTSPut = ivrSender.TTSFileIVRServerPut(resultWavUpload);
			
			try
			{
				// ??????????????? ??????/????????? ????????? ??????
				if(paramMap.getInt("seqgroup", 0) == 0)
				{
					JSONArray 	targetArr 	= (JSONArray)parser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("targetArr")));
					
					new Thread(new Runnable() {
						
						@Override
						public void run()
						{
							try
							{
								for(int i=0; i<targetArr.size(); i++)
								{
									String 		userSessionID 	= tid;
									String 		authReqNumber 	= FrameworkUtils.generateSessionID();
									JSONObject 	target 			= (JSONObject) targetArr.get(i);
									String 		phonenumber 	= target.get("phonenumber").toString().replaceAll("-", "").trim();
									
									// INSERT IVRLOG TABLE
									paramMap.put("phonenumber", 		phonenumber);
									paramMap.put("name", 				target.get("name").toString());
									paramMap.put("userSessionID", 		userSessionID);
									paramMap.put("ttsMentIntro01", 		paramMap.getStr("recFileName"));
									paramMap.put("forElection", 		"Y");
									
									
									if(paramMap.getStr("sendTime").equals("A"))
									{
										paramMap.put("statusCompletion", 	"I");
										paramMap.put("authReqNumber", 		authReqNumber);
										paramMap.put("round", 				1);
										ivrService.RegisterData(paramMap);

										UMSExecutorService.addCallRunable(new IVRRunnable(paramMap, resultTTSPut, paramMap.getInt("ivrlogseq"), phonenumber, callerID, authReqNumber));
										
										// CPS??? 15??? ????????? ?????????, 70ms*15=1050ms -> 15?????? ???1.05???
										// CPS??? 10?????? ????????? ?????????, 100ms*10=1000ms -> 10?????? ???1.00???
										try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
									}
									else
									{
										paramMap.put("statusCompletion", 	"W");
										paramMap.put("round", 				1);
										ivrService.RegisterData(paramMap);
									}
								}
							}
							catch (Exception e)
							{
								LOGGER.info("Executor shutdown..");
								e.printStackTrace();
							}
						}
					}).start();
					
				}
				// ??????????????? excel ????????? ????????? ??????
				else
				{
					List<MyCamelMap> targetArr = ivrService.SelectTempTargets(paramMap);
					
					new Thread(new Runnable() {
						
						@Override
						public void run()
						{
							for(int i=0; i<targetArr.size(); i++)
							{
								String 		userSessionID 	= tid;
								String 		authReqNumber 	= FrameworkUtils.generateSessionID();
								MyCamelMap 	target 			= targetArr.get(i);
								String 		phonenumber 	= target.getStr("phonenumber").replaceAll("-", "").trim();
								
								// INSERT IVRLOG TABLE
								paramMap.put("phonenumber", 		phonenumber);
								paramMap.put("name", 				target.getStr("name"));
								paramMap.put("userSessionID", 		userSessionID);
								paramMap.put("ttsMentIntro01", 		paramMap.getStr("recFileName"));
								paramMap.put("forElection", 		"Y");
								
								if(paramMap.getStr("sendTime").equals("A"))
								{
									paramMap.put("statusCompletion", 	"I");
									paramMap.put("authReqNumber", 		authReqNumber);
									paramMap.put("round", 				1);
									ivrService.RegisterData(paramMap);

									UMSExecutorService.addCallRunable(new IVRRunnable(paramMap, resultTTSPut, paramMap.getInt("ivrlogseq"), phonenumber, callerID, authReqNumber));
									
									// CPS??? 15??? ????????? ?????????, 70ms*15=1050ms -> 15?????? ???1.05???
									try { Thread.sleep(70); } catch (InterruptedException e) { e.printStackTrace(); }
								}
								else
								{
									paramMap.put("statusCompletion", 	"w");
									paramMap.put("round", 				1);
									ivrService.RegisterData(paramMap);
								}
							}
						}
					}).start();
					
				}
				
			}
			catch (ParseException e)
			{
				FrameworkUtils.exceptionToString(e);
			}
		}
		
		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}
	
	@RequestMapping("/RetrySelectedIVREntrusting")
	public @ResponseBody ResultMessage RetrySelectedIVREntrusting()
	{
		MyMap 	paramMap 		= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		// mapper ??????
		MyCamelMap ivrlogmapper = ivrService.SelectIvrlogmapper(paramMap);
		LOGGER.info("ivrlogmapper : " + ivrlogmapper);
		
		String 	callerId 	= ivrlogmapper.getStr("callerId");
		
		// .wav ?????? ??????
		MyCamelMap resultTTSPut = ivrService.SelectTTSFile(paramMap);
		LOGGER.info("resultTTSPut : " + resultTTSPut);
		
		// ????????? ?????? ?????????
		List<MyCamelMap> retryList = ivrService.ListPagingData(paramMap).getAllList();
		LOGGER.info("retryList.size : " + retryList.size());
		new Thread(new Runnable() {
			
			@Override
			public void run()
			{
				ivrlogmapper.put("round", 			ivrlogmapper.getInt("round") + 1);
				ivrlogmapper.put("ttsTitle", 		ivrlogmapper.getStr("title"));
				ivrlogmapper.put("ivrlogmapperseq", ivrlogmapper.getStr("originSeq"));
				ivrService.RegisterMapperData(ivrlogmapper);
				
				int ivrlogmapperseq = ivrlogmapper.getInt("ivrlogmapperseq");
				
				resultTTSPut.put("ivrlogmapperseq", ivrlogmapperseq);
				ttsfileputhistoryMapper.RegistTTSFilePutHistory(resultTTSPut);
				
				for(int i=0; i<retryList.size(); i++)
				{
					MyMap target = retryList.get(i);
					LOGGER.info("target : " + target);
					String authReqNumber 	= FrameworkUtils.generateSessionID();
					String phonenumber 		= target.getStr("phonenumber");
					
					target.put("callType", 			"A");
					target.put("round", 			ivrlogmapper.getInt("round") + 1);
					target.put("forElection", 		"Y");
					target.put("statusCompletion", 	"I");
					target.put("authReqNumber", 	authReqNumber);
					target.put("ivrlogmapperseq", 	ivrlogmapperseq);
					target.remove("nextcallDate");
					target.remove("userInput");
					ivrService.RegisterData(target);

					UMSExecutorService.addCallRunable(new IVRRunnable(target, resultTTSPut, target.getInt("ivrlogseq"), phonenumber, callerId, authReqNumber));
					
					// CPS??? 15??? ????????? ?????????, 70ms*15=1050ms -> 15?????? ???1.05???
					try { Thread.sleep(70); } catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
		}).start();
		
		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * ??????????????? ???????????? ?????????
	 */
	@RequestMapping("/RetryGroupIVREntrusting")
	public @ResponseBody ResultMessage RetryGroupIVREntrusting(Model model)
	{
		MyMap 	paramMap 		= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		String 	userSessionID 	= FrameworkUtils.generateSessionID();

		// ????????? ?????????
		List<MyCamelMap> 	resultList 		= ivrService.SelectRetryList(paramMap);
		
		int ivrlogmapperseq 	= paramMap.getInt("ivrlogmapperseq");

		for(MyCamelMap result : resultList)
		{
			int 	importance 	= 0;
			String 	status 		= "W";
			
			result.put("sendTime", 				'A');
			result.put("statusCompletion", 		"I");
			result.put("tid", 					userSessionID);
			result.put("userSessionID", 		userSessionID);
			result.put("ivrlogmapperseq", 		ivrlogmapperseq);
			result.put("sessionCompanyName", 	paramMap.getStr("SESSION_USER_NAME"));
			result.put("statusCompletion", 		"I");
			result.put("importance", 			importance);
			result.put("status", 				status);

			LOGGER.info("result : " + result);
			
			ivrService.RegisterData(result);			// INSERT IVRLOG
			callSchedulerService.RegisterData(result);	// INSERT CALL_SCHEDULER
			
			if(importance == 0)
			{
				basicInfo.setNormalCallExist(true);
				LOGGER.info("Normal Call Run.. " + basicInfo.isNormalCallExist());
			}
			else if(importance == 1)
			{
				basicInfo.setImportantCallExist(true);
				LOGGER.info("Important Call Run.. " + basicInfo.isImportantCallExist());
			}
			
		}

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * ??????????????? ?????? ?????????
	 */
	@RequestMapping("/RetryIVREntrusting")
	public @ResponseBody ResultMessage RetryIVREntrusting(Model model)
	{
		MyMap 		paramMap 		= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		String 		userSessionID 	= FrameworkUtils.generateSessionID();
		
		// ????????? ?????? IVRLOG
		MyCamelMap 	result 			= ivrService.SelectRetryTarget(paramMap);
		
		int 	importance 	= 0;
		String 	status 		= "W";
		
		result.put("sendTime", 				'A');
		result.put("statusCompletion", 		"I");
		result.put("tid", 					userSessionID);
		result.put("userSessionID", 		userSessionID);
		result.put("sessionCompanyName", 	paramMap.getStr("SESSION_USER_NAME"));
		result.put("statusCompletion", 		"I");
		result.put("importance", 			importance);
		result.put("status", 				status);

		ivrService.RegisterData(result);			// INSERT IVRLOG
		callSchedulerService.RegisterData(result);	// INSERT CALL_SCHEDULER
		
		if(importance == 0)
		{
			basicInfo.setNormalCallExist(true);
			LOGGER.info("Normal Call Run.. " + basicInfo.isNormalCallExist());
		}
		else if(importance == 1)
		{
			basicInfo.setImportantCallExist(true);
			LOGGER.info("Important Call Run.. " + basicInfo.isImportantCallExist());
		}
		
		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}


	/**
	 * ????????????
	 */
	@RequestMapping("/IVRReservation")
	public @ResponseBody ResultMessage IVRReservation()
	{
		MyMap 	paramMap 	= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		String 	sessionId 	= FrameworkUtils.generateSessionID();
		String 	callerID 	= paramMap.getStr("callerID", "");
		
		paramMap.put("tid", sessionId);
		paramMap.put("ttsMentIntro01", paramMap.getStr("ttsMent"));

		// ???????????? ?????? ???
		if(paramMap.getStr("isReceive").equals("Y"))
		{
			//
		}

		// ???????????? ????????? ???
		if(paramMap.getStr("isSave").equals("Y"))
		{
//			resultTTSMake.put("ttsMentTitle", paramMap.getStr("ttsTitle"));
//			arsalarmttsService.RegisterData(resultTTSMake);
		}
		
//		String nextcallDate = "";
//		if(!paramMap.getStr("sendDay", "").equals(""))
//		{
//			nextcallDate = paramMap.getStr("sendDay") + " " + paramMap.getStr("sendHour") + ":" + paramMap.getStr("sendMinute");
//			paramMap.put("reservationDate", nextcallDate);
//			paramMap.put("nextcallDate", nextcallDate);
//		}
//		else if(!paramMap.getStr("repeatDay", "").equals(""))
//		{
//			nextcallDate = paramMap.getStr("repeatDay") + " " + paramMap.getStr("repeatHour") + ":" + paramMap.getStr("repeatMinute");
//			paramMap.put("reservationDate", nextcallDate);
//			paramMap.put("nextcallDate", nextcallDate);
//		}
		
		// INSERT IVRLOGMAPPER TABLE
		paramMap.put("reservationDate", paramMap.getStr("nextcallDate"));
		paramMap.put("callerId", 		paramMap.getStr("callerID"));
		ivrService.RegisterMapperData(paramMap);
		int ivrlogmapperseq = paramMap.getInt("ivrlogmapperseq", 0);
		
		try
		{
			JSONParser 	parser 		= new JSONParser();
			JSONArray 	targetArr 	= (JSONArray)parser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("targetArr")));
			
			// TTS ?????? ????????? ??????
			if(paramMap.getStr("sendType").equals("A"))
			{
				String 		companyName 	= paramMap.getStr("SESSION_USER_NAME");
				String 		msg 			= FrameworkUtils.unescapeHtml(paramMap.getStr("ttsMent") + "<VTML_PAUSE TIME=\"500\" />").replaceAll("<br/>", ". ").trim();
				
				// ?????????
				if(paramMap.getStr("callType").equals("A"))
				{
					// TTS wav ?????? ??????
					MyMap resultTTSMake 	= ivrSender.RealTimeTTSMake(sessionId, companyName, msg);
					
					// TTS wav ?????? IVR ????????? ??????
					resultTTSMake.put("ivrlogmapperseq", ivrlogmapperseq);
					MyMap resultTTSPut 		= ivrSender.TTSFileIVRServerPut(resultTTSMake);
					
					for(int i=0; i<targetArr.size(); i++)
					{
						String 		userSessionID 	= FrameworkUtils.generateSessionID();
						JSONObject 	target 			= (JSONObject) targetArr.get(i);
						String 		phonenumber 	= target.get("phonenumber").toString().replaceAll("-", "").trim();
						String 		sendTime 		= paramMap.getStr("sendTime");// A:??????, B:??????, C:??????
						String 		type 			= "B";	// A:????????????, B:??????
						
						// INSERT IVRLOG TABLE
						paramMap.put("phonenumber", 		phonenumber);
						paramMap.put("name", 				target.get("name").toString());
						paramMap.put("userSessionID", 		userSessionID);
						paramMap.put("statusCompletion", 	"R");
						ivrService.RegisterData(paramMap);
						
						String ivrlogseq = paramMap.getStr("ivrlogseq");
						
						UMSExecutorService.addCallRunable(new IVRRunnable_0001(paramMap, sessionId, companyName, msg, ivrlogseq, type, sendTime, resultTTSPut, callerID));
					}
				}
				// ?????????
				else
				{
					JSONArray mentArr = (JSONArray)parser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("mentArr")));
					
					MyMap 		resultTTSMake 		= null;
					MyMap 		resultTTSPut 		= null;
					List<MyMap> resultTTSPutList 	= new ArrayList<>();
					
					// ??? ?????? ??? TTS wav ?????? ??????
					int orderNum = 0;
					for(int i=0; i<mentArr.size(); i++)
					{
						orderNum++;
						String tid = FrameworkUtils.generateSessionID();
						
						// TTS wav ?????? ??????
						resultTTSMake = ivrSender.RealTimeTTSMake(tid, companyName, mentArr.get(i).toString());
						resultTTSMake.put("ivrlogmapperseq", 	ivrlogmapperseq);
						resultTTSMake.put("orderNum", 			orderNum);
						
						// TTS wav ?????? IVR ????????? ??????
						resultTTSPut = ivrSender.TTSFileIVRServerPut(resultTTSMake);
						resultTTSPutList.add(resultTTSPut);
					}
					
					for(int i=0; i<targetArr.size(); i++)
					{
						String 		userSessionID 	= FrameworkUtils.generateSessionID();
						JSONObject 	target 			= (JSONObject) targetArr.get(i);
						String 		phonenumber 	= target.get("phonenumber").toString().replaceAll("-", "");
						String 		sendTime 		= paramMap.getStr("sendTime"); // A:??????, B:??????, C:??????

						// INSERT IVRLOG TABLE
						paramMap.put("phonenumber", 		phonenumber);
						paramMap.put("name", 				target.get("name").toString());
						paramMap.put("userSessionID", 		userSessionID);
						paramMap.put("statusCompletion", 	"R");
						ivrService.RegisterData(paramMap);
						
						String ivrlogseq = paramMap.getStr("ivrlogseq");
						
						UMSExecutorService.addCallRunable(new IVRRunnable_0002(sessionId, ivrlogseq, sendTime, callerID));
					}
				}
			}
		}
		catch (ParseException e)
		{
			FrameworkUtils.exceptionToString(e);
		}
		
		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * ?????? ???????????? ?????? ?????? ??????
	 */
	@RequestMapping("/SendWaitingCall")
	public @ResponseBody ResultMessage SendWaitingCall()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		// ????????? ?????????
		List<MyCamelMap> 	resultList 		= ivrService.SelectRetryList(paramMap);
		for(MyCamelMap result : resultList)
		{
			String 	userSessionID 	= FrameworkUtils.generateSessionID();
//			String 	ivrlogseq 		= result.getStr("ivrlogseq");
			int 	importance 		= 0;
			String 	status 			= "W";

			// ???????????? ???????????? ??????
			result.put("sendTime", 			"A");
			result.put("statusCompletion", 	"I");
			result.put("userSessionID", 	userSessionID);
			ivrService.UpdateSendTime(result);
			
			// INSERT CALL_SCHEDULER
			result.put("tid", 					userSessionID);
			result.put("userSessionID", 		userSessionID);
			result.put("sessionCompanyName", 	paramMap.getStr("SESSION_USER_NAME"));
			result.put("importance", 			importance);
			result.put("status", 				status);
			callSchedulerService.RegisterData(result);
			
//			UMSExecutorService.addCallRunable(new IVRRunnable_Resend(result, ivrlogseq));

		}
		
		// MAPPER TABLE??? ????????? ??????
		ivrService.UpdateMapperSendTime(paramMap);

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * ????????????
	 */
	@RequestMapping("/IVRRepeat")
	public @ResponseBody ResultMessage IVRRepeat()
	{
		MyMap 	paramMap 	= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		String 	sessionId 	= FrameworkUtils.generateSessionID();
		String 	callerID 	= paramMap.getStr("callerID", "");
		
		paramMap.put("tid", sessionId);
		paramMap.put("ttsMentIntro01", paramMap.getStr("ttsMent"));

		// ???????????? ?????? ???
		if(paramMap.getStr("isReceive").equals("Y"))
		{
			//
		}

		// ???????????? ????????? ???
		if(paramMap.getStr("isSave").equals("Y"))
		{
//			resultTTSMake.put("ttsMentTitle", paramMap.getStr("ttsTitle"));
//			arsalarmttsService.RegisterData(resultTTSMake);
		}
		
//		String nextcallDate = "";
//		if(!paramMap.getStr("sendDay", "").equals(""))
//		{
//			nextcallDate = paramMap.getStr("sendDay") + " " + paramMap.getStr("sendHour") + ":" + paramMap.getStr("sendMinute");
//			paramMap.put("reservationDate", nextcallDate);
//			paramMap.put("nextcallDate", nextcallDate);
//		}
//		else if(!paramMap.getStr("repeatDay", "").equals(""))
//		{
//			nextcallDate = paramMap.getStr("repeatDay") + " " + paramMap.getStr("repeatHour") + ":" + paramMap.getStr("repeatMinute");
//			paramMap.put("reservationDate", nextcallDate);
//			paramMap.put("nextcallDate", nextcallDate);
//		}
		
		// INSERT IVRLOGMAPPER TABLE
		paramMap.put("reservationDate", paramMap.getStr("repeatDate"));
		paramMap.put("callerId", 		paramMap.getStr("callerID"));
		ivrService.RegisterMapperData(paramMap);
		int ivrlogmapperseq = paramMap.getInt("ivrlogmapperseq", 0);
		
		try
		{
			JSONParser 	parser 		= new JSONParser();
			JSONArray 	targetArr 	= (JSONArray)parser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("targetArr")));
			
			// TTS ?????? ????????? ??????
			if(paramMap.getStr("sendType").equals("A"))
			{
				String 		companyName 	= paramMap.getStr("SESSION_USER_NAME");
				String 		msg 			= FrameworkUtils.unescapeHtml(paramMap.getStr("ttsMent") + "<VTML_PAUSE TIME=\"500\" />").replaceAll("<br/>", ". ").trim();
				
				// ?????????
				if(paramMap.getStr("callType").equals("A"))
				{
					// TTS wav ?????? ??????
					MyMap resultTTSMake 	= ivrSender.RealTimeTTSMake(sessionId, companyName, msg);
					
					// TTS wav ?????? IVR ????????? ??????
					resultTTSMake.put("ivrlogmapperseq", ivrlogmapperseq);
					MyMap resultTTSPut 		= ivrSender.TTSFileIVRServerPut(resultTTSMake);
					
					for(int i=0; i<targetArr.size(); i++)
					{
						
						String 		userSessionID 	= FrameworkUtils.generateSessionID();
						JSONObject 	target 			= (JSONObject) targetArr.get(i);
						String 		phonenumber 	= target.get("phonenumber").toString().replaceAll("-", "").trim();
						String 		sendTime 		= paramMap.getStr("sendTime");// A:??????, B:??????, C:??????
						String 		type 			= "B";	// A:????????????, B:??????
						
						// INSERT IVRLOG TABLE
						paramMap.put("phonenumber", 		phonenumber);
						paramMap.put("name", 				target.get("name").toString());
						paramMap.put("userSessionID", 		userSessionID);
						paramMap.put("statusCompletion", 	"R");
						paramMap.put("nextcallDate", 		paramMap.getStr("repeatDate"));
						ivrService.RegisterData(paramMap);
						
						String ivrlogseq = paramMap.getStr("ivrlogseq");
						
						UMSExecutorService.addCallRunable(new IVRRunnable_0001(paramMap, sessionId, companyName, msg, ivrlogseq, type, sendTime, resultTTSPut, callerID));
					}
				}
				// ?????????
				else
				{
					JSONArray mentArr = (JSONArray)parser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("mentArr")));
					
					MyMap 		resultTTSMake 		= null;
					MyMap 		resultTTSPut 		= null;
					List<MyMap> resultTTSPutList 	= new ArrayList<>();
					
					// ??? ?????? ??? TTS wav ?????? ??????
					int orderNum = 0;
					for(int i=0; i<mentArr.size(); i++)
					{
						orderNum++;
						String tid = FrameworkUtils.generateSessionID();
						
						// TTS wav ?????? ??????
						resultTTSMake = ivrSender.RealTimeTTSMake(tid, companyName, mentArr.get(i).toString());
						resultTTSMake.put("ivrlogmapperseq", 	ivrlogmapperseq);
						resultTTSMake.put("orderNum", 			orderNum);
						
						// TTS wav ?????? IVR ????????? ??????
						resultTTSPut = ivrSender.TTSFileIVRServerPut(resultTTSMake);
						resultTTSPutList.add(resultTTSPut);
					}
					
					for(int i=0; i<targetArr.size(); i++)
					{
						String 		userSessionID 	= FrameworkUtils.generateSessionID();
						JSONObject 	target 			= (JSONObject) targetArr.get(i);
						String 		phonenumber 	= target.get("phonenumber").toString().replaceAll("-", "");
						String 		sendTime 		= paramMap.getStr("sendTime"); // A:??????, B:??????, C:??????

						// INSERT IVRLOG TABLE
						paramMap.put("phonenumber", 		phonenumber);
						paramMap.put("name", 				target.get("name").toString());
						paramMap.put("userSessionID", 		userSessionID);
						paramMap.put("statusCompletion", 	"R");
						paramMap.put("nextcallDate", 		paramMap.getStr("repeatDate"));
						ivrService.RegisterData(paramMap);
						
						String ivrlogseq = paramMap.getStr("ivrlogseq");
						
						UMSExecutorService.addCallRunable(new IVRRunnable_0002(sessionId, ivrlogseq, sendTime, callerID));
					}
				}
			}
		}
		catch (ParseException e)
		{
			FrameworkUtils.exceptionToString(e);
		}

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * ???????????? ????????? ????????????
	 */
	@RequestMapping("/SendRepeatCall")
	public @ResponseBody ResultMessage SendRepeatCall(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		// ????????? ?????????
		List<MyCamelMap> 	resultList 		= ivrService.SelectSendRepeatCallList(paramMap);
		for(MyCamelMap result : resultList)
		{
			String 	userSessionID 	= FrameworkUtils.generateSessionID();
//			String 	ivrlogseq 		= result.getStr("ivrlogseq");
			int 	importance 		= 0;
			String 	status 			= "W";

			// ???????????? ???????????? ??????
			result.put("sendTime", 			"A");
			result.put("statusCompletion", 	"I");
			result.put("userSessionID", 	userSessionID);
			ivrService.UpdateSendTime(result);
			
			// INSERT CALL_SCHEDULER
			result.put("tid", 					userSessionID);
			result.put("userSessionID", 		userSessionID);
			result.put("sessionCompanyName", 	paramMap.getStr("SESSION_USER_NAME"));
			result.put("importance", 			importance);
			result.put("status", 				status);
			callSchedulerService.RegisterData(result);
			
//			String 	userSessionID 	= FrameworkUtils.generateSessionID();
//			String 	ivrlogseq 		= result.getStr("ivrlogseq");
//			String 	ivrlogmapperseq = result.getStr("ivrlogmapperseq");
//			
//			result.put("sendTime", 				'A');
//			result.put("statusCompletion", 		"I");
//			result.put("tid", 					userSessionID);
//			result.put("userSessionID", 		userSessionID);
//			result.put("ivrlogmapperseq", 		ivrlogmapperseq);
//			result.put("sessionCompanyName", 	paramMap.getStr("SESSION_USER_NAME"));
//			result.put("statusCompletion", 		"I");
//			
//			ivrService.RegisterData(result);
//
//			UMSExecutorService.addCallRunable(new IVRRunnable_Resend(result, ivrlogseq));
		}
		
		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * wav ?????? ??????.
	 */
	@RequestMapping("/recordPlay")
	public String recordPlay(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		model.addAttribute("fileUrl", 	paramMap.getStr("fileUrl"));

		return "/msg/recordPlay";
	}
	
}
