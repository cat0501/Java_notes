package cn.itcast.order.mapper;

import cn.itcast.model.TbOrder;

import java.util.List;
import java.util.Map;

public interface OrderMapper  {
    public List<TbOrder> search(Map<String,Object> searchMap);
}