package com.jerry.domainservice.config;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jerry.domainservice.api.cache.CacheProvider;
import com.jerry.domainservice.api.cache.impl.BiMapCacheProvider;
import com.jerry.domainservice.api.cache.properties.HashMapCacheProperties;
import com.jerry.domainservice.properties.DomainServiceProperties;

@Configuration
public class CacheConfig {
	@Bean
	public CacheProvider<String, String> cacheProvider(DomainServiceProperties properties) {
		BiMapCacheProvider cacheProvider = new BiMapCacheProvider();
		HashMapCacheProperties hcp = properties.getHashmapcache();
		cacheProvider.setNumberShouldBeEvicted(hcp.getNumberShouldBeEvicted());
		cacheProvider.setMaxCacheSize(hcp.getMaxCacheSize());
		cacheProvider.setSurvivePeriod(hcp.getSurvivePeriod());
		
		// 每秒执行下evict
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				cacheProvider.evict();
			}
			
		}, 1000L,1000L);
		
		return cacheProvider;
	}
}
