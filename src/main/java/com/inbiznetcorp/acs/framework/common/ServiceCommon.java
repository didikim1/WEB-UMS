package com.inbiznetcorp.acs.framework.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service("com.inbiznetcorp.acs.framework.common.ServiceCommon")
public class ServiceCommon
{
	@Autowired private Environment env;

	public String getIVRIP()
	{
		return env.getProperty("Common.IVR.Server.URL");
	}

	public String getIVRWebSocket()
	{
		return env.getProperty("Common.IVR.WebSocket.URL");
	}

	public String getSMSSenderURL()
	{
		return env.getProperty("Common.SMS.Sender.URL");
	}

	public String getJOBScheduledExcute()
	{
		return env.getProperty("Common.JOB.Scheduled.Excute");
	}

	public String getProfilesActiveName()
	{
		return env.getProperty("spring.profiles.active");
	}

	public String getCallThreadMaxCount()
	{
		return env.getProperty("Common.CallThreadMaxCount");
	}
}
