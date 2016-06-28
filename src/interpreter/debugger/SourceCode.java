package interpreter.debugger;

/**
 *
 * @author dreruff
 */
public class SourceCode {
    private String line;
    public boolean isBreakpoint;
    
    public SourceCode() {
        line = null;
        isBreakpoint = false;
    }
    
    public void setLine(String line) { 
        this.line = line; 
    }
    
    public String getLine() { return this.line; }
    
    public void setBreakpoint(Boolean status){ isBreakpoint = status; }
    
    public boolean isBreakpoint(){ return isBreakpoint; }
}