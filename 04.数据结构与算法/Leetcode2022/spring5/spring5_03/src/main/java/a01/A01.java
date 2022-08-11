package a01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author cat
 * @description
 * @date 2022/6/3 上午11:59
 */
//@SpringBootApplication
@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
public class A01 {
    private static final Logger log = LoggerFactory.getLogger(A01.class);


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {
        ConfigurableApplicationContext context = SpringApplication.run(A01.class, args);
        System.out.println(context);

        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String, Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);

        map.entrySet().stream().filter(e -> e.getKey().startsWith("component"))
                .forEach(e -> {
                    System.out.println(e.getKey() + "=" + e.getValue());
                });

        System.out.println("-------------------------------------------");

        //System.out.println(context.getMessage("hi", null, Locale.CHINA));
        //System.out.println(context.getMessage("hi", null, Locale.ENGLISH));
        //System.out.println(context.getMessage("hi", null, Locale.JAPANESE));

        Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : resources) {
            System.out.println(resource);
        }

        System.out.println("");
        System.out.println(context.getEnvironment().getProperty("JAVA_8_HOME"));
        System.out.println(context.getEnvironment().getProperty("JAVA_11_HOME"));
        System.out.println(context.getEnvironment().getProperty("spring.messages.basename"));

    }
}
