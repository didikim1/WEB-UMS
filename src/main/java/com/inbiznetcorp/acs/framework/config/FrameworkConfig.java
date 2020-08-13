package com.inbiznetcorp.acs.framework.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.descriptor.JspPropertyGroupDescriptor;
import javax.servlet.descriptor.TaglibDescriptor;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroup;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroupDescriptorImpl;
import org.apache.tomcat.util.descriptor.web.TaglibDescriptorImpl;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.inbiznetcorp.acs.framework.config.interceptor.AuthenticationInterceptor;

/*
 * https://gist.github.com/ghillert/39536f902d7ac0017964
 * http://kamsi76.tistory.com/entry/Spring4-JavaConfig-%EC%84%A4%EC%A0%95-WebInitializerjava
 */
@Configuration
public class FrameworkConfig extends WebMvcConfigurerAdapter implements EmbeddedServletContainerCustomizer {

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(new AuthenticationInterceptor());
		super.addInterceptors(registry);
	}

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container)
	{
		if(container instanceof TomcatEmbeddedServletContainerFactory) {

			final TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory
	          = (TomcatEmbeddedServletContainerFactory) container;

			tomcatEmbeddedServletContainerFactory.addContextCustomizers( new TomcatContextCustomizer() {

						@Override
						public void customize(Context context) {
							JspConfigDescriptor jspConfigDescriptor = new JspConfigDescriptor() {

									@Override
									public java.util.Collection<TaglibDescriptor> getTaglibs() {
										List<TaglibDescriptor> taglibDescriptors = new ArrayList<TaglibDescriptor>();

										TaglibDescriptor funLibrary = new TaglibDescriptorImpl("/WEB-INF/tags/jstl/funLibrary.tld", "funLibrary");
										taglibDescriptors.add(funLibrary);

										return taglibDescriptors;
									}

									@Override
									public java.util.Collection<JspPropertyGroupDescriptor> getJspPropertyGroups() {
										 Collection<JspPropertyGroupDescriptor> propertyGroup = new ArrayList<JspPropertyGroupDescriptor>();
							             JspPropertyGroupDescriptorImpl pgDescriptor = new JspPropertyGroupDescriptorImpl(new JspPropertyGroup());
							             propertyGroup.add(pgDescriptor);
							             return propertyGroup;
									}
								};

						 context.setJspConfigDescriptor(jspConfigDescriptor);

						}

			        }
			  );
		}

	}

}
