package com.itheima.shiro.core.base;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @Description：
 */
public class SimpleMapCache implements Cache<Object,Object>, Serializable {

    /**
     * Backing instance.
     */
    private final Map<Object, Object> map;

    /**
     * The name of this cache.
     */
    private final String name;

    public SimpleMapCache(String name, Map<Object, Object> backingMap) {
        if (name == null) {
            throw new IllegalArgumentException("Cache name cannot be null.");
        }
        if (backingMap == null) {
            throw new IllegalArgumentException("Backing map cannot be null.");
        }
        this.name = name;
        this.map = backingMap;
    }

    public Object get(Object key) throws CacheException {
        return map.get(key);
    }

    public Object put(Object key, Object value) throws CacheException {
        return map.put(key, value);
    }

    public Object remove(Object key) throws CacheException {
        return map.remove(key);
    }

    public void clear() throws CacheException {
        map.clear();
    }

    public int size() {
        return map.size();
    }

    public Set<Object> keys() {
        Set<Object> keys = map.keySet();
        if (!keys.isEmpty()) {
            return Collections.unmodifiableSet(keys);
        }
        return Collections.emptySet();
    }

    public Collection<Object> values() {
        Collection<Object> values = map.values();
        if (!CollectionUtils.isEmpty(values)) {
            return Collections.unmodifiableCollection(values);
        }
        return Collections.emptySet();
    }

    @Override
    public String toString() {
        return "SimpleMapCache{" +
                "map=" + map +
                ", name='" + name + '\'' +
                '}';
    }
}
