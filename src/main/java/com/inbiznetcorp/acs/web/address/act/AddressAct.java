package com.inbiznetcorp.acs.web.address.act;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
import com.inbiznetcorp.acs.web.address.service.AddressService;

@Controller
@RequestMapping("/addr")
public class AddressAct
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AddressAct.class);

	@Resource(name="com.inbiznetcorp.acs.web.message.service.AddressService")
	AddressService addressService;

	
	/**
	 * 개인 주소록 페이지
	 */
	@RequestMapping("/AddressPList")
	public String AddressPList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		BasicBean result = addressService.ListPagingData(paramMap);

		model.addAttribute("list", 				result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());
		model.addAttribute("searchType", 		paramMap.getStr("searchType", "NAME"));
		model.addAttribute("searchWord", 		paramMap.getStr("searchWord", ""));

		return "/address/AddressPList";
	}

	/**
	 * 그룹 주소록 페이지
	 */
	@RequestMapping("/AddressGList")
	public String AddressGList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		BasicBean result = addressService.ListPagingDataGroup(paramMap);

		model.addAttribute("list", 				result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());
		model.addAttribute("searchWord", 		paramMap.getStr("searchWord", ""));

		return "/address/AddressGList";
	}

	/**
	 * 그룹 주소록 상세 페이지
	 */
	@RequestMapping("/AddressDetailGList")
	public String AddressDetailGList(Model model, HttpServletRequest request)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		paramMap.put("seq", "groupseq");

		String seqStr = addressService.SelectAddressSeq(paramMap);
		paramMap.put("seq", seqStr);

		BasicBean result = addressService.ListPagingData(paramMap);
		model.addAttribute("list", 			result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());

		model.addAttribute("groupseq", paramMap.getStr("groupseq"));
		model.addAttribute("groupname", paramMap.getStr("groupname", request.getParameter("groupname")));

		return "/address/AddressDetailGList";
	}

	/**
	 * 그룹 주소록 삭제
	 */
	@RequestMapping("/DeleteGroup")
	public @ResponseBody ResultMessage DeleteGroup()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		addressService.DeleteGroup(paramMap);

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * 그룹 주소록 삭제 확인 팝업 페이지
	 */
	@RequestMapping("/GroupNameCheck")
	public String GroupNameCheck(Model model, HttpServletRequest request)
	{
		model.addAttribute("groupseq", 	request.getParameter("groupseq"));
		model.addAttribute("groupname", request.getParameter("groupname"));

		return "/address/GroupNameCheck";
	}


	/**
	 * 이름,번호를 이용하여 등록된 사람 찾기
	 */
	@RequestMapping("/SelectTarget")
	public @ResponseBody ResultMessage SelectTarget()
	{
		MyMap 				paramMap 	= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		List<MyCamelMap> 	result 		= null;
//		String 				message 	= "";

		result = addressService.SelectTarget(paramMap);
//		if(result.size() < 1)
//		{
//			result = addressService.SelectTargetByPhone(paramMap);
//			if(result.size() < 1)
//			{
//				message = "다른 이름의 같은 전화번호가 이미 등록되어 있습니다.";
//			}
//		}
//		else
//		{
//			message = "이미 등록된 정보가 있습니다.";
//		}

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!", result.size());
	}

	/**
	 * 이름,번호 확인 팝업 페이지
	 */
	@RequestMapping("/AddressCheck")
	public String AddressCheck(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		model.addAttribute("title", 		paramMap.getStr("title"));
		model.addAttribute("subtitle", 		paramMap.getStr("subtitle"));
		model.addAttribute("seq", 			paramMap.getStr("seq"));
		model.addAttribute("name", 			paramMap.getStr("name"));
		model.addAttribute("phonenumber", 	paramMap.getStr("phonenumber"));

		return "/address/AddressCheck";
	}

	/**
	 * 그룹 추가 시 그룹명 중복 확인
	 */
	@RequestMapping("/GroupNameDupl")
	public @ResponseBody ResultMessage GroupNameDupl(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		int result = addressService.GroupNameDupl(paramMap);

		if(result < 1)
		{
			return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
		}
		else
		{
			return new ResultMessage(ResultCode.RESULT_BAD_REQUEST, "fail..");
		}

	}

	/**
	 * 그룹 추가
	 */
	@RequestMapping("/RegisterGroup")
	public @ResponseBody ResultMessage RegisterGroupPopup(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		addressService.RegisterGroup(paramMap);

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * 그룹원 추가 팝업창
	 */
	@RequestMapping("/ExcelUpload")
	public String ExcelUpload(Model model, HttpServletRequest request)
	{
		model.addAttribute("seqgroupinfo", 	request.getParameter("seqgroupinfo"));
		model.addAttribute("etc", 			request.getParameter("etc"));

		return "/address/ExcelUpload";
	}

	/**
	 * 그룹원 등록
	 */
	@RequestMapping("/RegisterDataGroup")
	public @ResponseBody ResultMessage RegisterDataGroup(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		String[] seqStr = paramMap.getStr("seqStr").split("\\,");
		for(String seq : seqStr)
		{
			paramMap.put("seq", seq);
			addressService.RegisterAddressMapper(paramMap);
		}

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * 주소록 추가 등록
	 */
	@RequestMapping("/RegisterData")
	public @ResponseBody ResultMessage RegisterData(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		addressService.RegisterData(paramMap);

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * 개인 주소록에서 그룹원 삭제
	 */
	@RequestMapping("/DeletePData")
	public @ResponseBody ResultMessage DeletePData(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		addressService.DeletePData(paramMap);
		addressService.DeleteMapperData(paramMap);

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * 개인 주소록에서 그룹원 일괄 삭제
	 */
	@RequestMapping("/DeletePDatas")
	public @ResponseBody ResultMessage DeletePDatas(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		addressService.DeletePDatas(paramMap);
		addressService.DeleteMapperDatas(paramMap);

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * 그룹 주소록에서 그룹원 삭제
	 */
	@RequestMapping("/DeleteData")
	public @ResponseBody ResultMessage DeleteData(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		String seqaddressinfo 	= paramMap.getStr("seq").split("\\|")[0];
		String seqgroupinfo 	= paramMap.getStr("seq").split("\\|")[1];
		paramMap.put("seqaddressinfo", 	seqaddressinfo);
		paramMap.put("seqgroupinfo", 	seqgroupinfo);
		addressService.DeleteData(paramMap);

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}

	/**
	 * 개인주소록에서 등록버튼 클릭시 중복 검사
	 */
	@RequestMapping("/SearchDupl")
	public @ResponseBody ResultMessage SearchDupl(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		int result = addressService.SearchDupl(paramMap);

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!", result);
	}

	/**
	 * 개인주소록에서 이름 클릭시 상세정보창
	 */
	@RequestMapping("/AddressDetail")
	public String AddressDetail(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		MyCamelMap resultMap = addressService.AddressDetail(paramMap);

		model.addAttribute("data", resultMap);

		return "/address/AddressDetail";
	}

	/**
	 * 개인 주소록 1명 수정
	 */
	@RequestMapping("/ModifyPData")
	public @ResponseBody ResultMessage ModifyPData()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		try
		{
			addressService.ModifyPData(paramMap);
		}
		catch (Exception e)
		{
			FrameworkUtils.exceptionToString(e);
			return new ResultMessage(ResultCode.RESULT_BAD_REQUEST, "fail..");
		}

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}
	
	@RequestMapping("/selectTarget")
	@ResponseBody
	public ResultMessage selectTarget() {
		String result = ResultCode.RESULT_BAD_REQUEST;
		List<MyCamelMap> list = null;
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
//		System.out.println(paramMap);
		list = addressService.selectTargetList(paramMap);
		if(list.size() > 0) result = ResultCode.RESULT_OK;
		return new ResultMessage(result,list);
	}


}
