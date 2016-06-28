package interpreter.debugger;

import java.util.Iterator;

/**
 *
 * @author dreruff
 */

/** <pre>
 *  Binder objects group 3 fields
 *  1. a value
 *  2. the next link in the chain of symbols in the current scope
 *  3. the next link of a previous Binder for the same identifier
 *     in a previous scope
 *  </pre>
*/
class Binder {
    private final Object value;
    private final String prevtop;   // prior symbol in same scope
    private final Binder tail;      // prior binder for same symbol
                            // restore this when closing scope
    Binder(Object v, String p, Binder t) {
        value=v; prevtop=p; tail=t;
    }

    Object getValue() { return value; }
    String getPrevtop() { return prevtop; }
    Binder getTail() { return tail; }
}

class Table {

    private final java.util.HashMap<String,Binder> ids = new java.util.HashMap<>();
    private String top;     // reference to last symbol added to
                            // current scope; this essentially is the
                            // start of a linked list of symbols in scope
    private Binder marks;   // scope mark; essentially we have a stack of
                            // marks - push for new scope; pop when closing
                            // scope

    public Table(){}

    /**
    * Gets the object associated with the specified symbol in the Table.
    */
    public Object get(String key) {
	Binder e = ids.get(key);
	return e.getValue();
    }

    /**
    * Puts the specified value into the Table, bound to the specified Symbol.<br>
    * Maintain the list of symbols in the current scope (top);<br>
    * Add to list of symbols in prior scope with the same string identifier
    */
    public void put(String key, Object value) {
	ids.put(key, new Binder(value, top, ids.get(key)));
	top = key;
    }

    /**
    * Remembers the current state of the Table; push new mark on mark stack
    */
    public void beginScope() {
        marks = new Binder(null,top,marks);
        top = null;
    }
  
    public void pop(int num) {
        for (int i = 0; i < num; i++) {
            Binder e = ids.get(top);
            if (e.getTail() != null) 
                ids.put(top, e.getTail());
            else
                ids.remove(top);
            
            top = e.getPrevtop();
        }
    }
    
    public String[][] getVar() {
        Iterator<String> set = ids.keySet().iterator();
        String[][] variables = new String[ids.size()][2];
        for (int i = 0; i < ids.size(); i++) {
            variables[i][0] = set.next();
            variables[i][1] = ids.get(variables[i][0]).getValue().toString();
        }
        return variables;
    }
    
    /**
    * @return a set of the Table's symbols.
    */
    public java.util.Set<String> keys() {return ids.keySet();}
}



public class FunctionEnvironmentRecord {
    private final Table ids;
    private int startLine, endLine, currentLine;
    String name;
    
    public FunctionEnvironmentRecord(){
        ids = new Table();
        startLine = endLine = currentLine = 0; 
        name = "";
        //beginScope();
    }
    
    public void beginScope() {
        ids.beginScope();
    }
    
    public void setFunctionInfo(String name, int startLine, int endLine) {
        this.name = name;
        this.startLine = startLine;
        this.endLine = endLine;
    }
    
    public void enterSymbol(String id, Integer literal) {
        ids.put(id, literal);
    }
    
    public void setCurrentLine(int currentLine) { 
        this.currentLine = currentLine;
    }
    
    public String getFunctionName() { return name; }
    
    public int getStartLine() { return startLine; }
    
    public int getEndLine() { return endLine; }
    
    public int getCurrentLine() { return currentLine; }
    
    public String[][] getVar() { return ids.getVar(); }
    
    public void doPop(int i) { ids.pop(i); }
    
    public int getOffsetValue(String key){ return (Integer) ids.get(key); }
    
    public java.util.Set<String> getKeySet(){ return ids.keys(); }
    
    public void dump() {
        java.util.Iterator<String> iterator = ids.keys().iterator();
        java.lang.StringBuilder output = new StringBuilder("(<");
        String id;
   
        while(iterator.hasNext()) {
            id = iterator.next();
            output.append(id);
            output.append("/");
            output.append((Integer) ids.get(id));
            output.append(",");
        }
        if(output.length() > 2)         //checks if funStack empty
            output.deleteCharAt(output.length() - 1);
        output.append(">,");
        
        if (name.isEmpty())
            output.append("-,-,-,-");
        else {
            output.append(name + "," + startLine + "," + endLine + ",");
            if(currentLine == 0)
                output.append("-");
            else
                output.append(currentLine);
        }
        output.append(")");
        System.out.println(output.toString());
    }
    
    public static void main(String args[]) {
        FunctionEnvironmentRecord fctEnvRecord = new FunctionEnvironmentRecord();
        
        fctEnvRecord.beginScope();      //BS
        fctEnvRecord.dump();            //(< >,g, 1, 20,5)
        
        fctEnvRecord.setFunctionInfo("g", 1, 20);   //Function g 1 20
        fctEnvRecord.dump();                        //(< >,g, 1, 20,-)
        
        fctEnvRecord.setCurrentLine(5);         //LINE 5
        fctEnvRecord.dump();                    //(< >,g, 1, 20,5)
        
        fctEnvRecord.enterSymbol("a", 4);         //Enter a 4
        fctEnvRecord.dump();                    //(<a/4>, g, 1, 20, 5))
        
        fctEnvRecord.enterSymbol("b", 2);         //Enter b 2 
        fctEnvRecord.dump();                    //(<a/4,b/2>, g, 1, 20, 5)
        
        fctEnvRecord.enterSymbol("c", 7);         //Enter c 7 
        fctEnvRecord.dump();                    //(<a/4,c/7,b/2>, g, 1, 20, 5)
        
        fctEnvRecord.enterSymbol("a", 1);         //Enter a 1 
        fctEnvRecord.dump();                    //(<a/1, c/7,b/2>, g, 1, 20, 5)
        
        fctEnvRecord.doPop(2);          //Pop 2 
        fctEnvRecord.dump();            //( < a /4,b/2>, g, 1, 20, 5) 
        
        fctEnvRecord.doPop(1);          //Pop 1 
        fctEnvRecord.dump();            //(<a/4>, g, 1, 20, 5)
    }
}
