package com.inbiznetcorp.acs.framework.websocket;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper;
import com.inbiznetcorp.acs.web.ivr.service.IVRService;

public class IVRRunnable implements Runnable
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(IVRRunnable.class);
	
	IVRSender 		ivrSender 		= (IVRSender) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.IVRSender");
	IvrlogMapper 	ivrlogMapper 	= (IvrlogMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper");
	IVRService		ivrService 		= (IVRService) FrameworkBeans.findBean("com.inbiznetcorp.acs.web.ivr.service.IVRService");

	private MyMap 	mParamMap 		= null;
	private MyMap 	mResultTTSPut 	= null;
	private int 	mIvrlogseq 		= 0;
	private String 	mPhonenumber 	= null;
	private String 	mCallerID 		= null;
	private String 	mAuthReqNumber 	= null;

	public IVRRunnable()
	{

	}
	public IVRRunnable(MyMap paramMap, MyMap resultTTSPut, int ivrlogseq, String phonenumber, String callerID, String authReqNumber)
	{
		this.mParamMap   	= paramMap;
		this.mResultTTSPut 	= resultTTSPut;
		this.mIvrlogseq 	= ivrlogseq;
		this.mPhonenumber 	= phonenumber;
		this.mCallerID 		= callerID;
		this.mAuthReqNumber = authReqNumber;
	}
//	public IVRRunnable(MyMap paramMap, MyMap resultTTSPut, int ivrlogseq, String phonenumber)
//	{
//		this.mParamMap   	= paramMap;
//		this.mResultTTSPut 	= resultTTSPut;
//		this.mIvrlogseq 	= ivrlogseq;
//		this.mPhonenumber 	= phonenumber;
//	}

	@Override
	public void run()
	{
		MyMap 			resultTTSPlay 	= null;	// 콜 발송 결과

		LOGGER.info("Call Ready..");
		
		System.out.println("mIvrlogseq : " + mIvrlogseq);
		
		// 알림형
		if(mParamMap.getStr("callType").equals("A"))
		{
			LOGGER.info("CallType_A..");
			resultTTSPlay = ivrSender.wavPlay0001(mResultTTSPut, mPhonenumber, "B", mCallerID, mAuthReqNumber);
		}
		// 응답형
		else if(mParamMap.getStr("callType").equals("B"))
		{
			LOGGER.info("CallType_B..");
//			resultTTSPlay = ivrSender.wavPlay0002(mResultTTSPut, mPhonenumber, mRequstNumber);
		}

		LOGGER.info("TTSPlay_COMPLETE!!");

		System.out.println("##### resultTTSPlay : " + resultTTSPlay);
		if(resultTTSPlay != null)
		{
			resultTTSPlay.put("ivrlogseq", mIvrlogseq);
			ivrlogMapper.UpdateIVRLog(resultTTSPlay);
		}
		else
		{
			resultTTSPlay = new MyMap();
			resultTTSPlay.put("ivrlogseq", mIvrlogseq);
			ivrlogMapper.UpdateStatus(resultTTSPlay);
		}

//		LOGGER.info(mParamMap.getStr("userSessionID")+"_IVRLOG_UPDATE!!");

	}


}
