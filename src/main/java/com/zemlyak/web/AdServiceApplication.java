package com.zemlyak.web;

import com.zemlyak.web.ad.creative.dao.CreativesDaoCacheProxy;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

import com.zemlyak.web.ad.creative.CreativesDao;
import com.zemlyak.web.ad.creative.dao.CreativesDaoTemplate;
import com.zemlyak.web.ad.creative.CreativeService;
import com.zemlyak.web.ad.creative.service.CreativeServiceImpl;

@Configuration
@SpringBootApplication
public class AdServiceApplication {
    @Autowired
    private JdbcOperations jdbcOperations;
    
    @Bean
    public ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
    
    @Bean
    public CreativesDao creativesDao(){
        return new CreativesDaoCacheProxy(new CreativesDaoTemplate(jdbcOperations));
    }
    
    @Bean
    public CreativeService creativeService(){
        return new CreativeServiceImpl(creativesDao());
    }

	public static void main(String[] args) {
		SpringApplication.run(AdServiceApplication.class, args);
	}
}
