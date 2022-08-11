package cn.itcast.feign.client;

import cn.itcast.entity.Result;
import cn.itcast.model.TbOperatelog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cat
 * @description
 * @date 2022/4/6 下午12:41
 */

// 指定微服务名称log
@FeignClient("log")
public interface OperateLogFeign {

    @RequestMapping("/operateLog/add")
    public Result add(@RequestBody TbOperatelog operatelog);

}
