package com.jerry.domainservice.api.cache.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.jerry.domainservice.api.cache.exception.CachedObjectAlreadyExistedException;
import com.jerry.domainservice.api.cache.exception.NoSpaceException;

class BiMapCacheProviderTest {
	
	@InjectMocks
	BiMapCacheProvider cacheProvider = new BiMapCacheProvider();

	final static long MAX_CACHE_SIZE = 100; 
	
	final static int NUMBER_SHOULD_BE_EVICTED = 20;
	
	final static long SURVIVE_PERIOD = 5000;
	
	@BeforeEach
	void setup() {
		cacheProvider.setMaxCacheSize(MAX_CACHE_SIZE);
		cacheProvider.setNumberShouldBeEvicted(NUMBER_SHOULD_BE_EVICTED);
		cacheProvider.setSurvivePeriod(SURVIVE_PERIOD);
	}

	@Test
	void testAdd() {
		Assertions.assertDoesNotThrow(()->cacheProvider.add("1", "testvalue"));
	}
	
	@Test
	void testAddWithExceptions() {
		// 测试重复的对象
		Assertions.assertThrows(CachedObjectAlreadyExistedException.class,()->{
			cacheProvider.add("1", "testvalue2");
			cacheProvider.add("2", "testvalue2");
		});
	}

	@Test
	void testEvict() throws CachedObjectAlreadyExistedException, NoSpaceException{
		for(int i=0;i<MAX_CACHE_SIZE;i++) {			
			cacheProvider.add(String.valueOf(i), "testvalue" + String.valueOf(i));			
		}
		cacheProvider.evict();
	}
	
	@Test
	// 测试
	void testEvict2() throws CachedObjectAlreadyExistedException, NoSpaceException, InterruptedException {
		for(int i=0;i<MAX_CACHE_SIZE*0.5;i++) {
			cacheProvider.add(String.valueOf(i), "testvalue" + String.valueOf(i));
		}
		Thread.sleep(50000);
		cacheProvider.evict();
	}
	

	@Test
	void testGetKey() throws CachedObjectAlreadyExistedException, NoSpaceException {
		cacheProvider.add("1", "testvalue1");
		Assertions.assertEquals(cacheProvider.getKey("testvalue1"),"1");
	}

	@Test
	void testGetValue() throws CachedObjectAlreadyExistedException, NoSpaceException {
		cacheProvider.add("1", "testvalue1");
		Assertions.assertEquals(cacheProvider.getValue("1"),"testvalue1");
	}

	@Test
	void testContainsKey() throws CachedObjectAlreadyExistedException, NoSpaceException {
		cacheProvider.add("1", "testvalue1");
		Assertions.assertTrue(cacheProvider.containsKey("1"));
		Assertions.assertFalse(cacheProvider.containsKey("2"));
	}
}
