package calculator;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.HashMap;
import java.util.Map;

public class Calculator extends CalculatorBaseVisitor<Integer> {
    Map<String, Integer> memory = new HashMap<>();

    /** ID '=' expr NEWLINE */
    @Override
    public Integer visitAssign(CalculatorParser.AssignContext ctx) {
        var id = ctx.ID().getText(); // id在'='的左侧
        var value = visit(ctx.expr());  // '='右侧是表达式
        memory.put(id, value);          // 将标识符和赋值的映射关系存储在符号表中
        return value;
    }

    /** expr NEWLINE */
    @Override
    public Integer visitPrintExpr(CalculatorParser.PrintExprContext ctx) {
        var value = visit(ctx.expr());
        System.out.println(value);
        return 0;
    }

    /** INT */
    @Override
    public Integer visitInt(CalculatorParser.IntContext ctx) {
        return Integer.parseInt(ctx.INT().getText());
    }

    /** ID */
    @Override
    public Integer visitId(CalculatorParser.IdContext ctx) {
        var id = ctx.ID().getText();
        if (memory.containsKey(id)) return memory.get(id);
        return 0;
    }

    /** expr op=('*'|'/') expr */
    @Override
    public Integer visitMulDiv(CalculatorParser.MulDivContext ctx) {
        var left = visit(ctx.expr(0));
        var right = visit(ctx.expr(1));
        if (ctx.op.getType() == CalculatorParser.MUL) return left * right;
        return left / right;
    }

    /** expr op=('+'|'-') expr */
    @Override
    public Integer visitAddSub(CalculatorParser.AddSubContext ctx) {
        var left = visit(ctx.expr(0));
        var right = visit(ctx.expr(1));
        if (ctx.op.getType() == CalculatorParser.ADD) return left + right;
        return left - right;
    }

    /** '(' expr ')' */
    @Override
    public Integer visitParens(CalculatorParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

    public static void main(String[] args) {
        String s = """
                1+2
                a = 1
                b = 2
                a + b
                """;
        var stream = CharStreams.fromString(s);
        var lexer = new CalculatorLexer(stream);
        var tokens = new CommonTokenStream(lexer);
        var parser = new CalculatorParser(tokens);
        var tree = parser.prog();
        var calculator = new Calculator();
        calculator.visit(tree);
    }
}
