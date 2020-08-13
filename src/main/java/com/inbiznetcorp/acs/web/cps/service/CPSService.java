package com.inbiznetcorp.acs.web.cps.service;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkPagingUtils;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
import com.inbiznetcorp.acs.mapper.ivrserver.IvrserverMapper;

@Service("com.inbiznetcorp.acs.web.cps.service.CPSService")
public class CPSService
{
	@Resource(name="com.inbiznetcorp.acs.mapper.ivrserver.IvrserverMapper")
	IvrserverMapper ivrserverMapper;

	public BasicBean SelectIVRServerList(MyMap paramMap)
	{
		BasicBean resultBean = null;
		
		if(paramMap.getStr("sidx", "").equals(""))
		{
			paramMap.put("sidx", "ROW_NUM");
			paramMap.put("sord", "DESC");
		}

		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 10));
		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 10),
				ivrserverMapper.SelectIVRServerCount(paramMap), ivrserverMapper.SelectIVRServerList(paramMap));

		return resultBean;
	}

	public MyCamelMap SelectIVRServer(MyMap paramMap)
	{
		return ivrserverMapper.SelectIVRServer(paramMap);
	}
	
	public int InsertIVRServer(MyMap paramMap)
	{
		return ivrserverMapper.InsertIVRServer(paramMap);
	}

	public int UpdateIVRServer(MyMap paramMap)
	{
		return ivrserverMapper.UpdateIVRServer(paramMap);
	}

	public int IVRServerModify(MyMap paramMap)
	{
		try
		{
			JSONParser 	parser 			= new JSONParser();
			JSONArray 	ivrserverArr 	= (JSONArray) parser.parse(FrameworkUtils.unescapeHtml(paramMap.getStr("ivrserverArr")));
			
			for(int i=0; i<ivrserverArr.size(); i++)
			{
				JSONObject ivrserver = (JSONObject) ivrserverArr.get(i);
				paramMap.put("seqivrserver", 	ivrserver.get("seqivrserver"));
				paramMap.put("serverName", 		ivrserver.get("serverName"));
				paramMap.put("serverIp", 		ivrserver.get("serverIp"));
				paramMap.put("cps", 			ivrserver.get("cps"));
				
				ivrserverMapper.UpdateIVRServer(paramMap);
			}
		}
		catch (ParseException e)
		{
			FrameworkUtils.exceptionToString(e);
		}
		
		return 0;
	}
	
}
