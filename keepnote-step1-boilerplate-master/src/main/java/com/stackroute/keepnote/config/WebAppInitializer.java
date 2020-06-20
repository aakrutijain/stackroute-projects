package com.stackroute.keepnote.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	public WebAppInitializer() {
		super();
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {

		return new Class[] { WebMvcConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {

		return new Class[] {};
	}

	@Override
	protected String[] getServletMappings() {

		return new String[] { "/" };
	}

}
