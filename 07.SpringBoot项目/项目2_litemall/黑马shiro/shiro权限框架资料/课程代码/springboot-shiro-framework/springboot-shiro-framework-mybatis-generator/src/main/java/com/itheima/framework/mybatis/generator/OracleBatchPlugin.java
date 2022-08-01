package com.itheima.framework.mybatis.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.util.List;

/**
 * @author lim
 * @date 2017-07-24
 **/
public class OracleBatchPlugin extends PluginAdapter {

    private Log log = LogFactory.getLog(this.getClass());

    private int batchInsertType;

    public boolean validate(List<String> warnings) {
        batchInsertType = Integer.parseInt(properties.getProperty("batchInsertType", "0"));
        log.warn("batchInsertType：" + batchInsertType);
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement rootElement = document.getRootElement();
        switch (batchInsertType) {
            case 1:
                addBatchInsert1(rootElement, introspectedTable);
                break;
            case 2:
                addBatchInsert2(rootElement, introspectedTable);
                break;
            default:
                addBatchInsert(rootElement, introspectedTable);
                break;
        }
        addBatchUpdate(rootElement, introspectedTable);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType fullyQualifiedJavaType = FullyQualifiedJavaType.getNewListInstance();
        fullyQualifiedJavaType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable
            .getBaseRecordType()));
        Method batchInsert = new Method("batchInsert");
        Parameter iparam0 = new Parameter(fullyQualifiedJavaType, "list");
        batchInsert.addParameter(0, iparam0);
        interfaze.addMethod(batchInsert);
        Method batchUpdate = new Method("batchUpdate");
        Parameter uparam0 = new Parameter(fullyQualifiedJavaType, "list");
        uparam0.addAnnotation("@Param(\"list\")");
        Parameter uparam1 = new Parameter(new FullyQualifiedJavaType(
            introspectedTable.getExampleType()), "example");
        uparam1.addAnnotation("@Param(\"example\")");
        batchUpdate.addParameter(0, uparam0);
        batchUpdate.addParameter(1, uparam1);
        interfaze.addMethod(batchUpdate);
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    private void addBatchInsert(XmlElement rootElement, IntrospectedTable introspectedTable) {
        XmlElement batchInsert = new XmlElement("insert");
        batchInsert.addAttribute(new Attribute("id", "batchInsert"));
        batchInsert.addAttribute(new Attribute("parameterType", "java.util.List"));
        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();
        StringBuilder stringBuilder = new StringBuilder("insert into ");
        StringBuilder stringBuilder1 = new StringBuilder("select ");
        stringBuilder.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        stringBuilder.append("(");
        for (int i = 0; i < columnList.size(); i++) {
            IntrospectedColumn introspectedColumn = columnList.get(i);
            stringBuilder.append(introspectedColumn.getActualColumnName());
            stringBuilder1.append("#{item.").append(introspectedColumn.getJavaProperty())
                .append("}");
            if (i < (columnList.size() - 1)) {
                stringBuilder.append(",");
                stringBuilder1.append(",");
            }
        }
        stringBuilder.append(")");
        batchInsert.addElement(new TextElement(stringBuilder.toString()));
        batchInsert.addElement(new TextElement("select tmp_table.* from ("));
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("separator", "UNION ALL"));
        stringBuilder1.append(" from dual ");
        foreachElement.addElement(new TextElement(stringBuilder1.toString()));
        batchInsert.addElement(foreachElement);
        batchInsert.addElement(new TextElement(") tmp_table"));
        rootElement.addElement(batchInsert);
    }

    private void addBatchInsert1(XmlElement rootElement, IntrospectedTable introspectedTable) {
        XmlElement batchInsert = new XmlElement("insert");
        batchInsert.addAttribute(new Attribute("id", "batchInsert"));
        batchInsert.addAttribute(new Attribute("parameterType", "java.util.List"));
        batchInsert.addElement(new TextElement("insert all "));
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();
        StringBuilder stringBuilder = new StringBuilder("into ");
        stringBuilder.append(introspectedTable.getFullyQualifiedTableNameAtRuntime()).append("(");
        StringBuilder stringBuilder1 = new StringBuilder(" values (");
        for (int i = 0; i < columnList.size(); i++) {
            IntrospectedColumn introspectedColumn = columnList.get(i);
            stringBuilder.append(introspectedColumn.getActualColumnName());
            stringBuilder1.append("#{item.").append(introspectedColumn.getJavaProperty())
                .append("}");
            if (i < (columnList.size() - 1)) {
                stringBuilder.append(",");
                stringBuilder1.append(",");
            }
        }
        stringBuilder.append(")");
        stringBuilder1.append(")");
        stringBuilder.append(stringBuilder1);
        foreachElement.addElement(new TextElement(stringBuilder.toString()));
        batchInsert.addElement(foreachElement);
        batchInsert.addElement(new TextElement("select 1 from dual"));
        rootElement.addElement(batchInsert);
    }

    private void addBatchInsert2(XmlElement rootElement, IntrospectedTable introspectedTable) {
        XmlElement batchInsert = new XmlElement("insert");
        batchInsert.addAttribute(new Attribute("id", "batchInsert"));
        batchInsert.addAttribute(new Attribute("parameterType", "java.util.List"));
        batchInsert.addElement(new TextElement("begin"));
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("separator", ";"));
        StringBuilder stringBuilder = new StringBuilder("insert into ");
        stringBuilder.append(introspectedTable.getFullyQualifiedTableNameAtRuntime()).append("(");
        StringBuilder stringBuilder1 = new StringBuilder(" values (");
        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();
        for (int i = 0; i < columnList.size(); i++) {
            IntrospectedColumn introspectedColumn = columnList.get(i);
            stringBuilder.append(introspectedColumn.getActualColumnName());
            stringBuilder1.append("#{item.").append(introspectedColumn.getJavaProperty())
                .append("}");
            if (i < (columnList.size() - 1)) {
                stringBuilder.append(",");
                stringBuilder1.append(",");
            }
        }
        stringBuilder.append(")");
        stringBuilder1.append(")");
        stringBuilder.append(stringBuilder1);
        foreachElement.addElement(new TextElement(stringBuilder.toString()));
        batchInsert.addElement(foreachElement);
        batchInsert.addElement(new TextElement(";end;"));
        rootElement.addElement(batchInsert);
    }

    private void addBatchUpdate(XmlElement rootElement, IntrospectedTable introspectedTable) {
        XmlElement batchUpdate = new XmlElement("update");
        batchUpdate.addAttribute(new Attribute("id", "batchUpdate"));
        batchUpdate.addElement(new TextElement("begin"));
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("separator", ";"));
        foreachElement.addElement(new TextElement("update "
                                                  + introspectedTable
                                                      .getFullyQualifiedTableNameAtRuntime()));
        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();
        XmlElement setElement = new XmlElement("set");
        for (int i = 0; i < columnList.size(); i++) {
            IntrospectedColumn introspectedColumn = columnList.get(i);
            XmlElement ifElement = new XmlElement("if");
            String ifTestKey = "item." + introspectedColumn.getJavaProperty();
            StringBuilder ifTest = new StringBuilder(ifTestKey);
            ifTest.append("!= null");
            ifElement.addAttribute(new Attribute("test", ifTest.toString()));
            StringBuilder setVal = new StringBuilder(introspectedColumn.getActualColumnName());
            setVal.append("=").append("#{").append(ifTestKey).append("}");
            ifElement.addElement(new TextElement(setVal.toString()));
            setElement.addElement(ifElement);
        }
        foreachElement.addElement(setElement);
        XmlElement whereElement = new XmlElement("if");
        whereElement.addAttribute(new Attribute("test", "_parameter != null"));
        whereElement.addElement(new TextElement(
            "<include refid=\"Update_By_Example_Where_Clause\" />"));
        foreachElement.addElement(whereElement);
        batchUpdate.addElement(foreachElement);
        batchUpdate.addElement(new TextElement(";end;"));
        rootElement.addElement(batchUpdate);
    }
}
