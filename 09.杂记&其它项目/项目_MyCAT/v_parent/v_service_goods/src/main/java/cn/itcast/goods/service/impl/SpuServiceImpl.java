package cn.itcast.goods.service.impl;

import cn.itcast.goods.mapper.SpuMapper;
import cn.itcast.goods.service.SpuService;
import cn.itcast.model.TbSpu;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author cat
 * @description
 * @date 2022/4/5 下午11:12
 */
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Override
    public TbSpu findById(String id){
        return  spuMapper.findById(id);
    }

    /**
     * 条件+分页查询
     * @param searchMap 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public Page<TbSpu> findPage(Map<String,String> searchMap, int page, int size){
        // 设置分页查询的参数
        PageHelper.startPage(page,size);
        return (Page<TbSpu>) spuMapper.search(searchMap);
    }

}
