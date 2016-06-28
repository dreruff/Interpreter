package interpreter.bytecodes.debuggerbytecodes;

import interpreter.bytecodes.ReturnCode;
import interpreter.*;
import interpreter.debugger.DebuggerVirtualMachine;

/**
 *
 * @author dreruff
 */
public class DebugReturnCode extends ReturnCode {
    public void execute(VirtualMachine vm) {
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine) vm;
        super.execute(dvm);
        if(dvm.getTrace()) {
            String name = "";
	    for (int i = 0; i < dvm.getFERStackSize(); i++) 
	        name += " ";
	      
	    name += "exit " + dvm.getFunctionName()+ ": " + value;
            dvm.addTrace(name);
	}
        dvm.endFunction();
    }
}
