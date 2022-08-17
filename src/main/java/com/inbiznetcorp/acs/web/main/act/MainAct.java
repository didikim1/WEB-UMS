package com.inbiznetcorp.acs.web.main.act;

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
import com.inbiznetcorp.acs.mapper.main.MainMapper;
import com.inbiznetcorp.acs.web.address.service.AddressService;
import com.inbiznetcorp.acs.web.main.service.MainService;

@Controller
@RequestMapping("/main")
public class MainAct
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MainAct.class);
	
	@Resource(name="com.inbiznetcorp.acs.web.main.MainService")
	MainService mService;
	
	@Resource(name="com.inbiznetcorp.acs.web.message.service.AddressService")
	AddressService addressService;

	/**
	 * 회원가입페이지
	 */
	@RequestMapping("/Join")
	public String Join(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		return "/main/Join";
	}
	/**
	 * id체크
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/uniqIdChk.do" })
	public @ResponseBody ResultMessage uniqIdChk(Model model)
	{
	            MyMap              paramMap                        = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
	            MyCamelMap         resultMap                       = new MyCamelMap();

	            String             resultCode                      = ResultCode.RESULT_INTERNAL_SERVER_ERROR;
	            int                resultRegisterDataCount         = 0;

	            resultMap = mService.SelectOneData(paramMap);

	            if( resultMap == null ){
	                resultCode = ResultCode.RESULT_NOT_FOUND;
	            } else {
	                resultCode = ResultCode.RESULT_BAD_REQUEST;
	            }

	            return new ResultMessage(resultCode, null);
	}
	
	/**
	 * 계정정보 변경
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/ProcRegisterData.do" })
	public @ResponseBody ResultMessage ProcRegisterData(Model model)
	{
	    MyMap              paramMap                        = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
	    MyCamelMap         resultMap                       = new MyCamelMap();

	    String             resultCode                      = ResultCode.RESULT_INTERNAL_SERVER_ERROR;
	    int                resultRegisterDataCount         = 0;


        if(paramMap.getInt("seq", 0) > 0 )
        {
                resultCode      = ResultCode.RESULT_OK;
                resultRegisterDataCount = mService.ModifyData(paramMap);
        }
        else
        {
        	resultRegisterDataCount = mService.RegisterData( paramMap );
        	if( resultRegisterDataCount > 0 )
        	{
        		resultCode = ResultCode.RESULT_OK;
        	}
        }

	    return new ResultMessage(resultCode, null);
	}

}
	