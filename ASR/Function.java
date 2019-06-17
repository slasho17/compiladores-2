/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

import java.util.ArrayList;
import java.io.*;


public class Function {

	//atributos:
	private String id;
	private Type type;
	private ParamList paramList;
	private StatementList statList;


	//metodos:
	public Function( String Id ) {
      this.id = Id;
			//this.statList = new ArrayList<Stat>;

  }

	public void setReturnType(Type x){
		this.type  = x;
	}

  public String getId() {
		return this.id;
	 }

	public Type getType(){
		return this.type;
	}

	public void setParamList(ParamList paramlist){
		this.paramList = paramlist;
	}

public ParamList getParamList(){
	return this.paramList;
}

	public void setStatList(StatementList statlist){
		this.statList = statlist;
	}


  public void genC() {
		  
		this.statList.genC();
	
  }


}
