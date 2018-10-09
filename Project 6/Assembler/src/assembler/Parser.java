package assembler;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Parser {
    
    Path inputFile, outputFile;
    BufferedReader reader;
    int currentLine;
    Command currentCommand;
    boolean moreCommands;
    
    public Parser(Path input) {
        inputFile = input;
        
        try {
            reader = Files.newBufferedReader(inputFile, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean hasMoreCommands() {
        return moreCommands;
    }
    
    public void advance() {
        
    }
    
}
