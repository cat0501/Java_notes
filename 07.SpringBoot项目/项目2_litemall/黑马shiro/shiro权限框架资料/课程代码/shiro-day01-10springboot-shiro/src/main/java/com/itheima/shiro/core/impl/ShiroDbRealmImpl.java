package com.itheima.shiro.core.impl;

import com.itheima.shiro.constant.SuperConstant;
import com.itheima.shiro.core.ShiroDbRealm;
import com.itheima.shiro.core.base.ShiroUser;
import com.itheima.shiro.core.base.SimpleToken;
import com.itheima.shiro.core.bridge.UserBridgeService;
import com.itheima.shiro.pojo.User;
import com.itheima.shiro.utils.BeanConv;
import com.itheima.shiro.utils.EmptyUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description：自定义realm的抽象类实现
 */
public class ShiroDbRealmImpl extends ShiroDbRealm {

    @Autowired
    UserBridgeService userBridgeService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //token令牌信息
        SimpleToken simpleToken = (SimpleToken) token;
        //查询user对象
        User user = userBridgeService.findUserByLoginName(simpleToken.getUsername());
        if (EmptyUtil.isNullOrEmpty(user)){
            throw new UnknownAccountException("账号不存在！");
        }
        //构建认证令牌对象
        ShiroUser shiroUser = BeanConv.toBean(user, ShiroUser.class);
        shiroUser.setResourceIds(userBridgeService.findResourcesIds(shiroUser.getId()));
        String slat  = shiroUser.getSalt();
        String password = shiroUser.getPassWord();
        //构建认证信息对象:1、令牌对象 2、密文密码  3、加密因子 4、当前realm的名称
        return new SimpleAuthenticationInfo(shiroUser, password, ByteSource.Util.bytes(slat), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        return userBridgeService.getAuthorizationInfo(shiroUser);
    }

    @Override
    public void initCredentialsMatcher() {
        //指定密码算法
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher(SuperConstant.HASH_ALGORITHM);
        //指定迭代次数
        hashedCredentialsMatcher.setHashIterations(SuperConstant.HASH_INTERATIONS);
        //生效密码比较器
        setCredentialsMatcher(hashedCredentialsMatcher);
    }
}
