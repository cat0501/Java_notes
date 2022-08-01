package com.itheima.shiro.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description：springMVC配置,spring5之后不建议使用WebMvcConfigurerAdapter
 * @EnableWebMvc 自动开启springMVC
 * @ComponentScan 自动扫描web层装配bean
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.itheima.shiro.web")
public class SpringMvcConfig implements WebMvcConfigurer  {


}
