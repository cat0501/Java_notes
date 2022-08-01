

> 1

```java
loginService.route(loginVo);
```



> 2

> 关于SimpleToken：封装了用户名username、密码password、token类型tokenType的实体
>
> ```
> shiro-core:1.3.2
> org.apache.shiro.authc
> ```

```java
SimpleToken token = new SimpleToken(null, loginVo.getLoginName(), loginVo.getPassWord());
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220317215747297.png)

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220317220733342.png)



> 3

```java
Subject subject = SecurityUtils.getSubject();
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220317221604021.png)



> 进入源码部分

> 4

```java
subject.login(token);
```



```java
Subject subject = securityManager.login(this, token);
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220317222723370.png)



> DefaultSecurityManager

```java
info = authenticate(token);
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220317223027500.png)



> AuthenticatingSecurityManager

```java
return this.authenticator.authenticate(token);
```



```java
AuthenticationInfo info;
try {
    info = doAuthenticate(token);
```





> ModularRealmAuthenticator extends AbstractAuthenticator

```java
protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        assertRealmsConfigured();
        Collection<Realm> realms = getRealms();
        if (realms.size() == 1) {
            return doSingleRealmAuthentication(realms.iterator().next(), authenticationToken);
        } else {
            return doMultiRealmAuthentication(realms, authenticationToken);
        }
    }
```



```
AuthenticationInfo info = realm.getAuthenticationInfo(token);
```



> 

```
info = doGetAuthenticationInfo(token);
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220317232217446.png)





```java
protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
    CredentialsMatcher cm = getCredentialsMatcher();
    if (cm != null) {
        if (!cm.doCredentialsMatch(token, info)) {
```



```java
public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    Object tokenHashedCredentials = hashProvidedCredentials(token, info);
    Object accountCredentials = getCredentials(info);
    return equals(tokenHashedCredentials, accountCredentials);
}
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220317232653177.png)















