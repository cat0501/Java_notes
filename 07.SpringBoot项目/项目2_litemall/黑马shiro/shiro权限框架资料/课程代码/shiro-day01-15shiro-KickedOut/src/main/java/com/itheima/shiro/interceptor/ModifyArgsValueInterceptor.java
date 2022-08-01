package com.itheima.shiro.interceptor;

import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.utils.SeqGenerator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * 修改参数值
 **/
@Log4j2
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class ModifyArgsValueInterceptor implements Interceptor {

    private SeqGenerator seqGenerator;

    private int          workId     = 0;

    private String       primaryKey = "id";

    public ModifyArgsValueInterceptor() {

    }

    public ModifyArgsValueInterceptor(int workId, String primaryKey) {
        this(null, workId, primaryKey);
    }

    public ModifyArgsValueInterceptor(SeqGenerator seqGenerator, int workId, String primaryKey) {
        if (!EmptyUtil.isNullOrEmpty(primaryKey)) {
            this.primaryKey = primaryKey;
        }
        if (EmptyUtil.isNullOrEmpty(seqGenerator)) {
            this.workId = workId;
            init();
        } else {
            this.seqGenerator = seqGenerator;
        }
    }

    public ModifyArgsValueInterceptor(SeqGenerator seqGenerator, String primaryKey) {
        this(seqGenerator, 0, primaryKey);
    }

    private void init() {
        if (seqGenerator == null) {
            synchronized (this) {
                if (seqGenerator == null) {
                    seqGenerator = new SeqGenerator(workId);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        if (args == null || args.length != 2) {
            return invocation.proceed();
        } else {
            MappedStatement ms = (MappedStatement) args[0];
            // 操作类型
            SqlCommandType sqlCommandType = ms.getSqlCommandType();
            // 只处理insert操作
            if (!EmptyUtil.isNullOrEmpty(sqlCommandType) && sqlCommandType == SqlCommandType.INSERT) {
                if (args[1] instanceof Map) {
                    // 批量插入
                    List list = (List) ((Map) args[1]).get("list");
                    for (Object obj : list) {
                        setProperty(obj, primaryKey, seqGenerator.nextId());
                    }
                } else {
                    setProperty(args[1], primaryKey, seqGenerator.nextId());
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String workId = properties.getProperty("workId");
        if (!EmptyUtil.isNullOrEmpty(workId)) {
            this.workId = Integer.parseInt(workId);
        }
        String primaryKey = properties.getProperty("primaryKey");
        if (!EmptyUtil.isNullOrEmpty(primaryKey)) {
            this.primaryKey = primaryKey;
        }
        init();
    }

    /**
     * 设置对象属性值
     * @param obj 对象
     * @param property 属性名称
     * @param value 属性值
     */
    private void setProperty(Object obj, String property, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(property);
            if (!EmptyUtil.isNullOrEmpty(field)) {
                field.setAccessible(true);
                Object val = field.get(obj);
                if (EmptyUtil.isNullOrEmpty(val)) {
                    BeanUtils.setProperty(obj, property, value);
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            log.warn(e.toString());
        }
    }
}
