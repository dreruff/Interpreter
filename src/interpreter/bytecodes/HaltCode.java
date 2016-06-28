package interpreter.bytecodes;

import java.util.ArrayList;
import interpreter.VirtualMachine;

/**
 *
 * @author dreruff
 */
public class HaltCode extends ByteCode {

   public void init(ArrayList<String> args){}
    
    public void execute(VirtualMachine vm){ vm.stopVM(); }
    
    public void dump(){}
}
