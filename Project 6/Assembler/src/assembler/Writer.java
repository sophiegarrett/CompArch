package assembler;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Writer {
    
    Path outputFile;
    
    public Writer(Path output) {
        this.outputFile = output;
    }
    
    public void writeLine(String s) {
        
    }
}
