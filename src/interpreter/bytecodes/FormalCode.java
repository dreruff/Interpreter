package interpreter.bytecodes;


import java.util.ArrayList;
import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.VirtualMachine;



/**
 *
 * @author dreruff
 */

public class FormalCode extends ByteCode {
    private String id, offset;
    
    @Override
    public void init(ArrayList<String> args){
        id = args.get(0);
        offset = args.get(1);
    }
    
    @Override
    public void execute(VirtualMachine vm) {
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine) vm;
        dvm.enterSymbol(id, dvm.getRunStackSize() - Integer.parseInt(offset));
        dvm.dump(this);
    }

    @Override
    public void dump() {
        System.out.println("FORMAL " + id + " " + offset);
    }
    
    public String getOffset(){ return offset; }
}