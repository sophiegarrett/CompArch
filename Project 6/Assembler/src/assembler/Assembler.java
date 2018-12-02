// Main class
package assembler;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Assembler {

    public static void main(String[] args) {
        
        String filepath = args[0];
        System.out.println(filepath);
        String outputfile = filepath.split(".asm")[0] + ".hack";
        System.out.println(outputfile);
        
        Path inputFile = Paths.get(filepath);
        Path outputFile = Paths.get(outputfile);
        Boolean moreCommands;
        int lineCounter;
        String outputLine;
        
        Parser p = new Parser(inputFile);
        Code c = new Code();
        SymbolTable s = new SymbolTable();
        Writer w = new Writer(outputFile);
        moreCommands = p.hasMoreCommands();
        lineCounter = 0;
        
        // First pass
        while (moreCommands == true) {
            p.readLine();
            p.parseLine();
            Command cmd = p.getCommand();
            if (cmd.getType() == CommandType.L_COMMAND) {
                s.addEntry(cmd.getSymbol(), lineCounter);
            }
            else if (cmd.getType() == CommandType.A_COMMAND || cmd.getType() == CommandType.C_COMMAND) {
                lineCounter++;
            }
            moreCommands = p.hasMoreCommands();
            p.advance();
        }
        
        p = new Parser(inputFile);
        moreCommands = p.hasMoreCommands();
        
        // Second pass
        while (moreCommands == true) {
            p.readLine();
            p.parseLine();
            outputLine = c.translate(p.getCommand());
            w.writeLine(outputLine);
            moreCommands = p.hasMoreCommands();
            p.advance();
        }
        
        w.close();
    }
}
