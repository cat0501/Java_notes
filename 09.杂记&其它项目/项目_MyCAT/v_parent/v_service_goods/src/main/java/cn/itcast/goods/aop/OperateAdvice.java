package cn.itcast.goods.aop;

import cn.itcast.feign.client.OperateLogFeign;
import cn.itcast.model.TbOperatelog;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description 解析@OperateLog注解
 * @author Lemonade
 * @param: null
 * @updateTime 2022/4/6 下午12:39
 */

@Component
@Aspect
public class OperateAdvice {

	@Resource
	private OperateLogFeign operateLogFeign;

	// 环绕通知：指定拦截的是哪一个包，通过切入点表达式来指定：cn.itcast.goods.controller包下的所有类的所有方法（任意形参），
	// 并且此类上添加了operateLog注解
	@Around("execution(* cn.itcast.goods.controller.*.*(..)) && @annotation(operateLog)")
	public Object insertLogAround(ProceedingJoinPoint pjp , OperateLog operateLog) throws Throwable{
		System.out.println(" *********************************** 记录日志 [start]  ****************************** ");

		// 组装日志信息
		TbOperatelog op = new TbOperatelog();

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		op.setOperateTime(sdf.format(new Date()));
		op.setOperateUser("10000");

		// 通过ProceedingJoinPoint获取类名、方法、参数
		op.setOperateClass(pjp.getTarget().getClass().getName());
		op.setOperateMethod(pjp.getSignature().getName());

		String paramAndValue = "";
		Object[] args = pjp.getArgs();
		if(args != null){
			for (Object arg : args) {
				if(arg instanceof String || arg instanceof Integer || arg instanceof Long){
					paramAndValue += arg +",";
				}else{
					paramAndValue += JSON.toJSONString(arg)+",";
				}
			}
			op.setParamAndValue(paramAndValue);
		}

		long start_time = System.currentTimeMillis();

		//放行（执行原始方法）
		Object object = pjp.proceed();

		long end_time = System.currentTimeMillis();
		// 原始方法的耗时
		op.setCostTime(end_time - start_time);

		if(object != null){
			op.setReturnClass(object.getClass().getName());
			op.setReturnValue(object.toString());
		}else{
			op.setReturnClass("java.lang.Object");
			op.setParamAndValue("void");
		}

		// 插入日志
		operateLogFeign.add(op);

		System.out.println(" *********************************** 记录日志 [end]  ****************************** ");

		return object;
	}
}