package com.itheima.shiro.core.filter;

import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.utils.ShiroUserUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.redisson.api.RDeque;
import org.redisson.api.RedissonClient;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Description：
 */
@Log4j2
public class KickedOutAuthorizationFilter extends AccessControlFilter {

    private RedissonClient redissonClient;

    SessionDAO redisSessionDao;

    DefaultWebSessionManager sessionManager;

    public KickedOutAuthorizationFilter(RedissonClient redissonClient, SessionDAO redisSessionDao, DefaultWebSessionManager sessionManager) {
        this.redissonClient = redissonClient;
        this.redisSessionDao = redisSessionDao;
        this.sessionManager = sessionManager;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        if (!subject.isAuthenticated()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }
        //存放session对象进入队列
        String sessionId = ShiroUserUtil.getShiroSessionId();
        String LoginName = ShiroUserUtil.getShiroUser().getLoginName();
        RDeque<String> queue = redissonClient.getDeque("KickedOutAuthorizationFilter:"+LoginName);
        //判断sessionId是否存在于此用户的队列中
        boolean flag = queue.contains(sessionId);
        if (!flag) {
            queue.addLast(sessionId);
        }
        //如果此时队列大于1，则开始踢人
        if (queue.size() > 1) {
            sessionId = queue.getFirst();
            queue.removeFirst();
            Session session = null;
            try {
                session = sessionManager.getSession(new DefaultSessionKey(sessionId));
            }catch (UnknownSessionException ex){
                log.info("session已经失效");
            }catch (ExpiredSessionException expiredSessionException){
                log.info("session已经过期");
            }
            if (!EmptyUtil.isNullOrEmpty(session)){
                redisSessionDao.delete(session);
            }
        }
        return true;
    }
}
