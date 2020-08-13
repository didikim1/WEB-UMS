package com.inbiznetcorp.acs.web.server.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkPagingUtils;
import com.inbiznetcorp.acs.mapper.server.ServerMapper;

@Service("com.inbiznetcorp.acs.web.server.service.ServerService")
public class ServerService
{
	@Resource(name="com.inbiznetcorp.acs.mapper.server.ServerMapper")
	ServerMapper serverMapper;

	public BasicBean ListPagingData(MyMap paramMap)
	{
		BasicBean resultBean = null;

		if(paramMap.getStr("sidx", "").equals(""))
		{
			paramMap.put("sidx", "ROW_NUM");
			paramMap.put("sord", "DESC");
		}

		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 10));
		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 10),
				serverMapper.SelectOnePagingCount(paramMap), serverMapper.ListPagingData(paramMap));

		return resultBean;
	}


}
