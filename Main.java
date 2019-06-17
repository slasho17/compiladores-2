/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

import AST.*; import java.io.*;

public class Main {

    public static void main( String []args ) {
        File file;

        FileReader stream;
    
        int numChRead;
    
        Program program;
    
        if ( args.length != 1 ) {
            System.out.println("Usage:\nMain input");
            System.out.println("input is the file to be compiled");
        } else {
            file = new File(args[0]);
            if ( ! file.exists() || ! file.canRead() ) {
                System.out.println("Either the file " + args[0] + " does not exist or it cannot be");
                throw new RuntimeException();
            }
            try {
                stream = new FileReader(file);
            } catch ( FileNotFoundException e ) {
                System.out.println("Something wrong: file does not exist anymore");
                throw new RuntimeException();
            }
            // one more character for ’\0’ at the end that will be added by the
            // compiler
            char []input = new char[ (int ) file.length() + 1 ];
            try {
                numChRead = stream.read( input, 0, (int ) file.length() );
            } catch ( IOException e ) {
                System.out.println("Error reading file " + args[0]);
                throw new RuntimeException();
            }

            if ( numChRead != file.length() ) {
                System.out.println("Read error");
                throw new RuntimeException();
            }

            try {
                stream.close();
            } catch ( IOException e ) {
                System.out.println("Error in handling the file " + args[0]);
                throw new RuntimeException();
            }
            
            Compiler compiler = new Compiler();
        

            program = null;
            // the generated code goes to a file and so does the errors
            try {
                program = compiler.compile(input);
            } catch ( RuntimeException e ) {
                System.out.println(e);
            }

            if ( program != null ) {
                //program.genC();
            }
        }
    }
}