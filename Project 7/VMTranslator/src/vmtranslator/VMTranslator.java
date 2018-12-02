// Main class
package vmtranslator;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VMTranslator {

    public static void main(String[] args) {
        
        String filepath = args[0];
        System.out.println(filepath);
        String output = filepath.split(".vm")[0] + ".asm";
        System.out.println(output);
        
        Path inputFile = Paths.get(filepath);
        Path outputFile = Paths.get(output);
        Boolean moreCommands;
        String outputLine;
        
        Parser p = new Parser(inputFile);
        CodeWriter c = new CodeWriter(outputFile);
        moreCommands = p.hasMoreCommands();
        
        while (moreCommands == true) {
            p.readLine();
            p.parseLine();
            if (p.getCommand().getArg1() != null) {
                outputLine = p.getCommand().getType().name() + " " + p.getCommand().getArg1() + " " + p.getCommand().getArg2();
                System.out.println(outputLine);
                c.writeLine(outputLine);
            }
            moreCommands = p.hasMoreCommands();
            p.advance();
        }
        
        c.close();
    }
}
