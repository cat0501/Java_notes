package com.itheima.shiro.core.bridge;

import com.itheima.shiro.vo.ResourceVo;

import java.util.List;

/**
 * @Description：网关资源桥接器接口
 */
public interface ResourceBridgeService {

    /**
     * @Description 查询当前系统所有有效的DUBBO类型的服务
     * @param systemCode 系统编号：与mgt添加系统编号相同
     * @return
     */
    public List<ResourceVo> findValidResourceVoAll(String systemCode);
}
