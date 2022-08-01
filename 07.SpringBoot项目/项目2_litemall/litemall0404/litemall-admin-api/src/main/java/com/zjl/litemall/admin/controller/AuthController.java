package com.zjl.litemall.admin.controller;

import com.zjl.litemall.admin.util.AdminResponseCode;
import com.zjl.litemall.admin.util.Permission;
import com.zjl.litemall.admin.util.PermissionUtil;
import com.zjl.litemall.core.util.JacksonUtil;
import com.zjl.litemall.core.util.ResponseUtil;
import com.zjl.litemall.db.domain.LitemallAdmin;
import com.zjl.litemall.db.service.AdminService;
import com.zjl.litemall.db.service.PermissionService;
import com.zjl.litemall.db.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author cat
 * @description
 * @date 2022/4/5 上午10:19
 */

@RestController
@RequestMapping("/admin/auth")
public class AuthController {

    @Autowired
    AdminService adminService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;

    @PostMapping("/login")
    public Object login(@RequestBody String body, HttpServletRequest request) {
        // 1, 用户名和密码
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResponseUtil.badArgument();
        }
        // 2,调用SecurityUtils.getSubject().login(token)方法
        // 项目中我们习惯直接使用SecurityUtils.getSubject() 获取当前登录用户的信息
        Subject currentUser = SecurityUtils.getSubject();

        try {
            currentUser.login(new UsernamePasswordToken(username, password));
        } catch (UnknownAccountException uae) {
            return ResponseUtil.fail(AdminResponseCode.ADMIN_INVALID_ACCOUNT, "账号或密码错误");
        } catch (LockedAccountException lae) {
            return ResponseUtil.fail(AdminResponseCode.ADMIN_INVALID_ACCOUNT, "账号锁定不可用");
        } catch (AuthenticationException ae) {
            return ResponseUtil.fail(AdminResponseCode.ADMIN_INVALID_ACCOUNT, "认证失败");
        }

        // 这一步是否多余，有待验证
        currentUser = SecurityUtils.getSubject();
        LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();

        admin.setLastLoginTime(LocalDateTime.now());
        // 更新管理员表 litemall_admin
        adminService.updateById(admin);

        // userInfo
        Map<String, Object> adminInfo = new HashMap<String, Object>();
        adminInfo.put("nickName", admin.getUsername());
        adminInfo.put("avatar", admin.getAvatar());

        Map<Object, Object> result = new HashMap<>();
        result.put("userInfo", adminInfo);
        result.put("token", currentUser.getSession().getId());
        return ResponseUtil.ok(result);
    }

    @GetMapping("/info")
    public Object info() {
        Subject subject = SecurityUtils.getSubject();
        LitemallAdmin admin = (LitemallAdmin) subject.getPrincipal();

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("nickName", admin.getUsername());
        data.put("avatar", admin.getAvatar());

        Integer[] roleIds = admin.getRoleIds();
        Set<String> roles = roleService.queryByIds(roleIds);
        Set<String> permissions = permissionService.queryByRoleIds(roleIds);

        data.put("roles",roles);
        // 这里需要转换perms结构，因为对于前端而已API形式的权限更容易理解
        data.put("perms",toApi(permissions));

        return ResponseUtil.ok(data);
    }

    @Autowired
    private ApplicationContext context;
    private HashMap<String, String> systemPermissionsMap = null;

    private Collection<String> toApi(Set<String> permissions) {
        if (systemPermissionsMap == null) {
            systemPermissionsMap = new HashMap<>();
            final String basicPackage = "org.linlinjava.litemall.admin";
            List<Permission> systemPermissions = PermissionUtil.listPermission(context, basicPackage);
            for (Permission permission : systemPermissions) {
                String perm = permission.getRequiresPermissions().value()[0];
                String api = permission.getApi();
                systemPermissionsMap.put(perm, api);
            }
        }

        Collection<String> apis = new HashSet<>();
        for (String perm : permissions) {
            String api = systemPermissionsMap.get(perm);
            apis.add(api);

            if (perm.equals("*")) {
                apis.clear();
                apis.add("*");
                return apis;
                //                return systemPermissionsMap.values();

            }
        }
        return apis;
    }




}
