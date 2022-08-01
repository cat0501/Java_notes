package com.itheima.framework.mybatis.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * @author lim
 * @date 2017-07-19
 **/
public class OraclePaginationPlugin extends PluginAdapter {

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        addPageProperties(topLevelClass);
        return super.modelExampleClassGenerated(topLevelClass,
                introspectedTable);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document,
                                           IntrospectedTable introspectedTable) {
        XmlElement parentElement = document.getRootElement();

        // 产生分页语句前半部分
        XmlElement paginationPrefixElement = new XmlElement("sql");
        paginationPrefixElement.addAttribute(new Attribute("id",
                "OracleDialectPrefix"));
        XmlElement pageStart = new XmlElement("if");
        pageStart.addAttribute(new Attribute("test", "page != null &amp;&amp; row != null"));
        pageStart.addElement(new TextElement(
                "select * from ( select row_.*, rownum rownum_ from ( "));
        paginationPrefixElement.addElement(pageStart);
        parentElement.addElement(paginationPrefixElement);

        // 产生分页语句后半部分
        XmlElement paginationSuffixElement = new XmlElement("sql");
        paginationSuffixElement.addAttribute(new Attribute("id",
                "OracleDialectSuffix"));
        XmlElement pageEnd = new XmlElement("if");
        pageEnd.addAttribute(new Attribute("test", "page != null &amp;&amp; row != null"));
        pageEnd.addElement(new TextElement(
                "<![CDATA[ ) row_ ) where rownum_ > #{startRowNum} and rownum_ <= #{endRowNum} ]]>"));
        paginationSuffixElement.addElement(pageEnd);
        parentElement.addElement(paginationSuffixElement);

        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {

        XmlElement pageStart = new XmlElement("include"); //$NON-NLS-1$
        pageStart.addAttribute(new Attribute("refid", "OracleDialectPrefix"));
        element.getElements().add(0, pageStart);

        XmlElement isNotNullElement = new XmlElement("include"); //$NON-NLS-1$
        isNotNullElement.addAttribute(new Attribute("refid",
                "OracleDialectSuffix"));
        element.getElements().add(isNotNullElement);

        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element,
                introspectedTable);
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
        getStartRowNum.addBodyLine("return (this.page != null && this.row != null) ? ((this.page - 1) * this.row) : null;");
        topLevelClass.addMethod(getStartRowNum);

        Method getEndRowNum = new Method();
        getEndRowNum.setVisibility(JavaVisibility.PUBLIC);
        getEndRowNum.setReturnType(integerWrapper);
        getEndRowNum.setName("getEndRowNum");
        getEndRowNum.addBodyLine("return (this.page != null && this.row != null) ? (this.page * this.row) : null;");
        topLevelClass.addMethod(getEndRowNum);
    }

    /**
     * This plugin is always valid - no properties are required
     */
    public boolean validate(List<String> warnings) {
        return true;
    }
}
