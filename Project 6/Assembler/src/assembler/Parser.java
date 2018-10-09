package assembler;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Parser {
    
    Path inputFile, outputFile;
    BufferedReader reader;
    String currentLine;
    Command currentCommand;
    boolean moreCommands;
    
    public Parser(Path input) {
        inputFile = input;
        moreCommands = true;
        
        try {
            reader = Files.newBufferedReader(inputFile, StandardCharsets.UTF_8);
            currentLine = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean hasMoreCommands() {
        if (currentLine == null) {
            System.out.println("null");
            moreCommands = false;
        }
        return moreCommands;
    }
    
    public void readLine() {
        if (currentLine != null) {
            System.out.println(currentLine);
        }
    }
    
    public void advance() {
        try {
            currentLine = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
