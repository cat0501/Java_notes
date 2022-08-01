package com.zjl.litemall.db.service;

import com.zjl.litemall.db.dao.LitemallGoodsProductMapper;
import com.zjl.litemall.db.example.LitemallGoodsProductExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cat
 * @description
 * @date 2022/4/5 上午11:44
 */
@Service
public class GoodsProductService {

    @Autowired
    LitemallGoodsProductMapper goodsProductMapper;

    public int count() {
        LitemallGoodsProductExample goodsProductExample = new LitemallGoodsProductExample();

        goodsProductExample.or().andDeletedEqualTo(false);

        return (int) goodsProductMapper.countByExample(goodsProductExample);
    }

}
