package interpreter.bytecodes;

import interpreter.VirtualMachine;
import interpreter.debugger.DebuggerVirtualMachine;
import java.util.ArrayList;

/**
 *
 * @author dreruff
 */
public class FunctionCode extends ByteCode {
    private String name; 
    private int start, end;
    
    @Override
    public void init(ArrayList<String> args){
        name = args.get(0);
        start = Integer.parseInt(args.get(1));
        end = Integer.parseInt(args.get(2));
    }
    
    @Override
    public void execute(VirtualMachine vm) {
        DebuggerVirtualMachine dvm = (DebuggerVirtualMachine) vm;
        dvm.setFunction(name, start, end);
        if(dvm.getTrace()){
            String function = "";
            for (int i = 0; i < dvm.getFERStackSize(); i++)
                function += " ";
             
            function += name + "(";
            int pc = dvm.getProgramCounter() + 1;
            ByteCode code = dvm.getCode(pc);
            while (code instanceof FormalCode) {
                function += dvm.getValue(dvm.getRunStackSize() - Integer.parseInt(((FormalCode) code).getOffset())) + "";
                code = dvm.getCode(++pc);
            }
            
            dvm.addTrace(function+")");  
        }
        dvm.dump(this);
    }
    
    @Override
    public void dump() {
        System.out.println("FUNCTION " + name + " " + start + " " + end);
    }
    
    public void trace() {
        
    }

    public int getStart() { return start; }

    public int getEnd() { return end; }
}
