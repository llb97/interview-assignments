package com.jerry.domainservice.api.cache;

import com.jerry.domainservice.api.cache.exception.CachedObjectAlreadyExistedException;
import com.jerry.domainservice.api.cache.exception.NoSpaceException;

/**
 * Cache provider
 * @author jerry
 *
 * @param <K>
 * @param <V>
 */
public interface CacheProvider<K,V> {
	void add(K key,V obj) throws CachedObjectAlreadyExistedException, NoSpaceException;
	void evict();
	K getKey(V obj);
	V getValue(K key);
	boolean containsKey(K key);
}
