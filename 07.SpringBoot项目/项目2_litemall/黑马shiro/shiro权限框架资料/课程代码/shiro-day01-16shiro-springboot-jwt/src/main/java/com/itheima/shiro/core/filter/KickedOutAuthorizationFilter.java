package com.itheima.shiro.core.filter;

import com.itheima.shiro.constant.CacheConstant;
import com.itheima.shiro.core.impl.RedisSessionDao;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.utils.ShiroUserUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.redisson.api.RDeque;
import org.redisson.api.RedissonClient;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Description：自定义踢出过滤器
 */
@Log4j2
public class KickedOutAuthorizationFilter extends AccessControlFilter {

    private RedissonClient redissonClient;

    private RedisSessionDao redisSessionDao;

    private DefaultWebSessionManager defaultWebSessionManager;

    public KickedOutAuthorizationFilter(RedissonClient redissonClient, RedisSessionDao redisSessionDao, DefaultWebSessionManager defaultWebSessionManager) {
        this.redissonClient = redissonClient;
        this.redisSessionDao = redisSessionDao;
        this.defaultWebSessionManager = defaultWebSessionManager;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //1、只针对登录用户处理，首先判断是否登录
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated()){
            return true;
        }
        //2、使用RedissionClien创建队列
        String sessionId = ShiroUserUtil.getShiroSessionId();
        String logiName = ShiroUserUtil.getShiroUser().getLoginName();
        //2.1当前用户的队列
        RDeque<String> deque = redissonClient.getDeque(CacheConstant.GROUP_CAS + "kickedOutAuthorizationFilter:" + logiName);
        //3、判断当前sessionId是否存在于此用户的队列=key:登录名 value：多个sessionId
        boolean flag = deque.contains(sessionId);
        //4、不存在则放入队列尾端==>存入sessionId
        if (!flag){
            deque.addLast(sessionId);
        }
        //5、判断当前队列大小是否超过限定此账号的可在线人数
        if (deque.size()>1){
            //6、超过：
            //*从队列头部拿到用户sessionId
            //*从sessionManger根据sessionId拿到session
            //*从sessionDao中移除session会话
            sessionId = deque.getFirst();
            deque.removeFirst();
            Session session = null;
            try {
                session = defaultWebSessionManager.getSession(new DefaultSessionKey(sessionId));
            }catch (UnknownSessionException ex){
                log.info("session已经失效");
            }catch (ExpiredSessionException expiredSessionException){
                log.info("session已经过期");
            }
            if (!EmptyUtil.isNullOrEmpty(session)){
                redisSessionDao.delete(session);
            }
        }

        //7、未超过：放过操作
        return true;
    }
}
