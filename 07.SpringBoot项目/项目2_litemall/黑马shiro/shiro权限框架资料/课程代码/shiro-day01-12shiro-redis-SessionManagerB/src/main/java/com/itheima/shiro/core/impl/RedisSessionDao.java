package com.itheima.shiro.core.impl;

import com.itheima.shiro.constant.CacheConstant;
import com.itheima.shiro.utils.ShiroRedissionSerialize;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @Description：自定义统一sessiondao实现
 */
public class RedisSessionDao extends AbstractSessionDAO {

    @Resource(name = "redissonClientForShiro")
    RedissonClient redissonClient;

    private long globalSessionTimeout;

    /**
     * @Description 创建session
     * @param session 会话对象
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        //创建唯一标识的sessionId
        Serializable sessionId = generateSessionId(session);
        //为session会话指定唯一的sessionId
        assignSessionId(session, sessionId);
        //放入缓存中
        String key = CacheConstant.GROUP_CAS+sessionId.toString();
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.trySet(ShiroRedissionSerialize.serialize(session), globalSessionTimeout/1000, TimeUnit.SECONDS);
        return sessionId;
    }

    /**
     * @Description 读取sessio
     * @param sessionId 唯一标识
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        String key = CacheConstant.GROUP_CAS+sessionId.toString();
        RBucket<String> bucket = redissonClient.getBucket(key);
        return (Session) ShiroRedissionSerialize.deserialize(bucket.get());
    }

    /**
     * @Description 更新session
     * @param session 对象
     * @return
     */
    @Override
    public void update(Session session) throws UnknownSessionException {
        String key = CacheConstant.GROUP_CAS+session.getId().toString();
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set(ShiroRedissionSerialize.serialize(session), globalSessionTimeout/1000, TimeUnit.SECONDS);
    }

    /**
     * @Description 删除session
     * @param
     * @return
     */
    @Override
    public void delete(Session session) {
        String key = CacheConstant.GROUP_CAS+session.getId().toString();
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.delete();
    }

    /**
     * @Description 统计当前活跃用户数(后续扩展)
     * @param
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions() {
        return Collections.emptySortedSet();
    }

    public void setGlobalSessionTimeout(long globalSessionTimeout) {
        this.globalSessionTimeout = globalSessionTimeout;
    }
}
