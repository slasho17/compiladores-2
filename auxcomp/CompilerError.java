/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package auxcomp;

import Lexer.*;
import java.io.*;

public class CompilerError {


	public CompilerError(Lexer lexer){
		this.lexer = lexer;
		thereWasAnError = false;
	}

	public void setLexer( Lexer lexer ) {
		this.lexer = lexer;
	}

	public boolean wasAnErrorSignalled() {
		return thereWasAnError;
	}

	public void show( String strMessage ) {
		show( strMessage, false );
	}

	public void show( String strMessage, boolean goPreviousToken ) {
		// is goPreviousToken is true, the error is signalled at the line of the
		// previous token, not the last one.
		if ( goPreviousToken ) {
			System.out.println("Error at line " + lexer.getLineNumberBeforeLastToken() + ": ");
			System.out.println( lexer.getLineBeforeLastToken() );
		}else {
			System.out.println("Error at line " + lexer.getLineNumber() + ": ");
			System.out.println(lexer.getCurrentLine());
		}

		System.out.println( strMessage );
		System.out.flush();
		if ( System.out.checkError() )
			System.out.println("Error in signaling an error");
		thereWasAnError = true;

	}

	public void signal (String strMessage) {
		show( strMessage );
		System.out.flush();
		thereWasAnError = true;
		throw new RuntimeException();
	}

	//atributos
	private Lexer lexer;
	private boolean thereWasAnError;

}
