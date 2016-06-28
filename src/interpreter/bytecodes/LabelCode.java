package interpreter.bytecodes;

import java.util.ArrayList;
import interpreter.VirtualMachine;

/**
 *
 * @author dreruff
 */
public class LabelCode extends ByteCode {
    String label;

    public void init(ArrayList<String> args) { label = args.get(0); }
    
    public void execute(VirtualMachine vm) { vm.dump(this); }

    public void dump() {
        String str = "LABEL "; str = str.concat(label);
        System.out.println(str);
    }    
     
    public String getLabel(){ return label; }
}
