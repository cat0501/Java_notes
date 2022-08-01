package com.itheima.shiro.client;

import com.itheima.shiro.core.bridge.ResourceBridgeService;
import com.itheima.shiro.face.ResourceAdapterFace;
import com.itheima.shiro.vo.ResourceVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description：网关资源桥接器接口实现
 */
@Component("resourceBridgeService")
public class ResourceBridgeServiceImpl implements ResourceBridgeService {

    @Value("${itheima.resource.systemcode}")
    private String systemCode;

    @Reference(version = "1.0.0")
    ResourceAdapterFace resourceAdapterFace;

    @Override
    public List<ResourceVo> findValidResourceVoAll(String systemCode) {
        return resourceAdapterFace.findValidResourceVoAll(systemCode);
    }
}
