package com.zjl.litemall.db.dao;

import com.zjl.litemall.db.domain.LitemallNoticeAdmin;
import com.zjl.litemall.db.example.LitemallNoticeAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LitemallNoticeAdminMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    long countByExample(LitemallNoticeAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByExample(LitemallNoticeAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insert(LitemallNoticeAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insertSelective(LitemallNoticeAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallNoticeAdmin selectOneByExample(LitemallNoticeAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallNoticeAdmin selectOneByExampleSelective(@Param("example") LitemallNoticeAdminExample example, @Param("selective") LitemallNoticeAdmin.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallNoticeAdmin> selectByExampleSelective(@Param("example") LitemallNoticeAdminExample example, @Param("selective") LitemallNoticeAdmin.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallNoticeAdmin> selectByExample(LitemallNoticeAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallNoticeAdmin selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallNoticeAdmin.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallNoticeAdmin selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallNoticeAdmin selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExampleSelective(@Param("record") LitemallNoticeAdmin record, @Param("example") LitemallNoticeAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExample(@Param("record") LitemallNoticeAdmin record, @Param("example") LitemallNoticeAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKeySelective(LitemallNoticeAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKey(LitemallNoticeAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") LitemallNoticeAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_notice_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}