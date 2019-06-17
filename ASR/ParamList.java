/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import java.util.*;
import java.io.*;


public class ParamList{

    //atributos:
    private ArrayList<Parameter> arrayParam;

    //métodos:
    public ParamList(){
      this.arrayParam = new ArrayList<Parameter>();
    }

    public void addElement(Parameter param){
      arrayParam.add(param);

    }

    public int size(){
      return arrayParam.size();
    }

    public ArrayList<Parameter> getParamList(){
      return arrayParam;
    }

    public Parameter access( int i){
      return this.arrayParam.get(i);
    }

}
