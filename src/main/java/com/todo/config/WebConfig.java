package com.todo.config;

import java.util.Arrays;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
	
	@Bean
	  public DozerBeanMapper dozerBean() {
	    DozerBeanMapper dozerBean = new DozerBeanMapper();
	    return dozerBean;
	  }

}
