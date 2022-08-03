package com.inbiznetcorp.acs.web.result.act;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.result.ResultCode;
import com.inbiznetcorp.acs.framework.result.ResultMessage;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
import com.inbiznetcorp.acs.web.excel.read.ExcelView;
import com.inbiznetcorp.acs.web.ivr.service.IVRService;

@Controller
@RequestMapping("/result")
public class ResultAct
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ResultAct.class);

	@Resource(name="com.inbiznetcorp.acs.web.ivr.service.IVRService")
	IVRService ivrlogService;
	
	@Resource(name="com.inbiznetcorp.acs.web.excel.read.ExcelView")
	ExcelView mExcelView;

	/**
	 * 음성 발송내역 관리 페이지
	 */
	@RequestMapping("/ResultList")
	public String ResultList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

//		paramMap.put("sendTime", "A");
		
		// A:알림형, B:응답형, C:예약, D:주기
		paramMap.put("listType", "A");
		
		BasicBean result = ivrlogService.ListPagingMapperData(paramMap);

		model.addAttribute("list", 				result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());
		model.addAttribute("sSDate_", 			paramMap.getStr("sSDate_", ""));
		model.addAttribute("sEDate_", 			paramMap.getStr("sEDate_", ""));
		model.addAttribute("searchType_", 		paramMap.getStr("searchType_", "TITLE"));
		model.addAttribute("searchWord_", 		paramMap.getStr("searchWord_", ""));
		model.addAttribute("sidx", 				paramMap.getStr("sidx", "ROW_NUM"));
		model.addAttribute("sord", 				paramMap.getStr("sord", "DESC"));
		model.addAttribute("paramMap", 			paramMap);

		return "/result/ResultList";
	}

	/**
	 * 문자발송내역 관리 페이지
	 */
	@RequestMapping("/SMSResultList")
	public String SMSResultList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

//		paramMap.put("sendTime", "A");
		
		// A:알림형, B:응답형, C:예약, D:주기
		paramMap.put("listType", "A");
		
		BasicBean result = ivrlogService.ListPagingMapperData(paramMap);

		model.addAttribute("list", 				result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());
		model.addAttribute("sSDate_", 			paramMap.getStr("sSDate_", ""));
		model.addAttribute("sEDate_", 			paramMap.getStr("sEDate_", ""));
		model.addAttribute("searchType_", 		paramMap.getStr("searchType_", "TITLE"));
		model.addAttribute("searchWord_", 		paramMap.getStr("searchWord_", ""));
		model.addAttribute("sidx", 				paramMap.getStr("sidx", "ROW_NUM"));
		model.addAttribute("sord", 				paramMap.getStr("sord", "DESC"));
		model.addAttribute("paramMap", 			paramMap);

		return "/result/SMSResultList";
	}

	/**
	 * 예약발송내역 관리 페이지
	 */
	@RequestMapping("/ReservationList")
	public String ReservationList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

//		paramMap.put("sendTime", "B");
		
		// A:알림형, B:응답형, C:예약, D:주기
		paramMap.put("listType", "C");
		BasicBean result = ivrlogService.ListPagingMapperData(paramMap);

		model.addAttribute("list", 				result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());
		model.addAttribute("sSDate_", 			paramMap.getStr("sSDate_", ""));
		model.addAttribute("sEDate_", 			paramMap.getStr("sEDate_", ""));
		model.addAttribute("searchType_", 		paramMap.getStr("searchType_", "TITLE"));
		model.addAttribute("searchWord_", 		paramMap.getStr("searchWord_", ""));
		model.addAttribute("sidx", 				paramMap.getStr("sidx", "ROW_NUM"));
		model.addAttribute("sord", 				paramMap.getStr("sord", "DESC"));
		model.addAttribute("searchDType", 		paramMap.getStr("searchDType", "CREATEDATE"));

		return "/result/ReservationList";
	}

	/**
	 * 주기발송내역 관리 페이지
	 */
	@RequestMapping("/RepeatList")
	public String RepeatList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

//		paramMap.put("sendTime", "C");
		
		// A:알림형, B:응답형, C:예약, D:주기
		paramMap.put("listType", "D");
				
		BasicBean result = ivrlogService.ListPagingMapperData(paramMap);

		model.addAttribute("list", 				result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());
		model.addAttribute("sSDate_", 			paramMap.getStr("sSDate_", ""));
		model.addAttribute("sEDate_", 			paramMap.getStr("sEDate_", ""));
		model.addAttribute("searchType_", 		paramMap.getStr("searchType_", "TITLE"));
		model.addAttribute("searchWord_", 		paramMap.getStr("searchWord_", ""));
		model.addAttribute("sidx", 				paramMap.getStr("sidx", "ROW_NUM"));
		model.addAttribute("sord", 				paramMap.getStr("sord", "DESC"));

		return "/result/RepeatList";
	}

	/**
	 * 발송 상세 내역 관리 페이지
	 */
	@RequestMapping("/ResultDetailList")
	public String ResultDetailList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		if(paramMap.getStr("ivrlogmapperseq", "").equals(""))
		{
			return "redirect:/result/ResultList";
		}

		BasicBean result = ivrlogService.ListPagingData(paramMap);

		model.addAttribute("list", 				result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());
		model.addAttribute("sSDate", 			paramMap.getStr("sSDate", ""));
		model.addAttribute("sEDate", 			paramMap.getStr("sEDate", ""));
		model.addAttribute("searchType", 		paramMap.getStr("searchType", "NAME"));
		model.addAttribute("searchWord", 		paramMap.getStr("searchWord", ""));
		model.addAttribute("searchType_", 		paramMap.getStr("searchType_", "NAME"));
		model.addAttribute("searchWord_", 		paramMap.getStr("searchWord_", ""));
		model.addAttribute("ivrlogmapperseq", 	paramMap.getStr("ivrlogmapperseq"));
		model.addAttribute("sidx", 				paramMap.getStr("sidx", "ROW_NUM"));
		model.addAttribute("sord", 				paramMap.getStr("sord", "DESC"));
		model.addAttribute("sendTime", 			paramMap.getStr("sendTime", "A"));


		paramMap.put("sidx", "ROW_NUM");
		paramMap.put("sord", "DESC");
		BasicBean result_ = ivrlogService.ListPagingMapperData(paramMap);
		if(result_.getList().size() > 0)
		{
			model.addAttribute("list_", 			result_.getList().get(0));
		}

		return "/result/ResultDetailList";
	}

	/**
	 * 예약메세지 상세 내역 관리 페이지
	 */
	@RequestMapping("/ReservationDetailList")
	public String ReservationDetailList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		if(paramMap.getStr("ivrlogmapperseq", "").equals(""))
		{
			return "redirect:/result/ResultList";
		}

		BasicBean result = ivrlogService.ListPagingData(paramMap);

		model.addAttribute("list", 				result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());
		model.addAttribute("sSDate", 			paramMap.getStr("sSDate", ""));
		model.addAttribute("sEDate", 			paramMap.getStr("sEDate", ""));
		model.addAttribute("searchType", 		paramMap.getStr("searchType", "NAME"));
		model.addAttribute("searchWord", 		paramMap.getStr("searchWord", ""));
		model.addAttribute("searchType_", 		paramMap.getStr("searchType_", "NAME"));
		model.addAttribute("searchWord_", 		paramMap.getStr("searchWord_", ""));
		model.addAttribute("ivrlogmapperseq", 	paramMap.getStr("ivrlogmapperseq"));
		model.addAttribute("sidx", 				paramMap.getStr("sidx", "ROW_NUM"));
		model.addAttribute("sord", 				paramMap.getStr("sord", "DESC"));
		model.addAttribute("sendTime", 			paramMap.getStr("sendTime", "B"));
		model.addAttribute("searchDType", 		paramMap.getStr("searchDType", "RESPONSEDATE"));


		paramMap.put("sidx", "ROW_NUM");
		paramMap.put("sord", "DESC");
		BasicBean result_ = ivrlogService.ListPagingMapperData(paramMap);
		if(result_.getList().size() > 0)
		{
			model.addAttribute("list_", 			result_.getList().get(0));
		}

		return "/result/ReservationDetailList";
	}

	/**
	 * 반복메세지 상세 내역 관리 페이지
	 */
	@RequestMapping("/RepeatDetailList")
	public String RepeatDetailList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		if(paramMap.getStr("ivrlogmapperseq", "").equals(""))
		{
			return "redirect:/result/ResultList";
		}

		BasicBean result = ivrlogService.SelectRepeatListByIVRMapperSeq(paramMap);

		model.addAttribute("list", 				result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());
		model.addAttribute("sSDate", 			paramMap.getStr("sSDate", ""));
		model.addAttribute("sEDate", 			paramMap.getStr("sEDate", ""));
		model.addAttribute("sSDate_", 			paramMap.getStr("sSDate_", ""));
		model.addAttribute("sEDate_", 			paramMap.getStr("sEDate_", ""));
		model.addAttribute("searchType", 		paramMap.getStr("searchType", "NAME"));
		model.addAttribute("searchWord", 		paramMap.getStr("searchWord", ""));
		model.addAttribute("searchType_", 		paramMap.getStr("searchType_", "NAME"));
		model.addAttribute("searchWord_", 		paramMap.getStr("searchWord_", ""));
		model.addAttribute("ivrlogmapperseq", 	paramMap.getStr("ivrlogmapperseq"));
		model.addAttribute("sidx", 				paramMap.getStr("sidx", "ROW_NUM"));
		model.addAttribute("sord", 				paramMap.getStr("sord", "DESC"));
		model.addAttribute("sendTime", 			paramMap.getStr("sendTime", "C"));


		paramMap.put("sidx", "ROW_NUM");
		paramMap.put("sord", "DESC");
		BasicBean result_ = ivrlogService.ListPagingMapperData(paramMap);
		if(result_.getList().size() > 0)
		{
			model.addAttribute("list_", 			result_.getList().get(0));
		}

		return "/result/RepeatDetailList";
	}

	/**
	 * 반복메세지 전송시간별 상세 내역 관리 페이지
	 */
	@RequestMapping("/RepeatDetailListByTime")
	public String RepeatDetailListByTime(Model model)
	{
		MyMap 		paramMap 	= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		BasicBean 	result 		= ivrlogService.SelectRepeatListBySenddate(paramMap);

		model.addAttribute("list", 				result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());
		model.addAttribute("sSDate", 			paramMap.getStr("sSDate", ""));
		model.addAttribute("sEDate", 			paramMap.getStr("sEDate", ""));
		model.addAttribute("sSDate_", 			paramMap.getStr("sSDate_", ""));
		model.addAttribute("sEDate_", 			paramMap.getStr("sEDate_", ""));
		model.addAttribute("searchType", 		paramMap.getStr("searchType", "NAME"));
		model.addAttribute("searchWord", 		paramMap.getStr("searchWord", ""));
		model.addAttribute("searchType_", 		paramMap.getStr("searchType_", "NAME"));
		model.addAttribute("searchWord_", 		paramMap.getStr("searchWord_", ""));
		model.addAttribute("ivrlogmapperseq", 	paramMap.getStr("ivrlogmapperseq"));
		model.addAttribute("sidx", 				paramMap.getStr("sidx", "ROW_NUM"));
		model.addAttribute("sord", 				paramMap.getStr("sord", "DESC"));
		model.addAttribute("nextcallDate", 		paramMap.getStr("nextcallDate"));

		MyCamelMap mapper = ivrlogService.MapperInfoData(paramMap);
		model.addAttribute("mapper", mapper);

		return "/result/RepeatDetailListByTime";
	}

	/**
	 * 재발송 확인(그룹) 팝업 페이지
	 */
	@RequestMapping("/RetryCheck")
	public String RetryCheck(Model model, HttpServletRequest request)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		List<MyCamelMap> resultList = ivrlogService.SelectRetryList(paramMap);

		model.addAttribute("list", 				resultList);
		model.addAttribute("ivrlogmapperseq", 	request.getParameter("ivrlogmapperseq"));
//		model.addAttribute("ttsTitle", 			request.getParameter("ttsTitle"));

		return "/result/RetryCheck";
	}

	/**
	 * 재발송 확인(개인) 팝업 페이지
	 */
	@RequestMapping("/RetryDetailCheck")
	public String RetryDetailCheck(Model model, HttpServletRequest request)
	{
		model.addAttribute("ivrlogseq", 	request.getParameter("ivrlogseq"));
		model.addAttribute("name", 			request.getParameter("name"));
		model.addAttribute("phonenumber", 	request.getParameter("phonenumber"));

		return "/result/RetryDetailCheck";
	}

	/**
	 * 발송 상세 내역 관리 전송내용 클릭 시
	 */
	@RequestMapping("/TTSMentPopup")
	public String TTSMentPopup(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		model.addAttribute("ttsMentIntro01", FrameworkUtils.unescapeHtml(paramMap.getStr("ttsMentIntro01").replace("\r\n", "<br/>")));

		return "/result/TTSMentPopup";
	}

	/**
	 * 예약 취소
	 */
	@RequestMapping("/ReservationCancel")
	public @ResponseBody ResultMessage ReservationCancel()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		int result = ivrlogService.ReservationCancel(paramMap);
		if(result != 0)
		{
			return new ResultMessage(ResultCode.RESULT_OK, "DELETE");
		}
		else
		{
			return new ResultMessage(ResultCode.RESULT_OK, "NO_DELETE");
		}
	}

	/**
	 * 반복 취소
	 */
	@RequestMapping("/RepeatCancel")
	public @ResponseBody ResultMessage RepeatCancel()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		int result = ivrlogService.RepeatCancel(paramMap);
		if(result != 0)
		{
			return new ResultMessage(ResultCode.RESULT_OK, "DELETE");
		}
		else
		{
			return new ResultMessage(ResultCode.RESULT_OK, "NO_DELETE");
		}
	}
	
	/**
	 * 엑셀 다운로드
	 */
	@RequestMapping("/ExcelDownload")
	public ModelAndView ExcelDownload()
	{
		ModelAndView mav = new ModelAndView(mExcelView);
		
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		mav.addObject("source", 	"resultDetail.xlsx");
		mav.addObject("list", 		ivrlogService.ListPagingData(paramMap).getAllList());
		mav.addObject("target", 	"ACS_발송상세내역");
		mav.addObject("sSDate", 	paramMap.getStr("sSDate", null));
		mav.addObject("sEDate", 	paramMap.getStr("sEDate", null));
		
		return mav;
	}

}
