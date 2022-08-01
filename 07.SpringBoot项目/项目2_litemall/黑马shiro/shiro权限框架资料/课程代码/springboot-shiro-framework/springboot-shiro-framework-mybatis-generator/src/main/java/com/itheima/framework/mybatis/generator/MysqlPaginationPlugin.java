package com.itheima.framework.mybatis.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * Created by lim on 2017/11/6.
 */
public class MysqlPaginationPlugin extends PluginAdapter {

    /**
     * 修改examplte类
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        addPageProperties(topLevelClass);
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        addPageElement(element);
        return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
                                                                  IntrospectedTable introspectedTable) {
        addPageElement(element);
        return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
    }

    public boolean validate(List<String> list) {
        return true;
    }

    private void addPageProperties(TopLevelClass topLevelClass) {
        PrimitiveTypeWrapper integerWrapper = FullyQualifiedJavaType.getIntInstance()
            .getPrimitiveTypeWrapper();

        Field page = new Field();
        page.setName("page");
        page.setVisibility(JavaVisibility.PRIVATE);
        page.setType(integerWrapper);
        topLevelClass.addField(page);

        Method setPage = new Method();
        setPage.setVisibility(JavaVisibility.PUBLIC);
        setPage.setName("setPage");
        setPage.addParameter(new Parameter(integerWrapper, "page"));
        setPage.addBodyLine("this.page = page;");
        topLevelClass.addMethod(setPage);

        Method getPage = new Method();
        getPage.setVisibility(JavaVisibility.PUBLIC);
        getPage.setReturnType(integerWrapper);
        getPage.setName("getPage");
        getPage.addBodyLine("return page;");
        topLevelClass.addMethod(getPage);

        Field row = new Field();
        row.setName("row");
        row.setVisibility(JavaVisibility.PRIVATE);
        row.setType(integerWrapper);
        topLevelClass.addField(row);

        Method setRow = new Method();
        setRow.setVisibility(JavaVisibility.PUBLIC);
        setRow.setName("setRow");
        setRow.addParameter(new Parameter(integerWrapper, "row"));
        setRow.addBodyLine("this.row = row;");
        topLevelClass.addMethod(setRow);

        Method getRow = new Method();
        getRow.setVisibility(JavaVisibility.PUBLIC);
        getRow.setReturnType(integerWrapper);
        getRow.setName("getRow");
        getRow.addBodyLine("return row;");
        topLevelClass.addMethod(getRow);

        Method getStartRowNum = new Method();
        getStartRowNum.setVisibility(JavaVisibility.PUBLIC);
        getStartRowNum.setReturnType(integerWrapper);
        getStartRowNum.setName("getStartRowNum");
        getStartRowNum
            .addBodyLine("return (this.page != null && this.row != null) ? ((this.page - 1) * this.row) : null;");
        topLevelClass.addMethod(getStartRowNum);
    }

    private void addPageElement(XmlElement element) {
        XmlElement ifLimitNotNullElement = new XmlElement("if");
        ifLimitNotNullElement.addAttribute(new Attribute("test", "page &gt; 0 and row &gt; 0"));
        ifLimitNotNullElement.addElement(new TextElement("limit ${startRowNum}, ${row}"));
        element.addElement(ifLimitNotNullElement);

        XmlElement ifOffsetNullElement = new XmlElement("if");
        ifOffsetNullElement.addAttribute(new Attribute("test", "page &gt; 0 and row &lt;= 0"));
        ifOffsetNullElement.addElement(new TextElement("limit ${page}"));
        element.addElement(ifOffsetNullElement);
    }
}
