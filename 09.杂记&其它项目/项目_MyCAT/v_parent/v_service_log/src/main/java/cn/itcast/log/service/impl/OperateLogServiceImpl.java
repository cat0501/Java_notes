package cn.itcast.log.service.impl;

import cn.itcast.log.mapper.OperateLogMapper;
import cn.itcast.log.service.OperateLogService;
import cn.itcast.model.TbOperatelog;
import cn.itcast.util.IdWorker;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author cat
 * @description
 * @date 2022/4/6 上午11:46
 */
@Service
@Transactional
public class OperateLogServiceImpl implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Autowired
    private IdWorker idworker;

    @Transactional
    public void insert(TbOperatelog operationLog){
        // 获取分布式ID
        long id = idworker.nextId();
        operationLog.setId(id);

        operateLogMapper.insert(operationLog);
    }

    public Page<TbOperatelog> findPage(Map searchMap, Integer pageNum , Integer pageSize){
        System.out.println(searchMap);

        PageHelper.startPage(pageNum,pageSize);
        List<TbOperatelog> list = operateLogMapper.search(searchMap);

        return (Page<TbOperatelog>) list;
    }

}
