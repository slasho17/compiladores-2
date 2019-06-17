

/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import Lexer.*;

public class Variable{

    //atributos:
    private String id;
    private Type type;

    public Variable(String id){
        this.id = id;
    }

    public Variable(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    public String getId(){ 
        return this.id;
    }

    public void setType(Type type){
        this.type = type;
    }

    public Type getType(){
        return this.type;
    }


}