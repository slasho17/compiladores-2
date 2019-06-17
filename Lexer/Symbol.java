/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package Lexer;

public enum Symbol {
    EOF("eof"),
    IDENT("Id"),
    INTEGER("Int"),
    LITERALINT("LiteralInt"),
    BOOLEAN("Boolean"),
    TRUE("true"),
    FALSE("false"),
    FUNCTION("function"),
    STRING("String"),
    LITERALSTRING("LiteralString"),
    ARROW("->"),
    PLUS("+"),
    MINUS("-"),
    MULT("*"),
    DIV("/"),
    LT("<"),
    LE("<="),
    GT(">"),
    GE(">="),
    NEQ("!="),
    EQ("=="),
    ATRIB("="),
    LEFTPAR("("),
    RIGHTPAR(")"),
    OPENBRACE("{"),
    CLOSEBRACE("}"),
    SEMICOLON(";"),
    COLON(":"),
    VAR("var"),
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    COMMA(","),
    OR("or"),
    AND("and"),
    LEFTBRACK("["),
    RIGHTBRACK("]"),
    WRITELN("writeln"),
    WRITE("write"),
    READINT("readInt"),
    READSTRING("readString"),
    RETURN("return");

    Symbol(String name) {
        this.name = name;
    }

    public String toString() { return name; }
    public String name;
}
