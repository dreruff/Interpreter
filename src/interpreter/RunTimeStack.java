package interpreter;

import java.util.Stack;
import java.util.ArrayList;

/**
 *
 * @author dreruff
 */
public class RunTimeStack {

    private final Stack<Integer> framePointers;
    private final ArrayList<Integer> runStack;

    public RunTimeStack() {
        runStack = new ArrayList();
        framePointers = new Stack();
        framePointers.push(0);
    }

    public int peek() { return runStack.get(runStack.size() - 1); }

    public int pop() { return runStack.remove(runStack.size() - 1); }

    public int push(int value) {
        runStack.add(value); return value;
    }
    
    public Integer push(Integer value) { return push((int) value); }

    public void newFrameAt(int offset) {
        framePointers.push(runStack.size() - offset);
    }

    public void popFrame() {
        int returnValue = pop();
        int numOfPops = framePointers.pop();
        for (int i = 0; i < numOfPops; i++)
            pop();
        
        push(returnValue);
    }

    public int store(int offset) {
        int store = runStack.remove(runStack.size() - 1);
        runStack.set(offset + framePointers.peek(), store);
        return store;
    }

    public int load(int offset) {
        int load = runStack.get(framePointers.peek() + offset);
        runStack.add(load);
        return load;
    }

    public int getRunStackSize() {
        return runStack.size();
    }
    
    public String getValue(int position) {
        if (position == 0)
            return runStack.get(position).toString();
        else
            return runStack.get(position - 1).toString();
        
    }
}