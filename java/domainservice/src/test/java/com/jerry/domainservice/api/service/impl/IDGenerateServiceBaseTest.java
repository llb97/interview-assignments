package com.jerry.domainservice.api.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jerry.domainservice.api.cache.CacheProvider;

@ExtendWith(MockitoExtension.class)
class IDGenerateServiceBaseTest {

	@InjectMocks
	IDGenerateServiceBase service;

	@Mock
	CacheProvider<String, String> cacheProvider;

	@Test
	void testGenerate() {
		Assertions.assertDoesNotThrow(() -> service.generate("www.baidu.com"));
		Assertions.assertDoesNotThrow(() -> service.generate("www.baidu.com"));
		Assertions.assertEquals(service.generate("www.baidu.com").length(), 8);
	}
	
	@Test
	void testGetDomainName()  {
		Assertions.assertEquals(service.getDomainName("HrRxmMJD"),"");
	}

}
