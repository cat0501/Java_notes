package com.itheima.framework.mybatis.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * Created by lim on 2017/11/8.
 */
public class MysqlBatchPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement rootElement = document.getRootElement();
        addBatchInsert(rootElement, introspectedTable);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType fullyQualifiedJavaType = FullyQualifiedJavaType.getNewListInstance();
        fullyQualifiedJavaType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable
            .getBaseRecordType()));
        Method batchInsert = new Method("batchInsert");
        batchInsert.setReturnType(FullyQualifiedJavaType.getIntInstance());
        Parameter iparam0 = new Parameter(fullyQualifiedJavaType, "list");
        batchInsert.addParameter(0, iparam0);
        interfaze.addMethod(batchInsert);
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    private void addBatchInsert(XmlElement rootElement, IntrospectedTable introspectedTable) {
        XmlElement batchInsert = new XmlElement("insert");
        batchInsert.addAttribute(new Attribute("id", "batchInsert"));
        batchInsert.addAttribute(new Attribute("parameterType", "java.util.List"));
        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();
        // 定义sql
        StringBuilder stringBuilder = new StringBuilder("insert into ");
        // 定义value
        StringBuilder valueString = new StringBuilder(50);
        // 添加表名
        stringBuilder.append(introspectedTable.getFullyQualifiedTableNameAtRuntime()).append("\n");
        // 添加插入字段和拼接插入值
        stringBuilder.append("    (");
        for (int i = 0; i < columnList.size(); i++) {
            IntrospectedColumn introspectedColumn = columnList.get(i);
            stringBuilder.append(introspectedColumn.getActualColumnName());
            valueString.append("#{entity.").append(introspectedColumn.getJavaProperty())
                .append("}");
            if (i < (columnList.size() - 1)) {
                stringBuilder.append(", ");
                valueString.append(", ");
                if (i > 0 && i % 3 == 0) {
                    stringBuilder.append("\n").append("    ");
                    valueString.append("\n").append("      ");
                }
            }
        }
        stringBuilder.append(")").append("\n").append("    values").append("\n");
        // 添加values
        stringBuilder.append(
            "    <foreach collection =\"list\" item=\"entity\" index= \"index\" separator =\",\">")
            .append("\n");
        stringBuilder.append("      (").append(valueString).append(")").append("\n");
        stringBuilder.append("    </foreach>");
        batchInsert.addElement(new TextElement(stringBuilder.toString()));
        rootElement.addElement(batchInsert);
    }

}
