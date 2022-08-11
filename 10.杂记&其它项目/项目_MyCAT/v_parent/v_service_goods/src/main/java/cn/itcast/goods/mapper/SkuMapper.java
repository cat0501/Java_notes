package cn.itcast.goods.mapper;

import cn.itcast.model.TbSku;

/**
 * @author cat
 * @description
 * @date 2022/4/5 下午11:02
 */
public interface SkuMapper {

    //根据ID查询SKU
    public TbSku findById(String skuId);

}
