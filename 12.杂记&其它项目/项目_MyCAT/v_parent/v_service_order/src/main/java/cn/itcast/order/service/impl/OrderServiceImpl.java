package cn.itcast.order.service.impl;

import cn.itcast.model.TbOrder;
import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.service.OrderService;
import cn.itcast.util.IdWorker;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    //@Autowired
    //private OrderItemMapper orderItemMapper;
    //@Autowired
    //private IdWorker idWorker;

    /**
     * 增加
     * @param order
     */
    @Override
    public void add(TbOrder order){
        //1.获取购物车的相关数据(redis)
        
        //2.统计计算:总金额,总数量
        
        //3.填充订单数据并保存到tb_order

        //4.填充订单项数据并保存到tb_order_item

        //5.记录订单日志
        
        //6.扣减库存并增加销量

        //7.删除购物车数据(redis)

      
    }
    
    
    /**
     * 条件+分页查询
     * @param searchMap 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public Page<TbOrder> findPage(Map<String,Object> searchMap, int page, int size){
        PageHelper.startPage(page,size);

        return (Page<TbOrder>)orderMapper.search(searchMap);
    }
}