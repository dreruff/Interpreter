package interpreter.bytecodes;

import java.util.ArrayList;
import interpreter.VirtualMachine;

/**
 *
 * @author dreruff
 */
public class LoadCode extends ByteCode {
    int offset; 
    String id;

    public void init(ArrayList<String> args) {
        offset = Integer.parseInt(args.get(0));
        id = args.get(1);
    }

    public void execute(VirtualMachine vm) {
        vm.load(offset);
        vm.dump(this);
    }
    
    public void dump() {
        String str = "LOAD "; str = str.concat(((Integer)offset).toString());
        if(!id.isEmpty())  
            str=str.concat(" "+ id+ "   int "+ id +" <load "+ id + ">");
        
        System.out.println(str);
    }
}
