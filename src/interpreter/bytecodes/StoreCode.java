package interpreter.bytecodes;


import java.util.ArrayList;
import java.lang.StringBuilder;
import interpreter.VirtualMachine;

/**
 *
 * @author dreruff
 */
public class StoreCode extends ByteCode {
    int literal, storeVar; 
    String id;
    
    public void init(ArrayList<String> args) {
       literal = Integer.parseInt(args.get(0));
       id = args.get(1); 
    }

    public void execute(VirtualMachine vm) {
        vm.store(literal);
        vm.dump(this);
    }
    
    public void dump(){
        String str = "STORE "; Integer lit = literal;
        str = str.concat(lit.toString() + " " + id + "  " + id 
                + " = " + storeVar);
        System.out.println(str);
    }
}