package com.atguigu.government.service;

import com.atguigu.government.bean.SourceTarget;
import com.atguigu.government.sqlparser.SQLBaseVisitor;
import com.atguigu.government.sqlparser.SQLLexer;
import com.atguigu.government.sqlparser.SQLParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;
import java.util.List;

public class TableLineage extends SQLBaseVisitor<Object> {
    private final List<String> inputTables = new ArrayList<>();
    private String outputTable;

    @Override
    public Object visitInsertStatement(SQLParser.InsertStatementContext ctx) {
        outputTable = ctx.T_QIDENTIFIER().getText();
        return super.visitInsertStatement(ctx);
    }

    @Override
    public Object visitFromClause(SQLParser.FromClauseContext ctx) {
        inputTables.add(ctx.T_QIDENTIFIER().getText());
        return super.visitFromClause(ctx);
    }

    @Override
    public Object visitJoinClause(SQLParser.JoinClauseContext ctx) {
        inputTables.add(ctx.T_QIDENTIFIER().getText());
        return super.visitJoinClause(ctx);
    }

    public static List<SourceTarget> lineage(String sql) {
        var stream = CharStreams.fromString(sql);
        var lexer = new SQLLexer(stream);
        var tokens = new CommonTokenStream(lexer);
        var parser = new SQLParser(tokens);
        var ast = parser.statement();
        var tableLineage = new TableLineage();
        tableLineage.visit(ast);

        var edges = new ArrayList<SourceTarget>();
        for (var source : tableLineage.inputTables) {
            edges.add(new SourceTarget(source, tableLineage.outputTable));
        }
        return edges;
    }

    public static void main(String[] args) {
        String sql = "INSERT INTO gmall.tableOne SELECT id FROM gmall.tableTwo";
        lineage(sql);
    }
}
