package com.outsourcing.outsourcingproject.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.outsourcing.outsourcingproject.common.filter.JwtAuthenticationFilter;
import com.outsourcing.outsourcingproject.common.filter.RoleCheckFilter;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;

@Configuration
public class WebConfig {
	@Bean
	public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter(JwtUtil jwtUtil) {
		FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new JwtAuthenticationFilter(jwtUtil));
		registrationBean.addUrlPatterns("/api/*");
		registrationBean.setOrder(1); // 인증 먼저
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<RoleCheckFilter> roleCheckFilter() {
		FilterRegistrationBean<RoleCheckFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new RoleCheckFilter());
		registrationBean.addUrlPatterns("/api/*");
		registrationBean.setOrder(2); // 권한 다음
		return registrationBean;
	}
}
