package interpreter.debugger;

import interpreter.Program;
import interpreter.RunTimeStack;
import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.bytecodes.FormalCode;
import interpreter.bytecodes.FunctionCode;
import interpreter.bytecodes.LineCode;
import interpreter.bytecodes.ReadCode;
import interpreter.bytecodes.WriteCode;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author dreruff
 */
public class DebuggerVirtualMachine extends VirtualMachine {
    
    private final ArrayList<SourceCode> sourcecode;
    private SourceCode srcLine;
    private final Stack<FunctionEnvironmentRecord> ferStack;
    private FunctionEnvironmentRecord fer;
    private final ArrayList<String> trace;
    
    private boolean stepout;
    private boolean stepover;
    private boolean stepin;
    private boolean isTracing;
    
    public DebuggerVirtualMachine(Program program){
        super(program);
        
        sourcecode = new ArrayList();
        ferStack = new Stack();
        fer = new FunctionEnvironmentRecord();
        runStack = new RunTimeStack();
        returnAddrs = new Stack();
        trace = new ArrayList();
        pc = 0;
        isRunning = true;
        stepout = false;
        stepover = false;
        stepin = false;
        isTracing = false;
    }
    
    @Override
    public void executeProgram() {
        int envsize = ferStack.size();
        
        while (isRunning) {
            ByteCode code = program.getCode(pc);
            code.execute(this);
            pc++;
                       
            if (code instanceof LineCode) {
                LineCode linecode = (LineCode) code;
                               
                if (linecode.getLineNum() > 0 && sourcecode.get(linecode.getLineNum() - 1).isBreakpoint()) {  
                    
                    stepout = stepin = stepover = false;
                    
                    code = program.getCode(pc);
                    
                    if (code instanceof FunctionCode) {
                        code.execute(this);
                        pc++;
                        code = program.getCode(pc);
                    
                        while (code instanceof FormalCode) {
                            code.execute(this);
                            pc++;
                            code = program.getCode(pc);
                        }
                    }
                    break;
                } 
            }
            
            
            if (stepout)
                if (ferStack.size() == envsize - 1) {
                    stepout = false;
                    break;
                }
            
            
            if (stepover)
                if (envsize == ferStack.size()) {
                    if ((code instanceof LineCode) && ((LineCode)code).getLineNum() > 0) {
                        stepover = false;
                        break;
                    }
                }
            
            
            if (stepin)
                if (ferStack.size() == envsize + 1) {
                    
                    if(code instanceof FunctionCode) {
                        ByteCode nextcode = program.getCode(pc);
                        if (nextcode instanceof LineCode
                                || nextcode instanceof ReadCode
                                || nextcode instanceof WriteCode) {                        
                        stepin = false;
                        break;
                        }
                    }
                } else if (code instanceof LineCode && ((LineCode)code).getLineNum() > 0) {
                    stepin = false;
                    break;
                }
            
            //code.execute(this);
            //pc++;     
        }
    }
    
    public void loadSource(String sourceFile) {
        try{
            Scanner in = (new Scanner(new FileReader(sourceFile)));
            String nextLine;

            while (in.hasNextLine()) {
                srcLine = new SourceCode();
                nextLine = in.nextLine();
                srcLine.setLine(nextLine);
                sourcecode.add(srcLine);
            }
        } catch (IOException io) {
            System.out.print("IOException in Debugger VM");
        }
    }
    
    public void stepOut() { stepout = true; executeProgram(); }
    
    public void stepOver() { stepover = true; executeProgram(); }
    
    public void stepIn() { stepin = true; executeProgram(); }
    
    public ArrayList<SourceCode> getSourceCode() { return sourcecode; }
    
    public boolean getIsRunning() { return isRunning; }
    
    public void setIsRunning(boolean running) { isRunning = running; }
    
    public void setCurrentLine(int currentLine) {
        fer.setCurrentLine(currentLine);
    }
    
    public int getCurrentLine() { return fer.getCurrentLine(); }
    
    public int getFunctionStartLine() { return fer.getStartLine(); }
    
    public int getFunctionEndLine() { return fer.getEndLine(); }
    
    public void popSymbol(int offset) { fer.doPop(offset); }
    
    public void setFunction(String name, int start, int end) {
        fer.setFunctionInfo(name, start, end);
    }

    public boolean setBreaks(ArrayList<Integer> breaks) {
        for (Integer line : breaks)
            if (!program.isValidBrk(line))
                return false;
        
        for (Integer line : breaks)
            sourcecode.get(line - 1).setBreakpoint(true);
        
        return true;
    }
    
    public void clearBreaks(ArrayList<Integer> breaks) {
        for (Integer line : breaks) {
            sourcecode.get(line - 1).setBreakpoint(false);
        }
    }

    public void newFunction() {
        ferStack.push(fer);
        fer = new FunctionEnvironmentRecord();
        fer.beginScope();
    }

    public void endFunction() { fer = ferStack.pop(); }
    
    public void displayFunction() {
        for (int line = fer.getStartLine() - 1; line < fer.getEndLine(); line++) {
                
            if (sourcecode.get(line).isBreakpoint()) 
                System.out.print("*");
            else 
                System.out.print(" ");
                    
            System.out.print(String.format("%2d", 
                    (sourcecode.indexOf(sourcecode.get(line)) + 1)) + " ");
            
            int currentline = fer.getCurrentLine();
            if ( currentline == line + 1)
                System.out.println(sourcecode.get(line).getLine() + "         <------");
            else
                System.out.println(sourcecode.get(line).getLine());
        }
    }

    public void displayVars() {
        String[][] variables = fer.getVar();
        for (String[] variable : variables) {
            String offset = variable[1];
            variable[1] = (runStack.getValue(Integer.parseInt(offset)));
        }
        System.out.println();
        for (String[] variable : variables) {
            System.out.println("ID: " + variable[0] + "\tValue: " + variable[1]);
        }
        System.out.println();
    }
  
    public int getFERStackSize() { return ferStack.size(); }

    public String getFunctionName() { return fer.getFunctionName(); }
    
    public ByteCode getCode(int pc) { return program.getCode(pc); }

    public int getProgramCounter() { return pc; }
    
    public int getValue(int offset){ 
        return Integer.parseInt(runStack.getValue(offset)); 
    }
    
    public void enterSymbol(String var, int offset) {
        fer.enterSymbol(var, offset);
    }
    
    public boolean setTrace() {
        if (isTracing)
            return isTracing = false;
        else
            return isTracing = true;
    }
    
    public boolean getTrace() { return isTracing; }
    
    public void addTrace(String function) {
        if (!isTracing) return;
        
        trace.add(function);
    }
    
    public void printTrace() {
        if (!isTracing) return;
        
        System.out.println();
        for(String function: trace)
            System.out.println(function);
    }
    
    public void printCallStack() {
        for (int i = ferStack.size() - 1; i >= 0; i--) {
            FunctionEnvironmentRecord fun = ferStack.get(i);
            String[] name = fun.getFunctionName().split("<");
            System.out.println(name[0] + "\t" + fun.getCurrentLine());
        }
    }
}
