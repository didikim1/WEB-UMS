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
	 * 이어폰 듣기
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
	 * ARS로 듣기
	 */
	@RequestMapping("/IVREntrustingListen")
	public @ResponseBody ResultMessage IVREntrustingListen(Model model)
	{
		MyMap 	paramMap 	= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		String 	tid 		= FrameworkUtils.generateSessionID();
		String 	companyName = paramMap.getStr("SESSION_USER_NAME");
		String 	msg 		= FrameworkUtils.unescapeHtml(paramMap.getStr("msg") + "<VTML_PAUSE TIME=\"500\" />").replaceAll("<br/>", ". ").trim();
		String 	phonenumber = paramMap.getStr("phonenumber").replaceAll("-", "").replaceAll(",", "").trim();
		String 	type 		= "A"; 	// A:들어보기, B:발송
		String 	callerid 	= paramMap.getStr("callerid");
		
		UMSExecutorService.addCallRunable(new IVRRunnable_0001(tid, companyName, msg, phonenumber, type, callerid));

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * 음성메세지 발송
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

		// 음성회신 받을 때
		if(paramMap.getStr("isReceive").equals("Y"))
		{
			//
		}

		// 모음함에 저장할 때
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
			// TTS 직접 입력한 경우
			if(paramMap.getStr("sendType").equals("A"))
			{
				String 		companyName 	= paramMap.getStr("SESSION_USER_NAME");
				String 		msg 			= FrameworkUtils.unescapeHtml(paramMap.getStr("ttsMent") + "<VTML_PAUSE TIME=\"500\" />").replaceAll("<br/>", ". ").trim();
				
				LOGGER.info("CALLTYPE ::: " + paramMap.getStr("callType"));
				// 알림형
				if(paramMap.getStr("callType").equals("A"))
				{
					// TTS wav 파일 생성
					MyMap resultTTSMake 	= ivrSender.RealTimeTTSMake(sessionId, companyName, msg);
					
					// TTS wav 파일 IVR 서버에 전송
					resultTTSMake.put("ivrlogmapperseq", ivrlogmapperseq);
					MyMap resultTTSPut 		= ivrSender.TTSFileIVRServerPut(resultTTSMake);
					
					for(int i=0; i<targetArr.size(); i++)
					{
						String 		userSessionID 	= FrameworkUtils.generateSessionID();
						JSONObject 	target 			= (JSONObject) targetArr.get(i);
						String 		phonenumber 	= target.get("phonenumber").toString().replaceAll("-", "").trim();
						String 		sendTime 		= paramMap.getStr("sendTime");// A:즉시, B:예약, C:주기
						String 		type 			= "B";	// A:들어보기, B:발송
						
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
				// 응답형
				else
				{
					JSONArray ment2Arr = (JSONArray)parser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("originTTSMent2")));
					JSONArray ment3Arr = (JSONArray)parser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("originTTSMent3")));
					
					MyMap 		resultTTSMake 		= null;
					MyMap 		resultTTSPut 		= null;
					List<MyMap> resultTTSPutList 	= new ArrayList<>();
					LOGGER.info("응답형 질문멘트 정보 ::: "+ment2Arr.toString());
					LOGGER.info("응답형 답변멘트 정보 ::: "+ment2Arr.toString());
					String message = paramMap.getStr("originTTSMent1") ;
					// 콜 발송 전 TTS wav 파일 생성
					for(int i=0; i<ment2Arr.size(); i++)
					{
						String tid = FrameworkUtils.generateSessionID();
						if(i != 0) message = ""; 
						message += ment2Arr.get(i)+","+ment3Arr.get(i);
						LOGGER.info("ment info :: " + message + " // S");
						// TTS wav 파일 생성
						resultTTSMake = ivrSender.RealTimeTTSMake(tid, companyName, message);
//						resultTTSMake = ivrSender.RealTimeTTSMake(tid, companyName, mentArr.get(i).toString().replace(temp, ""));
						resultTTSMake.put("ivrlogmapperseq", 	ivrlogmapperseq);
						resultTTSMake.put("orderNum", 			(i+1));
						
						// TTS wav 파일 IVR 서버에 전송
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
						String 		sendTime 		= paramMap.getStr("sendTime"); // A:즉시, B:예약, C:주기

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
	 * wav파일로 콜 발송
	 */
	@RequestMapping("/SendCallByWav")
	public @ResponseBody ResultMessage SendCallByWav(Model model)
	{
		MyMap 		paramMap 	= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		JSONParser 	parser 		= new JSONParser();
		
		String callerID =paramMap.getStr("callerID", ""); 
		
		// .wav 파일 정보
		MyCamelMap fileInfo = ivrService.SelectFile(paramMap);
		
		String filePath		= fileInfo.getStr("uploadPath");
		String tid 			= FrameworkUtils.generateSessionID();
		String groupName 	= paramMap.getStr("SESSION_USER_NAME");
		
		// INSERT IVRLOGMAPPER TABLE
		paramMap.put("reservationDate", paramMap.getStr("nextcallDate", null));
		paramMap.put("round", 			1);
		paramMap.put("callerId", 		paramMap.getStr("callerID"));
		ivrService.RegisterMapperData(paramMap);
		
		// .wav 파일 서버에 전송
		MyMap resultWavUpload = ivrSender.WAVFileUpload(filePath, tid, groupName);
		if(resultWavUpload == null)
		{
			return new ResultMessage(ResultCode.RESULT_BAD_REQUEST, "fail..");
		}
		else
		{
			// 서버에 전송된 .wav 파일을 실제로 사용하는 디렉토리로 이동
			resultWavUpload.put("ivrlogmapperseq", paramMap.getInt("ivrlogmapperseq", 0));
			MyMap resultTTSPut = ivrSender.TTSFileIVRServerPut(resultWavUpload);
			
			try
			{
				// 받는사람을 직접/주소록 추가한 경우
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
										
										// CPS를 15로 맞추기 위해서, 70ms*15=1050ms -> 15콜을 약1.05초
										// CPS를 10으로 맞추기 위해서, 100ms*10=1000ms -> 10콜을 약1.00초
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
				// 받는사람을 excel 파일로 추가한 경우
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
									
									// CPS를 15로 맞추기 위해서, 70ms*15=1050ms -> 15콜을 약1.05초
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
		
		// mapper 정보
		MyCamelMap ivrlogmapper = ivrService.SelectIvrlogmapper(paramMap);
		LOGGER.info("ivrlogmapper : " + ivrlogmapper);
		
		String 	callerId 	= ivrlogmapper.getStr("callerId");
		
		// .wav 파일 정보
		MyCamelMap resultTTSPut = ivrService.SelectTTSFile(paramMap);
		LOGGER.info("resultTTSPut : " + resultTTSPut);
		
		// 재발송 대상 리스트
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
					
					// CPS를 15로 맞추기 위해서, 70ms*15=1050ms -> 15콜을 약1.05초
					try { Thread.sleep(70); } catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
		}).start();
		
		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * 음성메세지 그룹전체 재발송
	 */
	@RequestMapping("/RetryGroupIVREntrusting")
	public @ResponseBody ResultMessage RetryGroupIVREntrusting(Model model)
	{
		MyMap 	paramMap 		= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		String 	userSessionID 	= FrameworkUtils.generateSessionID();

		// 재발송 리스트
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
	 * 음성메세지 개인 재발송
	 */
	@RequestMapping("/RetryIVREntrusting")
	public @ResponseBody ResultMessage RetryIVREntrusting(Model model)
	{
		MyMap 		paramMap 		= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		String 		userSessionID 	= FrameworkUtils.generateSessionID();
		
		// 재발송 대상 IVRLOG
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
	 * 예약발송
	 */
	@RequestMapping("/IVRReservation")
	public @ResponseBody ResultMessage IVRReservation()
	{
		MyMap 	paramMap 	= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		String 	sessionId 	= FrameworkUtils.generateSessionID();
		String 	callerID 	= paramMap.getStr("callerID", "");
		
		paramMap.put("tid", sessionId);
		paramMap.put("ttsMentIntro01", paramMap.getStr("ttsMent"));

		// 음성회신 받을 때
		if(paramMap.getStr("isReceive").equals("Y"))
		{
			//
		}

		// 모음함에 저장할 때
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
			
			// TTS 직접 입력한 경우
			if(paramMap.getStr("sendType").equals("A"))
			{
				String 		companyName 	= paramMap.getStr("SESSION_USER_NAME");
				String 		msg 			= FrameworkUtils.unescapeHtml(paramMap.getStr("ttsMent") + "<VTML_PAUSE TIME=\"500\" />").replaceAll("<br/>", ". ").trim();
				
				// 알림형
				if(paramMap.getStr("callType").equals("A"))
				{
					// TTS wav 파일 생성
					MyMap resultTTSMake 	= ivrSender.RealTimeTTSMake(sessionId, companyName, msg);
					
					// TTS wav 파일 IVR 서버에 전송
					resultTTSMake.put("ivrlogmapperseq", ivrlogmapperseq);
					MyMap resultTTSPut 		= ivrSender.TTSFileIVRServerPut(resultTTSMake);
					
					for(int i=0; i<targetArr.size(); i++)
					{
						String 		userSessionID 	= FrameworkUtils.generateSessionID();
						JSONObject 	target 			= (JSONObject) targetArr.get(i);
						String 		phonenumber 	= target.get("phonenumber").toString().replaceAll("-", "").trim();
						String 		sendTime 		= paramMap.getStr("sendTime");// A:즉시, B:예약, C:주기
						String 		type 			= "B";	// A:들어보기, B:발송
						
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
				// 응답형
				else
				{
					JSONArray mentArr = (JSONArray)parser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("mentArr")));
					
					MyMap 		resultTTSMake 		= null;
					MyMap 		resultTTSPut 		= null;
					List<MyMap> resultTTSPutList 	= new ArrayList<>();
					
					// 콜 발송 전 TTS wav 파일 생성
					int orderNum = 0;
					for(int i=0; i<mentArr.size(); i++)
					{
						orderNum++;
						String tid = FrameworkUtils.generateSessionID();
						
						// TTS wav 파일 생성
						resultTTSMake = ivrSender.RealTimeTTSMake(tid, companyName, mentArr.get(i).toString());
						resultTTSMake.put("ivrlogmapperseq", 	ivrlogmapperseq);
						resultTTSMake.put("orderNum", 			orderNum);
						
						// TTS wav 파일 IVR 서버에 전송
						resultTTSPut = ivrSender.TTSFileIVRServerPut(resultTTSMake);
						resultTTSPutList.add(resultTTSPut);
					}
					
					for(int i=0; i<targetArr.size(); i++)
					{
						String 		userSessionID 	= FrameworkUtils.generateSessionID();
						JSONObject 	target 			= (JSONObject) targetArr.get(i);
						String 		phonenumber 	= target.get("phonenumber").toString().replaceAll("-", "");
						String 		sendTime 		= paramMap.getStr("sendTime"); // A:즉시, B:예약, C:주기

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
	 * 예약 대기중인 콜을 즉시 발송
	 */
	@RequestMapping("/SendWaitingCall")
	public @ResponseBody ResultMessage SendWaitingCall()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		// 예약콜 리스트
		List<MyCamelMap> 	resultList 		= ivrService.SelectRetryList(paramMap);
		for(MyCamelMap result : resultList)
		{
			String 	userSessionID 	= FrameworkUtils.generateSessionID();
//			String 	ivrlogseq 		= result.getStr("ivrlogseq");
			int 	importance 		= 0;
			String 	status 			= "W";

			// 예약콜을 즉시콜로 변경
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
		
		// MAPPER TABLE도 즉시로 변경
		ivrService.UpdateMapperSendTime(paramMap);

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * 주기발송
	 */
	@RequestMapping("/IVRRepeat")
	public @ResponseBody ResultMessage IVRRepeat()
	{
		MyMap 	paramMap 	= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		String 	sessionId 	= FrameworkUtils.generateSessionID();
		String 	callerID 	= paramMap.getStr("callerID", "");
		
		paramMap.put("tid", sessionId);
		paramMap.put("ttsMentIntro01", paramMap.getStr("ttsMent"));

		// 음성회신 받을 때
		if(paramMap.getStr("isReceive").equals("Y"))
		{
			//
		}

		// 모음함에 저장할 때
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
			
			// TTS 직접 입력한 경우
			if(paramMap.getStr("sendType").equals("A"))
			{
				String 		companyName 	= paramMap.getStr("SESSION_USER_NAME");
				String 		msg 			= FrameworkUtils.unescapeHtml(paramMap.getStr("ttsMent") + "<VTML_PAUSE TIME=\"500\" />").replaceAll("<br/>", ". ").trim();
				
				// 알림형
				if(paramMap.getStr("callType").equals("A"))
				{
					// TTS wav 파일 생성
					MyMap resultTTSMake 	= ivrSender.RealTimeTTSMake(sessionId, companyName, msg);
					
					// TTS wav 파일 IVR 서버에 전송
					resultTTSMake.put("ivrlogmapperseq", ivrlogmapperseq);
					MyMap resultTTSPut 		= ivrSender.TTSFileIVRServerPut(resultTTSMake);
					
					for(int i=0; i<targetArr.size(); i++)
					{
						
						String 		userSessionID 	= FrameworkUtils.generateSessionID();
						JSONObject 	target 			= (JSONObject) targetArr.get(i);
						String 		phonenumber 	= target.get("phonenumber").toString().replaceAll("-", "").trim();
						String 		sendTime 		= paramMap.getStr("sendTime");// A:즉시, B:예약, C:주기
						String 		type 			= "B";	// A:들어보기, B:발송
						
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
				// 응답형
				else
				{
					JSONArray mentArr = (JSONArray)parser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("mentArr")));
					
					MyMap 		resultTTSMake 		= null;
					MyMap 		resultTTSPut 		= null;
					List<MyMap> resultTTSPutList 	= new ArrayList<>();
					
					// 콜 발송 전 TTS wav 파일 생성
					int orderNum = 0;
					for(int i=0; i<mentArr.size(); i++)
					{
						orderNum++;
						String tid = FrameworkUtils.generateSessionID();
						
						// TTS wav 파일 생성
						resultTTSMake = ivrSender.RealTimeTTSMake(tid, companyName, mentArr.get(i).toString());
						resultTTSMake.put("ivrlogmapperseq", 	ivrlogmapperseq);
						resultTTSMake.put("orderNum", 			orderNum);
						
						// TTS wav 파일 IVR 서버에 전송
						resultTTSPut = ivrSender.TTSFileIVRServerPut(resultTTSMake);
						resultTTSPutList.add(resultTTSPut);
					}
					
					for(int i=0; i<targetArr.size(); i++)
					{
						String 		userSessionID 	= FrameworkUtils.generateSessionID();
						JSONObject 	target 			= (JSONObject) targetArr.get(i);
						String 		phonenumber 	= target.get("phonenumber").toString().replaceAll("-", "");
						String 		sendTime 		= paramMap.getStr("sendTime"); // A:즉시, B:예약, C:주기

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
	 * 주기발송 메세지 즉시발송
	 */
	@RequestMapping("/SendRepeatCall")
	public @ResponseBody ResultMessage SendRepeatCall(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		// 주기콜 리스트
		List<MyCamelMap> 	resultList 		= ivrService.SelectSendRepeatCallList(paramMap);
		for(MyCamelMap result : resultList)
		{
			String 	userSessionID 	= FrameworkUtils.generateSessionID();
//			String 	ivrlogseq 		= result.getStr("ivrlogseq");
			int 	importance 		= 0;
			String 	status 			= "W";

			// 예약콜을 즉시콜로 변경
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
	 * wav 파일 듣기.
	 */
	@RequestMapping("/recordPlay")
	public String recordPlay(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		model.addAttribute("fileUrl", 	paramMap.getStr("fileUrl"));

		return "/msg/recordPlay";
	}
	
}
