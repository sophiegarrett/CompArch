package vmtranslator;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CodeWriter {
    
    Path outputFile;
    BufferedWriter writer;
    
    public CodeWriter(Path output) {
        this.outputFile = output;
        
        try {
            writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
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
    
    public void setFileName(String fileName) {
        
    }
    
    public void writeArithmetic(String command) {
        
    }
    
    public void writePushPop(CommandType command, String segment, int index) {
        
    }
    
    public void close() {
        try {
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
}
