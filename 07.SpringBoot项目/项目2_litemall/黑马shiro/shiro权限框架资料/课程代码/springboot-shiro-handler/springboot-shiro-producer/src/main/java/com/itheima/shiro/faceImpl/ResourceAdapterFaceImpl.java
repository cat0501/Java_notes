package com.itheima.shiro.faceImpl;

import com.itheima.shiro.face.ResourceAdapterFace;
import com.itheima.shiro.pojo.Resource;
import com.itheima.shiro.service.ResourceService;
import com.itheima.shiro.utils.BeanConv;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.vo.ResourceVo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description：网关资源服务接口实现
 */
@Service(version = "1.0.0", retries = 3,timeout = 5000)
public class ResourceAdapterFaceImpl implements ResourceAdapterFace {

    @Autowired
    ResourceService resourceService;

    @Override
    public List<ResourceVo> findValidResourceVoAll(String systemCode) {
        List<Resource> resourceList =  resourceService.findValidResourceVoAll(systemCode);
        if (!EmptyUtil.isNullOrEmpty(resourceList)){
            return BeanConv.toBeanList(resourceList, ResourceVo.class);
        }
        return  null;
    }
}
