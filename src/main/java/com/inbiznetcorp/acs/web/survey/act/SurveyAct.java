package com.inbiznetcorp.acs.web.survey.act;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.result.ResultCode;
import com.inbiznetcorp.acs.framework.result.ResultMessage;
import com.inbiznetcorp.acs.web.survey.service.SurveyService;

@Controller
@RequestMapping("/survey")
public class SurveyAct
{
	@Resource(name="com.inbiznetcorp.acs.web.survey.service.SurveyService")
	SurveyService surveyService;
	
	@RequestMapping("/SurveyResultList")
	public String SurveyResultList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
//		paramMap.put("callType", "D");
		
		// A:알림형, B:응답형, C:예약, D:주기
		paramMap.put("listType", "B");
		
		BasicBean result = surveyService.SelectSurveyMapperList(paramMap);
		
		model.addAttribute("list", 				result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());
		model.addAttribute("sSDate_", 			paramMap.getStr("sSDate_", ""));
		model.addAttribute("sEDate_", 			paramMap.getStr("sEDate_", ""));
		model.addAttribute("searchType_", 		paramMap.getStr("searchType_", "TITLE"));
		model.addAttribute("searchWord_", 		paramMap.getStr("searchWord_", ""));
		model.addAttribute("sidx", 				paramMap.getStr("sidx", "ROW_NUM"));
		model.addAttribute("sord", 				paramMap.getStr("sord", "DESC"));
		
		return "/survey/SurveyList";
	}
	
	@RequestMapping("/DetailList")
	public String DetailList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		MyCamelMap mapperInfo = surveyService.SelectSurveyMapper(paramMap);
		model.addAttribute("mapperInfo", mapperInfo);

		// 해당 메세지(ivrlogmapper) 정보 검색
		BasicBean result = surveyService.SelectSurveyList(paramMap);
		
		model.addAttribute("list", 				result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());
		model.addAttribute("paramMap", 			paramMap);

		return "/survey/DetailList";
	}

	@RequestMapping("/TargetResult")
	public String TargetResult(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		MyCamelMap result = surveyService.SelectSurvey(paramMap);
		
		model.addAttribute("data", 		result);
		model.addAttribute("paramMap", 	paramMap);
		
		return "/survey/TargetResult";
	}

	@RequestMapping("/ResultStatistics")
	public String ResultStatistics(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		model.addAttribute("paramMap", 	paramMap);

		return "/survey/ResultStatistics";
	}

	@RequestMapping("/StatisticsGraph")
	public String StatisticsGraph(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		model.addAttribute("paramMap", 	paramMap);

		return "/survey/StatisticsGraph";
	}
	
	@RequestMapping("/getUserInput")
	public @ResponseBody ResultMessage getUserInput()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		// 전달받은 mapperseq와 qNumber로 통계 정보 검색
		List<MyMap> result = surveyService.getUserInput(paramMap);
				
		return new ResultMessage(ResultCode.RESULT_OK, "Success!!", result);
	}
	
	@RequestMapping("/Reporting")
	public String Reporting(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		// 
		
		return "/survey/Reporting";
	}
}
