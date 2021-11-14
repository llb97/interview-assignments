package com.jerry.domainservice.api.cache.impl;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.jerry.domainservice.api.cache.CacheProvider;
import com.jerry.domainservice.api.cache.exception.CachedObjectAlreadyExistedException;
import com.jerry.domainservice.api.cache.exception.NoSpaceException;
import com.jerry.domainservice.api.model.DomainInformationDto;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BiMapCacheProvider implements CacheProvider<String, String>{
	private BiMap<DomainInformationDto, String> bimap = HashBiMap.create();

	private AtomicInteger total = new AtomicInteger(0);
	
	private Object o = new Object();

	@Setter
	private long maxCacheSize = 0;

	@Setter
	// 每次准备抛弃的缓存数量， 默认20
	private int numberShouldBeEvicted = 20;

	@Setter
	// 缓存存活期间，如果当前时间 - 缓存最后访问时间 > survivePeriod， 那么该缓存则可以被清理。 默认60秒
	private long survivePeriod = 60000;// 180000L;

	/**
	 * 根据长域名得到短域名
	 * @param key	长域名
	 * @param value	短域名
	 * @throws CachedObjectAlreadyExistedException
	 * @throws NoSpaceException
	 */
	@Override
	public void add(String key, String value) throws CachedObjectAlreadyExistedException, NoSpaceException {
		synchronized (o) {
			if (bimap.size() > maxCacheSize) {
				throw new NoSpaceException("Service Rejected.");
			}
			DomainInformationDto info = new DomainInformationDto();
			
			info.setDomainName(key);
			info.setLastUpdateTime(System.currentTimeMillis());
			try {
				bimap.put(info, value);
			} catch (IllegalArgumentException e) {
				throw new CachedObjectAlreadyExistedException("Cache object " + value + " is existed in the cache.");
			}
		}
		// put id
		total.incrementAndGet();
	}

	@Override
	public void evict() {
		
		log.trace("Try to evict.");
		
		// 如果缓存数量小于最大缓存数的60%， 则跳过
		if (bimap.size() < maxCacheSize * 0.6) {
			return;
		}

		log.trace("Try to evict the last " + numberShouldBeEvicted + " number of domains.");

		// 取指定个数随机短域名信息
		Set<DomainInformationDto> keySet = bimap.keySet();
		int removeInt = numberShouldBeEvicted;
		for (DomainInformationDto info : keySet) {
			if (removeInt <= 0) {
				break;
			}
			if (info != null && System.currentTimeMillis() - info.getLastUpdateTime() > survivePeriod) {
				keySet.remove(info);
				total.decrementAndGet();
				log.trace("id={} will be evicted.", info.getDomainName());
				removeInt--;
			}
		}
		log.trace("Domain cache has been evicted.");
	}

	@Override
	public String getKey(String value) {
		DomainInformationDto info = bimap.inverse().get(value);
		info.setLastUpdateTime(System.currentTimeMillis());
		return info.getDomainName();
	}

	@Override
	public String getValue(String key) {
		DomainInformationDto info = new DomainInformationDto();
		info.setDomainName(key);
		return bimap.get(info);
	}

	@Override
	public boolean containsKey(String key) {
		DomainInformationDto info = new DomainInformationDto();
		info.setDomainName(key);
		return this.bimap.containsKey(info);
	}
}
