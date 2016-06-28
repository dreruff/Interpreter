package interpreter.debugger;

import interpreter.ByteCodeLoader;
import interpreter.Interpreter;
import interpreter.Program;
import interpreter.debugger.ui.UI;

/**
 *
 * @author dreruff
 */
public class Debugger {
    private final Interpreter interpreter;
    
    public Debugger(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public void debugRun(String sourcecode, ByteCodeLoader bcl) {
        Program program = bcl.loadCodes();
        DebuggerVirtualMachine dvm = new DebuggerVirtualMachine(program);
        dvm.loadSource(sourcecode);
        
        UI ui = new UI(dvm);
        while (dvm.getIsRunning())
            ui.userCommand();
    }
}
