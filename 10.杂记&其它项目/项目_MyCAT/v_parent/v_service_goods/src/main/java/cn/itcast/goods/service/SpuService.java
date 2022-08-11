package cn.itcast.goods.service;

import cn.itcast.model.TbSpu;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * @author cat
 * @description
 * @date 2022/4/5 下午11:11
 */
public interface SpuService {
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    TbSpu findById(String id);
    /**
     * 多条件分页查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    Page<TbSpu> findPage(Map<String, String> searchMap, int page, int size);
}
