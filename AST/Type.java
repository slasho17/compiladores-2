/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import Lexer.Symbol;

abstract public class Type {

	private String nome = "";

	public Type(String name) {
		this.nome = name;
	}

	public static Type booleanType = new BooleanType();
	public static Type integerType = new IntegerType();
	public static Type stringType = new StringType();

	public String getTypeName() { return this.nome; }

}
