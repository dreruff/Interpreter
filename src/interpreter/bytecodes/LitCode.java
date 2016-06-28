package interpreter.bytecodes;

import java.util.ArrayList;
import interpreter.VirtualMachine;

/**
 *
 * @author dreruff
 */
public class LitCode extends ByteCode {
    protected int literal;
    protected String id;

    public void init(ArrayList<String> args) {
        id = "";
        literal = Integer.parseInt(args.get(0));
        if (args.size() > 1)
            id = args.get(1);
    }

    public void execute(VirtualMachine vm) {
        vm.push(literal);
        vm.dump(this);
    }

    public void dump(){
        String str = "LIT "; str = str.concat(((Integer)literal).toString());
        if(!id.isEmpty())  
            str = str.concat(" "+ id+ "   int "+ id);
        
        System.out.println(str);
    }
}
