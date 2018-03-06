package com.cherrypicks.tcc.cms.campaign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cherrypicks.tcc.cms.campaign.config.DataSourceConfig;
import com.cherrypicks.tcc.cms.campaign.config.DubboConfig;
import com.cherrypicks.tcc.cms.campaign.config.RedisConfig;


@SpringBootApplication
@EnableScheduling
@Import({DubboConfig.class, DataSourceConfig.class, RedisConfig.class})
public class Application extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
