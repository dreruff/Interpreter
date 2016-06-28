package interpreter;

import java.util.Stack;
import interpreter.bytecodes.*;

/**
 *
 * @author dreruff
 */
public class VirtualMachine {
    protected int pc;
    protected Stack<Integer> returnAddrs;
    protected RunTimeStack runStack;
    protected boolean isRunning;
    protected Program program;
    boolean dumping;

    public VirtualMachine(Program program) {
        this.program = program;
        runStack = new RunTimeStack();
        returnAddrs = new Stack();
    }

    public void executeProgram() {
        pc = 0;
        isRunning = true;
        while (isRunning) {
            ByteCode bytecode = program.getCode(pc);
            bytecode.execute(this);
            // runStack.dump(); // check that the operation is correct
            pc++;
        }
    }

    public void setProgramCounter(int pc) { this.pc = pc; }
    
    public void dump(ByteCode bytecode) { if (dumping) bytecode.dump(); }

    public void setDumping(boolean dumping) { this.dumping = dumping; }

    public int peek() { return runStack.peek(); }

    public int pop() { return runStack.pop(); }

    public int push(int i) { return runStack.push(i); }

    public void saveReturnAddress() { returnAddrs.push(pc); }
    
    public Integer popReturnAddress() { return returnAddrs.pop(); }
    
    public void newFrameAt(int i) { runStack.newFrameAt(i); }

    public void popFrame() { runStack.popFrame(); }

    public int store(int i) { return runStack.store(i); }

    public int load(int i) { return runStack.load(i); }

    public Integer push(Integer i) { return runStack.push(i); }

    public void stopVM() { isRunning = false; }
    
    public int getRunStackSize() { return runStack.getRunStackSize(); }
}
