package com.itheima.framework.mybatis.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;
import java.util.Optional;

/**
 * Created by lim on 2017/11/14.
 */
public class CriteriaLikePlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        Optional<InnerClass> innerCls = topLevelClass.getInnerClasses().stream()
            .filter(innerClass -> "GeneratedCriteria".equals(innerClass.getType().getShortName()))
            .findFirst();
        innerCls
            .get()
            .getMethods()
            .forEach(
                method -> {
                    String name = method.getName();
                    if (name.endsWith("Like") && name.startsWith("and")) {
                        String oldBodyLine = method.getBodyLines().remove(0);
                        StringBuilder newBodyLine = new StringBuilder();
                        int index = oldBodyLine.lastIndexOf(",");
                        newBodyLine.append(oldBodyLine.substring(0, index)).append(" + \"%\"")
                            .append(oldBodyLine.substring(index));
                        method.addBodyLine(0, newBodyLine.toString());
                    }
                });
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }
}
