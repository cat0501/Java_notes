package com.itheima.shiro.mapper;

import com.itheima.shiro.pojo.FilterChain;
import com.itheima.shiro.pojo.FilterChainExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FilterChainMapper {
    long countByExample(FilterChainExample example);

    int deleteByExample(FilterChainExample example);

    int deleteByPrimaryKey(String id);

    int insert(FilterChain record);

    int insertSelective(FilterChain record);

    List<FilterChain> selectByExample(FilterChainExample example);

    FilterChain selectByPrimaryKey(@Param("id") String id, @Param("resultColumn") String resultColumn);

    int updateByExampleSelective(@Param("record") FilterChain record, @Param("example") FilterChainExample example);

    int updateByExample(@Param("record") FilterChain record, @Param("example") FilterChainExample example);

    int updateByPrimaryKeySelective(FilterChain record);

    int updateByPrimaryKey(FilterChain record);

    int batchInsert(List<FilterChain> list);
}