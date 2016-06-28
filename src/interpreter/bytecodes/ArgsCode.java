package interpreter.bytecodes;

import java.util.ArrayList;
import interpreter.VirtualMachine;

/**
 *
 * @author dreruff
 */
public class ArgsCode extends ByteCode {
    int numOfArgs;
    ArrayList<String> args;

    public void init(ArrayList<String> args) {
        numOfArgs = Integer.parseInt(args.get(0));
        this.args = args;
    }

    public void execute(VirtualMachine vm) {
        vm.newFrameAt(numOfArgs); 
        vm.dump(this);
    }
    
    public void dump() {    
        String str = "ARGS "; str = str.concat(((Integer)numOfArgs).toString());
        System.out.println(str);
    }
}
