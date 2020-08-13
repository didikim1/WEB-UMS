//package com.inbiznetcorp.acs.scheduled.service;
//
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ThreadPoolExecutor;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
//import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
//import com.inbiznetcorp.acs.framework.mymap.MyMap;
//import com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//public class CallScheduledTest
//{
//	@Resource(name="com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper")
//	SchedulerMapper schedulerMapper;
//	
//	@Test
//	public void sendNormalCall()
//	{
//		MyMap paramMap = new MyMap();
//		paramMap.put("SESSION_NORMAL_CPS", 	(int)(FrameworkBeans.findSessionBean().getCps()*(0.7)));	// 일반콜 thread수
//		
//		ExecutorService executorService = Executors.newFixedThreadPool(paramMap.getInt("SESSION_NORMAL_CPS"));
//		
//		Runnable runnable = new Runnable() {
//            
//            @Override
//            public void run() {
//            	
//				List<MyCamelMap> normalCallList = schedulerMapper.SelectNormalCallList(paramMap);
//				if(normalCallList != null)
//				{
//					for(MyCamelMap normalCall : normalCallList)
//					{
//						System.out.println("normalCall : " + normalCall);
//					}
//				}
//                
//                try
//                {
//					Thread.sleep(2000);
//				}
//                catch (InterruptedException e)
//                {
//					e.printStackTrace();
//				}
//            }
//        };
//		
//		while(true)
//		{
//			executorService.execute(runnable);
//		}
//		
//		
//	}
//	
////	@Test
////	public void sendVIPCall()
////	{
////		MyMap paramMap = new MyMap();
////		paramMap.put("SESSION_VIP_CPS", 	(int)(FrameworkBeans.findSessionBean().getCps()*(0.3)));	// 급한콜 thread수
////		
////		List<MyCamelMap> vipCallList = schedulerMapper.SelectImportantCallList(paramMap);
////		
////		if(vipCallList != null)
////		{
////			for(MyCamelMap vipCall : vipCallList)
////			{
////				System.out.println("vipCall : " + vipCall);
////			}
////		}
////	}
//}
