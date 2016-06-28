package interpreter.bytecodes.debuggerbytecodes;

import interpreter.bytecodes.CallCode;
import interpreter.VirtualMachine;
import interpreter.debugger.DebuggerVirtualMachine;

/**
 *
 * @author dreruff
 */
public class DebugCallCode extends CallCode {
    public void execute(VirtualMachine vm){
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine) vm;
        super.execute(dvm);
        dvm.newFunction();
        
    }
}
