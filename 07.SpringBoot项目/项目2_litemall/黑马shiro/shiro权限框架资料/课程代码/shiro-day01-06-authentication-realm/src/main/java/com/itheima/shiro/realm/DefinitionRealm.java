package com.itheima.shiro.realm;

import com.itheima.shiro.service.SecurityService;
import com.itheima.shiro.service.impl.SecurityServiceImpl;
import com.itheima.shiro.tools.DigestsUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.List;
import java.util.Map;

/**
 * @Description：
 */
public class DefinitionRealm extends AuthorizingRealm {

    public DefinitionRealm() {
        //指定密码匹配方式sha1
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher(DigestsUtil.SHA1);
        //指定密码迭代此时
        hashedCredentialsMatcher.setHashIterations(DigestsUtil.ITERATIONS);
        //使用父层方法是匹配方式生效
        setCredentialsMatcher(hashedCredentialsMatcher);
    }

    /**
     * @Description 认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取登录名
        String loginName = (String) authenticationToken.getPrincipal();
        SecurityService securityService = new SecurityServiceImpl();
        Map<String, String> map = securityService.findPasswordByLoginName(loginName);
        if(map.isEmpty()){
            throw  new UnknownAccountException("账户不存在");
        }
        String salt = map.get("salt");
        String password = map.get("password");
        return new SimpleAuthenticationInfo(loginName,password, ByteSource.Util.bytes(salt),getName());
    }

    /**
     * @Description 鉴权方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //拿到用户凭证信息
        String loginName = (String) principalCollection.getPrimaryPrincipal();
        //从数据库中查询对应的角色和权限
        SecurityService securityService = new SecurityServiceImpl();
        List<String> roles = securityService.findRoleByLoginName(loginName);
        List<String> permissions = securityService.findPermissionByLoginName(loginName);
        //构建资源校验对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }


}
