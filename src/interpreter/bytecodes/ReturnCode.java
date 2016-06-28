package interpreter.bytecodes;

import java.util.ArrayList;
import interpreter.VirtualMachine;

/**
 *
 * @author dreruff
 */
public class ReturnCode extends ByteCode {
    protected String label; 
    protected int value;

    public void init(ArrayList<String> args) {
        if (args.isEmpty())
            label = "";
        else
            label = args.get(0);
    }

    public void execute(VirtualMachine vm) {
        vm.setProgramCounter(vm.popReturnAddress());
        vm.popFrame();
        value = vm.peek();
        vm.dump(this);
    }
    
    public void dump(){       
        String str = "RETURN ";
        if(!label.isEmpty())
            str = str.concat(label);  

        System.out.println(str);
    }

}