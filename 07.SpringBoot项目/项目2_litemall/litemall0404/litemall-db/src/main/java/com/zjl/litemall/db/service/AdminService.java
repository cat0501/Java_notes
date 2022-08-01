package com.zjl.litemall.db.service;

import com.zjl.litemall.db.dao.LitemallAdminMapper;
import com.zjl.litemall.db.domain.LitemallAdmin;
import com.zjl.litemall.db.example.LitemallAdminExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cat
 * @description
 * @date 2022/4/5 上午10:51
 */
@Service
public class AdminService {

    @Autowired
    LitemallAdminMapper adminMapper;

    public int updateById(LitemallAdmin admin) {
        admin.setUpdateTime(LocalDateTime.now());
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    public List<LitemallAdmin> findAdmin(String username) {
        LitemallAdminExample example = new LitemallAdminExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }

}
