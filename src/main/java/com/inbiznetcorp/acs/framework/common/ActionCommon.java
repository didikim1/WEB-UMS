package com.inbiznetcorp.acs.framework.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.result.ResultMessage;

import egovframework.pagination.PaginationInfo;
@Controller
@RequestMapping("/common")
public class ActionCommon
{

	@ResponseBody
    @RequestMapping(value = "/jqGrid/init")
    public ResultMessage init(Model model) throws Exception
    {
        MyMap result = new MyMap();

        result.put("list", null);
        result.put("paginationInfo", new PaginationInfo());
        result.put("paramMap", new MyMap());

        return new ResultMessage("200", result);
    }
}
