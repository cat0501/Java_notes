package com.itheima.shiro.core.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Description：自定义路径解析器
 */
@Log4j2
public class CustomPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {

    private CustomDefaultFilterChainManager defaultFilterChainManager;

    public CustomDefaultFilterChainManager getDefaultFilterChainManager() {
        return defaultFilterChainManager;
    }

    public void setDefaultFilterChainManager(CustomDefaultFilterChainManager defaultFilterChainManager) {
        this.defaultFilterChainManager = defaultFilterChainManager;
    }

    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        //不在使用默认的过滤器链管理者，而是使用自定义的过滤器管理者
        FilterChainManager filterChainManager = getDefaultFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        }

        String requestURI = getPathWithinApplication(request);

        //the 'chain names' in this implementation are actually path patterns defined by the user.  We just use them
        //as the chain name for the FilterChainManager's requirements
        for (String pathPattern : filterChainManager.getChainNames()) {

            // If the path does match, then pass on to the subclass implementation for specific checks:
            if (pathMatches(pathPattern, requestURI)) {
                if (log.isTraceEnabled()) {
                    log.trace("Matched path pattern [" + pathPattern + "] for requestURI [" + requestURI + "].  " +
                            "Utilizing corresponding filter chain...");
                }
                return filterChainManager.proxy(originalChain, pathPattern);
            }
        }

        return null;
    }
}
