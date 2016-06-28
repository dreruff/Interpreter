package interpreter.bytecodes.debuggerbytecodes;

import interpreter.bytecodes.PopCode;
import interpreter.VirtualMachine;
import interpreter.debugger.DebuggerVirtualMachine;

/**
 *
 * @author dreruff
 */
public class DebugPopCode extends PopCode {
    public void execute(VirtualMachine vm) {
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine) vm;
        super.execute(dvm);
        dvm.popSymbol(numOfPops);
    }
}
