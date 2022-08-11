package cn.itcast.goods.controller;

import cn.itcast.entity.Result;
import cn.itcast.entity.StatusCode;
import cn.itcast.goods.service.SkuService;
import cn.itcast.model.TbSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cat
 * @description
 * @date 2022/4/5 下午11:46
 */
@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/sku")
public class SkuController {
    @Autowired
    private SkuService skuService;

    /**
     * @description 根据ID查询数据
     * @author Lemonade
     * @param: id
     * @updateTime 2022/4/5 下午11:48
     * @return: cn.itcast.entity.Result<cn.itcast.model.TbSku>
     */
    @GetMapping("/{id}")
    public Result<TbSku> findById(@PathVariable("id") String id){
        TbSku sku = skuService.findById(id);
        return new Result<>(true, StatusCode.OK,"查询成功",sku);
    }
}
