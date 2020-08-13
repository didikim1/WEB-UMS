package com.inbiznetcorp.acs.framework.beans;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

@Component("com.inbiznetcorp.framework.beans")
public class FrameworkBeans implements ApplicationContextAware
{

	@Override
	public void setApplicationContext(ApplicationContext __applicationContext) throws BeansException
	{
		this.smApplicationContext = __applicationContext;
	}

	/**
	 * @Method Name  : getBean
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 프레임워크에 등록된 Object 를 반환한다.
	 * @param id - 프레임워크에 등록된 bean id
	 * @return - 프레임워크에 등록된 Object
	 */
	public static Object findBean(String _sId)
	{
		Object obean = null;
		if ( smApplicationContext == null ) {
			return null;
		}
		else
		{
			obean = smApplicationContext.getBean(_sId);
			if(obean == null)
			{
				LOGGER.warn("============================================================");
				LOGGER.warn("[RED:00] {} not found on type `framework bean id`", _sId);
				LOGGER.warn("============================================================");
			}
			return obean;
		}
	}

	public Object addBean(String _sId, String _sClassPath) throws ClassNotFoundException{
		Object oBean = findBean(_sId);

		if ( oBean != null)
		{
			LOGGER.warn("============================================================");
			LOGGER.warn("[RED:00] {} existed `framework bean id`", _sId);
			LOGGER.warn("============================================================");
			return (false);
		}
		BeanDefinition beanDefinition = new RootBeanDefinition(Class.forName(_sClassPath));
		getGenericContext().registerBeanDefinition(_sId, beanDefinition);

		return findBean(_sId);
	}

	/**
	 * <pre>
	 * @Method Name  : setHttpServletBean
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 클라이언트 요청 정보를 `httpServletBean` 객체에 저장한다.
	 * @param _httpServletRequest
	 * @param _httpServletResponse
	 * </pre>
	 */
	public static void setHttpServletBean(HttpServletRequest _httpServletRequest,HttpServletResponse _httpServletResponse)
	{
		HttpServletBean httpServletBean = (HttpServletBean)FrameworkBeans.findBean("httpServletBean");
		httpServletBean.addHttpServlet(_httpServletRequest, _httpServletResponse);
	}

	/**
	 * <pre>
	 * @Method Name  : getHttpServletBean
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 클라이언트 요청 정보를 반환한다.
	 * @return
	 * </pre>
	 */
	public static HttpServletBean findHttpServletBean()
	{
		return (HttpServletBean)FrameworkBeans.findBean("httpServletBean");
	}

	/**
	 * <pre>
	 * @Method Name  : findSessionBean
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 클라이언트 Session 정볼르 반환한다.
	 * @return
	 * </pre>
	 */
	public static SessionBean findSessionBean()
	{
		SessionBean sb = (SessionBean)FrameworkBeans.findBean("sessionBean");
		return sb;
	}

	/**
	 * @Method Name  : getGenericContext
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 프레임워크 GenericApplicationContext 반환
	 * @return
	 */
	public static GenericApplicationContext getGenericContext(){
		return (GenericApplicationContext)smApplicationContext;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(FrameworkBeans.class);
	private static ApplicationContext smApplicationContext = null;

}
