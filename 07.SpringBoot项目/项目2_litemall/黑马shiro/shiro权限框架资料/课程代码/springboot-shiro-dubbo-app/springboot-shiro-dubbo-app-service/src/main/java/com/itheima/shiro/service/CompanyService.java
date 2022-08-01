package com.itheima.shiro.service;

import com.itheima.shiro.pojo.Company;

import java.util.List;

/**
 * @Description：公司接口
 */
public interface CompanyService {

    /**
     * @Description 分页查询list
     */
    List<Company> findCompanyList(Company company, Integer rows, Integer page);


    /**
     * @Description 分页统计
     */
    long countCompanyList(Company company);

    /**
     * @Description 按照Id查询公司
     */
    Company findOne(String id);

    /**
     * @Description 保存活修改
     */
    Boolean saveOrUpdateCompany(Company company);


  

  








}
