package com.zjl.litemall.db.service;

import com.zjl.litemall.db.dao.LitemallNoticeAdminMapper;
import com.zjl.litemall.db.example.LitemallNoticeAdminExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cat
 * @description
 * @date 2022/4/5 上午11:37
 */
@Service
public class NoticeAdminService {

    @Autowired
    LitemallNoticeAdminMapper noticeAdminMapper;

    public int countUnread(Integer adminId) {
        LitemallNoticeAdminExample example = new LitemallNoticeAdminExample();
        example.or().andAdminIdEqualTo(adminId).andReadTimeIsNull().andDeletedEqualTo(false);
        // 查通知管理员表
        return (int)noticeAdminMapper.countByExample(example);
    }

}
