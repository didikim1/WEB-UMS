package com.inbiznetcorp.acs.framework.websocket;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo;
import com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper;
import com.inbiznetcorp.acs.web.ivr.service.IVRService;

public class IVRRunnable_0002 implements Runnable
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(IVRRunnable_0002.class);
	
	IVRService 		ivrService 		= (IVRService) FrameworkBeans.findBean("com.inbiznetcorp.acs.web.ivr.service.IVRService");
	SchedulerMapper schedulerMapper = (SchedulerMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper");
	BasicInfo 		basicInfo 		= (BasicInfo) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo");

	private String  	mIvrlogseq 			= null; // ivrlogseq
	private String  	mIvrlogmapperseq 	= null; // ivrlogmapperseq
	private String  	mName 				= null; // 수신자 이름
	private String 		mPhonenumber 		= null;	// 수신자 전화번호
	private String 		mCallType 			= null;	// A:알림, B:응답, C:들어보기, D:설문지
	private String 		mSendTime 			= null; // A:즉시, B:예약, C:주기
	private int 		mImportance 		= 0;	// 0:일반, 1:중요
	private String 		mCallerID 			= null;	// 발신번호
	private String 		mSessionID 			= null;	// 발신번호

	public IVRRunnable_0002(String sessionID, String ivrlogseq, String sendTime, String callerID)
	{
		this.mSessionID 	= sessionID;
		this.mIvrlogseq 	= ivrlogseq;
		this.mSendTime 		= sendTime;
		this.mCallerID 		= callerID;
	}

	@Override
	public void run()
	{
		// IVRLOG 정보
		MyCamelMap mIVRLog 	= ivrService.SelectIVRLog(mIvrlogseq);
		mIvrlogmapperseq 	= mIVRLog.getStr("ivrlogmapperseq");
		mName 				= mIVRLog.getStr("name");
		mPhonenumber 		= mIVRLog.getStr("phonenumber").replaceAll("-", "");
		mCallType 			= mIVRLog.getStr("callType");
		
		// 즉시발송
		if(mSendTime.equals("A"))
		{
			LOGGER.info(mPhonenumber + "_SEND_NOW..");
			
			// INSERT CALL_SCHEDULER TABLE
			MyMap paramMap = new MyMap();
			paramMap.put("ivrlogseq", 		mIvrlogseq);
			paramMap.put("ivrlogmapperseq", mIvrlogmapperseq);
			paramMap.put("name", 			mName);
			paramMap.put("phonenumber", 	mPhonenumber);
			paramMap.put("callerId", 		mCallerID);
			paramMap.put("callType", 		mCallType);
			paramMap.put("importance", 		0);
			paramMap.put("status", 			"W");
			paramMap.put("userSessionID", 	mSessionID);
			schedulerMapper.RegisterData(paramMap);
			
			if(mImportance == 0)
			{
				basicInfo.setNormalCallExist(true);
				LOGGER.info("Normal Call isRun..	" + basicInfo.isNormalCallExist());
				LOGGER.info("Normal Call cps..		" + basicInfo.getSESSION_NORMAL_CPS());
				LOGGER.info("Normal Call Run.. ");
			}
			else if(mImportance == 1)
			{
				basicInfo.setImportantCallExist(true);
				LOGGER.info("Important Call Run.. " + basicInfo.isImportantCallExist());
				LOGGER.info("Important Call Run.. " + basicInfo.getSESSION_IMPORTANT_CPS());
				LOGGER.info("Important Call Run.. ");
			}
			
		}
		// 예약발송
		else if(mSendTime.equals("B"))
		{
			LOGGER.info(mPhonenumber + "_RESERVATION..");
		}
		// 주기발송
		else if(mSendTime.equals("C"))
		{
			LOGGER.info(mPhonenumber + "_REPEAT..");
		}
		
		
		

	}


}
