package com.zjl.litemall.admin.controller;

import com.zjl.litemall.core.util.ResponseUtil;
import com.zjl.litemall.db.domain.LitemallAdmin;
import com.zjl.litemall.db.service.NoticeAdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cat
 * @description
 * @date 2022/4/5 上午11:35
 */
@RestController
@RequestMapping("/admin/profile")
public class ProfileController {
    @Autowired
    private NoticeAdminService noticeAdminService;

    @GetMapping("/nnotice")
    public Object nNotice() {
        int count = noticeAdminService.countUnread(getAdminId());
        return ResponseUtil.ok(count);
    }

    private Integer getAdminId(){
        Subject currentUser = SecurityUtils.getSubject();
        LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();
        return admin.getId();
    }
}
