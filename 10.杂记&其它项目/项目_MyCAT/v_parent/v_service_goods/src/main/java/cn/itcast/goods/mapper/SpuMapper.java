package cn.itcast.goods.mapper;

import cn.itcast.model.TbSpu;

import java.util.List;
import java.util.Map;

/**
 * @author cat
 * @description
 * @date 2022/4/5 下午11:01
 */
public interface SpuMapper {

    public TbSpu findById(String spuId);

    /**
     * @description 条件查询: 模糊查询和精确查询结合，查询条件用map封装。使用到动态sql。
     * @author Lemonade
     * @param: searchMap
     * @updateTime 2022/4/5 下午11:19
     * @return: java.util.List<cn.itcast.model.TbSpu>
     */
    public List<TbSpu> search(Map<String,String> searchMap);

}
