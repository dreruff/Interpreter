package interpreter.debugger.ui;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import interpreter.debugger.DebuggerVirtualMachine;
import interpreter.debugger.SourceCode;

/**
 *
 * @author dreruff
 */
public class UI {
    private final DebuggerVirtualMachine dvm;
    private final ArrayList<SourceCode> src;
    private final ArrayList<Integer> breakpoints;
    private final Scanner scanner;
    int currentLine;
    
    public UI(DebuggerVirtualMachine dvm) {
        this.dvm = dvm;
        src = dvm.getSourceCode();
        scanner = new Scanner(System.in);
        currentLine = 1;
        breakpoints = new ArrayList();
    }
    
    public void userCommand() {
        display();
        System.out.print("\nType ? for help\n>> ");
        String line = scanner.next();
        
        switch (line) {
            case "?":
                help();
                break;
            case "sb":
                setBreak();
                break;
            case "clr":
                clearBreak();
                break;
            case "list":
                listBreak();
                break;
            case "fun":
                displayFunction();
                break;
            case "cont":
                continueExecution();
                break;
            case "quit":
                quitExecution();
                break;
            case "var":
                displayVars();
                break;
            case "out":
                stepOut();
                break;
            case "over":
                stepOver();
                break;
            case "in":
                stepIn();
                break;
            case "trace":
                setTrace();
                break;
            case "stack":
                printCallStack();
                break;
            default:
                System.out.println("**** ERROR: Invalid Command type \"?\" for list of Commands");
                break;
        }
    }
  
    public void help() {
        System.out.print(
                "\nMenu: \n"
                + "?\thelp menu\n"
                + "sb\tset breakpoints\t\tsb 1 2\n"
                + "clr\tclear breakpoints\tclr 3 4\n"
                + "fun\tdisplay function\n"
                + "cont\tcontinue execution\n"
                + "quit\tquit execution\n"
                + "var\tdisplay vars in function\n"
                + "out\tstep out\n"
                + "over\tstep over\n"
                + "in\tstep in\n"
                + "trace\tset trace\n"
                + "stack\tprint call stack\n");
    }
    
    public void setBreak() {
        ArrayList<Integer> brkpts = new ArrayList();
        String line = scanner.nextLine();
        StringTokenizer tok = new StringTokenizer(line);
        
        while (tok.hasMoreTokens())
            brkpts.add(Integer.parseInt(tok.nextToken()));
        
        if (dvm.setBreaks(brkpts)) {
            System.out.print("Breakpoint(s) set at: ");
            for (int point : brkpts){
                breakpoints.add(point);
                System.out.print(point + " ");
            }
        } else {
            System.out.print("**** One or more Breakpoint(s) are invalid ****\n"
                    + "Breakpoints can only be set at: "
                    + "blocks, while, if, return, assign");
        }
        System.out.println();
    }
    
    public void clearBreak() {
        ArrayList<Integer> brkpts = new ArrayList();
        String line = scanner.nextLine();
        StringTokenizer tok = new StringTokenizer(line);
        
        while (tok.hasMoreTokens()) 
            brkpts.add(Integer.parseInt(tok.nextToken()));
        

        dvm.clearBreaks(brkpts);

        System.out.print("Cleared breakpoints at: ");
        for (int point : brkpts) {
            breakpoints.remove((Integer)point);
            System.out.print(point + " ");
        }
        System.out.println();
    }
    
    public void listBreak() {
        if(breakpoints.isEmpty()) {
            System.out.println("No breakpoints set");
            return;
        }
        
        if(breakpoints.size() == 1)
            System.out.print("Breakpoint: ");
        else
            System.out.print("Breakpoints: ");
        
        for (int breaks : breakpoints) {
            System.out.print(breaks + " ");
        }
        System.out.println();
    }
    
    public void display() {
        currentLine = dvm.getCurrentLine();
        int i = 0;
        System.out.println();
        for (SourceCode line : src) {
            
            if (line.isBreakpoint())
                System.out.print("*");
            else
                System.out.print(" ");
                
            System.out.print(String.format("%2d", (src.indexOf(line) + 1)) + " ");
                
            if (currentLine == i + 1)
                System.out.println(line.getLine() + "         <------");
            else
                System.out.println(line.getLine());
            
            i++;
        }
        dvm.printTrace();
    }
    
    public void displayFunction() { dvm.displayFunction(); }
    
    public void displayVars(){ dvm.displayVars(); }
    
    public void continueExecution(){ dvm.executeProgram(); }
    
    public void quitExecution(){ dvm.setIsRunning(false); }
    
    public void stepOut() { dvm.stepOut(); }
    
    public void stepOver() { dvm.stepOver(); }
    
    public void stepIn() { dvm.stepIn(); }
              
    public void setTrace() { 
        if (dvm.setTrace())
            System.out.println("Tracing is on");
        else
            System.out.println("Tracing is off");
    }
         
    public void printCallStack() {
        dvm.printCallStack();
    }
}


