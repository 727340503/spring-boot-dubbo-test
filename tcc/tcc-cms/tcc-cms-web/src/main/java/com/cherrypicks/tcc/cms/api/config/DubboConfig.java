package com.cherrypicks.tcc.cms.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;

@Configuration
@EnableAutoConfiguration
@DubboComponentScan({ "com.cherrypicks.tcc.cms.api" })
public class DubboConfig {
	
	@Value("${dubbo.application.name}")
	private String dubboApplicationName;
	
	@Value("${dubbo.protocol.name}")
	private String dubboProtocolName;
	
	@Value("${dubbo.protocol.port}")
	private int dubboProtocolPort;
	
	@Value("${dubbo.protocol.thread}")
	private int dubboProtocolThread;
	
	@Value("${dubbo.registry.protocol}")
	private String dubboRegistryProtocol;

	@Value("${dubbo.registry.address}")
	private String dubboRegistryAddress;
	
	@Value("${dubbo.provider.timeout}")
	private int dubboProviderTimeout;

	@Value("${dubbo.provider.retries}")
	private int dubboProviderRetries;
	
	@Value("${dubbo.provider.delay}")
	private int dubboProviderDelay;
	
	@Value("${dubbo.consumer.timeout}")
	private int dubboConsumerTimeout;
	
	@Value("${dubbo.consumer.check}")
	private boolean dubboConsumerCheck;

	/**
	 * 上下文配置
	 * @return
	 */
	@Bean
	public ApplicationConfig applicationConfig() {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName(dubboApplicationName);
		return applicationConfig;
	}

	/**
	 * 协议配置
	 * @return
	 */
	@Bean
	public ProtocolConfig protocolConfig() {
		// 服务提供者协议配置
		ProtocolConfig protocolConfig = new ProtocolConfig();
		protocolConfig.setName(dubboProtocolName);
		protocolConfig.setPort(dubboProtocolPort);
		//线程池大小，默认100
		protocolConfig.setThreads(dubboProtocolThread);
		return protocolConfig;
	}

	/**
	 * 注册配置中心
	 * @return
	 */
	@Bean
	public RegistryConfig registryConfig() {
		// 连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
		registry.setProtocol(dubboRegistryProtocol);
		registry.setAddress(dubboRegistryAddress);
		return registry;
	}

	/**
	 * dubbo服务提供
	 * 
	 * @param applicationConfig
	 * @param registryConfig
	 * @param protocolConfig
	 * @return
	 */
	@Bean(name="defaultProvider")
	public ProviderConfig providerConfig(ApplicationConfig applicationConfig, final RegistryConfig registryConfig, final ProtocolConfig protocolConfig) {
		ProviderConfig providerConfig = new ProviderConfig();
		providerConfig.setTimeout(dubboProviderTimeout);
		providerConfig.setRetries(dubboProviderRetries);
		providerConfig.setDelay(dubboProviderDelay);
		providerConfig.setApplication(applicationConfig);
		providerConfig.setRegistry(registryConfig);
		providerConfig.setProtocol(protocolConfig);
		return providerConfig;
	}

	/**
	 * 服务消费者
	 * @return
	 */
	@Bean
	public ConsumerConfig consumerConfig() {
		ConsumerConfig consumerConfig = new ConsumerConfig();
		consumerConfig.setTimeout(dubboConsumerTimeout);
		consumerConfig.setCheck(dubboConsumerCheck);
		return consumerConfig;
	}

}
