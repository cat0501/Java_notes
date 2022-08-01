package com.itheima.shiro.service.impl;

import com.itheima.shiro.constant.SuperConstant;
import com.itheima.shiro.mapper.FilterChainMapper;
import com.itheima.shiro.pojo.FilterChain;
import com.itheima.shiro.pojo.FilterChainExample;
import com.itheima.shiro.service.FilterChainService;
import com.itheima.shiro.utils.BeanConv;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.utils.ExceptionsUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Description：
 */
@Service
@Log4j2
public class FilterChainServiceImpl implements FilterChainService {

    @Autowired
    private FilterChainMapper filterChainMapper;

    @Override
    public List<FilterChain> findFilterChainList() {
        FilterChainExample filterChainExample = new FilterChainExample();
        FilterChainExample.Criteria criteria = filterChainExample.createCriteria();
        criteria.andEnableFlagEqualTo(SuperConstant.YES);
        filterChainExample.setOrderByClause(" SORT_NO ASC");
        return filterChainMapper.selectByExample(filterChainExample);
    }

    @Override
    public List<FilterChain> findFilterChainList(FilterChain filterChain, Integer rows, Integer page) {
        FilterChainExample filterChainExample = this.filterChainListExample(filterChain);
        filterChainExample.setPage(page);
        filterChainExample.setRow(rows);
        filterChainExample.setOrderByClause(" SORT_NO ASC");
        return filterChainMapper.selectByExample(filterChainExample);
    }

    @Override
    public long countFilterChainList(FilterChain filterChain) {
        FilterChainExample filterChainExample = this.filterChainListExample(filterChain);
        return filterChainMapper.countByExample(filterChainExample);
    }

    /**
     *
     * <b>方法名：</b>：filterChainListExample<br>
     * <b>功能说明：</b>：分页多条件组合<br>
     * @author <font color='blue'>束文奇</font>
     * @date  2017年11月8日 下午4:24:32
     * @param filterChain
     * @return
     */
    private FilterChainExample filterChainListExample(FilterChain filterChain) {
        FilterChainExample filterChainExample = new FilterChainExample();
        FilterChainExample.Criteria criteria = filterChainExample.createCriteria();
        if (!EmptyUtil.isNullOrEmpty(filterChain)&&!EmptyUtil.isNullOrEmpty(filterChain.getUrlName())){
            criteria.andUrlNameLike("%"+filterChain.getUrlName());
        }
        if (!EmptyUtil.isNullOrEmpty(filterChain)&&!EmptyUtil.isNullOrEmpty(filterChain.getEnableFlag())){
            criteria.andEnableFlagEqualTo(filterChain.getEnableFlag());
        }
        return filterChainExample;
    }

    @Override
    public FilterChain getFilterChainById(String id) {
        return  BeanConv.toBean(filterChainMapper.selectByPrimaryKey(id, null), FilterChain.class);
    }

    @Override
    @Transactional
    public boolean saveOrUpdateFilterChain(FilterChain filterChain) throws IllegalAccessException, InvocationTargetException {
        Boolean flag = true;
        try {
            if (EmptyUtil.isNullOrEmpty(filterChain.getId())) {
                filterChainMapper.insert(filterChain);
            }else {
                filterChainMapper.updateByPrimaryKey(filterChain);
            }
        } catch (Exception e) {
            log.error("保存过滤器链出错{}", ExceptionsUtil.getStackTraceAsString(e));
            flag = false;
        }
        return flag;
    }

    @Override
    @Transactional
    public Boolean updateByIds(List<String> list, String enableFlag) {
        FilterChainExample filterChainExample = new FilterChainExample();
        filterChainExample.createCriteria().andIdIn(list);
        FilterChain filterChain = new FilterChain();
        filterChain.setEnableFlag(enableFlag);
        int row = filterChainMapper.updateByExampleSelective(filterChain, filterChainExample);
        if (row>0) {
            return true;
        }
        return false;
    }
}
