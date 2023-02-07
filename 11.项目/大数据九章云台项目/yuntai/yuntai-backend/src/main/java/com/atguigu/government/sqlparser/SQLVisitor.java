package com.atguigu.government.sqlparser;

// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SQLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SQLVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link SQLParser#statement}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStatement(SQLParser.StatementContext ctx);
    /**
     * Visit a parse tree produced by {@link SQLParser#insertStatement}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInsertStatement(SQLParser.InsertStatementContext ctx);
    /**
     * Visit a parse tree produced by {@link SQLParser#selectStatement}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSelectStatement(SQLParser.SelectStatementContext ctx);
    /**
     * Visit a parse tree produced by {@link SQLParser#fromClause}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFromClause(SQLParser.FromClauseContext ctx);
    /**
     * Visit a parse tree produced by {@link SQLParser#joinClause}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitJoinClause(SQLParser.JoinClauseContext ctx);
    /**
     * Visit a parse tree produced by {@link SQLParser#onClause}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOnClause(SQLParser.OnClauseContext ctx);
}
