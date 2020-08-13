//package com.inbiznetcorp.acs.scheduled.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
//import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
//import com.inbiznetcorp.acs.framework.mymap.MyMap;
//import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
//import com.inbiznetcorp.acs.framework.websocket.IVRSender;
//import com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo;
//import com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper;
//import com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper;
//import com.inbiznetcorp.acs.mapper.ttsfileputhistory.TTSfileputhistoryMapper;
//
//public class CallScheduled_backup implements Runnable
//{
//	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CallScheduled_backup.class);
//	
//	BasicInfo 				basicInfo 				= (BasicInfo) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo");
//	SchedulerMapper 		schedulerMapper 		= (SchedulerMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper");
//	IVRSender 				ivrSender 				= (IVRSender) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.IVRSender");
//	TTSfileputhistoryMapper ttsfileputhistoryMapper = (TTSfileputhistoryMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.ttsfileputhistory.TTSfileputhistoryMapper");
//	IvrlogMapper 			ivrlogMapper 			= (IvrlogMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper");
//
//	@Override
//	public void run()
//	{
//		LOGGER.info("=====================================");
//		LOGGER.info("CallScheduled is ready...");
//		LOGGER.info("=====================================");
//		
//		MyMap paramMap = new MyMap();
//		
//		while(true)
//		{
//			// 보내야할 일반 콜이 있을 때
//			if(basicInfo.isRunNormal())
//			{
//				paramMap.put("sessionNormalCps", 	basicInfo.getSESSION_NORMAL_CPS());
//				paramMap.put("sessionImportantCps", basicInfo.getSESSION_IMPORTANT_CPS());
//				
//				LOGGER.info("Normal Call Start..");
//				// 일반 콜 List
//				List<MyCamelMap> callList = schedulerMapper.SelectNormalCallList(paramMap);
//				
//				if(callList.size() > 0)
//				{
//					// 콜 발송
//					sendCall(callList);
//				}
//				else
//				{
//					// while문 중지
//					// 보낼 일반 콜이 없음.
//					basicInfo.setRunNormal(false);
//					LOGGER.info("Normal Call End..");
//				}
//			}
//			
//			// 보내야할 중요 콜이 있을 때
//			if(basicInfo.isRunImportant())
//			{
//				paramMap.put("sessionNormalCps", 	FrameworkBeans.findSessionBean().getCps()*(0.7));
//				paramMap.put("sessionImportantCps", FrameworkBeans.findSessionBean().getCps()*(0.3));
//
//				LOGGER.info("Important Call Start..");
//				// 중요 콜 List
//				List<MyCamelMap> callList = schedulerMapper.SelectImportantCallList(paramMap);
//				
//				if(callList.size() > 0)
//				{
//					// 콜 발송
//					sendCall(callList);
//				}
//				else
//				{
//					// while문 중지
//					// 보낼 일반 콜이 없음.
//					basicInfo.setRunImportant(false);
//					LOGGER.info("Important Call End..");
//				}
//			}
//			
//		}
//	}
//	
//	
//	// 콜 발송
//	public void sendCall(List<MyCamelMap> callList)
//	{
//		for(MyCamelMap call : callList)
//		{
//			// 알림형
//			if(call.getStr("callType").equals("A"))
//			{
//				// CallStatus = 'I'로 변경
//				schedulerMapper.UpdateCallStatusI(call);
//				
//				MyMap resultTTSPlay = ivrSender.wavPlay0001(call, call.getStr("phonenumber"));	// 발송
//				if(resultTTSPlay != null)
//				{
//					resultTTSPlay.put("ivrlogseq", call.getStr("seqivrlog"));
//					
//					// IVRLOG TABLE에 결과 UPDATE
//					ivrlogMapper.UpdateIVRLog(resultTTSPlay);
//				}
//
//				// CallStatus = 'C'로 변경
//				schedulerMapper.UpdateCallStatusC(call);
//			}
//			// 응답형
//			else if(call.getStr("callType").equals("B"))
//			{
//				List<MyMap> resultTTSPutList 	= new ArrayList<>();
//				
//				// ment 순서대로 get
//				List<MyMap> TTSPutList 			= ttsfileputhistoryMapper.SelectTTSfileputhistoryList(call.getStr("seqivrlog"));
//				for(MyMap TTSPut : TTSPutList)
//				{
//					String tid = FrameworkUtils.generateSessionID();
//					
//					TTSPut.put("tid", tid);
//					resultTTSPutList.add(TTSPut);
//				}
//
//				// CallStatus = 'I'로 변경
//				schedulerMapper.UpdateCallStatusI(call);
//				
//				MyMap resultTTSPlay = ivrSender.wavPlay0002(resultTTSPutList, call.getStr("phonenumber"));	// 발송
//				if(resultTTSPlay != null)
//				{
//					resultTTSPlay.put("ivrlogseq", call.getStr("seqivrlog"));
//					
//					// IVRLOG TABLE에 결과 UPDATE
//					ivrlogMapper.UpdateIVRLog(resultTTSPlay);
//				}
//				
//				// CallStatus = 'C'로 변경
//				schedulerMapper.UpdateCallStatusC(call);
//			}
//		}
//	}
//	
//}
