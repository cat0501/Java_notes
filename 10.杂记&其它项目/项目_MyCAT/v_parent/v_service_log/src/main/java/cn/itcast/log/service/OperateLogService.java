package cn.itcast.log.service;

import cn.itcast.model.TbOperatelog;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * @author cat
 * @description
 * @date 2022/4/6 上午11:45
 */
public interface OperateLogService {
    public void insert(TbOperatelog operationLog);
    
    // 根据条件查询日志列表（分页）
    public Page<TbOperatelog> findPage(Map searchMap, Integer pageNum , Integer pageSize);
}
