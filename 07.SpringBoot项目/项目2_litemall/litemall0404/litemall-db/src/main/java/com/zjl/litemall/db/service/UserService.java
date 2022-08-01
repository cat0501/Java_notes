package com.zjl.litemall.db.service;

import com.zjl.litemall.db.dao.LitemallUserMapper;
import com.zjl.litemall.db.example.LitemallUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cat
 * @description
 * @date 2022/4/5 上午11:43
 */
@Service
public class UserService {
    @Autowired
    LitemallUserMapper userMapper;

    public int count() {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andDeletedEqualTo(false);

        return (int) userMapper.countByExample(example);
    }

}
