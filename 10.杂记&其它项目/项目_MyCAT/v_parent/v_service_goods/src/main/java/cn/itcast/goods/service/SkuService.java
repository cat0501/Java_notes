package cn.itcast.goods.service;

import cn.itcast.model.TbSku;

/**
 * @author cat
 * @description
 * @date 2022/4/5 下午11:45
 */
public interface SkuService {
    /**
     * 根据ID查询SKU
     * @param skuId
     * @return
     */
    public TbSku findById(String skuId);
}
