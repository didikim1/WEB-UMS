package com.inbiznetcorp.acs.framework.websocket;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.common.ServiceCommon;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;

public class IVRWatcher
{
	public String IVR_URL = "";
	
	IVRWatcher()
	{
		ServiceCommon serviceCommon = (ServiceCommon) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.common.ServiceCommon");
		
		if("prd".equals(serviceCommon.getProfilesActiveName()))
		{
			if((FrameworkUtils.getCallCnt() % 2) == 0)
			{
				IVR_URL = "http://211.61.220.53";
			}
			else
			{
				IVR_URL = "http://211.61.220.54";
			}
		}
		else
		{
			if((FrameworkUtils.getCallCnt() % 2) == 0)
			{
				IVR_URL = "http://211.61.220.53";
			}
			else
			{
				IVR_URL = "http://211.61.220.54";
			}
//			IVR_URL = "http://211.61.220.42";
		}
	}
	
	public String getIVRUrl()
	{
		return this.IVR_URL;
	}
}
