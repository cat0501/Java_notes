package com.itheima.shiro.face;

import com.itheima.shiro.vo.ResourceVo;

import java.util.List;

/**
 * @Description：网关资源服务接口
 */
public interface ResourceAdapterFace {

    /**
     * @Description 获得当前系统是由有效的dubbo的资源
     */
    List<ResourceVo> findValidResourceVoAll(String systemCode);
}
