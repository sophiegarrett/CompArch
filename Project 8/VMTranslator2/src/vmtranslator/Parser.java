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
            if (currentLine.contains("//")) {
                currentLine = currentLine.split("//", 2)[0];
            }
            if (currentLine.contains("\t")) {
                currentLine = currentLine.split("\t", 2)[0];
            }
            
            String array[] = currentLine.split(" ", 4);
            
            if (array[0].matches("add|sub|neg|eq|gt|lt|and|or|not")) {
                currentCommand.setType(CommandType.C_ARITHMETIC);
                currentCommand.setArg1(array[0]);
            }
            
            else if (array[0].equals("return")) {
                currentCommand.setType(CommandType.C_RETURN);
            }
            
            else {
                switch(array[0]) {
                    case "push": currentCommand.setType(CommandType.C_PUSH); break;
                    case "pop": currentCommand.setType(CommandType.C_POP); break;
                    case "label": currentCommand.setType(CommandType.C_LABEL); break;
                    case "goto": currentCommand.setType(CommandType.C_GOTO); break;
                    case "if-goto": currentCommand.setType(CommandType.C_IF); break;
                    case "function": currentCommand.setType(CommandType.C_FUNCTION); break;
                    case "call": currentCommand.setType(CommandType.C_CALL); break;
                }
                
                currentCommand.setArg1(array[1]);
                
                if (array[0].matches("push|pop|function|call")) {
                    currentCommand.setArg2(Integer.parseInt(array[2]));
                }
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
