package cn.itcast.log.mapper;

import cn.itcast.model.TbOperatelog;

import java.util.List;
import java.util.Map;

/**
 * @author cat
 * @description
 * @date 2022/4/6 上午11:41
 */
public interface OperateLogMapper {

    /**
     * @description 插入日志
     * @author Lemonade
     * @param: operationLog
     * @updateTime 2022/4/6 下午1:42
     */
    public void insert(TbOperatelog operationLog);

    /**
     * @description 根据条件查询日志
     * @author Lemonade
     * @param: searchMap
     * @updateTime 2022/4/6 下午1:42
     * @return: java.util.List<cn.itcast.model.TbOperatelog>
     */
    public List<TbOperatelog> search(Map searchMap);

}
