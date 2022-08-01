package cn.itcast.order.service;

import cn.itcast.model.TbOrder;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * @author cat
 * @description
 * @date 2022/4/6 上午12:00
 */
public interface OrderService {

    /***
     * 新增
     * @param order
     */
    void add(TbOrder order);

    /**
     * 多条件分页查询订单
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    Page<TbOrder> findPage(Map<String, Object> searchMap, int page, int size);

}
