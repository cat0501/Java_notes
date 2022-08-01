package com.itheima.shiro.faced;

import com.itheima.shiro.request.CompanyPagReq;
import com.itheima.shiro.request.CompanyReq;
import com.itheima.shiro.response.CompanyRes;
import com.itheima.shiro.response.PageResponse;
import com.itheima.shiro.response.SingleResponse;

/**
 * @Description：公司接口
 */
public interface CompanyFace {

    /**
     * 分页查询公司
     * @param company
     * @return
     */
    PageResponse<CompanyRes> findCompanyList(CompanyPagReq company);

    /**
     * 根据id查询公司
     * @param company
     * @return
     */
    SingleResponse<CompanyRes> findOne(CompanyReq company);


    /**
     * 新增或修改公司
     * @param company
     * @return
     */
    SingleResponse<Boolean> saveOrUpdateCompany(CompanyReq company);
}
