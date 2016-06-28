package interpreter.bytecodes;

import java.util.ArrayList;
import interpreter.VirtualMachine;

/**
 *
 * @author dreruff
 */
public class FalseBranchCode extends ByteCode implements AddressResolution {
    String label;
    int address;

    public void init(ArrayList<String> args) { label = args.get(0); }
    
    public void execute(VirtualMachine vm) {
        int topOfStack = vm.pop();
        if (topOfStack == 0)
            vm.setProgramCounter(address);
        
        vm.dump(this);
    }
    
    public void dump() {
        String str = "FALSEBRANCH "; str = str.concat(label);
        System.out.println(str);
    }

    public String getLabel() { return label; }

    public void setAddress(int address) { this.address = address; }

    public int getAddress() { return address; }
}