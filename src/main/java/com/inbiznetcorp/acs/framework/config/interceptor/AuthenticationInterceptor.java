package com.inbiznetcorp.acs.framework.config.interceptor;

import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;

public class AuthenticationInterceptor implements HandlerInterceptor
{
	String[] UN_CKECKURLS = new String[]{"/index", "/loginProc", "/error/error500"};


    @Override
    public boolean preHandle(HttpServletRequest _httpServletRequest, HttpServletResponse _httpServletResponse, Object _handler) throws Exception
    {
    	int 		 cmpnyserviceid 			= 0;
    	int 		 cmpnychargerid				= 0;
    	String 		 typeCharge					= null;

        String url = _httpServletRequest.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping").toString();
        _httpServletRequest.setAttribute("MAPPING_URL", url);

        StringBuffer requestClientMessage = new StringBuffer();

        LOGGER.info(" Url="+url);

        FrameworkBeans.setHttpServletBean(_httpServletRequest, _httpServletResponse);

        String[] noSessionUrls = new String[] {"", "/", "/Login", "/LoginSubmit", "/ExecutorStop", "/ExecutorStopSubmit"};

//        cmpnychargerid = FrameworkBeans.findSessionBean().getCmpnyChargerID();

//        if( (FrameworkUtils.inArray(noSessionUrls, url) == -1) && ( cmpnychargerid == 0 ) )
//        {
//    		_httpServletResponse.sendRedirect("/Login");
//        	return false;
//        }
//        else
//        {
//        }

        LOGGER.info("==========================INTERCEPTOR==================================");
        LOGGER.info(" Request URL=" + url);
        LOGGER.info(" Connetion IPAddr=" + _httpServletRequest.getRemoteAddr());
        LOGGER.info(" Connetion X-Forwarded-For=" + _httpServletRequest.getHeader("x-forwarded-for"));
        for (Entry<Object, Object> elem : FrameworkBeans.findHttpServletBean().findClientRequestParameter().entrySet())
        {
            String mapKey = (String) elem.getKey();
            String mapVal = String.valueOf(elem.getValue());

            LOGGER.info("\t " + mapKey + " = " + mapVal);
        }

        LOGGER.info("==========================//INTERCEPTOR==================================");
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {

    }

    public static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInterceptor.class);
}