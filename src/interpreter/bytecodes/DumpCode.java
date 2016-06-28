package interpreter.bytecodes;

import java.util.ArrayList;
import interpreter.VirtualMachine;


/**
 *
 * @author dreruff
 */
public class DumpCode extends ByteCode {
    String status;
    
    public void init(ArrayList<String> args) { 
        status = args.get(0);
    }
    
    public void execute(VirtualMachine vm) {
        if(status.equals("ON"))
            vm.setDumping(true);
        else
            vm.setDumping(false);
    }
    
    public void dump(){}
}
