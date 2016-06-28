package interpreter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import interpreter.bytecodes.*;


/**
 *
 * @author dreruff
 */
public class ByteCodeLoader {
    private final BufferedReader source;
    String sourcecode;
    
    public ByteCodeLoader(String programFile) throws IOException {
        source = new BufferedReader(new FileReader( programFile ) );
    }
    
    public Program loadCodes() {
        String key, code;
        String line;
        ByteCode bytecode;
        ArrayList<String> args = new ArrayList();
        
        Program program = new Program();
        
        try {
            line = source.readLine();
            
            while(line != null) {
                StringTokenizer tok = new StringTokenizer(line);
                
                key = tok.nextToken();
                code = CodeTable.get(key);
                
                if (code.startsWith("Debug")) 
                    bytecode = (ByteCode) (Class.forName("interpreter.bytecodes.debuggerbytecodes." + code).newInstance());
                else 
                    bytecode = (ByteCode) (Class.forName("interpreter.bytecodes." + code).newInstance());
            
                
                while (tok.hasMoreTokens()) 
                    args.add(tok.nextToken());
                
                bytecode.init(args);
                program.set(bytecode);
                
                args.clear();
                line = source.readLine();   
            }
            
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e ) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        
        program.resolveAddress();
        return program;
    }
}
