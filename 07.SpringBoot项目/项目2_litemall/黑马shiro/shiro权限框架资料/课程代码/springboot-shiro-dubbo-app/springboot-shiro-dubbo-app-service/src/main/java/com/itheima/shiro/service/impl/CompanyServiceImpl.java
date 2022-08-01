package com.itheima.shiro.service.impl;

import com.itheima.shiro.mapper.CompanyMapper;
import com.itheima.shiro.pojo.Company;
import com.itheima.shiro.pojo.CompanyExample;
import com.itheima.shiro.service.CompanyService;
import com.itheima.shiro.utils.EmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description：
 */
@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
    
    @Autowired
    CompanyMapper companyMapper;

    /**
     * 分页查询公司信息
     * @param company
     * @param rows
     * @param page
     * @return
     */
    @Override
    public List<Company> findCompanyList(Company company, Integer rows, Integer page) {
        CompanyExample companyExample = this.companyListExample(company);
        companyExample.setOrderByClause("company_no asc ");
        companyExample.setPage(page);
        companyExample.setRow(rows);
        return companyMapper.selectByExample(companyExample);
    }

    /**
     * 根据条件查询公司数量
     * @param company
     * @return
     */
    @Override
    public long countCompanyList(Company company) {
        CompanyExample companyExample = this.companyListExample(company);
        return companyMapper.countByExample(companyExample);
    }

    private CompanyExample companyListExample(Company company) {
        CompanyExample companyExample = new CompanyExample();
        CompanyExample.Criteria criteria = companyExample.createCriteria();
        if (!EmptyUtil.isNullOrEmpty(company.getCompanyName())) {
            criteria.andCompanyNameLike(company.getCompanyName());
        }
        if (!EmptyUtil.isNullOrEmpty(company.getCompanyNo())) {
            criteria.andCompanyNameEqualTo(company.getCompanyNo());
        }
        return companyExample;
    }

    /**
     * 根据ID查询公司信息
     * @param id
     * @return
     */
    @Override
    public Company findOne(String id) {
        return companyMapper.selectByPrimaryKey(id, null);
    }

    /**
     * 新增或修改公司信息
     * @param company
     * @return
     */
    @Override
    public Boolean saveOrUpdateCompany(Company company) {
        Integer flag =0;
        if (EmptyUtil.isNullOrEmpty(company.getId())){
            flag = companyMapper.insert(company);
        }else {
            flag = companyMapper.updateByPrimaryKeySelective(company);
        }
        if (flag>0){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
