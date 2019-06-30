/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

import AST.*;
import java.util.*;
import Lexer.*;
import java.lang.Character;
import java.io.*;
import auxcomp.*;

public class Compiler {

	private SymbolTable symbolTable;
	private Lexer lexer;
	private CompilerError error;
    private Function currentFunction;

    public Program compile( char []input) {
			// analise semantica
        symbolTable = new SymbolTable();

        error = new CompilerError(lexer);
        lexer = new Lexer(input, error);
        error.setLexer(lexer);
        lexer.nextToken();
        return program();
    }

    private Program program() {
        //System.out.println("program");

        // Program ::= Func {Func}
    	ArrayList<Function> arrayFunction = new ArrayList<Function>();

        while ( lexer.token == Symbol.FUNCTION ) {
            lexer.nextToken();
            arrayFunction.add(func());
        }

        if ( lexer.token != Symbol.EOF )
        	error.signal("EOF expected");

        Program program = new Program(arrayFunction);

        //checagem da main:
        Function mainFunc = (Function) symbolTable.getInGlobal("main");

        if(mainFunc == null) {
           error.signal("Program must have a main function");
        }

        return program;
    }

    private Function func() {
        //System.out.println("func");
    	//Func ::= "function" Id [ "(" ParamList ")" ] ["->" Type ] StatList
    	Boolean isIdent = true;
    	String id = "";
    	Type type = null;
        Function f = null;

    	for (Symbol c : Symbol.values()) {
            if (c.name().equals(lexer.token)) {
                isIdent = false;
            }
        }

    	if(isIdent) {
    		id = lexer.getStringValue();

            Object fu = symbolTable.getInGlobal(id);

            if (fu != null) {
                error.signal("identifier " + id + " already exists");
            }

            f = new Function(id);
            currentFunction = f;

            // coloca o identificador da funcao na hashtable
            symbolTable.putsInGlobal(id, f);

    		lexer.nextToken();
            if(lexer.token == Symbol.LEFTPAR) {
                // analise semantica
                // verifica se funcao main nao tem parametros
                if (id.equals("main")) {
                    error.signal("Main function can't have parameter(s)");
                }

    			lexer.nextToken();
    			f.setParamList(paramList());

    			if(lexer.token == Symbol.RIGHTPAR) {
    				lexer.nextToken();
    			} else {
    				error.signal("right parenthesis missing");
    			}
    		}
    	} else {
            error.signal("Identifier expected");
        }

    	if(lexer.token == Symbol.ARROW) {
            if (id.equals("main")) {
                error.signal("Main function can't return any value");
            }

    		lexer.nextToken();
            type = type();
    	}
		f.setReturnType(type);
        f.setStatList(statList());
				if(type!=null && f.statList.hasReturn == false){
					error.signal("return statement expected");
				}
        symbolTable.removeLocalIdent();

    	return f;
    }

    private ParamList paramList() {
       // System.out.println("paramList");
        // ParamList ::= ParamDec {”, ”ParamDec}

        ParamList paramlist = null;

        if (lexer.token == Symbol.IDENT) {
            paramlist = new ParamList();
            paramDec(paramlist);

            while (lexer.token == Symbol.COMMA) {
                lexer.nextToken();
                paramDec(paramlist);
            }
        } else {
            error.signal("identifier expected");
        }

        return paramlist;
    }

    private void paramDec(ParamList paramList) {
        //System.out.println("paramDec");
        // ParamDec ::= Id ":" Type

        if (lexer.token != Symbol.IDENT) {
            error.signal("Identifier expected");
        }

        String name = lexer.getStringValue();
        lexer.nextToken();

        if (lexer.token != Symbol.COLON) {
            error.signal(": expected");
        } else {
            lexer.nextToken();
        }

        Object p = symbolTable.get(name);
        if (p != null){
            error.signal("parameter " + name + " already exists");
        }
        else{
            Parameter param = new Parameter(name);
            param.setType(type());
            paramList.addElement(param);
            symbolTable.putsInLocal(name, param);
        }
    }

    private Type type() {
        Type result;

        switch(lexer.token) {
            case INTEGER :
                result = new IntegerType();
                break;
            case BOOLEAN :
                result = new BooleanType();
                break;
            case STRING :
                result = new StringType();
                break;
            default:
                error.signal("Type expected");
                result = null;
        }

        lexer.nextToken();
        return result;
    }

    private StatementList statList() {
        //System.out.println("statList");
        // StatList ::= "{" {Stat} "}"
				Boolean flag=false;
        Symbol tkn;
        ArrayList<Statement> v = new ArrayList<Statement>();
				Statement stataux;
        if (lexer.token != Symbol.OPENBRACE) {
            error.signal("{ expected");
        } else {
            lexer.nextToken();
        }
        while ((tkn = lexer.token) != Symbol.CLOSEBRACE && tkn != Symbol.EOF) {
            if(lexer.token == Symbol.SEMICOLON){
                lexer.nextToken();
            }
            if(lexer.token != Symbol.CLOSEBRACE){
								stataux = stat();
								if (stataux instanceof ReturnStatement){
									flag=true;
								}
                v.add(stataux);
            }
        }
        if (tkn != Symbol.CLOSEBRACE) {
            error.signal("} expected");
        } else {
            lexer.nextToken();
        }
				StatementList s = new StatementList(v);
				if (flag) s.setHasReturn();
        return s;
    }

    private Statement stat() {
        //System.out.println("stat");
        // Stat ::= AssignExprStat| ReturnStat | VarDecStat | IfStat | WhileStat

        switch (lexer.token) {
            case IDENT :
                return assignExprStat();
            case TRUE:
                return assignExprStat();
            case FALSE:
                return assignExprStat();
            case LITERALINT:
                return assignExprStat();
            case LITERALSTRING:
                return assignExprStat();
            case RETURN :
                return returnStat();
            case VAR :
                return varDecStat();
            case IF :
                return IfStat();
            case WHILE:
                return whileStat();
            case WRITELN:
                return writeLn();
            case WRITE:
                return write();

            default :
                error.signal("Statement expected");
        }
        return null;
    }

    private AssignExprStatement assignExprStat() {
        //System.out.println("assignExprStat");
        // AssignExprStat ::= Expr [ "=" Expr] ";"
        Expr left = expr();
        Expr right = null;
				if (left.getExprName() == "FuncCall"){
					if(left.getType()!=null)//se a função for tipada retorna true
						error.signal("typed function needs variable to be assigned to");
				}

        if (lexer.token == Symbol.ATRIB) {

            if (left.getExprName() != "ExprIdentifier") {
                error.signal("Variable expected in assignment");
            }

            lexer.nextToken();
            right = expr();

            if (left.getType().getTypeName() != right.getType().getTypeName()) {
                error.signal("Incompatible types in assignment");
            }
        }

        if (lexer.token != Symbol.SEMICOLON) {
            error.signal("; expected");
        }
        lexer.nextToken();


        // #Sera implementado na segunda fase (Analisador Semantico)

        return new AssignExprStatement(left, right);
    }

    private IfStatement IfStat() {
        //System.out.println("IfStat");
        // IfStat ::= "if" Expr StatList
        lexer.nextToken();
        Expr e = expr();

				System.out.println("aqiu caraip" + lexer.token.toString());
        StatementList thenPart = statList();

        StatementList elsePart = null;
        if (lexer.token == Symbol.ELSE) {
            lexer.nextToken();
            elsePart = statList();
        }

        return new IfStatement( e, thenPart, elsePart );
    }

    private VarDecStat varDecStat() {
        //System.out.println("varDecStat");
        // VarDecStat ::= "var" Id ":" Type ";"
        Variable v = null;
        String id = null;
        Type type = null;
        lexer.nextToken();
        if (lexer.token != Symbol.IDENT) {
            error.signal("Identifier expected");
        } else {
            id = lexer.getStringValue();
            lexer.nextToken();
            Object p = symbolTable.get(id);
            if (p != null){
                error.signal("variable " + id + " already exists");
            }
        }

        if (lexer.token != Symbol.COLON) {
            error.signal(": expected");
        } else {
            lexer.nextToken();
        }

        v = new Variable(id);
        type = type();
        v.setType(type);
        symbolTable.putsInLocal(id, v);

        if (lexer.token != Symbol.SEMICOLON) {
            error.signal("; expected");
        }
        lexer.nextToken();

        return new VarDecStat(v);
    }

    private WhileStatement whileStat() {
        //System.out.println("whileStat");
        // WhileStat ::= "while" Expr StatList
        lexer.nextToken();

        Expr e = expr();
        StatementList stmt = statList();

        return new WhileStatement(e, stmt);
    }

    private ReturnStatement returnStat() {
        //System.out.println("returnStat");
        //ReturnStat ::= "return" Expr ";"
        lexer.nextToken();
        Expr e = expr();

				if (currentFunction.getType()!=null){
        	if (e.getType().getTypeName() != currentFunction.getType().getTypeName()) {
            	error.signal("Incompatible return type in function " + currentFunction.getId());
        	}
				}
        if (lexer.token != Symbol.SEMICOLON) {
            error.signal("; expected");
        }
        lexer.nextToken();

        // # Implementar analise semantica (verificar se esta dentro de funcao)
        return new ReturnStatement(e);
    }

    private Expr expr() {
        //System.out.println("expr");
        // Expr ::= ExprAnd {”or”ExprAnd}
        Expr left, right;
        left = exprAnd();

        if (lexer.token == Symbol.OR) {
            lexer.nextToken();
            right = exprAnd();

            // Analise semantica: expressao OR precisa ter 2 expressoes boolean
            if (left.getType().getTypeName() != "Boolean" || right.getType().getTypeName() != "Boolean")
                error.signal("Expression of boolean type expected");

            left = new CompositeExpr(left, Symbol.OR, right);
        }

        return left;
    }

    private Expr exprAnd() {
        //System.out.println("exprAnd");
        // ExprAnd ::= ExprRel {”and”ExprRel}
        Expr left, right;
        left = exprRel();

        if (lexer.token == Symbol.AND) {
            lexer.nextToken();
            right = exprRel();

            // Analise semantica: expressao AND precisa ter 3 expressoes boolean
            if (left.getType().getTypeName() != "Boolean" || right.getType().getTypeName() != "Boolean")
                error.signal("Expression of boolean type expected");

            left = new CompositeExpr(left, Symbol.AND, right);
        }
        return left;
    }

    private Expr exprRel() {
        //System.out.println("exprRel");
        // ExprRel ::= ExprAdd [ RelOp ExprAdd ]
        Expr left, right;
        left = exprAdd();
        Symbol op = lexer.token;

        if (op == Symbol.LT || op == Symbol.LE || op == Symbol.GT || op == Symbol.GE || op == Symbol.NEQ || op == Symbol.EQ) {
            lexer.nextToken();
            right = exprAdd();

            // Analise semantica: As duas expressoes precisam ter o mesmo tipo
            if (left.getType().getTypeName() != right.getType().getTypeName()) {
                error.signal("Incompatible types in expression");
            }

            left = new CompositeExpr(left, op, right);
        }
        return left;
    }

    private Expr exprAdd() {
        //System.out.println("exprAdd");
        // ExprAdd ::= ExprMult {(” + ” | ” − ”)ExprMult}
        Expr left, right;
        left = exprMult();
        Symbol op = lexer.token;

        while (lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS) {
            op = lexer.token;
            lexer.nextToken();
            right = exprMult();

            // Analise semantica: Expressao de soma precisa ter 2 expressoes de tipo inteiro
            if (left.getType().getTypeName() != "Int" || right.getType().getTypeName() != "Int")
                error.signal("Expression of type integer expected");

            left = new CompositeExpr(left, op, right);
        }

        return left;
    }

    private Expr exprMult() {
        //System.out.println("exprMult");
        // ExprMult ::= ExprUnary {(” ∗ ” | ”/”)ExprUnary}
        Expr left, right;
        left = exprUnary();
        Symbol op = lexer.token;

        while (lexer.token == Symbol.MULT || lexer.token == Symbol.DIV) {
            op = lexer.token;
            lexer.nextToken();
            right = exprUnary();

            // Analise semantica: Expressao de multiplicacao precisa ter 2 expressoes de tipo inteiro
            if (left.getType().getTypeName() != "Int" || right.getType().getTypeName() != "Int")

            left = new CompositeExpr(left, op, right);
        }

        return left;
    }

    private Expr exprUnary() {
        //System.out.println("exprUnary");
        // ExprUnary ::= [ ( "+" | "-" ) ] ExprPrimary
        Symbol op = null;
        if (lexer.token == Symbol.PLUS || lexer.token == Symbol.MINUS) {
            op = lexer.token;
            lexer.nextToken();
        }

        Expr e = exprPrimary();

        if (op != null && e.getType().getTypeName() != "Int") {
            error.signal("Unary operator " + op.toString() + " with type integer expected");
        }

        return new ExprUnary(op, e);
    }

    private Expr exprPrimary() {
        //System.out.println("exprPrimary");
        // ExprPrimary ::= Id | FuncCall | ExprLiteral
        Expr e;

        switch (lexer.token) {
            case LITERALINT:
                return exprLiteralInt();
            case LITERALSTRING:
                return exprLiteralString();
            case TRUE:
                return exprLiteralBoolean();
            case FALSE:
                return exprLiteralBoolean();
            case IDENT: // Sera uma variavel simples ou uma chamada de funcao
                return exprId();
            case READINT:
                return readInt();
            case READSTRING:
                return readString();

            default:
                error.signal("Statement expected");
                return null;
        }
    }

    private ExprLiteralInt exprLiteralInt() {
        //System.out.println("exprLiteralInt");
        if (lexer.token != Symbol.LITERALINT) {
            error.signal("Int expected");
        }

        int value = lexer.getNumberValue();
        lexer.nextToken();

        return new ExprLiteralInt(value);
    }

    private ExprLiteralString exprLiteralString() {
        //System.out.println("exprLiteralString");
        if (lexer.token != Symbol.LITERALSTRING) {
            error.signal("String expected");
        }

        String value = lexer.getStringValue();
        lexer.nextToken();

        return new ExprLiteralString(value);
    }

    private ExprLiteralBoolean exprLiteralBoolean() {
        //System.out.println("exprLiteralBoolean");
        if (lexer.token != Symbol.FALSE && lexer.token != Symbol.TRUE) {
            error.signal("Boolean expected");
        }

        boolean value = false;
        lexer.nextToken();

        return new ExprLiteralBoolean(value);
    }

    private FuncCall funcCall(String name) {
        //System.out.println("funcCall");
        // FuncCall ::= Id "(" [ Expr {”, ”Expr} ] ")"
        ArrayList<Expr> exprList = new ArrayList<Expr>();
        Expr e = null;
        Function f = (Function) symbolTable.getInGlobal(name);

        if (lexer.token != Symbol.LEFTPAR) {
            error.signal("( expected");
        }
        lexer.nextToken();

        ParamList pList = f.getParamList();
        int plSize;
        int i = 0;
        Parameter pAux;

        // Funcao sem parametro nao possui paramList
        if (pList != null) {
            plSize = pList.size();
        } else {
            plSize = 0;
        }

        if (lexer.token != Symbol.RIGHTPAR) {
            // processa todas expressoes
            while (true) {
                e = expr();
                exprList.add(e);

                // analise semantica
                // verifica se nao esta sendo passado parametro a mais
                if (i >= plSize) {
                    error.signal("Wrong number of parameters in call");
                }

                // verifica se o tipo dos parametros estao corretos
                pAux = pList.access(i);

                if (pAux.getType().getTypeName() != e.getType().getTypeName()) {
                    error.signal("Incompatible parameter types in call");
                }

                i++;

                if (lexer.token == Symbol.COMMA) {
                    lexer.nextToken();
                } else {
                    break;
                }
            }

            if (lexer.token != Symbol.RIGHTPAR) {
                error.signal(") expected");
            }
            lexer.nextToken();

            // analise semantica
            // verifica se nao esta sendo passado parametros a menos
            if (i < plSize) {
                error.signal("Wrong number of parameters in call");
            }

        } else {
            if (plSize > 0) {
                error.signal("Parameter expected");
            }

            lexer.nextToken();
        }

        return new FuncCall(f, exprList);
    }

    private Expr exprId() {
        //System.out.println("exprId");
        String name = lexer.getStringValue();
        lexer.nextToken();

        // Se for funcCall
        if (lexer.token == Symbol.LEFTPAR) {
            // Verifica se funcao foi declarada
            if (symbolTable.getInGlobal(name) == null) {
                error.signal("function " + name + " was not declared");
            }

            return funcCall(name);
        }

        // Se for exprId
        Variable id = (Variable) symbolTable.getInLocal(name);
        if (id == null){
            error.signal("identifier " + name + " doesn't exists");
        }

        return new ExprIdentifier(name, id.getType());
    }

    private Statement writeLn() {
        lexer.nextToken();
        Expr e = null;
        if (lexer.token == Symbol.LEFTPAR) {
            lexer.nextToken();
            e = expr();
            if(e.getType().getTypeName() != "Int" && e.getType().getTypeName() != "String")
                error.signal("type not allowed");
        }
        else error.signal("left par expected");
        if( lexer.token != Symbol.RIGHTPAR) error.signal("right par expected");
        lexer.nextToken();
        return new WriteLn(e);
    }

    private Statement write() {
        lexer.nextToken();
        Expr e = null;
        if (lexer.token == Symbol.LEFTPAR) {
            lexer.nextToken();
            e = expr();

           if(e.getType().getTypeName() != "Int" && e.getType().getTypeName() != "String")
                error.signal("type not allowed");
        }
        else error.signal("left par expected");
        if( lexer.token != Symbol.RIGHTPAR) error.signal("right par expected");
        lexer.nextToken();

        return new Write(e);
    }

    private Expr readInt() {
        lexer.nextToken();
        if (lexer.token == Symbol.LEFTPAR) {
            lexer.nextToken();
            if(lexer.token == Symbol.RIGHTPAR)
                lexer.nextToken();
            else
                error.signal("right par expected");
        }
        else error.signal("left par expected");
        return new ReadInt();
    }

    private Expr readString() {
        lexer.nextToken();
        if (lexer.token == Symbol.LEFTPAR) {
            lexer.nextToken();
            if(lexer.token == Symbol.RIGHTPAR)
                lexer.nextToken();
            else
                error.signal("right par expected");
        }
        else error.signal("left par expected");
        return new ReadString();
    }
}
