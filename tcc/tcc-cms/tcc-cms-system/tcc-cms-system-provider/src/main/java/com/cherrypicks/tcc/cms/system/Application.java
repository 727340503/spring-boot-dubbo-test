package com.cherrypicks.tcc.cms.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cherrypicks.tcc.cms.system.config.DataSourceConfig;
import com.cherrypicks.tcc.cms.system.config.DubboConfig;
import com.cherrypicks.tcc.cms.system.config.RedisConfig;

@SpringBootApplication
@EnableScheduling
@Import({DubboConfig.class, DataSourceConfig.class, RedisConfig.class})
public class Application extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
