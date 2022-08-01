package com.zjl.litemall.db.service;

import com.zjl.litemall.db.dao.LitemallGoodsMapper;
import com.zjl.litemall.db.example.LitemallGoodsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cat
 * @description
 * @date 2022/4/5 上午11:44
 */
@Service
public class GoodsService {


    @Autowired
    LitemallGoodsMapper goodsMapper;

    public int count() {
        LitemallGoodsExample goodsExample = new LitemallGoodsExample();
        goodsExample.or().andDeletedEqualTo(false);

        return (int) goodsMapper.countByExample(goodsExample);
    }

}
