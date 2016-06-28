package interpreter;

import java.util.HashMap;

/**
 *
 * @author dreruff
 */
public class CodeTable {

    private static final HashMap<String, String> table = new HashMap<>();

    public static String get(String code) {
        return table.get(code);
    }

    public static void init() {
        table.put("HALT", "HaltCode");
        table.put("POP", "PopCode");
        table.put("FALSEBRANCH", "FalseBranchCode");
        table.put("GOTO", "GoToCode");
        table.put("STORE", "StoreCode");
        table.put("LOAD", "LoadCode");
        table.put("LIT", "LitCode");
        table.put("ARGS", "ArgsCode");
        table.put("CALL", "CallCode");
        table.put("RETURN", "ReturnCode");
        table.put("BOP", "BopCode");
        table.put("READ", "ReadCode");
        table.put("WRITE", "WriteCode");
        table.put("LABEL", "LabelCode");
        table.put("DUMP", "DumpCode");
    }
    
    public static void initDebug(){
        table.put("CALL", "DebugCallCode");
        table.put("POP", "DebugPopCode");
        table.put("RETURN", "DebugReturnCode");
        table.put("LIT", "DebugLitCode");
        table.put("LINE", "LineCode");
        table.put("FUNCTION", "FunctionCode");
        table.put("FORMAL", "FormalCode");
        table.put("HALT", "HaltCode");
        table.put("FALSEBRANCH", "FalseBranchCode");
        table.put("GOTO", "GoToCode");
        table.put("STORE", "StoreCode");
        table.put("LOAD", "LoadCode");
        table.put("ARGS", "ArgsCode");
        table.put("BOP", "BopCode");
        table.put("READ", "ReadCode");
        table.put("WRITE", "WriteCode");
        table.put("LABEL", "LabelCode");
        table.put("DUMP", "DumpCode");
    }
}