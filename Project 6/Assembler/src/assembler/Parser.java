package assembler;
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
            
            if (currentLine.charAt(0) == '(') {
                currentCommand.setType(CommandType.L_COMMAND);
                String str = currentLine.substring(currentLine.indexOf("(") + 1, currentLine.indexOf(")"));
                currentCommand.setSymbol(str);
                System.out.println(currentCommand.getType() + " " + currentCommand.getSymbol());
            }
                
            else if (currentLine.charAt(0) == '@') {
                currentCommand.setType(CommandType.A_COMMAND);
                currentCommand.setSymbol(currentLine.substring(1));
                System.out.println(currentCommand.getType() + " " + currentCommand.getSymbol());
            }
                
            else {
                currentCommand.setType(CommandType.C_COMMAND);
                if (currentLine.contains("=") && currentLine.contains(";")) {
                    String str = currentLine.substring(currentLine.indexOf("=") + 1, currentLine.indexOf(";"));
                    currentCommand.setDest(currentLine.split("=")[0]);
                    currentCommand.setComp(str);
                    currentCommand.setJump(currentLine.split(";")[1]);
                }
                else if (currentLine.contains("=")) {
                    currentCommand.setDest(currentLine.split("=")[0]);
                    currentCommand.setComp(currentLine.split("=")[1]);
                }
                else if (currentLine.contains(";")) {
                    currentCommand.setComp(currentLine.split(";")[0]);
                    currentCommand.setJump(currentLine.split(";")[1]);
                }
                else {
                    currentCommand.setComp(currentLine);
                }
                System.out.println(currentCommand.getType() + " " + currentCommand.getDest() + "=" + currentCommand.getComp() + ";" + currentCommand.getJump());
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
