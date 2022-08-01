package com.itheima.shiro.faceImpl;

import com.itheima.shiro.face.FilterChainFace;
import com.itheima.shiro.pojo.FilterChain;
import com.itheima.shiro.service.FilterChainService;
import com.itheima.shiro.utils.BeanConv;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.vo.FilterChainVo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description：过滤器链服务化接口
 */
@Service(version = "1.0.0",retries = 3,timeout = 5000)
public class FilterChainFaceImpl implements FilterChainFace {

    @Autowired
    FilterChainService filterChainService;

    @Override
    public List<FilterChainVo> findFilterChainList() {
        List<FilterChain> filterChainList = filterChainService.findFilterChainList();
        if (!EmptyUtil.isNullOrEmpty(filterChainList)){
            return BeanConv.toBeanList(filterChainList, FilterChainVo.class);
        }
        return null;
    }
}
