package com.itheima.shiro.client;

import com.itheima.shiro.core.bridge.FilterChainBridgeService;
import com.itheima.shiro.face.FilterChainFace;
import com.itheima.shiro.vo.FilterChainVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description：自定义过滤器桥接接口实现
 */
@Service("filterChainBridgeService")
public class FilterChainBridgeServiceImpl implements FilterChainBridgeService {

    @Reference(version = "1.0.0")
    private FilterChainFace filterChainFace;

    @Override
    public List<FilterChainVo> findFilterChainList() {
        return filterChainFace.findFilterChainList();
    }
}
