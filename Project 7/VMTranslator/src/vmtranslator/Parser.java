package vmtranslator;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Parser {
    
    Path inputFile;
    BufferedReader reader;
    String currentLine;
    Command currentCommand;
    boolean moreCommands;
    
    public Parser(Path input) {
        this.inputFile = input;
        this.moreCommands = true;
        
        try {
            reader = Files.newBufferedReader(inputFile, StandardCharsets.UTF_8);
            currentLine = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean hasMoreCommands() {
        if (currentLine == null) {
            moreCommands = false;
        }
        return moreCommands;
    }
    
    public void readLine() {
        if (currentLine != null) {
            System.out.println(currentLine);
        }
    }
    
    public void parseLine() {
        currentCommand = new Command();
        if (currentLine != null && currentLine.length() > 0 && currentLine.charAt(0) != '/') {
            
            currentLine = currentLine.replaceAll("\\s","");
            if (currentLine.contains("/")) {
                currentLine = currentLine.split("/", 2)[0];
            }
            
        }
    }
    
    public Command getCommand() {
        return currentCommand;
    }
    
    public void advance() {
        try {
            currentLine = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
