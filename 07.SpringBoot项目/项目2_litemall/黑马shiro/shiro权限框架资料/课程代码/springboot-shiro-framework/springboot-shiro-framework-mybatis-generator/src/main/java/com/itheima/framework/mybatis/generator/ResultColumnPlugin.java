package com.itheima.framework.mybatis.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * @author lim
 * @date 2017-07-24
 **/
public class ResultColumnPlugin extends PluginAdapter {

    private static final FullyQualifiedJavaType stringType = FullyQualifiedJavaType
            .getStringInstance();

    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        Field resultColumn = new Field();
        resultColumn.setName("resultColumn");
        resultColumn.setVisibility(JavaVisibility.PRIVATE);
        resultColumn.setType(stringType);
        topLevelClass.addField(resultColumn);

        Method getResultColumn = new Method();
        getResultColumn.setVisibility(JavaVisibility.PUBLIC);
        getResultColumn.setReturnType(stringType);
        getResultColumn.setName("getResultColumn");
        getResultColumn.addBodyLine("return this.resultColumn;");
        topLevelClass.addMethod(getResultColumn);

        Method setResultColumn = new Method();
        setResultColumn.setVisibility(JavaVisibility.PUBLIC);
        setResultColumn.setName("setResultColumn");
        setResultColumn.addParameter(new Parameter(stringType, "resultColumn"));
        setResultColumn.addBodyLine("this.resultColumn = resultColumn;");
        topLevelClass.addMethod(setResultColumn);
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        modifyResultColumns(element);
        return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element,
                                                            IntrospectedTable introspectedTable) {
        element.getAttributes().remove(2);
        modifyResultColumns(element);
        return super.sqlMapSelectByPrimaryKeyElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method,
                                                           Interface interfaze,
                                                           IntrospectedTable introspectedTable) {
        Parameter resultColumn = new Parameter(stringType, "resultColumn");
        resultColumn.addAnnotation("@Param(\"resultColumn\")");
        method.addParameter(1, resultColumn);
        method.getParameters().get(0).addAnnotation("@Param(\"id\")");
        return super.clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    private void modifyResultColumns(XmlElement element) {
        List<Element> elementList = element.getElements();
        int index = 0;
        for (int i = 0; i < elementList.size(); i++) {
            Element ele = elementList.get(i);
            if ("<include refid=\"Base_Column_List\" />".equals(ele.getFormattedContent(0))) {
                index = i;
                break;
            }
        }
        element.getElements().remove(index);
        XmlElement resultColumns = new XmlElement("choose");
        XmlElement eWhen = new XmlElement("when");
        eWhen.addAttribute(new Attribute("test", "resultColumn != null"));
        eWhen.addElement(new TextElement("${resultColumn}"));
        XmlElement eOtherwise = new XmlElement("otherwise");
        XmlElement eInclude = new XmlElement("include");
        eInclude.addAttribute(new Attribute("refid", "Base_Column_List"));
        eOtherwise.addElement(eInclude);
        resultColumns.addElement(eWhen);
        resultColumns.addElement(eOtherwise);
        element.addElement(index, resultColumns);
    }

}