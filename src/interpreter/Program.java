package interpreter;

import java.util.HashMap;
import java.util.ArrayList;
import interpreter.bytecodes.*;

/**
 *
 * @author dreruff
 */
public class Program {
    private final ArrayList<ByteCode> bytecodes;
    private final HashMap<String, Integer> labelAddress;
    
    Program () {
        bytecodes = new ArrayList();
        labelAddress = new HashMap();
    }
    
    public void set(ByteCode bytecode) {
        if (bytecode instanceof LabelCode) {
            LabelCode labelBranch = (LabelCode) bytecode;
            addLabel(labelBranch.getLabel(),bytecodes.size());
        }
        bytecodes.add(bytecode);
    }
    
    public void resolveAddress() {
        for (ByteCode bytecode : bytecodes ) {
            if (bytecode instanceof AddressResolution) {
                AddressResolution ar = (AddressResolution) bytecode;
                ar.setAddress(labelAddress.get(ar.getLabel()));
            }
        }
    }
    
    public void addLabel(String label, int address) {
        labelAddress.put(label, address);
    }
    
    public ByteCode getCode(int index) { 
        return (ByteCode) bytecodes.get(index);
    }
    
    public boolean isValidBrk(int breakpoint){
        ByteCode bytecode;
        for(int i = 0 ; i < bytecodes.size() ; i++){
            bytecode = bytecodes.get(i);
            if(bytecode instanceof LineCode){
                int linenumber = ((LineCode)bytecode).getLineNum();
                if(breakpoint == linenumber)
                    return true;
            }
        }
        return false;
    }
}