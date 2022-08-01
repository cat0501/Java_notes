package com.zjl.litemall.db.service;

import com.zjl.litemall.db.dao.LitemallOrderMapper;
import com.zjl.litemall.db.example.LitemallOrderExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cat
 * @description
 * @date 2022/4/5 上午11:44
 */
@Service
public class OrderService {

    @Autowired
    LitemallOrderMapper orderMapper;

    public int count() {
        LitemallOrderExample orderExample = new LitemallOrderExample();
        orderExample.or().andDeletedEqualTo(false);

        return (int) orderMapper.countByExample(orderExample);
    }
}
