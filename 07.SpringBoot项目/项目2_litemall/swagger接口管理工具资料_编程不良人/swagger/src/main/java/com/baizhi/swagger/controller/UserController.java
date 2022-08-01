package com.baizhi.swagger.controller;

import com.baizhi.swagger.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
@Api(tags="用户服务相关接口描述")
public class UserController {

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有用户接口",
            notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用来查询所有用户信息的接口")
    public Map<String, Object> findAll() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", "查询所有成功!");
        map.put("state", true);
        return map;
    }


    @PostMapping("save")
    @ApiOperation(value = "保存用户信息接口",notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用来保存用户信息的接口")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id",value = "用户id",dataType = "String",defaultValue = "21",paramType = "body"),
//            @ApiImplicitParam(name ="name",value = "用户姓名",dataType = "String",defaultValue = "xiaochen",paramType = "body")
//    })
    @ApiResponses({
            @ApiResponse(code=401,message="当前请求没有被授权"),
            @ApiResponse(code=404,message="当前请求路径不存在"),
            @ApiResponse(code=200,message="保存用户信息成功")
    })
    public Map<String,Object> save(@RequestBody User user){
        System.out.println("id: "+user.getId());
        System.out.println("id: "+user.getName());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", "查询所有成功!");
        map.put("state", true);
        return map;
    }


}
