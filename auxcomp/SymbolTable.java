package auxcomp;

import java.util.*;


public class SymbolTable{

    //keeps two tables that associate identifier name, with object which is identified.
    private Hashtable localTable;
    private Hashtable globalTable;

    public SymbolTable(){
        globalTable = new Hashtable();
        localTable = new Hashtable();
    }

    //Hash_Table.put(key, value): if an existing key is passed then the previous value gets replaced by the new value. 
    //If a new pair is passed, then the pair gets inserted as a whole. 
    //If an existing key is passed then the previous value gets returned, else returns null.
    
    public Object putsInGlobal( String key, Object value){
        return globalTable.put(key, value);
    }

    public Object putsInLocal( String key, Object value){
        return localTable.put(key, value);
    }

    //Hash_Table.get() : method returns the value associated with the key_element in the parameter.
    // if no value associated, returns null

    public Object getInLocal( Object key ) {
        return localTable.get(key);
    }
    
    public Object getInGlobal( Object key ) {
        return globalTable.get(key);
    }

    public Object get( String key ) {
        // returns the object corresponding to the key.
        Object result;
        if ( (result = localTable.get(key)) != null ) {
            // found local identifier
            return result;
        }else{
            // global identifier, if it is in globalTable. If it isn't returns null.
            return globalTable.get(key);
        }
    }

    public void removeLocalIdent(){
        localTable.clear();
    }


}