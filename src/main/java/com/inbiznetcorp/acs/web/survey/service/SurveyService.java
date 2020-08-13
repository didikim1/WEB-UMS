package com.inbiznetcorp.acs.web.survey.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkPagingUtils;
import com.inbiznetcorp.acs.mapper.result.ResultMapper;
import com.inbiznetcorp.acs.mapper.result.SurveyMapper;

@Service("com.inbiznetcorp.acs.web.survey.service.SurveyService")
public class SurveyService
{
	@Resource(name="com.inbiznetcorp.acs.mapper.result.ResultMapper")
	ResultMapper resultMapper;
	
	@Resource(name="com.inbiznetcorp.acs.mapper.result.SurveyMapper")
	SurveyMapper surveyMapper;

	public BasicBean SelectSurveyMapperList(MyMap paramMap)
	{
		BasicBean resultBean = null;
		
		if(paramMap.getStr("sidx", "").equals(""))
		{
			paramMap.put("sidx", "ROW_NUM");
			paramMap.put("sord", "DESC");
		}
		
		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 10));
		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 10),
				resultMapper.SelectOnePagingMapperCount(paramMap), resultMapper.ListPagingMapperData(paramMap));
		
		return resultBean;
	}

	public BasicBean SelectSurveyList(MyMap paramMap)
	{
		BasicBean resultBean = null;
		
		if(paramMap.getStr("sidx", "").equals(""))
		{
			paramMap.put("sidx", "ROW_NUM");
			paramMap.put("sord", "DESC");
		}
		
		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 10));
		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 10),
				surveyMapper.SelectSurveyListCount(paramMap), surveyMapper.SelectSurveyList(paramMap));
		
		return resultBean;
	}

	public MyCamelMap SelectSurveyMapper(MyMap paramMap)
	{
		return surveyMapper.SelectSurveyMapper(paramMap);
	}
	
	public MyCamelMap SelectSurvey(MyMap paramMap)
	{
		return surveyMapper.SelectSurvey(paramMap);
	}

	public MyCamelMap SelectSurveyStatistic(MyMap paramMap)
	{
		return surveyMapper.SelectSurveyStatistic(paramMap);
	}

	@SuppressWarnings("unchecked")
	public List<MyMap> getUserInput(MyMap paramMap)
	{
		List<MyCamelMap> ivrlogList = surveyMapper.SelectSurveyIVRLog(paramMap);
		
		int 	qNumber = paramMap.getInt("qNumber");
		int[] 	dtmfArr = new int[10];
		
		for(MyCamelMap ivrlog : ivrlogList)
		{
			String[] userInputArr = ivrlog.getStr("userInput", "").split(",");
			
			if(qNumber <= userInputArr.length)
			{
				System.out.println("index : " + (Integer.parseInt(userInputArr[qNumber-1])-1));
				dtmfArr[(Integer.parseInt(userInputArr[qNumber-1])-1)]++;
			}
		}
		
		List<MyMap> dtmfList = new ArrayList<>();
		for(int i=0; i<10; i++)
		{
			MyMap objectMap = new MyMap();
			objectMap.put("key", i+1+"번");
			objectMap.put("value", dtmfArr[i]);
			dtmfList.add(objectMap);
		}
		
		return dtmfList;
	}

}
