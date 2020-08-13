package com.inbiznetcorp.acs.web.message.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkPagingUtils;
import com.inbiznetcorp.acs.mapper.message.ArsalarmttsMapper;

@Service("com.inbiznetcorp.acs.web.message.service.ArsalarmttsService")
public class ArsalarmttsService {

	@Resource(name="com.inbiznetcorp.acs.mapper.message.ArsalarmttsMapper")
	ArsalarmttsMapper arsalarmttsMapper;

	public BasicBean ListPagingData(MyMap paramMap)
	{
		BasicBean resultBean = null;

		if(paramMap.getStr("searchWord").length() > 0)
		{
			paramMap.put("page", 1);
		}

		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 10));
		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 10),
				arsalarmttsMapper.SelectOnePagingCount(paramMap), arsalarmttsMapper.ListPagingData(paramMap));

		return resultBean;
	}

	public int RegisterData(MyMap paramMap)
	{
		return arsalarmttsMapper.RegisterData(paramMap);
	}

}
