package com.atguigu.government.sqlparser;

// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class SQLParser extends Parser {
    static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            T__0=1, T_INSERT=2, T_SELECT=3, T_INTO=4, T_OVERWRITE=5, T_FROM=6, T_TABLE=7,
            T_JOIN=8, T_AND=9, T_ON=10, T_LPAREN=11, T_RPAREN=12, T_DOT=13, T_COMMA=14,
            T_QIDENTIFIER=15, T_IDENTIFIER=16, WS=17;
    public static final int
            RULE_statement = 0, RULE_insertStatement = 1, RULE_selectStatement = 2,
            RULE_fromClause = 3, RULE_joinClause = 4, RULE_onClause = 5;
    private static String[] makeRuleNames() {
        return new String[] {
                "statement", "insertStatement", "selectStatement", "fromClause", "joinClause",
                "onClause"
        };
    }
    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames() {
        return new String[] {
                null, "'='", "'INSERT'", "'SELECT'", "'INTO'", "'OVERWRITE'", "'FROM'",
                "'TABLE'", "'JOIN'", "'AND'", "'ON'", "'('", "')'", "'.'", "','"
        };
    }
    private static final String[] _LITERAL_NAMES = makeLiteralNames();
    private static String[] makeSymbolicNames() {
        return new String[] {
                null, null, "T_INSERT", "T_SELECT", "T_INTO", "T_OVERWRITE", "T_FROM",
                "T_TABLE", "T_JOIN", "T_AND", "T_ON", "T_LPAREN", "T_RPAREN", "T_DOT",
                "T_COMMA", "T_QIDENTIFIER", "T_IDENTIFIER", "WS"
        };
    }
    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() { return "java-escape"; }

    @Override
    public String[] getRuleNames() { return ruleNames; }

    @Override
    public String getSerializedATN() { return _serializedATN; }

    @Override
    public ATN getATN() { return _ATN; }

    public SQLParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
    }

    @SuppressWarnings("CheckReturnValue")
    public static class StatementContext extends ParserRuleContext {
        public InsertStatementContext insertStatement() {
            return getRuleContext(InsertStatementContext.class,0);
        }
        public SelectStatementContext selectStatement() {
            return getRuleContext(SelectStatementContext.class,0);
        }
        public StatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_statement; }
        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitStatement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final StatementContext statement() throws RecognitionException {
        StatementContext _localctx = new StatementContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_statement);
        try {
            setState(14);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case T_INSERT:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(12);
                    insertStatement();
                }
                break;
                case T_SELECT:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(13);
                    selectStatement();
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class InsertStatementContext extends ParserRuleContext {
        public TerminalNode T_INSERT() { return getToken(SQLParser.T_INSERT, 0); }
        public TerminalNode T_QIDENTIFIER() { return getToken(SQLParser.T_QIDENTIFIER, 0); }
        public SelectStatementContext selectStatement() {
            return getRuleContext(SelectStatementContext.class,0);
        }
        public TerminalNode T_INTO() { return getToken(SQLParser.T_INTO, 0); }
        public TerminalNode T_OVERWRITE() { return getToken(SQLParser.T_OVERWRITE, 0); }
        public TerminalNode T_TABLE() { return getToken(SQLParser.T_TABLE, 0); }
        public InsertStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_insertStatement; }
        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitInsertStatement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InsertStatementContext insertStatement() throws RecognitionException {
        InsertStatementContext _localctx = new InsertStatementContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_insertStatement);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(16);
                match(T_INSERT);
                setState(17);
                _la = _input.LA(1);
                if ( !(_la==T_INTO || _la==T_OVERWRITE) ) {
                    _errHandler.recoverInline(this);
                }
                else {
                    if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
                setState(19);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la==T_TABLE) {
                    {
                        setState(18);
                        match(T_TABLE);
                    }
                }

                setState(21);
                match(T_QIDENTIFIER);
                setState(22);
                selectStatement();
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class SelectStatementContext extends ParserRuleContext {
        public TerminalNode T_SELECT() { return getToken(SQLParser.T_SELECT, 0); }
        public List<TerminalNode> T_QIDENTIFIER() { return getTokens(SQLParser.T_QIDENTIFIER); }
        public TerminalNode T_QIDENTIFIER(int i) {
            return getToken(SQLParser.T_QIDENTIFIER, i);
        }
        public TerminalNode T_FROM() { return getToken(SQLParser.T_FROM, 0); }
        public FromClauseContext fromClause() {
            return getRuleContext(FromClauseContext.class,0);
        }
        public List<TerminalNode> T_COMMA() { return getTokens(SQLParser.T_COMMA); }
        public TerminalNode T_COMMA(int i) {
            return getToken(SQLParser.T_COMMA, i);
        }
        public SelectStatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_selectStatement; }
        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitSelectStatement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SelectStatementContext selectStatement() throws RecognitionException {
        SelectStatementContext _localctx = new SelectStatementContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_selectStatement);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(24);
                match(T_SELECT);
                setState(25);
                match(T_QIDENTIFIER);
                setState(30);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la==T_COMMA) {
                    {
                        {
                            setState(26);
                            match(T_COMMA);
                            setState(27);
                            match(T_QIDENTIFIER);
                        }
                    }
                    setState(32);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(33);
                match(T_FROM);
                setState(34);
                fromClause();
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class FromClauseContext extends ParserRuleContext {
        public TerminalNode T_LPAREN() { return getToken(SQLParser.T_LPAREN, 0); }
        public SelectStatementContext selectStatement() {
            return getRuleContext(SelectStatementContext.class,0);
        }
        public TerminalNode T_RPAREN() { return getToken(SQLParser.T_RPAREN, 0); }
        public TerminalNode T_QIDENTIFIER() { return getToken(SQLParser.T_QIDENTIFIER, 0); }
        public TerminalNode T_ON() { return getToken(SQLParser.T_ON, 0); }
        public OnClauseContext onClause() {
            return getRuleContext(OnClauseContext.class,0);
        }
        public List<JoinClauseContext> joinClause() {
            return getRuleContexts(JoinClauseContext.class);
        }
        public JoinClauseContext joinClause(int i) {
            return getRuleContext(JoinClauseContext.class,i);
        }
        public FromClauseContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_fromClause; }
        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitFromClause(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FromClauseContext fromClause() throws RecognitionException {
        FromClauseContext _localctx = new FromClauseContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_fromClause);
        int _la;
        try {
            setState(49);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case T_LPAREN:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(36);
                    match(T_LPAREN);
                    setState(37);
                    selectStatement();
                    setState(38);
                    match(T_RPAREN);
                }
                break;
                case T_QIDENTIFIER:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(40);
                    match(T_QIDENTIFIER);
                    setState(44);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    while (_la==T_JOIN) {
                        {
                            {
                                setState(41);
                                joinClause();
                            }
                        }
                        setState(46);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                    }
                    setState(47);
                    match(T_ON);
                    setState(48);
                    onClause();
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class JoinClauseContext extends ParserRuleContext {
        public TerminalNode T_JOIN() { return getToken(SQLParser.T_JOIN, 0); }
        public TerminalNode T_QIDENTIFIER() { return getToken(SQLParser.T_QIDENTIFIER, 0); }
        public JoinClauseContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_joinClause; }
        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitJoinClause(this);
            else return visitor.visitChildren(this);
        }
    }

    public final JoinClauseContext joinClause() throws RecognitionException {
        JoinClauseContext _localctx = new JoinClauseContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_joinClause);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(51);
                match(T_JOIN);
                setState(52);
                match(T_QIDENTIFIER);
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class OnClauseContext extends ParserRuleContext {
        public List<TerminalNode> T_QIDENTIFIER() { return getTokens(SQLParser.T_QIDENTIFIER); }
        public TerminalNode T_QIDENTIFIER(int i) {
            return getToken(SQLParser.T_QIDENTIFIER, i);
        }
        public List<TerminalNode> T_AND() { return getTokens(SQLParser.T_AND); }
        public TerminalNode T_AND(int i) {
            return getToken(SQLParser.T_AND, i);
        }
        public OnClauseContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_onClause; }
        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitOnClause(this);
            else return visitor.visitChildren(this);
        }
    }

    public final OnClauseContext onClause() throws RecognitionException {
        OnClauseContext _localctx = new OnClauseContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_onClause);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(54);
                match(T_QIDENTIFIER);
                setState(55);
                match(T__0);
                setState(56);
                match(T_QIDENTIFIER);
                setState(63);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la==T_AND) {
                    {
                        {
                            setState(57);
                            match(T_AND);
                            setState(58);
                            match(T_QIDENTIFIER);
                            setState(59);
                            match(T__0);
                            setState(60);
                            match(T_QIDENTIFIER);
                        }
                    }
                    setState(65);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        }
        catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally {
            exitRule();
        }
        return _localctx;
    }

    public static final String _serializedATN =
            "\u0004\u0001\u0011C\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
                    "\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
                    "\u0005\u0007\u0005\u0001\u0000\u0001\u0000\u0003\u0000\u000f\b\u0000\u0001"+
                    "\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u0014\b\u0001\u0001\u0001\u0001"+
                    "\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005"+
                    "\u0002\u001d\b\u0002\n\u0002\f\u0002 \t\u0002\u0001\u0002\u0001\u0002"+
                    "\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
                    "\u0001\u0003\u0005\u0003+\b\u0003\n\u0003\f\u0003.\t\u0003\u0001\u0003"+
                    "\u0001\u0003\u0003\u00032\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004"+
                    "\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
                    "\u0001\u0005\u0005\u0005>\b\u0005\n\u0005\f\u0005A\t\u0005\u0001\u0005"+
                    "\u0000\u0000\u0006\u0000\u0002\u0004\u0006\b\n\u0000\u0001\u0001\u0000"+
                    "\u0004\u0005B\u0000\u000e\u0001\u0000\u0000\u0000\u0002\u0010\u0001\u0000"+
                    "\u0000\u0000\u0004\u0018\u0001\u0000\u0000\u0000\u00061\u0001\u0000\u0000"+
                    "\u0000\b3\u0001\u0000\u0000\u0000\n6\u0001\u0000\u0000\u0000\f\u000f\u0003"+
                    "\u0002\u0001\u0000\r\u000f\u0003\u0004\u0002\u0000\u000e\f\u0001\u0000"+
                    "\u0000\u0000\u000e\r\u0001\u0000\u0000\u0000\u000f\u0001\u0001\u0000\u0000"+
                    "\u0000\u0010\u0011\u0005\u0002\u0000\u0000\u0011\u0013\u0007\u0000\u0000"+
                    "\u0000\u0012\u0014\u0005\u0007\u0000\u0000\u0013\u0012\u0001\u0000\u0000"+
                    "\u0000\u0013\u0014\u0001\u0000\u0000\u0000\u0014\u0015\u0001\u0000\u0000"+
                    "\u0000\u0015\u0016\u0005\u000f\u0000\u0000\u0016\u0017\u0003\u0004\u0002"+
                    "\u0000\u0017\u0003\u0001\u0000\u0000\u0000\u0018\u0019\u0005\u0003\u0000"+
                    "\u0000\u0019\u001e\u0005\u000f\u0000\u0000\u001a\u001b\u0005\u000e\u0000"+
                    "\u0000\u001b\u001d\u0005\u000f\u0000\u0000\u001c\u001a\u0001\u0000\u0000"+
                    "\u0000\u001d \u0001\u0000\u0000\u0000\u001e\u001c\u0001\u0000\u0000\u0000"+
                    "\u001e\u001f\u0001\u0000\u0000\u0000\u001f!\u0001\u0000\u0000\u0000 \u001e"+
                    "\u0001\u0000\u0000\u0000!\"\u0005\u0006\u0000\u0000\"#\u0003\u0006\u0003"+
                    "\u0000#\u0005\u0001\u0000\u0000\u0000$%\u0005\u000b\u0000\u0000%&\u0003"+
                    "\u0004\u0002\u0000&\'\u0005\f\u0000\u0000\'2\u0001\u0000\u0000\u0000("+
                    ",\u0005\u000f\u0000\u0000)+\u0003\b\u0004\u0000*)\u0001\u0000\u0000\u0000"+
                    "+.\u0001\u0000\u0000\u0000,*\u0001\u0000\u0000\u0000,-\u0001\u0000\u0000"+
                    "\u0000-/\u0001\u0000\u0000\u0000.,\u0001\u0000\u0000\u0000/0\u0005\n\u0000"+
                    "\u000002\u0003\n\u0005\u00001$\u0001\u0000\u0000\u00001(\u0001\u0000\u0000"+
                    "\u00002\u0007\u0001\u0000\u0000\u000034\u0005\b\u0000\u000045\u0005\u000f"+
                    "\u0000\u00005\t\u0001\u0000\u0000\u000067\u0005\u000f\u0000\u000078\u0005"+
                    "\u0001\u0000\u00008?\u0005\u000f\u0000\u00009:\u0005\t\u0000\u0000:;\u0005"+
                    "\u000f\u0000\u0000;<\u0005\u0001\u0000\u0000<>\u0005\u000f\u0000\u0000"+
                    "=9\u0001\u0000\u0000\u0000>A\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000"+
                    "\u0000?@\u0001\u0000\u0000\u0000@\u000b\u0001\u0000\u0000\u0000A?\u0001"+
                    "\u0000\u0000\u0000\u0006\u000e\u0013\u001e,1?";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
