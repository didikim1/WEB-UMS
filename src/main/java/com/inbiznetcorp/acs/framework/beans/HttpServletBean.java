package com.inbiznetcorp.acs.framework.beans;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;


/**
 * @FileName : HttpServletBean.java
 * @Project  : o2o-ui-auth-request
 * @Date     : 2016. 6. 12.
 * @작성자   : hsjeon1224(효성)
 * @변경이력 :
 * @프로그램 설명 : 요청 정보를 관리
 */
@Service("httpServletBean")
@Scope("request")
public class HttpServletBean
{
	private HttpServletRequest  mHttpServletRequest;
	private HttpServletResponse mHttpServletResponse;
	private HttpSession mSession;

	public HttpServletBean(){};

	/**
	 * <pre>
	 * @Method Name  : addHttpServlet
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 클라이언트의 요청 정보를 각가 멤버 변수에 담는다.
	 * @param _httpServletRequest
	 * @param _httpServletResponse
	 * </pre>
	 */
	public void addHttpServlet(HttpServletRequest _httpServletRequest, HttpServletResponse _httpServletResponse)
	{
		this.mHttpServletRequest = _httpServletRequest;
		this.mHttpServletResponse = _httpServletResponse;
		this.mSession = this.mHttpServletRequest.getSession();
	}

	public MyMap findClientRequestParameter()
	{
		MyMap mmClientRequestParameter=new MyMap();

		Map<String, String[]> map=this.mHttpServletRequest.getParameterMap();
		Iterator<String> it=map.keySet().iterator();
		String key=null;
		while(it.hasNext()){
			key=it.next();
			mmClientRequestParameter.put(key,this.getStringParams(key).length>1||key.indexOf("[]")!=-1?this.getStringParams(key):this.getStringParam(key));
		}
		
		String companyName = FrameworkBeans.findSessionBean().getUserName();
		if(companyName.equals("") || companyName.equals("test")) { companyName = "inbiznet"; }

		mmClientRequestParameter.put("SESSION_ID",				mSession.getId());
		mmClientRequestParameter.put("SESSION_USER_SEQ", 		FrameworkBeans.findSessionBean().getUserSeq());
		mmClientRequestParameter.put("SESSION_USER_NAME", 		FrameworkBeans.findSessionBean().getUserName());
		mmClientRequestParameter.put("SESSION_GRADE", 			FrameworkBeans.findSessionBean().getGrade());
		mmClientRequestParameter.put("SESSION_NORMAL_CPS", 		(int)(FrameworkBeans.findSessionBean().getCps()*(0.7)));
		mmClientRequestParameter.put("SESSION_IMPORTANT_CPS", 	(int)(FrameworkBeans.findSessionBean().getCps()*(0.3)));


		return mmClientRequestParameter;
	}

	/**
	 * <pre>
	 * @Method Name  : getStringParam
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : HttpServletRequest 에서 해당 name 값을 반환한다.
	 * @param name
	 * @return
	 * </pre>
	 */
	private String getStringParam(String name)
	{
		String ret=FrameworkUtils.escapeHtml(mHttpServletRequest.getParameter(name));
		return(ret==null?"":ret.trim());
	}

	/**
	 * <pre>
	 * @Method Name  : getStringParams
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : HttpServletRequest 에서 해당 name의 값이 여러개 일 경우
	 * @param name
	 * @return
	 * </pre>
	 */
	private String[] getStringParams(String name){
		String[] values=this.mHttpServletRequest.getParameterValues(name);
		if(values==null) values=new String[0];
		for(int i=0;i<values.length;i++){
			values[i]=FrameworkUtils.escapeHtml(values[i]);
		}
		return values;
	}



	public HttpServletRequest 	getHttpServletRequest() 	{return mHttpServletRequest;}
	public HttpServletResponse 	getHttpServletResponse() 	{return mHttpServletResponse;}
	public HttpSession 			getSession() 				{return mSession;}

	public void setHttpServletRequest(HttpServletRequest mHttpServletRequest) 		{this.mHttpServletRequest = mHttpServletRequest;}
	public void setmHttpServletResponse(HttpServletResponse mHttpServletResponse) 	{this.mHttpServletResponse = mHttpServletResponse;}
	public void setSession(HttpSession mSession) 									{this.mSession = mSession;}

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpServletBean.class);
}
