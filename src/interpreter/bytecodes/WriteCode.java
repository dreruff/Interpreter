package interpreter.bytecodes;

import java.util.ArrayList;
import interpreter.VirtualMachine;

/**
 *
 * @author dreruff
 */
public class WriteCode extends ByteCode {
    public void init(ArrayList<String> args) {}
    
    public void execute(VirtualMachine vm) {
        int value = vm.peek();
        System.out.println(value);
    }

     public void dump() { System.out.println("WRITE"); }
}