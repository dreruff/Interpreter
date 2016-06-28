package interpreter.bytecodes.debuggerbytecodes;

import interpreter.bytecodes.LitCode;
import interpreter.VirtualMachine;
import interpreter.debugger.DebuggerVirtualMachine;

/**
 *
 * @author dreruff
 */
public class DebugLitCode extends LitCode {
    public void execute(VirtualMachine vm){
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine) vm;
        super.execute(dvm);
        if(!id.isEmpty()){
            int offset = dvm.getRunStackSize();
            dvm.enterSymbol(id, offset); 
        }
    }
}
