package com.atguigu.government.sqlparser;

// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class SQLLexer extends Lexer {
    static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            T__0=1, T_INSERT=2, T_SELECT=3, T_INTO=4, T_OVERWRITE=5, T_FROM=6, T_TABLE=7,
            T_JOIN=8, T_AND=9, T_ON=10, T_LPAREN=11, T_RPAREN=12, T_DOT=13, T_COMMA=14,
            T_QIDENTIFIER=15, T_IDENTIFIER=16, WS=17;
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };

    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    private static String[] makeRuleNames() {
        return new String[] {
                "T__0", "T_INSERT", "T_SELECT", "T_INTO", "T_OVERWRITE", "T_FROM", "T_TABLE",
                "T_JOIN", "T_AND", "T_ON", "T_LPAREN", "T_RPAREN", "T_DOT", "T_COMMA",
                "T_QIDENTIFIER", "T_IDENTIFIER", "WS"
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


    public SQLLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
    }

    @Override
    public String getGrammarFileName() { return "SQL.g4"; }

    @Override
    public String[] getRuleNames() { return ruleNames; }

    @Override
    public String getSerializedATN() { return _serializedATN; }

    @Override
    public String[] getChannelNames() { return channelNames; }

    @Override
    public String[] getModeNames() { return modeNames; }

    @Override
    public ATN getATN() { return _ATN; }

    public static final String _serializedATN =
            "\u0004\u0000\u0011w\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
                    "\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
                    "\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
                    "\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
                    "\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
                    "\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0001\u0000\u0001\u0000\u0001"+
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
                    "\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
                    "\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
                    "\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
                    "\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
                    "\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
                    "\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
                    "\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t"+
                    "\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001"+
                    "\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000ee\b"+
                    "\u000e\n\u000e\f\u000eh\t\u000e\u0001\u000f\u0001\u000f\u0005\u000fl\b"+
                    "\u000f\n\u000f\f\u000fo\t\u000f\u0001\u0010\u0004\u0010r\b\u0010\u000b"+
                    "\u0010\f\u0010s\u0001\u0010\u0001\u0010\u0000\u0000\u0011\u0001\u0001"+
                    "\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f"+
                    "\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f"+
                    "\u001f\u0010!\u0011\u0001\u0000\u0003\u0002\u0000AZaz\u0003\u0000AZ__"+
                    "az\u0003\u0000\t\n\r\r  y\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003"+
                    "\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007"+
                    "\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001"+
                    "\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000"+
                    "\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000"+
                    "\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000"+
                    "\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000"+
                    "\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000"+
                    "\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0001#\u0001\u0000\u0000\u0000"+
                    "\u0003%\u0001\u0000\u0000\u0000\u0005,\u0001\u0000\u0000\u0000\u00073"+
                    "\u0001\u0000\u0000\u0000\t8\u0001\u0000\u0000\u0000\u000bB\u0001\u0000"+
                    "\u0000\u0000\rG\u0001\u0000\u0000\u0000\u000fM\u0001\u0000\u0000\u0000"+
                    "\u0011R\u0001\u0000\u0000\u0000\u0013V\u0001\u0000\u0000\u0000\u0015Y"+
                    "\u0001\u0000\u0000\u0000\u0017[\u0001\u0000\u0000\u0000\u0019]\u0001\u0000"+
                    "\u0000\u0000\u001b_\u0001\u0000\u0000\u0000\u001da\u0001\u0000\u0000\u0000"+
                    "\u001fi\u0001\u0000\u0000\u0000!q\u0001\u0000\u0000\u0000#$\u0005=\u0000"+
                    "\u0000$\u0002\u0001\u0000\u0000\u0000%&\u0005I\u0000\u0000&\'\u0005N\u0000"+
                    "\u0000\'(\u0005S\u0000\u0000()\u0005E\u0000\u0000)*\u0005R\u0000\u0000"+
                    "*+\u0005T\u0000\u0000+\u0004\u0001\u0000\u0000\u0000,-\u0005S\u0000\u0000"+
                    "-.\u0005E\u0000\u0000./\u0005L\u0000\u0000/0\u0005E\u0000\u000001\u0005"+
                    "C\u0000\u000012\u0005T\u0000\u00002\u0006\u0001\u0000\u0000\u000034\u0005"+
                    "I\u0000\u000045\u0005N\u0000\u000056\u0005T\u0000\u000067\u0005O\u0000"+
                    "\u00007\b\u0001\u0000\u0000\u000089\u0005O\u0000\u00009:\u0005V\u0000"+
                    "\u0000:;\u0005E\u0000\u0000;<\u0005R\u0000\u0000<=\u0005W\u0000\u0000"+
                    "=>\u0005R\u0000\u0000>?\u0005I\u0000\u0000?@\u0005T\u0000\u0000@A\u0005"+
                    "E\u0000\u0000A\n\u0001\u0000\u0000\u0000BC\u0005F\u0000\u0000CD\u0005"+
                    "R\u0000\u0000DE\u0005O\u0000\u0000EF\u0005M\u0000\u0000F\f\u0001\u0000"+
                    "\u0000\u0000GH\u0005T\u0000\u0000HI\u0005A\u0000\u0000IJ\u0005B\u0000"+
                    "\u0000JK\u0005L\u0000\u0000KL\u0005E\u0000\u0000L\u000e\u0001\u0000\u0000"+
                    "\u0000MN\u0005J\u0000\u0000NO\u0005O\u0000\u0000OP\u0005I\u0000\u0000"+
                    "PQ\u0005N\u0000\u0000Q\u0010\u0001\u0000\u0000\u0000RS\u0005A\u0000\u0000"+
                    "ST\u0005N\u0000\u0000TU\u0005D\u0000\u0000U\u0012\u0001\u0000\u0000\u0000"+
                    "VW\u0005O\u0000\u0000WX\u0005N\u0000\u0000X\u0014\u0001\u0000\u0000\u0000"+
                    "YZ\u0005(\u0000\u0000Z\u0016\u0001\u0000\u0000\u0000[\\\u0005)\u0000\u0000"+
                    "\\\u0018\u0001\u0000\u0000\u0000]^\u0005.\u0000\u0000^\u001a\u0001\u0000"+
                    "\u0000\u0000_`\u0005,\u0000\u0000`\u001c\u0001\u0000\u0000\u0000af\u0003"+
                    "\u001f\u000f\u0000bc\u0005.\u0000\u0000ce\u0003\u001f\u000f\u0000db\u0001"+
                    "\u0000\u0000\u0000eh\u0001\u0000\u0000\u0000fd\u0001\u0000\u0000\u0000"+
                    "fg\u0001\u0000\u0000\u0000g\u001e\u0001\u0000\u0000\u0000hf\u0001\u0000"+
                    "\u0000\u0000im\u0007\u0000\u0000\u0000jl\u0007\u0001\u0000\u0000kj\u0001"+
                    "\u0000\u0000\u0000lo\u0001\u0000\u0000\u0000mk\u0001\u0000\u0000\u0000"+
                    "mn\u0001\u0000\u0000\u0000n \u0001\u0000\u0000\u0000om\u0001\u0000\u0000"+
                    "\u0000pr\u0007\u0002\u0000\u0000qp\u0001\u0000\u0000\u0000rs\u0001\u0000"+
                    "\u0000\u0000sq\u0001\u0000\u0000\u0000st\u0001\u0000\u0000\u0000tu\u0001"+
                    "\u0000\u0000\u0000uv\u0006\u0010\u0000\u0000v\"\u0001\u0000\u0000\u0000"+
                    "\u0005\u0000fkms\u0001\u0006\u0000\u0000";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
