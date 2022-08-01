package com.itheima.shiro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @Description：
 */
@Data
@ConfigurationProperties(prefix = "itheima.framework.jwt")
public class JwtProperties implements Serializable {

    /**
     * @Description 签名密码
     */
    private String base64EncodedSecretKey;
}
