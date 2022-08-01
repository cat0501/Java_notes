package com.itheima.framework.mybatis.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.internal.DefaultCommentGenerator;

/**
 * @author lim
 * @date 2017-09-26
 **/
public class CommentGeneratorExt extends DefaultCommentGenerator {

    private boolean suppressAllComments;

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        if (introspectedColumn.getRemarks() != null && !"".equals(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/** ");
            field.addJavaDocLine("* " + introspectedColumn.getRemarks());
            field.addJavaDocLine("*/");
        }
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        //FIXME 后续修订
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        //FIXME 后续修订
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        //FIXME 后续修订
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        //FIXME 后续修订
    }
}
