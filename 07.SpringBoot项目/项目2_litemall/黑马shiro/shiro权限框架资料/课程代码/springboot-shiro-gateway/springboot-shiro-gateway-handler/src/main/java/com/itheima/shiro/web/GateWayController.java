package com.itheima.shiro.web;

import com.alibaba.fastjson.JSONObject;
import com.itheima.shiro.base.BaseRequest;
import com.itheima.shiro.cache.CacheWareService;
import com.itheima.shiro.constant.GateWayConstant;
import com.itheima.shiro.pojo.CacheWare;
import com.itheima.shiro.response.MultiResponse;
import com.itheima.shiro.response.PageResponse;
import com.itheima.shiro.response.SingleResponse;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.view.JsonResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

/**
 * @Description：网关统一入口
 */
@Controller
@Log4j2
public class GateWayController {

    @Autowired
    CacheWareService cacheWareService;

    /**
     * @Description 请求入口
     * @param serviceName 服务名称
     * @param methodName 目标方法
     * @param baseRequest 请求对象
     * @return
     */
    @RequestMapping(value = "{serviceName}/{methodName}",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult postGateWay(@PathVariable("serviceName") String serviceName,
                                  @PathVariable("methodName") String methodName,
                                  @RequestBody BaseRequest baseRequest) throws Exception{
        JsonResult jsonResult = null;
        if (EmptyUtil.isNullOrEmpty(serviceName)||EmptyUtil.isNullOrEmpty(methodName)){
            jsonResult = jsonResult.builder()
                    .result(GateWayConstant.FAIL)
                    .msg("参数缺失")
                    .code(GateWayConstant.PARAMETERS_MISSING)
                    .build();
                    return  jsonResult;
        }
        //传入参数处理
        JSONObject jsonObject = null;
        Object datas = baseRequest.getDatas();
        if (!EmptyUtil.isNullOrEmpty(datas)){
            jsonObject = JSONObject.parseObject(jsonObject.toJSONString(datas));
        }
        //获得缓存仓库可执行对象
        CacheWare cacheWare = cacheWareService.queryCacheWare(serviceName, methodName);
        //执行远程服务
        Object proxy = cacheWare.getProxy();
        Method method = cacheWare.getMethod();
        Class<?> methodParamsClass = cacheWare.getMethodParamsClass();
        Object result = null;
        if (EmptyUtil.isNullOrEmpty(methodParamsClass)){
            result = method.invoke(proxy);
        }else {
            Object arg = JSONObject.toJavaObject(jsonObject, methodParamsClass);
            result = method.invoke(proxy,arg);
        }
        //处理放回结果
        return  convResult(result);
    }

    /**
     * @Description 处理请求结果
     */
    private JsonResult convResult(Object result) {
        JsonResult jsonResult = JsonResult.builder()
                .result(GateWayConstant.SUCCEED)
                .msg("相应正常")
                .code(GateWayConstant.SUCCEED_CODE)
                .build();
        if (EmptyUtil.isNullOrEmpty(result)) {
            jsonResult = JsonResult.builder()
                    .result(GateWayConstant.FAIL)
                    .msg("返回结果为空")
                    .code(GateWayConstant.RESULT_ISNULLOREMPTY)
                    .build();
            return jsonResult;
        }
        if (result instanceof SingleResponse) {
            BeanUtils.copyProperties(result, jsonResult);
            @SuppressWarnings("rawtypes")
            SingleResponse singleResponse = (SingleResponse) result;
            jsonResult.setDatas(singleResponse.getValue());
        } else if (result instanceof MultiResponse) {
            BeanUtils.copyProperties(result, jsonResult);
            @SuppressWarnings("rawtypes")
            MultiResponse multiResponse = (MultiResponse) result;
            jsonResult.setDatas(multiResponse.getValues());
        } else if (result instanceof PageResponse) {
            BeanUtils.copyProperties(result, jsonResult);
            PageResponse pageResponse = (PageResponse)result;
            jsonResult.setDatas( pageResponse.getValues());
        } else {
            jsonResult = JsonResult.builder()
                    .result(GateWayConstant.FAIL)
                    .msg("返回结果格式不正确")
                    .code(GateWayConstant.RESULT_MISSING)
                    .build();
            return jsonResult;
        }
        return jsonResult;
    }
}
