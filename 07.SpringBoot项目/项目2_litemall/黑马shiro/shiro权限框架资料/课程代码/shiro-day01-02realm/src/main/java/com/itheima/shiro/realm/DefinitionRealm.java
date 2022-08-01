package com.itheima.shiro.realm;

import com.itheima.shiro.service.SecurityService;
import com.itheima.shiro.service.impl.SecurityServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @Description：
 */
public class DefinitionRealm extends AuthorizingRealm {

    /**
     * @Description 认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取登录名
        String loginName = (String) authenticationToken.getPrincipal();
        SecurityService securityService = new SecurityServiceImpl();
        String password = securityService.findPasswordByLoginName(loginName);
        if("".equals(password)){
            throw  new UnknownAccountException("账户不存在");
        }
        return new SimpleAuthenticationInfo(loginName,password,getName());
    }

    /**
     * @Description 鉴权方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


}
