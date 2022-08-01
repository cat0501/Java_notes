package com.itheima.shiro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @Description：springMVC配置,spring5之后不建议使用WebMvcConfigurerAdapter
 * @EnableWebMvc 自动开启springMVC
 * @ComponentScan 自动扫描web层装配bean
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.itheima.shiro.web")
public class SpringMvcConfig  implements WebMvcConfigurer  {

    /**
     * @Description 配置资源解析路径和后缀名
     */
    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views");
        viewResolver.setSuffix(".jsp");
        return  viewResolver;
    }

//    @Override
//    public void configureDefaultServletHandling(
//            DefaultServletHandlerConfigurer configurer) {
//        // 开启默认转发
//        configurer.enable();
//    }

    // 如果不加，则静态资源会被拦截，导致访问不到静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

}
