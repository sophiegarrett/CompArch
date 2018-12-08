// Main class
package vmtranslator;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VMTranslator {

    public static void main(String[] args) {
        
        String filepath = args[0];
        System.out.println(filepath);
        String outputFile = filepath.split(".vm")[0];
        System.out.println(outputFile);
        
        Path inputFile = Paths.get(filepath);
        Boolean moreCommands;
        String outputLine;
        
        Parser p = new Parser(inputFile);
        CodeWriter c = new CodeWriter(outputFile);
        moreCommands = p.hasMoreCommands();
        
        while (moreCommands == true) {
            p.readLine();
            p.parseLine();
            c.translate(p.getCommand());
            moreCommands = p.hasMoreCommands();
            p.advance();
        }
        
        c.close();
    }
}
