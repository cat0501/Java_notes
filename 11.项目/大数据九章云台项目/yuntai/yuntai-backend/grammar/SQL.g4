grammar SQL;

statement : insertStatement
          | selectStatement
          ;

insertStatement : T_INSERT (T_INTO | T_OVERWRITE) (T_TABLE)? T_QIDENTIFIER selectStatement
                ;

selectStatement : T_SELECT T_QIDENTIFIER (',' T_QIDENTIFIER)* T_FROM fromClause
                ;

fromClause : '(' selectStatement ')'
           | T_QIDENTIFIER (joinClause)* T_ON onClause
           ;

joinClause : T_JOIN T_QIDENTIFIER ;

onClause : T_QIDENTIFIER '=' T_QIDENTIFIER (T_AND T_QIDENTIFIER '=' T_QIDENTIFIER)* ;

T_INSERT      : 'INSERT' ;
T_SELECT      : 'SELECT' ;
T_INTO        : 'INTO' ;
T_OVERWRITE   : 'OVERWRITE' ;
T_FROM        : 'FROM' ;
T_TABLE       : 'TABLE' ;
T_JOIN        : 'JOIN' ;
T_AND         : 'AND' ;
T_ON          : 'ON' ;
T_LPAREN      : '(' ;
T_RPAREN      : ')' ;
T_DOT         : '.' ;
T_COMMA       : ',' ;
T_QIDENTIFIER : T_IDENTIFIER ('.' T_IDENTIFIER)* ;
T_IDENTIFIER  : [a-zA-Z] ([a-zA-Z] | '_')* ;
WS            : [ \n\t\r]+ -> skip;