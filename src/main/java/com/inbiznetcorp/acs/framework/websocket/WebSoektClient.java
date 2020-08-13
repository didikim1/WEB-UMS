package com.inbiznetcorp.acs.framework.websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
import com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper;

public class WebSoektClient
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(WebSoektClient.class);

	final int 	 TimeOut 	  = 300;
	final String SubProtocols = "arsauth-status";

	javax.websocket.Session webSoketSession = null;

	public boolean 		isCallSend() 		{ return mISCallSend; 	}
	public boolean 		isCallEnd() 		{ return mISCallEnd; 	}
	public JSONObject 	getIVRResMsg() 		{ return mIVRResMsg; 	}

	public String connect(String url, String userSessionID)
	{
		int iChekwConnectionID = 0;

		this.mUserSessionID = userSessionID;
		LOGGER.info("WebSocket Connection try....("+this.mUserSessionID+")");

		mSubprotocols.add(SubProtocols);

		try
		{
//			webSoketSession  = ContainerProvider.getWebSocketContainer()
//								    .connectToServer(new SimpleWebSocketClientEndpoint(), ClientEndpointConfig.Builder.create()
//									.preferredSubprotocols(mSubprotocols).build(), new URI(url));
			webSoketSession  = ContainerProvider.getWebSocketContainer()
					.connectToServer(new SimpleWebSocketClientEndpoint(), ClientEndpointConfig.Builder.create()
							.preferredSubprotocols(mSubprotocols).build(), new URI("ws://dev01.ring2pay.com:30000/"));

			webSoketSession.setMaxIdleTimeout(TimeOut*1000);

			LOGGER.info("isOpen ? ("+webSoketSession.isOpen()+")");
		}
		catch (DeploymentException | IOException | URISyntaxException e)
		{
			LOGGER.error("==========================================================================");
			LOGGER.error("==== ERROR "+e.getMessage()+"("+this.mUserSessionID+")");
			LOGGER.error("==========================================================================");
			return null;
		}

		while(mWS_CONNECTION_ID == null)
		{
			try { Thread.sleep(1*1000); } catch (InterruptedException e) { FrameworkUtils.exceptionToString(e); }

			if (iChekwConnectionID > 3) return null;
		}

		LOGGER.info("WebSocket UserID = " + mWS_CONNECTION_ID+"("+this.mUserSessionID+")");
		LOGGER.info("WebSocket Connection Ok!("+this.mUserSessionID+")");

		return mWS_CONNECTION_ID;
	}

	public void disconnect()
	{
		try
		{
			webSoketSession.close();
			webSoketSession = null;
		}
		catch (IOException e)
		{
			FrameworkUtils.exceptionToString(e);
		}
	}

	public boolean sendPacket(JSONObject jsonMsg)
	{
		if ( webSoketSession != null && webSoketSession.isOpen() )
		{
			try
			{
				LOGGER.info("## SendMsg = " + jsonMsg.toString());
				webSoketSession.getBasicRemote().sendText(jsonMsg.toString());

				return true;
			}
			catch (IOException e)
			{
				FrameworkUtils.exceptionToString(e);
				LOGGER.error("==========================================================================");
				LOGGER.error("==== ERROR "+e.getMessage()+"("+this.mUserSessionID+")");
				LOGGER.error("==========================================================================");
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	// Inner Class
    private class SimpleWebSocketClientEndpoint extends Endpoint
    {
		@Override
        public void onOpen(final Session session, final EndpointConfig config)
		{
			session.addMessageHandler(new MessageHandler.Whole<String>()
            {
                @SuppressWarnings("unchecked")
				@Override
                public void onMessage(String message)
                {
                	LOGGER.info("IVR Response Message = " + message);
                	try
                	{
						JSONObject responseObject = (JSONObject) new JSONParser().parse(message);
						String 	   mStrType		  = (String) responseObject.getOrDefault("TYPE", "");
						switch (mStrType)
						{
						case "WS_CONNECTION_ID":
							mWS_CONNECTION_ID =  (String) responseObject.getOrDefault("WS_CONNECTION_ID", "");
							break;
						case "ARSAUTH_RESULT":
							Long longARSSendResult = (Long) responseObject.getOrDefault("CODE", 01);
							if(200== longARSSendResult)
							{
								mISCallSend = true;
							}
							break;
						case "ARSAUTH-STATUS":
							if((boolean) responseObject.getOrDefault("CALL_END", ""))
							{
								mISCallEnd = true;
								mIVRResMsg = responseObject;

								IvrlogMapper ivrlogMapper = (com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper) FrameworkBeans.findBean("com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper");
			                	MyMap 			 paramMap 		  = new MyMap();

			                	paramMap.put("sessionnumber", mUserSessionID);
			                	paramMap.put("msg", 		  message);
			                	ivrlogMapper.InsertIVRHistory(paramMap);
							}
							break;
						default:
							break;
						}
					}
                	catch (ParseException e)
                	{
                		FrameworkUtils.exceptionToString(e);
					}
                	return;
                }
            });
        }

		public void onClose(final Session session, final CloseReason closeReason)
        {
        	String wsSessionID = session.getId();

        	if ( wsSessionID != null )
        	{
        		LOGGER.info("WebSocket Close....("+mUserSessionID+")");
        	}
        	try
        	{
				session.close();
			}
        	catch (IOException e)
        	{
        		FrameworkUtils.exceptionToString(e);
				LOGGER.error("==========================================================================");
				LOGGER.error("==== ERROR "+e.getMessage()+"("+mUserSessionID+")");
				LOGGER.error("==========================================================================");
			}
        	LOGGER.info("WebSocket Close....("+mUserSessionID+")");
        }

		public void onError(final Session session, final Throwable throwable)
        {
			FrameworkUtils.exceptionToString(throwable);
    		LOGGER.error("==========================================================================");
			LOGGER.error("==== ERROR "+throwable.getMessage()+"("+mUserSessionID+")");
			LOGGER.error("==========================================================================");
        }
    }

	private List<String>	mSubprotocols 			= new ArrayList<String>();
    private String			mUserSessionID			= null;
    private String			mWS_CONNECTION_ID		= null;

    private boolean			mISCallSend				= false;
    private boolean 		mISCallEnd				= false;
    private JSONObject		mIVRResMsg				= null;
}
