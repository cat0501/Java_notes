package com.itheima.shiro.service;

import com.itheima.shiro.faced.CompanyFace;
import com.itheima.shiro.pojo.Company;
import com.itheima.shiro.request.CompanyPagReq;
import com.itheima.shiro.request.CompanyReq;
import com.itheima.shiro.response.CompanyRes;
import com.itheima.shiro.response.PageResponse;
import com.itheima.shiro.response.SingleResponse;
import com.itheima.shiro.utils.BeanConv;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.utils.ResponseBuilder;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.itheima.shiro.enums.FaceError.COMPANY_IS_EMPTY;
import static com.itheima.shiro.enums.FaceError.COMPANY_SAVE_ERROR;

/**
 * @Description：
 */
@Service(version = "1.0.0")
public class CompanyFaceImpl implements CompanyFace {


    @Autowired
    private CompanyService companyService;

    @Override
    public PageResponse<CompanyRes> findCompanyList(CompanyPagReq interviewEvaluateReq) {
        Integer rows =interviewEvaluateReq.getRow();
        Integer page =interviewEvaluateReq.getPage();
        Company interviewEvaluate = BeanConv.toBean(interviewEvaluateReq, Company.class);
        Long count = companyService.countCompanyList(interviewEvaluate);
        List<Company> interviewEvaluateList = companyService.findCompanyList(interviewEvaluate, rows, page);
        List<CompanyRes> CompanyRess = BeanConv.toBeanList(interviewEvaluateList, CompanyRes.class);
        return ResponseBuilder.succTPage(CompanyRess,page,rows,count.intValue());
    }

    @Override
    public SingleResponse<CompanyRes> findOne(CompanyReq interviewEvaluateReq) {
        String id = interviewEvaluateReq.getId();
        if (EmptyUtil.isNullOrEmpty(id)) {
            return ResponseBuilder.failTSingle(COMPANY_IS_EMPTY);
        }
        Company Company = companyService.findOne(id);
        CompanyRes CompanyRes = BeanConv.toBean(Company, CompanyRes.class);
        return ResponseBuilder.succTSingle(CompanyRes);
    }

    @Override
    public SingleResponse<Boolean> saveOrUpdateCompany(CompanyReq interviewEvaluateReq) {
        Company interviewEvaluate = BeanConv.toBean(interviewEvaluateReq, Company.class);
        Boolean flag;
        try {
            flag = companyService.saveOrUpdateCompany(interviewEvaluate);
        }catch (Exception e){
            return ResponseBuilder.failTSingle(COMPANY_SAVE_ERROR);
        }
        return ResponseBuilder.succTSingle(flag);
    }
}
