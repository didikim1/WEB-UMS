package com.inbiznetcorp.acs.framework.websocket;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.common.ServiceCommon;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo;
import com.inbiznetcorp.acs.mapper.result.ResultMapper;
import com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper;
import com.inbiznetcorp.acs.web.ivr.service.IVRService;

public class IVRRunnable_0001 implements Runnable
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(IVRRunnable_0001.class);
	
	IVRSender 		ivrSender 		= (IVRSender) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.IVRSender");
	IVRService 		ivrService 		= (IVRService) FrameworkBeans.findBean("com.inbiznetcorp.acs.web.ivr.service.IVRService");
	SchedulerMapper schedulerMapper = (SchedulerMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper");
	ResultMapper 	resultMapper 	= (ResultMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.result.ResultMapper");
	BasicInfo 		basicInfo 		= (BasicInfo) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo");

	private MyMap 		mParamMap 			= null;	// 고유번호
	private String 		mTid 				= null;	// TTS wav파일 고유번호
	private String 		mCompanyName 		= null;	// 고객사
	private String 		mMsg 				= null;	// 메세지
	private String  	mIvrlogseq 			= null; // ivrlogseq
	private String  	mIvrlogmapperseq 	= null; // ivrlogmapperseq
	private String  	mName 				= null; // 수신자 이름
	private String 		mPhonenumber 		= null;	// 수신자 전화번호
	private String 		mCallType 			= null;	// A:알림, B:응답, C:들어보기, D:설문지
	private String 		mSendTime 			= null;	// A:즉시, B:예약, C:주기
	private String 		mType 				= null;	// A:들어보기, B:발송
	private int 		mImportance 		= 0;	// 0:일반, 1:중요
	private MyMap 		mResultTTSPut 		= null;	// wav 파일을 서버에 put한 결과
	private String 		mCallerID 			= null;	// 발신번호

	// 들어보기
	public IVRRunnable_0001(String tid, String companyName, String msg, String phonenumber, String type)
	{
		this.mTid   		= tid;
		this.mCompanyName   = companyName;
		this.mMsg   		= msg;
		this.mPhonenumber   = phonenumber;
		this.mType 			= type;
	}

	public IVRRunnable_0001(MyMap paramMap, String tid, String companyName, String msg, String ivrlogseq, String type, String sendTime, MyMap resultTTSPut, String callerID)
	{
		this.mParamMap   	= paramMap;
		this.mTid   		= tid;
		this.mCompanyName   = companyName;
		this.mMsg   		= msg;
		this.mIvrlogseq 	= ivrlogseq;
		this.mType 			= type;
		this.mSendTime 		= sendTime;
		this.mResultTTSPut 	= resultTTSPut;
		this.mCallerID 		= callerID;
	}

	@Override
	public void run()
	{
		// 들어보기
		if(mType.equals("A"))
		{
			MyMap 	resultTTSMake 		= ivrSender.RealTimeTTSMake(mTid, mCompanyName, mMsg);	// TTS wav 파일 생성
			
			MyMap 	resultTTSPut 		= ivrSender.TTSFileIVRServerPut(resultTTSMake);			// TTS wav 파일 IVR 서버에 전송
			int 	seqttsputhistory 	= resultTTSPut.getInt("seqttsputhistory");
			
			LOGGER.info(mPhonenumber + "_LISTEN..");
			
			MyMap 	resultTTSPlay 		= ivrSender.wavPlay0001(resultTTSPut, mPhonenumber, "A", mCallerID, mTid);	// ARS 요청
			
			resultTTSPlay.put("seqttsputhistory", seqttsputhistory);
			ivrService.RegistIVRListenlog(resultTTSPlay);	// IVRLISTENLOG에 이력 등록.
			
			LOGGER.info(mPhonenumber + "_InsertIVRListenlog Complete!!");
		}
		// 알림형 발송
		else if(mType.equals("B"))
		{
//			// TTS wav 파일 생성
//			MyMap resultTTSMake 	= ivrSender.RealTimeTTSMake(mTid, mCompanyName, mMsg);
//			
//			// TTS wav 파일 IVR 서버에 전송
//			resultTTSMake.put("ivrlogseq", mIvrlogseq);
//			MyMap resultTTSPut 		= ivrSender.TTSFileIVRServerPut(resultTTSMake);
			
			// IVRLOG 정보
			MyCamelMap mIVRLog = ivrService.SelectIVRLog(mIvrlogseq);
			mIvrlogmapperseq 	= mIVRLog.getStr("ivrlogmapperseq");
			mName 				= mIVRLog.getStr("name");
			mPhonenumber 		= mIVRLog.getStr("phonenumber").replaceAll("-", "");
			mCallType 			= mIVRLog.getStr("callType");
			
			LOGGER.info(mPhonenumber + "_IVRLOG.."+mIVRLog);
			
			// 모음함에 저장
			if(mParamMap.getStr("isSave").equals("Y"))
			{
				
			}
						
			// 즉시발송
			if(mSendTime.equals("A"))
			{
				LOGGER.info(mPhonenumber + "_SEND_NOW..");
				
				// INSERT CALL_SCHEDULER TABLE
				mResultTTSPut.put("ivrlogseq", 			mIvrlogseq);
				mResultTTSPut.put("ivrlogmapperseq", 	mIvrlogmapperseq);
				mResultTTSPut.put("name", 				mName);
				mResultTTSPut.put("phonenumber", 		mPhonenumber);
				mResultTTSPut.put("callType", 			mCallType);
				mResultTTSPut.put("importance", 		mImportance);
				mResultTTSPut.put("status", 			"W");	// W:대기중, I:진행중, C:완료
				mResultTTSPut.put("userSessionID", 		mTid);
				
				schedulerMapper.RegisterData(mResultTTSPut);
				LOGGER.info("Insert CallSchduler Complete!!");
				
//				// INSERT CALL_SCHEDULER TABLE
//				resultTTSPut.put("ivrlogseq", 		mIvrlogseq);
//				resultTTSPut.put("ivrlogmapperseq", mIvrlogmapperseq);
//				resultTTSPut.put("name", 			mName);
//				resultTTSPut.put("phonenumber", 	mPhonenumber);
//				resultTTSPut.put("callType", 		mCallType);
//				resultTTSPut.put("importance", 		mImportance);
//				resultTTSPut.put("status", 			"W");	// W:대기중, I:진행중, C:완료
//				resultTTSPut.put("userSessionID", 	mTid);
//				
//				schedulerMapper.RegisterData(resultTTSPut);
//				LOGGER.info("Insert CallSchduler Complete!!");
				
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


}
