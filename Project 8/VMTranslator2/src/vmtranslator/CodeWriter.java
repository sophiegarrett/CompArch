package vmtranslator;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CodeWriter {
    
    Path outputFile;
    String filename;
    BufferedWriter writer;
    int jmpCounter = 0;
    
    public CodeWriter(String output) {
        this.outputFile = Paths.get(output + ".asm");
        
        try {
            writer = Files.newBufferedWriter(this.outputFile, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void writeLine(String s) {
        try {
            if (s.length() != 0) {
                writer.write(s, 0, s.length());
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setFileName(String file) {
        if (file.contains("/")) {
            this.filename = (file.substring(file.lastIndexOf("/") + 1));
        }
        else if (file.contains("\\")) {
            this.filename = (file.substring(file.lastIndexOf("\\") + 1));
        }
        else {
            this.filename = file;
        }
        System.out.println(this.filename);
    }
    
    public void translate(Command cmd) {
        if (cmd.getType() != null) {
            System.out.println(cmd.getType().name() + " " + cmd.getArg1() + " " + cmd.getArg2());
            
            switch (cmd.getType()) {
                case C_ARITHMETIC: writeArithmetic(cmd.getArg1()); break;
                case C_PUSH:
                case C_POP: writePushPop(cmd.getType(), cmd.getArg1(), cmd.getArg2()); break;
                case C_LABEL: writeLabel(cmd.getArg1()); break;
                case C_GOTO: writeGoto(cmd.getArg1()); break;
                case C_IF: writeIf(cmd.getArg1()); break;
                case C_FUNCTION: writeFunction(cmd.getArg1(), cmd.getArg2()); break;
                case C_CALL: writeCall(cmd.getArg1(), cmd.getArg2()); break;
                case C_RETURN: writeReturn(); break;
                default: System.out.println("Error: invalid command type!"); break;
            }
        }
    }
    
    public void writeArithmetic(String command) {
        
        // First, pop the value at the top of the stack to the D register.
        writeLine("@SP");   // go to the stack pointer
        writeLine("M=M-1"); // decrement the stack pointer's value by 1
        writeLine("A=M");    // go to the top of the stack
        writeLine("D=M");   // D = the value at the top of the stack
        
        if (command.matches("add|sub|eq|gt|lt|and|or")) {
            
            // If the command is binary, pop the value that is now at the top of the stack to the A register.
            writeLine("@SP");   // go to the stack pointer
            writeLine("M=M-1"); // decrement the stack pointer's value by 1
            writeLine("A=M");    // go to the top of the stack
            writeLine("A=M");   // A = the value at the top of the stack
            
            switch (command) {
                case "add": writeLine("D=A+D"); break;  // D = A + D
                case "sub": writeLine("D=A-D"); break;  // D = A - D
                case "and": writeLine("D=A&D"); break;  // D = A & D
                case "or": writeLine("D=A|D"); break;   // D = A | D
            }
            
            if (command.matches("eq|gt|lt")) {
                writeLine("D=A-D");     // D = A - D
                writeLine("@TRUE" + jmpCounter);     // A = TRUE
                
                switch (command) {
                    case "eq": writeLine("D;JEQ"); break;     // jump to TRUE if A-D = 0
                    case "gt": writeLine("D;JGT"); break;     // jump to TRUE if A-D > 0 (i.e. if A > D)
                    case "lt": writeLine("D;JLT"); break;     // jump to TRUE if A-D < 0 (i.e. if A < D)
                }
                
                // Since we will have jumped to TRUE if our comparison were true, this next chunk only applies if the answer is false.
                writeLine("D=0");               // D = 0
                writeLine("@END_COMPARISON" + jmpCounter);   // A = END_COMPARISON
                writeLine("0;JMP");             // jump to END_COMPARISON
                
                writeLine("(TRUE" +jmpCounter + ")");            // jump here if it's true
                writeLine("A=-1");              // A = -1
                writeLine("D=A");               // D = -1
                
                writeLine("(END_COMPARISON" + jmpCounter + ")");  // jump here to move on to the rest of the program
                jmpCounter++;
            }
        }
        
        else if (command.matches("neg|not")) {
            switch (command) {
                case "neg": writeLine("D=-D"); break;   // D = -D
                case "not": writeLine("D=!D"); break;   // D = !D
            }
        }
        
        else {
            System.out.println("Error: invalid arithmetic command!");
        }
        
        // Finally, push the value of D to the top of the stack.
        writeLine("@SP");   // go to the stack pointer
        writeLine("A=M");   // go to the top of the stack
        writeLine("M=D");   // the value at the top of the stack = D
        writeLine("@SP");   // go to the stack pointer
        writeLine("M=M+1"); // increment the stack pointer's value by 1
    }
    
    public void writePushPop(CommandType command, String segment, int index) {
        
        String name;
        switch (segment) {
            case "local": name = "LCL"; break;
            case "argument": name = "ARG"; break;
            case "this": name = "THIS"; break;
            case "that": name = "THAT"; break;
            case "pointer": name = "3"; break;
            case "temp": name = "5"; break;
            default: name = ""; break;
        }
        
        if (command == CommandType.C_PUSH) {    // store x at the array entry pointed to by sp, then increment sp
            
            if (segment.equals("constant")) {
                writeLine("@" + index); // A = index
                writeLine("D=A");       // D = index
            }
            
            else if (segment.matches("local|argument|this|that")) {
                writeLine("@" + name);      // A = the pointer to the segment (local, argument, this, or that)
                writeLine("D=M");           // D = the address of the segment
                writeLine("@" + index);     // A = index
                writeLine("A=D+A");         // A = the address of the segment + index (i.e. the address we're trying to push from)
                writeLine("D=M");           // D = the value stored at the address we're trying to push from
            }
            
            else if (segment.matches("pointer|temp")) {
                writeLine("@" + name);      // A = the address of the segment (3 or 5)
                writeLine("D=A");           // D = the address of the segment
                writeLine("@" + index);     // A = index
                writeLine("A=D+A");         // A = the address of the segment + index (i.e. the address we're trying to push from)
                writeLine("D=M");           // D = the value stored at the address we're trying to push from
            }
            
            else if (segment.equals("static")) {
                writeLine("@" + filename + "." + index);    // @f.j
                writeLine("D=M");                           // D = the value stored at f.j
            }
            
            else {
                System.out.println("Error: not a valid segment!");
            }
            
            writeLine("@SP");   // go to the stack pointer
            writeLine("A=M");   // go to the top of the stack
            writeLine("M=D");   // the value at the top of the stack = D
            writeLine("@SP");   // go to the stack pointer
            writeLine("M=M+1"); // increment the stack pointer's value by 1
        }
        
        else if (command == CommandType.C_POP) {    // decrement sp, then return the value stored at the array entry pointed to by sp
            
            if (segment.matches("local|argument|this|that")) {
                writeLine("@" + name);      // A = the pointer to the segment (local, argument, this, or that)
                writeLine("D=M");           // D = the address of the segment
                writeLine("@" + index);     // A = index
                writeLine("D=D+A");         // D = the address of the segment + index (i.e. the address we're trying to pop to)
                writeLine("@pop_address");  // A = a new variable called "pop_address"
                writeLine("M=D");           // stores D (the address we're trying to pop to) at pop_address
            }
            
            else if (segment.matches("pointer|temp")) {
                writeLine("@" + name);      // A = the address of the segment (3 or 5);
                writeLine("D=A");           // A = the address of the segment
                writeLine("@" + index);     // A = index
                writeLine("D=D+A");         // D = the address of the segment + index (i.e. the address we're trying to pop to)
                writeLine("@pop_address");  // A = a new variable called "pop_address"
                writeLine("M=D");           // stores D (the address we're trying to pop to) at pop_address
            }
            
            else if (segment.equals("static")) {
                writeLine("@" + filename + "." + index);    // @f.j
                writeLine("D=A");                           // D = f.j
                writeLine("@pop_address");                  // A = a new variable called "pop_address"
                writeLine("M=D");                           // stores D (f.j) at pop_address
            }
            
            else {
                System.out.println("Error: not a valid segment!");
            }
            
            writeLine("@SP");           // go to the stack pointer
            writeLine("M=M-1");         // decrement the stack pointer's value by 1
            writeLine("A=M");           // go to the top of the stack
            writeLine("D=M");           // D = the value at the top of the stack
            writeLine("@pop_address");  // A = pop_address
            writeLine("A=M");           // A = the value stored at pop_address (i.e. the address we're trying to pop to)
            writeLine("M=D");           // stores D at the address we're trying to pop to
        }
        
        else {
            System.out.println("Error: not a push or pop command!");
        }
    }
    
    public void writeInit() {
        
    }
    
    public void writeLabel(String label) {
        writeLine("(" + label + ")");   // (label)
    }
    
    public void writeGoto(String label) {
        writeLine("@" + label);     // @label
        writeLine("0;JMP");         // unconditional jump to label
    }
    
    public void writeIf(String label) {
        // First, pop the value at the top of the stack to the D register.
        writeLine("@SP");       // go to the stack pointer
        writeLine("M=M-1");     // decrement the stack pointer's value by 1
        writeLine("A=M");       // go to the top of the stack
        writeLine("D=M");       // D = the value at the top of the stack
        
        // Next, perform the conditional jump.
        writeLine("@" + label); // @label
        writeLine("D;JNE");     // if D != 0, then jump to label
    }
    
    public void writeCall(String functionName, int numArgs) {
        
    }
    
    public void writeReturn() {
        
    }
    
    public void writeFunction(String functionName, int numLocals) {
        
    }
    
    public void close() {
        try {
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
}
