package com.adc.idea.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class InitBean implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		System.out.println("                  SOS                   ");
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
	}

}
