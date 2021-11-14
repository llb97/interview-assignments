package com.jerry.domainservice.api.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.jerry.domainservice.api.cache.CacheProvider;
import com.jerry.domainservice.api.cache.exception.CachedObjectAlreadyExistedException;
import com.jerry.domainservice.api.cache.exception.NoSpaceException;
import com.jerry.domainservice.api.exception.ServiceRejectException;
import com.jerry.domainservice.api.service.IDGenerateService;
import com.jerry.domainservice.api.service.util.NumberUtils;
import com.jerry.domainservice.properties.DomainServiceProperties;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IDGenerateServiceBase implements IDGenerateService {
 
	@Resource
	@Setter
	private CacheProvider<String, String> cacheProvider;
	
	@Resource
	private DomainServiceProperties properties;
	
	@Override
	public String generate(String longDomainName) {
		
		log.trace("Generate short domain information, longDomainName={}",longDomainName);
		
		String id = null;
		
		String shortName = this.cacheProvider.getValue(longDomainName);
		if (StringUtils.isNotEmpty(shortName)) {
			return shortName;
		}
		int repetitionLimit = 5;		// 连续重复超过5次，说民系统出现异常，不能提工服务
		do {
			if (repetitionLimit <= 0) {
				throw new ServiceRejectException("Service rejected.");
			}
			id = NumberUtils.convert(NumberUtils.SHORT_DOMAIN_LENGTH);
			
			try {
				cacheProvider.add(longDomainName, id);
				break;
			} catch (CachedObjectAlreadyExistedException e) {
				log.trace(e.getMessage() + ",shortDomainInformation={}", id);
				repetitionLimit--;
			} catch (NoSpaceException e) {
				throw new ServiceRejectException("Service rejected.",e);
			}
		} while (true);
		log.trace("Generate short domain information successfully, longDomainName={}, shortDomainInformation={}",longDomainName,id);
		return id;
	}
	
	@Override
	public String getDomainName(String id) {
		// 从cache中获取短域名对应的域名信息
		String domainName = cacheProvider.getKey(id);
		return domainName != null ? domainName : "";
	}
}
