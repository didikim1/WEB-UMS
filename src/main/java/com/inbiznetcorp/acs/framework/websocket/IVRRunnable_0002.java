package com.inbiznetcorp.acs.framework.websocket;

import java.util.List;

import org.json.simple.JSONArray;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo;
import com.inbiznetcorp.acs.mapper.result.ResultMapper;
import com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper;
import com.inbiznetcorp.acs.web.ivr.service.IVRService;

public class IVRRunnable_0002 implements Runnable
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(IVRRunnable_0002.class);
	
	IVRSender 		ivrSender 		= (IVRSender) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.IVRSender");
	IVRService 		ivrService 		= (IVRService) FrameworkBeans.findBean("com.inbiznetcorp.acs.web.ivr.service.IVRService");
	SchedulerMapper schedulerMapper = (SchedulerMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper");
	ResultMapper 	resultMapper 	= (ResultMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.result.ResultMapper");
	BasicInfo 		basicInfo 		= (BasicInfo) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo");

	private MyMap 		mParamMap 			= null;	// 고유번호
//	private String 		mTid 				= null;	// 고유번호
//	private String 		mCompanyName 		= null;	// 고객사
//	private JSONArray 	mMentArr 			= null;	// 메세지
	private String  	mIvrlogseq 			= null; // ivrlogseq
	private String  	mIvrlogmapperseq 	= null; // ivrlogmapperseq
	private String  	mName 				= null; // 수신자 이름
	private String 		mPhonenumber 		= null;	// 수신자 전화번호
	private String 		mCallType 			= null;	// A:알림, B:응답, C:들어보기, D:설문지
	private String 		mSendTime 			= null; // A:즉시, B:예약, C:주기
	private int 		mImportance 		= 0;	// 0:일반, 1:중요
//	private List<MyMap> mResultTTSPutList 	= null;	// wav 파일들을 서버에 put한 결과 list

//	public IVRRunnable_0002(MyMap paramMap, String tid, String companyName, JSONArray mentArr, String ivrlogseq, String sendTime)
//	{
//		this.mParamMap   	= paramMap;
//		this.mTid   		= tid;
//		this.mCompanyName   = companyName;
//		this.mMentArr 		= mentArr;
//		this.mIvrlogseq 	= ivrlogseq;
//		this.mSendTime 		= sendTime;
//	}
	
	public IVRRunnable_0002(MyMap paramMap, String tid, String companyName, JSONArray mentArr, String ivrlogseq, String sendTime, List<MyMap> resultTTSPutList)
	{
		this.mParamMap   		= paramMap;
//		this.mTid   			= tid;
//		this.mCompanyName   	= companyName;
//		this.mMentArr 			= mentArr;
		this.mIvrlogseq 		= ivrlogseq;
		this.mSendTime 			= sendTime;
//		this.mResultTTSPutList 	= resultTTSPutList;
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
		
//		MyMap 		resultTTSMake 		= null;
//		MyMap 		resultTTSPut 		= null;
//		List<MyMap> resultTTSPutList 	= new ArrayList<>();
//		
//		
//		// 콜 발송 전 TTS wav 파일 생성
//		int orderNum = 0;
//		for(int i=0; i<mMentArr.size(); i++)
//		{
//			orderNum++;
//			
//			// wav파일 고유 번호
//			String tid = FrameworkUtils.generateSessionID();
//			
//			// TTS wav 파일 생성
//			resultTTSMake = ivrSender.RealTimeTTSMake(tid, mCompanyName, mMentArr.get(i).toString());
//			resultTTSMake.put("ivrlogseq", 	mIvrlogseq);
//			resultTTSMake.put("orderNum", 	orderNum);
//			
//			// TTS wav 파일 IVR 서버에 전송
//			resultTTSPut = ivrSender.TTSFileIVRServerPut(resultTTSMake);
//			resultTTSPutList.add(resultTTSPut);
//		}
		
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
			paramMap.put("callType", 		mCallType);
			paramMap.put("importance", 		0);
			paramMap.put("status", 			"W");
			paramMap.put("userSessionID", 	mParamMap.getStr("userSessionID"));
//			paramMap.put("tid", 			mTid);
			schedulerMapper.RegisterData(paramMap);
			
			if(mImportance == 0)
			{
				basicInfo.setSESSION_NORMAL_CPS(mParamMap.getInt("SESSION_NORMAL_CPS"));
				basicInfo.setNormalCallExist(true);
				LOGGER.info("Normal Call isRun..	" + basicInfo.isNormalCallExist());
				LOGGER.info("Normal Call cps..		" + basicInfo.getSESSION_NORMAL_CPS());
				LOGGER.info("Normal Call Run.. ");
			}
			else if(mImportance == 1)
			{
				basicInfo.setSESSION_IMPORTANT_CPS(mParamMap.getInt("SESSION_IMPORTANT_CPS"));
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
