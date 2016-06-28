package interpreter.bytecodes;

import java.util.ArrayList;
import interpreter.VirtualMachine;

/**
 *
 * @author dreruff
 */
public class PopCode extends ByteCode {
    protected int numOfPops;

    public void init(ArrayList<String> args) {
        numOfPops = Integer.parseInt(args.get(0));
    }

    public void execute(VirtualMachine vm) {
        for (int i = 0; i < numOfPops; i++)
            vm.pop();
        
        vm.dump(this);
    }

    public void dump() {
        String str = "POP "; str = str.concat(((Integer)numOfPops).toString()); 
        System.out.println(str);
    }
    
    public int getOffset(){ return numOfPops; }
}