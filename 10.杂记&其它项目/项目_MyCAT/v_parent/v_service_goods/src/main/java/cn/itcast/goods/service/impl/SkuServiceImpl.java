package cn.itcast.goods.service.impl;

import cn.itcast.goods.mapper.SkuMapper;
import cn.itcast.goods.service.SkuService;
import cn.itcast.model.TbSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author cat
 * @description
 * @date 2022/4/5 下午11:46
 */
@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public TbSku findById(String skuId) {
        return skuMapper.findById(skuId);
    }

}
