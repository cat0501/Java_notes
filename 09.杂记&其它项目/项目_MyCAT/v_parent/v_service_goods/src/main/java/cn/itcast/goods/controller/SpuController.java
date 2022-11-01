package cn.itcast.goods.controller;

import cn.itcast.entity.PageResult;
import cn.itcast.entity.Result;
import cn.itcast.entity.StatusCode;
import cn.itcast.goods.aop.OperateLog;
import cn.itcast.goods.service.SpuService;
import cn.itcast.model.TbSpu;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author cat
 * @description
 * @date 2022/4/5 下午11:14
 */
@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @OperateLog
    public Result<TbSpu> findById(@PathVariable("id") String id){
        TbSpu spu = spuService.findById(id);
        return new Result<>(true, StatusCode.OK,"查询成功",spu);
    }

    /***
     * 分页搜索实现
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    @OperateLog
    public Result<TbSpu> findPage(@RequestBody Map<String, String> searchMap,
                                  @PathVariable  Integer page, @PathVariable  Integer size){
        Page<TbSpu> pageList = spuService.findPage(searchMap, page, size);

        // 返回的分页对象
        PageResult<TbSpu> pageResult=new PageResult<>(pageList.getTotal(),pageList.getResult());
        return new Result<>(true,StatusCode.OK,"查询成功",pageResult);
    }
}
