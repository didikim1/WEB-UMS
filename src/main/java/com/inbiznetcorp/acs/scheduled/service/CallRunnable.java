package com.inbiznetcorp.acs.scheduled.service;

import java.util.ArrayList;
import java.util.List;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
import com.inbiznetcorp.acs.framework.websocket.IVRSender;
import com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo;
import com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper;
import com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper;
import com.inbiznetcorp.acs.mapper.ttsfileputhistory.TTSfileputhistoryMapper;

public class CallRunnable implements Runnable
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CallRunnable.class);
	
	BasicInfo 				basicInfo 				= (BasicInfo) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo");
	SchedulerMapper 		schedulerMapper 		= (SchedulerMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper");
	IVRSender 				ivrSender 				= (IVRSender) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.IVRSender");
	TTSfileputhistoryMapper ttsfileputhistoryMapper = (TTSfileputhistoryMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.ttsfileputhistory.TTSfileputhistoryMapper");
	IvrlogMapper 			ivrlogMapper 			= (IvrlogMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper");
	
	private MyCamelMap 	mCall 			= null;
	private MyMap 		mResultTTSPlay 	= null;
	
	public CallRunnable(MyCamelMap call)
	{
		this.mCall = call;
	}

	@Override
	public void run()
	{
		LOGGER.info("Normal Call Start..");
		
		// 알림형
		if(mCall.getStr("callType").equals("A"))
		{
			String requestNumber = FrameworkUtils.generateSessionID();
			MyMap paramMap = new MyMap();
//			paramMap.put("ivrlogseq", mCall.getStr("seqivrlog"));
			paramMap.put("ivrlogmapperseq", mCall.getStr("seqivrlogmapper"));
			List<MyMap> TTSPutList = ttsfileputhistoryMapper.SelectTTSfileputhistoryList(paramMap);
			
			// 발송
			TTSPutList.get(0).put("tid", mCall.getStr("sessionid"));
			mResultTTSPlay = ivrSender.wavPlay0001(TTSPutList.get(0), mCall.getStr("phonenumber"), "A", mCall.getStr("callerId"), requestNumber);
		}
		// 응답형
		else if(mCall.getStr("callType").equals("B"))
		{
			List<MyMap> resultTTSPutList 	= new ArrayList<>();
			
			MyMap paramMap = new MyMap();
//			paramMap.put("ivrlogseq", mCall.getStr("seqivrlog"));
			paramMap.put("ivrlogmapperseq", mCall.getStr("seqivrlogmapper"));
			
			// ment 순서대로 get
			List<MyMap> TTSPutList 			= ttsfileputhistoryMapper.SelectTTSfileputhistoryList(paramMap);
			for(MyMap TTSPut : TTSPutList)
			{
				String tid = FrameworkUtils.generateSessionID();
				
				TTSPut.put("tid", tid);
				resultTTSPutList.add(TTSPut);
			}

			// 발송
			mResultTTSPlay = ivrSender.wavPlay0002(resultTTSPutList, mCall.getStr("phonenumber"), mCall.getStr("callerId"));
		}
		
		// 콜 결과 UPDATE
		UpdateCallResult(mResultTTSPlay);
		
		basicInfo.setNormalCallThreadFull(false);
		
		LOGGER.info("Normal Call End..");
	}
	
	
	
	// 콜 결과 UPDATE
	private void UpdateCallResult(MyMap resultTTSPlay)
	{
		if(resultTTSPlay != null)
		{
			resultTTSPlay.put("ivrlogseq", mCall.getStr("seqivrlog"));
			
			// IVRLOG TABLE에 결과 UPDATE
			ivrlogMapper.UpdateIVRLog(resultTTSPlay);
		}

		// CallScheduler TABLE CallStatus = 'C'로 변경
		schedulerMapper.UpdateCallStatusC(mCall);
	}
	

}
