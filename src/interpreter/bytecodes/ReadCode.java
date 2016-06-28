package interpreter.bytecodes;

import java.util.Scanner;
import java.util.ArrayList;
import interpreter.VirtualMachine;

/**
 *
 * @author dreruff
 */
public class ReadCode extends ByteCode {
    Scanner scanner = new Scanner(System.in);

    public void init(ArrayList<String> args) {}

    public void execute(VirtualMachine vm) {
        System.out.print("Input number: ");
        int input = scanner.nextInt();
        vm.push(input);
        vm.dump(this);
    }

    public void dump() {
        System.out.println("READ");
    }
}