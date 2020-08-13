package com.inbiznetcorp.acs.scheduled.service;

import java.util.List;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
import com.inbiznetcorp.acs.framework.websocket.ExecutorServiceImportant;
import com.inbiznetcorp.acs.framework.websocket.ExecutorServiceNormal;
import com.inbiznetcorp.acs.framework.websocket.IVRSender;
import com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo;
import com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper;
import com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper;
import com.inbiznetcorp.acs.mapper.ttsfileputhistory.TTSfileputhistoryMapper;

public class CallScheduled implements Runnable
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CallScheduled.class);
	
	BasicInfo 				basicInfo 				= (BasicInfo) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo");
	SchedulerMapper 		schedulerMapper 		= (SchedulerMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper");
	IVRSender 				ivrSender 				= (IVRSender) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.IVRSender");
	TTSfileputhistoryMapper ttsfileputhistoryMapper = (TTSfileputhistoryMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.ttsfileputhistory.TTSfileputhistoryMapper");
	IvrlogMapper 			ivrlogMapper 			= (IvrlogMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper");

	@Override
	public void run()
	{
		LOGGER.info("=====================================");
		LOGGER.info("CallScheduled is ready...");
		LOGGER.info("=====================================");
		
		MyMap paramMap = new MyMap();
		
		basicInfo.setNormalCallExist(true);
		
		
		while(true)
		{
			// 보내야할 일반 콜이 있을 때
			if(basicInfo.isNormalCallExist())
			{
				// threadpool이 남아있을 때
				if(!basicInfo.isNormalCallThreadFull())
				{
					// 진행중인 콜 갯수 검색 후(synchronized)
					paramMap.put("sessionNormalCps", basicInfo.getSESSION_NORMAL_CPS());
					List<MyCamelMap> normalCallList = schedulerMapper.SelectNormalCallList(paramMap);
					
					// threadpool 갯수이상 진행중이면 -> basicInfo.setNormalCallThreadFull(true);
					if(normalCallList.size() >= basicInfo.getSESSION_NORMAL_CPS())
					{
						basicInfo.setNormalCallThreadFull(true);
					}
					// threadpool 갯수보다 작게 진행중이면 -> 콜 1개 검색 -> 콜 발송 -> 콜 결과 업데이트 -> basicInfo.setNormalCallThreadFull(false);
					else
					{
						LOGGER.info("########################");
						// 대기 list중 첫번째 콜 검색
						MyCamelMap call = SelectNormalCall(paramMap);
						LOGGER.info("call : " + call);
						// 보내야할 콜이 있을 때
						if(call != null)
						{
							// CallStatus = 'I'로 변경
							schedulerMapper.UpdateCallStatusI(call);
							
							// 콜발송
							ExecutorServiceNormal.addCallRunable(new CallRunnable(call));
							
							// CPS를 15로 맞추기 위해서, 70ms*15=1050ms -> 15콜을 약1.05초
							try { Thread.sleep(70); } catch (InterruptedException e) { e.printStackTrace(); }
						}
						// 보내야할 콜이 없을 때
						else
						{
							basicInfo.setNormalCallExist(false);
							LOGGER.info("NormalCall is not exist..");
						}
					}
				}
				// threadpool이 가득 찼을 때
				else
				{
					// 작업 필요없음.
				}
			}
			// 보내야할 일반 콜이 없을 때
			else
			{
				// 작업 필요없음.
			}
			
			// 보내야할 급한 콜이 있을 때
			if(basicInfo.isImportantCallExist())
			{
				// threadpool이 남아있을 때
				if(!basicInfo.isImportantCallThreadFull())
				{
					// 진행중인 콜 갯수 검색 후(synchronized)
					paramMap.put("sessionImportantCps", basicInfo.getSESSION_IMPORTANT_CPS());
					List<MyCamelMap> importantCallList = schedulerMapper.SelectImportantCallList(paramMap);
					
					// threadpool 갯수이상 진행중이면 -> basicInfo.setImportantCallThreadFull(true);
					if(importantCallList.size() >= basicInfo.getSESSION_NORMAL_CPS())
					{
						basicInfo.setImportantCallThreadFull(true);
					}
					// threadpool 갯수보다 작게 진행중이면 -> 콜 1개 검색 -> 콜 발송 -> 콜 결과 업데이트 -> basicInfo.setImportantCallThreadFull(false);
					else
					{
						// 대기 list중 첫번째 콜 검색
						MyCamelMap call = SelectImportantCall(paramMap);
						
						// CallStatus = 'I'로 변경
						schedulerMapper.UpdateCallStatusI(call);
						
						// 보내야할 콜이 있을 때
						if(call != null)
						{
							// 콜발송
							ExecutorServiceImportant.addCallRunable(new CallRunnable(call));
							
							// CPS를 15로 맞추기 위해서, 70ms*15=1050ms -> 15콜을 약1.05초
							try { Thread.sleep(70); } catch (InterruptedException e) { e.printStackTrace(); }
						}
						// 보내야할 콜이 없을 때
						else
						{
							basicInfo.setImportantCallExist(false);
							LOGGER.info("ImportantCall is not exist..");
						}
					}
				}
				// threadpool이 가득 찼을 때
				else
				{
					// 작업 필요없음.
				}
			}
			// 보내야할 급한 콜이 없을 때
			else
			{
				// 작업 필요없음.
			}
			
			try
			{
				Thread.sleep(300);
			}
			catch (InterruptedException e)
			{
				FrameworkUtils.exceptionToString(e);
			}
		}
	}
	

	// 대기 list중 첫번째 일반 콜 검색(synchronized 사용)
	public synchronized MyCamelMap SelectNormalCall(MyMap paramMap)
	{
		return schedulerMapper.SelectNormalCall(paramMap);
	}
	
	// 대기 list중 첫번째 급한 콜 검색(synchronized 사용)
	public synchronized MyCamelMap SelectImportantCall(MyMap paramMap)
	{
		return schedulerMapper.SelectImportantCall(paramMap);
	}
	
	
}
