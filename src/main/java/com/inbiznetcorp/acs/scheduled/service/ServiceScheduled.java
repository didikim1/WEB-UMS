package com.inbiznetcorp.acs.scheduled.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.common.ServiceCommon;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
import com.inbiznetcorp.acs.framework.websocket.IVRRunnable;
import com.inbiznetcorp.acs.framework.websocket.IVRRunnable_0002;
import com.inbiznetcorp.acs.framework.websocket.IVRSender;
import com.inbiznetcorp.acs.framework.websocket.UMSExecutorService;
import com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo;
import com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper;
import com.inbiznetcorp.acs.mapper.ttsfileputhistory.TTSfileputhistoryMapper;
import com.inbiznetcorp.acs.web.ivr.service.IVRService;
import com.inbiznetcorp.acs.web.scheduler.service.CallSchedulerService;

@Service("com.inbiznetcorp.acs.scheduled.service.ServiceScheduled")
public class ServiceScheduled
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ServiceScheduled.class);
	
	IVRSender ivrSender = (IVRSender) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.IVRSender");
	BasicInfo basicInfo = (BasicInfo) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo");

	@Resource(name="com.inbiznetcorp.acs.framework.common.ServiceCommon")
	ServiceCommon mServiceCommon;

	@Resource(name="com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper")
	IvrlogMapper ivrlogMapper;
	
	@Resource(name="com.inbiznetcorp.acs.mapper.ttsfileputhistory.TTSfileputhistoryMapper")
	TTSfileputhistoryMapper ttsfileputhistoryMapper;
	
	@Resource(name="com.inbiznetcorp.acs.web.scheduler.service.CallSchedulerService")
	CallSchedulerService callSchedulerService;

	@Resource(name="com.inbiznetcorp.acs.web.ivr.service.IVRService")
	IVRService mIVRService;

	String loggerNewLine = "\n";

	@Scheduled(cron = "0 * * * * *") //??? ????????? ????????? ???????????????.
	public void ReservationScheduler()
	{
		if("0".equals(mServiceCommon.getJOBScheduledExcute()))
		{
			LOGGER.info(" JOB ???????????? ???????????????, ");
			return;
		}

		String 		 		currentDate				= FrameworkUtils.getDateToStr("yyyyMMddHHmm");
		StringBuffer 		sbLogger				= new StringBuffer();
		MyMap 		 		paramMap				= new MyMap();
		List<MyCamelMap> 	reservationAutoCalls 	= null;	// ????????????
		List<MyCamelMap> 	repeatAutoCalls 		= null;	// ????????????

		sbLogger.append(loggerNewLine+"==========================@Scheduled("+currentDate+")==================================");
        sbLogger.append(loggerNewLine+".......");

        paramMap.put("nextcallDate", currentDate);
        reservationAutoCalls = ivrlogMapper.SelectARSReservation(paramMap);
        sbLogger.append(loggerNewLine+" ARS ?????? ?????? Cnt = " + reservationAutoCalls.size());
        if(reservationAutoCalls.size() > 0)
        {
        	reservationAutoCall(reservationAutoCalls);
        	basicInfo.setNormalCallExist(true);
        	basicInfo.setImportantCallExist(true);
        }

        repeatAutoCalls = ivrlogMapper.SelectARSRepeat(paramMap);
        sbLogger.append(loggerNewLine+" ARS ?????? ?????? Cnt = " + repeatAutoCalls.size());
        if(repeatAutoCalls.size() > 0)
        {
        	repeatAutoCall(repeatAutoCalls);
        	basicInfo.setNormalCallExist(true);
        	basicInfo.setImportantCallExist(true);
        }

        sbLogger.append(loggerNewLine+".......");
        sbLogger.append(loggerNewLine+"=========================//Scheduled("+currentDate+")==================================");

        LOGGER.info(sbLogger.toString());
	}

	/**
	 * ???????????? ???
	 */
	private void reservationAutoCall(List<MyCamelMap> reservationAutoCalls)
	{
		for(MyCamelMap target : reservationAutoCalls)
		{
			String 	userSessionID 	= FrameworkUtils.generateSessionID();
			int 	importance 		= 0;
			String 	status 			= "W";

			// IVRLOG TABLE CALL_STATUS = 'I'??? ??????
			ivrlogMapper.UpdateStatus(target);
			
			if(target.getStr("forElection").equals("Y"))
			{
				MyMap 		paramMap 			= new MyMap();
				paramMap.put("callType", target.getStr("callType"));
				
				int 		ivrlogseq 			= target.getInt("ivrlogseq", 0);
				String 		phonenumber 		= target.getStr("phonenumber", "").replaceAll("-", "");
				
				MyMap searchMap = new MyMap();
				searchMap.put("ivrlogmapperseq", target.getInt("ivrlogmapperseq", 0));
				List<MyMap> resultTTSPutList 	= ttsfileputhistoryMapper.SelectTTSfileputhistoryList(searchMap);
				
				// ?????????
				if(resultTTSPutList.size() == 1)
				{
					MyMap resultTTSPut = resultTTSPutList.get(0);
					resultTTSPut.put("tid", 		userSessionID);
					resultTTSPut.put("groupName", 	target.getStr("groupIdentifier"));
					try
					{
						String requestNumber = FrameworkUtils.generateSessionID();
						UMSExecutorService.addCallRunable(new IVRRunnable(paramMap, resultTTSPut, ivrlogseq, phonenumber, target.getStr("callerId"), requestNumber));
						Thread.sleep(50);
					}
					catch (InterruptedException e)
					{
						FrameworkUtils.exceptionToString(e);
					}
				}
				// ?????????
				else
				{
					UMSExecutorService.addCallRunable(new IVRRunnable_0002(userSessionID, String.valueOf(ivrlogseq), "A", target.getStr("callerId")));
				}
				
			}
			else
			{
				// INSERT CALL_SCHEDULER
				target.put("tid", 					userSessionID);
				target.put("userSessionID", 		userSessionID);
				target.put("sessionCompanyName", 	target.getStr("groupIdentifier", ""));
				target.put("importance", 			importance);
				target.put("status", 				status);
				callSchedulerService.RegisterData(target);
			}
			
			
//			// ?????????
//			if(target.getStr("callType").equals("A"))
//			{
//			}
//			// ?????????
//			else if(target.getStr("callType").equals("B"))
//			{
//			}
		}

	}

	/**
	 * ???????????? ???
	 */
	private void repeatAutoCall(List<MyCamelMap> reservationAutoCalls)
	{
		for(MyCamelMap target : reservationAutoCalls)
		{
			String 	userSessionID 	= FrameworkUtils.generateSessionID();
			int 	importance 		= 0;
			String 	status 			= "W";

			// IVRLOG TABLE CALL_STATUS = 'I'??? ??????
			ivrlogMapper.UpdateStatus(target);
			
			// INSERT CALL_SCHEDULER
			target.put("tid", 					userSessionID);
			target.put("userSessionID", 		userSessionID);
			target.put("sessionCompanyName", 	target.getStr("groupIdentifier", ""));
			target.put("importance", 			importance);
			target.put("status", 				status);
			callSchedulerService.RegisterData(target);
			
			
			
//			String 	userSessionID 	= FrameworkUtils.generateSessionID();
//			MyMap 	resultTTSPlay 	= new MyMap();	// ??? ?????? ??????
//
//			target.put("statusCompletion", 	"I");
//			target.put("tid", 				userSessionID);
//			target.put("userSessionID", 	userSessionID);
//			target.put("filePath", 	target.getStr("recFilePrefix"));
//			target.put("ArsServerWavName", 	target.getStr("recFileName"));
//			target.put("fileUrl", 			target.getStr("recFileUrl"));
//			target.put("ttsMentAuthOk", 	"???????????????.");
//
//			// ?????????
//			if(target.getStr("callType").equals("A"))
//			{
//				resultTTSPlay = ivrSender.wavPlay0001(target, target.getStr("phonenumber"));
//			}
//			// ?????????
//			else if(target.getStr("callType").equals("B"))
//			{
////				resultTTSPlay = ivrSender.wavPlay0002(target, target.getStr("phonenumber"));
//			}
//
//			// ?????? ??? update
//			resultTTSPlay.put("ivrlogseq", 		target.getStr("ivrlogseq"));
//			mIVRService.UpdateIVRLog(resultTTSPlay);

			// ?????? ??? insert
			target.put("nextcallDate", 		NextRepeatDate(target));
			target.put("statusCompletion", 	"R");
			mIVRService.RegisterData(target);

			// IVRLOGMAPPER ????????? update
			mIVRService.UpdateMapperReservationDate(target);

		}
	}

	/**
	 * ???????????? ?????? ?????? ?????????
	 */
	public String NextRepeatDate(MyCamelMap target)
	{
		DateFormat 	dateFormat 	= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar 	cal 		= Calendar.getInstance();
		Timestamp 	ts 			= (Timestamp) target.get("nextcallDate");
		String 		repeatDate 	= "";

		cal.setTime(new Date(ts.getTime()));

		switch(target.getStr("repeatType"))
		{
			// ?????? ??????
			case "A":
				cal.add(Calendar.DATE, 1);
				break;
			// ????????????
			case "B":
				cal.add(Calendar.DATE, 7);
				break;
			// ????????????
			case "C":
				cal.add(Calendar.MONTH, 1);
				break;
			//???~??? ??????
			case "D":
				cal.add(Calendar.DATE, 1);
				Boolean isRun = true;
				while(isRun)
				{
					if((cal.get(Calendar.DAY_OF_WEEK) == 1) || (cal.get(Calendar.DAY_OF_WEEK) == 7))
					{
						cal.add(Calendar.DATE, 1);
					}
					else
					{
						isRun = false;
					}
				}
				break;
		}

		repeatDate = dateFormat.format(cal.getTime());

		return repeatDate;
	}
}
