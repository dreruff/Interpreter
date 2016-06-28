package interpreter.bytecodes;

import java.util.ArrayList;
import interpreter.VirtualMachine;

/**
 *
 * @author dreruff
 */
public abstract class ByteCode {
    public abstract void init(ArrayList<String> args);
    public abstract void execute(VirtualMachine vm);
    public abstract void dump();
}