
package com.itheima.shiro.core.base;

import com.itheima.shiro.utils.EmptyUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;


/**
 * @Description 缓存实现类, 实现序列 接口方便对象存储于第三方容器(Map存放键值对)
 */
public class SimpleMapCache implements Cache<Object, Object>, Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -917211877137436447L;

    private final Map<Object, Object> attributes;

    private final String name;

    public SimpleMapCache(String name, Map<Object, Object> backingMap) {
        if (name == null)
            throw new IllegalArgumentException("Cache name cannot be null.");
        if (backingMap == null) {
            throw new IllegalArgumentException("Backing map cannot be null.");
        } else {
            this.name = name;
            attributes = backingMap;
        }
    }

    public Object get(Object key) throws CacheException {
        return attributes.get(key);
    }

    public Object put(Object key, Object value) throws CacheException {
        return attributes.put(key, value);
    }

    public Object remove(Object key) throws CacheException {
        return attributes.remove(key);
    }

    public void clear() throws CacheException {
        attributes.clear();
    }

    public int size() {
        return attributes.size();
    }

    public Set<Object> keys() {
        Set<Object> keys = attributes.keySet();
        if (!keys.isEmpty())
            return Collections.unmodifiableSet(keys);
        else
            return Collections.emptySet();
    }

    public Collection<Object> values() {
        Collection<Object> values = attributes.values();
        if (!EmptyUtil.isNullOrEmpty(values))
            return Collections.unmodifiableCollection(values);
        else
            return Collections.emptySet();
    }

    @Override
    public String toString() {
        return "SimpleMapCache [attributes=" + attributes + ", name=" + name
                + ", keys()=" + keys() + ", size()=" + size() + ", values()="
                + values() + "]";
    }
}
