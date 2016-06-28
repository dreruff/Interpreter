package interpreter.bytecodes;

import java.util.ArrayList;
import interpreter.VirtualMachine;
import interpreter.debugger.DebuggerVirtualMachine;


/**
 *
 * @author dreruff
 */
public class LineCode extends ByteCode {
    int lineNumber;
    
    public void init(ArrayList<String> args){
        lineNumber = Integer.parseInt(args.get(0));
    }
    
    public void execute(VirtualMachine vm) {
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine) vm;
        dvm.setCurrentLine(lineNumber);
        dvm.dump(this);
    }
    
    public void dump() { System.out.println("LINE " + lineNumber); }
    
    public int getLineNum(){ return lineNumber; }
}
