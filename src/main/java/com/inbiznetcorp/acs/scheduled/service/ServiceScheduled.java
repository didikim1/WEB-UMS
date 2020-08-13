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

	@Scheduled(cron = "0 * * * * *") //매 분마다 정시에 실행됩니다.
	public void ReservationScheduler()
	{
		if("0".equals(mServiceCommon.getJOBScheduledExcute()))
		{
			LOGGER.info(" JOB 스케쥴러 대상이아님, ");
			return;
		}

		String 		 		currentDate				= FrameworkUtils.getDateToStr("yyyyMMddHHmm");
		StringBuffer 		sbLogger				= new StringBuffer();
		MyMap 		 		paramMap				= new MyMap();
		List<MyCamelMap> 	reservationAutoCalls 	= null;	// 예약발송
		List<MyCamelMap> 	repeatAutoCalls 		= null;	// 주기발송

		sbLogger.append(loggerNewLine+"==========================@Scheduled("+currentDate+")==================================");
        sbLogger.append(loggerNewLine+".......");

        paramMap.put("nextcallDate", currentDate);
        reservationAutoCalls = ivrlogMapper.SelectARSReservation(paramMap);
        sbLogger.append(loggerNewLine+" ARS 예약 발송 Cnt = " + reservationAutoCalls.size());
        if(reservationAutoCalls.size() > 0)
        {
        	reservationAutoCall(reservationAutoCalls);
        	basicInfo.setNormalCallExist(true);
        	basicInfo.setImportantCallExist(true);
        }

        repeatAutoCalls = ivrlogMapper.SelectARSRepeat(paramMap);
        sbLogger.append(loggerNewLine+" ARS 주기 발송 Cnt = " + repeatAutoCalls.size());
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
	 * 예약발송 콜
	 */
	private void reservationAutoCall(List<MyCamelMap> reservationAutoCalls)
	{
		for(MyCamelMap target : reservationAutoCalls)
		{
			String 	userSessionID 	= FrameworkUtils.generateSessionID();
			int 	importance 		= 0;
			String 	status 			= "W";

			// IVRLOG TABLE CALL_STATUS = 'I'로 변경
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
				
				// 알림형
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
				// 응답형
				else
				{

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
			
			
//			// 알림형
//			if(target.getStr("callType").equals("A"))
//			{
//			}
//			// 응답형
//			else if(target.getStr("callType").equals("B"))
//			{
//			}
		}

	}

	/**
	 * 주기발송 콜
	 */
	private void repeatAutoCall(List<MyCamelMap> reservationAutoCalls)
	{
		for(MyCamelMap target : reservationAutoCalls)
		{
			String 	userSessionID 	= FrameworkUtils.generateSessionID();
			int 	importance 		= 0;
			String 	status 			= "W";

			// IVRLOG TABLE CALL_STATUS = 'I'로 변경
			ivrlogMapper.UpdateStatus(target);
			
			// INSERT CALL_SCHEDULER
			target.put("tid", 					userSessionID);
			target.put("userSessionID", 		userSessionID);
			target.put("sessionCompanyName", 	target.getStr("groupIdentifier", ""));
			target.put("importance", 			importance);
			target.put("status", 				status);
			callSchedulerService.RegisterData(target);
			
			
			
//			String 	userSessionID 	= FrameworkUtils.generateSessionID();
//			MyMap 	resultTTSPlay 	= new MyMap();	// 콜 발송 결과
//
//			target.put("statusCompletion", 	"I");
//			target.put("tid", 				userSessionID);
//			target.put("userSessionID", 	userSessionID);
//			target.put("filePath", 	target.getStr("recFilePrefix"));
//			target.put("ArsServerWavName", 	target.getStr("recFileName"));
//			target.put("fileUrl", 			target.getStr("recFileUrl"));
//			target.put("ttsMentAuthOk", 	"감사합니다.");
//
//			// 알림형
//			if(target.getStr("callType").equals("A"))
//			{
//				resultTTSPlay = ivrSender.wavPlay0001(target, target.getStr("phonenumber"));
//			}
//			// 응답형
//			else if(target.getStr("callType").equals("B"))
//			{
////				resultTTSPlay = ivrSender.wavPlay0002(target, target.getStr("phonenumber"));
//			}
//
//			// 현재 콜 update
//			resultTTSPlay.put("ivrlogseq", 		target.getStr("ivrlogseq"));
//			mIVRService.UpdateIVRLog(resultTTSPlay);

			// 다음 콜 insert
			target.put("nextcallDate", 		NextRepeatDate(target));
			target.put("statusCompletion", 	"R");
			mIVRService.RegisterData(target);

			// IVRLOGMAPPER 예약일 update
			mIVRService.UpdateMapperReservationDate(target);

		}
	}

	/**
	 * 주기발송 콜의 다음 발송일
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
			// 매일 반복
			case "A":
				cal.add(Calendar.DATE, 1);
				break;
			// 주간반복
			case "B":
				cal.add(Calendar.DATE, 7);
				break;
			// 월간반복
			case "C":
				cal.add(Calendar.MONTH, 1);
				break;
			//월~금 반복
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
