package cn.itcast.log.controller;

import cn.itcast.entity.PageResult;
import cn.itcast.entity.Result;
import cn.itcast.entity.StatusCode;
import cn.itcast.log.service.OperateLogService;
import cn.itcast.model.TbOperatelog;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author cat
 * @description
 * @date 2022/4/6 下午12:05
 */
@RestController
@RequestMapping("/operateLog")
public class OperateLogController {

    @Autowired
    private OperateLogService operateLogService;

    @RequestMapping("/search/{page}/{size}")
    public Result findList(@RequestBody Map dataMap, @PathVariable Integer page, @PathVariable  Integer size){

        Page<TbOperatelog> pageList = operateLogService.findPage(dataMap, page, size);
        PageResult pageResult=new PageResult(pageList.getTotal(),pageList.getResult());

        return new Result(true, StatusCode.OK,"查询成功",pageResult);
    }


    @RequestMapping("/add")
    public Result add(@RequestBody TbOperatelog operatelog){
        operateLogService.insert(operatelog);
        return new Result(true,StatusCode.OK,"添加成功");
    }

}
