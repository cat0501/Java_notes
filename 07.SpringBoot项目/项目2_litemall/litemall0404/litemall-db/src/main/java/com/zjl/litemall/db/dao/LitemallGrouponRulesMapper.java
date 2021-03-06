package com.zjl.litemall.db.dao;

import com.zjl.litemall.db.domain.LitemallGrouponRules;
import com.zjl.litemall.db.example.LitemallGrouponRulesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LitemallGrouponRulesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    long countByExample(LitemallGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByExample(LitemallGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insert(LitemallGrouponRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insertSelective(LitemallGrouponRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGrouponRules selectOneByExample(LitemallGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGrouponRules selectOneByExampleSelective(@Param("example") LitemallGrouponRulesExample example, @Param("selective") LitemallGrouponRules.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallGrouponRules> selectByExampleSelective(@Param("example") LitemallGrouponRulesExample example, @Param("selective") LitemallGrouponRules.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallGrouponRules> selectByExample(LitemallGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGrouponRules selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallGrouponRules.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGrouponRules selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGrouponRules selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExampleSelective(@Param("record") LitemallGrouponRules record, @Param("example") LitemallGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExample(@Param("record") LitemallGrouponRules record, @Param("example") LitemallGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKeySelective(LitemallGrouponRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKey(LitemallGrouponRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") LitemallGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}